package net.tarantel.chickenroost.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;


public class ModEntities {


    /////TIER 1
    public static EntityType<ChickenBaseEntity> C_COBBLE;
    public static EntityType<ChickenBaseEntity> C_OAKWOOD;
    public static EntityType<ChickenBaseEntity> C_ANDESITE;
    public static EntityType<ChickenBaseEntity> C_SAND;
    public static EntityType<ChickenBaseEntity> C_VANILLA;
    public static EntityType<ChickenBaseEntity> C_GRAVEL;
    public static EntityType<ChickenBaseEntity> C_GRANIT;
    public static EntityType<ChickenBaseEntity> C_HONEYCOMB;
    public static EntityType<ChickenBaseEntity> C_FEATHER;
    public static EntityType<ChickenBaseEntity> C_WOOL;
    public static EntityType<ChickenBaseEntity> C_STONE;
    public static EntityType<ChickenBaseEntity> C_DIORITE;

    /////TIER 2
    public static EntityType<ChickenBaseEntity> C_MELON;
    public static EntityType<ChickenBaseEntity> C_NETHERRACK;
    public static EntityType<ChickenBaseEntity> C_SNOW;
    public static EntityType<ChickenBaseEntity> C_GLASS;
    public static EntityType<ChickenBaseEntity> C_SUGAR;
    public static EntityType<ChickenBaseEntity> C_FLINT;
    public static EntityType<ChickenBaseEntity> C_APPLE;
    public static EntityType<ChickenBaseEntity> C_BONE;
    public static EntityType<ChickenBaseEntity> C_COAL;
    public static EntityType<ChickenBaseEntity> C_CARROT;
    public static EntityType<ChickenBaseEntity> C_INK;
    public static EntityType<ChickenBaseEntity> C_BEETROOT;
    public static EntityType<ChickenBaseEntity> C_SWEETBERRIES;
    public static EntityType<ChickenBaseEntity> C_GLOWBERRIES;

    /////TIER 3
    public static EntityType<ChickenBaseEntity> C_SOULSOIL;
    public static EntityType<ChickenBaseEntity> C_STRING;
    public static EntityType<ChickenBaseEntity> C_BASALT;
    public static EntityType<ChickenBaseEntity> C_COPPER;
    public static EntityType<ChickenBaseEntity> C_CLAY;
    public static EntityType<ChickenBaseEntity> C_SOULSAND;
    public static EntityType<ChickenBaseEntity> C_SPONGE;
    public static EntityType<ChickenBaseEntity> C_LEATHER;
    /////TIER 4
    public static EntityType<ChickenBaseEntity> C_NETHERWART;
    public static EntityType<ChickenBaseEntity> C_REDSTONE;
    public static EntityType<ChickenBaseEntity> C_LAPIS;
    public static EntityType<ChickenBaseEntity> C_OBSIDIAN;
    public static EntityType<ChickenBaseEntity> C_MAGMACREAM;
    public static EntityType<ChickenBaseEntity> C_IRON;
    public static EntityType<ChickenBaseEntity> C_ROTTEN;
    public static EntityType<ChickenBaseEntity> C_SLIME;
    /////TIER 5
    public static EntityType<ChickenBaseEntity> C_CHORUSFRUIT;
    public static EntityType<ChickenBaseEntity> C_GLOWSTONE;
    public static EntityType<ChickenBaseEntity> C_ENDSTONE;
    public static EntityType<ChickenBaseEntity> C_GOLD;
    public static EntityType<ChickenBaseEntity> C_BLAZEROD;
    public static EntityType<ChickenBaseEntity> C_NETHERQUARTZ;
    public static EntityType<ChickenBaseEntity> C_TNT;
    public static EntityType<ChickenBaseEntity> C_ENDERPEARL;

    /////TIER 6
    public static EntityType<ChickenBaseEntity> C_EMERALD;
    public static EntityType<ChickenBaseEntity> C_GHASTTEAR;

    /////TIER 7
    public static EntityType<ChickenBaseEntity> C_DIAMOND;
    public static EntityType<ChickenBaseEntity> C_NETHERITE;
    public static EntityType<ChickenBaseEntity> C_NETHERSTAR;



