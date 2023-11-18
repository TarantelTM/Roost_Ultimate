
package net.tarantel.chickenroost.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import net.tarantel.chickenroost.util.ChickenStickTool;
import net.tarantel.chickenroost.util.WrenchTool;


public class ChickenStickItem extends Item {
	public ChickenStickItem(Settings settings) {
		super(settings.maxDamage(16).rarity(Rarity.COMMON).recipeRemainder(ModItems.CHICKEN_STICK));
	}
	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		super.useOnBlock(context);
		WrenchTool.execute(context.getWorld(), context.getBlockPos().getX(), context.getBlockPos().getY(),
				context.getBlockPos().getZ());

		return ActionResult.PASS;
	}
	@Override
	public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		super.useOnEntity(stack, user, entity, hand);
		World world = entity.getWorld();
		ActionResult retval = ActionResult.success(entity.getWorld().isClient());

		//Entity entity = this;
		//world.getSpawnPos().getX();
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();

		ChickenStickTool.execute(world, x, y, z, entity);
		return retval;
	}


	
}
