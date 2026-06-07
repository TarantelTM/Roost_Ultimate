package net.tarantel.chickenroost.recipes;

import java.util.function.Supplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
   public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, "chicken_roost");
   public static final Supplier<RecipeType<ThrowEggRecipe>> THROW_EGG_TYPE = RECIPE_TYPES.register("throwegg", () -> ThrowEggRecipe.Type.INSTANCE);
   public static final Supplier<RecipeType<BreederRecipe>> BASIC_BREEDING_TYPE = RECIPE_TYPES.register("basic_breeding", () -> BreederRecipe.Type.INSTANCE);
   public static final Supplier<RecipeType<SoulExtractorRecipe>> SOUL_EXTRACTION_TYPE = RECIPE_TYPES.register(
      "soul_extraction", () -> SoulExtractorRecipe.Type.INSTANCE
   );
   public static final Supplier<RecipeType<RoostRecipe>> ROOST_TYPE = RECIPE_TYPES.register("roost_output", () -> RoostRecipe.Type.INSTANCE);
   public static final Supplier<RecipeType<TrainerRecipe>> TRAINER_TYPE = RECIPE_TYPES.register("trainer_output", () -> TrainerRecipe.Type.INSTANCE);
   public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, "chicken_roost");
   public static final Supplier<RecipeSerializer<ThrowEggRecipe>> THROW_EGG_SERIALIZER = RECIPE_SERIALIZERS.register(
      "throwegg", () -> ThrowEggRecipe.Serializer.INSTANCE
   );
   public static final Supplier<RecipeSerializer<BreederRecipe>> BASIC_BREEDING_SERIALIZER = RECIPE_SERIALIZERS.register(
      "basic_breeding", () -> BreederRecipe.Serializer.INSTANCE
   );
   public static final Supplier<RecipeSerializer<SoulExtractorRecipe>> SOUL_EXTRACTION_SERIALIZER = RECIPE_SERIALIZERS.register(
      "soul_extraction", () -> SoulExtractorRecipe.Serializer.INSTANCE
   );
   public static final Supplier<RecipeSerializer<RoostRecipe>> ROOST_SERIALIZER = RECIPE_SERIALIZERS.register(
      "roost_output", () -> RoostRecipe.Serializer.INSTANCE
   );
   public static final Supplier<RecipeSerializer<TrainerRecipe>> TRAINER_SERIALIZER = RECIPE_SERIALIZERS.register(
      "trainer_output", () -> TrainerRecipe.Serializer.INSTANCE
   );

   public static void register(IEventBus bus) {
      RECIPE_SERIALIZERS.register(bus);
      RECIPE_TYPES.register(bus);
   }
}
