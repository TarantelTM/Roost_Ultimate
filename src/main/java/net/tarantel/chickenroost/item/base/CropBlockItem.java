package net.tarantel.chickenroost.item.base;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.tarantel.chickenroost.util.Config;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CropBlockItem extends ChickenSeedBase {
    private final int currentmaxxpp;

    public CropBlockItem(Block p_41579_, Properties p_41580_, int currentmaxxp) {
        super(p_41579_, p_41580_, currentmaxxp);
        currentmaxxpp = currentmaxxp;
    }


    public @NotNull String getDescriptionId() {
        return this.getOrCreateDescriptionId();
    }
    @Override
    public void appendHoverText(ItemStack itemstack, @org.jetbrains.annotations.Nullable Level world,
                                List<Component> list, TooltipFlag flag) {
        int maxLevel = switch (currentmaxxpp) {
            case 1 -> Config.ServerConfig.food_xp_tier_2.get();
            case 2 -> Config.ServerConfig.food_xp_tier_3.get();
            case 3 -> Config.ServerConfig.food_xp_tier_4.get();
            case 4 -> Config.ServerConfig.food_xp_tier_5.get();
            case 5 -> Config.ServerConfig.food_xp_tier_6.get();
            case 6 -> Config.ServerConfig.food_xp_tier_7.get();
            case 7 -> Config.ServerConfig.food_xp_tier_8.get();
            case 8 -> Config.ServerConfig.food_xp_tier_9.get();
            default -> Config.ServerConfig.food_xp_tier_1.get();
        };

        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.nullToEmpty((("§a") + "XP: " + "§9" + maxLevel)));
        list.add(Component.nullToEmpty("§1 Roost Ultimate"));
    }

    public int getCurrentMaxXp() {
        return currentmaxxpp;
    }
}