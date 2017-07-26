package net.sperly.simplelife.input;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class KeyBindings {

    public static KeyBinding tutorialKey;

    public static void init() {
        //tutorialKey = new KeyBinding("key.tutorial", Keyboard.KEY_T, "key.categories.simplelife");
        //ClientRegistry.registerKeyBinding(tutorialKey);
    }
}
