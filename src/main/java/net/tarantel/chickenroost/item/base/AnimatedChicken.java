package net.tarantel.chickenroost.item.base;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.item.renderer.AnimatedChickenRenderer;
import net.tarantel.chickenroost.util.ClientBiomeCache;
import net.tarantel.chickenroost.util.Config;
import net.tarantel.chickenroost.util.ModDataComponents;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;

import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtil;


import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Consumer;
@SuppressWarnings("deprecation")
public class AnimatedChicken extends ChickenItemBase implements GeoItem {
    private static final String NBT_LEVEL_KEY = "chickenlevel";
    private static final String NBT_XP_KEY    = "chickenxp";
    private static final String NBT_AGE_KEY    = "age";
    private static final ResourceLocation C_VANILLA_ID =
            ResourceLocation.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_vanilla");

    private final String localpath;
    private final int currentchickena;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public AnimatedChicken(Properties properties, String path, int currentchicken) {
        super(properties, currentchicken);
        this.localpath = path;
        this.currentchickena = currentchicken;
    }

    public String getLocalpath() {
        return localpath;
    }

    public static BlockPos rightposi(BlockPos blockPos, Direction direction)
    {
        final int[] xside = new int[]{0, 0, 0, 0, -1, 1};
        final int[] yside = new int[]{-1, 1, 0, 0, 0, 0};
        final int[] zside = new int[]{0, 0, -1, 1, 0, 0};

        int x = blockPos.getX() + xside[direction.ordinal()];
        int y = blockPos.getY() + yside[direction.ordinal()];
        int z = blockPos.getZ() + zside[direction.ordinal()];

        return new BlockPos(x, y, z);
    }



    private static @Nullable ServerLevel asServer(Level level) {
        return (level instanceof ServerLevel s) ? s : null;
    }

