package net.tarantel.chickenroost.handler;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.RegistryObject;

public class ModHandlers {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, ChickenRoostMod.MODID);

    public static final RegistryObject<MenuType<SoulBreeder_Handler>> SOUL_BREEDER_MENU =
            registerMenuType(SoulBreeder_Handler::new, "new_soul_breeder_menu");
    public static final RegistryObject<MenuType<Breeder_Handler>> BREEDER_MENU =
            registerMenuType(Breeder_Handler::new, "breeder_menu");
    public static final RegistryObject<MenuType<SoulExtractor_Handler>> SOUL_EXTRACTOR_MENU =
            registerMenuType(SoulExtractor_Handler::new, "soul_extractor_menu");
    public static final RegistryObject<MenuType<Roost_Handler>> ROOST_MENU_V1 =
            registerMenuType(Roost_Handler::new, "roost_menu_v1");
    public static final RegistryObject<MenuType<Trainer_Handler>> TRAINER =
            registerMenuType(Trainer_Handler::new, "trainer");
    public static final RegistryObject<MenuType<ChickenBookHandler>> BOOK =
            registerMenuType(ChickenBookHandler::new, "book");



    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory,
                                                                                                  String name) {
        return MENUS.register(name, () -> IMenuTypeExtension.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}