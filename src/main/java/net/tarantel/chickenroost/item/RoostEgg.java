package net.tarantel.chickenroost.item;

import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.EggItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class RoostEgg extends EggItem {
   public ResourceLocation entity;

   public RoostEgg(ResourceLocation entity, Properties properties) {
      super(properties);
      this.entity = entity;
   }

   @NotNull
   public InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand) {
      ItemStack itemstack = player.getItemInHand(hand);
      level.playSound(
         (Player)null,
         player.getX(),
         player.getY(),
         player.getZ(),
         SoundEvents.EGG_THROW,
         SoundSource.PLAYERS,
         0.5F,
         0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F)
      );
      if (!level.isClientSide) {
         EntityType<?> entityType = EntityType.byString(this.entity.toString()).orElse(EntityType.CHICKEN);
         RoostThrownEgg thrownegg = new RoostThrownEgg(level, player, entityType);
         thrownegg.setItem(itemstack);
         thrownegg.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
         level.addFreshEntity(thrownegg);
      }

      player.awardStat(Stats.ITEM_USED.get(this));
      itemstack.consume(1, player);
      return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
   }

   @NotNull
   public Projectile asProjectile(@NotNull Level level, Position pos, @NotNull ItemStack stack, @NotNull Direction direction) {
      RoostThrownEgg thrownegg = new RoostThrownEgg(level, pos.x(), pos.y(), pos.z());
      thrownegg.setItem(stack);
      return thrownegg;
   }
}
