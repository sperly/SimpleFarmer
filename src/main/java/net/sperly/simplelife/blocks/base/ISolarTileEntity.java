package net.sperly.simplelife.blocks.base;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.sperly.simplelife.items.SolarCellUpgrade1Item;
import net.sperly.simplelife.items.SolarCellUpgrade2Item;

public abstract class ISolarTileEntity extends TileEntity implements ITickable, IInventory
{
    public static final int SIZE = 11;

    public static final int[] UPGRADE_SLOTS = {0};
    public static final int INPUT_SLOT = 1;
    public static final int[] OUTPUT_SLOTS = {2, 3, 4, 5, 6, 7, 8, 9, 10};

    protected static int solarUpgradeLevel = 0;
    protected static boolean isWorking = false;

    // This item handler will hold our nine inventory slots
    protected ItemStackHandler itemStackHandler = new ItemStackHandler(SIZE)
    {
        @Override
        protected void onContentsChanged(int slot)
        {
            // We need to tell the tile entity that something has changed so
            // that the chest contents is persisted
            if (!itemStackHandler.getStackInSlot(UPGRADE_SLOTS[0]).isEmpty())
            {
                Item upgrades = itemStackHandler.getStackInSlot(UPGRADE_SLOTS[0]).getItem();
                if (upgrades instanceof SolarCellUpgrade1Item)
                    solarUpgradeLevel = 1;
                else if (upgrades instanceof SolarCellUpgrade2Item)
                    solarUpgradeLevel = 2;
                else
                    solarUpgradeLevel = 0;
            }
            else
                solarUpgradeLevel = 0;

            ISolarTileEntity.super.markDirty();
        }
    };

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        if (compound.hasKey("items"))
        {
            itemStackHandler.deserializeNBT((NBTTagCompound) compound.getTag("items"));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setTag("items", itemStackHandler.serializeNBT());
        return compound;
    }

    public int getUpgradeLevel()
    {
        return solarUpgradeLevel;
    }

    public boolean hasEnoughSun()
    {
        return isWorking;
    }

    public boolean canInteractWith(EntityPlayer playerIn)
    {
        // If we are too far away from this tile entity you cannot use it
        return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemStackHandler);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void update()
    {
        if (!getWorld().isRemote)
        {
            long time = getWorld().getWorldTime();
            if (((time < 450) || (time > 7700)) && solarUpgradeLevel < 1)
            {
                isWorking = false;
            }
            else if (((time > 12500) && (time < 23000)) && solarUpgradeLevel < 2)
            {
                isWorking = false;
            }
            else
            {
                isWorking = true;
            }
        }
    }

    protected int getFirstAvailableOutputSlot()
    {
        for (int slot : OUTPUT_SLOTS)
        {
            if (itemStackHandler.getStackInSlot(slot).getCount() == 0)
                return slot;
        }
        return -1;
    }

    @Override
    public int getSizeInventory()
    {
        return itemStackHandler.getSlots();
    }

    @Override
    public boolean isEmpty()
    {
        for(int i =0 ; i < itemStackHandler.getSlots();++i)
        {
            if(!itemStackHandler.getStackInSlot(i).isEmpty())
                return false;
        }
        return true;
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return itemStackHandler.getStackInSlot(slot);
    }

    @Override
    public ItemStack decrStackSize(int slot, int count)
    {
        ItemStack stack = itemStackHandler.getStackInSlot(slot);
        ItemStack returnStack = ItemStack.EMPTY;
        if(stack.isEmpty())
        {
            itemStackHandler.setStackInSlot(slot, ItemStack.EMPTY);
        }
        else if( stack.getCount() <= count)
        {
            itemStackHandler.setStackInSlot(slot, ItemStack.EMPTY);
            returnStack = stack;
        }
        else
        {
            returnStack = stack.splitStack(count);
            itemStackHandler.setStackInSlot(slot, stack);
        }

        return returnStack;
    }

    @Override
    public ItemStack removeStackFromSlot(int slot)
    {
        ItemStack stack = itemStackHandler.getStackInSlot(slot);
        itemStackHandler.setStackInSlot(slot, ItemStack.EMPTY);
        return stack;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemStack)
    {
        if (!itemStack.isEmpty() && itemStack.getCount() > getInventoryStackLimit()) {  // isEmpty();  getStackSize()
            itemStack.setCount(getInventoryStackLimit());  //setStackSize()
        }
        itemStackHandler.setStackInSlot(slot, itemStack);
        markDirty();
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer entityPlayer)
    {
        if (getWorld().getTileEntity(this.pos) != this) return false;

        return true;
    }

    @Override
    public void openInventory(EntityPlayer entityPlayer)
    {

    }

    @Override
    public void closeInventory(EntityPlayer entityPlayer)
    {

    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemStack)
    {
        return false;
    }

    @Override
    public int getField(int i)
    {
        return 0;
    }

    @Override
    public void setField(int i, int i1)
    {

    }

    @Override
    public int getFieldCount()
    {
        return 0;
    }

    @Override
    public void clear()
    {

    }

    @Override
    public String getName()
    {
        return null;
    }

    @Override
    public boolean hasCustomName()
    {
        return false;
    }
}
