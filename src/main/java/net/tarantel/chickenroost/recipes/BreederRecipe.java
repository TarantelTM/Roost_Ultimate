package net.tarantel.chickenroost.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.util.Config;
import org.jetbrains.annotations.NotNull;

public record BreederRecipe(ItemStack output, Ingredient ingredient0, Ingredient ingredient1, Ingredient ingredient2,
                            int time) implements Recipe<RecipeInput> {

    @Override
    public @NotNull ItemStack assemble(@NotNull RecipeInput container, HolderLookup.Provider registries) {
        return output;
    }


    public ResourceLocation getId() {
        return ChickenRoostMod.ownresource("basic_breeding");
    }


    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.Provider registries) {
        return output.copy();
    }

    public ItemStack getResultEmi() {
        return output.copy();
    }


    @Override
    public boolean matches(@NotNull RecipeInput pContainer, Level pLevel) {
        if (pLevel.isClientSide()) {
            return false;
        }
        if (Config.enablebothways.get()) {
            return (ingredient0.test(pContainer.getItem(1)) && ingredient1.test(pContainer.getItem(0)) && ingredient2.test(pContainer.getItem(2))) || (ingredient0.test(pContainer.getItem(1)) && ingredient1.test(pContainer.getItem(2)) && ingredient2.test(pContainer.getItem(0)));
        } else {
            return ingredient0.test(pContainer.getItem(1)) && ingredient1.test(pContainer.getItem(0)) && ingredient2.test(pContainer.getItem(2));
        }

    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.createWithCapacity(3);
        ingredients.add(0, ingredient0);
        ingredients.add(1, ingredient1);
        ingredients.add(2, ingredient2);
        return ingredients;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public @NotNull String getGroup() {
        return "basic_breeding";
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return BreederRecipe.Serializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<BreederRecipe> {
        private Type() {
        }

        public static final Type INSTANCE = new Type();
        public static final String ID = "basic_breeding";
    }

    public static final class Serializer implements RecipeSerializer<BreederRecipe> {
        private Serializer() {
        }

        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                ChickenRoostMod.ownresource("basic_breeding");


        private final MapCodec<BreederRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(CodecFix.ITEM_STACK_CODEC.fieldOf("output").forGetter((recipe) -> recipe.output), Ingredient.CODEC_NONEMPTY.fieldOf("food").forGetter((recipe) -> recipe.ingredient0), Ingredient.CODEC_NONEMPTY.fieldOf("left-chicken").forGetter((recipe) -> recipe.ingredient1), Ingredient.CODEC_NONEMPTY.fieldOf("right-chicken").forGetter((recipe) -> recipe.ingredient2), Codec.INT.fieldOf("time").orElse(20).forGetter((recipe) -> recipe.time)).apply(instance, BreederRecipe::new));
        private final StreamCodec<RegistryFriendlyByteBuf, BreederRecipe> STREAM_CODEC = StreamCodec.of(
                Serializer::write, Serializer::read);

        @Override
        public @NotNull MapCodec<BreederRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, BreederRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static BreederRecipe read(RegistryFriendlyByteBuf buffer) {
            Ingredient input0 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Ingredient input1 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Ingredient input2 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            ItemStack output = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);
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
}