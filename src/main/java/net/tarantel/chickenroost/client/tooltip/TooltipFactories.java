package net.tarantel.chickenroost.client.tooltip;


import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tarantel.chickenroost.ChickenRoostMod;

@Mod.EventBusSubscriber(
        modid = ChickenRoostMod.MODID,
        bus = Mod.EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT
)
public class TooltipFactories {

    @SubscribeEvent
    public static void register(RegisterClientTooltipComponentFactoriesEvent e) {
        //System.out.println(">>> REGISTER StackLineTooltip <<<");
        e.register(
                net.tarantel.chickenroost.client.tooltip.StackLineTooltip.class,
                net.tarantel.chickenroost.client.tooltip.ClientStackLineTooltip::new
        );
    }
}
