package net.tarantel.chickenroost.api;

import net.neoforged.neoforge.items.IItemHandler;

import javax.annotation.Nullable;

public interface ICollectorTarget {
    @Nullable
    String getCustomName();

    void setCustomName(String name);

    boolean isAutoOutputEnabled();

    void setAutoOutputFromGui(boolean enabled);

    void setAutoOutputClient(boolean enabled);

    /*@Nullable
    IItemHandler getItemHandler();*/

    int getReadSlot();
}
