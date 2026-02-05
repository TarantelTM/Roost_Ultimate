//package net.tarantel.chickenroost.api.jei;
//
//import mezz.jei.api.constants.VanillaTypes;
//import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
//import mezz.jei.api.gui.drawable.IDrawable;
//import mezz.jei.api.gui.drawable.IDrawableAnimated;
//import mezz.jei.api.gui.drawable.IDrawableStatic;
//import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
//import mezz.jei.api.helpers.IGuiHelper;
//import mezz.jei.api.recipe.IFocusGroup;
//import mezz.jei.api.recipe.RecipeIngredientRole;
//import mezz.jei.api.recipe.RecipeType;
//import mezz.jei.api.recipe.category.IRecipeCategory;
//import mezz.jei.api.recipe.types.IRecipeHolderType;
//import net.minecraft.client.gui.GuiGraphics;
//import net.minecraft.network.chat.Component;
//import net.minecraft.resources.Identifier;
//
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.crafting.RecipeHolder;
//import net.tarantel.chickenroost.ChickenRoostMod;
//import net.tarantel.chickenroost.block.blocks.ModBlocks;
//import net.tarantel.chickenroost.item.ModItems;
//import net.tarantel.chickenroost.recipes.BreederRecipe;
//import net.tarantel.chickenroost.recipes.ModRecipes;
//import net.tarantel.chickenroost.recipes.ThrowEggRecipe;
//import org.jetbrains.annotations.NotNull;
//@SuppressWarnings("ALL")
//public class BreederRecipeCategory implements IRecipeCategory<RecipeHolder<BreederRecipe>> {
//    public final static Identifier ARROWBACK = ChickenRoostMod.ownresource("textures/screens/arrowback.png");
//    public final static Identifier ARROW = ChickenRoostMod.ownresource( "textures/screens/arrow.png");
//    public static final IRecipeHolderType<BreederRecipe> TYPE = IRecipeHolderType.create(ModRecipes.BASIC_BREEDING_TYPE.get());
//    private final IDrawable background;
//    private final IDrawable icon;
//    private final IDrawableAnimated progressArrow;
//    private final IGuiHelper guiHelper;
//    private final IDrawableStatic arrowbacki;
//
//
//    public BreederRecipeCategory(IGuiHelper helper) {
//        this.guiHelper = helper;
//        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.BREEDER));
//        IDrawableStatic progressDrawable = helper.drawableBuilder(ARROW, 0, 10, 38, 10).setTextureSize(38, 10).addPadding(4,0,60,0).build();
//        this.arrowbacki = helper.drawableBuilder(ARROWBACK, 0, 10, 38, 10).setTextureSize(38, 10).addPadding(4,0,60,0).build();
//        this.progressArrow = helper.createAnimatedDrawable(progressDrawable, 100, IDrawableAnimated.StartDirection.LEFT,
//                false);
//        this.background = helper.createBlankDrawable(140, 16);
//
//
//    }
//
//    @Override
//    public IRecipeHolderType<BreederRecipe> getRecipeType() {
//        return TYPE;
//    }
//
//    @Override
//    public Component getTitle() {
//        return Component.literal("Basic Breeding");
//    }
//
//    @Override
//    public int getWidth() {
//        return this.background.getWidth();
//    }
//
//    @Override
//    public int getHeight() {
//        return this.background.getHeight();
//    }
//
//    @Override
//    public IDrawable getIcon() {
//        return icon;
//    }
//
//    @Override
//    public void draw(RecipeHolder<BreederRecipe> recipe, IRecipeSlotsView iRecipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
//        arrowbacki.draw(guiGraphics, 60, 4);
//        progressArrow.draw(guiGraphics, 60, 4);
//    }
//
//
//    @Override
//    public void setRecipe(IRecipeLayoutBuilder iRecipeLayoutBuilder, RecipeHolder<BreederRecipe> recipe, IFocusGroup iFocusGroup) {
//        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT, 21, 1)
//                .setBackground(guiHelper.getSlotDrawable(), -1, -1)
//                .add(recipe.value().getIngredients().get(0));
//        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT, 0, 1)
//                .setBackground(guiHelper.getSlotDrawable(), -1, -1)
//                .add(recipe.value().getIngredients().get(1));
//        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT, 41, 1)
//                .setBackground(guiHelper.getSlotDrawable(), -1, -1)
//                .add(recipe.value().getIngredients().get(2));
//
//        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.OUTPUT, 101, 1)
//                .setBackground(guiHelper.getSlotDrawable(), -1, -1).add(recipe.value().getResultItem(null));
//    }
//}