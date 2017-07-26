package net.sperly.simplelife;

import net.sperly.simplelife.blocks.solarfurnace.*;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SimpleLifeBlocks
{
    @GameRegistry.ObjectHolder("simplelife:solarfurnaceblock")
    public static SolarFurnaceBlock solarFurnaceBlock;

    @SideOnly(Side.CLIENT)
    public static void initModels()
    {
        solarFurnaceBlock.initModel();
    }

    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        event.getRegistry().register(new SolarFurnaceBlock());
    }

    public static void registerTileEntities()
    {
        GameRegistry.registerTileEntity(SolarFurnaceTileEntity.class, SimpleLife.MODID + "_solarfurnaceblock");

    }

    public static void registerBlockItems(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().register(new ItemBlock(SimpleLifeBlocks.solarFurnaceBlock).setRegistryName(SimpleLifeBlocks.solarFurnaceBlock.getRegistryName()));
    }
}
