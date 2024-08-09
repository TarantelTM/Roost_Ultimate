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

public class Trainer_Recipe implements Recipe<RecipeInput> {

    public final ItemStack output;
    public final Ingredient ingredient0;

    public Trainer_Recipe(ItemStack output, Ingredient ingredient0) {
        this.output = output;
        this.ingredient0 = ingredient0;
    }
    @Override
    public ItemStack assemble(RecipeInput container, HolderLookup.Provider registries) {
        return output;
    }
    public ResourceLocation getId() {
        return ChickenRoostMod.ownresource("trainer_output");
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
        return ingredient0.test(pContainer.getItem(0));
    }
    @Override
    public boolean isSpecial() {
        return true;
    }
    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.createWithCapacity(1);
        ingredients.add(0, ingredient0);
        return ingredients;
    }
    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }
    @Override
    public String getGroup() {
        return "trainer_output";
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }
    public static final class Type implements RecipeType<Trainer_Recipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "trainer_output";
    }
    public static final class Serializer implements RecipeSerializer<Trainer_Recipe> {
        private Serializer() {}
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                ChickenRoostMod.ownresource("trainer_output");

        private final MapCodec<Trainer_Recipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
            return instance.group(CodecFix.ITEM_STACK_CODEC.fieldOf("output").forGetter((recipe) -> {
                return recipe.output.copy();
            }), Ingredient.CODEC_NONEMPTY.fieldOf("chicken").forGetter((recipe) -> {
                return recipe.ingredient0;
            })).apply(instance, Trainer_Recipe::new);
        });

        private final StreamCodec<RegistryFriendlyByteBuf, Trainer_Recipe> STREAM_CODEC = StreamCodec.of(
                Serializer::write, Serializer::read);

        @Override
        public MapCodec<Trainer_Recipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, Trainer_Recipe> streamCodec() {
            return STREAM_CODEC;
        }


        private static Trainer_Recipe read(RegistryFriendlyByteBuf buffer) {
            Ingredient input0 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            ItemStack output = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);

            return new Trainer_Recipe(output, input0);
        }

        private static void write(RegistryFriendlyByteBuf  buffer, Trainer_Recipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient0);
            ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.output);
        }
    }
}