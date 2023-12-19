package net.tarantel.chickenroost.api.jade;

import net.minecraft.resources.ResourceLocation;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.block.tile.*;
import snownee.jade.api.*;

@WailaPlugin
public class JadePlugin implements IWailaPlugin {

    public static final ResourceLocation ROOST_PROGRESS = new ResourceLocation(ChickenRoostMod.MODID,"roost.progress");
    public static final ResourceLocation BREEDER_PROGRESS = new ResourceLocation(ChickenRoostMod.MODID,"breeder.progress");
    public static final ResourceLocation TRAINER_PROGRESS = new ResourceLocation(ChickenRoostMod.MODID,"trainer.progress");
    public static final ResourceLocation SOULBREEDER_PROGRESS = new ResourceLocation(ChickenRoostMod.MODID,"soul_breeder.progress");
    public static final ResourceLocation SOULEXTRACTOR_PROGRESS = new ResourceLocation(ChickenRoostMod.MODID,"soul_extractor.progress");
    private static IWailaClientRegistration client;

    @Override
    public void register(IWailaCommonRegistration registration) {
        //TODO register data providers
        registration.registerBlockDataProvider(RoostComponentProvider.INSTANCE, Roost_Tile.class);
        registration.registerProgress(RoostComponentProvider.INSTANCE, Roost_Tile.class);

        registration.registerBlockDataProvider(BreederProgressProvider.INSTANCE, Breeder_Tile.class);
        registration.registerProgress(BreederProgressProvider.INSTANCE, Breeder_Tile.class);

        registration.registerBlockDataProvider(TrainerProgressProvider.INSTANCE, Trainer_Tile.class);
        registration.registerProgress(TrainerProgressProvider.INSTANCE, Trainer_Tile.class);

        registration.registerBlockDataProvider(SoulBreederProgressProvider.INSTANCE, Soul_Breeder_Tile.class);
        registration.registerProgress(SoulBreederProgressProvider.INSTANCE, Soul_Breeder_Tile.class);

        registration.registerBlockDataProvider(SoulExtractorProgressProvider.INSTANCE, Soul_Extractor_Tile.class);
        registration.registerProgress(SoulExtractorProgressProvider.INSTANCE, Soul_Extractor_Tile.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        //TODO register component providers, icon providers, callbacks, and config options here
        JadePlugin.client = registration;
        registration.registerProgressClient(RoostComponentProvider.INSTANCE);
        registration.registerProgressClient(BreederProgressProvider.INSTANCE);
        registration.registerProgressClient(TrainerProgressProvider.INSTANCE);
        registration.registerProgressClient(SoulBreederProgressProvider.INSTANCE);
        registration.registerProgressClient(SoulExtractorProgressProvider.INSTANCE);
        //registration.registerBlockComponent(RoostComponentProvider.INSTANCE, Roost_Block.class);
    }

}


