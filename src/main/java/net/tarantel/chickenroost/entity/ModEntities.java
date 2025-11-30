
package net.tarantel.chickenroost.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.item.ModItems;
import net.tarantel.chickenroost.util.ChickenConfig;
import net.tarantel.chickenroost.util.ChickenData;
import net.tarantel.chickenroost.util.GsonChickenReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class ModEntities {

	public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(Registries.ENTITY_TYPE,
			ChickenRoostMod.MODID);

	public static final DeferredHolder<EntityType<?>, EntityType<BaseChickenEntity>> A_CHICKEN_LAVA = registerMonsterFireImmun("c_lava",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final DeferredHolder<EntityType<?>, EntityType<BaseChickenEntity>> A_CHICKEN_WATER = registerMob("c_water",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static <T extends Mob> DeferredHolder<EntityType<?>, EntityType<T>> registerMob(
			String name, EntityType.EntityFactory<T> factory,
			float width, float height, int primaryColor, int secondaryColor
	) {
		return REGISTRY.register(name, () -> EntityType.Builder.of(factory, MobCategory.CREATURE)
				.sized(0.4f, 0.7f)
				.clientTrackingRange(8)
				.build(name));
	}

	public static <T extends Mob> void registerMonster(
			String name, EntityType.EntityFactory<T> factory,
			float width, float height, int primaryColor, int secondaryColor
	) {
		REGISTRY.register(name, () -> EntityType.Builder.of(factory, MobCategory.MONSTER)
				.sized(0.4f, 0.7f)
				.clientTrackingRange(8)
				.build(name));
	}

	public static <T extends Mob> void registerMobFireImmun(
			String name, EntityType.EntityFactory<T> factory,
			float width, float height, int primaryColor, int secondaryColor
	) {
		REGISTRY.register(name, () -> EntityType.Builder.of(factory, MobCategory.CREATURE)
				.sized(0.4f, 0.7f)
				.clientTrackingRange(8)
				.fireImmune()
				.build(name));
	}

	public static <T extends Mob> DeferredHolder<EntityType<?>, EntityType<T>> registerMonsterFireImmun(
			String name, EntityType.EntityFactory<T> factory,
			float width, float height, int primaryColor, int secondaryColor
	) {
		return REGISTRY.register(name, () -> EntityType.Builder.of(factory, MobCategory.MONSTER)
				.sized(0.4f, 0.7f)
				.clientTrackingRange(8)
				.fireImmune()
				.build(name));
	}

	public static void readthis() {
		List<ChickenData> readItems = GsonChickenReader.readItemsFromFile();
        assert readItems != null;
        if(!readItems.isEmpty()){
			for(ChickenData etherItem : readItems){
				String id = etherItem.getId();
				String mobormonster = etherItem.getMobOrMonster();
				Boolean IS_FIRE = etherItem.getIsFire();
				extrachickens(id, mobormonster, IS_FIRE);
			}
		}
	}

	private static void extrachickens(String idd, String mobormonster, Boolean IS_FIRE) {
		if(mobormonster.equals("Mob")){
			if(IS_FIRE){
				registerMob(idd, BaseChickenEntity::new, 0.4f, 0.7f, 0x302219, 0xACACAC);
			}
			else {
				registerMobFireImmun(idd, BaseChickenEntity::new, 0.4f, 0.7f, 0x302219, 0xACACAC);
			}

		}
		else {
			if(IS_FIRE){
				registerMonster(idd, BaseChickenEntity::new, 0.4f, 0.7f, 0x302219, 0xACACAC);
			}
			else {
				registerMonsterFireImmun(idd, BaseChickenEntity::new, 0.4f, 0.7f, 0x302219, 0xACACAC);
			}

		}
	}



	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		event.enqueueWork(BaseChickenEntity::init);
	}

	public static final Map<EntityType<?>, List<ResourceLocation>> ENTITY_TO_BIOMES = new HashMap<>();

	public static void initChickenConfig() {
		List<ChickenData> readItems = GsonChickenReader.readItemsFromFile();
        assert readItems != null;
        if(!readItems.isEmpty()){
			for(ChickenData etherItem : readItems){

				String id = etherItem.getId();
				String dropitem = etherItem.getDropitem();
				int eggtime = etherItem.getEggtime();

				boolean IS_FIRE = etherItem.getIsFire();
				boolean IS_PROJECTILE = etherItem.getIsProjectile();
				boolean IS_EXPLOSION = etherItem.getIsExplosion();
				boolean IS_FALL = etherItem.getIsFall();
				boolean IS_DROWNING = etherItem.getIsDrowning();
				boolean IS_FREEZING = etherItem.getIsFreezing();
				boolean IS_LIGHTNING = etherItem.getIsLightning();
				boolean IS_WITHER = etherItem.getIsWither();
				int TIER = etherItem.getTier();
				ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(ChickenRoostMod.MODID, id);
				EntityType<?> entityType = EntityType.byString(resourceLocation.toString()).orElse(EntityType.CHICKEN);
				ChickenConfig.setDropStack(entityType, new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation.parse(dropitem))));
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

		ChickenConfig.setDropStack(A_CHICKEN_LAVA.get(), ModItems.LAVA_EGG.get().getDefaultInstance());
		ChickenConfig.setEggTime(A_CHICKEN_LAVA.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_WATER.get(), ModItems.WATER_EGG.get().getDefaultInstance());
		ChickenConfig.setEggTime(A_CHICKEN_WATER.get(), 600);
	}


	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		List<ChickenData> readItems = GsonChickenReader.readItemsFromFile();
        assert readItems != null;
        if(!readItems.isEmpty()){
			for(ChickenData etherItem : readItems){
				String id = etherItem.getId();
				ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(ChickenRoostMod.MODID, id);
				EntityType<? extends LivingEntity> entityType = (EntityType<? extends LivingEntity>) EntityType.byString(resourceLocation.toString()).orElse(EntityType.CHICKEN);
				event.put(entityType, BaseChickenEntity.createAttributes().build());
			}
		}
		event.put(A_CHICKEN_LAVA.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_WATER.get(), BaseChickenEntity.createAttributes().build());
	}

	public static void register(IEventBus eventBus) {
		readthis();
		REGISTRY.register(eventBus);
	}
}
