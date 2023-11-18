package net.tarantel.chickenroost.recipemanager;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BasicBreedingRecipe implements Recipe<SimpleInventory> {
    private final Identifier id;
    private final ItemStack output;

    private final DefaultedList<Ingredient> recipeItems;

    private final Map<Ingredient, Integer> inputs;

    public BasicBreedingRecipe(Identifier id, Map<Ingredient, Integer> inputs, ItemStack output, DefaultedList<Ingredient> recipeItems) {
        this.id = id;
        this.output = output;
        this.inputs = inputs;
        this.recipeItems = recipeItems;

    }

    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        if(world.isClient()) {
            return false;
        }
        return recipeItems.get(0).test(inventory.getStack(0)) && recipeItems.get(1).test(inventory.getStack(1));

    }

    @Override
    public ItemStack craft(SimpleInventory inventory, DynamicRegistryManager registryManager) {
        return output;
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> ingredients = DefaultedList.ofSize(2, Ingredient.EMPTY);
        for (int i = 0; i < recipeItems.size(); i++) {
            ingredients.set(i, Ingredient.ofStacks(recipeItems.get(i).getMatchingStacks()));
        }
        return ingredients;
    }


    public Map<Ingredient, Integer> getIngredientsMap() {
        return inputs;
    }

    /*@Override
    public ItemStack craft(SimpleInventory inventory) {
        return output;
    }*/


    @Override
    public boolean fits(int width, int height) {
        //return true;
        return width * height >= this.recipeItems.size();
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return output.copy();
    }

    public ItemStack newOutput(){
        return output.copy();
    }
    public List<Ingredient> getInput() {
        return this.recipeItems;
    }
    /*@Override
    public ItemStack getOutput() {
        return output.copy();
    }*/

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return BasicBreedingRecipe.Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return BasicBreedingRecipe.Type.INSTANCE;
    }


    public static class Type implements RecipeType<BasicBreedingRecipe> {
        private Type() { }
        public static final BasicBreedingRecipe.Type INSTANCE = new BasicBreedingRecipe.Type();
        public static final String ID = "basic_breeding";
    }

    public static class Serializer implements RecipeSerializer<BasicBreedingRecipe> {
        public static final BasicBreedingRecipe.Serializer INSTANCE = new BasicBreedingRecipe.Serializer();
        public static final String ID = "basic_breeding";

        @Override
        public BasicBreedingRecipe read(Identifier id, JsonObject json) {
            Map<IngredientData, MutableInt> ingredientDataToCount = new LinkedHashMap<>();

            for (var entry : JsonHelper.getArray(json, "ingredients")) {
                var object = entry.getAsJsonObject();

                IngredientData inputData;

                if (object.has("item")) {
                    inputData = new IngredientData(object.get("item").getAsString(), false);
                } else if (object.has("tag")) {
                    inputData = new IngredientData(object.get("tag").getAsString(), true);
                } else {
                    throw new JsonSyntaxException("An ingredient entry needs either a tag or an item");
                }

                ingredientDataToCount.computeIfAbsent(inputData, stringStringPair -> new MutableInt(0))
                        .add(object.keySet().contains("count") ? object.get("count").getAsInt() : 1);
            }

            if (ingredientDataToCount.isEmpty()) throw new JsonSyntaxException("Inputs cannot be empty");
            if (ingredientDataToCount.keySet().size() > 10) {
                throw new JsonSyntaxException("Recipe has more than 10 distinct input ingredients");
            }

            var ingredientToCount = new LinkedHashMap<Ingredient, Integer>();
            for (var entry : ingredientDataToCount.entrySet()) {
                final var ingredientData = entry.getKey();

                Ingredient ingredient;
                Identifier identifier = new Identifier(ingredientData.data());

                ingredient = ingredientData.isTag()
                        ? Ingredient.fromTag(TagKey.of(Registries.ITEM.getKey(), identifier))
                        : Ingredient.ofItems(JsonHelper.asItem(new JsonPrimitive(ingredientData.data()), identifier.toString()));

                ingredientToCount.put(ingredient, entry.getValue().intValue());
            }
            ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "output"));

            JsonArray ingredients = JsonHelper.getArray(json, "ingredients");
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(2, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new BasicBreedingRecipe(id, ingredientToCount, output, inputs);
        }

        @Override
        public BasicBreedingRecipe read(Identifier id, PacketByteBuf buf) {
            PacketByteBuf buf2 = new PacketByteBuf(buf);
            final var newinputs = buf2.readMap(value -> new LinkedHashMap<>(), Ingredient::fromPacket, PacketByteBuf::readVarInt);

            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromPacket(buf));
            }

            ItemStack output = buf.readItemStack();
            return new BasicBreedingRecipe(id, newinputs, output, inputs);
        }

        @Override
        public void write(PacketByteBuf buf, BasicBreedingRecipe recipe) {
            buf.writeMap(recipe.getIngredientsMap(), (buf1, ingredient) -> ingredient.write(buf1), PacketByteBuf::writeVarInt);
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.write(buf);
            }

            buf.writeItemStack(recipe.output.copy());
        }
    }

    private record IngredientData(String data, boolean isTag) {}
}