package net.tarantel.chickenroost.api.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.world.level.Level;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.item.ModItems;
import net.tarantel.chickenroost.recipes.*;
import net.tarantel.chickenroost.screen.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    public static RecipeType<ThrowEggRecipe> EGG_TYPE =
            new RecipeType<>(EggRecipeCategory.UID, ThrowEggRecipe.class);

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
        return new ResourceLocation("chicken_roost:jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
    	var jeiHelpers = registration.getJeiHelpers();
        registration.addRecipeCategories(new EggRecipeCategory(jeiHelpers.getGuiHelper()));

        registration.addRecipeCategories(new BreederRecipeCategory(jeiHelpers.getGuiHelper()));
        registration.addRecipeCategories(new SoulExtractionRecipeCategory(jeiHelpers.getGuiHelper()));

        registration.addRecipeCategories(new RoostRecipeCategory(jeiHelpers.getGuiHelper()));
        registration.addRecipeCategories(new TrainerRecipeCategory(jeiHelpers.getGuiHelper()));

    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        Level level = Minecraft.getInstance().level;


        List<Breeder_Recipe> recipesBasicBreeding = rm.getAllRecipesFor(Breeder_Recipe.Type.INSTANCE);
        registration.addRecipes(BASIC_BREEDING_TYPE, recipesBasicBreeding);

        List<ThrowEggRecipe> recipesBasicBreedingNew = rm.getAllRecipesFor(ThrowEggRecipe.Type.INSTANCE);
        registration.addRecipes(EGG_TYPE, recipesBasicBreedingNew);

        List<Soul_Extractor_Recipe> recipesSoulExtraction = rm.getAllRecipesFor(Soul_Extractor_Recipe.Type.INSTANCE);
        registration.addRecipes(SOUL_EXTRACTION_TYPE, recipesSoulExtraction);

        List<Roost_Recipe> recipesRoost = rm.getAllRecipesFor(Roost_Recipe.Type.INSTANCE);
        registration.addRecipes(ROOST_TYPE, recipesRoost);

        //List<Trainer_Recipe> recipesTrainer = rm.getAllRecipesFor(Trainer_Recipe.Type.INSTANCE);
        List<Trainer_Recipe> recipes =
                level.getRecipeManager()
                        .getAllRecipesFor(ModRecipes.TRAINER_TYPE.get());
        //registration.addRecipes(TRAINER_TYPE, recipesTrainer);
        registration.addRecipes(
                new RecipeType<>(TrainerRecipeCategory.UID, Trainer_Recipe.class),
                recipes
        );

    }
    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration){
        var egg = new ItemStack(ModItems.CHICKEN_STICK.get());
        registration.addRecipeCatalyst(egg, EggRecipeCategory.RECIPE_TYPE);

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

        registration.addRecipeClickArea(Breeder_Screen.class, 53, 30, 40, 10, JEIPlugin.BASIC_BREEDING_TYPE);
        registration.addRecipeClickArea(Soul_Extractor_Screen.class, 59, 41, 40, 10, JEIPlugin.SOUL_EXTRACTION_TYPE);
        registration.addRecipeClickArea(Trainer_Screen.class, 59, 41, 40, 10, JEIPlugin.TRAINER_TYPE);
        registration.addRecipeClickArea(Roost_Screen.class, 59, 41, 40, 10, JEIPlugin.ROOST_TYPE);

	}
}