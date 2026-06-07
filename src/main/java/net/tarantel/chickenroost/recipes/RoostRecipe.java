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

public record RoostRecipe(ItemStack output, Ingredient ingredient0, Ingredient ingredient1, int time) implements Recipe<RecipeInput> {
   @NotNull
   public ItemStack assemble(@NotNull RecipeInput container, @NotNull Provider registries) {
      ItemStack itemStack = this.output.getItem().getDefaultInstance();
      itemStack.applyComponents(this.output.getComponentsPatch());
      return itemStack;
   }

   public ResourceLocation getId() {
      return ChickenRoostMod.ownresource("roost_output");
   }

   @NotNull
   public ItemStack getResultItem(Provider registries) {
      return this.output;
   }

   public ItemStack getResultEmi() {
      return this.output.copy();
   }

   public boolean matches(@NotNull RecipeInput pContainer, Level pLevel) {
      if (pLevel.isClientSide()) {
         return false;
      } else {
         return !ChickenRoostMod.CONFIG.RoostSeeds
            ? this.ingredient1.test(pContainer.getItem(1))
            : this.ingredient0.test(pContainer.getItem(0)) && this.ingredient1.test(pContainer.getItem(1));
      }
   }

   public boolean isSpecial() {
      return true;
   }

   @NotNull
   public NonNullList<Ingredient> getIngredients() {
      NonNullList<Ingredient> ingredients = NonNullList.createWithCapacity(2);
      ingredients.add(0, this.ingredient0);
      ingredients.add(1, this.ingredient1);
      return ingredients;
   }

   public boolean canCraftInDimensions(int pWidth, int pHeight) {
      return true;
   }

   @NotNull
   public String getGroup() {
      return "roost_output";
   }

   @NotNull
   public RecipeSerializer<?> getSerializer() {
      return RoostRecipe.Serializer.INSTANCE;
   }

   @NotNull
   public RecipeType<?> getType() {
      return RoostRecipe.Type.INSTANCE;
   }

   public static final class Serializer implements RecipeSerializer<RoostRecipe> {
      public static final RoostRecipe.Serializer INSTANCE = new RoostRecipe.Serializer();
      public static final ResourceLocation ID = ChickenRoostMod.ownresource("roost_output");
      private final MapCodec<RoostRecipe> CODEC = RecordCodecBuilder.mapCodec(
         instance -> instance.group(
               CodecFix.ITEM_STACK_CODEC.fieldOf("output").forGetter(recipe -> recipe.output),
               Ingredient.CODEC_NONEMPTY.fieldOf("food").forGetter(recipe -> recipe.ingredient0),
               Ingredient.CODEC_NONEMPTY.fieldOf("chicken").forGetter(recipe -> recipe.ingredient1),
               Codec.INT.fieldOf("time").orElse(20).forGetter(recipe -> recipe.time)
            )
            .apply(instance, RoostRecipe::new)
      );
      private final StreamCodec<RegistryFriendlyByteBuf, RoostRecipe> STREAM_CODEC = StreamCodec.of(RoostRecipe.Serializer::write, RoostRecipe.Serializer::read);

      private Serializer() {
      }

      @NotNull
      public MapCodec<RoostRecipe> codec() {
         return this.CODEC;
      }

      @NotNull
      public StreamCodec<RegistryFriendlyByteBuf, RoostRecipe> streamCodec() {
         return this.STREAM_CODEC;
      }

      private static RoostRecipe read(RegistryFriendlyByteBuf buffer) {
         Ingredient input0 = (Ingredient)Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
         Ingredient input1 = (Ingredient)Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
         ItemStack output = (ItemStack)ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);
         int time = buffer.readVarInt();
         return new RoostRecipe(output, input0, input1, time);
      }

      private static void write(RegistryFriendlyByteBuf buffer, RoostRecipe recipe) {
         Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient0);
         Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient1);
         ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.output);
         buffer.writeVarInt(recipe.time);
      }
   }

   public static final class Type implements RecipeType<RoostRecipe> {
      public static final RoostRecipe.Type INSTANCE = new RoostRecipe.Type();
      public static final ResourceLocation ID = ChickenRoostMod.ownresource("roost_output");

      private Type() {
      }
   }
}
