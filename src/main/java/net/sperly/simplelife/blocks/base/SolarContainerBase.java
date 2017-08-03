package net.sperly.simplelife.blocks.base;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import net.sperly.simplelife.blocks.solarfurnace.SolarFurnaceTileEntity;
import net.sperly.simplelife.blocks.solargrinder.SolarGrinderTileEntity;
import net.sperly.simplelife.helpers.GrinderRecipes;
import net.sperly.simplelife.items.SolarCellUpgrade1Item;
import net.sperly.simplelife.items.SolarCellUpgrade2Item;

public class SolarContainerBase extends Container
{
    private ISolarTileEntity ste;
    private int [] cachedFields;

    protected final int HOTBAR_SLOT_COUNT = 9;
    protected final int PLAYER_INVENTORY_ROW_COUNT = 3;
    protected final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    protected final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    protected final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;

    public final int UPGRADE_SLOTS_COUNT = 1;
    public final int INPUT_SLOTS_COUNT = 1;
    public final int OUTPUT_SLOTS_COUNT = 9;
    public final int MACHINE_SLOTS_COUNT = UPGRADE_SLOTS_COUNT + INPUT_SLOTS_COUNT + OUTPUT_SLOTS_COUNT;

    // slot index is the unique index for all slots in this container i.e. 0 - 35 for invPlayer then 36 - 49 for tileInventoryFurnace
    protected final int VANILLA_FIRST_SLOT_INDEX = 0;
    protected final int FIRST_UPGRADE_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
    protected final int FIRST_INPUT_SLOT_INDEX = FIRST_UPGRADE_SLOT_INDEX + UPGRADE_SLOTS_COUNT;
    protected final int FIRST_OUTPUT_SLOT_INDEX = FIRST_INPUT_SLOT_INDEX + INPUT_SLOTS_COUNT;

    // slot number is the slot number within each component; i.e. invPlayer slots 0 - 35, and tileInventoryFurnace slots 0 - 14
    protected final int FIRST_UPGRADE_SLOT_NUMBER = 0;
    protected final int FIRST_INPUT_SLOT_NUMBER = FIRST_UPGRADE_SLOT_NUMBER + UPGRADE_SLOTS_COUNT;
    protected final int FIRST_OUTPUT_SLOT_NUMBER = FIRST_INPUT_SLOT_NUMBER + INPUT_SLOTS_COUNT;

