
package net.tarantel.chickenroost.item.base;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.tarantel.chickenroost.util.ChickenStickTool;
import net.tarantel.chickenroost.util.WrenchTool;

public class ChickenStickItem extends RoostUltimateItem {
	public ChickenStickItem() {
		super(new Properties().durability(16).rarity(Rarity.COMMON));
	}


	@Override
	public UseAnim getUseAnimation(ItemStack itemstack) {
		return UseAnim.BLOCK;
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		super.useOn(context);
		WrenchTool.execute(context.getLevel(), context.getClickedPos().getX(), context.getClickedPos().getY(),
				context.getClickedPos().getZ());
		return InteractionResult.SUCCESS;
	}

	public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity entity, InteractionHand hand) {
		super.interactLivingEntity(stack, player, entity, hand);
		Level level = entity.level();
		InteractionResult retval = InteractionResult.sidedSuccess(entity.level().isClientSide());

		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		ChickenStickTool.execute(level, x, y, z, entity);
		return retval;
	}
	@Override
	public float getDestroySpeed(ItemStack par1ItemStack, BlockState par2Block) {
		return 0F;
	}
}
