package net.tarantel.chickenroost.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.tarantel.chickenroost.recipes.RoostRecipe;

import java.util.*;

public class ClientRoostCache {

    private static final Map<Item, List<RoostRecipe>> CACHE = new HashMap<>();

    public static List<RoostRecipe> getRecipes(ItemStack chickenStack) {
        Item item = chickenStack.getItem();

        if (CACHE.containsKey(item)) {
            return CACHE.get(item);
        }

        List<RoostRecipe> result = new ArrayList<>();

        if (Minecraft.getInstance().level != null) {
            Minecraft.getInstance().level.getRecipeManager()
                    .getAllRecipesFor(RoostRecipe.Type.INSTANCE)
                    .forEach(recipe -> {
                        if (recipe.value().ingredient1().test(chickenStack)) {
                            result.add(recipe.value());
                        }
                    });
        }

        CACHE.put(item, result);
        return result;
    }

    public static void clear() {
        CACHE.clear();
    }
}