    public SolarContainerBase(IInventory playerInventory, ISolarTileEntity ste) {
        this.ste = ste;

        addPlayerSlots(playerInventory);
        addLocalSlots();
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    // This is where you specify what happens when a player shift clicks a slot in the gui
    //  (when you shift click a slot in the TileEntity Inventory, it moves it to the first available position in the hotbar and/or
    //    player inventory.  When you you shift-click a hotbar or player inventory item, it moves it to the first available
    //    position in the TileEntity inventory - either input or fuel as appropriate for the item you clicked)
    // At the very least you must override this and return EMPTY_ITEM or the game will crash when the player shift clicks a slot
    // returns EMPTY_ITEM if the source slot is empty, or if none of the source slot items could be moved.
    //   otherwise, returns a copy of the source stack
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int sourceSlotIndex)
    {
        Slot sourceSlot = inventorySlots.get(sourceSlotIndex);
        if (sourceSlot == null || !sourceSlot.getHasStack()) return ItemStack.EMPTY;
        ItemStack sourceStack = sourceSlot.getStack();
        ItemStack copyOfSourceStack = sourceStack.copy();



        // Check if the slot clicked is one of the vanilla container slots
        if (sourceSlotIndex >= VANILLA_FIRST_SLOT_INDEX && sourceSlotIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {

            // If the stack is smeltable and it is a SolarFurnace, try to merge merge the stack into the input slots.
            if (FurnaceRecipes.instance().getSmeltingResult(sourceStack).getCount() > 0 && (ste instanceof SolarFurnaceTileEntity)){
                if (!mergeItemStack(sourceStack, FIRST_INPUT_SLOT_INDEX, FIRST_INPUT_SLOT_INDEX + INPUT_SLOTS_COUNT, false)){
                    return ItemStack.EMPTY;
                }
            }

            //If item is grindable and the block is a grinder.
            if (GrinderRecipes.instance().getGrindingResult(sourceStack).getCount() > 0 && (ste instanceof SolarGrinderTileEntity)){
                if (!mergeItemStack(sourceStack, FIRST_INPUT_SLOT_INDEX, FIRST_INPUT_SLOT_INDEX + INPUT_SLOTS_COUNT, false)){
                    return ItemStack.EMPTY;
                }
            }

            //If Item is fertilizer and the block is a Harvester.

            // If items are of upgrade type, try putting it i the upgrade slot.
            else if ((sourceStack.getItem() instanceof SolarCellUpgrade1Item) || (sourceStack.getItem() instanceof SolarCellUpgrade2Item)) {
                if (!mergeItemStack(sourceStack, FIRST_UPGRADE_SLOT_INDEX, FIRST_UPGRADE_SLOT_INDEX + UPGRADE_SLOTS_COUNT, true)) {
                    return ItemStack.EMPTY;
                }
                ste.checkUpgradeSlot();
            }
            else {
                return ItemStack.EMPTY;
            }

        }
        else if (sourceSlotIndex >= FIRST_UPGRADE_SLOT_INDEX && sourceSlotIndex < FIRST_UPGRADE_SLOT_INDEX + MACHINE_SLOTS_COUNT) {
            // This is a furnace slot so merge the stack into the players inventory: try the hotbar first and then the main inventory
            //   because the main inventory slots are immediately after the hotbar slots, we can just merge with a single call
            if (!mergeItemStack(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
            ste.checkUpgradeSlot();
        }
        else {
            System.err.print("Invalid slotIndex:" + sourceSlotIndex);
            return ItemStack.EMPTY;
        }

        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.putStack(ItemStack.EMPTY);
        } else {
            sourceSlot.onSlotChanged();
        }

        sourceSlot.onTake(player, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        boolean allFieldsHaveChanged = false;
        boolean fieldHasChanged [] = new boolean[ste.getFieldCount()];
        if (cachedFields == null) {
            cachedFields = new int[ste.getFieldCount()];
            allFieldsHaveChanged = true;
        }
        for (int i = 0; i < cachedFields.length; ++i) {
            if (allFieldsHaveChanged || cachedFields[i] != ste.getField(i)) {
                cachedFields[i] = ste.getField(i);
                fieldHasChanged[i] = true;
            }
        }

        // go through the list of listeners (players using this container) and update them if necessary
        for (IContainerListener listener : this.listeners) {
            for (int fieldID = 0; fieldID < ste.getFieldCount(); ++fieldID) {
                if (fieldHasChanged[fieldID]) {
                    // Note that although sendProgressBarUpdate takes 2 ints on a server these are truncated to shorts
                    listener.sendWindowProperty(this, fieldID, cachedFields[fieldID]);
                    //listener.sendProgressBarUpdate(this, fieldID, cachedFields[fieldID]);
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int id, int data) {
        ste.setField(id, data);
    }

    private void addPlayerSlots(IInventory playerInventory) {
        // Slots for the main inventory 0 - 35
        for (int row = 0; row < 3; ++row)
        {
            for (int col = 0; col < 9; ++col)
            {
                int x = 10 + col * 18;
                int y = row * 18 + 70;
                addSlotToContainer(new Slot(playerInventory, col + row * 9 + 9, x, y));
            }
        }

        // Slots for the hotbar
        for (int row = 0; row < 9; ++row)
        {
            int x = 10 + row * 18;
            int y = 58 + 70;
            addSlotToContainer(new Slot(playerInventory, row, x, y));
        }
    }

    protected void addLocalSlots()
    {
        // Add our own slots 36 - 47
        IItemHandler itemHandler = ste.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        int slotIndex = FIRST_UPGRADE_SLOT_NUMBER;

        // Upgrade slot
        int x = 10;
        int y = 45;
        addSlotToContainer(new SolarUpgradeSlot(itemHandler, slotIndex, x, y));

        slotIndex = FIRST_INPUT_SLOT_NUMBER;

        //Input slot
        x = 82;
        y = 45;
        if(ste instanceof SolarFurnaceTileEntity)
        {
            addSlotToContainer(new SolarFurnaceInputSlot(itemHandler, slotIndex, x, y));
        }
        else if(ste instanceof SolarGrinderTileEntity)
        {
            addSlotToContainer(new SolarGrinderInputSlot(itemHandler, slotIndex, x, y));
        }
        //else if(ste instanceof SolarHarvesterTileEntity)
        //{
        //    addSlotToContainer(new SolarHarvesterFertilizerSlot(itemHandler, slotIndex, x, y));
        //}
        slotIndex++;

        for (int row = 0; row < 3; ++row)
        {
            for (int col = 0; col < 3; ++col)
            {
                x = 118 + col * 18;
                y = row * 18 + 9;
                addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex, x, y));
                slotIndex++;
            }
        }



    }
}
