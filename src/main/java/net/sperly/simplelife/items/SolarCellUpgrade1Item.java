package net.sperly.simplelife.items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.sperly.simplelife.SimpleLife;

public class SolarCellUpgrade1Item extends Item
{
    public SolarCellUpgrade1Item() {
        setRegistryName("solarcellupgrade1item");
        setUnlocalizedName(SimpleLife.MODID + ".solarcellupgrade1item");
        setCreativeTab(SimpleLife.tabSimpleLife);
        setMaxStackSize(1);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
