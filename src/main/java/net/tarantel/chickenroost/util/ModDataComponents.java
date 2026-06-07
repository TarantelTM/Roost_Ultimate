package net.tarantel.chickenroost.util;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredRegister.DataComponents;

public class ModDataComponents {
   public static final DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents("chicken_roost");
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<ChickenStats>> CHICKEN_STATS = DATA_COMPONENTS.registerComponentType(
      "chicken_stats", builder -> builder.persistent(ChickenStats.CODEC)
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<SimpleFluidContent>> SIMPLE_FLUID_CONTENT = DATA_COMPONENTS.registerComponentType(
      "simple_fluid_content", builder -> builder.persistent(SimpleFluidContent.CODEC).networkSynchronized(SimpleFluidContent.STREAM_CODEC)
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> CHICKENLEVEL = DATA_COMPONENTS.registerComponentType(
      "chickenlevel", builder -> builder.persistent(ExtraCodecs.intRange(0, 10000)).networkSynchronized(ByteBufCodecs.VAR_INT)
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> MAXLEVEL = DATA_COMPONENTS.registerComponentType(
      "maxlevel", builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL)
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> CHICKENXP = DATA_COMPONENTS.registerComponentType(
      "chickenxp", builder -> builder.persistent(ExtraCodecs.intRange(0, 100000000)).networkSynchronized(ByteBufCodecs.VAR_INT)
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> AGE = DATA_COMPONENTS.registerComponentType(
      "age", builder -> builder.persistent(ExtraCodecs.intRange(0, 100000000)).networkSynchronized(ByteBufCodecs.VAR_INT)
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> STORAGEAMOUNT = DATA_COMPONENTS.registerComponentType(
      "storageamount", builder -> builder.persistent(ExtraCodecs.intRange(0, 100000000)).networkSynchronized(ByteBufCodecs.VAR_INT)
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<RoostItemContainerContents>> CONTAINER = DATA_COMPONENTS.registerComponentType(
      "container",
      builder -> builder
         .persistent(RoostItemContainerContents.CODEC)
         .networkSynchronized(RoostItemContainerContents.STREAM_CODEC)
         .cacheEncoding()
   );

   public static void register(IEventBus bus) {
      DATA_COMPONENTS.register(bus);
   }
}
