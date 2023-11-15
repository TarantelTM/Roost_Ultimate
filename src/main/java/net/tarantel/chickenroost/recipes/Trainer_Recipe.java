package net.tarantel.chickenroost.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.tarantel.chickenroost.ChickenRoostMod;
import org.jetbrains.annotations.Nullable;

public class Trainer_Recipe implements Recipe<SimpleContainer> {

    public final ItemStack output;
    public final Ingredient ingredient0;

    public Trainer_Recipe(ItemStack output, Ingredient ingredient0) {
        this.output = output;
        this.ingredient0 = ingredient0;
    }
    @Override
    public ItemStack assemble(SimpleContainer simpleContainer, RegistryAccess registryAccess) {
        return output;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return output.copy();
    }
    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
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
        return "trainer_output";
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Trainer_Recipe.Serializer.INSTANCE;
    }
    @Override
    public RecipeType<?> getType() {
        return Trainer_Recipe.Type.INSTANCE;
    }
    public static class Type implements RecipeType<Trainer_Recipe> {
        private Type() { }
        public static final Trainer_Recipe.Type INSTANCE = new Trainer_Recipe.Type();
        public static final String ID = "trainer_output";
    }
    public static class Serializer implements RecipeSerializer<Trainer_Recipe> {
        public static final Trainer_Recipe.Serializer INSTANCE = new Trainer_Recipe.Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(ChickenRoostMod.MODID, "trainer_output");

        private final Codec<Trainer_Recipe> CODEC = RecordCodecBuilder.create((instance) -> {
            return instance.group(CodecFix.ITEM_STACK_CODEC.fieldOf("output").forGetter((recipe) -> {
                return recipe.output;
            }), Ingredient.CODEC_NONEMPTY.fieldOf("food").forGetter((recipe) -> {
                return recipe.ingredient0;
            })).apply(instance, Trainer_Recipe::new);
        });

        @Override
        public Codec<Trainer_Recipe> codec() {
            return CODEC;
        }

        @Override
        public Trainer_Recipe fromNetwork(FriendlyByteBuf buffer) {
            Ingredient input0 = Ingredient.fromNetwork(buffer);
            ItemStack output = buffer.readItem();

            return new Trainer_Recipe(output, input0);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, Trainer_Recipe recipe) {
            recipe.ingredient0.toNetwork(buffer);
            buffer.writeItemStack(recipe.output, false);
        }
    }
}