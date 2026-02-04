package net.tarantel.chickenroost.recipes;

import com.mojang.serialization.Codec;
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

public record SoulExtractorRecipe(ItemStack output, Ingredient ingredient0, int time) implements Recipe<RecipeInput> {
   @NotNull
   public ItemStack assemble(@NotNull RecipeInput container, @NotNull Provider registries) {
      return this.output;
   }

   public ResourceLocation getId() {
      return ChickenRoostMod.ownresource("soul_extraction");
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
      return "soul_extraction";
   }

   @NotNull
   public RecipeSerializer<?> getSerializer() {
      return SoulExtractorRecipe.Serializer.INSTANCE;
   }

   @NotNull
   public RecipeType<?> getType() {
      return SoulExtractorRecipe.Type.INSTANCE;
   }

   public static final class Serializer implements RecipeSerializer<SoulExtractorRecipe> {
      public static final SoulExtractorRecipe.Serializer INSTANCE = new SoulExtractorRecipe.Serializer();
      public static final ResourceLocation ID = ChickenRoostMod.ownresource("soul_extraction");
      private final MapCodec<SoulExtractorRecipe> CODEC = RecordCodecBuilder.mapCodec(
         instance -> instance.group(
               CodecFix.ITEM_STACK_CODEC.fieldOf("output").forGetter(recipe -> recipe.output),
               Ingredient.CODEC_NONEMPTY.fieldOf("chicken").forGetter(recipe -> recipe.ingredient0),
               Codec.INT.fieldOf("time").orElse(20).forGetter(recipe -> recipe.time)
            )
            .apply(instance, SoulExtractorRecipe::new)
      );
      private final StreamCodec<RegistryFriendlyByteBuf, SoulExtractorRecipe> STREAM_CODEC = StreamCodec.of(
         SoulExtractorRecipe.Serializer::write, SoulExtractorRecipe.Serializer::read
      );

      private Serializer() {
      }

      @NotNull
      public MapCodec<SoulExtractorRecipe> codec() {
         return this.CODEC;
      }

      @NotNull
      public StreamCodec<RegistryFriendlyByteBuf, SoulExtractorRecipe> streamCodec() {
         return this.STREAM_CODEC;
      }

      private static SoulExtractorRecipe read(RegistryFriendlyByteBuf buffer) {
         Ingredient input0 = (Ingredient)Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
         ItemStack output = (ItemStack)ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);
         int time = buffer.readVarInt();
         return new SoulExtractorRecipe(output, input0, time);
      }

      private static void write(RegistryFriendlyByteBuf buffer, SoulExtractorRecipe recipe) {
         Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient0);
         ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.output);
         buffer.writeVarInt(recipe.time);
      }
   }

   public static final class Type implements RecipeType<SoulExtractorRecipe> {
      public static final SoulExtractorRecipe.Type INSTANCE = new SoulExtractorRecipe.Type();
      public static final String ID = "soul_extraction";

      private Type() {
      }
   }
}
