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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.network.NetworkHooks;
import net.tarantel.chickenroost.Config;
import net.tarantel.chickenroost.handlers.ChickenBookHandler;
import net.tarantel.chickenroost.handlers.OwnCraftingMenu;
import net.tarantel.chickenroost.item.renderer.ChickenBookRenderer;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.function.Consumer;

public class ChickenBook extends Item implements IAnimatable {
    private static final Component CONTAINER_TITLE = Component.nullToEmpty("container.crafting");
    public AnimationFactory factory = GeckoLibUtil.createFactory(this);


    //public AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public ChickenBook(Properties p_41383_) {
        super(p_41383_);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
        InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
        int x = (int) entity.getX();
        int y = (int) entity.getY();
        int z = (int) entity.getZ();

        if (entity instanceof ServerPlayer _ent) {
            BlockPos _bpos = world.getBlockRandomPos(x, y, z, 233);
            NetworkHooks.openGui((ServerPlayer) _ent, new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return Component.nullToEmpty(" ");
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
        Level world = entity.level;
        Player player = entity.level.getNearestPlayer(entity, 1);
       if(Config.GUIDEBOOK.get() == true) {
           BlockPos newpos = new BlockPos((int) x, (int) y, (int) z);
           if (world.isClientSide) {
               return false;
           } else {
               player.openMenu(this.getMenuProvider(world, newpos));
               //entity.awardStat(Stats.INTERACT_WITH_CRAFTING_TABLE);

           }
       }
        return false;
    }

    @Override
    public boolean canAttackBlock(BlockState p_41441_, Level p_41442_, BlockPos p_41443_, Player p_41444_) {
        return false;
    }

    public MenuProvider getMenuProvider(Level p_52241_, BlockPos p_52242_) {
        return new SimpleMenuProvider(
                (p_52229_, p_52230_, p_52231_) -> new OwnCraftingMenu(p_52229_, p_52230_, ContainerLevelAccess.create(p_52241_, p_52242_)), CONTAINER_TITLE
        );
    }
    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IItemRenderProperties() {
            private final BlockEntityWithoutLevelRenderer renderer = new ChickenBookRenderer();

            @Override
            public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
                return renderer;
            }
        });
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<ChickenBook> controller = new AnimationController<>(this, "controller", 10, this::handleAnim);
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    //private static final RawAnimation CRAFTING = RawAnimation.begin().then("crafting.idle", Animation.LoopType.LOOP);
    //private static final RawAnimation IDLE = RawAnimation.begin().then("normal.idle", Animation.LoopType.LOOP);
    //private static final RawAnimation FINISH = RawAnimation.begin().then("crafting.finish2", Animation.LoopType.PLAY_ONCE);

    private PlayState handleAnim(AnimationEvent<ChickenBook> event) {
        AnimationController<ChickenBook> controller = event.getController();
        return PlayState.CONTINUE;
    }



   /* @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }*/

   /* @Override
    public double getTick(Object itemStack) {
        return RenderUtils.getCurrentTick();
    }*/

    /*@Override
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
    }*/


}
