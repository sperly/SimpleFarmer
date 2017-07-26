package net.sperly.simplelife.items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.sperly.simplelife.SimpleLife;

public class SolarCellUpgrade2Item extends Item
{
    public SolarCellUpgrade2Item() {
        setRegistryName("solarcellupgrade2item");
        setUnlocalizedName(SimpleLife.MODID + ".solarcellupgrade2item");
        setCreativeTab(SimpleLife.tabSimpleLife);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
