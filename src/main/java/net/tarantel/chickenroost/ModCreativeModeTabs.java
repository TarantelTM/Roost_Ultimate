package net.tarantel.chickenroost;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.tarantel.chickenroost.block.blocks.ModBlocks;

import java.util.function.Supplier;


public class ModCreativeModeTabs {

	public static final DeferredRegister<CreativeModeTab> TAB_CHICKEN_ROOST = DeferredRegister.create(Registries.CREATIVE_MODE_TAB,
			ChickenRoostMod.MODID);


	public static Supplier<CreativeModeTab> TAB_CHICKEN_ROOST_TAB = TAB_CHICKEN_ROOST.register("tab_chicken_roost", () ->
			CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.ROOST.get()))
					.title(Component.translatable("tab.chicken_roost.roost")).build());

	public static void register(IEventBus eventBus) {
		TAB_CHICKEN_ROOST.register(eventBus);
	}
}
