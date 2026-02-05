
package net.tarantel.chickenroost.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.util.ChickenConfig;
import net.tarantel.chickenroost.util.ChickenData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EventBusSubscriber(modid = ChickenRoostMod.MODID)
public class ModEntities {

	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
			DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, ChickenRoostMod.MODID);

	//private static final Map<String, DeferredHolder<EntityType<?>, EntityType<?>>> DYNAMIC_ENTITIES = new HashMap<>();
	private static final Map<String, DeferredHolder<EntityType<?>, EntityType<BaseChickenEntity>>> DYNAMIC_ENTITIES = new HashMap<>();

	private static ResourceKey<EntityType<?>> key(String name) {
		return ResourceKey.create(
				Registries.ENTITY_TYPE,
				Identifier.fromNamespaceAndPath(ChickenRoostMod.MODID, name)
		);
	}

	private static DeferredHolder<EntityType<?>, EntityType<BaseChickenEntity>> registerChicken(
			String name,
			MobCategory category,
			boolean fireImmune
	) {
		return ENTITY_TYPES.register(name, () -> {
			var builder = EntityType.Builder.of(BaseChickenEntity::new, category)
					.sized(0.4f, 0.7f)
					.clientTrackingRange(8);

			if (fireImmune) {
				builder.fireImmune();
			}

			return builder.build(key(name));
		});
	}

	public static void readFromJson() {
		List<ChickenData> chickens = ChickenRoostMod.chickens;
		if (chickens == null || chickens.isEmpty()) return;

		for (ChickenData data : chickens) {
			if (data.getId().equals("c_vanilla")) continue;

			boolean isMob = data.getMobOrMonster().equalsIgnoreCase("Mob");
			boolean fireImmune = !data.CanGetFireDamage;

			MobCategory category = isMob ? MobCategory.CREATURE : MobCategory.MONSTER;

			DeferredHolder<EntityType<?>, EntityType<BaseChickenEntity>> holder =
					registerChicken(data.getId(), category, fireImmune);

			DYNAMIC_ENTITIES.put(data.getId(), holder);
		}
	}


	/*@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		for (DeferredHolder<EntityType<?>, EntityType<BaseChickenEntity>> holder : DYNAMIC_ENTITIES.values()) {
			event.put(holder.get(), BaseChickenEntity.createAttributes().build());
		}
	}*/
	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		List<ChickenData> readItems = ChickenRoostMod.chickens;
		assert readItems != null;
		if(!readItems.isEmpty()){
			for(ChickenData etherItem : readItems){
				if (!etherItem.getId().equals("c_vanilla")) {
					String id = etherItem.getId();
					Identifier resourceLocation = Identifier.fromNamespaceAndPath(ChickenRoostMod.MODID, id);
					EntityType<? extends LivingEntity> entityType = (EntityType<? extends LivingEntity>) EntityType.byString(resourceLocation.toString()).orElse(EntityType.CHICKEN);
					event.put(entityType, BaseChickenEntity.createAttributes().build());
				}
			}
		}
	}
	public static void initChickenConfig() {
		for (ChickenData data : ChickenRoostMod.chickens) {
			DeferredHolder<EntityType<?>, EntityType<BaseChickenEntity>> holder = DYNAMIC_ENTITIES.get(data.getId());
			if (holder == null) continue;

			EntityType<?> type = holder.get();

			ChickenConfig.setEggTime(type, data.getEggtime());
			ChickenConfig.setTier(type, data.getTier());

			ChickenConfig.setIsFire(type, data.CanGetFireDamage);
			ChickenConfig.setIsProjectile(type, data.CanGetProjectileDamage);
			ChickenConfig.setIsExplosion(type, data.CanGetExplosionDamage);
			ChickenConfig.setIsFall(type, data.CanGetFallDamage);
			ChickenConfig.setIsDrowning(type, data.CanGetDrowningDamage);
			ChickenConfig.setIsFreezing(type, data.CanGetFreezingDamage);
			ChickenConfig.setIsLightning(type, data.CanGetLightningDamage);
			ChickenConfig.setIsWither(type, data.CanGetWitherDamage);

			Identifier dropId = Identifier.parse(data.getDropitem());
			ChickenConfig.setDropStack(type,
					new ItemStack(BuiltInRegistries.ITEM.get(dropId).get()));
		}
	}
	public static void register(IEventBus eventBus) {
		readFromJson();                 // ⬅️ VOR Registry
		ENTITY_TYPES.register(eventBus);
	}


	/*@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		event.enqueueWork(BaseChickenEntity::init);
	}

	public static final Map<EntityType<?>, List<Identifier>> ENTITY_TO_BIOMES = new HashMap<>();

	public static void initChickenConfig() {
		List<ChickenData> readItems = ChickenRoostMod.chickens;
		assert readItems != null;
		if(!readItems.isEmpty()){
			for(ChickenData etherItem : readItems){
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
					Identifier identifier = Identifier.fromNamespaceAndPath(ChickenRoostMod.MODID, id);
					EntityType<?> entityType = EntityType.byString(identifier.toString()).orElse(EntityType.CHICKEN);
					ChickenConfig.setDropStack(entityType, new ItemStack(BuiltInRegistries.ITEM.get(identifier.parse(dropitem)).get()));
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
		if(!readItems.isEmpty()){
			for(ChickenData etherItem : readItems){
				if (!etherItem.getId().equals("c_vanilla")) {
					String id = etherItem.getId();
					Identifier identifier = Identifier.fromNamespaceAndPath(ChickenRoostMod.MODID, id);
					EntityType<? extends LivingEntity> entityType = (EntityType<? extends LivingEntity>) EntityType.byString(identifier.toString()).orElse(EntityType.CHICKEN);
					event.put(entityType, BaseChickenEntity.createAttributes().build());
				}
			}
		}
	}

	public static void register(IEventBus eventBus) {
		readthis();
		REGISTRY.register(eventBus);
	}*/
}
