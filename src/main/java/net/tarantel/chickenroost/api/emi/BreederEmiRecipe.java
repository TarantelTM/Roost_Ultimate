/*package net.tarantel.chickenroost.api.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.tarantel.chickenroost.recipemanager.BasicBreedingRecipe;
import org.jetbrains.annotations.Nullable;


import java.util.*;


public class BreederEmiRecipe implements EmiRecipe {

    public BreederEmiRecipe(BasicBreedingRecipe recipe) {
        this.id = recipe.getId();

        //Convert inputs to a list of EMI Ingredients
        List<EmiIngredient> convertedInputs = new ArrayList<>();
        for (Map.Entry<Ingredient, Integer> entry : recipe.getIngredientsMap().entrySet()) {
            for (int i = entry.getValue(); i > 0; ) {
                int stackCount = Math.min(i, 64);

                convertedInputs.add(
                        EmiIngredient.of(Arrays.stream(entry.getKey().getMatchingStacks())
                                .map(ItemStack::copy)
                                .peek(stack -> stack.setCount(stackCount))
                                .map(EmiStack::of)
                                .toList()));

                i -= stackCount;
            }
        }
        this.inputs = convertedInputs;

        //tier and fuel same way as REI plugin

        //initialize the starting text and stack to show in the plugin
        //this.currentTierText = Text.translatable("container.alloy_forgery.rei.min_tier", minForgeTier).asOrderedText();
        this.currentStack = EmiStack.of(recipe.getOutput());

        //set the outputs list with the initial stack
        this.outputs = List.of(currentStack);

        //re-mapping overrides to EmiStacks for rendering in slot widget
        //Map<BasicBreedingRecipe.OverrideRange, EmiStack> tierOverrides = new HashMap<>();
        /*for (Map.Entry<BasicBreedingRecipe.OverrideRange, ItemStack> entry : recipe.getTierOverrides().entrySet()) {
            tierOverrides.put(entry.getKey(), EmiStack.of(entry.getValue()));
        }*/
        //this.overrides = tierOverrides;
        //this.overridesKeys = overrides.keySet().stream().toList();

       // this.cycleTierWidget = new CustomButtonWidget(127, 2, () -> !overrides.isEmpty(), ((mouseX, mouseY, button) -> cycleStacks()));
      /*  this.outputWidget = new CustomSlotWidget(currentStack, 104, 38, this);

    }

    //static final Identifier GUI_TEXTURE = new Identifier("chicken_roost:textures/gui/forge_controller.png");
/*
    private final Identifier id;
    private final List<EmiIngredient> inputs;
    private final List<EmiStack> outputs;
    //private final Map<BasicBreedingRecipe.OverrideRange, EmiStack> overrides;
    //private final List<BasicBreedingRecipe.OverrideRange> overridesKeys;
    private int currentIndex = 0;
    private OrderedText currentTierText;
    private EmiStack currentStack;
    //private final CustomTextWidget tierTextWidget;
    //private final CustomButtonWidget cycleTierWidget;
    private final CustomSlotWidget outputWidget;

   /* @Override
    public void addWidgets(WidgetHolder widgets) {
        //the min tier text
        //widgets.add(tierTextWidget);

        //the fuel required text
        widgets.addText(Text.translatable(getOutputs().get(0).getItemStack().getTranslationKey()).asOrderedText(), 8, 20, 0x404040, false);

        //the ten input slots background
        //widgets.addTexture(EmiTexture.SLOT, 6, 34);
        //widgets.addTexture(EmiTexture.SLOT, 107, 14);
        //widgets.addTexture(EmiTexture.SLOT, 111, 17);

        //the input slots themselves
        //for (int i = 0; i < 2; i++) {
            int x = 7 + 1 % 5 * 18;
            int y = 35 + 1 / 5 * 18;
            widgets.addTexture(EmiTexture.SLOT, 7 + 1 % 5 * 18, 35 + 1 / 5 * 18);
            widgets.addSlot(inputs.get(0), 7 + 1 % 5 * 18, 35 + 1 / 5 * 18).drawBack(false);
            widgets.addTexture(EmiTexture.SLOT, 7 + 2 % 5 * 18, 35 + 2 / 5 * 18);
            widgets.addSlot(inputs.get(1), 7 + 2 % 5 * 18, 35 + 2 / 5 * 18).drawBack(false);
     //   }

        //add the tier cycling button
        //widgets.add(cycleTierWidget);
        widgets.add(outputWidget);
    }

    /*private void cycleStacks() {
        currentIndex++;
        // (currentIndex > overrides.size()) currentIndex = 0;
        if (currentIndex == 0) {
            //currentTierText = Text.translatable("container.alloy_forgery.rei.min_tier", minForgeTier).asOrderedText();
            currentStack = outputs.get(0);
       /* } else {
            //BasicBreedingRecipe.OverrideRange range = overridesKeys.get(currentIndex - 1);
            //currentTierText = Text.translatable("container.alloy_forgery.rei.min_tier", range).asOrderedText();
            //currentStack = overrides.get(range);
        }*/
        //tierTextWidget.setText(currentTierText);
        //outputWidget.setMutableStack(currentStack);
    /*}

    @Override
    public EmiRecipeCategory getCategory() {
        return RoostEmiPlugin.FORGE_CATEGORY;
    }

    @Override
    public @Nullable Identifier getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return inputs;
    }

    @Override
    public List<EmiStack> getOutputs() {
        return outputs;
    }

    @Override
    public int getDisplayWidth() {
        return 142;
    }

    @Override
    public int getDisplayHeight() {
        return 78;
    }
}*/