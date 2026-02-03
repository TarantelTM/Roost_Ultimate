package net.tarantel.chickenroost.api;

import javax.annotation.Nullable;
import net.neoforged.neoforge.items.IItemHandler;

public interface ICollectorTarget {
   @Nullable
   String getCustomName();

   void setCustomName(String var1);

   boolean isAutoOutputEnabled();

   void setAutoOutputFromGui(boolean var1);

   void setAutoOutputClient(boolean var1);

   @Nullable
   IItemHandler getItemHandler();

   int getReadSlot();
}
