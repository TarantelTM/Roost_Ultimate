package net.tarantel.chickenroost.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.gui.widget.ScrollPanel;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.recipes.*;
import net.tarantel.chickenroost.util.Config;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Guide_Screen extends Screen {
    private List<Button> menuButtons = new ArrayList<>(); // Liste der Menübuttons
    private String currentContent = ""; // Aktueller Inhalt der Contentbox
    private List<? extends Recipe<?>> recipes; // Liste der Rezepte (generisch)
    private int scrollOffset = 0; // Scroll-Offset
    private RecipeType<?> currentRecipeType; // Aktueller Rezepttyp
    //private SimpleContainer slotContainer; // Container for the slot
    //private CustomSlot slot; // The slot instance


    public Guide_Screen() {
        super(Component.literal(""));
        //this.slotContainer = new SimpleContainer(4);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public int customindex = 99;

    @Override
    protected void init() {
        // Create menu entries on the left side
        int menuWidth = 150; // Width of the menu bar
        int menuMargin = 10; // Margin from the edge
        int buttonHeight = 20; // Height of each button
        int buttonSpacing = 5; // Spacing between buttons

        String[] menuItems = {
                "Start",
                "Roost Recipes",
                "Breeder Recipes",
                "Soul Extractor",
                "Trainer",
                "Colored Chicken"
        };

        for (int i = 0; i < menuItems.length; i++) {
            int buttonY = menuMargin + i * (buttonHeight + buttonSpacing); // Y position for each button
            int menuIndex = i; // Local copy of the loop variable, effectively final
            Button button = Button.builder(Component.literal(menuItems[i]), buttonWidget -> onMenuButtonClick(menuIndex))
                    .bounds(menuMargin, buttonY, menuWidth - 2 * menuMargin, buttonHeight)
                    .build();
            menuButtons.add(button);
            this.addRenderableWidget(button); // Add button to the screen
        }

        // Close button at the bottom right
        int closeButtonWidth = 100;
        int closeButtonHeight = 20;
        int closeMargin = 10;

        int closeX = this.width - closeButtonWidth - closeMargin;
        int closeY = this.height - closeButtonHeight - closeMargin;

        this.addRenderableWidget(Button.builder(Component.literal("Close"), button -> this.onClose())
                .bounds(closeX, closeY, closeButtonWidth, closeButtonHeight)
                .build());

        // Add scroll buttons
        int scrollButtonWidth = 20;
        int scrollButtonHeight = 20;
        int scrollButtonX = this.width - scrollButtonWidth - 10; // Top right
        int scrollButtonY = 10;

        // Scroll-Up button
        this.addRenderableWidget(Button.builder(Component.literal("↑"), button -> scrollUp())
                .bounds(scrollButtonX, scrollButtonY, scrollButtonWidth, scrollButtonHeight)
                .build());

        // Scroll-Down button
        this.addRenderableWidget(Button.builder(Component.literal("↓"), button -> scrollDown())
                .bounds(scrollButtonX, scrollButtonY + scrollButtonHeight + 5, scrollButtonWidth, scrollButtonHeight)
                .build());

        // Select the Start menu item by default
        onMenuButtonClick(0);
    }

    @Override
    public void renderBackground(@NotNull GuiGraphics poseStack) {
    //public void renderBackground(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        /*if (this.minecraft.level == null) {
            this.renderPanorama(poseStack);
        }*/
    }

    private static final ResourceLocation SLOT = new ResourceLocation(ChickenRoostMod.MODID,"textures/screens/slot.png");
    private static final ResourceLocation ARROW = new ResourceLocation(ChickenRoostMod.MODID,"textures/screens/arrow.png");

    private boolean isHovering(int x, int y, int width, int height, int mouseX, int mouseY) {
        return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics); // Render the background
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 40, 0xFFFFFF); // Draw the title
        // Render tooltips for all slots

        // Content box on the right side
        int contentBoxX = 160; // X position of the content box
        int contentBoxY = 10; // Y position of the content box
        int contentBoxWidth = this.width - 180; // Width of the content box
        int contentBoxHeight = this.height - 60; // Height of the content box

        // Draw the content box background and border
        guiGraphics.fill(contentBoxX, contentBoxY, contentBoxX + contentBoxWidth, contentBoxY + contentBoxHeight, 0xFF000000); // Black background
        guiGraphics.renderOutline(contentBoxX, contentBoxY, contentBoxWidth, contentBoxHeight, 0xFFFFFFFF); // White border

        // Draw content inside the content box
        guiGraphics.drawString(this.font, currentContent, contentBoxX + 10, contentBoxY + 10, 0xFFFFFF); // Text inside the content box

        // Render scrollable content
        int startY = contentBoxY + 30; // Start Y position for recipes
        int slotSpacing = 30; // Spacing between recipes

        // Enable scissor test to clip content to the content box
        guiGraphics.enableScissor(contentBoxX, contentBoxY + 25, contentBoxX + contentBoxWidth, contentBoxY + contentBoxHeight - 5);

        if (currentRecipeType != null) {
            for (int i = 0; i < recipes.size(); i++) {
                Recipe<?> recipe = recipes.get(i);

                // Number of ingredients in the recipe
                int ingredientCount = recipe.getIngredients().size();

                // Slot backgrounds
                int slotX1 = contentBoxX + 10;
                int slotX2 = slotX1 + (ingredientCount > 1 ? 40 : 0); // Shift if more than one ingredient
                int slotX3 = slotX2 + (ingredientCount > 2 ? 40 : 0); // Shift if more than two ingredients
                int outputSlotX = slotX3 + 40 + (ingredientCount > 2 ? 60 : 40); // Output slot position based on ingredient count
                int slotY = startY + i * slotSpacing - scrollOffset;

                // Draw light gray background behind slots and arrow
                int backgroundWidth = outputSlotX + 20 - slotX1; // Background width based on output slot position
                guiGraphics.fill(slotX1 - 5, slotY - 5, slotX1 + backgroundWidth + 5, slotY + 25, 0x80AAAAAA); // Gray background

                // Swap Input 1 and Input 2 for Roost and Breeder recipes
                if (currentRecipeType == ModRecipes.ROOST_TYPE.get() || currentRecipeType == ModRecipes.BASIC_BREEDING_TYPE.get()) {
                    int temp = slotX1;
                    slotX1 = slotX2;
                    slotX2 = temp;
                }

                // Swap Input 1 and Output for Trainer recipes
                if (currentRecipeType == ModRecipes.TRAINER_TYPE.get()) {
                    int temp = slotX1;
                    slotX1 = outputSlotX;
                    outputSlotX = temp;
                }

                // Input 1
                ItemStack input1 = recipe.getIngredients().get(0).getItems()[0];
                if (!input1.isEmpty()) {
                    if (!input1.isEmpty()) {
                        guiGraphics.renderItem(input1, slotX1, slotY);
                    }
                }

                // Input 2 (if present)
                ItemStack input2 = ItemStack.EMPTY;
                if (ingredientCount > 1) {
                    input2 = recipe.getIngredients().get(1).getItems()[0];
                    if (!input2.isEmpty()) {
                        guiGraphics.renderItem(input2, slotX2, slotY);
                    }
                }

                // Input 3 (if present)
                ItemStack input3 = ItemStack.EMPTY;
                if (ingredientCount > 2) {
                    input3 = recipe.getIngredients().get(2).getItems()[0];
                    if (!input3.isEmpty()) {
                        guiGraphics.renderItem(input3, slotX3, slotY);
                    }
                }

                // Arrow between inputs and output
                int arrowX = slotX3 + (ingredientCount > 2 ? 40 : 20); // Arrow position based on ingredient count
                int arrowY = slotY + 2;
                guiGraphics.blit(ARROW, arrowX, arrowY, 0, 0, 40, 10, 40, 10); // Draw arrow texture

                // Output
                ItemStack output = recipe.getResultItem(null); // Pass null or a valid Level
                if (!output.isEmpty()) {
                    /*CustomSlot customSlot = new CustomSlot(this.slotContainer, 3, outputSlotX, slotY); // Create the slot
                    this.addRenderableWidget(customSlot); // Add the slot to the screen
                    customSlot.set(output);*/
                    guiGraphics.renderItem(output, outputSlotX, slotY);
                }
                for (var widget : this.renderables) {
                    if (widget instanceof CustomSlot slot && slot.isHighlightable()) {
                        ItemStack stack = slot.getItem();
                        if (!stack.isEmpty()) {
                            guiGraphics.renderTooltip(this.font, stack, mouseX, mouseY);
                        }
                    }
                }
                // Tooltips for items
                if (isHovering(slotX1, slotY, 16, 16, mouseX, mouseY) && !input1.isEmpty()) {
                    guiGraphics.renderTooltip(this.font, input1, mouseX, mouseY);
                }
                if (isHovering(slotX2, slotY, 16, 16, mouseX, mouseY) && !input2.isEmpty()) {
                    guiGraphics.renderTooltip(this.font, input2, mouseX, mouseY);
                }
                if (isHovering(slotX3, slotY, 16, 16, mouseX, mouseY) && !input3.isEmpty()) {
                    guiGraphics.renderTooltip(this.font, input3, mouseX, mouseY);
                }
                if (isHovering(outputSlotX, slotY, 16, 16, mouseX, mouseY) && !output.isEmpty()) {
                    guiGraphics.renderTooltip(this.font, output, mouseX, mouseY);
                }

                // Slot background for Input 1
                guiGraphics.blit(SLOT, slotX1 - 2, slotY - 2, 0, 0, 20, 20, 20, 20);

                // Slot background for Input 2 (if present)
                if (!input2.isEmpty()) {
                    guiGraphics.blit(SLOT, slotX2 - 2, slotY - 2, 0, 0, 20, 20, 20, 20);
                }

                // Slot background for Input 3 (if present)
                if (!input3.isEmpty()) {
                    guiGraphics.blit(SLOT, slotX3 - 2, slotY - 2, 0, 0, 20, 20, 20, 20);
                }

                // Slot background for Output
                guiGraphics.blit(SLOT, outputSlotX - 2, slotY - 2, 0, 0, 20, 20, 20, 20);
            }
        }

        // Disable scissor test after rendering the content
        guiGraphics.disableScissor();

        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    private void addRenderableWidget(CustomSlot slot) {
    }

    private void shuffleArray(ItemStack[] array) {
        RandomSource random =  Minecraft.getInstance().level.random;
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            // Swap
            ItemStack temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }
    @Override
    public void onClose() {
        this.minecraft.setScreen(null);
    }

    // Methode, die aufgerufen wird, wenn ein Menübutton geklickt wird
    private void onMenuButtonClick(int menuIndex) {
        Level level = Minecraft.getInstance().player.level();
        RecipeManager recipeManager = level.getRecipeManager();
        scrollOffset = 0;
        if (level == null) return;

        switch (menuIndex) {
            case 0: // Start
                currentContent = "Welcome to the Chicken Roost Guide!";
                currentRecipeType = null;
                recipes = List.of();
                break;
            case 1: // Roost Recipes
                currentContent = "Roost Recipes";
                currentRecipeType = ModRecipes.ROOST_TYPE.get();
                recipes = recipeManager.getAllRecipesFor(Roost_Recipe.Type.INSTANCE)
                        .stream()
                        .map(recipeHolder -> recipeHolder) // Access the Recipe from RecipeHolder
                        .toList();
                break;
            case 2: // Breeder Recipes
                currentContent = "Breeder Recipes";
                currentRecipeType = ModRecipes.BASIC_BREEDING_TYPE.get();
                recipes = recipeManager.getAllRecipesFor(Breeder_Recipe.Type.INSTANCE)
                        .stream()
                        .map(recipeHolder -> recipeHolder) // Access the Recipe from RecipeHolder
                        .toList();
                break;
            case 3: // Soul Extractor
                currentContent = "Soul Extractor Recipes";
                currentRecipeType = ModRecipes.SOUL_EXTRACTION_TYPE.get();
                recipes = recipeManager.getAllRecipesFor(Soul_Extractor_Recipe.Type.INSTANCE)
                        .stream()
                        .map(recipeHolder -> recipeHolder) // Access the Recipe from RecipeHolder
                        .toList();
                break;
            case 4: // Trainer
                currentContent = "Trainer Recipes";
                currentRecipeType = ModRecipes.TRAINER_TYPE.get();
                recipes = recipeManager.getAllRecipesFor(Trainer_Recipe.Type.INSTANCE)
                        .stream()
                        .map(recipeHolder -> recipeHolder) // Access the Recipe from RecipeHolder
                        .toList();
                break;
            case 5: // Colored Chicken
                currentContent = "Colored Chicken Recipes";
                currentRecipeType = ModRecipes.THROW_EGG_TYPE.get();
                recipes = recipeManager.getAllRecipesFor(ThrowEggRecipe.Type.INSTANCE)
                        .stream()
                        .map(recipeHolder -> recipeHolder) // Access the Recipe from RecipeHolder
                        .toList();
                break;
            default:
                currentContent = "Invalid Menu";
                currentRecipeType = null;
                recipes = List.of();
                break;
        }
    }


    private void scrollUp() {
        // Nach oben scrollen
        scrollOffset = Math.max(0, scrollOffset - 30);
    }

    private void scrollDown() {
        // Nach unten scrollen
        scrollOffset += 30;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollDelta) {
        // Adjust the scroll offset based on the scroll direction
        if (scrollDelta > 0) {
            scrollUp(); // Scroll up if the scroll delta is positive
        } else if (scrollDelta < 0) {
            scrollDown(); // Scroll down if the scroll delta is negative
        }
        return true; // Return true to indicate the event was handled
    }
}