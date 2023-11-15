package net.tarantel.chickenroost.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.RegistryAccess;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
@SuppressWarnings("ALL")
public class Soul_Extractor_Recipe implements Recipe<SimpleContainer> {

    public final ItemStack output;
    public final Ingredient ingredient0;

    public Soul_Extractor_Recipe(ItemStack output, Ingredient ingredient0) {
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
        return "soul_extraction";
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Soul_Extractor_Recipe.Serializer.INSTANCE;
    }
    @Override
    public RecipeType<?> getType() {
        return Soul_Extractor_Recipe.Type.INSTANCE;
    }
    public static class Type implements RecipeType<Soul_Extractor_Recipe> {
        private Type() { }
        public static final Soul_Extractor_Recipe.Type INSTANCE = new Soul_Extractor_Recipe.Type();
        public static final String ID = "soul_extraction";
    }
    public static class Serializer implements RecipeSerializer<Soul_Extractor_Recipe> {
        public static final Soul_Extractor_Recipe.Serializer INSTANCE = new Soul_Extractor_Recipe.Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(ChickenRoostMod.MODID, "soul_extraction");

        private final Codec<Soul_Extractor_Recipe> CODEC = RecordCodecBuilder.create((instance) -> {
            return instance.group(CodecFix.ITEM_STACK_CODEC.fieldOf("output").forGetter((recipe) -> {
                return recipe.output;
            }), Ingredient.CODEC_NONEMPTY.fieldOf("chicken").forGetter((recipe) -> {
                return recipe.ingredient0;
            })).apply(instance, Soul_Extractor_Recipe::new);
        });

        @Override
        public Codec<Soul_Extractor_Recipe> codec() {
            return CODEC;
        }

        @Override
        public Soul_Extractor_Recipe fromNetwork(FriendlyByteBuf buffer) {
            Ingredient input0 = Ingredient.fromNetwork(buffer);
            ItemStack output = buffer.readItem();

            return new Soul_Extractor_Recipe(output, input0);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, Soul_Extractor_Recipe recipe) {
            recipe.ingredient0.toNetwork(buffer);
            buffer.writeItemStack(recipe.output, false);
        }
    }
}