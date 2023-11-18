package net.tarantel.chickenroost.handler;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.tarantel.chickenroost.ChickenRoostMod;


public class ModScreenHandlers {

    public static final ScreenHandlerType<breeder_handler> BREEDER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(ChickenRoostMod.MODID, "screen_breeder"), new ScreenHandlerType<>(breeder_handler::new, FeatureFlags.DEFAULT_ENABLED_FEATURES));
    public static final ScreenHandlerType<trainer_handler> TRAINER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(ChickenRoostMod.MODID, "screen_trainer"), new ScreenHandlerType<>(trainer_handler::new, FeatureFlags.DEFAULT_ENABLED_FEATURES));
    public static final ScreenHandlerType<roost_handler> ROOST =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(ChickenRoostMod.MODID, "screen_roost"), new ScreenHandlerType<>(roost_handler::new, FeatureFlags.DEFAULT_ENABLED_FEATURES));




    /*public static final ScreenHandlerType<breeder_handler> BREEDER =
            new ScreenHandlerType<>(breeder_handler::new);

    public static ScreenHandlerType<trainer_handler> TRAINER =
            new ScreenHandlerType<>(trainer_handler::new);

    public static ScreenHandlerType<roost_handler> ROOST =
            new ScreenHandlerType<>(roost_handler::new);*/

    public static void registerAllScreenHandlers() {
        /*Registry.register(Registries.SCREEN_HANDLER, new Identifier(ChickenRoostMod.MODID, "breeder"),
                BREEDER);
        Registry.register(Registries.SCREEN_HANDLER, new Identifier(ChickenRoostMod.MODID, "trainer"),
                TRAINER);
        Registry.register(Registries.SCREEN_HANDLER, new Identifier(ChickenRoostMod.MODID, "roost"),
                ROOST);*/

    }
}