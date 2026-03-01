package net.tarantel.chickenroost.item.base;

import java.util.function.Consumer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.tarantel.chickenroost.item.renderer.AnimatedIngotRenderer;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtil;

public class AnimatedIngotItem extends Item implements GeoItem {
   private final String localpath;
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

   public AnimatedIngotItem(Properties properties, String path) {
      super(properties);
      this.localpath = path;
   }

   public String getLocalpath() {
      return this.localpath;
   }

   private PlayState predicate(AnimationState animationState) {
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

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept(new IClientItemExtensions() {
         private AnimatedIngotRenderer renderer;

         @NotNull
         public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            if (this.renderer == null) {
               this.renderer = new AnimatedIngotRenderer();
            }

            return this.renderer;
         }
      });
   }

   public float getDestroySpeed(@NotNull ItemStack par1ItemStack, @NotNull BlockState par2Block) {
      return 0.0F;
   }
}
