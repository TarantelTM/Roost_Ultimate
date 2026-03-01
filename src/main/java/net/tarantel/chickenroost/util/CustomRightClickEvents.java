package net.tarantel.chickenroost.util;

import java.util.Optional;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.EntityInteract;

@EventBusSubscriber(modid = "chicken_roost", bus = Bus.GAME)
public class CustomRightClickEvents {
   @SubscribeEvent
   public static void onEntityInteract(EntityInteract event) {
      Player player = event.getEntity();
      ItemStack itemStack = event.getItemStack();
      ResourceLocation customType = ResourceLocation.fromNamespaceAndPath("chicken_roost", "c_feather");
      Optional<EntityType<?>> entityType = EntityType.byString(String.valueOf(customType));
      if (event.getTarget() instanceof LivingEntity targetEntity && targetEntity.getType() == EntityType.CHICKEN && itemStack.getItem() == Items.FEATHER) {
         Level world = player.level();
         if (!world.isClientSide()) {
            Entity babyChicken = entityType.get().create(world);
            if (babyChicken != null) {
               babyChicken.moveTo(targetEntity.getX(), targetEntity.getY(), targetEntity.getZ(), targetEntity.getYRot(), targetEntity.getXRot());
               player.level().addFreshEntity(babyChicken);
               targetEntity.discard();
            }

            if (!player.isCreative()) {
               itemStack.shrink(1);
            }
         }

         event.setCanceled(true);
      }
   }
}
