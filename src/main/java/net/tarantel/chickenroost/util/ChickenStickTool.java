package net.tarantel.chickenroost.util;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class ChickenStickTool {
    /**
     * Executes a process to handle entities of specific types by converting them into corresponding item entities
     * with preserved data components, then spawning the item entities into the world and discarding the original entities.
     *
     * @param world The instance of the level or world where the operation is performed.
     * @param x The x-coordinate where the item entity will be spawned.
     * @param y The y-coordinate where the item entity will be spawned.
     * @param z The z-coordinate where the item entity will be spawned.
     * @param entity The entity to be processed and transformed into an item entity.
     */
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null)
            return;

        ItemStack itemchicken = ItemStack.EMPTY;
        if (entity.getType().is(ModTags.ROOSTCHICKENS)) {
            String newentity = entity.getEncodeId().toLowerCase();
            ResourceLocation myEntity = ResourceLocation.tryParse(newentity.toString());

            itemchicken = new ItemStack(BuiltInRegistries.ITEM.get(myEntity));
            itemchicken.getOrCreateTag().putInt("roost_lvl", entity.getPersistentData().getInt("roost_lvl"));
            itemchicken.getOrCreateTag().putInt("roost_xp", entity.getPersistentData().getInt("roost_xp"));
            //System.out.println(newentity);
            //System.out.println(itemchicken);
            if (world instanceof Level _level && !_level.isClientSide()) {
                ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, itemchicken);
                entityToSpawn.setPickUpDelay(10);
                _level.addFreshEntity(entityToSpawn);
            }

            if (!entity.level().isClientSide())
                entity.discard();
        }
        if (entity.getType().is(ModTags.VANILLA)) {
            String newentity = ("chicken_roost:c_vanilla");
            ResourceLocation myEntity = ResourceLocation.tryParse(newentity.toString());

            itemchicken = new ItemStack(BuiltInRegistries.ITEM.get(myEntity));
            itemchicken.getOrCreateTag().putInt("roost_lvl", entity.getPersistentData().getInt("roost_lvl"));
            itemchicken.getOrCreateTag().putInt("roost_xp", entity.getPersistentData().getInt("roost_xp"));
            //System.out.println(newentity);
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