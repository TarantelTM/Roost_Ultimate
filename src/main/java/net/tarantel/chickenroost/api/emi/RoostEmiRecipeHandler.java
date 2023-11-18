/*package net.tarantel.chickenroost.api.emi;


import dev.emi.emi.api.recipe.EmiCraftingRecipe;
import dev.emi.emi.api.recipe.EmiPlayerInventory;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.handler.EmiCraftContext;
import dev.emi.emi.api.recipe.handler.EmiRecipeHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.slot.Slot;
import net.tarantel.chickenroost.handler.breeder_handler;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
public class RoostEmiRecipeHandler implements EmiRecipeHandler<breeder_handler> {
    @Override
    public List<Slot> getInputSources(breeder_handler handler) {
        List<Slot> slots = new ArrayList<>();
        for (int i = 0; i < handler.slots.size(); i++) {
            if (i < 2) continue;
            slots.add(handler.slots.get(i));
        }
        return slots;
    }

    @Override
    public List<Slot> getCraftingSlots(breeder_handler handler) {
        List<Slot> slots = new ArrayList<>();
        for (int i = 2; i < 12; i++) {
            slots.add(handler.slots.get(i));
        }
        return slots;
    }

    @Override
    public @Nullable Slot getOutputSlot(breeder_handler handler) {
        return EmiRecipeHandler.super.getOutputSlot(handler);
    }

    @Override
    public EmiPlayerInventory getInventory(HandledScreen<breeder_handler> screen) {
        return empty;
    }

    @Override
    public boolean supportsRecipe(EmiRecipe recipe) {
        return recipe.getCategory() == RoostEmiPlugin.FORGE_CATEGORY && recipe.supportsRecipeTree();
    }

    @Override
    public boolean canCraft(EmiRecipe recipe, EmiCraftContext<breeder_handler> context) {
        return true;
    }

    @Override
    public boolean craft(EmiRecipe recipe, EmiCraftContext<breeder_handler> context) {
        if (recipe instanceof EmiCraftingRecipe craftingRecipe) {
            MinecraftClient.getInstance().setScreen(context.getScreen());
            AllPackets.getChannel().sendToServer(new BlueprintAssignCompleteRecipePacket(craftingRecipe.getId()));
            return true;
        }
        return false;
    }
}*/