package net.sperly.simplelife.compat;

import net.sperly.simplelife.compat.top.TOPCompatibility;
//import mcjty.modtut.compat.waila.WailaCompatibility;
import net.minecraftforge.fml.common.Loader;

public class MainCompatHandler {

    public static void registerWaila() {
//        if (Loader.isModLoaded("Waila")) {
//            WailaCompatibility.register();
//        }
    }

    public static void registerTOP() {
        if (Loader.isModLoaded("theoneprobe")) {
            TOPCompatibility.register();
        }
    }

}
