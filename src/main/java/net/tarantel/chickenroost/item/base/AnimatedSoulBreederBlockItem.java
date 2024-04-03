package net.tarantel.chickenroost.item.base;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.IItemRenderProperties;
import net.tarantel.chickenroost.item.renderer.AnimatedSoulBreederItemRenderer;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.function.Consumer;
public class AnimatedSoulBreederBlockItem extends BlockItem implements IAnimatable {
    public AnimationFactory factory = GeckoLibUtil.createFactory(this);
    //private static final RawAnimation IDLE_NORMAL = RawAnimation.begin().thenLoop("normal.idle");
    public AnimatedSoulBreederBlockItem(Block block, Properties properties) {
        super(block, properties);
        //SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<AnimatedSoulBreederBlockItem> controller = new AnimationController<>(this, "controller", 10, this::handleAnim);
        controller.setAnimation(new AnimationBuilder().loop("normal.idle"));
        data.addAnimationController(controller);
    }
    private PlayState handleAnim(AnimationEvent<AnimatedSoulBreederBlockItem> event) {
        AnimationController<AnimatedSoulBreederBlockItem> controller = event.getController();
        return PlayState.CONTINUE;
    }
    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IItemRenderProperties() {
            private final BlockEntityWithoutLevelRenderer renderer = new AnimatedSoulBreederItemRenderer();

            @Override
            public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
                return renderer;
            }
        });
    }


    @Override
    public float getDestroySpeed(ItemStack par1ItemStack, BlockState par2Block) {
        return 0F;
    }

}