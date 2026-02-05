package net.tarantel.chickenroost.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.RoostBaseRecipe;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public record SoulExtractorRecipe(Holder<Item> output, Ingredient ingredient0, int time) implements RoostBaseRecipe<RecipeInput> {

    public @NotNull ItemStack assemble(@NotNull RecipeInput container, HolderLookup.Provider registries) {
        return output.value().getDefaultInstance();
    }
    @Override
    public ItemStack assemble(RecipeInput recipeInput) {
        return output.value().getDefaultInstance();
    }

    public Identifier getId() {
        return ChickenRoostMod.ownresource("soul_extraction");
    }


    public @NotNull ItemStack getResultItem(HolderLookup.Provider registries) {
        return output.value().getDefaultInstance();
    }

    public ItemStack getResultEmi() {
        return output.value().getDefaultInstance();
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


    public @NotNull NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.createWithCapacity(1);
        ingredients.addFirst(ingredient0);
        return ingredients;
    }

    @Override
    public boolean isIngredient(ItemStack itemStack) {
        return getIngredients().getFirst().test(itemStack);
    }

    @Override
    public boolean isResult(ItemStack itemStack) {
        return itemStack.is(output.value());
    }


    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }


    public @NotNull String getGroup() {
        return "soul_extraction";
    }

    @Override
    public RecipeSerializer<? extends Recipe<RecipeInput>> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<? extends Recipe<RecipeInput>> getType() {
        return Type.INSTANCE;
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return ModRecipes.SOUL_EXTRACTION_CATEGORY.get();
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
        public static final Identifier ID =
                ChickenRoostMod.ownresource("soul_extraction");

        private final MapCodec<SoulExtractorRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(BuiltInRegistries.ITEM.holderByNameCodec().fieldOf("output").forGetter((recipe) -> recipe.output),
                Ingredient.CODEC.fieldOf("chicken").forGetter((recipe) -> recipe.ingredient0), Codec.INT.fieldOf("time").orElse(20).forGetter((recipe) -> recipe.time)).apply(instance, SoulExtractorRecipe::new));

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
            Holder<Item> output = ByteBufCodecs.holderRegistry(Registries.ITEM).decode(buffer);
            Ingredient input0 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);

            int time = buffer.readVarInt();
            return new SoulExtractorRecipe(output, input0, time);
        }


        private static void write(RegistryFriendlyByteBuf buffer, SoulExtractorRecipe recipe) {
            ByteBufCodecs.holderRegistry(Registries.ITEM).encode(buffer, recipe.output());
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient0);

            buffer.writeVarInt(recipe.time);
        }
    }
}