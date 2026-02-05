package net.tarantel.chickenroost.item.base;

import com.google.common.base.Suppliers;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.tarantel.chickenroost.ChickenRoostMod;
//import net.tarantel.chickenroost.client.ClientBreedingCache;
import net.tarantel.chickenroost.item.renderer.AnimatedChickenRenderer;
import net.tarantel.chickenroost.recipes.RoostRecipe;
import net.tarantel.chickenroost.util.ClientBiomeCache;
import net.tarantel.chickenroost.util.Config;
import net.tarantel.chickenroost.util.ModDataComponents;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;

import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.animation.object.PlayState;
import software.bernie.geckolib.animation.state.AnimationTest;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtil;


import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class AnimatedChicken extends ChickenItemBase implements GeoItem {
    private static final String NBT_LEVEL_KEY = "chickenlevel";
    private static final String NBT_XP_KEY    = "chickenxp";
    private static final String NBT_AGE_KEY    = "age";
    private static final Identifier C_VANILLA_ID =
            Identifier.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_vanilla");

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
            Identifier id = Identifier.parse(
                    item.getDefaultInstance()
                            .typeHolder()
                            .getRegisteredName()
            );

            Optional<EntityType<?>> resolved =
                    BuiltInRegistries.ENTITY_TYPE
                            .get(id)
                            .map(h -> h.value());

            return resolved.orElse(EntityType.CHICKEN);
        }

        return EntityType.CHICKEN;
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

        Entity e = type.spawn(level, stack, player, pos, EntitySpawnReason.SPAWN_ITEM_USE, false, false);
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
    public @NotNull InteractionResult use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        BlockHitResult hit = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);

        if (hit.getType() != HitResult.Type.BLOCK) return InteractionResult.PASS;
        if (!(level.getBlockState(hit.getBlockPos()).getBlock() instanceof LiquidBlock)) return InteractionResult.PASS;

        if (!(level instanceof ServerLevel server)) return InteractionResult.SUCCESS;

        BlockPos pos = hit.getBlockPos();
        if (!level.mayInteract(player, pos) || !player.mayUseItemAt(pos, hit.getDirection(), stack)) {
            return InteractionResult.FAIL;
        }

        boolean ok = spawn(server, player, stack, pos);
        return ok ? InteractionResult.CONSUME : InteractionResult.PASS;
    }


    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, TooltipDisplay tooltipDisplay, Consumer<Component> components, TooltipFlag tooltipFlag) {
        try {
        super.appendHoverText(pStack, pContext,tooltipDisplay, components, tooltipFlag);

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
            Level world = pContext.level();
        int level = 0;
        int xp = 0;
        if(pStack.has(ModDataComponents.CHICKENLEVEL)){
            level = pStack.get(ModDataComponents.CHICKENLEVEL.value());
        }
        if(pStack.has(ModDataComponents.CHICKENXP)){
            xp = pStack.get(ModDataComponents.CHICKENXP.value());
        }
            components.accept(Component.translatable(
                    "roost_chicken.chickeninfo.tier",
                    currentchickena + 1
            ));

            components.accept(Component.translatable(
                    "roost_chicken.chickeninfo.level",
                    level, maxLevel
            ));

            components.accept(Component.translatable(
                    "roost_chicken.chickeninfo.xp",
                    xp, maxXP
            ));


            if (world == null || world.isClientSide()) {
                String itemId = BuiltInRegistries.ITEM.getKey(pStack.getItem()).toString();
                List<String> biomes = ClientBiomeCache.getBiomes(itemId);

                if (!biomes.isEmpty()) {
                    components.accept(Component.translatable("roost_chicken.biomeinfo.spawn"));
                    for (String biome : biomes) {
                        try {

                            String key = "biome." + biome.replace(":", ".");
                            components.accept(Component.literal(" - ").append(Component.translatable(key)));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    components.accept(Component.translatable("roost_chicken.biomeinfo.nospawn"));
                }
            }


            /*if (world == null || world.isClientSide()) {

                var recipes = ClientBreedingCache.getRecipes(pStack);

                if (!recipes.isEmpty()) {
                    components.accept(Component.translatable("roost_chicken.breedinginfo.title"));

                    for (var recipe : recipes) {
                        components.accept(Component.literal(" §7• ")
                                .append(Component.translatable("roost_chicken.breedinginfo.recipe")));

                        addIngredientLine((List<Component>) components, recipe.ingredient0());
                        addIngredientLine((List<Component>) components, recipe.ingredient1());
                        addIngredientLine((List<Component>) components, recipe.ingredient2());
                    }
                } else {
                    components.accept(Component.translatable("roost_chicken.breedinginfo.none"));
                }
            }*/

            components.accept(Component.literal("§1 Roost Ultimate"));
        } catch (Exception e) {
            System.out.println("Error in Tooltip:");
            e.printStackTrace();
        }
    }

    private static Component buildRoostOutputLine(RoostRecipe recipe) {

        ItemStack output = recipe.getResultItem(null).copy();
        output.setCount(1);

        MutableComponent line = Component.literal(" ");
        line.append(output.getHoverName());

        return line.withStyle(ChatFormatting.GRAY);
    }

    /*private static void addIngredientLine(List<Component> list, Ingredient ingredient) {
        SlotDisplay display = ingredient.display();

        Component text = switch (display) {
            case SlotDisplay.ItemSlotDisplay item ->
                    item.item().value().getName();
            case SlotDisplay.ItemStackSlotDisplay stack ->
                    stack.stack();
            case SlotDisplay.TagSlotDisplay tag ->
                    Component.literal("#").append(
                            Component.literal(tag.tag().location().toString())
                    );
            default ->
                    Component.literal("?");
        };

        list.add(Component.literal("   - ").append(text));
    }*/



    /*private PlayState predicate(AnimationState animationState) {
        animationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }*/


    private static final RawAnimation POPUP_ANIM = RawAnimation.begin().thenLoop("idle");


    //@Override
    /*public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>("controller", 0, animTest -> {
            final ItemDisplayContext context = animTest.getData(DataTickets.ITEM_RENDER_PERSPECTIVE);


                return PlayState.CONTINUE;


        }).receiveTriggeredAnimations()
                .triggerableAnim("IDLE", POPUP_ANIM));


    }*/

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>("controller", 0, this::predicate));
    }

    private PlayState predicate(AnimationTest<GeoAnimatable> state) {
        state.controller().setAnimation(RawAnimation.begin().thenLoop("idle"));
        return PlayState.CONTINUE;
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
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            // Defer creation of our renderer then cache it so that it doesn't get instantiated too early
            private final Supplier<AnimatedChickenRenderer> renderer = Suppliers.memoize(AnimatedChickenRenderer::new);

            @Override
            @Nullable
            public GeoItemRenderer<AnimatedChicken> getGeoItemRenderer() {
                return this.renderer.get();
            }
        });
    }

    /*@Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            // Defer creation of our renderer then cache it so that it doesn't get instantiated too early
            private final Supplier<AnimatedChickenRenderer<?>> renderer = Suppliers.memoize(AnimatedChickenRenderer::new);

            @Nullable
            @Override
            public GeoItemRenderer<?, ?> getGeoItemRenderer() {
                return this.renderer.get();
            }
        });
    }*/

    /*@Override
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
    }*/

    @Override
    public float getDestroySpeed(@NotNull ItemStack par1ItemStack, @NotNull BlockState par2Block) {
        return 0F;
    }
}