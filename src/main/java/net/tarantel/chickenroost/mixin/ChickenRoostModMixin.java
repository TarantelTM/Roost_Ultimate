package net.tarantel.chickenroost.mixin;

import com.google.common.primitives.UnsignedLong;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.item.ItemStack;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.item.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class ChickenRoostModMixin {
	@Inject(at = @At("HEAD"), method = "init()V")
	private void init(CallbackInfo info) {
		ChickenRoostMod.LOGGER.info("This line is printed by an example mod mixin! :");
	}
}
