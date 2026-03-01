package net.tarantel.chickenroost.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraft.world.entity.EntityType.EntityFactory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.util.ChickenConfig;
import net.tarantel.chickenroost.util.ChickenData;

@EventBusSubscriber(bus = Bus.MOD)
public class ModEntities {
   public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(Registries.ENTITY_TYPE, "chicken_roost");
   public static final Map<EntityType<?>, List<ResourceLocation>> ENTITY_TO_BIOMES = new HashMap<>();

   public static <T extends Mob> DeferredHolder<EntityType<?>, EntityType<T>> registerMob(
      String name, EntityFactory<T> factory, float width, float height, int primaryColor, int secondaryColor
   ) {
      return REGISTRY.register(name, () -> Builder.of(factory, MobCategory.CREATURE).sized(0.4F, 0.7F).clientTrackingRange(8).build(name));
   }

   public static <T extends Mob> void registerMonster(String name, EntityFactory<T> factory, float width, float height, int primaryColor, int secondaryColor) {
      REGISTRY.register(name, () -> Builder.of(factory, MobCategory.MONSTER).sized(0.4F, 0.7F).clientTrackingRange(8).build(name));
   }

   public static <T extends Mob> void registerMobFireImmun(
      String name, EntityFactory<T> factory, float width, float height, int primaryColor, int secondaryColor
   ) {
      REGISTRY.register(name, () -> Builder.of(factory, MobCategory.CREATURE).sized(0.4F, 0.7F).clientTrackingRange(8).fireImmune().build(name));
   }

   public static <T extends Mob> DeferredHolder<EntityType<?>, EntityType<T>> registerMonsterFireImmun(
      String name, EntityFactory<T> factory, float width, float height, int primaryColor, int secondaryColor
   ) {
      return REGISTRY.register(name, () -> Builder.of(factory, MobCategory.MONSTER).sized(0.4F, 0.7F).clientTrackingRange(8).fireImmune().build(name));
   }

   public static void readthis() {
      List<ChickenData> readItems = ChickenRoostMod.chickens;

      assert readItems != null;

      if (!readItems.isEmpty()) {
         for (ChickenData etherItem : readItems) {
            String id = etherItem.getId();
            String mobormonster = etherItem.getMobOrMonster();
            Boolean IS_FIRE = etherItem.CanGetFireDamage;
            extrachickens(id, mobormonster, IS_FIRE);
         }
      }
   }

   private static void extrachickens(String idd, String mobormonster, Boolean IS_FIRE) {
      if (mobormonster.equals("Mob")) {
         if (IS_FIRE) {
            registerMob(idd, BaseChickenEntity::new, 0.4F, 0.7F, 3154457, 11316396);
         } else {
            registerMobFireImmun(idd, BaseChickenEntity::new, 0.4F, 0.7F, 3154457, 11316396);
         }
      } else if (IS_FIRE) {
         registerMonster(idd, BaseChickenEntity::new, 0.4F, 0.7F, 3154457, 11316396);
      } else {
         registerMonsterFireImmun(idd, BaseChickenEntity::new, 0.4F, 0.7F, 3154457, 11316396);
      }
   }

   @SubscribeEvent
   public static void init(FMLCommonSetupEvent event) {
      event.enqueueWork(BaseChickenEntity::init);
   }

   public static void initChickenConfig() {
      List<ChickenData> readItems = ChickenRoostMod.chickens;

      assert readItems != null;

      if (!readItems.isEmpty()) {
         for (ChickenData etherItem : readItems) {
            if (!etherItem.getId().equals("c_vanilla")) {
               String id = etherItem.getId();
               String dropitem = etherItem.getDropitem();
               int eggtime = etherItem.getEggtime();
               boolean IS_FIRE = etherItem.CanGetFireDamage;
               boolean IS_PROJECTILE = etherItem.CanGetProjectileDamage;
               boolean IS_EXPLOSION = etherItem.CanGetExplosionDamage;
               boolean IS_FALL = etherItem.CanGetFallDamage;
               boolean IS_DROWNING = etherItem.CanGetDrowningDamage;
               boolean IS_FREEZING = etherItem.CanGetFreezingDamage;
               boolean IS_LIGHTNING = etherItem.CanGetLightningDamage;
               boolean IS_WITHER = etherItem.CanGetWitherDamage;
               int TIER = etherItem.getTier();
               ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath("chicken_roost", id);
               EntityType<?> entityType = EntityType.byString(resourceLocation.toString()).orElse(EntityType.CHICKEN);
               ChickenConfig.setDropStack(entityType, new ItemStack((ItemLike)BuiltInRegistries.ITEM.get(ResourceLocation.parse(dropitem))));
               ChickenConfig.setEggTime(entityType, eggtime);
               ChickenConfig.setIsFire(entityType, IS_FIRE);
               ChickenConfig.setIsProjectile(entityType, IS_PROJECTILE);
               ChickenConfig.setIsExplosion(entityType, IS_EXPLOSION);
               ChickenConfig.setIsFall(entityType, IS_FALL);
               ChickenConfig.setIsDrowning(entityType, IS_DROWNING);
               ChickenConfig.setIsFreezing(entityType, IS_FREEZING);
               ChickenConfig.setIsLightning(entityType, IS_LIGHTNING);
               ChickenConfig.setIsWither(entityType, IS_WITHER);
               ChickenConfig.setTier(entityType, TIER);
            }
         }
      }
   }

   @SubscribeEvent
   public static void registerAttributes(EntityAttributeCreationEvent event) {
      List<ChickenData> readItems = ChickenRoostMod.chickens;

      assert readItems != null;

      if (!readItems.isEmpty()) {
         for (ChickenData etherItem : readItems) {
            if (!etherItem.getId().equals("c_vanilla")) {
               String id = etherItem.getId();
               ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath("chicken_roost", id);
               @SuppressWarnings("unchecked")
               EntityType<? extends LivingEntity> entityType = (EntityType<? extends LivingEntity>) EntityType.byString(resourceLocation.toString()).orElse(EntityType.CHICKEN);
               event.put(entityType, BaseChickenEntity.createAttributes().build());
            }
         }
      }
   }

   public static void register(IEventBus eventBus) {
      readthis();
      REGISTRY.register(eventBus);
   }
}
