package net.tarantel.chickenroost.spawn;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tarantel.chickenroost.ChickenRoostMod;

@Mod.EventBusSubscriber(modid = ChickenRoostMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCapabilities {

    public static final Capability<IChickenTorchChunkData> TORCH_DATA =
            CapabilityManager.get(new CapabilityToken<>(){});

    @SubscribeEvent
    public static void registerCaps(RegisterCapabilitiesEvent event) {
        event.register(IChickenTorchChunkData.class);
    }
}
