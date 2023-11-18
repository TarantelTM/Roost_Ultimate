package net.tarantel.chickenroost.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.registries.ForgeRegistries;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.handler.ChickenBookHandler;
import net.tarantel.chickenroost.item.ModItems;
import net.tarantel.chickenroost.util.Config;

import java.util.HashMap;
import java.util.Map;

public class Chicken_Book_Screen extends AbstractContainerScreen<ChickenBookHandler> {
    private final static HashMap<String, Object> guistate = ChickenBookHandler.guistate;
    private final Level world;
    private final int x, y, z;
    private final Player entity;
    private int page = 0;
    public IItemHandler internal;
    public final Map<Integer, Slot> customSlots = new HashMap<>();

    private final ChickenBookHandler iHandler;
    private Slot slotty;
    Button button_empty;
    Button button_empty1;

    public Chicken_Book_Screen(ChickenBookHandler container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.world = container.level;
        this.x = container.x;
        this.y = container.y;
        this.z = container.z;
        this.entity = container.entity;
        this.imageWidth = 433;
        this.imageHeight = 244;
        this.iHandler = container;



        //this.page = container.page;
    }

    private final ResourceLocation texture0 = new ResourceLocation("chicken_roost:textures/screens/00.png");
    private final ResourceLocation emptypage = new ResourceLocation("chicken_roost:textures/screens/emptypage.png");
    private final ResourceLocation texture00 = new ResourceLocation("chicken_roost:textures/screens/000.png");
    private final ResourceLocation texture1 = new ResourceLocation("chicken_roost:textures/screens/1.png");
    private final ResourceLocation texture2 = new ResourceLocation("chicken_roost:textures/screens/2.png");
    private final ResourceLocation texture3 = new ResourceLocation("chicken_roost:textures/screens/2.png");
    private final ResourceLocation texture4 = new ResourceLocation("chicken_roost:textures/screens/3.png");

    @Override
    public void render(GuiGraphics ms, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(ms);
        super.render(ms, mouseX, mouseY, partialTicks);
        this.renderTooltip(ms, mouseX, mouseY);

    }

    @Override
    protected void renderBg(GuiGraphics ms, float partialTicks, int gx, int gy) {
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        if(this.page == 0) {
            RenderSystem.setShaderTexture(0, texture0);
            ms.blit(texture0, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        }

        if(this.page == 1) {
            RenderSystem.setShaderTexture(0, texture00);
            ms.blit(texture00, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        }

        if(this.page == 2) {
            RenderSystem.setShaderTexture(0, texture1);
            ms.blit(texture1, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        }
        if(this.page == 3) {
            RenderSystem.setShaderTexture(0, texture2);
            ms.blit(texture2, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        }
        if(this.page == 4) {
            RenderSystem.setShaderTexture(0, texture3);
            ms.blit(texture3, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        }
        if(this.page == 5) {
            RenderSystem.setShaderTexture(0, texture4);
            ms.blit(texture4, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        }
        if(this.page == 6) {
            RenderSystem.setShaderTexture(0, texture4);
            ms.blit(texture4, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        }
        if(this.page >= 7) {
            RenderSystem.setShaderTexture(0, emptypage);
            ms.blit(emptypage, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

        }
        RenderSystem.disableBlend();


    }

    @Override
    public boolean keyPressed(int key, int b, int c) {
        if (key == 256) {
            this.minecraft.player.closeContainer();
            return true;
        }
        return super.keyPressed(key, b, c);
    }




    private ItemStack doplanks;

    private int Ticker = 0;
    private int popo = 404;
    private int currentpages = 7;
    @Override
    public void containerTick() {
        super.containerTick();
        init();
        Ticker ++;
        if(Ticker == 20){
            doplanks =  new ItemStack(ForgeRegistries.ITEMS.tags().getTag(ItemTags.create(new ResourceLocation("minecraft:planks"))).getRandomElement(RandomSource.create()).orElseGet(() -> Items.AIR));

            Ticker = 0;
        }

        if(this.page > currentpages){
            this.page = currentpages;
        }
        if(this.page < 0){
            this.page = 0;
        }
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        if(this.page == 0) {
            int seed2 = 8;
            int seed3 = 171;

            int seedhigh1 = 64;
            int texthigh1 = 130;
            int texthigh2 = 260;
            int seedhigh2 = 127;
            guiGraphics.pose().pushPose();
            guiGraphics.pose().scale(0.5F, 0.5F, 0.5F);
            guiGraphics.drawString(this.font, Component.literal("Seed Tier 1"), 65 + 70, 20, 6749952, false);
            guiGraphics.drawString(this.font, Component.literal("Seed Tier 2"), 215 + seed2 + 230, 20, 6749952, false);
            guiGraphics.drawString(this.font, Component.literal("Seed Tier 3"), 215 + seed3 + 390, 20, 6749952, false);
            guiGraphics.drawString(this.font, Component.literal("Seed Tier 4"), 65 + 70, 20 + texthigh1, 6749952, false);
            guiGraphics.drawString(this.font, Component.literal("Seed Tier 5"), 215 + seed2 + 230, 20 + texthigh1, 6749952, false);
            guiGraphics.drawString(this.font, Component.literal("Seed Tier 6"), 215 + seed3 + 390, 20 + texthigh1, 6749952, false);
            guiGraphics.drawString(this.font, Component.literal("Seed Tier 7"), 65 + 70, 20 + texthigh2, 6749952, false);
            guiGraphics.drawString(this.font, Component.literal("Seed Tier 8"), 215 + seed2 + 230, 20 + texthigh2, 6749952, false);
            guiGraphics.drawString(this.font, Component.literal("Seed Tier 9"), 215 + seed3 + 390, 20 + texthigh2, 6749952, false);
            guiGraphics.pose().popPose();
            guiGraphics.pose().pushPose();
            guiGraphics.renderTooltip(this.font, Component.literal("Craftingtable Seed Recipes"), 130, 222);
            //region tier1
            guiGraphics.renderItem(ModItems.CHICKEN_ESSENCE_TIER_1.get().getDefaultInstance(), 8, 8);
            guiGraphics.renderItem(Items.WHEAT_SEEDS.getDefaultInstance(), 26, 8);
            guiGraphics.renderItem(Items.WHEAT_SEEDS.getDefaultInstance(), 26, 26);
            guiGraphics.renderItem(Items.WHEAT_SEEDS.getDefaultInstance(), 26, 43);
            guiGraphics.renderItem(Items.WHEAT_SEEDS.getDefaultInstance(), 43, 8);
            guiGraphics.renderItem(Items.WHEAT_SEEDS.getDefaultInstance(), 43, 26);
            guiGraphics.renderItem(Items.WHEAT_SEEDS.getDefaultInstance(), 43, 43);
            guiGraphics.renderItem(Items.WHEAT_SEEDS.getDefaultInstance(), 8, 26);
            guiGraphics.renderItem(Items.WHEAT_SEEDS.getDefaultInstance(), 8, 43);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_1.get().getDefaultInstance(), 88, 26);
            //endregion
            //region tier2
            guiGraphics.renderItem(ModItems.CHICKEN_ESSENCE_TIER_2.get().getDefaultInstance(), 158 + seed2, 8);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_1.get().getDefaultInstance(), 176 + seed2, 8);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_1.get().getDefaultInstance(), 176 + seed2, 26);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_1.get().getDefaultInstance(), 176 + seed2, 43);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_1.get().getDefaultInstance(), 193 + seed2, 8);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_1.get().getDefaultInstance(), 193 + seed2, 26);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_1.get().getDefaultInstance(), 193 + seed2, 43);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_1.get().getDefaultInstance(), 158 + seed2, 26);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_1.get().getDefaultInstance(), 158 + seed2, 43);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_2.get().getDefaultInstance(), 238 + seed2, 26);
            //endregion

