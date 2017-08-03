package net.sperly.simplelife.blocks.solarharvester;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

import net.sperly.simplelife.blocks.base.ISolarTileEntity;

public class SolarHarvesterTileEntity extends ISolarTileEntity
{
    @Override
    public void update()
    {
        super.update();

        if (!getWorld().isRemote)
        {

        }
    }

    @Override
    public boolean canInsertItem(int i, ItemStack itemStack, EnumFacing enumFacing)
    {
        return false;
    }
}
