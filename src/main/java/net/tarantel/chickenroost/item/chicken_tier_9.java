
package net.tarantel.chickenroost.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import net.tarantel.chickenroost.util.Config;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class chicken_tier_9 extends Item {

	public chicken_tier_9(Settings settings) {
		super(settings.maxCount(64).fireproof().rarity(Rarity.COMMON));
	}





	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		int roost_chickenlevel = 0;
		int roost_chickenxp = 0;
		super.appendTooltip(stack, world, tooltip, context);
		roost_chickenlevel = (int) ((stack).getOrCreateNbt().getInt("roost_lvl"));
		roost_chickenxp = (int) ((stack).getOrCreateNbt().getInt("roost_xp"));
		tooltip.add(Text.literal("\u00A71" + "Tier: " + "\u00A79" + "9"));
		tooltip.add(Text.literal((("\u00A7e") + "Level: " + "\u00A79" + ((roost_chickenlevel)) + "/" + (((int) Config.maxlevel_tier_9.get())))));
		tooltip.add(Text.literal((("\u00A7a") + "XP: " + "\u00A79" + ((roost_chickenxp)) + "/" + (((int) Config.xp_tier_9.get())))));
		tooltip.add(Text.literal("\u00A71 Roost Ultimate"));
	}

	@Override
	public void onCraft(ItemStack stack, World world, PlayerEntity player) {
		super.onCraft(stack, world, player);

	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(stack, world, entity, slot, selected);

	}
}