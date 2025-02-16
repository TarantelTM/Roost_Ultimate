package net.tarantel.chickenroost.api.jei;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.recipes.Soul_Breeder_Recipe;
import org.jetbrains.annotations.NotNull;
@SuppressWarnings("deprecation")
public class SoulBreedingRecipeCategory implements IRecipeCategory<Soul_Breeder_Recipe> {
    public final static ResourceLocation UID = new ResourceLocation(ChickenRoostMod.MODID, "soul_breeding");
    public final static ResourceLocation ARROWBACK = new ResourceLocation(ChickenRoostMod.MODID, "textures/screens/arrowback.png");
    public final static ResourceLocation SLOT = new ResourceLocation(ChickenRoostMod.MODID, "textures/screens/slot.png");
    //public static final RecipeType<Soul_Breeder_Recipe> RECIPE_TYPE = RecipeType.create(ChickenRoostMod.MODID, "soul_breeding", Soul_Breeder_Recipe.class);
    public static final RecipeType<Soul_Breeder_Recipe> RECIPE_TYPE = new RecipeType<>(UID, Soul_Breeder_Recipe.class);
    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableAnimated progress;

    private final IDrawableStatic slot_1;
    private final IDrawableStatic slot_2;
    private final IDrawableStatic slot_3;
    private final IDrawableStatic arrowbacki;

    public SoulBreedingRecipeCategory(IGuiHelper helper) {
        ResourceLocation ARROW = new ResourceLocation(ChickenRoostMod.MODID, "textures/screens/arrow.png");
        this.background = helper.createBlankDrawable(130, 20);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.SOUL_BREEDER.get()));

        IDrawableStatic progressDrawable = helper.drawableBuilder(ARROW, 0, 10, 38, 10).setTextureSize(38, 10).addPadding(4,0,47,0).build();
        this.slot_1 = helper.drawableBuilder(SLOT, 0, 18, 18, 18).setTextureSize(18, 18).addPadding(0,0,0,0).build();
        this.slot_2 = helper.drawableBuilder(SLOT, 0, 18, 18, 18).setTextureSize(18, 18).addPadding(0,0,24,0).build();
        this.slot_3 = helper.drawableBuilder(SLOT, 0, 18, 18, 18).setTextureSize(18, 18).addPadding(0,0,90,0).build();
        this.arrowbacki = helper.drawableBuilder(ARROWBACK, 0, 10, 38, 10).setTextureSize(38, 10).addPadding(4,0,47,0).build();

        this.progress = helper.createAnimatedDrawable(progressDrawable, 100, IDrawableAnimated.StartDirection.LEFT,
                false);


    }

    @Override
    public RecipeType<Soul_Breeder_Recipe> getRecipeType() {
        return JEIPlugin.BREEDING_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Soul Breeding");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public void draw(Soul_Breeder_Recipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX,
                     double mouseY) {

        this.slot_1.draw(guiGraphics);
        this.slot_2.draw(guiGraphics);
        this.slot_3.draw(guiGraphics);
        this.arrowbacki.draw(guiGraphics);
        this.progress.draw(guiGraphics);
    }
    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, Soul_Breeder_Recipe recipe, @NotNull IFocusGroup focuses) {
    	//super.setRecipe(builder, recipe, focuses);
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 1)
                .addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 25, 1)
                .addIngredients(recipe.getIngredients().get(1));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 91, 1).addItemStack(recipe.output);
    }
}