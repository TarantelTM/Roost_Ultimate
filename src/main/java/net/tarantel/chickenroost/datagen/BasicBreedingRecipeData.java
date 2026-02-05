package net.tarantel.chickenroost.datagen;

public class BasicBreedingRecipeData {

    public String left;
    public String right;
    public String foodTag;
    public String output;
    public Integer time;

    public static BasicBreedingRecipeData of(
            String left,
            String right,
            String foodTag,
            String output,
            Integer time
    ) {
        BasicBreedingRecipeData r = new BasicBreedingRecipeData();
        r.left = left;
        r.right = right;
        r.foodTag = foodTag;
        r.output = output;
        r.time = time;
        return r;
    }
}