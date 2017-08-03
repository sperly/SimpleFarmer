package net.sperly.simplelife.proxy;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Blocks;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.sperly.simplelife.*;
//import net.sperly.simplelife.items.FirstItem;
import net.sperly.simplelife.helpers.*;
import net.sperly.simplelife.compat.MainCompatHandler;
import net.sperly.simplelife.network.PacketHandler;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.io.File;

@Mod.EventBusSubscriber
public class CommonProxy {

    // Config instance
    public static Configuration config;

    public void preInit(FMLPreInitializationEvent e) {
        File directory = e.getModConfigurationDirectory();
        config = new Configuration(new File(directory.getPath(), "simplelife.cfg"));
        Config.readConfig();

        // Initialize our packet handler. Make sure the name is
        // 20 characters or less!
        PacketHandler.registerMessages("simplelife");

        MainCompatHandler.registerWaila();
        MainCompatHandler.registerTOP();
    }

    public void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(SimpleLife.instance, new GuiProxy());
    }

    public void postInit(FMLPostInitializationEvent e) {
        if (config.hasChanged()) {
            config.save();
        }
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        // Register Blocks
        SimpleLifeBlocks.registerBlocks(event);

        // Register TileEntities
        SimpleLifeBlocks.registerTileEntities();
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        //Register Items
        SimpleLifeItems.registerItems(event);

        //Register Blocks items
        SimpleLifeBlocks.registerBlockItems(event);

        //Register Grinding Recipes

        GrinderRecipes.instance().addGrindingRecipe(new ItemStack(Blocks.IRON_ORE), new ItemStack(Item.getByNameOrId("simplelife:irondustitem"), 2));
        GrinderRecipes.instance().addGrindingRecipe(new ItemStack(Blocks.COAL_ORE), new ItemStack(Items.COAL, 2));
        GrinderRecipes.instance().addGrindingRecipe(new ItemStack(Blocks.LAPIS_ORE), new ItemStack(Items.DYE, 2, 4));

        //Register smelting recipes
        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(Item.getByNameOrId("simplelife:irondustitem")), new ItemStack(Items.IRON_INGOT), 0.1F);
    }

}
