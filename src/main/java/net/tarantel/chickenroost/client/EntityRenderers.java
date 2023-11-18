package net.tarantel.chickenroost.client;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.tarantel.chickenroost.entity.ModEntities;
import net.tarantel.chickenroost.entity.*;

public class EntityRenderers {
    public static void load() {
        EntityRendererRegistry.register(ModEntities.C_COBBLE, Model_c_cobble::new);
        EntityRendererRegistry.register(ModEntities.C_OAKWOOD, Model_c_oakwood::new);
        EntityRendererRegistry.register(ModEntities.C_ANDESITE, Model_c_andesite::new);
        EntityRendererRegistry.register(ModEntities.C_SAND, Model_c_sand::new);
        EntityRendererRegistry.register(ModEntities.C_VANILLA, Model_c_vanilla::new);
        EntityRendererRegistry.register(ModEntities.C_GRAVEL, Model_c_gravel::new);
        EntityRendererRegistry.register(ModEntities.C_GRANIT, Model_c_granit::new);
        EntityRendererRegistry.register(ModEntities.C_HONEYCOMB, Model_c_honeycomb::new);
        EntityRendererRegistry.register(ModEntities.C_FEATHER, Model_c_feather::new);
        EntityRendererRegistry.register(ModEntities.C_WOOL, Model_c_wool::new);
        EntityRendererRegistry.register(ModEntities.C_STONE, Model_c_stone::new);
        EntityRendererRegistry.register(ModEntities.C_DIORITE, Model_c_diorite::new);

        EntityRendererRegistry.register(ModEntities.C_MELON, Model_c_melon::new);
        EntityRendererRegistry.register(ModEntities.C_NETHERRACK, Model_c_netherrack::new);
        EntityRendererRegistry.register(ModEntities.C_SNOW, Model_c_snow::new);
        EntityRendererRegistry.register(ModEntities.C_GLASS, Model_c_glass::new);
        EntityRendererRegistry.register(ModEntities.C_SUGAR, Model_c_sugar::new);
        EntityRendererRegistry.register(ModEntities.C_FLINT, Model_c_flint::new);
        EntityRendererRegistry.register(ModEntities.C_APPLE, Model_c_apple::new);
        EntityRendererRegistry.register(ModEntities.C_BONE, Model_c_bone::new);
        EntityRendererRegistry.register(ModEntities.C_COAL, Model_c_coal::new);
        EntityRendererRegistry.register(ModEntities.C_CARROT, Model_c_carrot::new);
        EntityRendererRegistry.register(ModEntities.C_INK, Model_c_ink::new);
        EntityRendererRegistry.register(ModEntities.C_BEETROOT, Model_c_beetroot::new);
        EntityRendererRegistry.register(ModEntities.C_SWEETBERRIES, Model_c_sweetberries::new);
        EntityRendererRegistry.register(ModEntities.C_GLOWBERRIES, Model_c_glowberries::new);

        EntityRendererRegistry.register(ModEntities.C_SOULSOIL, Model_c_soulsoil::new);
        EntityRendererRegistry.register(ModEntities.C_STRING, Model_c_string::new);
        EntityRendererRegistry.register(ModEntities.C_BASALT, Model_c_basalt::new);
        EntityRendererRegistry.register(ModEntities.C_COPPER, Model_c_copper::new);
        EntityRendererRegistry.register(ModEntities.C_CLAY, Model_c_clay::new);
        EntityRendererRegistry.register(ModEntities.C_SOULSAND, Model_c_soulsand::new);
        EntityRendererRegistry.register(ModEntities.C_SPONGE, Model_c_sponge::new);
        EntityRendererRegistry.register(ModEntities.C_LEATHER, Model_c_leather::new);

        EntityRendererRegistry.register(ModEntities.C_NETHERWART, Model_c_netherwart::new);
        EntityRendererRegistry.register(ModEntities.C_REDSTONE, Model_c_redstone::new);
        EntityRendererRegistry.register(ModEntities.C_LAPIS, Model_c_lapis::new);
        EntityRendererRegistry.register(ModEntities.C_OBSIDIAN, Model_c_obsidian::new);
        EntityRendererRegistry.register(ModEntities.C_MAGMACREAM, Model_c_magmacream::new);
        EntityRendererRegistry.register(ModEntities.C_IRON, Model_c_iron::new);
        EntityRendererRegistry.register(ModEntities.C_ROTTEN, Model_c_rotten::new);
        EntityRendererRegistry.register(ModEntities.C_SLIME, Model_c_slime::new);

        EntityRendererRegistry.register(ModEntities.C_CHORUSFRUIT, Model_c_chorusfruit::new);
        EntityRendererRegistry.register(ModEntities.C_GLOWSTONE, Model_c_glowstone::new);
        EntityRendererRegistry.register(ModEntities.C_ENDSTONE, Model_c_endstone::new);
        EntityRendererRegistry.register(ModEntities.C_GOLD, Model_c_gold::new);
        EntityRendererRegistry.register(ModEntities.C_BLAZEROD, Model_c_blazerod::new);
        EntityRendererRegistry.register(ModEntities.C_NETHERQUARTZ, Model_c_netherquartz::new);
        EntityRendererRegistry.register(ModEntities.C_TNT, Model_c_tnt::new);
        EntityRendererRegistry.register(ModEntities.C_ENDERPEARL, Model_c_enderpearl::new);

        EntityRendererRegistry.register(ModEntities.C_EMERALD, Model_c_emerald::new);
        EntityRendererRegistry.register(ModEntities.C_GHASTTEAR, Model_c_ghasttear::new);

        EntityRendererRegistry.register(ModEntities.C_DIAMOND, Model_c_diamond::new);
        EntityRendererRegistry.register(ModEntities.C_NETHERITE, Model_c_netherite::new);
        EntityRendererRegistry.register(ModEntities.C_NETHERSTAR, Model_c_netherstar::new);

    }
}
