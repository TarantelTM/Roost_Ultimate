package net.tarantel.chickenroost.recipes;

import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.tarantel.chickenroost.ChickenRoostMod;

public class Breeder_Recipe implements Recipe<Container> {
    public final ResourceLocation recipeId;
    public final ItemStack output;
    public final Ingredient ingredient0;
    public final Ingredient ingredient1;
    public final Ingredient ingredient2;
    public final int time;

    public Breeder_Recipe(ResourceLocation recipeId, ItemStack output, Ingredient ingredient0, Ingredient ingredient1, Ingredient ingredient2, int time) {
        this.recipeId = recipeId;
        this.output = output;
        this.ingredient0 = ingredient0;
        this.ingredient1 = ingredient1;
        this.ingredient2 = ingredient2;
        this.time = time;
    }



    @Override
    public boolean matches(Container container, Level level) {
        if (level.isClientSide()) {
            return false;
        }
        return ingredient0.test(container.getItem(1)) && ingredient1.test(container.getItem(0)) && ingredient2.test(container.getItem(2));
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return output.copy();
    }


    public ItemStack getResultEmi() {
        return output.copy();
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.createWithCapacity(3);
        ingredients.add(0, ingredient0);
        ingredients.add(1, ingredient1);
        ingredients.add(2, ingredient2);
        return ingredients;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return output.copy();
    }

    @Override
    public String getGroup() {
        return "basic_breeding";
    }

    public int getTime(){
        return time;
    }

    @Override
    public ResourceLocation getId() {
        return recipeId;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.BASIC_BREEDING_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.BASIC_BREEDING_TYPE.get();
    }

    public static class Type implements RecipeType<Breeder_Recipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "basic_breeding";
    }

    public static final class Serializer implements RecipeSerializer<Breeder_Recipe> {
        private Serializer() {}
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(ChickenRoostMod.MODID,"basic_breeding");

        @Override
        public Breeder_Recipe fromJson(ResourceLocation recipeId, JsonObject json) {
            ItemStack output = ShapedRecipe.itemStackFromJson(json.getAsJsonObject("output"));
            Ingredient ingredient0 = Ingredient.fromJson(json.getAsJsonObject("food"));
            Ingredient ingredient1 = Ingredient.fromJson(json.getAsJsonObject("left-chicken"));
            Ingredient ingredient2 = Ingredient.fromJson(json.getAsJsonObject("right-chicken"));
            int time;
            if( !json.has( "time" ) ){
                time = 20;
            }
            else{
                time = json.get("time").getAsInt();
            }
            return new Breeder_Recipe(recipeId, output, ingredient0, ingredient1, ingredient2, time);
        }

        @Override
        public Breeder_Recipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient ingredient0 = Ingredient.fromNetwork(buffer);
            Ingredient ingredient1 = Ingredient.fromNetwork(buffer);
            Ingredient ingredient2 = Ingredient.fromNetwork(buffer);
            ItemStack output = buffer.readItem();
            int time = buffer.readVarInt();
            return new Breeder_Recipe(recipeId, output, ingredient0, ingredient1, ingredient2, time);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, Breeder_Recipe recipe) {
            recipe.ingredient0.toNetwork(buffer);
            recipe.ingredient1.toNetwork(buffer);
            recipe.ingredient2.toNetwork(buffer);
            buffer.writeItem(recipe.output);
            buffer.writeVarInt(recipe.time);
        }
    }
}