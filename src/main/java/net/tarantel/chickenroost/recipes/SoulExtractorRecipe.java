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
public record SoulExtractorRecipe(ItemStack output, Ingredient ingredient0, int time) implements Recipe<RecipeInput> {

    @Override
    public @NotNull ItemStack assemble(@NotNull RecipeInput container, HolderLookup.@NotNull Provider registries) {
        return output;
    }

    public ResourceLocation getId() {
        return ChickenRoostMod.ownresource("soul_extraction");
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
        return ingredient0.test(pContainer.getItem(0));
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.createWithCapacity(1);
        ingredients.addFirst(ingredient0);
        return ingredients;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public @NotNull String getGroup() {
        return "soul_extraction";
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return SoulExtractorRecipe.Serializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return SoulExtractorRecipe.Type.INSTANCE;
    }

    public static final class Type implements RecipeType<SoulExtractorRecipe> {
        private Type() {
        }

        public static final Type INSTANCE = new Type();
        public static final String ID = "soul_extraction";
    }

    public static final class Serializer implements RecipeSerializer<SoulExtractorRecipe> {
        private Serializer() {
        }

        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                ChickenRoostMod.ownresource("soul_extraction");

        private final MapCodec<SoulExtractorRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(CodecFix.ITEM_STACK_CODEC.fieldOf("output").forGetter((recipe) -> recipe.output), Ingredient.CODEC_NONEMPTY.fieldOf("chicken").forGetter((recipe) -> recipe.ingredient0), Codec.INT.fieldOf("time").orElse(20).forGetter((recipe) -> recipe.time)).apply(instance, SoulExtractorRecipe::new));

        private final StreamCodec<RegistryFriendlyByteBuf, SoulExtractorRecipe> STREAM_CODEC = StreamCodec.of(
                Serializer::write, Serializer::read);

        @Override
        public @NotNull MapCodec<SoulExtractorRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, SoulExtractorRecipe> streamCodec() {
            return STREAM_CODEC;
        }


        private static SoulExtractorRecipe read(RegistryFriendlyByteBuf buffer) {
            Ingredient input0 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            ItemStack output = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);
            int time = buffer.readVarInt();
            return new SoulExtractorRecipe(output, input0, time);
        }


        private static void write(RegistryFriendlyByteBuf buffer, SoulExtractorRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient0);
            ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.output);
            buffer.writeVarInt(recipe.time);
        }
    }
}