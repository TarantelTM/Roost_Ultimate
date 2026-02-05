package net.tarantel.chickenroost.util;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.tarantel.chickenroost.ChickenRoostMod;

import java.util.Optional;

@EventBusSubscriber(modid = ChickenRoostMod.MODID)
public class CustomRightClickEvents {

    public static void placeEntity(Entity entity, Entity source) {
        entity.setPos(
                source.getX(),
                source.getY(),
                source.getZ()
        );
        entity.setYRot(source.getYRot());
        entity.setXRot(source.getXRot());
        entity.setYHeadRot(source.getYRot());
        entity.setYBodyRot(source.getYRot());
    }

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getEntity();
        ItemStack itemStack = event.getItemStack();

        if (!(event.getTarget() instanceof LivingEntity targetEntity)) return;
        if (targetEntity.getType() != EntityType.CHICKEN) return;
        if (itemStack.getItem() != Items.FEATHER) return;

        Level world = player.level();
        if (world.isClientSide()) return;

        Identifier id =
                 Identifier.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_feather");

        Optional<EntityType<?>> entityType =
                BuiltInRegistries.ENTITY_TYPE.getOptional(id);

        if (world instanceof ServerLevel serverLevel && entityType.isPresent()) {

            Entity babyChicken = entityType.get().create(serverLevel, EntitySpawnReason.MOB_SUMMONED);
            if (babyChicken != null) {

                babyChicken.setPos(
                        targetEntity.getX(),
                        targetEntity.getY(),
                        targetEntity.getZ()
                );
                babyChicken.setYRot(targetEntity.getYRot());
                babyChicken.setXRot(targetEntity.getXRot());
                babyChicken.setYHeadRot(targetEntity.getYRot());
                babyChicken.setYBodyRot(targetEntity.getYRot());

                serverLevel.addFreshEntity(babyChicken);
                targetEntity.discard();
            }

            if (!player.isCreative()) {
                itemStack.shrink(1);
            }

            event.setCanceled(true);
        }
    }
}