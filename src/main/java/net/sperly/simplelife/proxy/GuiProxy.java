package net.sperly.simplelife.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.sperly.simplelife.blocks.base.SolarContainerBase;
import net.sperly.simplelife.blocks.solarfurnace.SolarFurnaceGui;
import net.sperly.simplelife.blocks.solarfurnace.SolarFurnaceTileEntity;

public class GuiProxy implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof SolarFurnaceTileEntity) {
            return new SolarContainerBase(player.inventory, (SolarFurnaceTileEntity) te);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof SolarFurnaceTileEntity) {
            SolarFurnaceTileEntity containerTileEntity = (SolarFurnaceTileEntity) te;
            return new SolarFurnaceGui(containerTileEntity, new SolarContainerBase(player.inventory, containerTileEntity));
        }
        return null;
    }
}
