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
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.crafting.RecipeHolder;
//import net.tarantel.chickenroost.ChickenRoostMod;
//import net.tarantel.chickenroost.block.blocks.ModBlocks;
//import net.tarantel.chickenroost.recipes.BreederRecipe;
//import net.tarantel.chickenroost.recipes.ModRecipes;
//import net.tarantel.chickenroost.recipes.TrainerRecipe;
//import org.jetbrains.annotations.NotNull;
//@SuppressWarnings("deprecation")
//public class TrainerRecipeCategory implements IRecipeCategory<RecipeHolder<TrainerRecipe>> {
//    public final static Identifier UID = ChickenRoostMod.ownresource("trainer_output");
//    public final static Identifier ARROWBACK = ChickenRoostMod.ownresource("textures/screens/arrowback.png");
//    public final static Identifier SLOT = ChickenRoostMod.ownresource("textures/screens/slot.png");
//    public static final RecipeType<TrainerRecipe> RECIPE_TYPE = RecipeType.create(ChickenRoostMod.MODID, "trainer_output", TrainerRecipe.class);
//    public static final IRecipeHolderType<TrainerRecipe> TYPE = IRecipeHolderType.create(ModRecipes.TRAINER_TYPE.get());
//
//    private final IDrawable background;
//    private final IDrawable icon;
//    private final IDrawableAnimated progress;
//
//
//    private final IDrawableStatic slot_2;
//    private final IDrawableStatic slot_3;
//    private final IDrawableStatic arrowbacki;
//
//    public TrainerRecipeCategory(IGuiHelper helper) {
//        Identifier ARROW = ChickenRoostMod.ownresource("textures/screens/arrow.png");
//        this.background = helper.createBlankDrawable(130, 20);
//        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.TRAINER));
//
//        IDrawableStatic progressDrawable = helper.drawableBuilder(ARROW, 0, 10, 38, 10).setTextureSize(38, 10).addPadding(4,0,47,0).build();
//        this.slot_2 = helper.drawableBuilder(SLOT, 0, 18, 18, 18).setTextureSize(18, 18).addPadding(0,0,24,0).build();
//        this.slot_3 = helper.drawableBuilder(SLOT, 0, 18, 18, 18).setTextureSize(18, 18).addPadding(0,0,90,0).build();
//        this.arrowbacki = helper.drawableBuilder(ARROWBACK, 0, 10, 38, 10).setTextureSize(38, 10).addPadding(4,0,47,0).build();
//
//        this.progress = helper.createAnimatedDrawable(progressDrawable, 100, IDrawableAnimated.StartDirection.LEFT,
//                false);
//
//
//    }
//
//    @Override
//    public IRecipeHolderType<TrainerRecipe> getRecipeType() {
//        return TYPE;
//    }
//
//    @Override
//    public Component getTitle() {
//        return Component.literal("Trainer");
//    }
//
//    @Override
//    public int getWidth() {
//        return background.getWidth();
//    }
//
//    @Override
//    public int getHeight() {
//        return background.getHeight();
//    }
//
//    @Override
//    public IDrawable getIcon() {
//        return icon;
//    }
//
//    @Override
//    public void draw(RecipeHolder<TrainerRecipe> recipe, IRecipeSlotsView iRecipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
//
//        this.slot_2.draw(guiGraphics);
//        this.slot_3.draw(guiGraphics);
//        this.arrowbacki.draw(guiGraphics);
//        this.progress.draw(guiGraphics);
//    }
//
//    @Override
//    public void setRecipe(IRecipeLayoutBuilder iRecipeLayoutBuilder, RecipeHolder<TrainerRecipe> recipe, IFocusGroup iFocusGroup) {
//        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT, 91, 1)
//                .addIngredients(recipe.value().getIngredients().get(0));
//
//        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.OUTPUT, 25, 1).addItemStack(recipe.value().output());
//    }
//}