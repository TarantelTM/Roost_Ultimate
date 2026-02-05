
package net.tarantel.chickenroost.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.item.ModItems;
import net.tarantel.chickenroost.recipes.Breeder_Recipe;
import net.tarantel.chickenroost.util.ChickenConfig;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.lang.reflect.Field;

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
    public final int TIER;
    private int newEggTime;
    // --- Spawn-from-item cache ---
    @Nullable
    private Integer ageFromItem = null;

    @Nullable
    private Boolean babyFromItem = null;

    public BaseChickenEntity(EntityType<BaseChickenEntity> type, Level world) {
        super(type, world);
        this.xpReward = 0;
        this.setNoAi(false);
        //this.setPersistenceRequired();
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
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.TIER = ChickenConfig.getTier(type);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void registerGoals() {
        //super.registerGoals();
        this.goalSelector.addGoal(1, new RandomStrollGoal(this, 1));
        this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(3, new FloatGoal(this));
        this.goalSelector.addGoal(
                4,
                new TemptGoal(this, 1.0D, getFoodIngredient(), false)
        );

        this.goalSelector.addGoal(5, new BreedGoal(this, 1.0D));
    }
    private Ingredient getFoodIngredient() {
        return switch (this.TIER) {
            case 2 -> Ingredient.of(ItemTags.create(new ResourceLocation("c", "seeds/tier2orup")));
            case 3 -> Ingredient.of(ItemTags.create(new ResourceLocation("c", "seeds/tier3orup")));
            case 4 -> Ingredient.of(ItemTags.create(new ResourceLocation("c", "seeds/tier4orup")));
            case 5 -> Ingredient.of(ItemTags.create(new ResourceLocation("c", "seeds/tier5orup")));
            case 6 -> Ingredient.of(ItemTags.create(new ResourceLocation("c", "seeds/tier6orup")));
            case 7 -> Ingredient.of(ItemTags.create(new ResourceLocation("c", "seeds/tier7orup")));
            case 8 -> Ingredient.of(ItemTags.create(new ResourceLocation("c", "seeds/tier8orup")));
            case 9 -> Ingredient.of(ItemTags.create(new ResourceLocation("c", "seeds/tier9orup")));
            default -> Ingredient.of(ItemTags.create(new ResourceLocation("c", "seeds/tier1orup")));
        };
    }
    private TagKey<Item> getCorrectFood() {
        return switch (this.TIER) {
            case 2 -> ItemTags.create(new ResourceLocation("c", "seeds/tier2orup"));
            case 3 -> ItemTags.create(new ResourceLocation("c", "seeds/tier3orup"));
            case 4 -> ItemTags.create(new ResourceLocation("c", "seeds/tier4orup"));
            case 5 -> ItemTags.create(new ResourceLocation("c", "seeds/tier5orup"));
            case 6 -> ItemTags.create(new ResourceLocation("c", "seeds/tier6orup"));
            case 7 -> ItemTags.create(new ResourceLocation("c", "seeds/tier7orup"));
            case 8 -> ItemTags.create(new ResourceLocation("c", "seeds/tier8orup"));
            case 9 -> ItemTags.create(new ResourceLocation("c", "seeds/tier9orup"));
            default -> ItemTags.create(new ResourceLocation("c", "seeds/tier1orup"));
        };
    }

    @Override
    public boolean isFood(@NotNull ItemStack stack) {
        boolean result = stack.is(this.getCorrectFood());
        System.out.println("Tier: " + this.TIER + " | isFood=" + result);
        return result;
    }
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        //  Vanilla Seeds komplett blocken
        if (stack.is(Tags.Items.SEEDS)) {
            return InteractionResult.PASS;
        }

        //  falsches Tier-Seed blocken
        if (!this.isFood(stack)) {
            return InteractionResult.PASS;
        }

        //  korrektes Seed → Vanilla-Logik darf laufen
        return super.mobInteract(player, hand);
    }
    public void readSpawnDataFromItem(ItemStack stack) {
        if (!stack.hasTag()) return;

        CompoundTag tag = stack.getTag();

        if (tag.contains("Age", Tag.TAG_INT)) {
            this.ageFromItem = tag.getInt("Age");
        }

        if (tag.contains("IsBaby", Tag.TAG_BYTE)) {
            this.babyFromItem = tag.getBoolean("IsBaby");
        }
    }

    @Override
    public boolean canFallInLove() {
        return super.canFallInLove() && this.getAge() == 0;
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


        for (Breeder_Recipe recipe : level.getRecipeManager()
                .getAllRecipesFor(Breeder_Recipe.Type.INSTANCE)) {

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
                    child.setAge(-24000); // Baby
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
    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
	protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn) {
		super.dropCustomDeathLoot(source, looting, recentlyHitIn);
        //this.spawnAtLocation(dropStack);
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        if (this.isBaby()) {

            return this.getType().getDimensions().scale(0.5F);
        }
        return super.getDimensions(pose);
    }

    @Override
    public SpawnGroupData finalizeSpawn(
            ServerLevelAccessor level,
            DifficultyInstance difficulty,
            MobSpawnType spawnType,
            @Nullable SpawnGroupData spawnData,
            @Nullable CompoundTag tag
    ) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnType, spawnData, tag);

        // 1️⃣ Item-Daten haben höchste Priorität
        if (ageFromItem != null) {
            this.setAge(ageFromItem);
        } else if (babyFromItem != null) {
            this.setBaby(babyFromItem);
        }
        // 2️⃣ Fallback: Breeding erzeugt immer Babys
        else if (spawnType == MobSpawnType.BREEDING) {
            this.setBaby(true);
        }

        // 3️⃣ Cache leeren (wichtig!)
        ageFromItem = null;
        babyFromItem = null;

        return data;
    }


    @Override
    public ItemEntity spawnAtLocation(@NotNull ItemStack stack) {
        // Vanilla-Eier unterdrücken oder ersetzen
        if (stack.is(Items.EGG)) {
            // Variante A: gar nichts droppen
            // return null;

            // Variante B: durch deinen dropStack ersetzen
            if (!dropStack.isEmpty()) {
                return super.spawnAtLocation(dropStack.copy());
            }
            return null;
        }

        return super.spawnAtLocation(stack);
    }
    @Override
    public ItemEntity spawnAtLocation(ItemLike itemLike) {
        return this.spawnAtLocation(new ItemStack(itemLike));
    }
    /*private static final Field VANILLA_EGG_TIME;
    static {
        try {
            // ACHTUNG: hier musst du den obfuskierten Feldnamen einsetzen (f_xxxxx)
            VANILLA_EGG_TIME = Chicken.class.getDeclaredField("eggTime"); // mit Mappings evtl. so, sonst SRG/obf
            VANILLA_EGG_TIME.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Cannot find Chicken.eggTime field!", e);
        }
    }*/

    private final int configuredEggTime; // aus ChickenConfig


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
            //this.spawnAtLocation(dropStack);
            this.gameEvent(GameEvent.ENTITY_PLACE);
            this.eggTime = configuredEggTime;
        }
        /*if (!this.level().isClientSide && this.isAlive() && !this.isBaby() && !this.isChickenJockey()) {
            try {
                int vanillaTime = (int) VANILLA_EGG_TIME.get(this);
                if (vanillaTime > this.configuredEggTime) {
                    VANILLA_EGG_TIME.set(this, this.configuredEggTime);
                }
            } catch (IllegalAccessException e) {
                ChickenRoostMod.LOGGER.error("Failed to modify Chicken eggTime", e);
            }
        }*/
    }
    @Override
	public int getExperienceReward() {
		return this.isChickenJockey() ? 10 : super.getExperienceReward();
	}
    @Override
    public void readAdditionalSaveData(CompoundTag p_28243_) {
        super.readAdditionalSaveData(p_28243_);
        this.isChickenJockey = p_28243_.getBoolean("IsChickenJockey");
        if (p_28243_.contains("EggLayTime")) {
            this.eggTime = p_28243_.getInt("EggLayTime");
        }

    }
    @Override
    public void addAdditionalSaveData(CompoundTag p_28257_) {
        super.addAdditionalSaveData(p_28257_);
        p_28257_.putBoolean("IsChickenJockey", this.isChickenJockey);
        p_28257_.putInt("EggLayTime", this.eggTime);
    }
    @Override
	public SoundEvent getAmbientSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.chicken.ambient"));
	}

	@Override
	public void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.chicken.step")), 0.15f, 1);
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.chicken.hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.chicken.death"));
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

    /* --- tiny helpers (stay in the same class) --- */

    // Covers lava/on_fire/in_fire via tag; add known omissions explicitly.
    private static boolean isFireLike(DamageSource src) {
        return src.is(DamageTypeTags.IS_FIRE) || isAny(src,
                DamageTypes.FIREBALL, DamageTypes.UNATTRIBUTED_FIREBALL, DamageTypes.FIREWORKS);
    }

    // Vanilla tag + explicit mob projectile (if tag misses it in your version).
    private static boolean isProjectileLike(DamageSource src) {
        return src.is(DamageTypeTags.IS_PROJECTILE) || isAny(src,
                DamageTypes.ARROW, DamageTypes.MOB_PROJECTILE);
    }

    // Vanilla tag + explicit player explosion (defensive).
    private static boolean isExplosionLike(DamageSource src) {
        return src.is(DamageTypeTags.IS_EXPLOSION) || isAny(src, DamageTypes.PLAYER_EXPLOSION);
    }

    @SafeVarargs
    private static boolean isAny(DamageSource src, ResourceKey<DamageType>... keys) {
        for (ResourceKey<DamageType> k : keys) if (src.is(k)) return true; // why: keep list local & extendable
        return false;
    }

    public static void init() {
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
        builder = builder.add(Attributes.MAX_HEALTH, 3);
        builder = builder.add(Attributes.ARMOR, 0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 0);
        builder = builder.add(Attributes.FOLLOW_RANGE, 16);
        return builder;
    }
}
