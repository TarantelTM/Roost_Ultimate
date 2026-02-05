package net.tarantel.chickenroost.spawn;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;

public class GroupSpawnHelper {

    public static void spawnGroup(
            Level level,
            Mob leader,
            EntityType<?> type,
            int min,
            int max
    ) {
        if (max <= 1) return;

        int count = min + level.random.nextInt(Math.max(1, max - min + 1));

        for (int i = 1; i < count; i++) {
            BlockPos offset = leader.blockPosition().offset(
                    level.random.nextInt(7) - 3,
                    0,
                    level.random.nextInt(7) - 3
            );

            Mob mob = (Mob) type.create(level);
            if (mob == null) continue;

            mob.moveTo(
                    offset.getX() + 0.5,
                    offset.getY(),
                    offset.getZ() + 0.5,
                    level.random.nextFloat() * 360F,
                    0
            );

            if (!mob.checkSpawnRules(level, MobSpawnType.NATURAL)) continue;
            if (!mob.checkSpawnObstruction(level)) continue;

            level.addFreshEntity(mob);
        }
    }
}