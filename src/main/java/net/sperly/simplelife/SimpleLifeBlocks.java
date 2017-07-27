package net.sperly.simplelife;


import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.sperly.simplelife.blocks.solarfurnace.*;
import net.sperly.simplelife.blocks.solargrinder.*;
import net.sperly.simplelife.blocks.solarharvester.*;

public class SimpleLifeBlocks
{
    @GameRegistry.ObjectHolder("simplelife:solarfurnaceblock")
    public static SolarFurnaceBlock solarFurnaceBlock;

    @GameRegistry.ObjectHolder("simplelife:solargrinderblock")
    public static SolarGrinderBlock solarGrinderBlock;

    @GameRegistry.ObjectHolder("simplelife:solarharvesterblock")
    public static SolarHarvesterBlock solarHarvesterBlock;

    @SideOnly(Side.CLIENT)
    public static void initModels()
    {
        solarFurnaceBlock.initModel();
        solarGrinderBlock.initModel();
        solarHarvesterBlock.initModel();
    }

    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        event.getRegistry().register(new SolarFurnaceBlock());
        event.getRegistry().register(new SolarGrinderBlock());
        event.getRegistry().register(new SolarHarvesterBlock());
    }

    public static void registerTileEntities()
    {
        GameRegistry.registerTileEntity(SolarFurnaceTileEntity.class, SimpleLife.MODID + "_solarfurnaceblock");
        GameRegistry.registerTileEntity(SolarGrinderTileEntity.class, SimpleLife.MODID + "_solargrinderblock");
        GameRegistry.registerTileEntity(SolarHarvesterTileEntity.class, SimpleLife.MODID + "_solarharvesterblock");
    }

    public static void registerBlockItems(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().register(new ItemBlock(SimpleLifeBlocks.solarFurnaceBlock).setRegistryName(SimpleLifeBlocks.solarFurnaceBlock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(SimpleLifeBlocks.solarGrinderBlock).setRegistryName(SimpleLifeBlocks.solarGrinderBlock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(SimpleLifeBlocks.solarHarvesterBlock).setRegistryName(SimpleLifeBlocks.solarHarvesterBlock.getRegistryName()));
    }
}
