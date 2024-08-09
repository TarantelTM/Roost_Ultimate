package net.tarantel.chickenroost.api.emi;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.recipes.*;

@EmiEntrypoint
public class EmiPlugin implements dev.emi.emi.api.EmiPlugin {
    public static final EmiStack BREEDER_WORKSTATION = EmiStack.of(ModBlocks.BREEDER);
    public static final EmiStack ROOST_WORKSTATION = EmiStack.of(ModBlocks.ROOST);
    public static final EmiStack TRAINER_WORKSTATION = EmiStack.of(ModBlocks.TRAINER);
    public static final EmiStack SOUL_EXTRACTOR_WORKSTATION = EmiStack.of(ModBlocks.SOUL_EXTRACTOR);
    public static final EmiStack SOUL_BREEDER_WORKSTATION = EmiStack.of(ModBlocks.SOUL_BREEDER);


    public static final EmiRecipeCategory BREEDER_CATEGORY
            = new EmiRecipeCategory(BREEDER_WORKSTATION.getId(), BREEDER_WORKSTATION);
    public static final EmiRecipeCategory ROOST_CATEGORY
            = new EmiRecipeCategory(ROOST_WORKSTATION.getId(), ROOST_WORKSTATION);
    public static final EmiRecipeCategory TRAINER_CATEGORY
            = new EmiRecipeCategory(TRAINER_WORKSTATION.getId(), TRAINER_WORKSTATION);
    public static final EmiRecipeCategory SOUL_EXTRACTOR_CATEGORY
            = new EmiRecipeCategory(SOUL_EXTRACTOR_WORKSTATION.getId(), SOUL_EXTRACTOR_WORKSTATION);

    public static final EmiRecipeCategory SOUL_BREEDER_CATEGORY
            = new EmiRecipeCategory(SOUL_BREEDER_WORKSTATION.getId(), SOUL_BREEDER_WORKSTATION);

    @Override
    public void register(EmiRegistry registry) {
        // Tell EMI to add a tab for your category
        registry.addCategory(BREEDER_CATEGORY);
        registry.addCategory(ROOST_CATEGORY);
        registry.addCategory(TRAINER_CATEGORY);
        registry.addCategory(SOUL_EXTRACTOR_CATEGORY);
        registry.addCategory(SOUL_BREEDER_CATEGORY);

        // Add all the workstations your category uses
        registry.addWorkstation(BREEDER_CATEGORY, BREEDER_WORKSTATION);
        registry.addWorkstation(ROOST_CATEGORY, ROOST_WORKSTATION);
        registry.addWorkstation(TRAINER_CATEGORY, TRAINER_WORKSTATION);
        registry.addWorkstation(SOUL_EXTRACTOR_CATEGORY, SOUL_EXTRACTOR_WORKSTATION);
        registry.addWorkstation(SOUL_BREEDER_CATEGORY, SOUL_BREEDER_WORKSTATION);

        RecipeManager manager = registry.getRecipeManager();

        for (RecipeHolder<Breeder_Recipe> breeder : manager.getAllRecipesFor(ModRecipes.BASIC_BREEDING_TYPE.get())) {
            registry.addRecipe(new EmiBreederRecipe(breeder.value()));
        }
        for (RecipeHolder<Roost_Recipe> roost : manager.getAllRecipesFor(ModRecipes.ROOST_TYPE.get())) {
            registry.addRecipe(new EmiRoostRecipe(roost.value()));
        }
        for (RecipeHolder<Trainer_Recipe> trainer : manager.getAllRecipesFor(ModRecipes.TRAINER_TYPE.get())) {
            registry.addRecipe(new EmiTrainerRecipe(trainer.value()));
        }
        for (RecipeHolder<Soul_Extractor_Recipe> soul : manager.getAllRecipesFor(ModRecipes.SOUL_EXTRACTION_TYPE.get())) {
            registry.addRecipe(new EmiSoulExtractorRecipe(soul.value()));
        }
        for (RecipeHolder<Soul_Breeder_Recipe> soulbreed : manager.getAllRecipesFor(ModRecipes.SOUL_BREEDING_TYPE.get())) {
            registry.addRecipe(new EmiSoulBreederRecipe(soulbreed.value()));
        }
    }
}