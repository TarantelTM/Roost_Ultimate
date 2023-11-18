package net.tarantel.chickenroost.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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
    private final ResourceLocation id;
    public final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;

    public Trainer_Recipe(ResourceLocation id, ItemStack output,
                                 NonNullList<Ingredient> recipeItems) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide()) {
            return false;
        }

        return recipeItems.get(0).test(pContainer.getItem(0));
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
    public String getGroup() {
        return "Trainer";
    }

    @Override
    public boolean isSpecial() {
        return true;
    }
    @Override
    public NonNullList<Ingredient> getIngredients() {
        return recipeItems;
    }

    /*@Override
    public ItemStack assemble(SimpleContainer pContainer) {
        return output;
    }
*/
    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    /* @Override
     public ItemStack getResultItem() {
         return output.copy();
     }
 */
    @Override
    public ResourceLocation getId() {
        return id;
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

        @Override
        public Trainer_Recipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new Trainer_Recipe(pRecipeId, output, inputs);
        }

        @Override
        public @Nullable Trainer_Recipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(buf));
            }

            ItemStack output = buf.readItem();
            return new Trainer_Recipe(id, output, inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, Trainer_Recipe recipe) {
            buf.writeInt(recipe.getIngredients().size());

            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }
            buf.writeItem(recipe.output);
        }
    }
}