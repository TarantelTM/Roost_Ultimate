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
public record RoostRecipe(Holder<Item> output, Ingredient ingredient0, Ingredient ingredient1,
                          int time) implements RoostBaseRecipe<RecipeInput> {


    public @NotNull ItemStack assemble(@NotNull RecipeInput container, HolderLookup.Provider registries) {
        ItemStack itemStack = this.output.value().getDefaultInstance();
        itemStack.applyComponents(this.output.value().getDefaultInstance().getComponentsPatch());
        return itemStack;
    }
    @Override
    public ItemStack assemble(RecipeInput recipeInput) {
        ItemStack itemStack = this.output.value().getDefaultInstance();
        itemStack.applyComponents(this.output.value().getDefaultInstance().getComponentsPatch());
        return itemStack;
    }

    public Identifier getId() {
        return ChickenRoostMod.ownresource("roost_output");
    }


    public @NotNull ItemStack getResultItem(HolderLookup.Provider registries) {
        return this.output.value().getDefaultInstance();
    }

    public ItemStack getResultEmi() {
        return output.value().getDefaultInstance();
    }

    @Override
    public boolean matches(@NotNull RecipeInput pContainer, Level pLevel) {
        if (pLevel.isClientSide()) {
            return false;
        }
        if (ChickenRoostMod.CONFIG.RoostSeeds) {
            return ingredient0.test(pContainer.getItem(0)) && ingredient1.test(pContainer.getItem(1));
        }else {
            return ingredient1.test(pContainer.getItem(1));
        }

    }

    @Override
    public boolean isSpecial() {
        return true;
    }


    public @NotNull NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.createWithCapacity(2);
        ingredients.add(0, ingredient0);
        ingredients.add(1, ingredient1);
        return ingredients;
    }

    @Override
    public boolean isIngredient(ItemStack stack) {
        return ingredient0.test(stack) && ingredient1.test(stack);
    }

    @Override
    public boolean isResult(ItemStack itemStack) {
        return itemStack.is(output.value());
    }


    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }


    public @NotNull String getGroup() {
        return "roost_output";
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
        return ModRecipes.ROOST_CATEGORY.get();
    }

    public static final class Type implements RecipeType<RoostRecipe> {
        private Type() {
        }

        public static final Type INSTANCE = new Type();
        public static final Identifier ID =
                ChickenRoostMod.ownresource("roost_output");
    }

    public static final class Serializer implements RecipeSerializer<RoostRecipe> {
        private Serializer() {
        }

        public static final Serializer INSTANCE = new Serializer();
        public static final Identifier ID =
                ChickenRoostMod.ownresource("roost_output");

        private final MapCodec<RoostRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) ->
                instance.group(BuiltInRegistries.ITEM.holderByNameCodec().fieldOf("output").forGetter((recipe) -> recipe.output),
                        Ingredient.CODEC.fieldOf("food").forGetter((recipe) -> recipe.ingredient0),
                        Ingredient.CODEC.fieldOf("chicken").forGetter((recipe) -> recipe.ingredient1),
                        Codec.INT.fieldOf("time").orElse(20).forGetter((recipe) -> recipe.time)).apply(instance,
                        RoostRecipe::new));

        private final StreamCodec<RegistryFriendlyByteBuf, RoostRecipe> STREAM_CODEC = StreamCodec.of(
                Serializer::write, Serializer::read);

        @Override
        public @NotNull MapCodec<RoostRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, RoostRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static RoostRecipe read(RegistryFriendlyByteBuf buffer) {
            Holder<Item> output = ByteBufCodecs.holderRegistry(Registries.ITEM).decode(buffer);
            Ingredient input0 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Ingredient input1 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);

            int time = buffer.readVarInt();
            return new RoostRecipe(output, input0, input1, time);
        }

        private static void write(RegistryFriendlyByteBuf buffer, RoostRecipe recipe) {
            ByteBufCodecs.holderRegistry(Registries.ITEM).encode(buffer, recipe.output());
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient0);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient1);
            buffer.writeVarInt(recipe.time);
        }
    }
}