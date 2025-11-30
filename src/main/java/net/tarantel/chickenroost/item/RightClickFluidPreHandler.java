package net.tarantel.chickenroost.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.tarantel.chickenroost.ChickenRoostMod;


@EventBusSubscriber(modid = ChickenRoostMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class RightClickFluidPreHandler {


    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock e) {
        Player player = e.getEntity();
        if (player.isSpectator()) return;
        InteractionHand hand = e.getHand();
        if (!(player.getItemInHand(hand).getItem() instanceof UniversalFluidItem item)) return;


        Level level = player.level();
        BlockHitResult hit = e.getHitVec();
        BlockPos pos = hit.getBlockPos();
        Direction face = hit.getDirection();


        boolean requireFull = item.getAmount() >= 1000;
        boolean filled = item.tryFillAnyHandler(level, pos, face, requireFull)
                || item.tryFillAnyHandler(level, pos, null, requireFull);


        if (filled) {
            if (!player.getAbilities().instabuild) {
                player.getItemInHand(hand).shrink(1);
            }

            e.setCanceled(true);
            e.setCancellationResult(InteractionResult.SUCCESS);
            player.swing(hand);
        }
    }
}