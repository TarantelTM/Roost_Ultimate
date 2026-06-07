package net.tarantel.chickenroost.util;

import com.google.gson.GsonBuilder;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.HolderSet.Direct;
import net.minecraft.core.HolderSet.Named;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers.AddSpawnsBiomeModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;

public class ServerBiomeCache {
   private static final Map<String, List<String>> ENTITY_TO_BIOMES = new HashMap<>();

   public static void rebuild(ServerLevel level) {
      ENTITY_TO_BIOMES.clear();
      RegistryAccess access = level.registryAccess();
      Registry<BiomeModifier> modifiers = access.registryOrThrow(Keys.BIOME_MODIFIERS);
      Registry<Biome> biomes = access.registryOrThrow(Registries.BIOME);

      for (Entry<ResourceKey<BiomeModifier>, BiomeModifier> entry : modifiers.entrySet()) {
         if (entry.getValue() instanceof AddSpawnsBiomeModifier add) {
            for (SpawnerData spawner : add.spawners()) {
               ResourceLocation entityId = BuiltInRegistries.ENTITY_TYPE.getKey(spawner.type);
               List<String> list = ENTITY_TO_BIOMES.computeIfAbsent(entityId.toString(), k -> new ArrayList<>());
               HolderSet<Biome> biomeSet = add.biomes();
               if (biomeSet instanceof Named<Biome> named) {
                  TagKey<Biome> tag = named.key();
                  biomes.getTag(tag).ifPresent(tagged -> tagged.forEach(h -> addBiome(biomes, h, list)));
               } else if (biomeSet instanceof Direct<Biome> direct) {
                  direct.forEach(h -> addBiome(biomes, h, list));
               }
            }
         }
      }

      writeJson(level);
   }

   private static void addBiome(Registry<Biome> registry, Holder<Biome> holder, List<String> list) {
      ResourceLocation id = registry.getKey((Biome)holder.value());
      if (id != null && !list.contains(id.toString())) {
         list.add(id.toString());
      }
   }

   private static void writeJson(ServerLevel level) {
      try {
         Path file = level.getServer().getServerDirectory().toAbsolutePath().resolve("chicken_biomes.json");

         try (Writer writer = Files.newBufferedWriter(file)) {
            new GsonBuilder().setPrettyPrinting().create().toJson(ENTITY_TO_BIOMES, writer);
         }

         System.out.println("[ChickenRoost] Biome cache written");
      } catch (Exception var7) {
         var7.printStackTrace();
      }
   }
}
