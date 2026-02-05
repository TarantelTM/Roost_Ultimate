package net.tarantel.chickenroost.util;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.tarantel.chickenroost.entity.BaseChickenEntity;

public class ChickenStickTool {

    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null) return;

        ItemStack result = ItemStack.EMPTY;

        if (entity instanceof BaseChickenEntity roost) {
            result = createChickenItem(
                    roost,
                    roost.getEncodeId()
            );
        }
        else if (entity instanceof Chicken roost) {
            result = createChickenItem(
                    roost,
                    "chicken_roost:c_vanilla"
            );
        }

        if (result.isEmpty()) return;

        if (world instanceof Level level && !level.isClientSide()) {
            ItemEntity drop = new ItemEntity(level, x, y, z, result);
            drop.setPickUpDelay(10);
            level.addFreshEntity(drop);
        }

        if (!entity.level().isClientSide()) {
            entity.discard();
        }
    }



    private static ItemStack createChickenItem(Entity entity, String itemId) {
        ItemStack stack = new ItemStack(
                BuiltInRegistries.ITEM.get(new ResourceLocation(itemId))
        );

        CompoundTag tag = stack.getOrCreateTag();


        if (entity instanceof AgeableMob ageable) {
            tag.putInt("Age", ageable.getAge());
            tag.putBoolean("IsBaby", ageable.isBaby());
        }


        tag.putInt("roost_lvl", entity.getPersistentData().getInt("roost_lvl"));
        tag.putInt("roost_xp", entity.getPersistentData().getInt("roost_xp"));

        return stack;
    }

}