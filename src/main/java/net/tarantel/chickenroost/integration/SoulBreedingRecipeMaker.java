/*package net.tarantel.chickenroost.integration;

import java.util.ArrayList;
import java.util.List;
import net.tarantel.chickenroost.SoulBreedingRecipe;
import net.tarantel.chickenroost.recipes.ModRecipes;
import net.minecraft.world.item.crafting.RecipeManager;

public final class SoulBreedingRecipeMaker
{
	// private static final Logger LOGGER = LogManager.getLogger();
	private SoulBreedingRecipeMaker() {}
	
	
	public static List<SoulBreedingRecipe> getSoulBreedingRecipes(RecipeManager recipeManager)
	{
		// Collection<Recipe<?>> recipes = recipeManager.getRecipes();
        List<SoulBreedingRecipe> recipesSoulBreeding = new ArrayList<SoulBreedingRecipe> ();
        recipesSoulBreeding = recipeManager.getAllRecipesFor(ModRecipes.SOUL_BREEDING_TYPE.get());
        
//        fusion_recipes = recipes.stream()
//        		.filter(t -> t.getType() == ModRecipeTypes.FUSION_TYPE.get())
//        		.toList();
        return recipesSoulBreeding;
	}
} // end class*/