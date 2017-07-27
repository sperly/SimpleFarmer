package net.sperly.simplelife.helpers;

import com.google.common.collect.Maps;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLLog;

import java.util.Iterator;
import java.util.Map;

public class GrinderRecipes {
    private static final GrinderRecipes GRINDING_RECIPES = new GrinderRecipes();
    private static final Map<ItemStack, ItemStack> grindingList = Maps.newHashMap();

    public GrinderRecipes() {

    }

    public static GrinderRecipes instance() {
        return GRINDING_RECIPES;
    }

    public ItemStack getGrindingResult(ItemStack itemStack) {
        Iterator iter = this.grindingList.entrySet().iterator();
        Map.Entry post;

        do {
            if(!iter.hasNext()) {
                return ItemStack.EMPTY;
            }
            post = (Map.Entry)iter.next();
        }while(!this.compareItemStacks(itemStack, (ItemStack)post.getKey()));

        return (ItemStack)post.getValue();
    }

    public void addGrindingRecipe(ItemStack inStack, ItemStack resultStack) {
        if(this.getGrindingResult(inStack) != ItemStack.EMPTY) {
            FMLLog.log.info("Ignored smelting recipe with conflicting input: {} = {}", inStack, resultStack);
        }
        else {
            this.grindingList.put(inStack, resultStack);
        }
    }

    private boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
        return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
    }
}
