package net.tarantel.chickenroost.util;

import net.minecraft.block.Block;

import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class BlockTagManager {




    public static class Blocks{
        public static final TagKey<Block> ROOSTBLOCKS = createTag("roostultimate");
        //public static final TagKey<Block> BREEDABLE = createTag("roostbreedablechi");
    }


    public static TagKey<Block> createTag(String name){
        return TagKey.of(Registries.BLOCK.getKey(), new Identifier("fabric", name));
    }
}

