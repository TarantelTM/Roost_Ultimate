

package net.tarantel.chickenroost.entity;

import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.item.ModItems;
import net.tarantel.chickenroost.util.ChickenConfig;
import net.tarantel.chickenroost.util.GsonChickenReader;
import net.tarantel.chickenroost.util.ChickenData;

import java.util.List;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {


	public static final MobCategory ROOSTMOBS =
			MobCategory.create(
					"roostmobs",
					"roostmobs",
					15,     // Mobcap
					false,
					true,
					128
			);

	public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(Registries.ENTITY_TYPE, ChickenRoostMod.MODID);


	private static <T extends Entity> RegistryObject<EntityType<T>> registerMob(
			String name, EntityType.EntityFactory<T> factory,
			float width, float height, int primaryColor, int secondaryColor
	) {
		return REGISTRY.register(name, () -> EntityType.Builder.of(factory, ROOSTMOBS)
				.sized(0.4f, 0.7f)
				.clientTrackingRange(8)
				.build(name));
	}

	private static <T extends Entity> RegistryObject<EntityType<T>> registerMonster(
			String name, EntityType.EntityFactory<T> factory,
			float width, float height, int primaryColor, int secondaryColor
	) {
		return REGISTRY.register(name, () -> EntityType.Builder.of(factory, ROOSTMOBS)
				.sized(0.4f, 0.7f)
				.clientTrackingRange(8)
				.build(name));
	}

	private static <T extends Entity> RegistryObject<EntityType<T>> registerMobFireImmun(
			String name, EntityType.EntityFactory<T> factory,
			float width, float height, int primaryColor, int secondaryColor
	) {
		return REGISTRY.register(name, () -> EntityType.Builder.of(factory, ROOSTMOBS)
				.sized(0.4f, 0.7f)
				.clientTrackingRange(8)
				.fireImmune()
				.build(name));
	}

	private static <T extends Entity> RegistryObject<EntityType<T>> registerMonsterFireImmun(
			String name, EntityType.EntityFactory<T> factory,
			float width, float height, int primaryColor, int secondaryColor
	) {
		return REGISTRY.register(name, () -> EntityType.Builder.of(factory, ROOSTMOBS)
				.sized(0.4f, 0.7f)
				.clientTrackingRange(8)
				.fireImmune()
				.build(name));
	}

	public static void readthis() {
		List<ChickenData> readItems = ChickenRoostMod.chickens;
		if(!readItems.isEmpty()){
			for(ChickenData etherItem : readItems){
				String id = etherItem.getId();
				String mobormonster = etherItem.getMobOrMonster();
				Boolean IS_FIRE = etherItem.CanGetFireDamage;
				extrachickens(id, mobormonster, IS_FIRE);
			}
		}
	}

	private static RegistryObject<EntityType<BaseChickenEntity>> extrachickens(String idd, String mobormonster, Boolean IS_FIRE) {
		if(mobormonster.equals("Mob")){
			if(IS_FIRE){
				return registerMob(idd, BaseChickenEntity::new, 0.4f, 0.7f, 0x302219,0xACACAC);
			}
			else {
				return registerMobFireImmun(idd, BaseChickenEntity::new, 0.4f, 0.7f, 0x302219,0xACACAC);
			}

		}
		else {
			if(IS_FIRE){
				return registerMonster(idd, BaseChickenEntity::new, 0.4f, 0.7f, 0x302219,0xACACAC);
			}
			else {
				return registerMonsterFireImmun(idd, BaseChickenEntity::new, 0.4f, 0.7f, 0x302219,0xACACAC);
			}

		}

	}



	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			BaseChickenEntity.init();
		});
	}
	public static void initChickenConfig() {

		List<ChickenData> readItems = ChickenRoostMod.chickens;
		if(!readItems.isEmpty()){
			for(ChickenData etherItem : readItems){

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
				ResourceLocation resourceLocation = new ResourceLocation(ChickenRoostMod.MODID, id);
				EntityType<?> entityType = EntityType.byString(resourceLocation.toString()).orElse(EntityType.CHICKEN);
				ChickenConfig.setDropStack(entityType, new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(dropitem))));
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
	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		List<ChickenData> readItems = ChickenRoostMod.chickens;
		if(!readItems.isEmpty()){
			for(ChickenData etherItem : readItems){
				String id = etherItem.getId();
				ResourceLocation resourceLocation = new ResourceLocation(ChickenRoostMod.MODID, id);
				EntityType<? extends LivingEntity> entityType = (EntityType<? extends LivingEntity>) EntityType.byString(resourceLocation.toString()).orElse(EntityType.CHICKEN);
				event.put(entityType, BaseChickenEntity.createAttributes().build());
			}
		}
	}

	public static void register(IEventBus eventBus) {
		readthis();
		REGISTRY.register(eventBus);
	}
}