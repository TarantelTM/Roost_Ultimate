
package net.tarantel.chickenroost.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo.BiomeInfo.Builder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@SuppressWarnings("ALL")
public record ModEntitySpawn (HolderSet<Biome> biomes, SpawnerData spawn) implements BiomeModifier {
    
    public static DeferredRegister<Codec<? extends BiomeModifier>> SERIALIZER = DeferredRegister
			.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, ChickenRoostMod.MODID);

	static Supplier<Codec<ModEntitySpawn>> ROOST_SPAWN_CODEC = SERIALIZER.register("mobspawns",
			() -> RecordCodecBuilder.create(builder -> builder
					.group(Biome.LIST_CODEC.fieldOf("biomes").forGetter(ModEntitySpawn::biomes),
							SpawnerData.CODEC.fieldOf("spawn").forGetter(ModEntitySpawn::spawn))
					.apply(builder, ModEntitySpawn::new)));

	@Override
	public void modify(Holder<Biome> biome, Phase phase, Builder builder) {
		if (phase == Phase.ADD && this.biomes.contains(biome)) {
			builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER, this.spawn).addSpawn(MobCategory.CREATURE, this.spawn);
		}
	}

	@Override
	public Codec<? extends BiomeModifier> codec() {
		return ROOST_SPAWN_CODEC.get();
	}

	


}
