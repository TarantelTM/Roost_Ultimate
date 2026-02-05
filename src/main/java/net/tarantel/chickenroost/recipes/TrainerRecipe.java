package net.tarantel.chickenroost.recipes;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.RoostBaseRecipe;
import org.jetbrains.annotations.NotNull;

public record TrainerRecipe(ItemStack output, Ingredient ingredient0) implements RoostBaseRecipe<RecipeInput> {

    @Override
    public @NotNull ItemStack assemble(@NotNull RecipeInput container,@NotNull HolderLookup.Provider registries) {
        return output;
    }

    public Identifier getId() {
        return ChickenRoostMod.ownresource("trainer_output");
    }


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
        return ItemStack.isSameItemSameComponents(output, itemStack);
    }


    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }


    public @NotNull String getGroup() {
        return "trainer_output";
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
        return ModRecipes.TRAINER_CATEGORY.get();
    }

    public static final class Type implements RecipeType<TrainerRecipe> {
        private Type() {
        }

        public static final Type INSTANCE = new Type();
        public static final String ID = "trainer_output";
    }

    public static final class Serializer implements RecipeSerializer<TrainerRecipe> {
        private Serializer() {
        }

        public static final Serializer INSTANCE = new Serializer();
        public static final Identifier ID =
                ChickenRoostMod.ownresource("trainer_output");

        private final MapCodec<TrainerRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(CodecFix.ITEM_STACK_CODEC.fieldOf("output").forGetter((recipe) -> recipe.output.copy()), Ingredient.CODEC.fieldOf("chicken").forGetter((recipe) -> recipe.ingredient0)).apply(instance, TrainerRecipe::new));

        private final StreamCodec<RegistryFriendlyByteBuf, TrainerRecipe> STREAM_CODEC = StreamCodec.of(
                Serializer::write, Serializer::read);

        @Override
        public @NotNull MapCodec<TrainerRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, TrainerRecipe> streamCodec() {
            return STREAM_CODEC;
        }


        private static TrainerRecipe read(RegistryFriendlyByteBuf buffer) {
            Ingredient input0 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            ItemStack output = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);

            return new TrainerRecipe(output, input0);
        }

        private static void write(RegistryFriendlyByteBuf buffer, TrainerRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient0);
            ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.output);
        }
    }
}