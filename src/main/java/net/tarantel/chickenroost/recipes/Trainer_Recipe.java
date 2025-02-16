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
import org.jetbrains.annotations.NotNull;

public class Trainer_Recipe implements Recipe<SimpleContainer> {
    public final ResourceLocation recipeId;
    public final ItemStack output;
    public final Ingredient ingredient0;

    public Trainer_Recipe(ResourceLocation recipeId,ItemStack output, Ingredient ingredient0) {
        this.recipeId = recipeId;
        this.output = output;
        this.ingredient0 = ingredient0;
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
        if (level.isClientSide()) {
            return false;
        }
        return ingredient0.test(container.getItem(0));
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.createWithCapacity(1);
        ingredients.add(0, ingredient0);
        return ingredients;
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
        public static final ResourceLocation ID = new ResourceLocation(ChickenRoostMod.MODID, "trainer_output");

        @Override
        public Trainer_Recipe fromJson(ResourceLocation recipeId, JsonObject json) {
            ItemStack output = CraftingHelper.getItemStack(json.getAsJsonObject("output"), true);
            Ingredient ingredient0 = Ingredient.fromJson(json.getAsJsonObject("chicken"));
            return new Trainer_Recipe(recipeId, output, ingredient0);
        }

        @Override
        public Trainer_Recipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient input0 = Ingredient.fromNetwork(buffer);
        ItemStack output = buffer.readItem();
            return new Trainer_Recipe(recipeId, output, input0);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, Trainer_Recipe recipe) {
            recipe.ingredient0.toNetwork(buffer);
            buffer.writeItem(recipe.output);
        }
    }
}