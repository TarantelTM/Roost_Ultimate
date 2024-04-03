package net.tarantel.chickenroost.util;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.tarantel.chickenroost.ChickenRoostMod;

public class ModTags {
    public static final TagKey<EntityType<?>> ROOSTCHICKENS = of("roostultimate");
    public static final TagKey<EntityType<?>> VANILLA = of("vanilla");
    public static final TagKey<EntityType<?>> VANILLAEXTRA = of("vanillaextra");


    private ModTags() {

    }

    private static TagKey<EntityType<?>> of(String id) {
        return TagKey.create(Registry.ENTITY_TYPE.key(), new ResourceLocation("forge",id));
    }

    private static TagKey<Block> tag(String id) {
        return BlockTags.create(new ResourceLocation(ChickenRoostMod.MODID,id));
    }
}
