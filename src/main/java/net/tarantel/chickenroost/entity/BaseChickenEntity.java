package net.tarantel.chickenroost.entity;

import java.lang.reflect.Field;
import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.item.ModItems;
import net.tarantel.chickenroost.recipes.BreederRecipe;
import net.tarantel.chickenroost.util.ChickenConfig;
import org.jetbrains.annotations.NotNull;

public class BaseChickenEntity extends Chicken {
   private int eggTime;
   private final ItemStack dropStack;
   private final Boolean IS_FIRE;
   private final Boolean IS_PROJECTILE;
   private final Boolean IS_EXPLOSION;
   private final Boolean IS_FALL;
   private final Boolean IS_DROWNING;
   private final Boolean IS_FREEZING;
   private final Boolean IS_LIGHTNING;
   private final Boolean IS_WITHER;
   private final int TIER;
   private int newEggTime;
   private static final Field VANILLA_EGG_TIME;
   private final int configuredEggTime;

   public BaseChickenEntity(EntityType<BaseChickenEntity> type, Level world) {
      super(type, world);
      this.xpReward = 0;
      this.setNoAi(false);
      this.setPersistenceRequired();
      this.dropStack = ChickenConfig.getDropStack(type);
      this.eggTime = this.random.nextInt(ChickenConfig.getEggTime(type)) + 6000;
      this.newEggTime = this.random.nextInt(ChickenConfig.getEggTime(type)) + 6000;
      this.configuredEggTime = this.random.nextInt(ChickenConfig.getEggTime(type)) + 6000;
      this.IS_FIRE = ChickenConfig.getIsFire(type);
      this.IS_PROJECTILE = ChickenConfig.getIsProjectile(type);
      this.IS_EXPLOSION = ChickenConfig.getIsExplosion(type);
      this.IS_FALL = ChickenConfig.getIsFall(type);
      this.IS_DROWNING = ChickenConfig.getIsDrowning(type);
      this.IS_FREEZING = ChickenConfig.getIsFreezing(type);
      this.IS_LIGHTNING = ChickenConfig.getIsLightning(type);
      this.IS_WITHER = ChickenConfig.getIsWither(type);
      this.setPathfindingMalus(PathType.WATER, 0.0F);
      this.TIER = ChickenConfig.getTier(type);
   }

   public boolean isFood(@NotNull ItemStack stack) {
      return switch (this.TIER) {
         case 2 -> stack.is(ModItems.CHICKEN_FOOD_TIER_2);
         case 3 -> stack.is(ModItems.CHICKEN_FOOD_TIER_3);
         case 4 -> stack.is(ModItems.CHICKEN_FOOD_TIER_4);
         case 5 -> stack.is(ModItems.CHICKEN_FOOD_TIER_5);
         case 6 -> stack.is(ModItems.CHICKEN_FOOD_TIER_6);
         case 7 -> stack.is(ModItems.CHICKEN_FOOD_TIER_7);
         case 8 -> stack.is(ModItems.CHICKEN_FOOD_TIER_8);
         case 9 -> stack.is(ModItems.CHICKEN_FOOD_TIER_9);
         default -> stack.is(ModItems.CHICKEN_FOOD_TIER_1);
      };
   }

