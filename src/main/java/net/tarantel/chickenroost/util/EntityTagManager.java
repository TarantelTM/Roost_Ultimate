package net.tarantel.chickenroost.util;


import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;


public class EntityTagManager {

    public static final TagKey<EntityType<?>> ROOSTCHICKENS = of("roostultimate");
    public static final TagKey<EntityType<?>> VANILLA = of("vanilla");

    private EntityTagManager() {

    }

    private static TagKey<EntityType<?>> of(String id) {
        return TagKey.of(Registries.ENTITY_TYPE.getKey(), new Identifier("fabric",id));
    }
}
