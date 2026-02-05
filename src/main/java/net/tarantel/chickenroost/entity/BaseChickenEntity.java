
package net.tarantel.chickenroost.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.item.ModItems;
import net.tarantel.chickenroost.recipes.BreederRecipe;
import net.tarantel.chickenroost.util.ChickenConfig;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Optional;

public class BaseChickenEntity extends Chicken {

    private Integer eggTime;

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
    private static <T extends AgeableMob> T createBaby(
            EntityType<T> type,
            ServerLevel level,
            BlockPos pos
    ) {
        T child = type.create(level, EntitySpawnReason.BREEDING);
        if (child == null) return null;

        // Position + Rotation setzen (moveTo gibt’s nicht mehr)
        child.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
        // optional: child.setYRot(...); child.setXRot(...);

        child.setAge(-24000);
        return child;
    }
    @Override
    public Chicken getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob otherParent) {
        BlockPos pos = this.blockPosition();

        if (!(otherParent instanceof BaseChickenEntity parent2)) {
            Chicken fallback = createBaby(EntityType.CHICKEN, level, pos);
            return fallback; // kann null sein, aber MC erwartet i.d.R. nicht-null; s.u.
        }

        Identifier id1 = EntityType.getKey(this.getType());
        Identifier id2 = EntityType.getKey(parent2.getType());

        ItemStack stack1 = new ItemStack(BuiltInRegistries.ITEM.get(id1).get());
        ItemStack stack2 = new ItemStack(BuiltInRegistries.ITEM.get(id2).get());

        RecipeManager recipeManager = level.getServer().getRecipeManager();

        for (RecipeHolder<?> holder : recipeManager.getRecipes()) {
            if (!(holder.value() instanceof BreederRecipe recipe)) continue;

            boolean match =
                    (recipe.ingredient1().test(stack1) && recipe.ingredient2().test(stack2)) ||
                            (recipe.ingredient1().test(stack2) && recipe.ingredient2().test(stack1));

            if (!match) continue;

            ItemStack result = recipe.getResultItem(level.registryAccess());
            Identifier resultId = BuiltInRegistries.ITEM.getKey(result.getItem());

            EntityType<?> rawType =
                    BuiltInRegistries.ENTITY_TYPE.get(resultId).orElseThrow().value();


            @SuppressWarnings("unchecked")
            EntityType<? extends BaseChickenEntity> childType =
                    (EntityType<? extends BaseChickenEntity>) rawType;

            BaseChickenEntity child =
                    (BaseChickenEntity) createBaby((EntityType) childType, level, pos);

            if (child != null) {
                return child;
            }
        }


        Chicken fallback = createBaby(EntityType.CHICKEN, level, pos);
        return fallback;
    }

    @Override
    public boolean checkSpawnRules(LevelAccessor level, @NotNull EntitySpawnReason spawnType) {
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
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.4));
        this.goalSelector.addGoal(2, new BreedGoal(this, (double)1.0F));
        this.goalSelector.addGoal(3, new TemptGoal(this, (double)1.0F, (p_481852_) -> p_481852_.is(ItemTags.CHICKEN_FOOD), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, (double)1.0F));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    protected void dropCustomDeathLoot(@NotNull ServerLevel serverLevel, @NotNull DamageSource source, boolean recentlyHitIn) {
        super.dropCustomDeathLoot(serverLevel, source, recentlyHitIn);
    }

    @Override
    public @NotNull EntityDimensions getDefaultDimensions(@NotNull Pose pose) {
        if (this.isBaby()) {

            return this.getType().getDimensions().scale(0.5F).withEyeHeight(0.2975F);
        }
        return super.getDefaultDimensions(pose);
    }

    @Override
    public ItemEntity spawnAtLocation(ServerLevel level,@NotNull ItemStack stack) {
        if (stack.is(Items.EGG)) {
            if (!dropStack.isEmpty()) {
                return super.spawnAtLocation(level, dropStack.copy());
            }
            return null;
        }

        return super.spawnAtLocation(level, stack);
    }
    @Override
    public ItemEntity spawnAtLocation(ServerLevel level, @NotNull ItemLike itemLike) {
        return this.spawnAtLocation(level, new ItemStack(itemLike).getItem());
    }
    private static final Field VANILLA_EGG_TIME;
    static {
        try {
            VANILLA_EGG_TIME = Chicken.class.getDeclaredField("eggTime");
            VANILLA_EGG_TIME.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Cannot find Chicken.eggTime field!", e);
        }
    }

    private final int configuredEggTime;

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
        if (!this.onGround() && vec3.y < (double)0.0F) {
            this.setDeltaMovement(vec3.multiply((double)1.0F, 0.6, (double)1.0F));
        }

        this.flap += this.flapping * 2.0F;
        if (!this.level().isClientSide() && this.isAlive() && !this.isBaby() && !this.isChickenJockey() && --this.eggTime <= 0) {
            this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.gameEvent(GameEvent.ENTITY_PLACE);
            this.eggTime = configuredEggTime;
        }

        if (!this.level().isClientSide() && this.isAlive() && !this.isBaby() && !this.isChickenJockey()) {
            try {
                int vanillaTime = (int) VANILLA_EGG_TIME.get(this);
                if (vanillaTime > this.configuredEggTime) {
                    VANILLA_EGG_TIME.set(this, this.configuredEggTime);
                }
            } catch (IllegalAccessException e) {
                //ChickenRoostMod.LOGGER.error("Failed to modify Chicken eggTime", e);
            }
        }

    }

    @Override
    public int getBaseExperienceReward(ServerLevel level) {
        return this.isChickenJockey() ? 10 : super.getBaseExperienceReward(level);
    }
    @Override
    public void readAdditionalSaveData(@NotNull ValueInput input) {
        super.readAdditionalSaveData(input);

        this.isChickenJockey =
                input.getBooleanOr("IsChickenJockey", this.isChickenJockey);

        input.getInt("EggLayTime")
                .ifPresent(value -> this.eggTime = value);
    }
    @Override
    public void addAdditionalSaveData(@NotNull ValueOutput p_28257_) {
        super.addAdditionalSaveData(p_28257_);
        p_28257_.putBoolean("IsChickenJockey", this.isChickenJockey);
        p_28257_.putInt("EggLayTime", this.eggTime);
    }
    /*@Override
    public @NotNull SoundEvent getAmbientSound() {
        return BuiltInRegistries.SOUND_EVENT.get(Identifier.withDefaultNamespace("entity.chicken.ambient"));
    }

    @Override
    public void playStepSound(@NotNull BlockPos pos, @NotNull BlockState blockIn) {
        this.playSound(Objects.requireNonNull(BuiltInRegistries.SOUND_EVENT.get(Identifier.withDefaultNamespace("entity.chicken.step"))), 0.15f, 1);
    }

    @Override
    public @NotNull SoundEvent getHurtSound(@NotNull DamageSource ds) {
        return Objects.requireNonNull(BuiltInRegistries.SOUND_EVENT.get(Identifier.withDefaultNamespace("entity.chicken.hurt")));
    }

    @Override
    public @NotNull SoundEvent getDeathSound() {
        return Objects.requireNonNull(BuiltInRegistries.SOUND_EVENT.get(Identifier.withDefaultNamespace("entity.chicken.death")));
    }*/
    @Override
    protected void actuallyHurt(ServerLevel level, DamageSource source, float amount) {
        if (!canTakeDamage(source)) return;
        super.actuallyHurt(level,source, amount);
    }

    private boolean canTakeDamage(DamageSource source) {
        if (isFireLike(source))              return IS_FIRE;
        if (isProjectileLike(source))       return IS_PROJECTILE;
        if (isExplosionLike(source))        return IS_EXPLOSION;
        if (source.is(DamageTypes.FALL))    return IS_FALL;
        if (source.is(DamageTypes.DROWN))   return IS_DROWNING;
        if (source.is(DamageTypes.FREEZE))  return IS_FREEZING;
        if (source.is(DamageTypes.LIGHTNING_BOLT)) return IS_LIGHTNING;
        if (isAny(source, DamageTypes.WITHER, DamageTypes.WITHER_SKULL)) return IS_WITHER;
        return true;
    }

    /*@Override
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
    }*/

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
    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
        builder = builder.add(Attributes.MAX_HEALTH, 3);
        builder = builder.add(Attributes.TEMPT_RANGE, 18);
        return builder;
    }

    /*public static AttributeSupplier.Builder createAttributes() {
        return BaseChickenEntity.createLivingAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.3d)
                .add(Attributes.MAX_HEALTH, 3D)
                .add(Attributes.ARMOR, 0d)
                .add(Attributes.ATTACK_DAMAGE, 0D)
                .add(Attributes.FOLLOW_RANGE, 16D);
    }*/
}
