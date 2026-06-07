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
import net.tarantel.chickenroost.util.Config;
import org.jetbrains.annotations.NotNull;

public record BreederRecipe(ItemStack output, Ingredient ingredient0, Ingredient ingredient1, Ingredient ingredient2, int time) implements Recipe<RecipeInput> {
   @NotNull
   public ItemStack assemble(@NotNull RecipeInput container, Provider registries) {
      return this.output;
   }

   public ResourceLocation getId() {
      return ChickenRoostMod.ownresource("basic_breeding");
   }

   @NotNull
   public ItemStack getResultItem(Provider registries) {
      return this.output.copy();
   }

   public ItemStack getResultEmi() {
      return this.output.copy();
   }

   public boolean matches(@NotNull RecipeInput pContainer, Level pLevel) {
      if (pLevel.isClientSide()) {
         return false;
      } else if (ChickenRoostMod.CONFIG.BreederSeeds) {
         return Config.enablebothways.get()
            ? this.ingredient0.test(pContainer.getItem(1)) && this.ingredient1.test(pContainer.getItem(0)) && this.ingredient2.test(pContainer.getItem(2))
               || this.ingredient0.test(pContainer.getItem(1)) && this.ingredient1.test(pContainer.getItem(2)) && this.ingredient2.test(pContainer.getItem(0))
            : this.ingredient0.test(pContainer.getItem(1)) && this.ingredient1.test(pContainer.getItem(0)) && this.ingredient2.test(pContainer.getItem(2));
      } else {
         return Config.enablebothways.get()
            ? this.ingredient1.test(pContainer.getItem(0)) && this.ingredient2.test(pContainer.getItem(2))
               || this.ingredient1.test(pContainer.getItem(2)) && this.ingredient2.test(pContainer.getItem(0))
            : this.ingredient1.test(pContainer.getItem(0)) && this.ingredient2.test(pContainer.getItem(2));
      }
   }

   public boolean isSpecial() {
      return true;
   }

   @NotNull
   public NonNullList<Ingredient> getIngredients() {
      NonNullList<Ingredient> ingredients = NonNullList.createWithCapacity(3);
      ingredients.add(0, this.ingredient0);
      ingredients.add(1, this.ingredient1);
      ingredients.add(2, this.ingredient2);
      return ingredients;
   }

   public boolean canCraftInDimensions(int pWidth, int pHeight) {
      return true;
   }

   @NotNull
   public String getGroup() {
      return "basic_breeding";
   }

   @NotNull
   public RecipeSerializer<?> getSerializer() {
      return BreederRecipe.Serializer.INSTANCE;
   }

   @NotNull
   public RecipeType<?> getType() {
      return BreederRecipe.Type.INSTANCE;
   }

   public static final class Serializer implements RecipeSerializer<BreederRecipe> {
      public static final BreederRecipe.Serializer INSTANCE = new BreederRecipe.Serializer();
      public static final ResourceLocation ID = ChickenRoostMod.ownresource("basic_breeding");
      private final MapCodec<BreederRecipe> CODEC = RecordCodecBuilder.mapCodec(
         instance -> instance.group(
               CodecFix.ITEM_STACK_CODEC.fieldOf("output").forGetter(recipe -> recipe.output),
               Ingredient.CODEC_NONEMPTY.fieldOf("food").forGetter(recipe -> recipe.ingredient0),
               Ingredient.CODEC_NONEMPTY.fieldOf("left-chicken").forGetter(recipe -> recipe.ingredient1),
               Ingredient.CODEC_NONEMPTY.fieldOf("right-chicken").forGetter(recipe -> recipe.ingredient2),
               Codec.INT.fieldOf("time").orElse(20).forGetter(recipe -> recipe.time)
            )
            .apply(instance, BreederRecipe::new)
      );
      private final StreamCodec<RegistryFriendlyByteBuf, BreederRecipe> STREAM_CODEC = StreamCodec.of(
         BreederRecipe.Serializer::write, BreederRecipe.Serializer::read
      );

      private Serializer() {
      }

      @NotNull
      public MapCodec<BreederRecipe> codec() {
         return this.CODEC;
      }

      @NotNull
      public StreamCodec<RegistryFriendlyByteBuf, BreederRecipe> streamCodec() {
         return this.STREAM_CODEC;
      }

      private static BreederRecipe read(RegistryFriendlyByteBuf buffer) {
         Ingredient input0 = (Ingredient)Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
         Ingredient input1 = (Ingredient)Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
         Ingredient input2 = (Ingredient)Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
         ItemStack output = (ItemStack)ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);
         int time = buffer.readVarInt();
         return new BreederRecipe(output, input0, input1, input2, time);
      }

      private static void write(RegistryFriendlyByteBuf buffer, BreederRecipe recipe) {
         Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient0);
         Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient1);
         Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient2);
         ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.output);
         buffer.writeVarInt(recipe.time);
      }
   }

   public static class Type implements RecipeType<BreederRecipe> {
      public static final BreederRecipe.Type INSTANCE = new BreederRecipe.Type();
      public static final String ID = "basic_breeding";

      private Type() {
      }
   }
}
