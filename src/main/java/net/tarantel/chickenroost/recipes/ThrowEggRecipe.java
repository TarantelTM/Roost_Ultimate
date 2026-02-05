package net.tarantel.chickenroost.recipes;

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
public record ThrowEggRecipe(Holder<Item> output, Ingredient ingredient0) implements RoostBaseRecipe<RecipeInput> {


    public @NotNull ItemStack assemble(@NotNull RecipeInput container, HolderLookup.Provider registries) {
        return output.value().getDefaultInstance();
    }
    @Override
    public ItemStack assemble(RecipeInput recipeInput) {
        return output.value().getDefaultInstance();
    }

    public Identifier getId() {
        return ChickenRoostMod.ownresource("throwegg");
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
        return "throwegg";
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
        return ModRecipes.THROW_EGG_CATEGORY.get();
    }

    public static final class Type implements RecipeType<ThrowEggRecipe> {
        private Type() {
        }

        public static final Type INSTANCE = new Type();
        public static final String ID = "throwegg";
    }

    public static final class Serializer implements RecipeSerializer<ThrowEggRecipe> {
        private Serializer() {
        }

        public static final Serializer INSTANCE = new Serializer();
        public static final Identifier ID =
                ChickenRoostMod.ownresource("throwegg");

        private final MapCodec<ThrowEggRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(BuiltInRegistries.ITEM.holderByNameCodec().fieldOf("output").forGetter((recipe) -> recipe.output), Ingredient.CODEC.fieldOf("egg").forGetter((recipe) -> recipe.ingredient0)).apply(instance, ThrowEggRecipe::new));

        private final StreamCodec<RegistryFriendlyByteBuf, ThrowEggRecipe> STREAM_CODEC = StreamCodec.of(
                Serializer::write, Serializer::read);

        @Override
        public @NotNull MapCodec<ThrowEggRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, ThrowEggRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static ThrowEggRecipe read(RegistryFriendlyByteBuf buffer) {
            Holder<Item> output = ByteBufCodecs.holderRegistry(Registries.ITEM).decode(buffer);
            Ingredient input0 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);

            return new ThrowEggRecipe(output, input0);
        }

        private static void write(RegistryFriendlyByteBuf buffer, ThrowEggRecipe recipe) {
            ByteBufCodecs.holderRegistry(Registries.ITEM).encode(buffer, recipe.output());
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient0);

        }
    }
}