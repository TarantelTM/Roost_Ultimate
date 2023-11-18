package net.tarantel.chickenroost.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ChickenBaseEntity extends ChickenEntity {
    public ChickenBaseEntity(EntityType<ChickenBaseEntity> type, World world) {
        super(type, world);
        experiencePoints = 0;
        setAiDisabled(false);
        setPersistent();
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(1, new WanderAroundGoal(this, 1));
        this.goalSelector.add(2, new LookAroundGoal(this));
        this.goalSelector.add(3, new SwimGoal(this));
    }

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.DEFAULT;
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_CHICKEN_AMBIENT;
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.15f, 1);
    }

    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return SoundEvents.ENTITY_CHICKEN_HURT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_CHICKEN_DEATH;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source.isIn(DamageTypeTags.IS_FALL))
            return false;
        return super.damage(source, amount);
    }

    public static void init() {
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        DefaultAttributeContainer.Builder builder = MobEntity.createMobAttributes();
        builder = builder.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3);
        builder = builder.add(EntityAttributes.GENERIC_MAX_HEALTH, 3);
        builder = builder.add(EntityAttributes.GENERIC_ARMOR, 0);
        builder = builder.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3);
        builder = builder.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16);
        return builder;
    }
}
