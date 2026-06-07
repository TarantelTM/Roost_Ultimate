package net.tarantel.chickenroost.util;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifier.Phase;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo.BiomeInfo.Builder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;
import org.jetbrains.annotations.NotNull;

public record ModEntitySpawnMob(HolderSet<Biome> biomes, SpawnerData spawn) implements BiomeModifier {
   public static DeferredRegister<MapCodec<? extends BiomeModifier>> SERIALIZER = DeferredRegister.create(Keys.BIOME_MODIFIER_SERIALIZERS, "chicken_roost");
   static Supplier<MapCodec<ModEntitySpawnMob>> ROOST_SPAWN_CODEC_MOB = SERIALIZER.register(
      "mobspawns",
      () -> RecordCodecBuilder.mapCodec(
         builder -> builder.group(
               Biome.LIST_CODEC.fieldOf("biomes").forGetter(ModEntitySpawnMob::biomes), SpawnerData.CODEC.fieldOf("spawn").forGetter(ModEntitySpawnMob::spawn)
            )
            .apply(builder, ModEntitySpawnMob::new)
      )
   );

   public void modify(@NotNull Holder<Biome> biome, @NotNull Phase phase, @NotNull Builder builder) {
      if (phase == Phase.ADD && this.biomes.contains(biome)) {
         builder.getMobSpawnSettings().addSpawn(MobCategory.CREATURE, this.spawn);
      }
   }

   @NotNull
   public MapCodec<? extends BiomeModifier> codec() {
      return ROOST_SPAWN_CODEC_MOB.get();
   }
}
