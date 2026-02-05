package net.tarantel.chickenroost.datagen;

import java.util.ArrayList;
import java.util.List;

public class ChickenRecipeContainer {

    public RoostOutputRecipeData roost_output;
    public List<BasicBreedingRecipeData> basic_breeding;


    public static Builder builder() {
        return new Builder();
    }


    public static class Builder {

        private RoostOutputRecipeData roost;
        private final List<BasicBreedingRecipeData> breeding = new ArrayList<>();


        public Builder roost(String foodTag, String output, Integer time) {
            RoostOutputRecipeData r = new RoostOutputRecipeData();
            r.foodTag = foodTag;
            r.output = output;
            r.time = time;
            this.roost = r;
            return this;
        }


        public Builder breed(
                String left,
                String right,
                String foodTag,
                String output,
                Integer time
        ) {
            BasicBreedingRecipeData b = new BasicBreedingRecipeData();
            b.left = left;
            b.right = right;
            b.foodTag = foodTag;
            b.output = output;
            b.time = time;
            this.breeding.add(b);
            return this;
        }


        public ChickenRecipeContainer build() {
            ChickenRecipeContainer c = new ChickenRecipeContainer();

            if (roost != null) {
                c.roost_output = roost;
            }

            if (!breeding.isEmpty()) {
                c.basic_breeding = breeding;
            }

            return c;
        }
    }
}
