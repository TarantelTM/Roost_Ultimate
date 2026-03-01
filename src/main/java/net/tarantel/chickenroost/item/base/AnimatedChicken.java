package net.tarantel.chickenroost.item.base;

import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
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
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.tarantel.chickenroost.client.ClientBreedingCache;
import net.tarantel.chickenroost.item.renderer.AnimatedChickenRenderer;
import net.tarantel.chickenroost.recipes.BreederRecipe;
import net.tarantel.chickenroost.recipes.RoostRecipe;
import net.tarantel.chickenroost.util.ClientBiomeCache;
import net.tarantel.chickenroost.util.Config;
import net.tarantel.chickenroost.util.ModDataComponents;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.animation.Animation.LoopType;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtil;

public class AnimatedChicken extends ChickenItemBase implements GeoItem {
   private static final String NBT_LEVEL_KEY = "chickenlevel";
   private static final String NBT_XP_KEY = "chickenxp";
   private static final String NBT_AGE_KEY = "age";
   private static final ResourceLocation C_VANILLA_ID = ResourceLocation.fromNamespaceAndPath("chicken_roost", "c_vanilla");
   private final String localpath;
   private final int currentchickena;
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

   public AnimatedChicken(Properties properties, String path, int currentchicken) {
      super(properties, currentchicken);
      this.localpath = path;
      this.currentchickena = currentchicken;
   }

   public String getLocalpath() {
      return this.localpath;
   }

   public static BlockPos rightposi(BlockPos blockPos, Direction direction) {
      int[] xside = new int[]{0, 0, 0, 0, -1, 1};
      int[] yside = new int[]{-1, 1, 0, 0, 0, 0};
      int[] zside = new int[]{0, 0, -1, 1, 0, 0};
      int x = blockPos.getX() + xside[direction.ordinal()];
      int y = blockPos.getY() + yside[direction.ordinal()];
      int z = blockPos.getZ() + zside[direction.ordinal()];
      return new BlockPos(x, y, z);
   }

   @Nullable
   private static ServerLevel asServer(Level level) {
      return level instanceof ServerLevel s ? s : null;
   }

   private static boolean isVanillaChickenItem(Item item) {
      return BuiltInRegistries.ITEM.getKey(item).equals(C_VANILLA_ID);
   }

