///package net.tarantel.chickenroost.block.tile.render;
///
///import net.minecraft.advancements.critereon.InventoryChangeTrigger;
///import net.minecraft.data.PackOutput;
///import net.minecraft.data.recipes.FinishedRecipe;
///import net.minecraft.data.recipes.RecipeCategory;
///import net.minecraft.data.recipes.RecipeProvider;
///import net.minecraft.data.recipes.ShapedRecipeBuilder;
///import net.minecraft.world.item.Items;
///import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
///import net.tarantel.chickenroost.item.ModItems;
///
///import java.util.function.Consumer;
///
///public class afafa extends RecipeProvider implements IConditionBuilder {
///
///    public afafa(PackOutput pOutput) {
///        super(pOutput);
///    }
///
///    @Override
///    protected void buildRecipes(Consumer<FinishedRecipe> recipeConsumer) {
///        addTools(recipeConsumer);
///
///    }
///
///    private void addTools(Consumer<FinishedRecipe> recipeConsumer) {
///        ShapedRecipeBuilder
///                .shaped(RecipeCategory.TOOLS, ModItems.CHICKEN_SCANNER.get())
///                .define('I', Items.COAL)
///                .define('G', ModItems.CHICKEN_ESSENCE_TIER_1.get())
///                .pattern("I I")
///                .pattern(" G ")
///                .pattern(" I ")
///                .unlockedBy("has_ingredient", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.SOUL_BREEDER.get()))
///                .save(recipeConsumer);
///    }
///}