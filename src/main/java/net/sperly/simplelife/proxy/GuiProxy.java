package net.sperly.simplelife.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.sperly.simplelife.blocks.base.SolarContainerBase;
import net.sperly.simplelife.blocks.solarfurnace.SolarFurnaceGui;
import net.sperly.simplelife.blocks.solarfurnace.SolarFurnaceTileEntity;
import net.sperly.simplelife.blocks.solargrinder.SolarGrinderGui;
import net.sperly.simplelife.blocks.solargrinder.SolarGrinderTileEntity;
import net.sperly.simplelife.blocks.solarharvester.SolarHarvesterGui;
import net.sperly.simplelife.blocks.solarharvester.SolarHarvesterTileEntity;

public class GuiProxy implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof SolarFurnaceTileEntity) {
            return new SolarContainerBase(player.inventory, (SolarFurnaceTileEntity) te);
        }
        else if (te instanceof SolarGrinderTileEntity) {
            return new SolarContainerBase(player.inventory, (SolarGrinderTileEntity) te);
        }
        else if (te instanceof SolarHarvesterTileEntity) {
            return new SolarContainerBase(player.inventory, (SolarHarvesterTileEntity) te);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof SolarFurnaceTileEntity) {
            return new SolarFurnaceGui((SolarFurnaceTileEntity) te, new SolarContainerBase(player.inventory, (SolarFurnaceTileEntity) te));
        }
        else if (te instanceof SolarGrinderTileEntity) {
            SolarGrinderTileEntity containerTileEntity = (SolarGrinderTileEntity) te;
            return new SolarGrinderGui(containerTileEntity, new SolarContainerBase(player.inventory, containerTileEntity));
        }
        else if (te instanceof SolarHarvesterTileEntity) {
            SolarHarvesterTileEntity containerTileEntity = (SolarHarvesterTileEntity) te;
            return new SolarHarvesterGui(containerTileEntity, new SolarContainerBase(player.inventory, containerTileEntity));
        }
        return null;
    }
}
