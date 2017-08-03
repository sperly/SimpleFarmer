package net.sperly.simplelife.blocks.solargrinder;

import net.minecraft.item.ItemStack;

import net.minecraft.util.EnumFacing;
import net.sperly.simplelife.blocks.base.ISolarTileEntity;
import net.sperly.simplelife.blocks.solarfurnace.SolarFurnaceTileEntity;
import net.sperly.simplelife.helpers.GrinderRecipes;

public class SolarGrinderTileEntity extends ISolarTileEntity
{

    public SolarGrinderTileEntity()
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
                if (GrinderRecipes.instance().getGrindingResult(itemStackHandler.getStackInSlot(INPUT_SLOT)).getCount() > 0)
                {
                    if(this.workTimeRemaining == 0)
                    {
                        if(moveOneItemToOutput())
                            this.workTimeRemaining = this.workTime;

                    }
                    else
                    {
                        if(isWorking)
                            this.workTimeRemaining -= 1;
                    }
                    markDirty();
                }
            }
            else
            {
                workTimeRemaining = workTime;
            }
        }
    }
    @Override
    public boolean canInsertItem(int i, ItemStack itemStack, EnumFacing enumFacing)
    {
        if((enumFacing != EnumFacing.DOWN) && (GrinderRecipes.instance().getGrindingResult(itemStackHandler.getStackInSlot(INPUT_SLOT)).getCount() > 0))
            return true;
        else
            return false;
    }
}
