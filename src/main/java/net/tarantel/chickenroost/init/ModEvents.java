package net.tarantel.chickenroost.init;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.particle.ModParticles;
import net.tarantel.chickenroost.particle.SoulParticles;

@Mod.EventBusSubscriber(modid = ChickenRoostMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerParticleFactories(final RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.SOUL_PARTICLES.get(), SoulParticles.Provider::new);
        /*Minecraft.getInstance().particleEngine.register(ModParticles.SOUL_PARTICLES.get(),
                SoulParticles.Provider::new);*/
    }
}