package net.tarantel.chickenroost.util;

import com.google.gson.annotations.SerializedName;
import net.tarantel.chickenroost.datagen.ChickenRecipeContainer;

public class ChickenData {
   public String ChickenName;
   public String MobOrMonster;
   public String id;
   public String itemtexture;
   public String mobtexture;
   public String dropitem;
   public int eggtime;
   public int tier;
   public ChickenRecipeContainer recipes;
   @SerializedName("CanGetFireDamage")
   public boolean CanGetFireDamage;
   @SerializedName("CanGetProjectileDamage")
   public boolean CanGetProjectileDamage;
   @SerializedName("CanGetExplosionDamage")
   public boolean CanGetExplosionDamage;
   @SerializedName("CanGetFallDamage")
   public boolean CanGetFallDamage;
   @SerializedName("CanGetDrowningDamage")
   public boolean CanGetDrowningDamage;
   @SerializedName("CanGetFreezingDamage")
   public boolean CanGetFreezingDamage;
   @SerializedName("CanGetLightningDamage")
   public boolean CanGetLightningDamage;
   @SerializedName("CanGetWitherDamage")
   public boolean CanGetWitherDamage;
   public transient ChickenData.DamageFlags damageFlags;

   public ChickenData(
      String ChickenName,
      String MobOrMonster,
      String id,
      String itemtexture,
      String mobtexture,
      String dropitem,
      int eggtime,
      int tier,
      boolean CanGetFireDamage,
      boolean CanGetProjectileDamage,
      boolean CanGetExplosionDamage,
      boolean CanGetFallDamage,
      boolean CanGetDrowningDamage,
      boolean CanGetFreezingDamage,
      boolean CanGetLightningDamage,
      boolean CanGetWitherDamage
   ) {
      this.ChickenName = ChickenName;
      this.MobOrMonster = MobOrMonster;
      this.id = id;
      this.itemtexture = itemtexture;
      this.mobtexture = mobtexture;
      this.dropitem = dropitem;
      this.eggtime = eggtime;
      this.tier = tier;
      this.CanGetFireDamage = CanGetFireDamage;
      this.CanGetProjectileDamage = CanGetProjectileDamage;
      this.CanGetExplosionDamage = CanGetExplosionDamage;
      this.CanGetFallDamage = CanGetFallDamage;
      this.CanGetDrowningDamage = CanGetDrowningDamage;
      this.CanGetFreezingDamage = CanGetFreezingDamage;
      this.CanGetLightningDamage = CanGetLightningDamage;
      this.CanGetWitherDamage = CanGetWitherDamage;
      this.rebuildDamageFlags();
      this.recipes = null;
   }

   public ChickenData(
      String ChickenName,
      String MobOrMonster,
      String id,
      String itemtexture,
      String mobtexture,
      String dropitem,
      int eggtime,
      int tier,
      boolean CanGetFireDamage,
      boolean CanGetProjectileDamage,
      boolean CanGetExplosionDamage,
      boolean CanGetFallDamage,
      boolean CanGetDrowningDamage,
      boolean CanGetFreezingDamage,
      boolean CanGetLightningDamage,
      boolean CanGetWitherDamage,
      ChickenRecipeContainer recipes
   ) {
      this(
         ChickenName,
         MobOrMonster,
         id,
         itemtexture,
         mobtexture,
         dropitem,
         eggtime,
         tier,
         CanGetFireDamage,
         CanGetProjectileDamage,
         CanGetExplosionDamage,
         CanGetFallDamage,
         CanGetDrowningDamage,
         CanGetFreezingDamage,
         CanGetLightningDamage,
         CanGetWitherDamage
      );
      this.recipes = recipes;
   }

   private Object readResolve() {
      this.rebuildDamageFlags();
      return this;
   }

   private void rebuildDamageFlags() {
      this.damageFlags = new ChickenData.DamageFlags();
      this.damageFlags.CanGetFireDamage = this.CanGetFireDamage;
      this.damageFlags.CanGetProjectileDamage = this.CanGetProjectileDamage;
      this.damageFlags.CanGetExplosionDamage = this.CanGetExplosionDamage;
      this.damageFlags.CanGetFallDamage = this.CanGetFallDamage;
      this.damageFlags.CanGetDrowningDamage = this.CanGetDrowningDamage;
      this.damageFlags.CanGetFreezingDamage = this.CanGetFreezingDamage;
      this.damageFlags.CanGetLightningDamage = this.CanGetLightningDamage;
      this.damageFlags.CanGetWitherDamage = this.CanGetWitherDamage;
   }

   public ChickenRecipeContainer getRecipes() {
      return this.recipes;
   }

   public String getChickenName() {
      return this.ChickenName;
   }

   public String getMobOrMonster() {
      return this.MobOrMonster;
   }

   public String getId() {
      return this.id;
   }

   public String getItemtexture() {
      return this.itemtexture;
   }

   public String getMobtexture() {
      return this.mobtexture;
   }

   public String getDropitem() {
      return this.dropitem;
   }

   public int getEggtime() {
      return this.eggtime;
   }

   public int getTier() {
      return this.tier;
   }

   public void setChickenName(String ChickenName) {
      this.ChickenName = ChickenName;
   }

   public void setMobOrMonster(String MobOrMonster) {
      this.MobOrMonster = MobOrMonster;
   }

   public void setId(String id) {
      this.id = id;
   }

   public void setItemtexture(String itemtexture) {
      this.itemtexture = itemtexture;
   }

   public void setMobtexture(String mobtexture) {
      this.mobtexture = mobtexture;
   }

   public void setDropitem(String dropitem) {
      this.dropitem = dropitem;
   }

   public void setEggtime(int eggtime) {
      this.eggtime = eggtime;
   }

   public void setTier(int tier) {
      this.tier = tier;
   }

   public static class DamageFlags {
      public boolean CanGetFireDamage;
      public boolean CanGetProjectileDamage;
      public boolean CanGetExplosionDamage;
      public boolean CanGetFallDamage;
      public boolean CanGetDrowningDamage;
      public boolean CanGetFreezingDamage;
      public boolean CanGetLightningDamage;
      public boolean CanGetWitherDamage;
   }
}
