package net.tarantel.chickenroost.api.jade;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Chicken;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.api.jade.components.*;
import net.tarantel.chickenroost.block.blocks.*;
import net.tarantel.chickenroost.block.tile.*;
import net.tarantel.chickenroost.pipe.PipeBlock;
import net.tarantel.chickenroost.pipe.PipeBlockEntity;
import snownee.jade.api.*;

@WailaPlugin(ChickenRoostMod.MODID)
public class JadePlugin implements IWailaPlugin {

    public static final ResourceLocation ROOST_PROGRESS = ChickenRoostMod.ownresource("roost.progress");
    public static final ResourceLocation BREEDER_PROGRESS = ChickenRoostMod.ownresource("breeder.progress");
    public static final ResourceLocation TRAINER_PROGRESS = ChickenRoostMod.ownresource("trainer.progress");
    public static final ResourceLocation SOULEXTRACTOR_PROGRESS = ChickenRoostMod.ownresource("soul_extractor.progress");
    public static final ResourceLocation BASECHICKENENTITY = ChickenRoostMod.ownresource("basechickenentity.data");
    public static final ResourceLocation COLLECTOR = ChickenRoostMod.ownresource("collector.state");
    public static final ResourceLocation FEEDER = ChickenRoostMod.ownresource("feeder.state");
    public static final ResourceLocation PIPE = ChickenRoostMod.ownresource("pipe.state");
    public static IWailaClientRegistration CLIENT_REGISTRATION;

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(RoostComponentProvider.INSTANCE, RoostTile.class);

        registration.registerBlockDataProvider(BreederComponentProvider.INSTANCE, BreederTile.class);

        registration.registerBlockDataProvider(TrainerComponentProvider.INSTANCE, TrainerTile.class);

        registration.registerBlockDataProvider(SoulExtractorComponentProvider.INSTANCE, SoulExtractorTile.class);
        registration.registerBlockDataProvider(CollectorComponentProvider.INSTANCE, CollectorTile.class);
        registration.registerBlockDataProvider(FeederComponentProvider.INSTANCE, FeederTile.class);

        registration.registerBlockDataProvider(PipeComponentProvider.INSTANCE, PipeBlockEntity.class);

        registration.registerEntityDataProvider(ChickenComponentProvider.INSTANCE, Chicken.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        CLIENT_REGISTRATION = registration;

        registration.registerBlockComponent(RoostComponentProvider.INSTANCE, RoostBlock.class);
        registration.registerBlockComponent(BreederComponentProvider.INSTANCE, BreederBlock.class);
        registration.registerBlockComponent(TrainerComponentProvider.INSTANCE, TrainerBlock.class);
        registration.registerBlockComponent(SoulExtractorComponentProvider.INSTANCE, SoulExtractorBlock.class);
        registration.registerBlockComponent(CollectorComponentProvider.INSTANCE, CollectorBlock.class);
        registration.registerBlockComponent(FeederComponentProvider.INSTANCE, FeederBlock.class);
        registration.registerBlockComponent(PipeComponentProvider.INSTANCE, PipeBlock.class);
        registration.registerEntityComponent(ChickenComponentProvider.INSTANCE, Chicken.class);
    }

}


