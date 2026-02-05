package net.tarantel.chickenroost.client;

//import net.minecraft.client.Minecraft;
//import net.minecraft.client.multiplayer.ClientPacketListener;
//import net.minecraft.client.multiplayer.ClientRecipeContainer;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.crafting.RecipeHolder;
//
//import net.tarantel.chickenroost.recipes.RoostRecipe;
//
//import java.util.*;
//
//public class ClientRoostCache {
//
//    private static final Map<Item, List<RoostRecipe>> CACHE = new HashMap<>();
//
//    public static List<RoostRecipe> getRecipes(ItemStack chickenStack) {
//        Item item = chickenStack.getItem();
//
//        if (CACHE.containsKey(item)) {
//            return CACHE.get(item);
//        }
//
//        List<RoostRecipe> result = new ArrayList<>();
//
//        Minecraft mc = Minecraft.getInstance();
//        ClientPacketListener connection = mc.getConnection();
//
//        if (connection != null && connection.recipes() instanceof ClientRecipeContainer container) {
//
//            for (RecipeHolder<?> holder : container.getAllRecipes()) {
//                if (holder.value() instanceof RoostRecipe recipe) {
//                    if (recipe.ingredient1().test(chickenStack)) {
//                        result.add(recipe);
//                    }
//                }
//            }
//        }
//
//        CACHE.put(item, result);
//        return result;
//    }
//
//    public static void clear() {
//        CACHE.clear();
//    }
//}
//