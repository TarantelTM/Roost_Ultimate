package net.tarantel.chickenroost.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

public class ModTags {
    public static final TagKey<EntityType<?>> ROOSTCHICKENS = of("roostultimate");
    public static final TagKey<EntityType<?>> VANILLA = of("vanilla");
    private ModTags() {

    }
    public static class Items {
        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation("forge", name));
        }
    }
    private static TagKey<EntityType<?>> of(String id) {
        return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("forge",id));
    }
}
