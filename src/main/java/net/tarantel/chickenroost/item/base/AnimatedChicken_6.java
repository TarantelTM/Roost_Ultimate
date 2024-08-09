package net.tarantel.chickenroost.item.base;

import net.tarantel.chickenroost.util.ModDataComponents;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtil;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.tarantel.chickenroost.item.renderer.AnimatedChickenRenderer_6;
import net.tarantel.chickenroost.util.Config;

import java.util.List;
import java.util.function.Consumer;

public class AnimatedChicken_6 extends ChickenItemBase implements GeoItem {

    private String localpath;
    public int currentchickena;
    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public AnimatedChicken_6(Properties properties, String path, int currentchicken) {
        super(properties, currentchicken);
        this.localpath = path;
        this.currentchickena = currentchicken;
    }
    public String getLocalpath() {
        return localpath;
    }

    @Override
    public void appendHoverText(ItemStack itemstack, TooltipContext context, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, context, list, flag);
        list.add(Component.nullToEmpty("\u00A71" + "Tier: " + "\u00A79" + "6"));
        list.add(Component.nullToEmpty((("\u00A7e") + "Level: " + "\u00A79" + ((itemstack.get(ModDataComponents.CHICKENLEVEL))) + "/" + (((int) Config.maxlevel_tier_6.get())))));
        list.add(Component.nullToEmpty((("\u00A7a") + "XP: " + "\u00A79" + ((itemstack.get(ModDataComponents.CHICKENXP))) + "/" + (((int) Config.xp_tier_6.get())))));
        list.add(Component.nullToEmpty("\u00A71 Roost Ultimate"));
    }
    private PlayState predicate(AnimationState animationState) {
        animationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
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
            private AnimatedChickenRenderer_6 renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if(this.renderer == null) {
                    renderer = new AnimatedChickenRenderer_6();
                }

                return this.renderer;
            }
        });
    }

    @Override
    public float getDestroySpeed(ItemStack par1ItemStack, BlockState par2Block) {
        return 0F;
    }


}