   public Chicken getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob otherParent) {
      if (!(otherParent instanceof BaseChickenEntity parent2)) {
         Chicken fallback = (Chicken)EntityType.CHICKEN.create(level);
         if (fallback != null) {
            fallback.setAge(-24000);
         }

         return fallback;
      } else {
         ResourceLocation id1 = EntityType.getKey(this.getType());
         ResourceLocation id2 = EntityType.getKey(parent2.getType());
         ItemStack stack1 = new ItemStack((ItemLike)BuiltInRegistries.ITEM.get(id1));
         ItemStack stack2 = new ItemStack((ItemLike)BuiltInRegistries.ITEM.get(id2));

         for (RecipeHolder<BreederRecipe> holder : level.getRecipeManager().getAllRecipesFor(BreederRecipe.Type.INSTANCE)) {
            BreederRecipe recipe = (BreederRecipe)holder.value();
            boolean match = recipe.ingredient1().test(stack1) && recipe.ingredient2().test(stack2)
               || recipe.ingredient1().test(stack2) && recipe.ingredient2().test(stack1);
            if (match) {
               ItemStack result = recipe.getResultItem(level.registryAccess());
               ResourceLocation resultId = BuiltInRegistries.ITEM.getKey(result.getItem());
               EntityType<?> rawType = (EntityType<?>)BuiltInRegistries.ENTITY_TYPE.get(resultId);
               BaseChickenEntity child = (BaseChickenEntity)rawType.create(level);
               if (child != null) {
                  child.setAge(-24000);
                  ChickenRoostMod.LOGGER.info("Breeding success: {} + {} -> {}", new Object[]{id1, id2, resultId});
                  return child;
               }

               ChickenRoostMod.LOGGER.warn("Failed to create child entity for resultId {}", resultId);
            }
         }

         Chicken fallback = (Chicken)EntityType.CHICKEN.create(level);
         if (fallback != null) {
            fallback.setAge(-24000);
         }

         ChickenRoostMod.LOGGER.info("No breeding recipe matched: {} + {} -> fallback CHICKEN", id1, id2);
         return fallback;
      }
   }

   public boolean checkSpawnRules(LevelAccessor level, @NotNull MobSpawnType spawnType) {
      int max = 10;
      int radius = 20;
      BlockPos pos = this.blockPosition();
      long count = level.getEntitiesOfClass(this.getClass(), new AABB(pos).inflate(16.0 * radius)).size();
      return count < max && super.checkSpawnRules(level, spawnType);
   }

   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(1, new RandomStrollGoal(this, 1.0));
      this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
      this.goalSelector.addGoal(3, new FloatGoal(this));
   }

   public boolean removeWhenFarAway(double distanceToClosestPlayer) {
      return false;
   }

   protected void dropCustomDeathLoot(@NotNull ServerLevel serverLevel, @NotNull DamageSource source, boolean recentlyHitIn) {
      super.dropCustomDeathLoot(serverLevel, source, recentlyHitIn);
   }

   @NotNull
   public EntityDimensions getDefaultDimensions(@NotNull Pose pose) {
      return this.isBaby() ? this.getType().getDimensions().scale(0.5F).withEyeHeight(0.2975F) : super.getDefaultDimensions(pose);
   }

   public ItemEntity spawnAtLocation(@NotNull ItemStack stack) {
      if (stack.is(Items.EGG)) {
         return !this.dropStack.isEmpty() ? super.spawnAtLocation(this.dropStack.copy()) : null;
      } else {
         return super.spawnAtLocation(stack);
      }
   }

   public ItemEntity spawnAtLocation(@NotNull ItemLike itemLike) {
      return this.spawnAtLocation(new ItemStack(itemLike));
   }

   public void aiStep() {
      super.aiStep();
      this.oFlap = this.flap;
      this.oFlapSpeed = this.flapSpeed;
      this.flapSpeed = this.flapSpeed + (this.onGround() ? -1.0F : 4.0F) * 0.3F;
      this.flapSpeed = Mth.clamp(this.flapSpeed, 0.0F, 1.0F);
      if (!this.onGround() && this.flapping < 1.0F) {
         this.flapping = 1.0F;
      }

      this.flapping *= 0.9F;
      Vec3 vec3 = this.getDeltaMovement();
      if (!this.onGround() && vec3.y < 0.0) {
         this.setDeltaMovement(vec3.multiply(1.0, 0.6, 1.0));
      }

      this.flap = this.flap + this.flapping * 2.0F;
      if (!this.level().isClientSide && this.isAlive() && !this.isBaby() && !this.isChickenJockey() && --this.eggTime <= 0) {
         this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
         this.gameEvent(GameEvent.ENTITY_PLACE);
         this.eggTime = this.configuredEggTime;
      }

      if (!this.level().isClientSide && this.isAlive() && !this.isBaby() && !this.isChickenJockey()) {
         try {
            int vanillaTime = (Integer)VANILLA_EGG_TIME.get(this);
            if (vanillaTime > this.configuredEggTime) {
               VANILLA_EGG_TIME.set(this, this.configuredEggTime);
            }
         } catch (IllegalAccessException var3) {
            ChickenRoostMod.LOGGER.error("Failed to modify Chicken eggTime", var3);
         }
      }
   }

   public int getBaseExperienceReward() {
      return this.isChickenJockey() ? 10 : super.getBaseExperienceReward();
   }

   public void readAdditionalSaveData(@NotNull CompoundTag p_28243_) {
      super.readAdditionalSaveData(p_28243_);
      this.isChickenJockey = p_28243_.getBoolean("IsChickenJockey");
      if (p_28243_.contains("EggLayTime")) {
         this.eggTime = p_28243_.getInt("EggLayTime");
      }
   }

   public void addAdditionalSaveData(@NotNull CompoundTag p_28257_) {
      super.addAdditionalSaveData(p_28257_);
      p_28257_.putBoolean("IsChickenJockey", this.isChickenJockey);
      p_28257_.putInt("EggLayTime", this.eggTime);
   }

   @NotNull
   public SoundEvent getAmbientSound() {
      return Objects.requireNonNull((SoundEvent)BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.withDefaultNamespace("entity.chicken.ambient")));
   }

   public void playStepSound(@NotNull BlockPos pos, @NotNull BlockState blockIn) {
      this.playSound(
         Objects.requireNonNull((SoundEvent)BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.withDefaultNamespace("entity.chicken.step"))), 0.15F, 1.0F
      );
   }

   @NotNull
   public SoundEvent getHurtSound(@NotNull DamageSource ds) {
      return Objects.requireNonNull((SoundEvent)BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.withDefaultNamespace("entity.chicken.hurt")));
   }

   @NotNull
   public SoundEvent getDeathSound() {
      return Objects.requireNonNull((SoundEvent)BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.withDefaultNamespace("entity.chicken.death")));
   }

   public boolean hurt(@NotNull DamageSource damageSource, float amount) {
      if (isFireLike(damageSource)) {
         return this.IS_FIRE && super.hurt(damageSource, amount);
      } else if (isProjectileLike(damageSource)) {
         return this.IS_PROJECTILE && super.hurt(damageSource, amount);
      } else if (isExplosionLike(damageSource)) {
         return this.IS_EXPLOSION && super.hurt(damageSource, amount);
      } else if (damageSource.is(DamageTypes.FALL)) {
         return this.IS_FALL && super.hurt(damageSource, amount);
      } else if (damageSource.is(DamageTypes.DROWN)) {
         return this.IS_DROWNING && super.hurt(damageSource, amount);
      } else if (damageSource.is(DamageTypes.FREEZE)) {
         return this.IS_FREEZING && super.hurt(damageSource, amount);
      } else if (damageSource.is(DamageTypes.LIGHTNING_BOLT)) {
         return this.IS_LIGHTNING && super.hurt(damageSource, amount);
      } else {
         return !isAny(damageSource, DamageTypes.WITHER, DamageTypes.WITHER_SKULL)
            ? super.hurt(damageSource, amount)
            : this.IS_WITHER && super.hurt(damageSource, amount);
      }
   }

   private static boolean isFireLike(DamageSource src) {
      return src.is(DamageTypeTags.IS_FIRE) || isAny(src, DamageTypes.FIREBALL, DamageTypes.UNATTRIBUTED_FIREBALL, DamageTypes.FIREWORKS);
   }

   private static boolean isProjectileLike(DamageSource src) {
      return src.is(DamageTypeTags.IS_PROJECTILE) || isAny(src, DamageTypes.ARROW, DamageTypes.MOB_PROJECTILE);
   }

   private static boolean isExplosionLike(DamageSource src) {
      return src.is(DamageTypeTags.IS_EXPLOSION) || isAny(src, DamageTypes.PLAYER_EXPLOSION);
   }

   @SafeVarargs
   private static boolean isAny(DamageSource src, ResourceKey<DamageType>... keys) {
      for (ResourceKey<DamageType> k : keys) {
         if (src.is(k)) {
            return true;
         }
      }

      return false;
   }

   public static void init() {
   }

   public static @NotNull Builder createAttributes() {
      Builder builder = Mob.createMobAttributes();
      builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
      builder = builder.add(Attributes.MAX_HEALTH, 3.0);
      builder = builder.add(Attributes.ARMOR, 0.0);
      builder = builder.add(Attributes.ATTACK_DAMAGE, 0.0);
      return builder.add(Attributes.FOLLOW_RANGE, 16.0);
   }

   static {
      try {
         VANILLA_EGG_TIME = Chicken.class.getDeclaredField("eggTime");
         VANILLA_EGG_TIME.setAccessible(true);
      } catch (NoSuchFieldException var1) {
         throw new RuntimeException("Cannot find Chicken.eggTime field!", var1);
      }
   }
}
