package net.tarantel.chickenroost.block.tile;

import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.capabilities.Capabilities.ItemHandler;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.pipe.PipeBlockEntity;

public class ModBlockEntities {
   public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, "chicken_roost");
   public static final Supplier<BlockEntityType<CollectorTile>> COLLECTOR = BLOCK_ENTITIES.register(
      "collector", () -> Builder.of(CollectorTile::new, new Block[]{(Block)ModBlocks.COLLECTOR.get()}).build(null)
   );
   public static final Supplier<BlockEntityType<FeederTile>> FEEDER = BLOCK_ENTITIES.register(
      "feeder", () -> Builder.of(FeederTile::new, new Block[]{(Block)ModBlocks.FEEDER.get()}).build(null)
   );
   public static final Supplier<BlockEntityType<BreederTile>> BREEDER = BLOCK_ENTITIES.register(
      "breeder", () -> Builder.of(BreederTile::new, new Block[]{(Block)ModBlocks.BREEDER.get()}).build(null)
   );
   public static final Supplier<BlockEntityType<ChickenStorageTile>> CHICKENSTORAGE = BLOCK_ENTITIES.register(
      "chickenstorage", () -> Builder.of(ChickenStorageTile::new, new Block[]{(Block)ModBlocks.CHICKENSTORAGE.get()}).build(null)
   );
   public static final Supplier<BlockEntityType<SoulExtractorTile>> SOUL_EXTRACTOR = BLOCK_ENTITIES.register(
      "soul_extractor", () -> Builder.of(SoulExtractorTile::new, new Block[]{(Block)ModBlocks.SOUL_EXTRACTOR.get()}).build(null)
   );
   public static final Supplier<BlockEntityType<RoostTile>> ROOST = BLOCK_ENTITIES.register(
      "roost", () -> Builder.of(RoostTile::new, new Block[]{(Block)ModBlocks.ROOST.get()}).build(null)
   );
   public static final Supplier<BlockEntityType<TrainerTile>> TRAINER = BLOCK_ENTITIES.register(
      "trainer", () -> Builder.of(TrainerTile::new, new Block[]{(Block)ModBlocks.TRAINER.get()}).build(null)
   );
   public static final Supplier<BlockEntityType<PipeBlockEntity>> PIPE = BLOCK_ENTITIES.register(
      "pipe",
      () -> Builder.of(
            PipeBlockEntity::new,
            new Block[]{
               (Block)ModBlocks.PIPE_TIER1.get(), (Block)ModBlocks.PIPE_TIER2.get(), (Block)ModBlocks.PIPE_TIER3.get(), (Block)ModBlocks.PIPE_TIER4.get()
            }
         )
         .build(null)
   );

   public static void register(IEventBus eventBus) {
      BLOCK_ENTITIES.register(eventBus);
   }

   public static void registerCapabilities(RegisterCapabilitiesEvent event) {
      event.registerBlockEntity(ItemHandler.BLOCK, CHICKENSTORAGE.get(), ChickenStorageTile::getItemHandlerCapability);
      event.registerBlockEntity(ItemHandler.BLOCK, BREEDER.get(), BreederTile::getItemHandlerCapability);
      event.registerBlockEntity(ItemHandler.BLOCK, SOUL_EXTRACTOR.get(), SoulExtractorTile::getItemHandlerCapability);
      event.registerBlockEntity(ItemHandler.BLOCK, ROOST.get(), RoostTile::getItemHandlerCapability);
      event.registerBlockEntity(ItemHandler.BLOCK, COLLECTOR.get(), CollectorTile::getItemHandlerCapability);
      event.registerBlockEntity(ItemHandler.BLOCK, TRAINER.get(), TrainerTile::getItemHandlerCapability);
      event.registerBlockEntity(ItemHandler.BLOCK, FEEDER.get(), FeederTile::getItemHandlerCapability);
   }
}
