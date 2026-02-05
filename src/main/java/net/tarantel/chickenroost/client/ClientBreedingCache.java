//package net.tarantel.chickenroost.client;
//
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.multiplayer.ClientPacketListener;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.crafting.RecipeHolder;
//import net.minecraft.world.item.crafting.RecipeManager;
//import net.tarantel.chickenroost.recipes.BreederRecipe;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class ClientBreedingCache {
//
//    private static final Map<Item, List<BreederRecipe>> CACHE = new HashMap<>();
//
//    public static List<BreederRecipe> getRecipes(ItemStack outputStack) {
//        Item item = outputStack.getItem();
//
//        if (CACHE.containsKey(item)) {
//            return CACHE.get(item);
//        }
//
//        List<BreederRecipe> result = new ArrayList<>();
//
//        Minecraft mc = Minecraft.getInstance();
//        ClientPacketListener connection = mc.getConnection();
//
//        if (connection != null) {
//            RecipeManager recipeManager = Minecraft.getInstance().level.getServer().getRecipeManager();
//
//            for (RecipeHolder<?> holder : recipeManager.getRecipes()) {
//                if (!(holder.value() instanceof BreederRecipe recipe)) continue;
//
//                if (ItemStack.isSameItemSameComponents(recipe.output(), outputStack)) {
//                    result.add(recipe);
//                }
//            }
//        }
//
//        CACHE.put(item, result);
//        return result;
//    }
//
//
//    public static void clear() {
//        CACHE.clear();
//    }
//}
//