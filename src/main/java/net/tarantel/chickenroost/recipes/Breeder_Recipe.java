package net.tarantel.chickenroost.recipes;

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

public class Breeder_Recipe implements Recipe<RecipeInput> {

    public final ItemStack output;
    public final Ingredient ingredient0;
    public final Ingredient ingredient1;


    public Breeder_Recipe(ItemStack output, Ingredient ingredient0, Ingredient ingredient1) {
        this.output = output;
        this.ingredient0 = ingredient0;
        this.ingredient1 = ingredient1;
    }
    @Override
    public ItemStack assemble(RecipeInput container, HolderLookup.Provider registries) {
        return output;
    }



    public ResourceLocation getId() {
        return ChickenRoostMod.ownresource("basic_breeding");
    }


    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return output.copy();
    }

    public ItemStack getResultEmi(){
        return output.copy();
    }


    @Override
    public boolean matches(RecipeInput pContainer, Level pLevel) {
        if(pLevel.isClientSide()) {
            return false;
        }
        return ingredient0.test(pContainer.getItem(1)) && ingredient1.test(pContainer.getItem(0));
    }
    @Override
    public boolean isSpecial() {
        return true;
    }
    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.createWithCapacity(2);
        ingredients.add(0, ingredient0);
        ingredients.add(1, ingredient1);
        return ingredients;
    }

    /*public Ingredient ingredient0(){
        return recipeItems.get(0);
    }

    public Ingredient ingredient1(){
        return recipeItems.get(1);
    }*/
    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }
    @Override
    public String getGroup() {
        return "basic_breeding";
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Breeder_Recipe.Serializer.INSTANCE;
    }
    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }
    public static class Type implements RecipeType<Breeder_Recipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "basic_breeding";
    }
    public static final class Serializer implements RecipeSerializer<Breeder_Recipe> {
        private Serializer() {}
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                ChickenRoostMod.ownresource("basic_breeding");

        private final MapCodec<Breeder_Recipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
            return instance.group(CodecFix.ITEM_STACK_CODEC.fieldOf("output").forGetter((recipe) -> {
                return recipe.output;
            }), Ingredient.CODEC_NONEMPTY.fieldOf("food").forGetter((recipe) -> {
                return recipe.ingredient0;
            }), Ingredient.CODEC_NONEMPTY.fieldOf("chicken").forGetter((recipe) -> {
                return recipe.ingredient1;
            })).apply(instance, Breeder_Recipe::new);
        });
        private final StreamCodec<RegistryFriendlyByteBuf, Breeder_Recipe> STREAM_CODEC = StreamCodec.of(
                Serializer::write, Serializer::read);
        @Override
        public MapCodec<Breeder_Recipe> codec() {
            return CODEC;
        }
        @Override
        public StreamCodec<RegistryFriendlyByteBuf, Breeder_Recipe> streamCodec() {
            return STREAM_CODEC;
        }
       // @Override
        private static  Breeder_Recipe read(RegistryFriendlyByteBuf buffer) {
            Ingredient input0 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Ingredient input1 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            ItemStack output  = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);

            return new Breeder_Recipe(output, input0, input1);
        }

        //@Override
        private static void write(RegistryFriendlyByteBuf buffer, Breeder_Recipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient0);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient1);
            ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.output);
        }
    }
}