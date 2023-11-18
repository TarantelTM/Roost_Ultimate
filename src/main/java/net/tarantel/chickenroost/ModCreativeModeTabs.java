package net.tarantel.chickenroost;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.tarantel.chickenroost.block.blocks.ModBlocks;

@Mod.EventBusSubscriber(modid = ChickenRoostMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeModeTabs {

	public static final DeferredRegister<CreativeModeTab> TAB_CHICKEN_ROOST = DeferredRegister.create(Registries.CREATIVE_MODE_TAB,
			ChickenRoostMod.MODID);

	public static RegistryObject<CreativeModeTab> TAB_CHICKEN_ROOST_TAB = TAB_CHICKEN_ROOST.register("tab_chicken_roost", () ->
			CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.ROOST.get()))
					.title(Component.translatable("tab.chicken_roost.roost")).build());

	public static void register(IEventBus eventBus) {
		TAB_CHICKEN_ROOST.register(eventBus);
	}
}
