package net.tarantel.chickenroost.item;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.tarantel.chickenroost.ChickenRoostMod;

@EventBusSubscriber(modid = ChickenRoostMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public final class CapabilityRegistration {
    private static final int CAPACITY_MB = 1000;

    private CapabilityRegistration() {}

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.registerItem(
                Capabilities.FluidHandler.ITEM,
                (stack, ctx) -> {

                    if (stack.getItem() instanceof UniversalFluidItem) {
                        return new SingleUseFluidHandler(stack);
                    }
                    return null;
                },
                ModItems.LAVA_EGG.get(),
                ModItems.WATER_EGG.get()

        );
    }
}