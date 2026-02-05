package net.tarantel.chickenroost.api.jade.components;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Chicken;
import net.tarantel.chickenroost.api.jade.JadePlugin;
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
        if(entity instanceof Chicken chickenEntity){
            iTooltip.add(Component.literal("Level: " + data.getInt("chickenlevel")));
            iTooltip.add(Component.literal("XP: " + data.getInt("chickenxp")));
        }
    }

    @Override
    public void appendServerData(CompoundTag compoundTag, EntityAccessor entityAccessor) {
        Entity entity = entityAccessor.getEntity();
        if(entity instanceof Chicken chickenEntity){
            compoundTag.putInt("chickenlevel", entity.getPersistentData().getInt("chickenlevel"));
            compoundTag.putInt("chickenxp", entity.getPersistentData().getInt("chickenxp"));
        }
    }
}