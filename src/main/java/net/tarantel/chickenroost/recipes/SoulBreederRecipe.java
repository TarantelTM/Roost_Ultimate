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
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public record SoulBreederRecipe(ItemStack output, Ingredient ingredient0, Ingredient ingredient1,
                                int time) implements Recipe<RecipeInput> {

    @Override
    public @NotNull ItemStack assemble(@NotNull RecipeInput container, HolderLookup.@NotNull Provider registries) {
        return output;
    }

    public ResourceLocation getId() {
        return ChickenRoostMod.ownresource("soul_breeding");
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
        return ingredient0.test(pContainer.getItem(0)) && ingredient1.test(pContainer.getItem(1));
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.createWithCapacity(2);
        ingredients.add(0, ingredient0);
        ingredients.add(1, ingredient1);
        return ingredients;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public @NotNull String getGroup() {
        return "soul_breeding";
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static final class Type implements RecipeType<SoulBreederRecipe> {
        private Type() {
        }

        public static final Type INSTANCE = new Type();
        public static final String ID = "soul_breeding";
    }

    public static final class Serializer implements RecipeSerializer<SoulBreederRecipe> {
        private Serializer() {
        }

        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                ChickenRoostMod.ownresource("soul_breeding");

        private final MapCodec<SoulBreederRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(CodecFix.ITEM_STACK_CODEC.fieldOf("output").forGetter((recipe) -> recipe.output), Ingredient.CODEC_NONEMPTY.fieldOf("soul").forGetter((recipe) -> recipe.ingredient0), Ingredient.CODEC_NONEMPTY.fieldOf("chicken").forGetter((recipe) -> recipe.ingredient1), Codec.INT.fieldOf("time").orElse(20).forGetter((recipe) -> recipe.time)).apply(instance, SoulBreederRecipe::new));

        private final StreamCodec<RegistryFriendlyByteBuf, SoulBreederRecipe> STREAM_CODEC = StreamCodec.of(
                Serializer::write, Serializer::read);

        @Override
        public @NotNull MapCodec<SoulBreederRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, SoulBreederRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static SoulBreederRecipe read(RegistryFriendlyByteBuf buffer) {
            Ingredient input0 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Ingredient input1 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            ItemStack output = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);
            int time = buffer.readVarInt();
            return new SoulBreederRecipe(output, input0, input1, time);
        }

        private static void write(RegistryFriendlyByteBuf buffer, SoulBreederRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient0);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient1);
            ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.output);
            buffer.writeVarInt(recipe.time);
        }
    }
}