    public static void load() {
        /////TIER 1

        C_COBBLE = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_cobble"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_OAKWOOD = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_oakwood"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_ANDESITE = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_andesite"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_SAND = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_sand"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_VANILLA = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_vanilla"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_GRAVEL = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_gravel"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_GRANIT = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_granit"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_HONEYCOMB = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_honeycomb"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_FEATHER = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_feather"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_WOOL = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_wool"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_STONE = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_stone"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_DIORITE = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_diorite"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        /////TIER 2
        C_MELON = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_melon"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_NETHERRACK = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_netherrack"),
                FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_SNOW = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_snow"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_GLASS = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_glass"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_SUGAR = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_sugar"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_FLINT = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_flint"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_APPLE = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_apple"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_BONE = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_bone"),
                FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_COAL = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_coal"),
                FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_CARROT = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_carrot"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_INK = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_ink"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_BEETROOT = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_beetroot"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_SWEETBERRIES = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_sweetberries"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_GLOWBERRIES = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_glowberries"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        /////TIER 3
        C_SOULSOIL = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_soulsoil"),
                FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_STRING = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_string"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_BASALT = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_basalt"),
                FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_COPPER = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_copper"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_CLAY = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_clay"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_SOULSAND = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_soulsand"),
                FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_SPONGE = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_sponge"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_LEATHER = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_leather"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        /////TIER 4
        C_NETHERWART = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_netherwart"),
                FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_REDSTONE = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_redstone"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_LAPIS = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_lapis"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_OBSIDIAN = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_obsidian"),
                FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_MAGMACREAM = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_magmacream"),
                FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_IRON = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_iron"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_ROTTEN = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_rotten"),
                FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_SLIME = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_slime"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        /////TIER 5
        C_CHORUSFRUIT = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_chorusfruit"),
                FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_GLOWSTONE = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_glowstone"),
                FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_ENDSTONE = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_endstone"),
                FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_GOLD = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_gold"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_BLAZEROD = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_blazerod"),
                FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_NETHERQUARTZ = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_netherquartz"),
                FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_TNT = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_tnt"),
                FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_ENDERPEARL = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_enderpearl"),
                FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        /////TIER 6
        C_EMERALD = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_emerald"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_GHASTTEAR = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_ghasttear"),
                FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        /////TIER 7
        C_DIAMOND = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_diamond"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_NETHERITE = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_netherite"),
                FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        C_NETHERSTAR = Registry.register(Registries.ENTITY_TYPE, new Identifier("chicken_roost:c_netherstar"),
                FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ChickenBaseEntity::new).dimensions(new EntityDimensions(0.4f, 0.7f, true)).trackRangeBlocks(64).forceTrackedVelocityUpdates(true).trackedUpdateRate(3).build());
        ChickenBaseEntity.init();
        /////TIER 1
        FabricDefaultAttributeRegistry.register(C_COBBLE, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_OAKWOOD, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_ANDESITE, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_SAND, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_VANILLA, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_GRAVEL, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_GRANIT, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_HONEYCOMB, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_FEATHER, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_WOOL, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_STONE, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_DIORITE, ChickenBaseEntity.createAttributes());
        /////TIER 2
        FabricDefaultAttributeRegistry.register(C_MELON, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_NETHERRACK, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_SNOW, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_GLASS, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_SUGAR, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_FLINT, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_APPLE, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_BONE, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_COAL, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_CARROT, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_INK, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_BEETROOT, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_SWEETBERRIES, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_GLOWBERRIES, ChickenBaseEntity.createAttributes());
        /////TIER 3
        FabricDefaultAttributeRegistry.register(C_SOULSOIL, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_STRING, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_BASALT, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_COPPER, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_CLAY, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_SOULSAND, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_SPONGE, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_LEATHER, ChickenBaseEntity.createAttributes());
        /////TIER 4
        FabricDefaultAttributeRegistry.register(C_NETHERWART, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_REDSTONE, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_LAPIS, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_OBSIDIAN, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_MAGMACREAM, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_IRON, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_ROTTEN, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_SLIME, ChickenBaseEntity.createAttributes());
        /////TIER 5
        FabricDefaultAttributeRegistry.register(C_CHORUSFRUIT, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_GLOWSTONE, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_ENDSTONE, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_GOLD, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_BLAZEROD, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_NETHERQUARTZ, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_TNT, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_ENDERPEARL, ChickenBaseEntity.createAttributes());
        /////TIER 6
        FabricDefaultAttributeRegistry.register(C_EMERALD, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_GHASTTEAR, ChickenBaseEntity.createAttributes());
        /////TIER 7
        FabricDefaultAttributeRegistry.register(C_DIAMOND, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_NETHERITE, ChickenBaseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(C_NETHERSTAR, ChickenBaseEntity.createAttributes());


    }

    private static <T extends Entity> EntityType<T> createArrowEntityType(EntityType.EntityFactory<T> factory) {
        return FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, factory).dimensions(EntityDimensions.fixed(0.5f, 0.5f)).trackRangeBlocks(1).trackedUpdateRate(64).build();
    }

}