
package net.tarantel.chickenroost.util;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.tarantel.chickenroost.ChickenRoostMod;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public record ModEntitySpawnMonster(HolderSet<Biome> biomes, MobSpawnSettings.SpawnerData spawn) implements BiomeModifier {


	public static DeferredRegister<MapCodec<? extends BiomeModifier>> SERIALIZER = DeferredRegister.create(
			NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, ChickenRoostMod.MODID);


	static Supplier<MapCodec<ModEntitySpawnMonster>> ROOST_SPAWN_CODEC_MOB = SERIALIZER.register("monsterspawns",
			() -> RecordCodecBuilder.mapCodec(
					builder -> builder.group(Biome.LIST_CODEC.fieldOf("biomes").forGetter(ModEntitySpawnMonster::biomes),
							MobSpawnSettings.SpawnerData.CODEC.fieldOf("spawn").forGetter(
									ModEntitySpawnMonster::spawn)).apply(builder, ModEntitySpawnMonster::new)));


	@Override
	public void modify(@NotNull Holder<Biome> biome, @NotNull Phase phase, ModifiableBiomeInfo.BiomeInfo.@NotNull Builder builder) {
		if (phase == Phase.ADD && this.biomes.contains(biome)) {
			builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER, this.spawn);
		}
	}


	@Override
	public @NotNull MapCodec<? extends BiomeModifier> codec() {
		return ROOST_SPAWN_CODEC_MOB.get();
	}

	


}
