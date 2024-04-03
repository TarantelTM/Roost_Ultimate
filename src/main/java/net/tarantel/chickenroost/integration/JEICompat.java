package net.tarantel.chickenroost.integration;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.block.ModBlocks;
import net.tarantel.chickenroost.recipes.*;
import net.tarantel.chickenroost.screen.*;

import java.util.List;
import java.util.Objects;
@SuppressWarnings("ALL")
@JeiPlugin
public class JEICompat implements IModPlugin {
    public static RecipeType<Soul_Breeder_Recipe> SOUL_BREEDING_TYPE =
            new RecipeType<>(SoulBreedingRecipeCategory.UID, Soul_Breeder_Recipe.class);
    public static RecipeType<Breeder_Recipe> BASIC_BREEDING_TYPE =
            new RecipeType<>(BreederRecipeCategory.UID, Breeder_Recipe.class);
    public static RecipeType<Soul_Extractor_Recipe> SOUL_EXTRACTION_TYPE =
            new RecipeType<>(SoulExtractionRecipeCategory.UID, Soul_Extractor_Recipe.class);

    public static RecipeType<Roost_Recipe> ROOST_TYPE =
            new RecipeType<>(RoostRecipeCategory.UID, Roost_Recipe.class);

    public static RecipeType<Trainer_Recipe> TRAINER_TYPE =
            new RecipeType<>(TrainerRecipeCategory.UID, Trainer_Recipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(ChickenRoostMod.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
    	var jeiHelpers = registration.getJeiHelpers();
        registration.addRecipeCategories(new SoulBreedingRecipeCategory(jeiHelpers.getGuiHelper()));
        registration.addRecipeCategories(new BreederRecipeCategory(jeiHelpers.getGuiHelper()));
        registration.addRecipeCategories(new SoulExtractionRecipeCategory(jeiHelpers.getGuiHelper()));
        registration.addRecipeCategories(new RoostRecipeCategory(jeiHelpers.getGuiHelper()));
        registration.addRecipeCategories(new TrainerRecipeCategory(jeiHelpers.getGuiHelper()));
        
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<Soul_Breeder_Recipe> recipesSoulBreeding = rm.getAllRecipesFor(Soul_Breeder_Recipe.Type.INSTANCE);
        registration.addRecipes(SOUL_BREEDING_TYPE, recipesSoulBreeding);
        
        List<Breeder_Recipe> recipesBasicBreeding = rm.getAllRecipesFor(Breeder_Recipe.Type.INSTANCE);
        registration.addRecipes(BASIC_BREEDING_TYPE, recipesBasicBreeding);
        
        List<Soul_Extractor_Recipe> recipesSoulExtraction = rm.getAllRecipesFor(Soul_Extractor_Recipe.Type.INSTANCE);
        registration.addRecipes(SOUL_EXTRACTION_TYPE, recipesSoulExtraction);

        List<Roost_Recipe> recipesRoost = rm.getAllRecipesFor(Roost_Recipe.Type.INSTANCE);
        registration.addRecipes(ROOST_TYPE, recipesRoost);

        List<Trainer_Recipe> recipesTrainer = rm.getAllRecipesFor(Trainer_Recipe.Type.INSTANCE);
        registration.addRecipes(TRAINER_TYPE, recipesTrainer);

        
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration){
    	var soulbreeder = new ItemStack(ModBlocks.SOUL_BREEDER.get());
    	registration.addRecipeCatalyst(soulbreeder, SoulBreedingRecipeCategory.RECIPE_TYPE);
    	var basicbreeder = new ItemStack(ModBlocks.BREEDER.get());
    	registration.addRecipeCatalyst(basicbreeder, BreederRecipeCategory.RECIPE_TYPE);
    	var soulextractor = new ItemStack(ModBlocks.SOUL_EXTRACTOR.get());
    	registration.addRecipeCatalyst(soulextractor, SoulExtractionRecipeCategory.RECIPE_TYPE);

        var roostyv1 = new ItemStack(ModBlocks.ROOST.get());
        registration.addRecipeCatalyst(roostyv1, RoostRecipeCategory.RECIPE_TYPE);

        var trainer = new ItemStack(ModBlocks.TRAINER.get());
        registration.addRecipeCatalyst(trainer, TrainerRecipeCategory.RECIPE_TYPE);
    	
    }

    @Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration)
	{
      //registration.addRecipeClickArea(NewSoulBreederScreen.class, 2, 2, 70, 31, JEITutorialModPlugin.BREEDING_TYPE);
        registration.addRecipeClickArea(Soul_Breeder_Screen.class, 59, 41, 40, 10, JEICompat.SOUL_BREEDING_TYPE);
        registration.addRecipeClickArea(Breeder_Screen.class, 53, 30, 40, 10, JEICompat.BASIC_BREEDING_TYPE);
        registration.addRecipeClickArea(Soul_Extractor_Screen.class, 59, 41, 40, 10, JEICompat.SOUL_EXTRACTION_TYPE);
        registration.addRecipeClickArea(Trainer_Screen.class, 59, 41, 40, 10, JEICompat.TRAINER_TYPE);
        registration.addRecipeClickArea(Roost_Screen.class, 59, 41, 40, 10, JEICompat.ROOST_TYPE);
	}
}