package net.sperly.simplelife.blocks.solargrinder;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.sperly.simplelife.blocks.base.ISolarGui;
import net.sperly.simplelife.blocks.base.SolarContainerBase;

@SideOnly(Side.CLIENT)
public class SolarGrinderGui extends ISolarGui{

    private SolarGrinderTileEntity te;

    public SolarGrinderGui(SolarGrinderTileEntity tileEntity, SolarContainerBase container) {
        super(tileEntity, container, "textures/gui/solarfurnacegui.png");
        this.te = tileEntity;
    }
}
