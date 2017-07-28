package net.sperly.simplelife.blocks.solarfurnace;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.sperly.simplelife.blocks.base.ISolarGui;
import net.sperly.simplelife.blocks.base.SolarContainerBase;

import java.awt.*;
import java.util.*;

@SideOnly(Side.CLIENT)
public class SolarFurnaceGui extends ISolarGui
{
    private SolarFurnaceTileEntity te;

    public SolarFurnaceGui(SolarFurnaceTileEntity tileEntity, SolarContainerBase container) {
        super(tileEntity, container, "textures/gui/solarfurnacegui.png");
        this.te = tileEntity;
    }

    final int  WORK_BAR_XPOS = 100;
    final int WORK_BAR_YPOS = 45;
    final int WORK_BAR_ICON_U = 0;   // texture position of white arrow icon
    final int WORK_BAR_ICON_V = 180;
    final int WORK_BAR_WIDTH = 16;
    final int WORK_BAR_HEIGHT = 16;


}
