/*package net.tarantel.chickenroost.api.emi;

import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.util.Identifier;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.block.ModBlocks;
import net.tarantel.chickenroost.recipemanager.BasicBreedingRecipe;

public class RoostEmiPlugin implements EmiPlugin {

    private static final Identifier FORGE_ID = new Identifier("chicken_roost:basic_breeding");
    public static final EmiRecipeCategory FORGE_CATEGORY = new EmiRecipeCategory(FORGE_ID, EmiStack.of(ModBlocks.BREEDER));


    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(FORGE_CATEGORY);


            registry.addWorkstation(FORGE_CATEGORY, EmiStack.of(ModBlocks.BREEDER));


        for (BasicBreedingRecipe recipe : registry.getRecipeManager().listAllOfType(BasicBreedingRecipe.Type.INSTANCE)) {
            registry.addRecipe(new BreederEmiRecipe(recipe));
        }

        registry.addRecipeHandler(ChickenRoostMod.BREEDER_FABRIC_HANDLER, new RoostEmiRecipeHandler());
    }
}
/*
 */
