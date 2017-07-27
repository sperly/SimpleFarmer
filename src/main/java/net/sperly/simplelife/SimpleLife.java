package net.sperly.simplelife;

import net.sperly.simplelife.helpers.GrinderRecipes;
import net.sperly.simplelife.proxy.CommonProxy;
import net.sperly.simplelife.commands.TeleportCommand;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = SimpleLife.MODID, name = SimpleLife.MODNAME, version = SimpleLife.MODVERSION, dependencies = "required-after:forge@[13.19.0.2129,)", useMetadata = true)
public class SimpleLife
{
    public static final String MODID = "simplelife";
    public static final String MODVERSION = "0.1.0";
    public static final String MODNAME = "Simple Life";

    @SidedProxy(clientSide = "net.sperly.simplelife.proxy.ClientProxy", serverSide = "net.sperly.simplelife.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static SimpleLife instance;
    public static GrinderRecipes gRecipes = new GrinderRecipes();

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e)
    {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e)
    {
        proxy.postInit(e);
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new TeleportCommand());
    }

    public static CreativeTabs tabSimpleLife = new CreativeTabs("SimpleLife") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(SimpleLifeItems.manualItem);
        }
    };
}

