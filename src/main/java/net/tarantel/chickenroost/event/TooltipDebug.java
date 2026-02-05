//package net.tarantel.chickenroost.event;
//
//import com.mojang.datafixers.util.Either;
//import net.minecraft.network.chat.Component;
//import net.minecraft.world.inventory.tooltip.TooltipComponent;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.Items;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.client.event.RenderTooltipEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod;
//import net.tarantel.chickenroost.client.tooltip.StackLineTooltip;
//
//@Mod.EventBusSubscriber(
//        modid = "chicken_roost",
//        bus = Mod.EventBusSubscriber.Bus.FORGE,
//        value = Dist.CLIENT
//)
//public class TooltipDebug {
//
//    @SubscribeEvent
//    public static void onGather(RenderTooltipEvent.GatherComponents e) {
//        System.out.println(">>> GATHER TOOLTIP FIRED <<<");
//
//        e.getTooltipElements().add(
//                Either.left(Component.literal("§cDEBUG LINE"))
//        );
//
//        e.getTooltipElements().add(
//                Either.right(
//                        new StackLineTooltip(
//                                new ItemStack(Items.DIAMOND)
//                        )
//                )
//        );
//    }
//}