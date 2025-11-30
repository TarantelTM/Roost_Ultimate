
package net.tarantel.chickenroost.entity;

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
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
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

import java.util.Objects;

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

    public BaseChickenEntity(EntityType<BaseChickenEntity> type, Level world) {
        super(type, world);
        this.xpReward = 0;
        this.setNoAi(false);
        this.setPersistenceRequired();
        this.dropStack = ChickenConfig.getDropStack(type);
        this.eggTime = ChickenConfig.getEggTime(type);

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


    @Override
    public boolean isFood(@NotNull ItemStack stack) {
        return switch (this.TIER){
            case 2 -> (stack.is(ModItems.CHICKEN_FOOD_TIER_2));
            case 3 -> (stack.is(ModItems.CHICKEN_FOOD_TIER_3));
            case 4 -> (stack.is(ModItems.CHICKEN_FOOD_TIER_4));
            case 5 -> (stack.is(ModItems.CHICKEN_FOOD_TIER_5));
            case 6 -> (stack.is(ModItems.CHICKEN_FOOD_TIER_6));
            case 7 -> (stack.is(ModItems.CHICKEN_FOOD_TIER_7));
            case 8 -> (stack.is(ModItems.CHICKEN_FOOD_TIER_8));
            case 9 -> (stack.is(ModItems.CHICKEN_FOOD_TIER_9));
            default -> (stack.is(ModItems.CHICKEN_FOOD_TIER_1));
        };

    }

    @Override
    public Chicken getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob otherParent) {
        if (!(otherParent instanceof BaseChickenEntity parent2)) {

            Chicken fallback = EntityType.CHICKEN.create(level);
            if (fallback != null) fallback.setAge(-24000);
            return fallback;
        }


        ResourceLocation id1 = EntityType.getKey(this.getType());
        ResourceLocation id2 = EntityType.getKey(parent2.getType());


        ItemStack stack1 = new ItemStack(BuiltInRegistries.ITEM.get(id1));
        ItemStack stack2 = new ItemStack(BuiltInRegistries.ITEM.get(id2));


        for (RecipeHolder<BreederRecipe> holder : level.getRecipeManager().getAllRecipesFor(BreederRecipe.Type.INSTANCE)) {
            BreederRecipe recipe = holder.value();

            boolean match = (recipe.ingredient1().test(stack1) && recipe.ingredient2().test(stack2)) ||
                    (recipe.ingredient1().test(stack2) && recipe.ingredient2().test(stack1));

            if (match) {
                ItemStack result = recipe.getResultItem(level.registryAccess());
                ResourceLocation resultId = BuiltInRegistries.ITEM.getKey(result.getItem());
                EntityType<?> rawType = BuiltInRegistries.ENTITY_TYPE.get(resultId);

                @SuppressWarnings("unchecked")
                EntityType<? extends BaseChickenEntity> childType = (EntityType<? extends BaseChickenEntity>) rawType;

                BaseChickenEntity child = childType.create(level);
                if (child != null) {
                    child.setAge(-24000);
                    ChickenRoostMod.LOGGER.info("Breeding success: {} + {} -> {}", id1, id2, resultId);
                    return child;
                } else {
                    ChickenRoostMod.LOGGER.warn("Failed to create child entity for resultId {}", resultId);
                }
            }
        }


        Chicken fallback = EntityType.CHICKEN.create(level);
        if (fallback != null) fallback.setAge(-24000);
        ChickenRoostMod.LOGGER.info("No breeding recipe matched: {} + {} -> fallback CHICKEN", id1, id2);
        return fallback;
    }

    @Override
    public boolean checkSpawnRules(LevelAccessor level, @NotNull MobSpawnType spawnType) {
        int max = 10;
        int radius = 20;
        BlockPos pos = this.blockPosition();

        long count = level.getEntitiesOfClass(
                this.getClass(),
                new AABB(pos).inflate(16.0D * radius)
        ).size();

        return count < max && super.checkSpawnRules(level, spawnType);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new RandomStrollGoal(this, 1));
        this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(3, new FloatGoal(this));
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    protected void dropCustomDeathLoot(@NotNull ServerLevel serverLevel, @NotNull DamageSource source, boolean recentlyHitIn) {
        super.dropCustomDeathLoot(serverLevel, source, recentlyHitIn);
        this.spawnAtLocation(dropStack);
    }

    @Override
    public @NotNull EntityDimensions getDefaultDimensions(@NotNull Pose pose) {
        if (this.isBaby()) {

            return this.getType().getDimensions().scale(0.5F).withEyeHeight(0.2975F);
        }
        return super.getDefaultDimensions(pose);
    }
    @Override
    public void aiStep() {
        super.aiStep();
        this.oFlap = this.flap;
        this.oFlapSpeed = this.flapSpeed;
        this.flapSpeed += (this.onGround() ? -1.0F : 4.0F) * 0.3F;
        this.flapSpeed = Mth.clamp(this.flapSpeed, 0.0F, 1.0F);
        if (!this.onGround() && this.flapping < 1.0F) {
            this.flapping = 1.0F;
        }

        this.flapping *= 0.9F;
        Vec3 vec3 = this.getDeltaMovement();
        if (!this.onGround() && vec3.y < 0.0D) {
            this.setDeltaMovement(vec3.multiply(1.0D, 0.6D, 1.0D));
        }

        this.flap += this.flapping * 2.0F;
        if (!this.level().isClientSide && this.isAlive() && !this.isBaby() && !this.isChickenJockey() && --this.eggTime <= 0) {
            this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.spawnAtLocation(dropStack);
            this.gameEvent(GameEvent.ENTITY_PLACE);
            this.eggTime = this.random.nextInt(6000) + 6000;
        }

    }
    @Override
    public int getBaseExperienceReward() {
        return this.isChickenJockey() ? 10 : super.getBaseExperienceReward();
    }
    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag p_28243_) {
        super.readAdditionalSaveData(p_28243_);
        this.isChickenJockey = p_28243_.getBoolean("IsChickenJockey");
        if (p_28243_.contains("EggLayTime")) {
            this.eggTime = p_28243_.getInt("EggLayTime");
        }

    }
    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag p_28257_) {
        super.addAdditionalSaveData(p_28257_);
        p_28257_.putBoolean("IsChickenJockey", this.isChickenJockey);
        p_28257_.putInt("EggLayTime", this.eggTime);
    }
    @Override
    public @NotNull SoundEvent getAmbientSound() {
        return Objects.requireNonNull(BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.withDefaultNamespace("entity.chicken.ambient")));
    }

    @Override
    public void playStepSound(@NotNull BlockPos pos, @NotNull BlockState blockIn) {
        this.playSound(Objects.requireNonNull(BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.withDefaultNamespace("entity.chicken.step"))), 0.15f, 1);
    }

    @Override
    public @NotNull SoundEvent getHurtSound(@NotNull DamageSource ds) {
        return Objects.requireNonNull(BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.withDefaultNamespace("entity.chicken.hurt")));
    }

    @Override
    public @NotNull SoundEvent getDeathSound() {
        return Objects.requireNonNull(BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.withDefaultNamespace("entity.chicken.death")));
    }

    @Override
    public boolean hurt(@NotNull DamageSource damageSource, float amount) {
        if (isFireLike(damageSource))              return IS_FIRE       && super.hurt(damageSource, amount);
        if (isProjectileLike(damageSource))       return IS_PROJECTILE && super.hurt(damageSource, amount);
        if (isExplosionLike(damageSource))        return IS_EXPLOSION  && super.hurt(damageSource, amount);
        if (damageSource.is(DamageTypes.FALL))    return IS_FALL       && super.hurt(damageSource, amount);
        if (damageSource.is(DamageTypes.DROWN))   return IS_DROWNING   && super.hurt(damageSource, amount);
        if (damageSource.is(DamageTypes.FREEZE))  return IS_FREEZING   && super.hurt(damageSource, amount);
        if (damageSource.is(DamageTypes.LIGHTNING_BOLT))
            return IS_LIGHTNING  && super.hurt(damageSource, amount);
        if (isAny(damageSource, DamageTypes.WITHER, DamageTypes.WITHER_SKULL))
            return IS_WITHER     && super.hurt(damageSource, amount);
        return super.hurt(damageSource, amount);
    }


    private static boolean isFireLike(DamageSource src) {
        return src.is(DamageTypeTags.IS_FIRE) || isAny(src,
                DamageTypes.FIREBALL, DamageTypes.UNATTRIBUTED_FIREBALL, DamageTypes.FIREWORKS);
    }


    private static boolean isProjectileLike(DamageSource src) {
        return src.is(DamageTypeTags.IS_PROJECTILE) || isAny(src,
                DamageTypes.ARROW, DamageTypes.MOB_PROJECTILE);
    }


    private static boolean isExplosionLike(DamageSource src) {
        return src.is(DamageTypeTags.IS_EXPLOSION) || isAny(src, DamageTypes.PLAYER_EXPLOSION);
    }

    @SafeVarargs
    private static boolean isAny(DamageSource src, ResourceKey<DamageType>... keys) {
        for (ResourceKey<DamageType> k : keys) if (src.is(k)) return true;
        return false;
    }

    public static void init() {
    }
    public static AttributeSupplier.@NotNull Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
        builder = builder.add(Attributes.MAX_HEALTH, 3);
        builder = builder.add(Attributes.ARMOR, 0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 0);
        builder = builder.add(Attributes.FOLLOW_RANGE, 16);
        return builder;
    }
}
