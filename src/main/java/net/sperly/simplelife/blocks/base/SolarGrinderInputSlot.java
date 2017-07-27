package net.sperly.simplelife.blocks.base;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.sperly.simplelife.helpers.GrinderRecipes;

import javax.annotation.Nonnull;

public class SolarGrinderInputSlot extends SlotItemHandler
{
    public SolarGrinderInputSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition)
    {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack itemStack) {

        int grindRecipes = GrinderRecipes.instance().getGrindingResult(itemStack).getCount();
        if(grindRecipes > 0)
        {
            return true;
        }
        return false;
    }
}