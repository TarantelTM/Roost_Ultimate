package net.tarantel.chickenroost.integration;

import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.recipes.Breeder_Recipe;
import net.tarantel.chickenroost.block.ModBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import org.jetbrains.annotations.NotNull;
@SuppressWarnings("ALL")
public class BreederRecipeCategory implements IRecipeCategory<Breeder_Recipe> {
    public final static ResourceLocation UID = new ResourceLocation(ChickenRoostMod.MODID, "basic_breeding");
    public final static ResourceLocation ARROWBACK = new ResourceLocation("chicken_roost:textures/screens/arrowback.png");
    public final static ResourceLocation SLOT = new ResourceLocation("chicken_roost:textures/screens/slot.png");
    public static final RecipeType<Breeder_Recipe> RECIPE_TYPE = RecipeType.create(ChickenRoostMod.MODID, "basic_breeding", Breeder_Recipe.class);
    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableAnimated progress;

    private final IDrawableStatic slot_1;
    private final IDrawableStatic slot_2;
    private final IDrawableStatic slot_3;
    private final IDrawableStatic arrowbacki;

    public BreederRecipeCategory(IGuiHelper helper) {
        ResourceLocation ARROW = new ResourceLocation("chicken_roost:textures/screens/arrow.png");
        this.background = helper.createBlankDrawable(130, 60);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM, new ItemStack(ModBlocks.BREEDER.get()));

        IDrawableStatic progressDrawable = helper.drawableBuilder(ARROW, 0, 10, 38, 10).setTextureSize(38, 10).addPadding(30,0,47,0).build();
        this.slot_1 = helper.drawableBuilder(SLOT, 0, 18, 18, 18).setTextureSize(18, 18).addPadding(3,0,10,0).build();
        this.slot_2 = helper.drawableBuilder(SLOT, 0, 18, 18, 18).setTextureSize(18, 18).addPadding(26,0,24,0).build();
        this.slot_3 = helper.drawableBuilder(SLOT, 0, 18, 18, 18).setTextureSize(18, 18).addPadding(26,0,90,0).build();
        this.arrowbacki = helper.drawableBuilder(ARROWBACK, 0, 10, 38, 10).setTextureSize(38, 10).addPadding(30,0,47,0).build();

        this.progress = helper.createAnimatedDrawable(progressDrawable, 100, IDrawableAnimated.StartDirection.LEFT,
                false);


    }

    @Override
    public RecipeType<Breeder_Recipe> getRecipeType() {
        return JEICompat.BASIC_BREEDING_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.nullToEmpty("Basic Breeding");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public void draw(Breeder_Recipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack guiGraphics, double mouseX,
                     double mouseY) {

        this.slot_1.draw(guiGraphics);
        this.slot_2.draw(guiGraphics);
        this.slot_3.draw(guiGraphics);
        this.arrowbacki.draw(guiGraphics);
        this.progress.draw(guiGraphics);
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends Breeder_Recipe> getRecipeClass() {
        return JEICompat.BASIC_BREEDING_TYPE.getRecipeClass();
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, Breeder_Recipe recipe, @NotNull IFocusGroup focuses) {
        //super.setRecipe(builder, recipe, focuses);
        builder.addSlot(RecipeIngredientRole.INPUT, 11, 4)
                .addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 25, 27)
                .addIngredients(recipe.getIngredients().get(1));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 91, 27).addItemStack(recipe.getResultItem());
    }
}