package net.tarantel.chickenroost.util;

import com.google.gson.annotations.SerializedName;
import net.tarantel.chickenroost.datagen.ChickenRecipeContainer;

public class ChickenData {

    // ======================
    // Basisdaten
    // ======================
    public String ChickenName;
    public String MobOrMonster;
    public String id;
    public String itemtexture;
    public String mobtexture;
    public String dropitem;
    public int eggtime;
    public int tier;

    // ======================
    // Rezepte (optional)
    // ======================
    public ChickenRecipeContainer recipes;

    // ======================
    // 🔥 DAMAGE FLAGS – FLACH (JSON)
    // ======================
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

    // ======================
    // 🔒 INTERN (NICHT JSON)
    // ======================
    public transient DamageFlags damageFlags;

    // ======================
    // DamageFlags Klasse
    // ======================
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

    // =========================================================
    // 🔥 KONSTRUKTOR OHNE REZEPTE
    // =========================================================
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

        // 🔹 flache JSON-Felder
        this.CanGetFireDamage = CanGetFireDamage;
        this.CanGetProjectileDamage = CanGetProjectileDamage;
        this.CanGetExplosionDamage = CanGetExplosionDamage;
        this.CanGetFallDamage = CanGetFallDamage;
        this.CanGetDrowningDamage = CanGetDrowningDamage;
        this.CanGetFreezingDamage = CanGetFreezingDamage;
        this.CanGetLightningDamage = CanGetLightningDamage;
        this.CanGetWitherDamage = CanGetWitherDamage;

        // 🔹 interne Struktur synchronisieren
        rebuildDamageFlags();

        this.recipes = null;
    }

    // =========================================================
    // 🔥 KONSTRUKTOR MIT REZEPTE
    // =========================================================
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

    // =========================================================
    // 🔁 AUTOMATISCHE SYNC NACH JSON-DESERIALISIERUNG
    // =========================================================
    private Object readResolve() {
        rebuildDamageFlags();
        return this;
    }

    private void rebuildDamageFlags() {
        this.damageFlags = new DamageFlags();
        this.damageFlags.CanGetFireDamage = CanGetFireDamage;
        this.damageFlags.CanGetProjectileDamage = CanGetProjectileDamage;
        this.damageFlags.CanGetExplosionDamage = CanGetExplosionDamage;
        this.damageFlags.CanGetFallDamage = CanGetFallDamage;
        this.damageFlags.CanGetDrowningDamage = CanGetDrowningDamage;
        this.damageFlags.CanGetFreezingDamage = CanGetFreezingDamage;
        this.damageFlags.CanGetLightningDamage = CanGetLightningDamage;
        this.damageFlags.CanGetWitherDamage = CanGetWitherDamage;
    }

    // =========================================================
    // ✅ DEINE GETTER (ALLE DRIN, UNVERÄNDERT)
    // =========================================================


    public ChickenRecipeContainer getRecipes() {
        return recipes;
    }

    public String getChickenName() {
        return ChickenName;
    }
    public String getMobOrMonster() {
        return MobOrMonster;
    }
    public String getId() {
        return id;
    }
    public String getItemtexture() {
        return itemtexture;
    }
    public String getMobtexture() {
        return mobtexture;
    }
    public String getDropitem() {
        return dropitem;
    }

    public int getEggtime() {
        return eggtime;
    }
    public int getTier() {
        return tier;
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


}
