
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


public class chicken_tier_1 extends Item {

	public chicken_tier_1(Settings settings) {
		super(settings.maxCount(64).fireproof().rarity(Rarity.COMMON));
	}

	

	

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		int roost_chickenlevel ;
		int roost_chickenxp;
		super.appendTooltip(stack, world, tooltip, context);
		roost_chickenlevel = ((stack).getOrCreateNbt().getInt("roost_lvl"));
		roost_chickenxp = ((stack).getOrCreateNbt().getInt("roost_xp"));
		tooltip.add(Text.literal("\u00A71" + "Tier: " + "\u00A79" + "1"));
		tooltip.add(Text.literal((("\u00A7e") + "Level: " + "\u00A79" + ((roost_chickenlevel)) + "/" + (((int) Config.maxlevel_tier_1.get())))));
		tooltip.add(Text.literal((("\u00A7a") + "XP: " + "\u00A79" + ((roost_chickenxp)) + "/" + (((int) Config.xp_tier_1.get())))));
		tooltip.add(Text.literal("\u00A71 Roost Ultimate"));
	}

	@Override
	public void onCraft(ItemStack stack, World world, PlayerEntity player) {
		super.onCraft(stack, world, player);

	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(stack, world, entity, slot, selected);
		//stack.getOrCreateNbt().putInt("roost_lvl", 22);
		//(stack).getOrCreateNbt().putInt("roost_xp", 222);
	}
}