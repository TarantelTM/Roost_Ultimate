package net.tarantel.chickenroost.recipemanager;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.tarantel.chickenroost.ChickenRoostMod;

public class ModRecipes {

    public static void registerRecipes() {
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(ChickenRoostMod.MODID, SoulBreedingRecipe.Serializer.ID),
                SoulBreedingRecipe.Serializer.INSTANCE);

        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(ChickenRoostMod.MODID, BasicBreedingRecipe.Serializer.ID),
                BasicBreedingRecipe.Serializer.INSTANCE);

        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(ChickenRoostMod.MODID, SoulExtractionRecipe.Serializer.ID),
                SoulExtractionRecipe.Serializer.INSTANCE);

        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(ChickenRoostMod.MODID, Roost_Recipe.Serializer.ID),
                Roost_Recipe.Serializer.INSTANCE);


        Registry.register(Registries.RECIPE_TYPE, new Identifier(ChickenRoostMod.MODID, SoulBreedingRecipe.Type.ID),
                SoulBreedingRecipe.Type.INSTANCE);

        Registry.register(Registries.RECIPE_TYPE, new Identifier(ChickenRoostMod.MODID, BasicBreedingRecipe.Type.ID),
                BasicBreedingRecipe.Type.INSTANCE);

        Registry.register(Registries.RECIPE_TYPE, new Identifier(ChickenRoostMod.MODID, SoulExtractionRecipe.Type.ID),
                SoulExtractionRecipe.Type.INSTANCE);

        Registry.register(Registries.RECIPE_TYPE, new Identifier(ChickenRoostMod.MODID, Roost_Recipe.Type.ID),
                Roost_Recipe.Type.INSTANCE);

    }


}