package net.tarantel.chickenroost.item.base;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.tarantel.chickenroost.Config;

import java.util.List;

public class ModItemNameBlockItem_3 extends BlockItem {
    public ModItemNameBlockItem_3(Block p_41579_, Item.Properties p_41580_) {
        super(p_41579_, p_41580_);
    }

    public String getDescriptionId() {
        return this.getOrCreateDescriptionId();
    }
    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {

        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.nullToEmpty((("\u00A7a") + "XP: " + "\u00A79" + (((int) Config.SEED_XP_TIER_3.get())))));
        list.add(Component.nullToEmpty("\u00A71 Roost Ultimate"));
    }
}