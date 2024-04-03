package net.tarantel.chickenroost.item.base;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.IItemRenderProperties;
import net.tarantel.chickenroost.Config;
import net.tarantel.chickenroost.item.renderer.AnimatedChickenRenderer_7;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.List;
import java.util.function.Consumer;

public class AnimatedChicken_7 extends Item implements IAnimatable {
    private String localpath;
    public AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public AnimatedChicken_7(Properties properties, String path) {
        super(properties);
        this.localpath = path;
    }
    public String getLocalpath() {
        return localpath;
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        int roost_chickenlevel = 0;
        int roost_chickenxp = 0;
        super.appendHoverText(itemstack, world, list, flag);
        roost_chickenlevel = (int) ((itemstack).getOrCreateTag().getInt("roost_lvl"));
        roost_chickenxp = (int) ((itemstack).getOrCreateTag().getInt("roost_xp"));
        list.add(Component.nullToEmpty("\u00A71" + "Tier: " + "\u00A79" + "7"));
        list.add(Component.nullToEmpty((("\u00A7e") + "Level: " + "\u00A79" + ((roost_chickenlevel)) + "/" + (((int) Config.MAX_LEVEL_TIER_7.get())))));
        list.add(Component.nullToEmpty((("\u00A7a") + "XP: " + "\u00A79" + ((roost_chickenxp)) + "/" + (((int) Config.MAX_XP_TIER_7.get())))));
        list.add(Component.nullToEmpty("\u00A71 Roost Ultimate"));
    }
    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<AnimatedChicken_7> controller = new AnimationController<>(this, "controller", 10, this::handleAnim);
        controller.setAnimation(new AnimationBuilder().loop("idle"));
        data.addAnimationController(controller);
    }
    private PlayState handleAnim(AnimationEvent<AnimatedChicken_7> event) {
        AnimationController<AnimatedChicken_7> controller = event.getController();
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
            private final BlockEntityWithoutLevelRenderer renderer = new AnimatedChickenRenderer_7();

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