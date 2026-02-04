package net.tarantel.chickenroost.api.jei;

import java.util.ArrayList;
import java.util.List;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.item.ModItems;
import net.tarantel.chickenroost.recipes.BreederRecipe;
import net.tarantel.chickenroost.recipes.ModRecipes;
import net.tarantel.chickenroost.recipes.RoostRecipe;
import net.tarantel.chickenroost.recipes.SoulExtractorRecipe;
import net.tarantel.chickenroost.recipes.ThrowEggRecipe;
import net.tarantel.chickenroost.recipes.TrainerRecipe;
import net.tarantel.chickenroost.screen.BreederScreen;
import net.tarantel.chickenroost.screen.RoostScreen;
import net.tarantel.chickenroost.screen.SoulExtractorScreen;
import net.tarantel.chickenroost.screen.TrainerScreen;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
   public static mezz.jei.api.recipe.RecipeType<ThrowEggRecipe> EGG_TYPE = new mezz.jei.api.recipe.RecipeType(EggRecipeCategory.UID, ThrowEggRecipe.class);
   public static mezz.jei.api.recipe.RecipeType<BreederRecipe> BASIC_BREEDING_TYPE = new mezz.jei.api.recipe.RecipeType(
      BreederRecipeCategory.UID, BreederRecipe.class
   );
   public static mezz.jei.api.recipe.RecipeType<SoulExtractorRecipe> SOUL_EXTRACTION_TYPE = new mezz.jei.api.recipe.RecipeType(
      SoulExtractionRecipeCategory.UID, SoulExtractorRecipe.class
   );
   public static mezz.jei.api.recipe.RecipeType<RoostRecipe> ROOST_TYPE = new mezz.jei.api.recipe.RecipeType(RoostRecipeCategory.UID, RoostRecipe.class);
   public static mezz.jei.api.recipe.RecipeType<TrainerRecipe> TRAINER_TYPE = new mezz.jei.api.recipe.RecipeType(TrainerRecipeCategory.UID, TrainerRecipe.class);

   public ResourceLocation getPluginUid() {
      return ChickenRoostMod.ownresource("jei_plugin");
   }

   public void registerCategories(IRecipeCategoryRegistration registration) {
      IJeiHelpers jeiHelpers = registration.getJeiHelpers();
      registration.addRecipeCategories(new IRecipeCategory[]{new EggRecipeCategory(jeiHelpers.getGuiHelper())});
      registration.addRecipeCategories(new IRecipeCategory[]{new BreederRecipeCategory(jeiHelpers.getGuiHelper())});
      registration.addRecipeCategories(new IRecipeCategory[]{new SoulExtractionRecipeCategory(jeiHelpers.getGuiHelper())});
      registration.addRecipeCategories(new IRecipeCategory[]{new RoostRecipeCategory(jeiHelpers.getGuiHelper())});
      registration.addRecipeCategories(new IRecipeCategory[]{new TrainerRecipeCategory(jeiHelpers.getGuiHelper())});
   }

   public void registerRecipes(@NotNull IRecipeRegistration registration) {
      ClientLevel world = Minecraft.getInstance().level;
      if (world != null) {
         RecipeManager manager = world.getRecipeManager();
         registration.addRecipes(EggRecipeCategory.RECIPE_TYPE, this.getRecipe(manager, ModRecipes.THROW_EGG_TYPE.get()));
         registration.addRecipes(BreederRecipeCategory.RECIPE_TYPE, this.getRecipe(manager, ModRecipes.BASIC_BREEDING_TYPE.get()));
         registration.addRecipes(SoulExtractionRecipeCategory.RECIPE_TYPE, this.getRecipe(manager, ModRecipes.SOUL_EXTRACTION_TYPE.get()));
         registration.addRecipes(RoostRecipeCategory.RECIPE_TYPE, this.getRecipe(manager, ModRecipes.ROOST_TYPE.get()));
         registration.addRecipes(TrainerRecipeCategory.RECIPE_TYPE, this.getRecipe(manager, ModRecipes.TRAINER_TYPE.get()));
      }
   }

   public <C extends RecipeInput, T extends Recipe<C>> List<T> getRecipe(RecipeManager manager, RecipeType<T> recipeType) {
      List<T> list = new ArrayList<>();
      manager.getAllRecipesFor(recipeType).forEach(tRecipeHolder -> list.add((T)tRecipeHolder.value()));
      return list;
   }

   public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
      ItemStack egg = new ItemStack((ItemLike)ModItems.CHICKEN_STICK.get());
      registration.addRecipeCatalyst(egg, new mezz.jei.api.recipe.RecipeType[]{EggRecipeCategory.RECIPE_TYPE});
      ItemStack basicbreeder = new ItemStack((ItemLike)ModBlocks.BREEDER.get());
      registration.addRecipeCatalyst(basicbreeder, new mezz.jei.api.recipe.RecipeType[]{BreederRecipeCategory.RECIPE_TYPE});
      ItemStack soulextractor = new ItemStack((ItemLike)ModBlocks.SOUL_EXTRACTOR.get());
      registration.addRecipeCatalyst(soulextractor, new mezz.jei.api.recipe.RecipeType[]{SoulExtractionRecipeCategory.RECIPE_TYPE});
      ItemStack roostyv1 = new ItemStack((ItemLike)ModBlocks.ROOST.get());
      registration.addRecipeCatalyst(roostyv1, new mezz.jei.api.recipe.RecipeType[]{RoostRecipeCategory.RECIPE_TYPE});
      ItemStack trainer = new ItemStack((ItemLike)ModBlocks.TRAINER.get());
      registration.addRecipeCatalyst(trainer, new mezz.jei.api.recipe.RecipeType[]{TrainerRecipeCategory.RECIPE_TYPE});
   }

   public void registerGuiHandlers(IGuiHandlerRegistration registration) {
      registration.addRecipeClickArea(BreederScreen.class, 53, 41, 40, 10, new mezz.jei.api.recipe.RecipeType[]{BASIC_BREEDING_TYPE});
      registration.addRecipeClickArea(SoulExtractorScreen.class, 59, 41, 40, 10, new mezz.jei.api.recipe.RecipeType[]{SOUL_EXTRACTION_TYPE});
      registration.addRecipeClickArea(TrainerScreen.class, 59, 41, 40, 10, new mezz.jei.api.recipe.RecipeType[]{TRAINER_TYPE});
      registration.addRecipeClickArea(RoostScreen.class, 59, 25, 40, 10, new mezz.jei.api.recipe.RecipeType[]{ROOST_TYPE});
   }
}
