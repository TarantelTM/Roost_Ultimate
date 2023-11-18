package net.tarantel.chickenroost;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.tarantel.chickenroost.block.ModBlocks;
import net.tarantel.chickenroost.block.entity.ModBlockEntities;
import net.tarantel.chickenroost.block.entity.client.ChickenRendererBreeder;
import net.tarantel.chickenroost.block.entity.client.ChickenRendererRoost;
import net.tarantel.chickenroost.client.EntityRenderers;
import net.tarantel.chickenroost.client.model.Modelregistry;
import net.tarantel.chickenroost.client.screen.breeder_screen;
import net.tarantel.chickenroost.client.screen.roost_screen;
import net.tarantel.chickenroost.client.screen.trainer_screen;
import net.tarantel.chickenroost.handler.ModScreenHandlers;
import net.tarantel.chickenroost.network.DataReceiver;

public class ChickenRoostModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        DataReceiver.registerS2CPackets();
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CROPBLOCK_1, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CROPBLOCK_2, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CROPBLOCK_3, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CROPBLOCK_4, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CROPBLOCK_5, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CROPBLOCK_6, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CROPBLOCK_7, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CROPBLOCK_8, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CROPBLOCK_9, RenderLayer.getCutout());
        HandledScreens.register(ChickenRoostMod.BREEDER_FABRIC_HANDLER, breeder_screen::new);
        HandledScreens.register(ModScreenHandlers.BREEDER, breeder_screen::new);
        HandledScreens.register(ModScreenHandlers.TRAINER, trainer_screen::new);
        HandledScreens.register(ModScreenHandlers.ROOST, roost_screen::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.BREEDER, ChickenRendererBreeder::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.ROOST, ChickenRendererRoost::new);
        EntityRenderers.load();
        Modelregistry.load();
    }
}