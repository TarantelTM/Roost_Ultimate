
package net.tarantel.chickenroost.util;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tarantel.chickenroost.block.ModBlocks;

public class ModTabs {
	public static CreativeModeTab TAB_CHICKEN_ROOST;


	public static void load() {
		TAB_CHICKEN_ROOST = new CreativeModeTab("tabchicken_roost") {
			@Override
			public ItemStack makeIcon() {
				return new ItemStack(ModBlocks.ROOST.get());
			}


			@OnlyIn(Dist.CLIENT)
			public boolean hasSearchBar() {
				return false;
			}
		};
	}
}
