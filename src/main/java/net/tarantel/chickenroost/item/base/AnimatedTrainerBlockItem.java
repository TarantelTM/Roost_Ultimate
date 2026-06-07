package net.tarantel.chickenroost.item.base;

import java.util.function.Consumer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.tarantel.chickenroost.ChickenRoostMod;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtil;

public class AnimatedTrainerBlockItem extends BlockItem implements GeoItem {
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   private static final RawAnimation IDLE_NORMAL = RawAnimation.begin().thenLoop("idle");

   public AnimatedTrainerBlockItem(Block block, Properties properties) {
      super(block, properties);
      SingletonGeoAnimatable.registerSyncedAnimatable(this);
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept(new IClientItemExtensions() {
         private GeoItemRenderer<AnimatedTrainerBlockItem> renderer = null;

         @NotNull
         public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            if (this.renderer == null) {
               this.renderer = new GeoItemRenderer(new DefaultedBlockGeoModel(ChickenRoostMod.ownresource("trainer")));
            }

            return this.renderer;
         }
      });
   }

   public void registerControllers(ControllerRegistrar controllerRegistrar) {
      controllerRegistrar.add(new AnimationController(this, state -> state.setAndContinue(IDLE_NORMAL)));
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   public double getTick(Object itemStack) {
      return RenderUtil.getCurrentTick();
   }
}
