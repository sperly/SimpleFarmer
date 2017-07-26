package net.sperly.simplelife.blocks.base;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

import net.sperly.simplelife.items.SolarCellUpgrade1Item;
import net.sperly.simplelife.items.SolarCellUpgrade2Item;

public class SolarUpgradeSlot extends SlotItemHandler
{
    public SolarUpgradeSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition)
    {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack itemStack)
    {
        if(itemStack.getItem() instanceof SolarCellUpgrade1Item || itemStack.getItem() instanceof SolarCellUpgrade2Item)
        {
            return true;
        }
        return false;
    }
}