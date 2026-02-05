package net.tarantel.chickenroost.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.tarantel.chickenroost.recipes.Breeder_Recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientBreedingCache {

    private static final Map<Item, List<Breeder_Recipe>> CACHE = new HashMap<>();

    public static List<Breeder_Recipe> getRecipes(ItemStack outputStack) {
        Item item = outputStack.getItem();

        if (CACHE.containsKey(item)) {
            return CACHE.get(item);
        }

        List<Breeder_Recipe> result = new ArrayList<>();

        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level != null) {
            minecraft.level.getRecipeManager()
                    .getAllRecipesFor(Breeder_Recipe.Type.INSTANCE)
                    .forEach(recipe -> {
                        ItemStack recipeResult = recipe.getResultItem(
                                minecraft.level.registryAccess()
                        );

                        if (ItemStack.isSameItemSameTags(recipeResult, outputStack)) {
                            result.add(recipe);
                        }
                    });
        }

        CACHE.put(item, result);
        return result;
    }

    /** Call on resource reload */
    public static void clear() {
        CACHE.clear();
    }
}
