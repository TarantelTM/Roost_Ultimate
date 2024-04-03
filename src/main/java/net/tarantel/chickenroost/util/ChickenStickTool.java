package net.tarantel.chickenroost.util;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class ChickenStickTool {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null)
            return;

        ItemStack itemchicken = ItemStack.EMPTY;
        if (entity.getType().is(ModTags.ROOSTCHICKENS)) {
            String newentity = entity.getEncodeId().toLowerCase();
            ResourceLocation myEntity = ResourceLocation.tryParse(newentity.toString());

            itemchicken = new ItemStack(Registry.ITEM.get(myEntity));
            System.out.println(newentity);
            //System.out.println(itemchicken);
            if (world instanceof Level _level && !_level.isClientSide()) {
                ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, itemchicken);
                entityToSpawn.setPickUpDelay(10);
                _level.addFreshEntity(entityToSpawn);
            }

            if (!entity.level.isClientSide())
                entity.discard();
        }
        if (entity.getType().is(ModTags.VANILLA)) {
            String newentity = ("chicken_roost:c_vanilla");
            ResourceLocation myEntity = ResourceLocation.tryParse(newentity.toString());

            itemchicken = new ItemStack(Registry.ITEM.get(myEntity));
            System.out.println(newentity);
            if (world instanceof Level _level && !_level.isClientSide()) {
                ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, itemchicken);
                entityToSpawn.setPickUpDelay(10);
                _level.addFreshEntity(entityToSpawn);
            }

            if (!entity.level.isClientSide())
                entity.discard();
        }
    }
}
