package net.tarantel.chickenroost.item.base;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.registries.ForgeRegistries;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.client.ClientBreedingCache;
import net.tarantel.chickenroost.client.ClientRoostCache;
import net.tarantel.chickenroost.client.tooltip.StackLineTooltip;
import net.tarantel.chickenroost.entity.BaseChickenEntity;
import net.tarantel.chickenroost.item.renderer.AnimatedChickenRenderer;

import net.tarantel.chickenroost.util.ClientBiomeCache;
import net.tarantel.chickenroost.util.Config;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class AnimatedChicken extends ChickenItemBase implements GeoItem {

    private static final String NBT_LEVEL_KEY = "roost_lvl";
    private static final String NBT_XP_KEY    = "roost_xp";
    private static final String NBT_AGE_KEY    = "age";
    private static final ResourceLocation C_VANILLA_ID =
            new ResourceLocation("chicken_roost:c_vanilla");

    private final String localpath;
    private final int currentchickena;
    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

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



    private static @javax.annotation.Nullable ServerLevel asServer(Level level) {
        return (level instanceof ServerLevel s) ? s : null;
    }

    private static boolean isVanillaChickenItem(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).equals(C_VANILLA_ID);
    }

    private static EntityType<?> resolveEntityType(Item item) {

        if (item instanceof ChickenItemBase && !isVanillaChickenItem(item)) {
            ResourceLocation id = ForgeRegistries.ITEMS.getKey(item);
            return BuiltInRegistries.ENTITY_TYPE.get(id);
        }
        return BuiltInRegistries.ENTITY_TYPE.get(new ResourceLocation("minecraft:chicken"));
    }

    private static void applyChickenData(Entity entity, ItemStack stack) {
        CompoundTag itemTag = stack.getTag();
        if (itemTag == null) return;

        CompoundTag entityTag = entity.getPersistentData();

        // Level
        if (itemTag.contains(NBT_LEVEL_KEY, CompoundTag.TAG_INT)) {
            entityTag.putInt(NBT_LEVEL_KEY, itemTag.getInt(NBT_LEVEL_KEY));
        }

        // XP
        if (itemTag.contains(NBT_XP_KEY, CompoundTag.TAG_INT)) {
            entityTag.putInt(NBT_XP_KEY, itemTag.getInt(NBT_XP_KEY));
        }

        // ✅ Age – VANILLA-WEG
        if (entity instanceof AgeableMob ageable) {
            boolean hasAge = itemTag.contains("Age", Tag.TAG_INT);
            boolean hasIsBaby = itemTag.contains("IsBaby", Tag.TAG_BYTE);

            if (hasAge) {
                int age = itemTag.getInt("Age");
                if (hasIsBaby) {
                    boolean isBaby = itemTag.getBoolean("IsBaby");
                    if (isBaby && age >= 0) age = -24000;
                    if (!isBaby && age < 0) age = 0;
                }
                ageable.setAge(age);
            } else if (hasIsBaby) {
                ageable.setBaby(itemTag.getBoolean("IsBaby"));
            }
        }
    }


    private void postSpawnSideEffects(ServerLevel level, @javax.annotation.Nullable Player player, ItemStack stack, BlockPos posOrEntityPos) {
        stack.shrink(1);
        if (player != null) player.awardStat(Stats.ITEM_USED.get(this));
        level.gameEvent(player, GameEvent.ENTITY_PLACE, posOrEntityPos);
    }

    private boolean spawn(ServerLevel level, @javax.annotation.Nullable Player player, ItemStack stack, BlockPos pos) {
        EntityType<?> type = resolveEntityType(stack.getItem());

        Entity e = type.spawn(level, stack, player, pos, MobSpawnType.SPAWN_EGG, false, false);
        if (e == null) return false;


        if (e instanceof BaseChickenEntity mob) {
            mob.setPersistenceRequired();
        }
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
    public void appendHoverText(ItemStack itemstack, @org.jetbrains.annotations.Nullable Level world,
                                List<Component> list, TooltipFlag flag) {
        try {
            super.appendHoverText(itemstack, world, list, flag);

            // ---- Max-Werte pro Tier ----
            int maxLevel = switch (currentchickena) {
                case 1 -> Config.ServerConfig.maxlevel_tier_2.get();
                case 2 -> Config.ServerConfig.maxlevel_tier_3.get();
                case 3 -> Config.ServerConfig.maxlevel_tier_4.get();
                case 4 -> Config.ServerConfig.maxlevel_tier_5.get();
                case 5 -> Config.ServerConfig.maxlevel_tier_6.get();
                case 6 -> Config.ServerConfig.maxlevel_tier_7.get();
                case 7 -> Config.ServerConfig.maxlevel_tier_8.get();
                case 8 -> Config.ServerConfig.maxlevel_tier_9.get();
                default -> Config.ServerConfig.maxlevel_tier_1.get();
            };

            int maxXP = switch (currentchickena) {
                case 1 -> Config.ServerConfig.xp_tier_2.get();
                case 2 -> Config.ServerConfig.xp_tier_3.get();
                case 3 -> Config.ServerConfig.xp_tier_4.get();
                case 4 -> Config.ServerConfig.xp_tier_5.get();
                case 5 -> Config.ServerConfig.xp_tier_6.get();
                case 6 -> Config.ServerConfig.xp_tier_7.get();
                case 7 -> Config.ServerConfig.xp_tier_8.get();
                case 8 -> Config.ServerConfig.xp_tier_9.get();
                default -> Config.ServerConfig.xp_tier_1.get();
            };

            // ---- 1.20.1: Level/XP aus NBT statt DataComponents ----
            int level = 0;
            int xp = 0;

            var tag = itemstack.getTag(); // kann null sein
            if (tag != null) {
                if (tag.contains("roost_lvl", net.minecraft.nbt.Tag.TAG_INT)) {
                    level = tag.getInt("roost_lvl");
                }
                if (tag.contains("roost_xp", net.minecraft.nbt.Tag.TAG_INT)) {
                    xp = tag.getInt("roost_xp");
                }
            }

            // ---- Tooltip Text (deine Translation Keys bleiben) ----
            list.add(Component.translatable("roost_chicken.chickeninfo.tier", currentchickena + 1));
            list.add(Component.translatable("roost_chicken.chickeninfo.level", level, maxLevel));
            list.add(Component.translatable("roost_chicken.chickeninfo.xp", xp, maxXP));

            if (world == null || world.isClientSide) {
                var recipes = ClientRoostCache.getRecipes(itemstack);
                if (!recipes.isEmpty()) {
                    list.add(Component.literal("\u0000ROOST_OUTPUT_MARKER"));
                    list.add(Component.translatable("roost_chicken.roostinfo.title"));
                }
            }
            // ---- Biome-Info: nur clientseitig sinnvoll ----
            // In 1.20.1: appendHoverText läuft auch serverseitig. Cache nur auf Client nutzen!
            if (world == null || world.isClientSide) {
                String itemId = net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(itemstack.getItem()).toString();
                java.util.List<String> biomes = ClientBiomeCache.getBiomes(itemId);

                if (!biomes.isEmpty()) {
                    list.add(Component.translatable("roost_chicken.biomeinfo.spawn"));
                    for (String biome : biomes) {
                        try {
                            // biome: "minecraft:plains" -> key "biome.minecraft.plains"
                            String key = "biome." + biome.replace(":", ".");
                            list.add(Component.literal(" - ").append(Component.translatable(key)));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    list.add(Component.translatable("roost_chicken.biomeinfo.nospawn"));
                }
            }

            // ---- Breeding-Info (Client only) ----
            if (world == null || world.isClientSide) {

                var recipes = ClientBreedingCache.getRecipes(itemstack);

                if (!recipes.isEmpty()) {
                    list.add(Component.translatable("roost_chicken.breedinginfo.title"));

                    for (var recipe : recipes) {
                        list.add(Component.literal(" §7• ")
                                .append(Component.translatable("roost_chicken.breedinginfo.recipe")));

                        addIngredientLine(list, recipe.ingredient0());
                        addIngredientLine(list, recipe.ingredient1());
                        addIngredientLine(list, recipe.ingredient2());
                    }
                } else {
                    list.add(Component.translatable("roost_chicken.breedinginfo.none"));
                }
            }

            list.add(Component.literal("§1 Roost Ultimate"));
        } catch (Exception e) {
            System.out.println("Error in Tooltip:");
            e.printStackTrace();
        }
    }

    private static void addIngredientLine(List<Component> list, Ingredient ingredient) {
        ItemStack[] stacks = ingredient.getItems();
        if (stacks.length > 0) {
            list.add(Component.literal("   - ")
                    .append(stacks[0].getHoverName()));
        }
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
        return RenderUtils.getCurrentTick();
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private AnimatedChickenRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if(this.renderer == null) {
                    renderer = new AnimatedChickenRenderer();
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