package net.tarantel.chickenroost.util;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.tarantel.chickenroost.entity.BaseChickenEntity;

import java.util.Objects;

public class ChickenStickTool {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null)
            return;
        if (entity instanceof AgeableMob ageable && ageable.isBaby()) {
            return;
        }

        ItemStack itemchicken;
        if (entity instanceof BaseChickenEntity) {
            String newentity = Objects.requireNonNull(entity.getEncodeId()).toLowerCase();
            ResourceLocation myEntity = ResourceLocation.tryParse(newentity);

            itemchicken = new ItemStack(BuiltInRegistries.ITEM.get(myEntity));
            itemchicken.set(ModDataComponents.CHICKENLEVEL, entity.getPersistentData().getInt("chickenlevel"));
            itemchicken.set(ModDataComponents.CHICKENXP, entity.getPersistentData().getInt("chickenxp"));
            itemchicken.set(ModDataComponents.AGE, entity.getPersistentData().getInt("age"));
            if (world instanceof Level _level && !_level.isClientSide()) {
                ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, itemchicken);
                entityToSpawn.setPickUpDelay(10);
                _level.addFreshEntity(entityToSpawn);
            }

            if (!entity.level().isClientSide())
                entity.discard();
        }
        if (entity.getType() == EntityType.CHICKEN) {
            String newentity = ("chicken_roost:c_vanilla");
            ResourceLocation myEntity = ResourceLocation.tryParse(newentity);

            itemchicken = new ItemStack(BuiltInRegistries.ITEM.get(myEntity));
            itemchicken.set(ModDataComponents.CHICKENLEVEL, entity.getPersistentData().getInt("chickenlevel"));
            itemchicken.set(ModDataComponents.CHICKENXP, entity.getPersistentData().getInt("chickenxp"));
            itemchicken.set(ModDataComponents.AGE, entity.getPersistentData().getInt("age"));
            if (world instanceof Level _level && !_level.isClientSide()) {
                ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, itemchicken);
                entityToSpawn.setPickUpDelay(10);
                _level.addFreshEntity(entityToSpawn);
            }

            if (!entity.level().isClientSide())
                entity.discard();
        }
    }
}