    private static boolean isVanillaChickenItem(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).equals(C_VANILLA_ID);
    }

    private static EntityType<?> resolveEntityType(Item item) {

        if (item instanceof ChickenItemBase && !isVanillaChickenItem(item)) {
            ResourceLocation id = ResourceLocation.parse(
                    item.getDefaultInstance().getItemHolder().getRegisteredName());
            return BuiltInRegistries.ENTITY_TYPE.get(id);
        }
        return BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.parse("minecraft:chicken"));
    }

    private static void applyChickenData(Entity entity, ItemStack stack) {
        if (stack.has(ModDataComponents.CHICKENLEVEL)) {
            entity.getPersistentData().putInt(NBT_LEVEL_KEY, stack.get(ModDataComponents.CHICKENLEVEL.value()));
        }
        if (stack.has(ModDataComponents.CHICKENXP)) {
            entity.getPersistentData().putInt(NBT_XP_KEY, stack.get(ModDataComponents.CHICKENXP.value()));
        }
        if (stack.has(ModDataComponents.AGE)) {
            entity.getPersistentData().putInt(NBT_AGE_KEY, stack.get(ModDataComponents.AGE.value()));
        }
    }

    private void postSpawnSideEffects(ServerLevel level, @Nullable Player player, ItemStack stack, BlockPos posOrEntityPos) {
        stack.consume(1, player);
        if (player != null) player.awardStat(Stats.ITEM_USED.get(this));
        level.gameEvent(player, GameEvent.ENTITY_PLACE, posOrEntityPos);
    }

    private boolean spawn(ServerLevel level, @Nullable Player player, ItemStack stack, BlockPos pos) {
        EntityType<?> type = resolveEntityType(stack.getItem());

        Entity e = type.spawn(level, stack, player, pos, MobSpawnType.SPAWN_EGG, false, false);
        if (e == null) return false;

        applyChickenData(e, stack);
        postSpawnSideEffects(level, player, stack, pos);
        return true;
    }




    @Override
    public @NotNull InteractionResult useOn(UseOnContext ctx) {
        ServerLevel server = asServer(ctx.getLevel());
        if (server == null) return InteractionResult.SUCCESS;

        ItemStack stack = ctx.getItemInHand();
        BlockPos   pos  = rightposi(ctx.getClickedPos(), ctx.getClickedFace());
        boolean ok = spawn(server, ctx.getPlayer(), stack, pos);
        return ok ? InteractionResult.CONSUME : InteractionResult.PASS;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        BlockHitResult hit = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);

        if (hit.getType() != HitResult.Type.BLOCK) return InteractionResultHolder.pass(stack);
        if (!(level.getBlockState(hit.getBlockPos()).getBlock() instanceof LiquidBlock)) return InteractionResultHolder.pass(stack);

        if (!(level instanceof ServerLevel server)) return InteractionResultHolder.success(stack);

        BlockPos pos = hit.getBlockPos();
        if (!level.mayInteract(player, pos) || !player.mayUseItemAt(pos, hit.getDirection(), stack)) {
            return InteractionResultHolder.fail(stack);
        }

        boolean ok = spawn(server, player, stack, pos);
        return ok ? InteractionResultHolder.consume(stack) : InteractionResultHolder.pass(stack);
    }


    @Override
    public void appendHoverText(@NotNull ItemStack itemstack, @NotNull TooltipContext context, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        try {
        super.appendHoverText(itemstack, context, list, flag);

            int maxLevel = switch (currentchickena) {
                case 1 -> Config.maxlevel_tier_2.get();
                case 2 -> Config.maxlevel_tier_3.get();
                case 3 -> Config.maxlevel_tier_4.get();
                case 4 -> Config.maxlevel_tier_5.get();
                case 5 -> Config.maxlevel_tier_6.get();
                case 6 -> Config.maxlevel_tier_7.get();
                case 7 -> Config.maxlevel_tier_8.get();
                case 8 -> Config.maxlevel_tier_9.get();
                default -> Config.maxlevel_tier_1.get();
            };
            int maxXP = switch (currentchickena) {
                case 1 -> Config.xp_tier_2.get();
                case 2 -> Config.xp_tier_3.get();
                case 3 -> Config.xp_tier_4.get();
                case 4 -> Config.xp_tier_5.get();
                case 5 -> Config.xp_tier_6.get();
                case 6 -> Config.xp_tier_7.get();
                case 7 -> Config.xp_tier_8.get();
                case 8 -> Config.xp_tier_9.get();
                default -> Config.xp_tier_1.get();
            };


        int level = 0;
        int xp = 0;
        if(itemstack.has(ModDataComponents.CHICKENLEVEL)){
            level = itemstack.get(ModDataComponents.CHICKENLEVEL.value());
        }
        if(itemstack.has(ModDataComponents.CHICKENXP)){
            xp = itemstack.get(ModDataComponents.CHICKENXP.value());
        }
        list.add(Component.nullToEmpty("§1" + "Tier: " + "§9" + (currentchickena + 1)));
        list.add(Component.nullToEmpty((("§e") + "Level: " + "§9" + level + "/" + maxLevel)));
        list.add(Component.nullToEmpty((("§a") + "XP: " + "§9" + xp + "/" +  maxXP)));

            String itemId = BuiltInRegistries.ITEM.getKey(itemstack.getItem()).toString();
            List<String> biomes = ClientBiomeCache.getBiomes(itemId);
            if (!biomes.isEmpty()) {
                list.add(Component.literal("Spawns in:"));
                for (String biome : biomes) {
                    try {
                        list.add(Component.translatable(" - %s", Component.translatable("biome." + biome.replace(":", "."))));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                list.add(Component.literal("Spawns in: No Spawn found, maybe check Recipes"));
            }
        list.add(Component.nullToEmpty("§1 Roost Ultimate"));
        } catch (Exception e) {
            System.out.println("Error in Tooltip:");
            e.printStackTrace();
        }
    }

    private PlayState predicate(AnimationState animationState) {
        animationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
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
            private AnimatedChickenRenderer renderer;

            @Override
            public @NotNull BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if(this.renderer == null) {
                    renderer = new AnimatedChickenRenderer();
                }

                return this.renderer;
            }
        });
    }

    @Override
    public float getDestroySpeed(@NotNull ItemStack par1ItemStack, @NotNull BlockState par2Block) {
        return 0F;
    }
}