package net.tarantel.chickenroost.handler;

import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModHandlers {
   public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, "chicken_roost");
   public static final Supplier<MenuType<BreederHandler>> BREEDER_MENU = registerMenuType(BreederHandler::new, "breeder_menu");
   public static final Supplier<MenuType<SoulExtractorHandler>> SOUL_EXTRACTOR_MENU = registerMenuType(SoulExtractorHandler::new, "soul_extractor_menu");
   public static final Supplier<MenuType<RoostHandler>> ROOST_MENU_V1 = registerMenuType(RoostHandler::new, "roost_menu_v1");
   public static final Supplier<MenuType<TrainerHandler>> TRAINER = registerMenuType(TrainerHandler::new, "trainer");
   public static final Supplier<MenuType<CollectorHandler>> COLLECTOR_MENU = registerMenuType(CollectorHandler::new, "collector_menu");
   public static final Supplier<MenuType<FeederHandler>> FEEDER_MENU = registerMenuType(FeederHandler::new, "feeder_menu");

   private static <T extends AbstractContainerMenu> Supplier<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name) {
      return MENU_TYPES.register(name, () -> IMenuTypeExtension.create(factory));
   }

   public static void register(IEventBus eventBus) {
      MENU_TYPES.register(eventBus);
   }
}
