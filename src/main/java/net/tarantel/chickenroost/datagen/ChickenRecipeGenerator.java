package net.tarantel.chickenroost.datagen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import net.neoforged.fml.loading.FMLPaths;
import net.tarantel.chickenroost.util.ChickenData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ChickenRecipeGenerator {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static final int DEFAULT_BREEDING_TIME = 20;
    private static final int DEFAULT_ROOST_TIME = 20;

    public static void generate(List<ChickenData> chickens) {

        Path root = FMLPaths.GAMEDIR.get()
                .resolve("crlib/resources/data/chicken_roost/recipes");

        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        for (ChickenData chicken : chickens) {
            ChickenRecipeContainer recipes = chicken.getRecipes();
            if (recipes == null) continue;

            generateRoostOutput(root, chicken, recipes.roost_output);
            generateBreeding(root, chicken, recipes.basic_breeding);
        }
    }

    private static void generateRoostOutput(
            Path root,
            ChickenData chicken,
            RoostOutputRecipeData recipe
    ) {
        if (recipe == null) return;

        JsonObject json = new JsonObject();
        json.addProperty("type", "chicken_roost:roost_output");

        JsonObject food = new JsonObject();
        food.addProperty("tag", recipe.foodTag);
        json.add("food", food);

        JsonObject chick = new JsonObject();
        chick.addProperty("item", "chicken_roost:" + chicken.getId());
        json.add("chicken", chick);

        json.addProperty("time",
                recipe.time != null ? recipe.time : DEFAULT_ROOST_TIME);

        JsonObject output = new JsonObject();
        output.addProperty("item", recipe.output);
        json.add("output", output);

        write(root.resolve(chicken.getId() + "_roost.json"), json);
    }

    private static void generateBreeding(
            Path root,
            ChickenData chicken,
            List<BasicBreedingRecipeData> recipes
    ) {
        if (recipes == null) return;

        int index = 0;
        for (BasicBreedingRecipeData r : recipes) {

            JsonObject json = new JsonObject();
            json.addProperty("type", "chicken_roost:basic_breeding");

            JsonObject food = new JsonObject();
            if (r.foodTag != null)
                food.addProperty("tag", r.foodTag);
            json.add("food", food);

            JsonObject left = new JsonObject();
            left.addProperty("item", r.left);
            json.add("left-chicken", left);

            JsonObject right = new JsonObject();
            right.addProperty("item", r.right);
            json.add("right-chicken", right);

            json.addProperty("time",
                    r.time != null ? r.time : DEFAULT_BREEDING_TIME);

            JsonObject output = new JsonObject();
            output.addProperty("item", r.output);
            json.add("output", output);

            write(
                    root.resolve(chicken.getId() + "_breeding_" + index + ".json"),
                    json
            );
            index++;
        }
    }

    private static void write(Path path, JsonObject json) {
        try {
            Files.createDirectories(path.getParent());
            Files.writeString(path, GSON.toJson(json));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
