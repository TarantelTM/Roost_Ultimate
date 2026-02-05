package net.tarantel.chickenroost.api.jade;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Chicken;
import net.tarantel.chickenroost.entity.BaseChickenEntity;
import net.tarantel.chickenroost.item.ModItems;
import net.tarantel.chickenroost.util.ChickenConfig;
import net.tarantel.chickenroost.util.ChickenData;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;

public enum ChickenComponentProvider implements IEntityComponentProvider, IServerDataProvider<EntityAccessor> {
    INSTANCE;

    private ChickenComponentProvider(){

    }
    @Override
    public int getDefaultPriority() {
        return TooltipPosition.BODY;
    }

    @Override
    public ResourceLocation getUid() {
        return JadePlugin.BASECHICKENENTITY;
    }

    @Override
    public void appendTooltip(ITooltip iTooltip, EntityAccessor entityAccessor, IPluginConfig iPluginConfig) {
        Entity entity = entityAccessor.getEntity();
        CompoundTag data = entityAccessor.getServerData();
        if(entity.getType() == EntityType.CHICKEN){
            iTooltip.add(Component.literal("Level: " + data.getInt("roost_lvl")));
            iTooltip.add(Component.literal("XP: " + data.getInt("roost_xp")));
            iTooltip.add(Component.literal("Tier: " + ChickenConfig.getTier(EntityType.CHICKEN)));
        }
        if (entity instanceof BaseChickenEntity chicken) {
            iTooltip.add(Component.literal("Level: " + data.getInt("roost_lvl")));
            iTooltip.add(Component.literal("XP: " + data.getInt("roost_xp")));
            iTooltip.add(Component.literal("Tier: " + ChickenConfig.getTier(chicken.getType())));
        }
    }

    @Override
    public void appendServerData(CompoundTag compoundTag, EntityAccessor entityAccessor) {
        Entity entity = entityAccessor.getEntity();
        if(entity.getType() == EntityType.CHICKEN){
            compoundTag.putInt("roost_lvl", entity.getPersistentData().getInt("roost_lvl"));
            compoundTag.putInt("roost_xp", entity.getPersistentData().getInt("roost_xp"));
            compoundTag.putInt("tier", entity.getPersistentData().getInt("tier"));
        }
        if (entity instanceof BaseChickenEntity chicken) {
            compoundTag.putInt("roost_lvl", entity.getPersistentData().getInt("roost_lvl"));
            compoundTag.putInt("roost_xp", entity.getPersistentData().getInt("roost_xp"));

            // ✅ Tier mitsenden
            compoundTag.putInt("roost_tier", chicken.TIER);
        }
    }
}