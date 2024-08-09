package net.tarantel.chickenroost.util;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.tarantel.chickenroost.ChickenRoostMod;

public class ModDataComponents {

    public static final DeferredRegister<DataComponentType<?>> COMPONENTS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, ChickenRoostMod.MODID);

    public static void register(IEventBus bus) {
        COMPONENTS.register(bus);
    }

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> CHICKENLEVEL = COMPONENTS.register("chickenlevel",
            () -> DataComponentType.<Integer>builder()
                    .persistent(ExtraCodecs.intRange(0, 10000))
                    .networkSynchronized(ByteBufCodecs.VAR_INT)
                    .build()
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> CHICKENXP = COMPONENTS.register("chickenxp",
            () -> DataComponentType.<Integer>builder()
                    .persistent(ExtraCodecs.intRange(0, 100000000))
                    .networkSynchronized(ByteBufCodecs.VAR_INT)
                    .build()
    );

}
