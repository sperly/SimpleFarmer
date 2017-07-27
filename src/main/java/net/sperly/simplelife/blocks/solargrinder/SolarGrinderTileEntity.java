package net.sperly.simplelife.blocks.solargrinder;

import net.minecraft.item.ItemStack;

import net.sperly.simplelife.blocks.base.ISolarTileEntity;
import net.sperly.simplelife.helpers.GrinderRecipes;

public class SolarGrinderTileEntity extends ISolarTileEntity
{
    @Override
    public void update()
    {
        super.update();

        if (!getWorld().isRemote)
        {
            if (itemStackHandler.getStackInSlot(INPUT_SLOT).getCount() > 0)
            {
                if (GrinderRecipes.instance().getGrindingResult(itemStackHandler.getStackInSlot(INPUT_SLOT)).getCount() > 0)
                {
                    ItemStack grindedItems = GrinderRecipes.instance().getGrindingResult(itemStackHandler.getStackInSlot(INPUT_SLOT)).copy();
                    grindedItems.setCount(itemStackHandler.getStackInSlot(INPUT_SLOT).getCount());
                    int rest = mergeStacksInInventory(grindedItems);
                    if(rest > 0)
                    {
                        ItemStack restStack = itemStackHandler.getStackInSlot(INPUT_SLOT);
                        restStack.setCount(rest);
                        itemStackHandler.setStackInSlot(INPUT_SLOT, restStack);
                    }
                    else if (rest == 0)
                    {
                        itemStackHandler.setStackInSlot(INPUT_SLOT, ItemStack.EMPTY);
                    }
                    markDirty();
                }
            }
        }
    }
}
