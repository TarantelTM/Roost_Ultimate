//package net.tarantel.chickenroost.util;
//
//import net.minecraft.core.Registry;
//import net.minecraft.core.registries.BuiltInRegistries;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.item.crafting.*;
//
//public interface OwnRecipeType<T extends Recipe<?>> extends RecipeType<Recipe> {
//    RecipeType<Recipe<?>> CRAFTING = register("crafting");
//
//    static <T extends Recipe<?>> net.minecraft.world.item.crafting.RecipeType<T> register(final String p_44120_) {
//        return Registry.register(BuiltInRegistries.RECIPE_TYPE, new ResourceLocation(p_44120_), new net.minecraft.world.item.crafting.RecipeType<T>() {
//            @Override
//            public String toString() {
//                return p_44120_;
//            }
//        });
//    }
//
//    public static <T extends Recipe<?>> net.minecraft.world.item.crafting.RecipeType<T> simple(final ResourceLocation name) {
//        final String toString = name.toString();
//        return new net.minecraft.world.item.crafting.RecipeType<T>() {
//            @Override
//            public String toString() {
//                return toString;
//            }
//        };
//    }
//}
//