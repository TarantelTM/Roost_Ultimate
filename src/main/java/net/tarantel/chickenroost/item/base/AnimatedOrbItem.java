package net.tarantel.chickenroost.item.base;

import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;
import software.bernie.geckolib.util.GeckoLibUtil;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.tarantel.chickenroost.item.renderer.AnimatedIngotRenderer;
import net.tarantel.chickenroost.item.renderer.AnimatedOrbRenderer;
import software.bernie.geckolib.util.RenderUtil;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class AnimatedOrbItem extends RoostUltimateItem implements GeoItem {

    private String localpath;

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public AnimatedOrbItem(Properties properties, String path) {
        super(properties);
        this.localpath = path;
    }
    public String getLocalpath() {
        return localpath;
    }

    private PlayState predicate(AnimationState animationState) {
        //animationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public double getTick(Object itemStack) {
        return RenderUtil.getCurrentTick();
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private AnimatedOrbRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if(this.renderer == null) {
                    renderer = new AnimatedOrbRenderer();
                }
                renderer.addRenderLayer(new AutoGlowingGeoLayer<>(renderer));
                return this.renderer;
            }

        });
    }

    @Override
    public float getDestroySpeed(ItemStack par1ItemStack, BlockState par2Block) {
        return 0F;
    }

}