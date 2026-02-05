package net.tarantel.chickenroost.recipes;

import com.mojang.serialization.Codec;
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
import net.tarantel.chickenroost.util.Config;
import org.jetbrains.annotations.NotNull;

public record BreederRecipe(ItemStack output, Ingredient ingredient0, Ingredient ingredient1, Ingredient ingredient2,
                            int time) implements RoostBaseRecipe<RecipeInput> {

    @Override
    public @NotNull ItemStack assemble(@NotNull RecipeInput container, HolderLookup.Provider registries) {
        return output;
    }


    public Identifier getId() {
        return ChickenRoostMod.ownresource("basic_breeding");
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
        if (ChickenRoostMod.CONFIG.BreederSeeds) {
            if (Config.enablebothways.get()) {
                return (ingredient0.test(pContainer.getItem(1)) && ingredient1.test(pContainer.getItem(0)) && ingredient2.test(pContainer.getItem(2))) || (ingredient0.test(pContainer.getItem(1)) && ingredient1.test(pContainer.getItem(2)) && ingredient2.test(pContainer.getItem(0)));
            } else {
                return ingredient0.test(pContainer.getItem(1)) && ingredient1.test(pContainer.getItem(0)) && ingredient2.test(pContainer.getItem(2));
            }
        }else {
            if (Config.enablebothways.get()) {
                return (ingredient1.test(pContainer.getItem(0)) && ingredient2.test(pContainer.getItem(2))) || (ingredient1.test(pContainer.getItem(2)) && ingredient2.test(pContainer.getItem(0)));
            } else {
                return ingredient1.test(pContainer.getItem(0)) && ingredient2.test(pContainer.getItem(2));
            }
        }


    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return ModRecipes.BREEDING_CATEGORY.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    public @NotNull NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.createWithCapacity(3);
        ingredients.add(0, ingredient0);
        ingredients.add(1, ingredient1);
        ingredients.add(2, ingredient2);
        return ingredients;
    }

    @Override
    public boolean isIngredient(ItemStack stack) {
        return ingredient0.test(stack) && ingredient1.test(stack) && ingredient2.test(stack) ;
    }

    @Override
    public boolean isResult(ItemStack itemStack) {
        return ItemStack.isSameItemSameComponents(output, itemStack);
    }

    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }


    public @NotNull String getGroup() {
        return "basic_breeding";
    }

    /*@Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }
*/
    /*@Override
    public @NotNull RecipeType<?> getType() {
        return Type.INSTANCE;
    }*/

    @Override
    public RecipeSerializer<? extends Recipe<RecipeInput>> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<? extends Recipe<RecipeInput>> getType() {
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
        public static final Identifier ID =
                ChickenRoostMod.ownresource("basic_breeding");


        private final MapCodec<BreederRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group
                (CodecFix.ITEM_STACK_CODEC.fieldOf("output").forGetter((recipe) -> recipe.output),
                        Ingredient.CODEC.fieldOf("food").forGetter((recipe) -> recipe.ingredient0),
                        Ingredient.CODEC.fieldOf("left-chicken").forGetter((recipe) -> recipe.ingredient1),
                        Ingredient.CODEC.fieldOf("right-chicken").forGetter((recipe) -> recipe.ingredient2),
                        Codec.INT.fieldOf("time").orElse(20).forGetter((recipe) -> recipe.time)).apply(instance, BreederRecipe::new));
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
            ItemStack output = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);
            Ingredient input0 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Ingredient input1 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Ingredient input2 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            int time = buffer.readVarInt();

            return new BreederRecipe(output, input0, input1, input2, time);
        }

        private static void write(RegistryFriendlyByteBuf buffer, BreederRecipe recipe) {
            ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.output);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient0);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient1);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient2);
            buffer.writeVarInt(recipe.time);
        }
    }
}