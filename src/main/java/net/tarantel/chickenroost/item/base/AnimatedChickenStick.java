package net.tarantel.chickenroost.item.base;

import com.google.common.base.Suppliers;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.tarantel.chickenroost.item.renderer.AnimatedChickenRenderer;
import net.tarantel.chickenroost.util.*;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.animation.object.PlayState;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import net.tarantel.chickenroost.item.renderer.AnimatedChickenStickRenderer;


import javax.annotation.Nullable;
import java.util.function.Consumer;
import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class AnimatedChickenStick extends RoostUltimateItem implements GeoItem {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final RawAnimation IDLE_NORMAL = RawAnimation.begin().thenLoop("idle");


    public AnimatedChickenStick(Properties properties) {
        super(properties);
    }

    /*private PlayState predicate(AnimationState animationState) {
        animationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }*/

    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>("controller", 0, animTest -> {
            final ItemDisplayContext context = animTest.getData(DataTickets.ITEM_RENDER_PERSPECTIVE);


            return PlayState.CONTINUE;


        }).receiveTriggeredAnimations()
                .triggerableAnim("idle", IDLE_NORMAL));


    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    /*@Override
    public double getTick(Object itemStack) {
        return RenderUtil.getCurrentTick();
    }*/


    @Override
    public void appendHoverText(@NotNull ItemStack itemstack, @NotNull TooltipContext context, @NotNull TooltipDisplay tooltipDisplay, @NotNull Consumer<Component> list, @NotNull TooltipFlag flag) {
        try {
            super.appendHoverText(itemstack, context, tooltipDisplay, list, flag);
            list.accept(Component.translatable(
                    "roost_chicken.chickenstick.info.catch"
            ));
            list.accept(Component.translatable(
                    "roost_chicken.chickenstick.info.blocks"
            ));
            list.accept(Component.translatable(
                    "roost_chicken.chickenstick.info.pipe"
            ));
            list.accept(Component.literal("§1 Roost Ultimate"));
        } catch (Exception e) {
            System.out.println("Error in Tooltip:");
            e.printStackTrace();
        }
    }




    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            // Defer creation of our renderer then cache it so that it doesn't get instantiated too early
            private final Supplier<AnimatedChickenStickRenderer> renderer = Suppliers.memoize(AnimatedChickenStickRenderer::new);

            @Override
            @Nullable
            public GeoItemRenderer<AnimatedChickenStick> getGeoItemRenderer() {
                return this.renderer.get();
            }
        });
    }

    /*@Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private AnimatedChickenStickRenderer renderer;

            @Override
            public @NotNull BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if(this.renderer == null) {
                    renderer = new AnimatedChickenStickRenderer();
                }

                return this.renderer;
            }
        });
    }*/

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        super.useOn(context);
        WrenchTool.execute(context.getLevel(), context.getClickedPos().getX(), context.getClickedPos().getY(),
                context.getClickedPos().getZ());

        return InteractionResult.SUCCESS;
    }


    public @NotNull InteractionResult interactLivingEntity(@NotNull ItemStack stack, @NotNull Player player, @NotNull LivingEntity entity, @NotNull InteractionHand hand) {
        super.interactLivingEntity(stack, player, entity, hand);
        Level level = entity.level();
        InteractionResult retval = InteractionResult.SUCCESS;

        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();
        ChickenStickTool.execute(level, x, y, z, entity);
        return retval;
    }
    @Override
    public float getDestroySpeed(@NotNull ItemStack par1ItemStack, @NotNull BlockState par2Block) {
        return 0F;
    }


}