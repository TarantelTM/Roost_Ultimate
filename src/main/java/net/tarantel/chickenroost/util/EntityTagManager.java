package net.tarantel.chickenroost.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.tarantel.chickenroost.ChickenRoostMod;

public class EntityTagManager {
    public static final TagKey<EntityType<?>> ROOSTCHICKENS = of("roostultimate");
    public static final TagKey<EntityType<?>> VANILLA = of("vanilla");
    public static final TagKey<EntityType<?>> VANILLAEXTRA = of("vanillaextra");

    private EntityTagManager() {


    }

    private static TagKey<EntityType<?>> of(String id) {
        return TagKey.create(Registries.ENTITY_TYPE, ChickenRoostMod.commonsource(id));
    }
}
