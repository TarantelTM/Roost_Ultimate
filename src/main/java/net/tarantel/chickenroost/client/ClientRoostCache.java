package net.tarantel.chickenroost.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.tarantel.chickenroost.recipes.Roost_Recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientRoostCache {

    private static final Map<Item, List<Roost_Recipe>> CACHE = new HashMap<>();

    public static List<Roost_Recipe> getRecipes(ItemStack chickenStack) {
        Item item = chickenStack.getItem();

        if (CACHE.containsKey(item)) {
            return CACHE.get(item);
        }

        List<Roost_Recipe> result = new ArrayList<>();

        if (Minecraft.getInstance().level != null) {
            Minecraft.getInstance().level.getRecipeManager()
                    .getAllRecipesFor(Roost_Recipe.Type.INSTANCE)
                    .forEach(recipe -> {
                        // Chicken ist ingredient1
                        if (recipe.ingredient1.test(chickenStack)) {
                            result.add(recipe);
                            System.out.println("Stack is Chicken: " + recipe.ingredient1);
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
