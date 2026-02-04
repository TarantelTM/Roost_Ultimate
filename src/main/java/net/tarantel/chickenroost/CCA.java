package net.tarantel.chickenroost;

import dan200.computercraft.api.peripheral.PeripheralCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.tarantel.chickenroost.api.cc.ChickenBreederPeripheral;
import net.tarantel.chickenroost.api.cc.ChickenRoostPeripheral;
import net.tarantel.chickenroost.api.cc.ChickenSoulExtractorPeripheral;
import net.tarantel.chickenroost.api.cc.ChickenTrainerPeripheral;
import net.tarantel.chickenroost.block.tile.ModBlockEntities;

public class CCA {
   public static void registercctweaked(RegisterCapabilitiesEvent event) {
      event.registerBlockEntity(PeripheralCapability.get(), ModBlockEntities.TRAINER.get(), (be, ctx) -> new ChickenTrainerPeripheral(be));
      event.registerBlockEntity(PeripheralCapability.get(), ModBlockEntities.ROOST.get(), (be, ctx) -> new ChickenRoostPeripheral(be));
      event.registerBlockEntity(PeripheralCapability.get(), ModBlockEntities.BREEDER.get(), (be, ctx) -> new ChickenBreederPeripheral(be));
      event.registerBlockEntity(PeripheralCapability.get(), ModBlockEntities.SOUL_EXTRACTOR.get(), (be, ctx) -> new ChickenSoulExtractorPeripheral(be));
   }
}