            //region tier3
            guiGraphics.renderItem(ModItems.CHICKEN_ESSENCE_TIER_3.get().getDefaultInstance(), 158 + seed3, 8);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_2.get().getDefaultInstance(), 176 + seed3, 8);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_2.get().getDefaultInstance(), 176 + seed3, 26);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_2.get().getDefaultInstance(), 176 + seed3, 43);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_2.get().getDefaultInstance(), 193 + seed3, 8);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_2.get().getDefaultInstance(), 193 + seed3, 26);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_2.get().getDefaultInstance(), 193 + seed3, 43);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_2.get().getDefaultInstance(), 158 + seed3, 26);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_2.get().getDefaultInstance(), 158 + seed3, 43);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_3.get().getDefaultInstance(), 238 + seed3, 26);
            //endregion

            //region tier4
            guiGraphics.renderItem(ModItems.CHICKEN_ESSENCE_TIER_4.get().getDefaultInstance(), 8, 8 + seedhigh1);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_3.get().getDefaultInstance(), 26, 8 + seedhigh1);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_3.get().getDefaultInstance(), 26, 26 + seedhigh1);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_3.get().getDefaultInstance(), 26, 43 + seedhigh1);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_3.get().getDefaultInstance(), 43, 8 + seedhigh1);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_3.get().getDefaultInstance(), 43, 26 + seedhigh1);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_3.get().getDefaultInstance(), 43, 43 + seedhigh1);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_3.get().getDefaultInstance(), 8, 26 + seedhigh1);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_3.get().getDefaultInstance(), 8, 43 + seedhigh1);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_4.get().getDefaultInstance(), 88, 26 + seedhigh1);
            //endregion

            //region tier5
            guiGraphics.renderItem(ModItems.CHICKEN_ESSENCE_TIER_5.get().getDefaultInstance(), 158 + seed2, 8 + seedhigh1);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_4.get().getDefaultInstance(), 176 + seed2, 8 + seedhigh1);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_4.get().getDefaultInstance(), 176 + seed2, 26 + seedhigh1);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_4.get().getDefaultInstance(), 176 + seed2, 43 + seedhigh1);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_4.get().getDefaultInstance(), 193 + seed2, 8 + seedhigh1);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_4.get().getDefaultInstance(), 193 + seed2, 26 + seedhigh1);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_4.get().getDefaultInstance(), 193 + seed2, 43 + seedhigh1);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_4.get().getDefaultInstance(), 158 + seed2, 26 + seedhigh1);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_4.get().getDefaultInstance(), 158 + seed2, 43 + seedhigh1);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_5.get().getDefaultInstance(), 238 + seed2, 26 + seedhigh1);
            //endregion

            //region tier6
            guiGraphics.renderItem(ModItems.CHICKEN_ESSENCE_TIER_6.get().getDefaultInstance(), 158 + seed3, 8 + seedhigh1);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_5.get().getDefaultInstance(), 176 + seed3, 8 + seedhigh1);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_5.get().getDefaultInstance(), 176 + seed3, 26 + seedhigh1);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_5.get().getDefaultInstance(), 176 + seed3, 43 + seedhigh1);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_5.get().getDefaultInstance(), 193 + seed3, 8 + seedhigh1);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_5.get().getDefaultInstance(), 193 + seed3, 26 + seedhigh1);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_5.get().getDefaultInstance(), 193 + seed3, 43 + seedhigh1);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_5.get().getDefaultInstance(), 158 + seed3, 26 + seedhigh1);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_5.get().getDefaultInstance(), 158 + seed3, 43 + seedhigh1);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_6.get().getDefaultInstance(), 238 + seed3, 26 + seedhigh1);
            //endregion

            //region tier7
            guiGraphics.renderItem(ModItems.CHICKEN_ESSENCE_TIER_7.get().getDefaultInstance(), 8, 8 + seedhigh2);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_6.get().getDefaultInstance(), 26, 8 + seedhigh2);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_6.get().getDefaultInstance(), 26, 26 + seedhigh2);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_6.get().getDefaultInstance(), 26, 43 + seedhigh2);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_6.get().getDefaultInstance(), 43, 8 + seedhigh2);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_6.get().getDefaultInstance(), 43, 26 + seedhigh2);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_6.get().getDefaultInstance(), 43, 43 + seedhigh2);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_6.get().getDefaultInstance(), 8, 26 + seedhigh2);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_6.get().getDefaultInstance(), 8, 43 + seedhigh2);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_7.get().getDefaultInstance(), 88, 26 + seedhigh2);
            //endregion

            //region tier8
            guiGraphics.renderItem(ModItems.CHICKEN_ESSENCE_TIER_8.get().getDefaultInstance(), 158 + seed2, 8 + seedhigh2);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_7.get().getDefaultInstance(), 176 + seed2, 8 + seedhigh2);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_7.get().getDefaultInstance(), 176 + seed2, 26 + seedhigh2);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_7.get().getDefaultInstance(), 176 + seed2, 43 + seedhigh2);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_7.get().getDefaultInstance(), 193 + seed2, 8 + seedhigh2);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_7.get().getDefaultInstance(), 193 + seed2, 26 + seedhigh2);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_7.get().getDefaultInstance(), 193 + seed2, 43 + seedhigh2);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_7.get().getDefaultInstance(), 158 + seed2, 26 + seedhigh2);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_7.get().getDefaultInstance(), 158 + seed2, 43 + seedhigh2);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_8.get().getDefaultInstance(), 238 + seed2, 26 + seedhigh2);
            //endregion

            //region tier9
            guiGraphics.renderItem(ModItems.CHICKEN_ESSENCE_TIER_9.get().getDefaultInstance(), 158 + seed3, 8 + seedhigh2);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_8.get().getDefaultInstance(), 176 + seed3, 8 + seedhigh2);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_8.get().getDefaultInstance(), 176 + seed3, 26 + seedhigh2);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_8.get().getDefaultInstance(), 176 + seed3, 43 + seedhigh2);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_8.get().getDefaultInstance(), 193 + seed3, 8 + seedhigh2);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_8.get().getDefaultInstance(), 193 + seed3, 26 + seedhigh2);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_8.get().getDefaultInstance(), 193 + seed3, 43 + seedhigh2);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_8.get().getDefaultInstance(), 158 + seed3, 26 + seedhigh2);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_8.get().getDefaultInstance(), 158 + seed3, 43 + seedhigh2);
            guiGraphics.renderItem(ModItems.CHICKEN_FOOD_TIER_9.get().getDefaultInstance(), 238 + seed3, 26 + seedhigh2);
            //endregion
            guiGraphics.pose().popPose();


        }

        if(this.page == 1) {
            int seed2 = 8;
            int seed3 = 171;

            int seedhigh1 = 64;
            int texthigh1 = 130;
            int texthigh2 = 260;
            int seedhigh2 = 127;
            guiGraphics.pose().pushPose();
            guiGraphics.pose().scale(0.5F, 0.5F, 0.5F);
            guiGraphics.drawString(this.font, Component.literal("Chicken Stick"), 65 + 70, 20, 6749952, false);
            guiGraphics.drawString(this.font, Component.literal("Breeder"), 215 + seed2 + 230, 20, 6749952, false);
            guiGraphics.drawString(this.font, Component.literal("Roost"), 215 + seed3 + 390, 20, 6749952, false);
            guiGraphics.drawString(this.font, Component.literal("Soul Breeder"), 65 + 70, 20 + texthigh1, 6749952, false);
            guiGraphics.drawString(this.font, Component.literal("Soul Extractor"), 215 + seed2 + 230, 20 + texthigh1, 6749952, false);
            guiGraphics.drawString(this.font, Component.literal("Trainer"), 215 + seed3 + 390, 20 + texthigh1, 6749952, false);
            guiGraphics.pose().popPose();
            guiGraphics.pose().pushPose();
            guiGraphics.renderTooltip(this.font, Component.literal("Craftingtable Block and Item Recipes"), 115, 222);
            //region tier1
            guiGraphics.renderItem(Items.EGG.getDefaultInstance(), 26, 8);
            guiGraphics.renderItem(Items.STICK.getDefaultInstance(), 26, 26);
            guiGraphics.renderItem(Items.FEATHER.getDefaultInstance(), 26, 43);
            guiGraphics.renderItem(ModItems.CHICKEN_STICK.get().getDefaultInstance(), 88, 26);
            //endregion


            //region tier2
            guiGraphics.renderItem(ModBlocks.ROOST.get().asItem().getDefaultInstance(), 158 + seed2, 8);
            guiGraphics.renderItem(ModItems.CHICKENCHICKEN.get().getDefaultInstance(), 176 + seed2, 8);
            guiGraphics.renderItem(ModItems.CHICKEN_STICK.get().getDefaultInstance(), 158 + seed2, 26);
            guiGraphics.renderItem(ModBlocks.BREEDER.get().asItem().getDefaultInstance(), 238 + seed2, 26);
            //endregion


            //region tier3
            guiGraphics.renderItem(doplanks, 158 + seed3, 8);
            guiGraphics.renderItem(doplanks, 176 + seed3, 8);
            guiGraphics.renderItem(doplanks, 194 + seed3, 8);
            guiGraphics.renderItem(doplanks, 158 + seed3, 26);
            guiGraphics.renderItem(doplanks, 194 + seed3, 26);
            guiGraphics.renderItem(Items.COMPOSTER.getDefaultInstance(), 158 + seed3, 44);
            guiGraphics.renderItem(Items.COMPOSTER.getDefaultInstance(), 176 + seed3, 44);
            guiGraphics.renderItem(Items.COMPOSTER.getDefaultInstance(), 194 + seed3, 44);
            guiGraphics.renderItem(ModBlocks.ROOST.get().asItem().getDefaultInstance(), 238 + seed3, 26);
            //endregion


            //region tier4
            guiGraphics.renderItem(ModItems.CHICKEN_ESSENCE_TIER_1.get().getDefaultInstance(), 8, 8 + seedhigh1);
            guiGraphics.renderItem(ModItems.CHICKEN_ESSENCE_TIER_2.get().getDefaultInstance(), 26, 8 + seedhigh1);
            guiGraphics.renderItem(ModItems.CHICKEN_ESSENCE_TIER_3.get().getDefaultInstance(), 44, 8 + seedhigh1);
            guiGraphics.renderItem(ModBlocks.BREEDER.get().asItem().getDefaultInstance(), 9, 26 + seedhigh1);
            guiGraphics.renderItem(ModBlocks.SOUL_EXTRACTOR.get().asItem().getDefaultInstance(), 26, 26 + seedhigh1);
            guiGraphics.renderItem(ModBlocks.ROOST.get().asItem().getDefaultInstance(), 44, 26 + seedhigh1);
            guiGraphics.renderItem(ModItems.CHICKEN_ESSENCE_TIER_4.get().getDefaultInstance(), 8, 44 + seedhigh1);
            guiGraphics.renderItem(ModItems.CHICKEN_ESSENCE_TIER_5.get().getDefaultInstance(), 26, 44 + seedhigh1);
            guiGraphics.renderItem(ModItems.CHICKEN_ESSENCE_TIER_6.get().getDefaultInstance(), 44, 44 + seedhigh1);
            guiGraphics.renderItem(ModItems.SOUL_BREEDER.get().getDefaultInstance(), 88, 26 + seedhigh1);
            //endregion


            //region tier5
            guiGraphics.renderItem(Items.CRAFTING_TABLE.getDefaultInstance(), 158 + seed2, 8 + seedhigh1);
            guiGraphics.renderItem(Items.GLASS_BOTTLE.getDefaultInstance(), 176 + seed2, 8 + seedhigh1);
            guiGraphics.renderItem(Items.CRAFTING_TABLE.getDefaultInstance(), 194 + seed2, 8 + seedhigh1);
            guiGraphics.renderItem(Items.BONE.getDefaultInstance(), 158 + seed2, 26 + seedhigh1);
            guiGraphics.renderItem(ModItems.CHICKEN_STICK.get().getDefaultInstance(), 176 + seed2, 26 + seedhigh1);
            guiGraphics.renderItem(Items.BONE.getDefaultInstance(), 194 + seed2, 26 + seedhigh1);
            guiGraphics.renderItem(Items.FURNACE.getDefaultInstance(), 158 + seed2, 44 + seedhigh1);
            guiGraphics.renderItem(Items.GLASS_BOTTLE.getDefaultInstance(), 176 + seed2, 44 + seedhigh1);
            guiGraphics.renderItem(Items.FURNACE.getDefaultInstance(), 194 + seed2, 44 + seedhigh1);
            guiGraphics.renderItem(ModBlocks.SOUL_EXTRACTOR.get().asItem().getDefaultInstance(), 238 + seed2, 26 + seedhigh1);
            //endregion


            //region tier6
            guiGraphics.renderItem(Items.BLAZE_ROD.getDefaultInstance(), 158 + seed3, 8 + seedhigh1);
            guiGraphics.renderItem(Items.CLOCK.getDefaultInstance(), 176 + seed3, 8 + seedhigh1);
            guiGraphics.renderItem(Items.BLAZE_ROD.getDefaultInstance(), 194 + seed3, 8 + seedhigh1);
            guiGraphics.renderItem(ModBlocks.BREEDER.get().asItem().getDefaultInstance(), 158 + seed3, 26 + seedhigh1);
            guiGraphics.renderItem(ModBlocks.BREEDER.get().asItem().getDefaultInstance(), 176 + seed3, 26 + seedhigh1);
            guiGraphics.renderItem(ModBlocks.BREEDER.get().asItem().getDefaultInstance(), 194 + seed3, 26 + seedhigh1);
            guiGraphics.renderItem(Items.GOLDEN_CARROT.getDefaultInstance(), 158 + seed3, 44 + seedhigh1);
            guiGraphics.renderItem(Items.ANVIL.getDefaultInstance(), 176 + seed3, 44 + seedhigh1);
            guiGraphics.renderItem(Items.GOLDEN_CARROT.getDefaultInstance(), 194 + seed3, 44 + seedhigh1);
            guiGraphics.renderItem(ModItems.TRAINER.get().getDefaultInstance(), 238 + seed3, 26 + seedhigh1);
            //endregion
            guiGraphics.pose().popPose();
        }

        if(this.page == 2) {
            guiGraphics.pose().pushPose();
            guiGraphics.pose().scale(1.7F, 1.7F, 1.7F);
            guiGraphics.drawString(this.font, Component.literal("BREEDER"), 40, 4, 32768, false);
            guiGraphics.drawString(this.font, Component.literal("Slot 1: Chicken"), 4, 15, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Slot 2: Seed"), 4, 24, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Slot 3: Breeder Output"), 4, 33, 0, false);
            guiGraphics.pose().popPose();
            guiGraphics.pose().pushPose();
            guiGraphics.drawString(this.font, Component.literal("1"), 264, 35, 6749952, false);
            guiGraphics.drawString(this.font, Component.literal("2"), 290, 35, 6749952, false);
            guiGraphics.drawString(this.font, Component.literal("3"),  354, 35, 6749952, false);
            guiGraphics.drawString(this.font, Component.literal("4"),  372, 35, 6749952, false);
            guiGraphics.drawString(this.font, Component.literal("5"),  389, 35, 6749952, false);
            guiGraphics.drawString(this.font, Component.literal("6"),  407, 35, 6749952, false);
            guiGraphics.drawString(this.font, Component.literal("7"),  354, 53, 6749952, false);
            guiGraphics.drawString(this.font, Component.literal("8"),  372, 53, 6749952, false);
            guiGraphics.drawString(this.font, Component.literal("9"),  389, 53, 6749952, false);
            guiGraphics.drawString(this.font, Component.literal("10"),  404, 53, 6749952, false);
            guiGraphics.pose().popPose();
            guiGraphics.pose().pushPose();
            guiGraphics.pose().scale(1.3F, 1.3F, 1.3F);
            guiGraphics.drawString(this.font, Component.literal("Chicken Tier Example"), 4, 70, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Tier 1 Chicken need Tier 1-9 Seeds"), 4, 79, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Tier 2 Chicken need Tier 2-9 Seeds"), 4, 88, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Tier 3 Chicken need Tier 3-9 Seeds"), 4, 97, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Tier 4 Chicken need Tier 4-9 Seeds"), 4, 106, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Tier 5 Chicken need Tier 5-9 Seeds"), 4, 115, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Tier 6 Chicken need Tier 6-9 Seeds"), 4, 124, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Tier 7 Chicken need Tier 7-9 Seeds"), 4, 133, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Tier 8 Chicken need Tier 8-9 Seeds"), 4, 142, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Tier 9 Chicken need Tier 9 Seeds"), 4, 151, 0, false);
            guiGraphics.pose().popPose();
            }

        if(this.page == 3) {
            guiGraphics.pose().pushPose();
            guiGraphics.pose().scale(1.7F, 1.7F, 1.7F);
            guiGraphics.drawString(this.font, Component.literal("ROOST"), 40, 4, 32768, false);
            guiGraphics.drawString(this.font, Component.literal("Slot 1: Chicken"), 4, 15, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Slot 2: Seed"), 4, 24, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Slot 3: Resource Output"), 4, 33, 0, false);
            guiGraphics.pose().popPose();
            guiGraphics.pose().pushPose();
            guiGraphics.pose().scale(1.5F, 1.5F, 1.5F);
            guiGraphics.drawString(this.font, Component.literal("1"),  177, 15, 6749952, false);
            guiGraphics.drawString(this.font, Component.literal("2"), 189, 30, 6749952, false);
            guiGraphics.drawString(this.font, Component.literal("3"),  244, 30, 6749952, false);
            guiGraphics.pose().popPose();
            guiGraphics.pose().pushPose();
            guiGraphics.pose().scale(1.3F, 1.3F, 1.3F);
            guiGraphics.drawString(this.font, Component.literal("Chicken Tier Example"), 4, 70, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Tier 1 Chicken need Tier 1-9 Seeds"), 4, 79, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Tier 2 Chicken need Tier 2-9 Seeds"), 4, 88, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Tier 3 Chicken need Tier 3-9 Seeds"), 4, 97, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Tier 4 Chicken need Tier 4-9 Seeds"), 4, 106, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Tier 5 Chicken need Tier 5-9 Seeds"), 4, 115, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Tier 6 Chicken need Tier 6-9 Seeds"), 4, 124, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Tier 7 Chicken need Tier 7-9 Seeds"), 4, 133, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Tier 8 Chicken need Tier 8-9 Seeds"), 4, 142, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Tier 9 Chicken need Tier 9 Seeds"), 4, 151, 0, false);
            guiGraphics.pose().popPose();
        }

        if(this.page == 4) {
            guiGraphics.pose().pushPose();
            guiGraphics.pose().scale(1.7F, 1.7F, 1.7F);
            guiGraphics.drawString(this.font, Component.literal("SOUL BREEDER"), 40, 4, 32768, false);
            guiGraphics.drawString(this.font, Component.literal("Slot 1: Soul"), 4, 15, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Slot 2: Chicken"), 4, 24, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Slot 3: Chicken Output"), 4, 33, 0, false);
            guiGraphics.pose().popPose();
            guiGraphics.pose().pushPose();
            guiGraphics.pose().scale(1.5F, 1.5F, 1.5F);
            guiGraphics.drawString(this.font, Component.literal("1"), 177, 15, 6749952, false);
            guiGraphics.drawString(this.font, Component.literal("2"), 189, 30, 6749952, false);
            guiGraphics.drawString(this.font, Component.literal("3"),  244, 30, 6749952, false);
            guiGraphics.pose().popPose();
            guiGraphics.pose().pushPose();
            guiGraphics.pose().scale(1.3F, 1.3F, 1.3F);
            guiGraphics.drawString(this.font, Component.literal("Chicken Tier Example"), 4, 70, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Tier 1 Chicken need Tier 1-9 Souls"), 4, 79, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Tier 2 Chicken need Tier 2-9 Souls"), 4, 88, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Tier 3 Chicken need Tier 3-9 Souls"), 4, 97, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Tier 4 Chicken need Tier 4-9 Souls"), 4, 106, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Tier 5 Chicken need Tier 5-9 Souls"), 4, 115, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Tier 6 Chicken need Tier 6-9 Souls"), 4, 124, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Tier 7 Chicken need Tier 7-9 Souls"), 4, 133, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Tier 8 Chicken need Tier 8-9 Souls"), 4, 142, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Tier 9 Chicken need Tier 9 Souls"), 4, 151, 0, false);
            guiGraphics.pose().popPose();
        }
        if(this.page == 5) {
            guiGraphics.pose().pushPose();
            guiGraphics.pose().scale(1.7F, 1.7F, 1.7F);
            guiGraphics.drawString(this.font, Component.literal("SOUL EXTRACTOR"), 40, 4, 32768, false);
            guiGraphics.drawString(this.font, Component.literal("Slot 1: Chicken"), 4, 15, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Slot 2: Soul Output"), 4, 24, 0, false);
            guiGraphics.pose().popPose();
            guiGraphics.pose().pushPose();
            guiGraphics.pose().scale(1.5F, 1.5F, 1.5F);
            guiGraphics.drawString(this.font, Component.literal("1"), 189, 30, 6749952, false);
            guiGraphics.drawString(this.font, Component.literal("2"),  244, 30, 6749952, false);
            guiGraphics.pose().popPose();
            guiGraphics.pose().pushPose();
            guiGraphics.pose().scale(1.3F, 1.3F, 1.3F);
            guiGraphics.drawString(this.font, Component.literal("Chicken Tier Example"), 4, 70, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Tier 1 Chickens produce Tier 1 Souls"), 4, 79, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Tier 2 Chickens produce Tier 2 Souls"), 4, 88, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Tier 3 Chickens produce Tier 3 Souls"), 4, 97, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Tier 4 Chickens produce Tier 4 Souls"), 4, 106, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Tier 5 Chickens produce Tier 5 Souls"), 4, 115, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Tier 6 Chickens produce Tier 6 Souls"), 4, 124, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Tier 7 Chickens produce Tier 7 Souls"), 4, 133, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Tier 8 Chickens produce Tier 8 Souls"), 4, 142, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Tier 9 Chickens produce Tier 9 Souls"), 4, 151, 0, false);
            guiGraphics.pose().popPose();
        }
        if(this.page == 6) {
            guiGraphics.pose().pushPose();
            guiGraphics.pose().scale(1.7F, 1.7F, 1.7F);
            guiGraphics.drawString(this.font, Component.literal("CHICKEN TRAINER"), 40, 4, 32768, false);
            guiGraphics.drawString(this.font, Component.literal("Slot 1: Seed"), 4, 15, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Slot 2: Chicken"), 4, 24, 0, false);
            guiGraphics.pose().popPose();
            guiGraphics.pose().pushPose();
            guiGraphics.pose().scale(1.5F, 1.5F, 1.5F);
            guiGraphics.drawString(this.font, Component.literal("1"), 189, 30, 6749952, false);
            guiGraphics.drawString(this.font, Component.literal("2"),  244, 30, 6749952, false);
            guiGraphics.pose().popPose();
            guiGraphics.pose().pushPose();
            guiGraphics.pose().scale(1.3F, 1.3F, 1.3F);
            guiGraphics.drawString(this.font, Component.literal("Seed Tier Example"), 4, 70, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Tier 1 Seed give " + "\u00A7a" + Config.food_xp_tier_1.get() + "\u00A79" + "XP"), 4, 79, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Tier 2 Seed give " + "\u00A7a" + Config.food_xp_tier_2.get() + "\u00A79" + "XP"), 4, 88, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Tier 3 Seed give " + "\u00A7a" + Config.food_xp_tier_3.get() + "\u00A79" + "XP"), 4, 97, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Tier 4 Seed give " + "\u00A7a" + Config.food_xp_tier_4.get() + "\u00A79" + "XP"), 4, 106, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Tier 5 Seed give " + "\u00A7a" + Config.food_xp_tier_5.get() + "\u00A79" + "XP"), 4, 115, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Tier 6 Seed give " + "\u00A7a" + Config.food_xp_tier_6.get() + "\u00A79" + "XP"), 4, 124, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Tier 7 Seed give " + "\u00A7a" + Config.food_xp_tier_7.get() + "\u00A79" + "XP"), 4, 133, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Tier 8 Seed give " + "\u00A7a" + Config.food_xp_tier_8.get() + "\u00A79" + "XP"), 4, 142, 0, false);
            guiGraphics.drawString(this.font, Component.literal("Tier 9 Seed give " + "\u00A7a" + Config.food_xp_tier_9.get() + "\u00A79" + "XP"), 4, 151, 0, false);
            guiGraphics.pose().popPose();
        }

        if(this.page == 7){
            guiGraphics.pose().pushPose();
            guiGraphics.pose().scale(1.5F, 1.5F, 1.5F);
            guiGraphics.drawString(this.font, Component.literal("World Spawn of Chickens per Biome"), 10, 10, 0, false);
            guiGraphics.pose().popPose();
            /*guiGraphics.drawWordWrap(this.font, Component.literal("Stone Chicken: Stony Peaks, Stony Shore         " +
                    "Sand Chicken: Desert                      " +
                    "Snow Chicken: Snowy Beach, Snowy Plains, Snowy Slopes, Snowy Taiga" +
                    "Soul Sand Chicken: Soul Sand Valley                      " +
                    "Soul Soil Chicken: Soul Sand Valley     " + "\u00A7a" + "                " +
                    "Netherrack Chicken: Nether Wastes                      " +
                    "Crimson Chicken: Crimson Forest                      " +
                    "Warped Chicken: Warped Forest                      " +
                    "Basalt Chicken: Basalt Deltas                      " +
                    "End Stone Chicken: End Barrens, End Highlands, End Midlands, Small End Islands" +
                    "Oak Chicken: Forest, Flower Forest                      " +
                    "Birch Chicken: Birch Forest                      " +
                    "Acacia Chicken: Flower Forest                      " +
                    "Jungle Chicken: Jungle                      " +
                    "Spruce Chicken: Old Growth Spruce Taiga                      " +
                    "Dark Oak Chicken: Dark Forest                      " +
                    "Sugar Chicken: Beach                      " +
                    "Beetroot Chicken: Flower Forest                      " +
                    "Glowberry Chicken: Flower Forest                      " +
                    "Melon Chicken: Flower Forest                      " +
                    "Carrot Chicken: Flower Forest                      " +
                    "Sweet Berry Chicken: Flower Forest, Sunflower Plains" +
                    "String Chicken: Dark Forest"
            ), 10, 30, 250, 10);*/
            guiGraphics.pose().pushPose();
            guiGraphics.pose().scale(0.8F, 0.8F, 0.8F);

            guiGraphics.pose().translate(-3, -20, 10);
            guiGraphics.drawString(this.font, Component.literal("\u00A79" + "Stone Chicken: " + "\u00A7a" + "Stony Peaks, Stony Shore"), 10, 60, 0, false);
            guiGraphics.drawString(this.font, Component.literal("\u00A79" + "Sand Chicken:"+ "\u00A7a" + " Desert"), 10, 70, 0, false);
            guiGraphics.drawString(this.font, Component.literal("\u00A79" + "Snow Chicken:"+ "\u00A7a" + " Snowy Beach, Snowy Plains, Snowy Slopes, Snowy Taiga"), 10, 80, 0, false);
            guiGraphics.drawString(this.font, Component.literal("\u00A79" + "Soul Sand Chicken:"+ "\u00A7a" + " Soul Sand Valley"), 10, 90, 0, false);
            guiGraphics.drawString(this.font, Component.literal("\u00A79" + "Soul Soil Chicken:"+ "\u00A7a" + " Soul Sand Valley"), 10, 100, 0, false);
            guiGraphics.drawString(this.font, Component.literal("\u00A79" + "Netherrack Chicken:"+ "\u00A7a" + " Nether Wastes"), 10, 110, 0, false);
            guiGraphics.drawString(this.font, Component.literal("\u00A79" + "Crimson Chicken:"+ "\u00A7a" + " Crimson Forest"), 10, 120, 0, false);
            guiGraphics.drawString(this.font, Component.literal("\u00A79" + "Warped Chicken:"+ "\u00A7a" + " Warped Forest"), 10, 130, 0, false);
            guiGraphics.drawString(this.font, Component.literal("\u00A79" + "Basalt Chicken:"+ "\u00A7a" + " Basalt Deltas"), 10, 140, 0, false);
            guiGraphics.drawString(this.font, Component.literal("\u00A79" + "End Stone Chicken:"+ "\u00A7a" + " End Barrens, End Highlands, End Midlands, Small End Islands"), 10, 150, 0, false);
            guiGraphics.drawString(this.font, Component.literal("\u00A79" + "Oak Chicken:"+ "\u00A7a" + " Forest, Flower Forest"), 10, 160, 0, false);
            guiGraphics.drawString(this.font, Component.literal("\u00A79" + "Birch Chicken:"+ "\u00A7a" + " Birch Forest"), 10, 170, 0, false);
            guiGraphics.drawString(this.font, Component.literal("\u00A79" + "Acacia Chicken:"+ "\u00A7a" + " Flower Forest"), 10, 180, 0, false);
            guiGraphics.drawString(this.font, Component.literal("\u00A79" + "Jungle Chicken:"+ "\u00A7a" + " Jungle"), 10, 190, 0, false);
            guiGraphics.drawString(this.font, Component.literal("\u00A79" + "Spruce Chicken:"+ "\u00A7a" + " Old Growth Spruce Taiga"), 10, 200, 0, false);
            guiGraphics.drawString(this.font, Component.literal("\u00A79" + "Dark Oak Chicken:"+ "\u00A7a" + " Dark Forest"), 10, 210, 0, false);
            guiGraphics.drawString(this.font, Component.literal("\u00A79" + "Sugar Chicken:"+ "\u00A7a" + " Beach"), 10, 220, 0, false);
            guiGraphics.drawString(this.font, Component.literal("\u00A79" + "Beetroot Chicken:"+ "\u00A7a" + " Flower Forest"), 10, 230, 0, false);
            guiGraphics.drawString(this.font, Component.literal("\u00A79" + "Glowberry Chicken:"+ "\u00A7a" + " Flower Forest"), 10, 240, 0, false);
            guiGraphics.drawString(this.font, Component.literal("\u00A79" + "Melon Chicken:"+ "\u00A7a" + " Flower Forest"), 10, 250, 0, false);
            guiGraphics.drawString(this.font, Component.literal("\u00A79" + "Carrot Chicken:"+ "\u00A7a" + " Flower Forest"), 10, 260, 0, false);
            guiGraphics.drawString(this.font, Component.literal("\u00A79" + "Sweet Berry Chicken:"+ "\u00A7a" + " Flower Forest, Sunflower Plains"), 10, 270, 0, false);
            guiGraphics.drawString(this.font, Component.literal("\u00A79" + "String Chicken:"+ "\u00A7a" + " Dark Forest"), 10, 280, 0, false);
            //guiGraphics.drawString(this.font, Component.literal("FFFFFFFFFFFFFFFFFFFFFFFF"), 10, 180, 0, false);
            guiGraphics.pose().popPose();

        }
        }



    @Override
    public void onClose() {
        super.onClose();
    }

    @Override
    public void init() {
        super.init();
            button_empty = Button.builder(Component.literal("<"), e -> {
                if (true) {
                    if (this.page > 0) {
                        this.page--;
                    }
                    //System.out.println("+" + page);
                }
            }).bounds(this.leftPos + 3, this.topPos + 222, 30, 20).build();
            guistate.put("button:button_empty", button_empty);
            this.addRenderableWidget(button_empty);
            button_empty1 = Button.builder(Component.literal(">"), e -> {
                if (true) {
                    if (this.page < currentpages) {
                        this.page++;
                    }
                    //System.out.println("-" + this.page);
                }
            }).bounds(this.leftPos + 400, this.topPos + 222, 30, 20).build();
            guistate.put("button:button_empty1", button_empty1);
            this.addRenderableWidget(button_empty1);
    }
}
