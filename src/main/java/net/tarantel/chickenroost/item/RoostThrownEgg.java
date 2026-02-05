package net.tarantel.chickenroost.item;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class RoostThrownEgg extends ThrowableItemProjectile {
    private static final EntityDimensions ZERO_SIZED_DIMENSIONS = EntityDimensions.fixed(0.0F, 0.0F);


    private EntityType customtype;
    public RoostThrownEgg(EntityType<? extends ThrownEgg> entityType, Level level) {
        super(entityType, level);
    }

    public RoostThrownEgg(Level level, LivingEntity shooter, EntityType entity) {
        super(EntityType.EGG, shooter, level);
        this.customtype = entity;
    }

    public RoostThrownEgg(Level level, double x, double y, double z) {
        super(EntityType.EGG, x, y, z, level);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            double d0 = 0.08;

            for(int i = 0; i < 8; ++i) {
                this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), ((double)this.random.nextFloat() - 0.5) * 0.08, ((double)this.random.nextFloat() - 0.5) * 0.08, ((double)this.random.nextFloat() - 0.5) * 0.08);
            }
        }

    }
    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        result.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 0.0F);
    }
    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide) { // Use `level` instead of `level()` in Forge 1.20.1
            if (this.random.nextInt(8) == 0) {
                int i = 1;
                if (this.random.nextInt(32) == 0) {
                    i = 4;
                }

                for (int j = 0; j < i; ++j) {
                    Entity chicken = customtype.create(this.level()); // Use `level` instead of `level()`
                    if (chicken != null) {
                        // chicken.setAge(-24000); // Uncomment if needed
                        chicken.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);

                        // Attempt to add the entity to the world
                        if (this.level().addFreshEntity(chicken)) {
                            // Entity successfully added
                        } else {
                            // Handle the case where the entity could not be added
                            break;
                        }
                    }
                }
            }

            this.level().broadcastEntityEvent(this, (byte) 3); // Use `level` instead of `level()`
            this.discard();
        }
    }


    protected Item getDefaultItem() {
        return ModItems.LAVA_EGG.get();
    }
}