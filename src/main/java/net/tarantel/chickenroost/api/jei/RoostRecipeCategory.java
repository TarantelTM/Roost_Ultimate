package net.tarantel.chickenroost.api.jei;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.drawable.IDrawableAnimated.StartDirection;
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
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.recipes.RoostRecipe;
import org.jetbrains.annotations.NotNull;

public class RoostRecipeCategory implements IRecipeCategory<RoostRecipe> {
   public static final ResourceLocation UID = ChickenRoostMod.ownresource("roost_output");
   public static final ResourceLocation ARROWBACK = ChickenRoostMod.ownresource("textures/screens/arrowback.png");
   public static final ResourceLocation SLOT = ChickenRoostMod.ownresource("textures/screens/slot.png");
   public static final RecipeType<RoostRecipe> RECIPE_TYPE = RecipeType.create("chicken_roost", "roost_output", RoostRecipe.class);
   private final IDrawable background;
   private final IDrawable icon;
   private final IDrawableAnimated progress;
   private final IDrawableStatic slot_1;
   private final IDrawableStatic slot_2;
   private final IDrawableStatic slot_3;
   private final IDrawableStatic arrowbacki;

   public RoostRecipeCategory(IGuiHelper helper) {
      ResourceLocation ARROW = ChickenRoostMod.ownresource("textures/screens/arrow.png");
      this.background = helper.createBlankDrawable(130, 20);
      this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack((ItemLike)ModBlocks.ROOST.get()));
      IDrawableStatic progressDrawable = helper.drawableBuilder(ARROW, 0, 10, 38, 10).setTextureSize(38, 10).addPadding(4, 0, 47, 0).build();
      this.slot_1 = helper.drawableBuilder(SLOT, 0, 18, 18, 18).setTextureSize(18, 18).addPadding(0, 0, 0, 0).build();
      this.slot_2 = helper.drawableBuilder(SLOT, 0, 18, 18, 18).setTextureSize(18, 18).addPadding(0, 0, 24, 0).build();
      this.slot_3 = helper.drawableBuilder(SLOT, 0, 18, 18, 18).setTextureSize(18, 18).addPadding(0, 0, 90, 0).build();
      this.arrowbacki = helper.drawableBuilder(ARROWBACK, 0, 10, 38, 10).setTextureSize(38, 10).addPadding(4, 0, 47, 0).build();
      this.progress = helper.createAnimatedDrawable(progressDrawable, 100, StartDirection.LEFT, false);
   }

   public RecipeType<RoostRecipe> getRecipeType() {
      return JEIPlugin.ROOST_TYPE;
   }

   public Component getTitle() {
      return Component.literal("Roost");
   }

   public IDrawable getBackground() {
      return this.background;
   }

   public void draw(RoostRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
      this.slot_1.draw(guiGraphics);
      this.slot_2.draw(guiGraphics);
      this.slot_3.draw(guiGraphics);
      this.arrowbacki.draw(guiGraphics);
      this.progress.draw(guiGraphics);
   }

   public IDrawable getIcon() {
      return this.icon;
   }

   public void setRecipe(@NotNull IRecipeLayoutBuilder builder, RoostRecipe recipe, @NotNull IFocusGroup focuses) {
      builder.addSlot(RecipeIngredientRole.INPUT, 1, 1).addIngredients((Ingredient)recipe.getIngredients().get(0));
      builder.addSlot(RecipeIngredientRole.INPUT, 25, 1).addIngredients((Ingredient)recipe.getIngredients().get(1));
      builder.addSlot(RecipeIngredientRole.OUTPUT, 91, 1).addItemStack(recipe.output());
   }
}
