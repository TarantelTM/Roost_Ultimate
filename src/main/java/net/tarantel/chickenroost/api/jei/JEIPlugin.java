package net.tarantel.chickenroost.api.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.recipes.*;
import net.tarantel.chickenroost.screen.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    public static mezz.jei.api.recipe.RecipeType<Soul_Breeder_Recipe> BREEDING_TYPE =
            new mezz.jei.api.recipe.RecipeType<>(SoulBreedingRecipeCategory.UID, Soul_Breeder_Recipe.class);
    public static mezz.jei.api.recipe.RecipeType<Breeder_Recipe> BASIC_BREEDING_TYPE =
            new mezz.jei.api.recipe.RecipeType<>(BreederRecipeCategory.UID, Breeder_Recipe.class);
    public static mezz.jei.api.recipe.RecipeType<Soul_Extractor_Recipe> SOUL_EXTRACTION_TYPE =
            new mezz.jei.api.recipe.RecipeType<>(SoulExtractionRecipeCategory.UID, Soul_Extractor_Recipe.class);

    public static mezz.jei.api.recipe.RecipeType<Roost_Recipe> ROOST_TYPE =
            new mezz.jei.api.recipe.RecipeType<>(RoostRecipeCategory.UID, Roost_Recipe.class);

    public static mezz.jei.api.recipe.RecipeType<Trainer_Recipe> TRAINER_TYPE =
            new mezz.jei.api.recipe.RecipeType<>(TrainerRecipeCategory.UID, Trainer_Recipe.class);


    @Override
    public ResourceLocation getPluginUid() {
        return ChickenRoostMod.ownresource("jei_plugin");
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
    public void registerRecipes(@NotNull IRecipeRegistration registration) {
        var world = Minecraft.getInstance().level;
        if (world != null) {
            var manager = world.getRecipeManager();
            registration.addRecipes(SoulBreedingRecipeCategory.RECIPE_TYPE,
                    getRecipe(manager, ModRecipes.SOUL_BREEDING_TYPE.get()));
            registration.addRecipes(BreederRecipeCategory.RECIPE_TYPE,
                    getRecipe(manager, ModRecipes.BASIC_BREEDING_TYPE.get()));
            registration.addRecipes(SoulExtractionRecipeCategory.RECIPE_TYPE,
                    getRecipe(manager, ModRecipes.SOUL_EXTRACTION_TYPE.get()));
            registration.addRecipes(RoostRecipeCategory.RECIPE_TYPE,
                    getRecipe(manager, ModRecipes.ROOST_TYPE.get()));
            registration.addRecipes(TrainerRecipeCategory.RECIPE_TYPE,
                    getRecipe(manager, ModRecipes.TRAINER_TYPE.get()));

        }

    }

    public <C extends RecipeInput, T extends Recipe<C>> List<T> getRecipe(RecipeManager manager, RecipeType<T> recipeType){
        List<T> list = new ArrayList<>();
        manager.getAllRecipesFor(recipeType).forEach(tRecipeHolder -> {
            list.add(tRecipeHolder.value());
        });
        return list;
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
        registration.addRecipeClickArea(Soul_Breeder_Screen.class, 59, 41, 40, 10, JEIPlugin.BREEDING_TYPE);
        registration.addRecipeClickArea(Breeder_Screen.class, 53, 30, 40, 10, JEIPlugin.BASIC_BREEDING_TYPE);
        registration.addRecipeClickArea(Soul_Extractor_Screen.class, 59, 41, 40, 10, JEIPlugin.SOUL_EXTRACTION_TYPE);
        registration.addRecipeClickArea(Trainer_Screen.class, 59, 41, 40, 10, JEIPlugin.TRAINER_TYPE);
        registration.addRecipeClickArea(Roost_Screen.class, 59, 41, 40, 10, JEIPlugin.ROOST_TYPE);

	}
}