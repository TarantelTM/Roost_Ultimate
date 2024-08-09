package net.tarantel.chickenroost.handler;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.tarantel.chickenroost.screen.OwnCraftingMenu;

import java.util.function.Supplier;

public class ModHandlers {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, ChickenRoostMod.MODID);

    public static final Supplier<MenuType<SoulBreeder_Handler>> SOUL_BREEDER_MENU =
            registerMenuType(SoulBreeder_Handler::new, "new_soul_breeder_menu");
    public static final Supplier<MenuType<Breeder_Handler>> BREEDER_MENU =
            registerMenuType(Breeder_Handler::new, "breeder_menu");
    public static final Supplier<MenuType<SoulExtractor_Handler>> SOUL_EXTRACTOR_MENU =
            registerMenuType(SoulExtractor_Handler::new, "soul_extractor_menu");
    public static final Supplier<MenuType<Roost_Handler>> ROOST_MENU_V1 =
            registerMenuType(Roost_Handler::new, "roost_menu_v1");
    public static final Supplier<MenuType<Trainer_Handler>> TRAINER =
            registerMenuType(Trainer_Handler::new, "trainer");
    public static final Supplier<MenuType<ChickenBookHandler>> BOOK =
            registerMenuType(ChickenBookHandler::new, "book");


    private static <T extends AbstractContainerMenu> Supplier<MenuType<T>> registerMenuType(IContainerFactory<T> factory,
                                                                                                  String name) {
        return MENU_TYPES.register(name, () -> IMenuTypeExtension.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENU_TYPES.register(eventBus);
    }
}