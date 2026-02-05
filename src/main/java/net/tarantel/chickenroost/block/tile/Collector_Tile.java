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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.block.blocks.Roost_Block;
import net.tarantel.chickenroost.handler.Collector_Handler;
import net.tarantel.chickenroost.util.WrappedHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;


public class Collector_Tile extends BlockEntity implements MenuProvider {

    public final ItemStackHandler itemHandler = new ItemStackHandler(54) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            assert level != null;
            if (!level.isClientSide) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };


    private final Set<BlockPos> activeRoosts = new HashSet<>();
    private int collectRange = 10;


    public Collector_Tile(BlockPos pos, BlockState state) {
        super(ModBlockEntities.COLLECTOR.get(), pos, state);
    }


    public Set<BlockPos> getActiveRoosts() {
        return java.util.Collections.unmodifiableSet(this.activeRoosts);
    }

    public void setRoostActive(BlockPos pos, boolean active) {
        if (active) this.activeRoosts.add(pos);
        else this.activeRoosts.remove(pos);
        setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }
    public int getCollectRange() { return this.collectRange; }
    public void setCollectRange(int r) {
        int nr = Math.max(5, Math.min(30, r));
        if (nr != this.collectRange) {
            this.collectRange = nr;
            setChanged();
            if (level != null && !level.isClientSide) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
            }
        }
    }




    private int _roostValidationTicker = 0;


    /*private boolean isValidRoost(BlockPos pos) {
        if (this.level == null || pos == null) return false;

        BlockEntity be = this.level.getBlockEntity(pos);
        if (!(be instanceof Roost_Tile)) return false;

        BlockState state = this.level.getBlockState(pos);
        return state.getBlock() instanceof Roost_Block;
    }*/

    private boolean isValidCollectorTarget(BlockPos pos) {
        if (this.level == null || pos == null) return false;

        BlockEntity be = this.level.getBlockEntity(pos);
        if (be == null) return false;

        // Erlaubte Collector-Quellen
        return be instanceof Roost_Tile
                || be instanceof Breeder_Tile
                || be instanceof Soul_Extractor_Tile;
    }

    private void pruneMissingCollectorTargets() {
        if (this.level == null || activeRoosts.isEmpty()) return;

        activeRoosts.removeIf(pos -> !isValidCollectorTarget(pos));
    }

    /*private void pruneMissingRoosts() {
        if (this.level == null || activeRoosts == null || activeRoosts.isEmpty()) return;
        activeRoosts.removeIf(pos -> !isValidRoost(pos));
    }*/


    private void maybePruneCollectorTargetsPeriodic() {

        if ((++_roostValidationTicker % 20) == 0) {
            pruneMissingCollectorTargets();
        }
    }

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap = new HashMap<>();

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {

            if (side == null) {
                return lazyItemHandler.cast();
            }

            // Erzeuge Handler nur einmal pro Seite
            directionWrappedHandlerMap.computeIfAbsent(side, dir ->
                    LazyOptional.of(() ->
                            new WrappedHandler(
                                    itemHandler,
                                    slot -> true,          // ✅ ALLE Slots dürfen extrahiert werden
                                    (slot, stack) -> itemHandler.isItemValid(slot, stack) // Insert-Logik
                            )
                    )
            );

            return directionWrappedHandlerMap.get(side).cast();
        }

        return super.getCapability(cap, side);
    }


    public @Nullable IItemHandler getItemHandlerCapability(@Nullable Direction side) {

        return itemHandler;
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
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
    public void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());

        long[] arr = this.activeRoosts.stream().mapToLong(BlockPos::asLong).toArray();
        nbt.putLongArray("collector.roosts", arr);
        nbt.putInt("collector.range", this.collectRange);
        super.saveAdditional(nbt);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));

        this.activeRoosts.clear();
        long[] arr = nbt.getLongArray("collector.roosts");
        for (long l : arr) this.activeRoosts.add(BlockPos.of(l));

        this.collectRange = Math.max(5, Math.min(30, nbt.getInt("collector.range")));
        pruneMissingCollectorTargets();
    }


    private static void moveOneStack(IItemHandler from, IItemHandler to) {
        ItemStack stack = from.getStackInSlot(2);
        if (stack.isEmpty()) return;
        ItemStack moving = stack.copy();
        for (int i = 0; i < to.getSlots() && !moving.isEmpty(); i++) {
            moving = to.insertItem(i, moving, false);
        }
        int moved = stack.getCount() - (moving.isEmpty() ? 0 : moving.getCount());
        if (moved > 0) {
            from.extractItem(2, moved, false);
        }
    }

    private static void pullFromSlot(
            IItemHandler from,
            IItemHandler to,
            int fromSlot
    ) {
        ItemStack stack = from.getStackInSlot(fromSlot);
        if (stack.isEmpty()) return;

        ItemStack moving = stack.copy();

        // versuche in Collector-Inventar einzufügen
        for (int i = 0; i < to.getSlots() && !moving.isEmpty(); i++) {
            moving = to.insertItem(i, moving, false);
        }

        int moved = stack.getCount() - moving.getCount();
        if (moved > 0) {
            from.extractItem(fromSlot, moved, false);
        }
    }



    private static void pullFromMultipleSlots(
            IItemHandler from,
            IItemHandler to,
            int[] slots
    ) {
        for (int i = 0; i < slots.length; i++) {
            pullFromSlot(from, to, slots[i]);
        }
    }



    private static void collectFromActiveRoosts(Level level, Collector_Tile self) {
        if (self.activeRoosts.isEmpty()) return;

        for (BlockPos pos : new HashSet<>(self.activeRoosts)) {

            BlockEntity be = level.getBlockEntity(pos);
            if (be == null) continue;

            IItemHandler sourceInv = be.getCapability(
                    ForgeCapabilities.ITEM_HANDLER, null
            ).orElse(null);

            if (sourceInv == null) continue;

            // ===== ROOST =====
            if (be instanceof Roost_Tile) {
                // Roost output = Slot 2
                pullFromSlot(sourceInv, self.itemHandler, 2);
            }

            // ===== SOUL EXTRACTOR =====
            else if (be instanceof Soul_Extractor_Tile) {
                // Soul Extractor output = Slot 1
                pullFromSlot(sourceInv, self.itemHandler, 1);
            }

            // ===== BREEDER =====
            else if (be instanceof Breeder_Tile) {
                // Breeder outputs: Slot 1 und Slots 3–11
                int[] breederSlots = new int[] {
                        3, 4, 5, 6, 7, 8, 9, 10, 11
                };
                pullFromMultipleSlots(sourceInv, self.itemHandler, breederSlots);
            }
        }
    }




    public void drops() {
        // Create a SimpleContainer to hold the items from the item handler
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        // Create an ItemStack for the block
        ItemStack itemStack = new ItemStack(ModBlocks.COLLECTOR.get());

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

    public static void tick(Level level, BlockPos pos, BlockState state, Collector_Tile tile) {
        if (level.isClientSide()) return;
        tile.maybePruneCollectorTargetsPeriodic();
        collectFromActiveRoosts(level, tile);
        setChanged(level, pos, state);
    }


    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("block.chicken_roost.collector");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory inv, @NotNull Player player) {
        return new Collector_Handler(id, inv, this);
    }
}
