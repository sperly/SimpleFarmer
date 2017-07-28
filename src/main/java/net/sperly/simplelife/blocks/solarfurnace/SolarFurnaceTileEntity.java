package net.sperly.simplelife.blocks.solarfurnace;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import net.sperly.simplelife.blocks.base.ISolarTileEntity;

public class SolarFurnaceTileEntity extends ISolarTileEntity
{
    public SolarFurnaceTileEntity()
    {
        this.checkUpgradeSlot();
    }

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

                    if(this.workTimeRemaining == 0)
                    {
                        if(moveOneItemToOutput())
                            this.workTimeRemaining = this.workTime;

                    }
                    else
                    {
                        this.workTimeRemaining -= 1;
                    }
                    /*ItemStack smeltedItems = FurnaceRecipes.instance().getSmeltingResult(itemStackHandler.getStackInSlot(INPUT_SLOT)).copy();
                    smeltedItems.setCount(itemStackHandler.getStackInSlot(INPUT_SLOT).getCount());
                    int rest = mergeStacksInInventory(smeltedItems);
                    if(rest > 0)
                    {
                        ItemStack restStack = itemStackHandler.getStackInSlot(INPUT_SLOT);
                        restStack.setCount(rest);
                        itemStackHandler.setStackInSlot(INPUT_SLOT, restStack);
                    }
                    else if (rest == 0)
                    {
                        itemStackHandler.setStackInSlot(INPUT_SLOT, ItemStack.EMPTY);
                    }*/
                    markDirty();

                }
            }
            else
            {
                workTimeRemaining = workTime;
            }
        }
    }

    private boolean moveOneItemToOutput()
    {
        ItemStack smeltedItems = FurnaceRecipes.instance().getSmeltingResult(itemStackHandler.getStackInSlot(INPUT_SLOT)).copy();
        smeltedItems.setCount(1);
        int rest = mergeStacksInInventory(smeltedItems);
        if (rest == 0)
        {
            ItemStack restStack = itemStackHandler.getStackInSlot(INPUT_SLOT);
            restStack.setCount(itemStackHandler.getStackInSlot(INPUT_SLOT).getCount() - 1);
            itemStackHandler.setStackInSlot(INPUT_SLOT, restStack);
            return true;
        }
        return false;
    }
}
