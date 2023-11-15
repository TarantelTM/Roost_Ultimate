
package net.tarantel.chickenroost.item.base;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.tarantel.chickenroost.util.Config;

import java.util.List;

public class Chicken_Tier_2 extends Item {

	public Chicken_Tier_2() {
		super(new Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON));
	}

	@Override
	public int getUseDuration(ItemStack itemstack) {
		return 0;
	}

	@Override
	public float getDestroySpeed(ItemStack par1ItemStack, BlockState par2Block) {
		return 0F;
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
		int roost_chickenlevel = 0;
		int roost_chickenxp = 0;
		super.appendHoverText(itemstack, world, list, flag);
		roost_chickenlevel = (int) ((itemstack).getOrCreateTag().getInt("roost_lvl"));
		roost_chickenxp = (int) ((itemstack).getOrCreateTag().getInt("roost_xp"));
		list.add(Component.nullToEmpty("\u00A71" + "Tier: " + "\u00A79" + "2"));
		list.add(Component.nullToEmpty((("\u00A7e") + "Level: " + "\u00A79" + ((roost_chickenlevel)) + "/" + (((int) Config.maxlevel_tier_2.get())))));
		list.add(Component.nullToEmpty((("\u00A7a") + "XP: " + "\u00A79" + ((roost_chickenxp)) + "/" + (((int) Config.xp_tier_2.get())))));
		list.add(Component.nullToEmpty("\u00A71 Roost Ultimate"));
	}
}