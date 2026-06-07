package net.tarantel.chickenroost.item.base;

import java.util.List;
import java.util.function.Consumer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.tarantel.chickenroost.item.renderer.AnimatedChickenStickRenderer;
import net.tarantel.chickenroost.util.ChickenStickTool;
import net.tarantel.chickenroost.util.WrenchTool;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.animation.Animation.LoopType;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtil;

public class AnimatedChickenStick extends RoostUltimateItem implements GeoItem {
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

   public AnimatedChickenStick(Properties properties) {
      super(properties);
   }

   private PlayState predicate(AnimationState animationState) {
      animationState.getController().setAnimation(RawAnimation.begin().then("idle", LoopType.LOOP));
      return PlayState.CONTINUE;
   }

   public void registerControllers(ControllerRegistrar controllerRegistrar) {
      controllerRegistrar.add(new AnimationController(this, "controller", 0, this::predicate));
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   public double getTick(Object itemStack) {
      return RenderUtil.getCurrentTick();
   }

   public void appendHoverText(@NotNull ItemStack itemstack, @NotNull TooltipContext context, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
      try {
         super.appendHoverText(itemstack, context, list, flag);
         list.add(Component.translatable("roost_chicken.chickenstick.info.catch"));
         list.add(Component.translatable("roost_chicken.chickenstick.info.blocks"));
         list.add(Component.translatable("roost_chicken.chickenstick.info.pipe"));
         list.add(Component.literal("§1 Roost Ultimate"));
      } catch (Exception var6) {
         System.out.println("Error in Tooltip:");
         var6.printStackTrace();
      }
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept(new IClientItemExtensions() {
         private AnimatedChickenStickRenderer renderer;

         @NotNull
         public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            if (this.renderer == null) {
               this.renderer = new AnimatedChickenStickRenderer();
            }

            return this.renderer;
         }
      });
   }

   @NotNull
   public InteractionResult useOn(@NotNull UseOnContext context) {
      super.useOn(context);
      WrenchTool.execute(context.getLevel(), context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ());
      return InteractionResult.SUCCESS;
   }

   @NotNull
   public InteractionResult interactLivingEntity(@NotNull ItemStack stack, @NotNull Player player, @NotNull LivingEntity entity, @NotNull InteractionHand hand) {
      super.interactLivingEntity(stack, player, entity, hand);
      Level level = entity.level();
      InteractionResult retval = InteractionResult.sidedSuccess(entity.level().isClientSide());
      double x = entity.getX();
      double y = entity.getY();
      double z = entity.getZ();
      ChickenStickTool.execute(level, x, y, z, entity);
      return retval;
   }

   public float getDestroySpeed(@NotNull ItemStack par1ItemStack, @NotNull BlockState par2Block) {
      return 0.0F;
   }
}
