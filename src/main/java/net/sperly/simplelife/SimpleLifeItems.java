package net.sperly.simplelife;

import net.sperly.simplelife.items.ManualItem;
import net.sperly.simplelife.items.*;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SimpleLifeItems {

    @GameRegistry.ObjectHolder("simplelife:manualitem")
    public static ManualItem manualItem;

    @GameRegistry.ObjectHolder("simplelife:solarcellitem")
    public static SolarCellItem solarCellItem;

    @GameRegistry.ObjectHolder("simplelife:solarcellupgrade1item")
    public static SolarCellUpgrade1Item solarCellUpgrade1Item;

    @GameRegistry.ObjectHolder("simplelife:solarcellupgrade2item")
    public static SolarCellUpgrade2Item solarCellUpgrade2Item;

    public static void initModels() {
        manualItem.initModel();
        solarCellItem.initModel();
        solarCellUpgrade1Item.initModel();
        solarCellUpgrade2Item.initModel();
    }

    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().register(new ManualItem());
        event.getRegistry().register(new SolarCellItem());
        event.getRegistry().register(new SolarCellUpgrade1Item());
        event.getRegistry().register(new SolarCellUpgrade2Item());
    }
}
