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
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.item.ModItems;
import net.tarantel.chickenroost.recipes.*;
import net.tarantel.chickenroost.screen.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    public static mezz.jei.api.recipe.RecipeType<ThrowEggRecipe> EGG_TYPE =
            new mezz.jei.api.recipe.RecipeType<>(EggRecipeCategory.UID, ThrowEggRecipe.class);
    public static mezz.jei.api.recipe.RecipeType<BreederRecipe> BASIC_BREEDING_TYPE =
            new mezz.jei.api.recipe.RecipeType<>(BreederRecipeCategory.UID, BreederRecipe.class);
    public static mezz.jei.api.recipe.RecipeType<SoulExtractorRecipe> SOUL_EXTRACTION_TYPE =
            new mezz.jei.api.recipe.RecipeType<>(SoulExtractionRecipeCategory.UID, SoulExtractorRecipe.class);

    public static mezz.jei.api.recipe.RecipeType<RoostRecipe> ROOST_TYPE =
            new mezz.jei.api.recipe.RecipeType<>(RoostRecipeCategory.UID, RoostRecipe.class);

    public static mezz.jei.api.recipe.RecipeType<TrainerRecipe> TRAINER_TYPE =
            new mezz.jei.api.recipe.RecipeType<>(TrainerRecipeCategory.UID, TrainerRecipe.class);


    @Override
    public ResourceLocation getPluginUid() {
        return ChickenRoostMod.ownresource("jei_plugin");
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
    public void registerRecipes(@NotNull IRecipeRegistration registration) {
        var world = Minecraft.getInstance().level;
        if (world != null) {
            var manager = world.getRecipeManager();
            registration.addRecipes(EggRecipeCategory.RECIPE_TYPE,
                    getRecipe(manager, ModRecipes.THROW_EGG_TYPE.get()));
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
        registration.addRecipeClickArea(BreederScreen.class, 53, 41, 40, 10, JEIPlugin.BASIC_BREEDING_TYPE);
        registration.addRecipeClickArea(SoulExtractorScreen.class, 59, 41, 40, 10, JEIPlugin.SOUL_EXTRACTION_TYPE);
        registration.addRecipeClickArea(TrainerScreen.class, 59, 41, 40, 10, JEIPlugin.TRAINER_TYPE);
        registration.addRecipeClickArea(RoostScreen.class, 59, 25, 40, 10, JEIPlugin.ROOST_TYPE);

	}
}