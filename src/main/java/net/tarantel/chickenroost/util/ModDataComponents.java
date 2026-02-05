package net.tarantel.chickenroost.util;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.tarantel.chickenroost.ChickenRoostMod;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class ModDataComponents {


    public static final DeferredRegister<DataComponentType<?>> COMPONENTS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, ChickenRoostMod.MODID);
    public static final DeferredRegister.DataComponents DATA_COMPONENTS =
            DeferredRegister.createDataComponents(ChickenRoostMod.MODID);

    public static void register(IEventBus bus) {
        COMPONENTS.register(bus);
        DATA_COMPONENTS.register(bus);
    }

    public static final Supplier<DataComponentType<ChickenStats>> CHICKEN_STATS =
            DATA_COMPONENTS.register("chicken_stats",
                    () -> DataComponentType.<ChickenStats>builder()
                            .persistent(ChickenStats.CODEC)
                            .build()
            );
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SimpleFluidContent>> SIMPLE_FLUID_CONTENT =
            DATA_COMPONENTS.registerComponentType(
                    "simple_fluid_content",
                    (UnaryOperator<DataComponentType.Builder<SimpleFluidContent>>) builder ->
                            builder

                                    .persistent(SimpleFluidContent.CODEC)
                                    .networkSynchronized(SimpleFluidContent.STREAM_CODEC)
            );
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> CHICKENLEVEL = COMPONENTS.register("chickenlevel",
            () -> DataComponentType.<Integer>builder()
                    .persistent(ExtraCodecs.intRange(0, 10000))
                    .networkSynchronized(ByteBufCodecs.VAR_INT)
                    .build()
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> MAXLEVEL =
            COMPONENTS.register("maxlevel",
                    () -> DataComponentType.<Boolean>builder()
                            .persistent(Codec.BOOL)
                            .networkSynchronized(ByteBufCodecs.BOOL)
                            .build()
            );


    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> CHICKENXP = COMPONENTS.register("chickenxp",
            () -> DataComponentType.<Integer>builder()
                    .persistent(ExtraCodecs.intRange(0, 100000000))
                    .networkSynchronized(ByteBufCodecs.VAR_INT)
                    .build()
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> AGE = COMPONENTS.register("age",
            () -> DataComponentType.<Integer>builder()
                    .persistent(ExtraCodecs.intRange(0, 100000000))
                    .networkSynchronized(ByteBufCodecs.VAR_INT)
                    .build()
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> STORAGEAMOUNT = COMPONENTS.register("storageamount",
            () -> DataComponentType.<Integer>builder()
                    .persistent(ExtraCodecs.intRange(0, 100000000))
                    .networkSynchronized(ByteBufCodecs.VAR_INT)
                    .build()
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<RoostItemContainerContents>> CONTAINER = COMPONENTS.register(
            "container",() -> DataComponentType.<RoostItemContainerContents>builder().persistent(RoostItemContainerContents.CODEC).networkSynchronized(RoostItemContainerContents.STREAM_CODEC).cacheEncoding().build()
    );
}
