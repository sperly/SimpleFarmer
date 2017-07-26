package net.sperly.simplelife.blocks.solarfurnace;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.ITickable;

import net.sperly.simplelife.blocks.base.ISolarTileEntity;

public class SolarFurnaceTileEntity extends ISolarTileEntity implements ITickable, IInventory
{
    private static int smeltTime = 0;

    @Override
    public void update()
    {
        super.update();

        if (!getWorld().isRemote)
        {
            if (itemStackHandler.getStackInSlot(INPUT_SLOT).getCount() > 0)
            {
                if (FurnaceRecipes.instance().getSmeltingResult(itemStackHandler.getStackInSlot(INPUT_SLOT)).getCount() > 0)
                {
                    ItemStack smeltedItems = FurnaceRecipes.instance().getSmeltingResult(itemStackHandler.getStackInSlot(INPUT_SLOT)).copy();
                    smeltedItems.setCount(itemStackHandler.getStackInSlot(INPUT_SLOT).getCount());
                    int slot = getFirstAvailableOutputSlot();
                    if ((slot > 0) && (slot <= OUTPUT_SLOTS[8]))
                    {
                        itemStackHandler.setStackInSlot(slot, smeltedItems);
                        itemStackHandler.setStackInSlot(INPUT_SLOT, ItemStack.EMPTY);
                        SolarFurnaceTileEntity.this.markDirty();
                    }
                }
            }
        }
    }
}
