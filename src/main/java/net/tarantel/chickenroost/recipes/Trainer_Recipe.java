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
import net.tarantel.chickenroost.item.base.AnimatedChicken;
import org.jetbrains.annotations.NotNull;

public class Trainer_Recipe implements Recipe<SimpleContainer> {
    public final ResourceLocation recipeId;
    public final ItemStack output;
    //public final Ingredient ingredient0;

    public Trainer_Recipe(ResourceLocation recipeId, ItemStack output) {
        this.recipeId = recipeId;
        this.output = output;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SimpleContainer container, @NotNull RegistryAccess registryAccess) {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return recipeId;
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess registryAccess) {
        return output.copy();
    }

    @Override
    public boolean matches(@NotNull SimpleContainer container, Level level) {
        ItemStack stack = container.getItem(0);
        return !stack.isEmpty() && stack.getItem() instanceof AnimatedChicken;
    }

    @Override
    public boolean isSpecial() {
        return false;
    }

    /*@Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.createWithCapacity(1);
        ingredients.add(0, ingredient0);
        return ingredients;
    }*/

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {

        Ingredient chickenIngredient = Ingredient.of(
                ForgeRegistries.ITEMS.getValues().stream()
                        .filter(item -> item instanceof AnimatedChicken)
                        .map(ItemStack::new)
        );

        NonNullList<Ingredient> list = NonNullList.create();
        list.add(chickenIngredient);
        return list;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public @NotNull String getGroup() {
        return "trainer_output";
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipes.TRAINER_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipes.TRAINER_TYPE.get();
    }

    public static final class Type implements RecipeType<Trainer_Recipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "trainer_output";
    }

    public static final class Serializer implements RecipeSerializer<Trainer_Recipe> {
        private Serializer() {}
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation("chicken_roost:trainer_output");

        @Override
        public Trainer_Recipe fromJson(ResourceLocation recipeId, JsonObject json) {
            ItemStack output = CraftingHelper.getItemStack(json.getAsJsonObject("output"), true);
            return new Trainer_Recipe(recipeId, output);
        }

        @Override
        public Trainer_Recipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            ItemStack output = buffer.readItem();
            return new Trainer_Recipe(recipeId, output);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, Trainer_Recipe recipe) {
            buffer.writeItem(recipe.output);
        }
    }
}