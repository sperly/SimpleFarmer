package net.sperly.simplelife.blocks.base;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class SolarFurnaceInputSlot extends SlotItemHandler
{
    public SolarFurnaceInputSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition)
    {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack itemStack) {
        int smeltRecipes = FurnaceRecipes.instance().getSmeltingResult(itemStack).getCount();
        if(smeltRecipes > 0)
        {
            return true;
        }
        return false;
    }
}