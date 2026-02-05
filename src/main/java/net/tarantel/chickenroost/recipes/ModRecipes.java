package net.tarantel.chickenroost.recipes;

import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.tarantel.chickenroost.ChickenRoostMod;

import java.util.function.Supplier;

public class ModRecipes {
    public static void register(IEventBus bus){
        RECIPE_SERIALIZERS.register(bus);
        RECIPE_TYPES.register(bus);
    }
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, ChickenRoostMod.MODID);


    public static final RegistryObject<RecipeType<Breeder_Recipe>> BASIC_BREEDING_TYPE =
            RECIPE_TYPES.register("basic_breeding", () -> Breeder_Recipe.Type.INSTANCE);
    public static final Supplier<RecipeType<ThrowEggRecipe>> THROW_EGG_TYPE =
            RECIPE_TYPES.register("throwegg", () -> ThrowEggRecipe.Type.INSTANCE);
    public static final RegistryObject<RecipeType<Soul_Extractor_Recipe>> SOUL_EXTRACTION_TYPE =
            RECIPE_TYPES.register("soul_extraction", () -> Soul_Extractor_Recipe.Type.INSTANCE);
    public static final RegistryObject<RecipeType<Roost_Recipe>> ROOST_TYPE =
            RECIPE_TYPES.register("roost_output", () -> Roost_Recipe.Type.INSTANCE);

    public static final RegistryObject<RecipeType<Trainer_Recipe>> TRAINER_TYPE =
            RECIPE_TYPES.register("trainer_output", () -> Trainer_Recipe.Type.INSTANCE);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ChickenRoostMod.MODID);

    public static final RegistryObject<RecipeSerializer<Breeder_Recipe>> BASIC_BREEDING_SERIALIZER =
            RECIPE_SERIALIZERS.register("basic_breeding", () -> Breeder_Recipe.Serializer.INSTANCE);
    public static final Supplier<RecipeSerializer<ThrowEggRecipe>> THROW_EGG_SERIALIZER =
            RECIPE_SERIALIZERS.register("throwegg", () -> ThrowEggRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<Soul_Extractor_Recipe>> SOUL_EXTRACTION_SERIALIZER =
            RECIPE_SERIALIZERS.register("soul_extraction", () -> Soul_Extractor_Recipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<Roost_Recipe>> ROOST_SERIALIZER =
            RECIPE_SERIALIZERS.register("roost_output", () -> Roost_Recipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<Trainer_Recipe>> TRAINER_SERIALIZER =
            RECIPE_SERIALIZERS.register("trainer_output", () -> Trainer_Recipe.Serializer.INSTANCE);
}