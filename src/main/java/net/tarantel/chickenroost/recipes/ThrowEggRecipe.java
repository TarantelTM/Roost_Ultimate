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

public record ThrowEggRecipe(ItemStack output, Ingredient ingredient0) implements Recipe<RecipeInput> {
   @NotNull
   public ItemStack assemble(@NotNull RecipeInput container, @NotNull Provider registries) {
      return this.output;
   }

   public ResourceLocation getId() {
      return ChickenRoostMod.ownresource("throwegg");
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
      return "throwegg";
   }

   @NotNull
   public RecipeSerializer<?> getSerializer() {
      return ThrowEggRecipe.Serializer.INSTANCE;
   }

   @NotNull
   public RecipeType<?> getType() {
      return ThrowEggRecipe.Type.INSTANCE;
   }

   public static final class Serializer implements RecipeSerializer<ThrowEggRecipe> {
      public static final ThrowEggRecipe.Serializer INSTANCE = new ThrowEggRecipe.Serializer();
      public static final ResourceLocation ID = ChickenRoostMod.ownresource("throwegg");
      private final MapCodec<ThrowEggRecipe> CODEC = RecordCodecBuilder.mapCodec(
         instance -> instance.group(
               CodecFix.ITEM_STACK_CODEC.fieldOf("output").forGetter(recipe -> recipe.output),
               Ingredient.CODEC_NONEMPTY.fieldOf("egg").forGetter(recipe -> recipe.ingredient0)
            )
            .apply(instance, ThrowEggRecipe::new)
      );
      private final StreamCodec<RegistryFriendlyByteBuf, ThrowEggRecipe> STREAM_CODEC = StreamCodec.of(
         ThrowEggRecipe.Serializer::write, ThrowEggRecipe.Serializer::read
      );

      private Serializer() {
      }

      @NotNull
      public MapCodec<ThrowEggRecipe> codec() {
         return this.CODEC;
      }

      @NotNull
      public StreamCodec<RegistryFriendlyByteBuf, ThrowEggRecipe> streamCodec() {
         return this.STREAM_CODEC;
      }

      private static ThrowEggRecipe read(RegistryFriendlyByteBuf buffer) {
         Ingredient input0 = (Ingredient)Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
         ItemStack output = (ItemStack)ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);
         return new ThrowEggRecipe(output, input0);
      }

      private static void write(RegistryFriendlyByteBuf buffer, ThrowEggRecipe recipe) {
         Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient0);
         ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.output);
      }
   }

   public static final class Type implements RecipeType<ThrowEggRecipe> {
      public static final ThrowEggRecipe.Type INSTANCE = new ThrowEggRecipe.Type();
      public static final String ID = "throwegg";

      private Type() {
      }
   }
}
