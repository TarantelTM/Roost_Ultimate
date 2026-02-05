package net.tarantel.chickenroost.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record ChickenStats(int level, int xp) {

    public static final Codec<ChickenStats> CODEC = RecordCodecBuilder.create(inst ->
            inst.group(
                    Codec.INT.fieldOf("roost_lvl").forGetter(ChickenStats::level),
                    Codec.INT.fieldOf("roost_xp").forGetter(ChickenStats::xp)
            ).apply(inst, ChickenStats::new)
    );
}