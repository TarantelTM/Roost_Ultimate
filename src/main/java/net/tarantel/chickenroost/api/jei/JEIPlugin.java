package net.tarantel.chickenroost.api.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.item.ModItems;
import net.tarantel.chickenroost.recipes.*;
import net.tarantel.chickenroost.screen.BreederScreen;
import net.tarantel.chickenroost.screen.RoostScreen;
import net.tarantel.chickenroost.screen.SoulExtractorScreen;
import net.tarantel.chickenroost.screen.TrainerScreen;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class JEIPlugin implements IModPlugin {


    public static RecipeMap recipeMap = null;

    /*public static mezz.jei.api.recipe.RecipeType<ThrowEggRecipe> EGG_TYPE =
            new mezz.jei.api.recipe.RecipeType<>(EggRecipeCategory.UID, ThrowEggRecipe.class);
    public static mezz.jei.api.recipe.RecipeType<BreederRecipe> BASIC_BREEDING_TYPE =
            new mezz.jei.api.recipe.RecipeType<>(BreederRecipeCategory.UID, BreederRecipe.class);
    public static mezz.jei.api.recipe.RecipeType<SoulExtractorRecipe> SOUL_EXTRACTION_TYPE =
            new mezz.jei.api.recipe.RecipeType<>(SoulExtractionRecipeCategory.UID, SoulExtractorRecipe.class);

    public static mezz.jei.api.recipe.RecipeType<RoostRecipe> ROOST_TYPE =
            new mezz.jei.api.recipe.RecipeType<>(RoostRecipeCategory.UID, RoostRecipe.class);

    public static mezz.jei.api.recipe.RecipeType<TrainerRecipe> TRAINER_TYPE =
            new mezz.jei.api.recipe.RecipeType<>(TrainerRecipeCategory.UID, TrainerRecipe.class);*/


    @Override
    public Identifier getPluginUid() {
        return ChickenRoostMod.ownresource("jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
    	var jeiHelpers = registration.getJeiHelpers();

        registration.addRecipeCategories(
                new EggRecipeCategory(jeiHelpers.getGuiHelper()),
                new BreederRecipeCategory(jeiHelpers.getGuiHelper()),
                new SoulExtractionRecipeCategory(jeiHelpers.getGuiHelper()),
                new RoostRecipeCategory(jeiHelpers.getGuiHelper()),
                new TrainerRecipeCategory(jeiHelpers.getGuiHelper()));

    }


    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration) {
        if(recipeMap != null) {
            registration.addRecipes(EggRecipeCategory.TYPE, new ArrayList<>(recipeMap.byType(ModRecipes.THROW_EGG_TYPE.get())));
            registration.addRecipes(BreederRecipeCategory.TYPE, new ArrayList<>(recipeMap.byType(ModRecipes.BASIC_BREEDING_TYPE.get())));
            registration.addRecipes(SoulExtractionRecipeCategory.TYPE, new ArrayList<>(recipeMap.byType(ModRecipes.SOUL_EXTRACTION_TYPE.get())));
            registration.addRecipes(RoostRecipeCategory.TYPE, new ArrayList<>(recipeMap.byType(ModRecipes.ROOST_TYPE.get())));
            registration.addRecipes(TrainerRecipeCategory.TYPE, new ArrayList<>(recipeMap.byType(ModRecipes.TRAINER_TYPE.get())));
            /*var manager = world.level.getServer().getRecipeManager();
            registration.addRecipes(EggRecipeCategory.RECIPE_TYPE,
                    getRecipesOfType(manager, ModRecipes.THROW_EGG_TYPE.get()));
            registration.addRecipes(BreederRecipeCategory.RECIPE_TYPE,
                    getRecipesOfType(manager, ModRecipes.BASIC_BREEDING_TYPE.get()));
            registration.addRecipes(SoulExtractionRecipeCategory.RECIPE_TYPE,
                    getRecipesOfType(manager, ModRecipes.SOUL_EXTRACTION_TYPE.get()));
            registration.addRecipes(RoostRecipeCategory.RECIPE_TYPE,
                    getRecipesOfType(manager, ModRecipes.ROOST_TYPE.get()));
            registration.addRecipes(TrainerRecipeCategory.RECIPE_TYPE,
                    getRecipesOfType(manager, ModRecipes.TRAINER_TYPE.get()));*/

        }

    }

    @SuppressWarnings("unchecked")
    public static <C extends RecipeInput, T extends Recipe<C>>
    List<T> getRecipesOfType(RecipeManager manager, RecipeType<T> type) {

        return manager.getRecipes().stream()                 // Collection<RecipeHolder<?>>
                .map(h -> (RecipeHolder<?>) h)
                .map(RecipeHolder::value)                    // Recipe<?>
                .filter(r -> r.getType() == type)            // nur dein Typ
                .map(r -> (T) r)                             // cast, weil wir gefiltert haben
                .toList();
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration){


        registration.addCraftingStation(BreederRecipeCategory.TYPE, new ItemStack(ModItems.BREEDER.get()));
        registration.addCraftingStation(RoostRecipeCategory.TYPE, new ItemStack(ModItems.ROOST.get()));
        registration.addCraftingStation(SoulExtractionRecipeCategory.TYPE, new ItemStack(ModItems.EXTRACTOR.get()));
        registration.addCraftingStation(TrainerRecipeCategory.TYPE, new ItemStack(ModItems.TRAINER.get()));

        /*var egg = new ItemStack(ModItems.CHICKEN_STICK.get());
        registration.addRecipeCatalyst(egg, EggRecipeCategory.RECIPE_TYPE);
    	var basicbreeder = new ItemStack(ModBlocks.BREEDER);
    	registration.addRecipeCatalyst(basicbreeder, BreederRecipeCategory.RECIPE_TYPE);
    	var soulextractor = new ItemStack(ModBlocks.SOUL_EXTRACTOR);
    	registration.addRecipeCatalyst(soulextractor, SoulExtractionRecipeCategory.RECIPE_TYPE);

        var roostyv1 = new ItemStack(ModBlocks.ROOST);
        registration.addRecipeCatalyst(roostyv1, RoostRecipeCategory.RECIPE_TYPE);

        var trainer = new ItemStack(ModBlocks.TRAINER);
        registration.addRecipeCatalyst(trainer, TrainerRecipeCategory.RECIPE_TYPE);*/

    }

    @Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration)
	{
        registration.addRecipeClickArea(BreederScreen.class, 53, 41, 40, 10, BreederRecipeCategory.TYPE);
        registration.addRecipeClickArea(SoulExtractorScreen.class, 59, 41, 40, 10, SoulExtractionRecipeCategory.TYPE);
        registration.addRecipeClickArea(TrainerScreen.class, 59, 41, 40, 10, TrainerRecipeCategory.TYPE);
        registration.addRecipeClickArea(RoostScreen.class, 59, 25, 40, 10, RoostRecipeCategory.TYPE);

	}
}