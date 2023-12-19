//package net.tarantel.chickenroost.util;
//
//import net.minecraft.world.inventory.CraftingContainer;
//import net.minecraft.world.item.crafting.CraftingBookCategory;
//import net.minecraft.world.item.crafting.Recipe;
//import net.minecraft.world.item.crafting.RecipeType;
//
//public interface OwnCraftingRecipe extends Recipe<CraftingContainer> {
//    @Override
//    default RecipeType<Recipe<?>> getType() {
//        return OwnRecipeType.CRAFTING;
//    }
//
//    CraftingBookCategory category();
//}