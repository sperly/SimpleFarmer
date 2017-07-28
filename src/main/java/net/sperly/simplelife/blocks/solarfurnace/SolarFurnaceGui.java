package net.sperly.simplelife.blocks.solarfurnace;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.sperly.simplelife.blocks.base.ISolarGui;
import net.sperly.simplelife.blocks.base.SolarContainerBase;

@SideOnly(Side.CLIENT)
public class SolarFurnaceGui extends ISolarGui
{
    public SolarFurnaceGui(SolarFurnaceTileEntity tileEntity, SolarContainerBase container) {
        super(tileEntity, container, "textures/gui/solarfurnacegui.png");
    }
}
