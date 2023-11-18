package net.tarantel.chickenroost.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;


public class ChickenStickTool {
    public static void execute(WorldAccess world, double x, double y, double z, Entity entity) {
        if (entity == null)
            return;

        ItemStack itemchicken = ItemStack.EMPTY;
        if (entity.getType().isIn(EntityTagManager.ROOSTCHICKENS)) {
            String newentity = ("chicken_roost:" + entity.getType().getUntranslatedName());
            Identifier myEntity = Identifier.tryParse(newentity.toString());

            itemchicken = new ItemStack(Registries.ITEM.get(myEntity));
            System.out.println(newentity);
            if (world instanceof World _level && !_level.isClient()) {
                ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, itemchicken);
                entityToSpawn.setPickupDelay(10);
                _level.spawnEntity(entityToSpawn);
            }

            if (!entity.getWorld().isClient())
                entity.discard();
        }
        if (entity.getType().isIn(EntityTagManager.VANILLA)) {
            String newentity = ("chicken_roost:c_vanilla");
            Identifier myEntity = Identifier.tryParse(newentity.toString());

            itemchicken = new ItemStack(Registries.ITEM.get(myEntity));
            System.out.println(newentity);
            if (world instanceof World _level && !_level.isClient()) {
                ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, itemchicken);
                entityToSpawn.setPickupDelay(10);
                _level.spawnEntity(entityToSpawn);
            }

            if (!entity.getWorld().isClient())
                entity.discard();
        }
    }
}

