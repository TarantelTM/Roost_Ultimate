package net.tarantel.chickenroost.datagen;

public class RoostOutputRecipeData {

    public String foodTag;
    public String output;
    public Integer time;

    public static RoostOutputRecipeData of(
            String foodTag,
            String output,
            Integer time
    ) {
        RoostOutputRecipeData r = new RoostOutputRecipeData();
        r.foodTag = foodTag;
        r.output = output;
        r.time = time;
        return r;
    }
}