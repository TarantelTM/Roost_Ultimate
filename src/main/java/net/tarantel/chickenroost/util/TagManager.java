package net.tarantel.chickenroost.util;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class TagManager {

    public static class Items{
        public static final TagKey<Item> SEEDS = createTag("seeds/tiered");
        public static final TagKey<Item> BREEDABLE = createTag("roostbreedablechi");
        public static final TagKey<Item> CHICKEN = createTag("chickens");
    }


    public static TagKey<Item> createTag(String name){
        return TagKey.of(Registries.ITEM.getKey(), new Identifier("fabric", name));
    }

}
