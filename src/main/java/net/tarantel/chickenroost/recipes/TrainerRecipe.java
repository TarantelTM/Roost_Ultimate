package net.tarantel.chickenroost.recipes;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.tarantel.chickenroost.ChickenRoostMod;
import org.jetbrains.annotations.NotNull;

public record TrainerRecipe(ItemStack output, Ingredient ingredient0) implements Recipe<RecipeInput> {
   @NotNull
   public ItemStack assemble(@NotNull RecipeInput container, @NotNull Provider registries) {
      return this.output;
   }

   public ResourceLocation getId() {
      return ChickenRoostMod.ownresource("trainer_output");
   }

   @NotNull
   public ItemStack getResultItem(Provider registries) {
      return this.output.copy();
   }

   public ItemStack getResultEmi() {
      return this.output.copy();
   }

   public boolean matches(@NotNull RecipeInput pContainer, Level pLevel) {
      return pLevel.isClientSide() ? false : this.ingredient0.test(pContainer.getItem(0));
   }

   public boolean isSpecial() {
      return true;
   }

   @NotNull
   public NonNullList<Ingredient> getIngredients() {
      NonNullList<Ingredient> ingredients = NonNullList.createWithCapacity(1);
      ingredients.addFirst(this.ingredient0);
      return ingredients;
   }

   public boolean canCraftInDimensions(int pWidth, int pHeight) {
      return true;
   }

   @NotNull
   public String getGroup() {
      return "trainer_output";
   }

   @NotNull
   public RecipeSerializer<?> getSerializer() {
      return TrainerRecipe.Serializer.INSTANCE;
   }

   @NotNull
   public RecipeType<?> getType() {
      return TrainerRecipe.Type.INSTANCE;
   }

   public static final class Serializer implements RecipeSerializer<TrainerRecipe> {
      public static final TrainerRecipe.Serializer INSTANCE = new TrainerRecipe.Serializer();
      public static final ResourceLocation ID = ChickenRoostMod.ownresource("trainer_output");
      private final MapCodec<TrainerRecipe> CODEC = RecordCodecBuilder.mapCodec(
         instance -> instance.group(
               CodecFix.ITEM_STACK_CODEC.fieldOf("output").forGetter(recipe -> recipe.output.copy()),
               Ingredient.CODEC_NONEMPTY.fieldOf("chicken").forGetter(recipe -> recipe.ingredient0)
            )
            .apply(instance, TrainerRecipe::new)
      );
      private final StreamCodec<RegistryFriendlyByteBuf, TrainerRecipe> STREAM_CODEC = StreamCodec.of(
         TrainerRecipe.Serializer::write, TrainerRecipe.Serializer::read
      );

      private Serializer() {
      }

      @NotNull
      public MapCodec<TrainerRecipe> codec() {
         return this.CODEC;
      }

      @NotNull
      public StreamCodec<RegistryFriendlyByteBuf, TrainerRecipe> streamCodec() {
         return this.STREAM_CODEC;
      }

      private static TrainerRecipe read(RegistryFriendlyByteBuf buffer) {
         Ingredient input0 = (Ingredient)Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
         ItemStack output = (ItemStack)ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);
         return new TrainerRecipe(output, input0);
      }

      private static void write(RegistryFriendlyByteBuf buffer, TrainerRecipe recipe) {
         Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient0);
         ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.output);
      }
   }

   public static final class Type implements RecipeType<TrainerRecipe> {
      public static final TrainerRecipe.Type INSTANCE = new TrainerRecipe.Type();
      public static final String ID = "trainer_output";

      private Type() {
      }
   }
}
