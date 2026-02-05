package net.tarantel.chickenroost.block.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;


import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.api.ICollectorTarget;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.block.blocks.Roost_Block;
import net.tarantel.chickenroost.handler.Roost_Handler;
import net.tarantel.chickenroost.item.base.*;
import net.tarantel.chickenroost.recipes.Breeder_Recipe;
import net.tarantel.chickenroost.recipes.ModRecipes;
import net.tarantel.chickenroost.recipes.Roost_Recipe;
import net.tarantel.chickenroost.recipes.Soul_Extractor_Recipe;
import net.tarantel.chickenroost.util.Config;
import net.tarantel.chickenroost.util.WrappedHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class Roost_Tile extends BlockEntity implements MenuProvider, ICollectorTarget {
    public final ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(slot == 1){
                resetProgress();
            }
        }
        @Override
        public int getSlotLimit(int slot)
        {
            if(slot == 0){
                return 64;
            }
            if(slot == 1) {
                return 1;
            }
            if(slot == 2) {
                return 64;
            }
            return 0;
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0 -> (stack.getItem() instanceof ChickenSeedBase);
                case 1 -> (stack.getItem() instanceof ChickenItemBase);
                case 2 -> false;
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();


    private static final int OUTPUT_SLOT = 2;
    @Nullable
    private String customName = "ROOST";

    public void setCustomName(String name) {
        if (name == null) name = "";
        this.customName = name;
        setChanged();
        if (this.level != null && !this.level.isClientSide) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
        }
    }

    public String getCustomName() {
        return this.customName;
    }

    public ItemStack getRenderStack() {
        ItemStack stack;

        if(!itemHandler.getStackInSlot(1).isEmpty()) {
            stack = itemHandler.getStackInSlot(1);
        } else {
            stack = ItemStack.EMPTY;
        }

        return stack;
    }

    public void setHandler(ItemStackHandler itemStackHandler) {
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            itemHandler.setStackInSlot(i, itemStackHandler.getStackInSlot(i));
        }
    }
    protected final ContainerData data;
    public int progress = 0;
    public int maxProgress = ( Config.ServerConfig.roost_speed_tick.get() * 20);

    public int getScaledProgress() {
        int progresss = progress;
        int maxProgresss = maxProgress;  // Max Progress
        int progressArrowSize = 200; // This is the height in pixels of your arrow

        return maxProgresss != 0 && progresss != 0 ? progresss * progressArrowSize / maxProgresss : 0;
    }
    public final int[] LevelList;
    public final int[] XPList;
    public final int[] XPAmountList;

    public Roost_Tile(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ROOST.get(), pos, state);

        this.LevelList = new int[]{
                Objects.requireNonNullElse(Config.ServerConfig.maxlevel_tier_1.get(), 128),
                Objects.requireNonNullElse(Config.ServerConfig.maxlevel_tier_2.get(), 128),
                Objects.requireNonNullElse(Config.ServerConfig.maxlevel_tier_3.get(), 128),
                Objects.requireNonNullElse(Config.ServerConfig.maxlevel_tier_4.get(), 128),
                Objects.requireNonNullElse(Config.ServerConfig.maxlevel_tier_5.get(), 128),
                Objects.requireNonNullElse(Config.ServerConfig.maxlevel_tier_6.get(), 128),
                Objects.requireNonNullElse(Config.ServerConfig.maxlevel_tier_7.get(), 128),
                Objects.requireNonNullElse(Config.ServerConfig.maxlevel_tier_8.get(), 128),
                Objects.requireNonNullElse(Config.ServerConfig.maxlevel_tier_9.get(), 128)
        };

        this.XPList = new int[]{
                Config.ServerConfig.xp_tier_1.get(),
                Config.ServerConfig.xp_tier_2.get(),
                Config.ServerConfig.xp_tier_3.get(),
                Config.ServerConfig.xp_tier_4.get(),
                Config.ServerConfig.xp_tier_5.get(),
                Config.ServerConfig.xp_tier_6.get(),
                Config.ServerConfig.xp_tier_7.get(),
                Config.ServerConfig.xp_tier_8.get(),
                Config.ServerConfig.xp_tier_9.get()
        };

        this.XPAmountList = new int[]{
                Config.ServerConfig.food_xp_tier_1.get(),
                Config.ServerConfig.food_xp_tier_2.get(),
                Config.ServerConfig.food_xp_tier_3.get(),
                Config.ServerConfig.food_xp_tier_4.get(),
                Config.ServerConfig.food_xp_tier_5.get(),
                Config.ServerConfig.food_xp_tier_6.get(),
                Config.ServerConfig.food_xp_tier_7.get(),
                Config.ServerConfig.food_xp_tier_8.get(),
                Config.ServerConfig.food_xp_tier_9.get()
        };

        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> Roost_Tile.this.progress;
                    case 1 -> Roost_Tile.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> Roost_Tile.this.progress = value;
                    case 1 -> Roost_Tile.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public @NotNull Component getDisplayName() {

        return Component.translatable("name.chicken_roost.roost");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
        return new Roost_Handler(id, inventory, this, this.data);
    }
    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap =
            new HashMap<>();
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if(side == null) {
                return lazyItemHandler.cast();
            }
            for (Direction direction : Arrays.asList(Direction.DOWN, Direction.UP, Direction.NORTH, Direction.EAST, Direction.WEST, Direction.SOUTH)) {
                directionWrappedHandlerMap.put (direction, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 2, (i, s) -> false)));
            }
            for (Direction direction : Arrays.asList(Direction.DOWN,Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH, Direction.UP)) {
                directionWrappedHandlerMap.put (direction, LazyOptional.of(() -> new WrappedHandler(itemHandler, (index) -> index == 2,
                        (index, stack) -> itemHandler.isItemValid(0, stack) || itemHandler.isItemValid(1, stack))));
            }

            if(directionWrappedHandlerMap.containsKey(side)) {
                Direction localDir = this.getBlockState().getValue(Roost_Block.FACING);

                if(side == Direction.UP || side == Direction.DOWN) {
                    return directionWrappedHandlerMap.get(side).cast();
                }

                return switch (localDir) {
                    default -> directionWrappedHandlerMap.get(side.getOpposite()).cast();
                    case EAST -> directionWrappedHandlerMap.get(side.getClockWise()).cast();
                    case SOUTH -> directionWrappedHandlerMap.get(side).cast();
                    case WEST -> directionWrappedHandlerMap.get(side.getCounterClockWise()).cast();
                };
            }
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        if(!level.isClientSide()) {
            ///  ModMessages.sendToClients(new RoostItemStackSyncS2CPacket(this.itemHandler, worldPosition));
        }
        setChanged();
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());
        nbt.putInt("roost.progress", this.progress);
        nbt.putString("roost.custom_name", this.customName);
        nbt.putBoolean("AutoOutput", autoOutput);
        nbt.putBoolean("LastRedstonePowered", lastRedstonePowered);
        super.saveAdditional(nbt);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("roost.progress");
        this.customName = nbt.getString("roost.custom_name");
        if (nbt.contains("AutoOutput")) {
            this.autoOutput = nbt.getBoolean("AutoOutput");
        } else {
            this.autoOutput = false; // fallback, falls alte Welten
        }
        if (nbt.contains("LastRedstonePowered")) {
            this.lastRedstonePowered = nbt.getBoolean("LastRedstonePowered");
        } else {
            this.lastRedstonePowered = false;
        }

    }

    public void drops() {
        // Create a SimpleContainer to hold the items from the item handler
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        // Create an ItemStack for the block
        ItemStack itemStack = new ItemStack(ModBlocks.ROOST.get());

        // Save the inventory contents to the ItemStack's NBT
        CompoundTag nbt = new CompoundTag();
        ListTag itemsTag = new ListTag();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty()) {
                CompoundTag itemTag = new CompoundTag();
                itemTag.putInt("Slot", i);
                stack.save(itemTag);
                itemsTag.add(itemTag);
            }
        }
        nbt.put("Items", itemsTag);
        itemStack.setTag(nbt);

        // Create a SimpleContainer to hold the block's ItemStack
        SimpleContainer block = new SimpleContainer(1);
        block.setItem(0, itemStack.copy());

        // Drop the contents in the world
        Containers.dropContents(Objects.requireNonNull(this.level), this.worldPosition, block);
    }
    private boolean autoOutput = false; // Standard: an

    /**
     * Edge-detection for redstone.
     * - Rising edge (OFF -> ON) enables AutoOutput.
     * - While redstone stays ON, the user may disable AutoOutput via GUI,
     *   and it will NOT be forced back ON until the next rising edge.
     */
    private boolean lastRedstonePowered = false;

    public boolean isAutoOutputEnabled() {
        return autoOutput;
    }

    public void setAutoOutputEnabled(boolean enabled) {
        this.autoOutput = enabled;

        if (level != null && !level.isClientSide) {
            setChanged(level, worldPosition, getBlockState());
        }
    }

    public static void tick(Level level, BlockPos pos, BlockState state, Roost_Tile pEntity) {
        if(level.isClientSide()) {
            return;
        }
        boolean powered = level.hasNeighborSignal(pos);
        if (powered && !pEntity.lastRedstonePowered) {
            pEntity.setAutoOutputEnabled(true); // nur bei OFF -> ON
        }
        pEntity.lastRedstonePowered = powered;


        setChanged(level, pos, state);
        if(hasRecipe(pEntity)) {
            pEntity.progress++;


            if(pEntity.progress >= pEntity.maxProgress) {
                craftItem(pEntity);
            }
        } else {
            pEntity.resetProgress();


            setChanged(level, pos, state);
        }
        if(pEntity.isAutoOutputEnabled()) {
            // <<< HIER: Output nach unten schieben >>>
            tryPushOutputDown(level, pos, state, pEntity);
        }

    }

    private static void tryPushOutputDown(Level level, BlockPos pos, BlockState state, Roost_Tile tile) {
        // Stack im Output-Slot holen
        ItemStack outputStack = tile.itemHandler.getStackInSlot(OUTPUT_SLOT);
        if (outputStack.isEmpty()) {
            return;
        }

        // ItemHandler des Blocks direkt unter dem Roost holen (von oben)
        BlockEntity belowBe = level.getBlockEntity(pos.below());
        IItemHandler belowHandler = belowBe == null
                ? null
                : belowBe.getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.UP).orElse(null);

        if (belowHandler == null) {
            return;
        }

        // Kopie des aktuellen Output-Stacks, mit dem wir arbeiten
        ItemStack remaining = outputStack.copy();

        // Versuche, so viel wie möglich in das Inventar darunter zu schieben
        for (int slot = 0; slot < belowHandler.getSlots() && !remaining.isEmpty(); slot++) {
            remaining = belowHandler.insertItem(slot, remaining, false); // simulate = false -> wirklich einfügen
        }

        // Wenn sich nichts geändert hat, wurde nichts eingefügt -> fertig
        if (remaining.getCount() == outputStack.getCount()) {
            return;
        }

        // Berechnen, wie viele Items tatsächlich verschoben wurden
        int moved = outputStack.getCount() - remaining.getCount();

        // Output-Slot im Roost anpassen (Stack verkleinern oder leeren)
        ItemStack newStack = outputStack.copy();
        newStack.shrink(moved); // verringert die Anzahl um "moved"

        if (newStack.isEmpty()) {
            newStack = ItemStack.EMPTY;
        }

        tile.itemHandler.setStackInSlot(OUTPUT_SLOT, newStack);
        setChanged(level, pos, state);
    }

    private void resetProgress() {

        this.progress = 0;
    }
    public static ItemStack ChickenItem;
    public static ChickenSeedBase FoodItem;
    public static ChickenItemBase MyChicken;


    private static final String NBT_LEVEL_KEY = "roost_lvl";
    private static final String NBT_XP_KEY    = "roost_xp";

    private static void craftItem(Roost_Tile pEntity) {
        MyChicken   = (ChickenItemBase) pEntity.itemHandler.getStackInSlot(1).getItem().getDefaultInstance().getItem();
        ChickenItem = pEntity.itemHandler.getStackInSlot(1);

        Level level = pEntity.level;
        if (level == null) {
            pEntity.resetProgress();
            return;
        }

        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }

        Optional<Roost_Recipe> recipeOpt = level.getRecipeManager()
                .getRecipeFor(Roost_Recipe.Type.INSTANCE, inventory, level);

        if (recipeOpt.isEmpty()) {
            pEntity.resetProgress();
            return;
        }

        Roost_Recipe recipe = recipeOpt.get();

        // ---- 1.20.1: Level/XP über NBT absichern (Defaults wie DataComponents) ----
        var tag = ChickenItem.getOrCreateTag();
        if (!tag.contains(NBT_LEVEL_KEY, net.minecraft.nbt.Tag.TAG_INT)) tag.putInt(NBT_LEVEL_KEY, 0);
        if (!tag.contains(NBT_XP_KEY, net.minecraft.nbt.Tag.TAG_INT))    tag.putInt(NBT_XP_KEY, 0);

        int roostLvl = tag.getInt(NBT_LEVEL_KEY);
        int roostXp  = tag.getInt(NBT_XP_KEY);

        // Bonus-Ausgabe: lvl/2 + recipe.output.count
        int chickenBonus = (roostLvl / 2) + recipe.output.getCount();

        // Output-Stack bauen und auf max 64 cappen
        ItemStack out = recipe.output.copy();
        int newCount = pEntity.itemHandler.getStackInSlot(2).getCount() + chickenBonus;
        out.setCount(Math.min(newCount, out.getMaxStackSize())); // meist 64

        // Sicherheit
        if (!(pEntity.itemHandler.getStackInSlot(1).getItem() instanceof ChickenItemBase)) {
            pEntity.resetProgress();
            return;
        }

        // Tier-Index wie in deinem 1.20.1 Code
        int tierIndex = MyChicken.currentchickena;

        // Seeds-Branch wie in 1.21: Wenn Seeds aktiv, XP/Level-Progress, sonst nur Output
        if (ChickenRoostMod.CONFIG.RoostSeeds) {
            // Seed Item nur dann lesen, wenn Seeds aktiv sind
            if (pEntity.itemHandler.getStackInSlot(0).getItem() instanceof ChickenSeedBase) {
                FoodItem = (ChickenSeedBase) pEntity.itemHandler.getStackInSlot(0).getItem().getDefaultInstance().getItem();

                int xpGain = (int) (pEntity.XPAmountList[FoodItem.getCurrentMaxXp()] * Config.ServerConfig.roostxp.get());
                int xpNeeded = pEntity.XPList[tierIndex];

                // Leveln nur, wenn noch unter Max-Level
                if (roostLvl < pEntity.LevelList[tierIndex]) {
                    if (roostXp + xpGain >= xpNeeded) {
                        tag.putInt(NBT_LEVEL_KEY, roostLvl + 1);
                        tag.putInt(NBT_XP_KEY, 0);
                    } else {
                        tag.putInt(NBT_XP_KEY, roostXp + xpGain);
                    }
                }

                // Seeds verbrauchen (wie bei dir)
                pEntity.itemHandler.extractItem(0, 1, false);
            } else {
                // Seeds aktiv aber kein Seed drin: trotzdem Output? -> je nach Design
                // Hier: kein Seed => kein craft
                pEntity.resetProgress();
                return;
            }

            // Chicken im Slot belassen
            pEntity.itemHandler.extractItem(1, 0, true); // no-op, aber du hattest das so drin
            pEntity.itemHandler.setStackInSlot(1, ChickenItem);
            pEntity.itemHandler.setStackInSlot(2, out.copy());
            pEntity.resetProgress();
            return;
        }

        // ---- Seeds AUS: nur Output (wie dein else-Branch in 1.21) ----
        pEntity.itemHandler.extractItem(1, 0, true); // no-op
        pEntity.itemHandler.setStackInSlot(2, out.copy());
        pEntity.resetProgress();
    }


    private static boolean hasRecipe(Roost_Tile entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<Roost_Recipe> recipe = level.getRecipeManager()
                .getRecipeFor(Roost_Recipe.Type.INSTANCE, inventory, level);

        if(recipe.isPresent()){
            entity.maxProgress = ( Config.ServerConfig.roost_speed_tick.get() * recipe.get().time);
        }

        return recipe.isPresent() && canInsertAmountIntoOutputSlot(inventory) &&
                canInsertItemIntoOutputSlot(inventory, recipe.get().output.getItem().getDefaultInstance());
    }


    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack stack) {
        return inventory.getItem(2).getItem() == stack.getItem() || inventory.getItem(2).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(2).getMaxStackSize() > inventory.getItem(2).getCount();
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }
}