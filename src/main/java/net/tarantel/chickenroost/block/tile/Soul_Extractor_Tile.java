package net.tarantel.chickenroost.block.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
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
import net.tarantel.chickenroost.api.ICollectorTarget;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.block.blocks.Soul_Extractor_Block;
import net.tarantel.chickenroost.handler.SoulExtractor_Handler;
import net.tarantel.chickenroost.item.base.*;
import net.tarantel.chickenroost.network.ExtractorStackSyncS2CPacket;
import net.tarantel.chickenroost.network.ModMessages;
import net.tarantel.chickenroost.recipes.ModRecipes;
import net.tarantel.chickenroost.recipes.Soul_Extractor_Recipe;
import net.tarantel.chickenroost.util.Config;
import net.tarantel.chickenroost.util.WrappedHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class Soul_Extractor_Tile extends BlockEntity implements MenuProvider, ICollectorTarget {

    public final ItemStackHandler itemHandler = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(slot == 0){
                resetProgress();
            }
            assert level != null;
            if(!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0 -> (stack.getItem() instanceof ChickenItemBase);
                case 1 -> false;
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();


    private static final int OUTPUT_SLOT = 2;
    @Nullable
    private String customName = "EXTRACTOR";

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

        if(!itemHandler.getStackInSlot(0).isEmpty()) {
            stack = itemHandler.getStackInSlot(0);
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
    public int maxProgress = ((int) Config.ServerConfig.extractor_speedtimer.get() * 20);
    public int getScaledProgress() {
        int progresss = progress;
        int maxProgresss = maxProgress;  // Max Progress
        int progressArrowSize = 200; // This is the height in pixels of your arrow

        return maxProgresss != 0 && progresss != 0 ? progresss * progressArrowSize / maxProgresss : 0;
    }

    public Soul_Extractor_Tile(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SOUL_EXTRACTOR.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> Soul_Extractor_Tile.this.progress;
                    case 1 -> Soul_Extractor_Tile.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> Soul_Extractor_Tile.this.progress = value;
                    case 1 -> Soul_Extractor_Tile.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("name.chicken_roost.soul_extractor_");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
        return new SoulExtractor_Handler(id, inventory, this, this.data);
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
                directionWrappedHandlerMap.put (direction, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 1, (i, s) -> false)));
            }
            for (Direction direction : Arrays.asList(Direction.DOWN,Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH, Direction.UP)) {
                directionWrappedHandlerMap.put (direction, LazyOptional.of(() -> new WrappedHandler(itemHandler, (index) -> index == 1,
                        (index, stack) -> itemHandler.isItemValid(0, stack) || itemHandler.isItemValid(1, stack))));
            }

            if(directionWrappedHandlerMap.containsKey(side)) {
                Direction localDir = this.getBlockState().getValue(Soul_Extractor_Block.FACING);

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
            ModMessages.sendToClients(new ExtractorStackSyncS2CPacket(this.itemHandler, worldPosition));
        }
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());
        nbt.putInt("soul_extractor.progress", this.progress);
        nbt.putBoolean("AutoOutput", autoOutput);
        nbt.putBoolean("LastRedstonePowered", lastRedstonePowered);
        nbt.putString("soul_extractor.custom_name", this.customName);
        super.saveAdditional(nbt);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("soul_extractor.progress");
        this.customName = nbt.getString("soul_extractor.custom_name");
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
        ItemStack itemStack = new ItemStack(ModBlocks.SOUL_EXTRACTOR.get());

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

    public static void tick(Level level, BlockPos pos, BlockState state, Soul_Extractor_Tile pEntity) {
        if(level.isClientSide()) {
            return;
        }

        ModMessages.sendToClients(new ExtractorStackSyncS2CPacket(pEntity.itemHandler, pEntity.worldPosition));
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
    private static void tryPushOutputDown(Level level, BlockPos pos, BlockState state, Soul_Extractor_Tile tile) {
        // Stack im Output-Slot holen
        ItemStack outputStack = tile.itemHandler.getStackInSlot(1);
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

        tile.itemHandler.setStackInSlot(1, newStack);
        setChanged(level, pos, state);
    }

    private void resetProgress() {
        this.progress = 0;
    }


    private static void craftItem(Soul_Extractor_Tile pEntity) {
        Level level = pEntity.level;
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }

        Optional<Soul_Extractor_Recipe> recipe = level.getRecipeManager()
                .getRecipeFor(Soul_Extractor_Recipe.Type.INSTANCE, inventory, level);

        if(hasRecipe(pEntity)) {
            //pEntity.itemHandler.extractItem(0, 1, false);
            pEntity.itemHandler.extractItem(0, 1, false);
            pEntity.itemHandler.setStackInSlot(1, new ItemStack(recipe.get().output.getItem(),
                    pEntity.itemHandler.getStackInSlot(1).getCount() + 1));

            pEntity.resetProgress();
        }
    }

    private static boolean hasRecipe(Soul_Extractor_Tile entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<Soul_Extractor_Recipe> recipe = level.getRecipeManager()
                .getRecipeFor(Soul_Extractor_Recipe.Type.INSTANCE, inventory, level);

            if(recipe.isPresent()){
                entity.maxProgress = ( Config.ServerConfig.extractor_speedtimer.get() * recipe.get().time);
            }

        return recipe.isPresent() && canInsertAmountIntoOutputSlot(inventory) &&
                canInsertItemIntoOutputSlot(inventory, recipe.get().output.getItem().getDefaultInstance());
    }


    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack stack) {
        return inventory.getItem(1).getItem() == stack.getItem() || inventory.getItem(1).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(1).getMaxStackSize() > inventory.getItem(1).getCount();
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