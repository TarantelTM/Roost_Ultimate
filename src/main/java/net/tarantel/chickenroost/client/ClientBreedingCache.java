package net.tarantel.chickenroost.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.tarantel.chickenroost.recipes.BreederRecipe;

import java.util.*;

public class ClientBreedingCache {

    private static final Map<Item, List<BreederRecipe>> CACHE = new HashMap<>();

    public static List<BreederRecipe> getRecipes(ItemStack outputStack) {
        Item item = outputStack.getItem();

        if (CACHE.containsKey(item)) {
            return CACHE.get(item);
        }

        List<BreederRecipe> result = new ArrayList<>();

        if (Minecraft.getInstance().level != null) {
            Minecraft.getInstance().level.getRecipeManager()
                    .getAllRecipesFor(BreederRecipe.Type.INSTANCE)
                    .forEach(recipe -> {
                        if (ItemStack.isSameItemSameComponents(
                                recipe.value().output(), outputStack)) {
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
