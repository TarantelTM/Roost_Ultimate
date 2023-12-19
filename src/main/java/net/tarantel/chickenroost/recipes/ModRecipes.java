package net.tarantel.chickenroost.recipes;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.tarantel.chickenroost.ChickenRoostMod;

import java.util.function.Supplier;

public class ModRecipes {
    public static void register(IEventBus bus){
        RECIPE_SERIALIZERS.register(bus);
        RECIPE_TYPES.register(bus);
    }
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, ChickenRoostMod.MODID);
    public static final Supplier<RecipeType<Soul_Breeder_Recipe>> SOUL_BREEDING_TYPE =
            RECIPE_TYPES.register("soul_breeding", () -> Soul_Breeder_Recipe.Type.INSTANCE);
    public static final Supplier<RecipeType<Breeder_Recipe>> BASIC_BREEDING_TYPE =
            RECIPE_TYPES.register("basic_breeding", () -> Breeder_Recipe.Type.INSTANCE);
    public static final Supplier<RecipeType<Soul_Extractor_Recipe>> SOUL_EXTRACTION_TYPE =
            RECIPE_TYPES.register("soul_extraction", () -> Soul_Extractor_Recipe.Type.INSTANCE);
    public static final Supplier<RecipeType<Roost_Recipe>> ROOST_TYPE =
            RECIPE_TYPES.register("roost_output", () -> Roost_Recipe.Type.INSTANCE);


  //  public static final RegistryObject<RecipeType<OwnShapedRecipe>> SHAPED_RECIPE_TYPE =
    //        RECIPE_TYPES.register("roost_output", () -> OwnRecipeType.CRAFTING);

    public static final Supplier<RecipeType<Trainer_Recipe>> TRAINER_TYPE =
            RECIPE_TYPES.register("trainer_output", () -> Trainer_Recipe.Type.INSTANCE);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, ChickenRoostMod.MODID);
    public static final Supplier<RecipeSerializer<Soul_Breeder_Recipe>> SOUL_BREEDING_SERIALIZER =
            RECIPE_SERIALIZERS.register("soul_breeding", () -> Soul_Breeder_Recipe.Serializer.INSTANCE);
    public static final Supplier<RecipeSerializer<Breeder_Recipe>> BASIC_BREEDING_SERIALIZER =
            RECIPE_SERIALIZERS.register("basic_breeding", () -> Breeder_Recipe.Serializer.INSTANCE);
    public static final Supplier<RecipeSerializer<Soul_Extractor_Recipe>> SOUL_EXTRACTION_SERIALIZER =
            RECIPE_SERIALIZERS.register("soul_extraction", () -> Soul_Extractor_Recipe.Serializer.INSTANCE);
    public static final Supplier<RecipeSerializer<Roost_Recipe>> ROOST_SERIALIZER =
            RECIPE_SERIALIZERS.register("roost_output", () -> Roost_Recipe.Serializer.INSTANCE);

    public static final Supplier<RecipeSerializer<Trainer_Recipe>> TRAINER_SERIALIZER =
            RECIPE_SERIALIZERS.register("trainer_output", () -> Trainer_Recipe.Serializer.INSTANCE);

   // public static final RegistryObject<RecipeSerializer<OwnShapedRecipe>> SHAPED_RECIPE_SERIALIZER =
    //        RECIPE_SERIALIZERS.register("trainer_output", () -> new OwnShapedRecipe.Serializer());
}