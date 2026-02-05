package net.tarantel.chickenroost.recipes;

import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.util.Config;

public class Roost_Recipe implements Recipe<SimpleContainer> {
    public final ResourceLocation recipeId;
    public final ItemStack output;
    public final Ingredient ingredient0;
    public final Ingredient ingredient1;
    public final int time;

    public Roost_Recipe(ResourceLocation recipeId, ItemStack output, Ingredient ingredient0, Ingredient ingredient1, int time) {
        this.recipeId = recipeId;
        this.output = output;
        this.ingredient0 = ingredient0;
        this.ingredient1 = ingredient1;
        this.time = time;
    }

    @Override
    public ItemStack assemble(SimpleContainer container, RegistryAccess registryAccess) {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return recipeId;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return output.copy();
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level level) {
        if (level.isClientSide()) {
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

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.createWithCapacity(2);
        ingredients.add(0, ingredient0);
        ingredients.add(1, ingredient1);
        return ingredients;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public String getGroup() {
        return "roost_output";
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.ROOST_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.ROOST_TYPE.get();
    }

    public static final class Type implements RecipeType<Roost_Recipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "roost_output";
    }

    public static final class Serializer implements RecipeSerializer<Roost_Recipe> {
        private Serializer() {}
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation("chicken_roost:roost_output");

        @Override
        public Roost_Recipe fromJson(ResourceLocation recipeId, JsonObject json) {
            ItemStack output = CraftingHelper.getItemStack(json.getAsJsonObject("output"), true);
            Ingredient ingredient0 = Ingredient.fromJson(json.getAsJsonObject("food"));
            Ingredient ingredient1 = Ingredient.fromJson(json.getAsJsonObject("chicken"));
            int time;
            if( !json.has( "time" ) ){
                time = 20;
            }
            else{
                time = json.get("time").getAsInt();
            }
            return new Roost_Recipe(recipeId, output, ingredient0, ingredient1, time);
        }

        @Override
        public Roost_Recipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient input0 = Ingredient.fromNetwork(buffer);
            Ingredient input1 = Ingredient.fromNetwork(buffer);
            ItemStack output = buffer.readItem();
            int time = buffer.readVarInt();
            return new Roost_Recipe(recipeId, output, input0, input1, time);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, Roost_Recipe recipe) {
            recipe.ingredient0.toNetwork(buffer);
            recipe.ingredient1.toNetwork(buffer);
            buffer.writeItem(recipe.output);
            buffer.writeVarInt(recipe.time);
        }
    }
}