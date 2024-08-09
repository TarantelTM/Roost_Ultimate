package net.tarantel.chickenroost.item.base;

import io.netty.buffer.Unpooled;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.common.extensions.IItemExtension;
import net.tarantel.chickenroost.handler.ChickenBookHandler;
import net.tarantel.chickenroost.item.renderer.ChickenBookRenderer;
import net.tarantel.chickenroost.screen.OwnCraftingMenu;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtil;


import java.util.function.Consumer;

public class ChickenBook extends RoostUltimateItem implements GeoItem, IItemExtension {
    private static final Component CONTAINER_TITLE = Component.translatable("container.crafting");



    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public ChickenBook(Properties p_41383_) {
        super(p_41383_);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
        InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();


        if (entity instanceof ServerPlayer _ent) {
            BlockPos _bpos = BlockPos.containing(x, y, z);
            ServerPlayer theplayer = (ServerPlayer) entity;
            _ent.openMenu(new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return Component.literal(" ");
                }

                @Override
                public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
                    return new ChickenBookHandler(id, inventory,new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(_bpos));

                }
            }, _bpos);
        }
        return ar;
    }


    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        //InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();
        Level world = entity.level();
        Player player = entity.level().getNearestPlayer(entity, 1);

        BlockPos newpos = new BlockPos((int) x, (int) y ,(int) z);
        if (world.isClientSide) {
            return false;
        } else {
            player.openMenu(this.getMenuProvider(world, newpos));
            //entity.awardStat(Stats.INTERACT_WITH_CRAFTING_TABLE);

        }
        return false;
    }


    /*@Override
    protected MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return new SimpleMenuProvider(
                (p_52229_, p_52230_, p_52231_) -> new CraftingMenu(p_52229_, p_52230_, ContainerLevelAccess.create(level, pos)), CONTAINER_TITLE
        );
    }*/
    //@Override
    protected MenuProvider getMenuProvider(Level p_52241_, BlockPos p_52242_) {
        return new SimpleMenuProvider(
                (p_52229_, p_52230_, p_52231_) -> new OwnCraftingMenu(p_52229_, p_52230_, ContainerLevelAccess.create(p_52241_, p_52242_)), CONTAINER_TITLE
        );
    }

    private static final RawAnimation CRAFTING = RawAnimation.begin().then("crafting.idle", Animation.LoopType.LOOP);
    private static final RawAnimation IDLE = RawAnimation.begin().then("normal.idle", Animation.LoopType.LOOP);
    private static final RawAnimation FINISH = RawAnimation.begin().then("crafting.finish2", Animation.LoopType.PLAY_ONCE);

    private PlayState predicate(AnimationState<GeoAnimatable> state) {
        AnimationController<GeoAnimatable> controller = state.getController();
        controller.triggerableAnim("craft", CRAFTING);
        controller.triggerableAnim("idle", IDLE);
        controller.triggerableAnim("finish", FINISH);
        if(controller.hasAnimationFinished()){

        }

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
            private ChickenBookRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if(this.renderer == null) {
                    renderer = new ChickenBookRenderer();
                }

                return this.renderer;
            }
        });
    }



    /*@Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag compound) {
        return new NewCraftingCap();
    }

    @Override
    public CompoundTag getShareTag(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        stack.getCapability(Capabilities.ITEM_HANDLER, null).ifPresent(capability -> nbt.put("Inventory", ((ItemStackHandler) capability).serializeNBT()));
        return nbt;
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundTag nbt) {
        super.readShareTag(stack, nbt);
        if (nbt != null)
            stack.getCapability(Capabilities.ITEM_HANDLER, null).ifPresent(capability -> ((ItemStackHandler) capability).deserializeNBT((CompoundTag) nbt.get("Inventory")));
    }
*/

}
