package net.tarantel.chickenroost.util;


import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.entity.BaseChickenEntity;

@Mod.EventBusSubscriber(modid = ChickenRoostMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CustomRightClickEvents {



    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        Level level = event.getLevel();
        Player player = event.getEntity();
        ItemStack itemStack = event.getItemStack();
        // Get the target entity

            ResourceLocation customType = new ResourceLocation("chicken_roost:c_feather");
            EntityType<?> entityType = ForgeRegistries.ENTITY_TYPES.getValue(customType);
            if (event.getTarget() instanceof LivingEntity) {
                LivingEntity targetEntity = (LivingEntity) event.getTarget();
                // Check if the entity is a chicken
                if (targetEntity.getType() == EntityType.CHICKEN) {
                    // Check if the player is holding a feather
                    if (itemStack.getItem() == Items.FEATHER) {
                        // Get the player's world
                        Level world = player.level();
                        // Perform custom logic (server-side only)
                        if (!world.isClientSide()) {


                            Entity babyChicken = entityType.create(world);
                            if (babyChicken != null) {
                                babyChicken.moveTo(targetEntity.getX(), targetEntity.getY(), targetEntity.getZ(), targetEntity.getYRot(), targetEntity.getXRot()); // Spawn at the same position
                                player.level().addFreshEntity(babyChicken); // Add the baby chicken to the world
                                targetEntity.discard();
                            }
                            if (!player.isCreative()) {
                                itemStack.shrink(1);
                            }
                        }

                        // Cancel the event to prevent further processing (optional)
                        event.setCanceled(true);
                    }
                }
            }
        }
    }