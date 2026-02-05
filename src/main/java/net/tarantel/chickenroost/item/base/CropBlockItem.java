package net.tarantel.chickenroost.item.base;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;
import net.tarantel.chickenroost.util.Config;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CropBlockItem extends ChickenSeedBase {

        private final Supplier<Block> blockSupplier;
        private final int currentmaxxpp;

        public CropBlockItem(Supplier<Block> blockSupplier, Properties properties, int currentmaxxp) {
            super(blockSupplier.get(), properties, currentmaxxp);
            this.blockSupplier = blockSupplier;
            this.currentmaxxpp = currentmaxxp;
        }

    /*@Override
    public @NotNull String getDescriptionId() {
        return this.getDescriptionId();
    }*/

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, TooltipDisplay tooltipDisplay, Consumer<Component> components, TooltipFlag tooltipFlag) {
        int maxLevel = switch (currentmaxxpp) {
            case 1 -> Config.food_xp_tier_2.get();
            case 2 -> Config.food_xp_tier_3.get();
            case 3 -> Config.food_xp_tier_4.get();
            case 4 -> Config.food_xp_tier_5.get();
            case 5 -> Config.food_xp_tier_6.get();
            case 6 -> Config.food_xp_tier_7.get();
            case 7 -> Config.food_xp_tier_8.get();
            case 8 -> Config.food_xp_tier_9.get();
            default -> Config.food_xp_tier_1.get();
        };

        super.appendHoverText(pStack, pContext,tooltipDisplay, components, tooltipFlag);
        components.accept(Component.nullToEmpty((("§a") + "XP: " + "§9" + maxLevel)));
        components.accept(Component.nullToEmpty("§1 Roost Ultimate"));
    }

    public int getCurrentMaxXp() {
        return currentmaxxpp;
    }
}