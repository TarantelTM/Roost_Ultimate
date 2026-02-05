package net.tarantel.chickenroost.item;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;

import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class RoostThrownEgg extends ThrowableItemProjectile {
    private static final EntityDimensions ZERO_SIZED_DIMENSIONS = EntityDimensions.fixed(0.0F, 0.0F);


    private EntityType customtype;

    public RoostThrownEgg(Level level, LivingEntity shooter, EntityType<?> entity) {
        super(
                EntityType.EGG,               // EntityType des Projektils
                shooter,
                level,
                new ItemStack(ModItems.WOOD_ESSENCE.get()) // ItemStack ist Pflicht
        );
        this.customtype = entity;
    }


    public RoostThrownEgg(Level level, double x, double y, double z) {
        super(
                EntityType.EGG,
                x, y, z,
                level,
                new ItemStack(ModItems.WOOD_ESSENCE.get())
        );
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            for(int i = 0; i < 8; ++i) {
                this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), ((double)this.random.nextFloat() - 0.5) * 0.08, ((double)this.random.nextFloat() - 0.5) * 0.08, ((double)this.random.nextFloat() - 0.5) * 0.08);
            }
        }

    }
    @Override
    protected void onHitEntity(@NotNull EntityHitResult result) {
        super.onHitEntity(result);
        result.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 0.0F);
    }
    @Override
    protected void onHit(@NotNull HitResult result) {
        super.onHit(result);

        if (this.level().isClientSide()) return;

        if (this.random.nextInt(8) == 0) {
            int count = this.random.nextInt(32) == 0 ? 4 : 1;

            if (!(this.level() instanceof ServerLevel serverLevel)) {
                return;
            }

            for (int j = 0; j < count; ++j) {

                Entity chicken = customtype.spawn(
                        serverLevel,
                        this.blockPosition(),
                        EntitySpawnReason.BREEDING
                );

                if (chicken == null) break;

                // Position / Rotation (moveTo-Ersatz)
                chicken.setPos(this.getX(), this.getY(), this.getZ());
                chicken.setYRot(this.getYRot());
                chicken.setXRot(0.0F);
                chicken.setYHeadRot(this.getYRot());
                chicken.setYBodyRot(this.getYRot());

                // optional, aber sinnvoll:
                if (!chicken.fudgePositionAfterSizeChange(ZERO_SIZED_DIMENSIONS)) {
                    break;
                }
            }
        }

        this.level().broadcastEntityEvent(this, (byte) 3);
        this.discard();
    }


    protected @NotNull Item getDefaultItem() {
        return ModItems.WOOD_ESSENCE.get();
    }
}