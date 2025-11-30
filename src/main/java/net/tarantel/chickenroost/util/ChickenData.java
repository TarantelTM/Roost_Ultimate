package net.tarantel.chickenroost.util;

public class ChickenData {
    private String ChickenName;
    private String MobOrMonster;
    private String id;
    private String itemtexture;
    private String mobtexture;
    private String dropitem;
    private int eggtime;
    private int tier;

    private boolean CanGetFireDamage;
    private boolean CanGetProjectileDamage;
    private boolean CanGetExplosionDamage;
    private boolean CanGetFallDamage;
    private boolean CanGetDrowningDamage;
    private boolean CanGetFreezingDamage;
    private boolean CanGetLightningDamage;
    private boolean CanGetWitherDamage;
    public Boolean getIsFire() {
        return CanGetFireDamage;
    }
    public Boolean getIsProjectile() {
        return CanGetProjectileDamage;
    }
    public Boolean getIsExplosion() {
        return CanGetExplosionDamage;
    }
    public Boolean getIsFall() {
        return CanGetFallDamage;
    }
    public Boolean getIsDrowning() {
        return CanGetDrowningDamage;
    }
    public Boolean getIsFreezing() {
        return CanGetFreezingDamage;
    }
    public Boolean getIsLightning() {
        return CanGetLightningDamage;
    }
    public Boolean getIsWither() {
        return CanGetWitherDamage;
    }

    public void setIS_FIRE(Boolean CanGetFireDamage) {
        this.CanGetFireDamage = CanGetFireDamage;
    }
    public void setIS_PROJECTILE(Boolean CanGetProjectileDamage) {
        this.CanGetProjectileDamage = CanGetProjectileDamage;
    }
    public void setIS_EXPLOSION(Boolean CanGetExplosionDamage) {
        this.CanGetExplosionDamage = CanGetExplosionDamage;
    }
    public void setIS_FALL(Boolean CanGetFallDamage) {
        this.CanGetFallDamage = CanGetFallDamage;
    }
    public void setIS_DROWNING(Boolean CanGetDrowningDamage) {
        this.CanGetDrowningDamage = CanGetDrowningDamage;
    }
    public void setIS_FREEZING(Boolean CanGetFreezingDamage) {
        this.CanGetFreezingDamage = CanGetFreezingDamage;
    }
    public void setIS_LIGHTNING(Boolean CanGetLightningDamage) {
        this.CanGetLightningDamage = CanGetLightningDamage;
    }
    public void setIS_WITHER(Boolean CanGetWitherDamage) {
        this.CanGetWitherDamage = CanGetWitherDamage;
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
    public ChickenData(String ChickenName, String MobOrMonster, String id, String itemtexture, String mobtexture, String dropitem, int eggtime, int tier, Boolean CanGetFireDamage, Boolean CanGetProjectileDamage, Boolean CanGetExplosionDamage, Boolean CanGetFallDamage, Boolean CanGetDrowningDamage, Boolean CanGetFreezingDamage, Boolean CanGetLightningDamage, Boolean CanGetWitherDamage ) {
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
    }
}