   private static EntityType<?> resolveEntityType(Item item) {
      if (item instanceof ChickenItemBase && !isVanillaChickenItem(item)) {
         ResourceLocation id = ResourceLocation.parse(item.getDefaultInstance().getItemHolder().getRegisteredName());
         return (EntityType<?>)BuiltInRegistries.ENTITY_TYPE.get(id);
      } else {
         return (EntityType<?>)BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.parse("minecraft:chicken"));
      }
   }

   private static void applyChickenData(Entity entity, ItemStack stack) {
      if (stack.has(ModDataComponents.CHICKENLEVEL)) {
         entity.getPersistentData().putInt("chickenlevel", (Integer)stack.get((DataComponentType)ModDataComponents.CHICKENLEVEL.value()));
      }

      if (stack.has(ModDataComponents.CHICKENXP)) {
         entity.getPersistentData().putInt("chickenxp", (Integer)stack.get((DataComponentType)ModDataComponents.CHICKENXP.value()));
      }

      if (stack.has(ModDataComponents.AGE)) {
         entity.getPersistentData().putInt("age", (Integer)stack.get((DataComponentType)ModDataComponents.AGE.value()));
      }
   }

   private void postSpawnSideEffects(ServerLevel level, @Nullable Player player, ItemStack stack, BlockPos posOrEntityPos) {
      stack.consume(1, player);
      if (player != null) {
         player.awardStat(Stats.ITEM_USED.get(this));
      }

      level.gameEvent(player, GameEvent.ENTITY_PLACE, posOrEntityPos);
   }

   private boolean spawn(ServerLevel level, @Nullable Player player, ItemStack stack, BlockPos pos) {
      EntityType<?> type = resolveEntityType(stack.getItem());
      Entity e = type.spawn(level, stack, player, pos, MobSpawnType.SPAWN_EGG, false, false);
      if (e == null) {
         return false;
      } else {
         applyChickenData(e, stack);
         this.postSpawnSideEffects(level, player, stack, pos);
         return true;
      }
   }

   @NotNull
   public InteractionResult useOn(UseOnContext ctx) {
      ServerLevel server = asServer(ctx.getLevel());
      if (server == null) {
         return InteractionResult.SUCCESS;
      } else {
         ItemStack stack = ctx.getItemInHand();
         BlockPos pos = rightposi(ctx.getClickedPos(), ctx.getClickedFace());
         boolean ok = this.spawn(server, ctx.getPlayer(), stack, pos);
         return ok ? InteractionResult.CONSUME : InteractionResult.PASS;
      }
   }

   @NotNull
   public InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      BlockHitResult hit = getPlayerPOVHitResult(level, player, Fluid.SOURCE_ONLY);
      if (hit.getType() != Type.BLOCK) {
         return InteractionResultHolder.pass(stack);
      } else if (!(level.getBlockState(hit.getBlockPos()).getBlock() instanceof LiquidBlock)) {
         return InteractionResultHolder.pass(stack);
      } else if (level instanceof ServerLevel server) {
         BlockPos pos = hit.getBlockPos();
         if (level.mayInteract(player, pos) && player.mayUseItemAt(pos, hit.getDirection(), stack)) {
            boolean ok = this.spawn(server, player, stack, pos);
            return ok ? InteractionResultHolder.consume(stack) : InteractionResultHolder.pass(stack);
         } else {
            return InteractionResultHolder.fail(stack);
         }
      } else {
         return InteractionResultHolder.success(stack);
      }
   }

   public void appendHoverText(@NotNull ItemStack itemstack, @NotNull TooltipContext context, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
      try {
         super.appendHoverText(itemstack, context, list, flag);

         int maxLevel = switch (this.currentchickena) {
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

         int maxXP = switch (this.currentchickena) {
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
         Level world = context.level();
         int level = 0;
         int xp = 0;
         if (itemstack.has(ModDataComponents.CHICKENLEVEL)) {
            level = (Integer)itemstack.get((DataComponentType)ModDataComponents.CHICKENLEVEL.value());
         }

         if (itemstack.has(ModDataComponents.CHICKENXP)) {
            xp = (Integer)itemstack.get((DataComponentType)ModDataComponents.CHICKENXP.value());
         }

         list.add(Component.translatable("roost_chicken.chickeninfo.tier", new Object[]{this.currentchickena + 1}));
         list.add(Component.translatable("roost_chicken.chickeninfo.level", new Object[]{level, maxLevel}));
         list.add(Component.translatable("roost_chicken.chickeninfo.xp", new Object[]{xp, maxXP}));
         if (world == null || world.isClientSide) {
            String itemId = BuiltInRegistries.ITEM.getKey(itemstack.getItem()).toString();
            List<String> biomes = ClientBiomeCache.getBiomes(itemId);
            if (!biomes.isEmpty()) {
               list.add(Component.translatable("roost_chicken.biomeinfo.spawn"));

               for (String biome : biomes) {
                  try {
                     String key = "biome." + biome.replace(":", ".");
                     list.add(Component.literal(" - ").append(Component.translatable(key)));
                  } catch (Exception var15) {
                     var15.printStackTrace();
                  }
               }
            } else {
               list.add(Component.translatable("roost_chicken.biomeinfo.nospawn"));
            }
         }

         if (world == null || world.isClientSide) {
            List<BreederRecipe> recipes = ClientBreedingCache.getRecipes(itemstack);
            if (!recipes.isEmpty()) {
               list.add(Component.translatable("roost_chicken.breedinginfo.title"));

               for (BreederRecipe recipe : recipes) {
                  list.add(Component.literal(" §7• ").append(Component.translatable("roost_chicken.breedinginfo.recipe")));
                  addIngredientLine(list, recipe.ingredient0());
                  addIngredientLine(list, recipe.ingredient1());
                  addIngredientLine(list, recipe.ingredient2());
               }
            } else {
               list.add(Component.translatable("roost_chicken.breedinginfo.none"));
            }
         }

         list.add(Component.literal("§1 Roost Ultimate"));
      } catch (Exception var16) {
         System.out.println("Error in Tooltip:");
         var16.printStackTrace();
      }
   }

   private static Component buildRoostOutputLine(RoostRecipe recipe) {
      ItemStack output = recipe.getResultItem(null).copy();
      output.setCount(1);
      MutableComponent line = Component.literal(" ");
      line.append(output.getHoverName());
      return line.withStyle(ChatFormatting.GRAY);
   }

   private static void addIngredientLine(List<Component> list, Ingredient ingredient) {
      ItemStack[] stacks = ingredient.getItems();
      if (stacks.length > 0) {
         list.add(Component.literal("   - ").append(stacks[0].getHoverName()));
      }
   }

   private PlayState predicate(AnimationState animationState) {
      animationState.getController().setAnimation(RawAnimation.begin().then("idle", LoopType.LOOP));
      return PlayState.CONTINUE;
   }

   public void registerControllers(ControllerRegistrar controllerRegistrar) {
      controllerRegistrar.add(new AnimationController(this, "controller", 0, this::predicate));
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   public double getTick(Object itemStack) {
      return RenderUtil.getCurrentTick();
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept(new IClientItemExtensions() {
         private AnimatedChickenRenderer renderer;

         @NotNull
         public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            if (this.renderer == null) {
               this.renderer = new AnimatedChickenRenderer();
            }

            return this.renderer;
         }
      });
   }

   public float getDestroySpeed(@NotNull ItemStack par1ItemStack, @NotNull BlockState par2Block) {
      return 0.0F;
   }
}
