package net.tarantel.chickenroost.api.emi;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.item.ModItems;
import net.tarantel.chickenroost.recipes.*;

@EmiEntrypoint
public class EmiRoostPlugin implements EmiPlugin {
    public static final EmiStack EGG_WORKSTATION = EmiStack.of(ModItems.CHICKEN_STICK);
    public static final EmiStack BREEDER_WORKSTATION = EmiStack.of(ModBlocks.BREEDER);
    public static final EmiStack ROOST_WORKSTATION = EmiStack.of(ModBlocks.ROOST);
    public static final EmiStack TRAINER_WORKSTATION = EmiStack.of(ModBlocks.TRAINER);
    public static final EmiStack SOUL_EXTRACTOR_WORKSTATION = EmiStack.of(ModBlocks.SOUL_EXTRACTOR);



    public static final EmiRecipeCategory EGG_CATEGORY
            = new EmiRecipeCategory(EGG_WORKSTATION.getId(), EGG_WORKSTATION);
    public static final EmiRecipeCategory BREEDER_CATEGORY
            = new EmiRecipeCategory(BREEDER_WORKSTATION.getId(), BREEDER_WORKSTATION);
    public static final EmiRecipeCategory ROOST_CATEGORY
            = new EmiRecipeCategory(ROOST_WORKSTATION.getId(), ROOST_WORKSTATION);
    public static final EmiRecipeCategory TRAINER_CATEGORY
            = new EmiRecipeCategory(TRAINER_WORKSTATION.getId(), TRAINER_WORKSTATION);
    public static final EmiRecipeCategory SOUL_EXTRACTOR_CATEGORY
            = new EmiRecipeCategory(SOUL_EXTRACTOR_WORKSTATION.getId(), SOUL_EXTRACTOR_WORKSTATION);

    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(EGG_CATEGORY);
        registry.addCategory(BREEDER_CATEGORY);
        registry.addCategory(ROOST_CATEGORY);
        registry.addCategory(TRAINER_CATEGORY);
        registry.addCategory(SOUL_EXTRACTOR_CATEGORY);


        registry.addWorkstation(EGG_CATEGORY, EGG_WORKSTATION);
        registry.addWorkstation(BREEDER_CATEGORY, BREEDER_WORKSTATION);
        registry.addWorkstation(ROOST_CATEGORY, ROOST_WORKSTATION);
        registry.addWorkstation(TRAINER_CATEGORY, TRAINER_WORKSTATION);
        registry.addWorkstation(SOUL_EXTRACTOR_CATEGORY, SOUL_EXTRACTOR_WORKSTATION);


        RecipeManager manager = registry.getRecipeManager();
        for (RecipeHolder<ThrowEggRecipe> egg : manager.getAllRecipesFor(ModRecipes.THROW_EGG_TYPE.get())) {
            registry.addRecipe(new EmiEggRecipe(egg.value()));
        }
        for (RecipeHolder<BreederRecipe> breeder : manager.getAllRecipesFor(ModRecipes.BASIC_BREEDING_TYPE.get())) {
            registry.addRecipe(new EmiBreederRecipe(breeder.value()));
        }
        for (RecipeHolder<RoostRecipe> roost : manager.getAllRecipesFor(ModRecipes.ROOST_TYPE.get())) {
            registry.addRecipe(new EmiRoostRecipe(roost.value()));
        }
        for (RecipeHolder<TrainerRecipe> trainer : manager.getAllRecipesFor(ModRecipes.TRAINER_TYPE.get())) {
            registry.addRecipe(new EmiTrainerRecipe(trainer.value()));
        }
        for (RecipeHolder<SoulExtractorRecipe> soul : manager.getAllRecipesFor(ModRecipes.SOUL_EXTRACTION_TYPE.get())) {
            registry.addRecipe(new EmiSoulExtractorRecipe(soul.value()));
        }

    }
}