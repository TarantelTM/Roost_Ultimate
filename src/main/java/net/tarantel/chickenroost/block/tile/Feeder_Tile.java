package net.tarantel.chickenroost.block.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
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
import net.tarantel.chickenroost.handler.Feeder_Handler;
import net.tarantel.chickenroost.util.WrappedHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class Feeder_Tile extends BlockEntity implements MenuProvider {

    public enum StackSendMode {
        SINGLE, HALF, FULL;

        public StackSendMode next() {
            return values()[(this.ordinal() + 1) % values().length];
        }

        public int limitFor(ItemStack stack) {
            int max = Math.max(1, stack.getMaxStackSize());
            return switch (this) {
                case SINGLE -> 1;
                case HALF -> Math.max(1, max / 2);
                case FULL -> max;
            };
        }

        public int id() {
            return this.ordinal();
        }

        public static StackSendMode byId(int id) {
            if (id < 0 || id >= values().length) return SINGLE;
            return values()[id];
        }
    }

    public final ItemStackHandler itemHandler = new ItemStackHandler(54) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (level != null && !level.isClientSide) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    private final Set<BlockPos> activeRoosts = new HashSet<>();
    private int feedRange = 10;

    private final Map<BlockPos, Item> preferredSeeds = new HashMap<>();

    // ===== Round Robin =====
    private boolean roundRobinEnabled = false;
    private int roundRobinIndex = 0;

    // ===== Stack send mode =====
    private StackSendMode stackSendMode = StackSendMode.SINGLE;

    private int _roostValidationTicker = 0;

    public Feeder_Tile(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FEEDER.get(), pos, state);
    }

    // ===== Range / roosts =====

    public int getFeedRange() {
        return this.feedRange;
    }

    public void setFeedRange(int r) {
        r = Math.max(5, Math.min(30, r));
        if (this.feedRange != r) {
            this.feedRange = r;
            setChanged();
            if (level != null && !level.isClientSide) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
            }
        }
    }

    public void addActiveRoost(BlockPos roost) {
        if (this.activeRoosts.add(roost.immutable())) {
            setChanged();
            if (level != null && !level.isClientSide) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
            }
        }
    }

    public void removeActiveRoost(BlockPos roost) {
        if (this.activeRoosts.remove(roost)) {
            setChanged();
            if (level != null && !level.isClientSide) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
            }
        }
    }

    public Set<BlockPos> getActiveRoosts() {
        return Collections.unmodifiableSet(activeRoosts);
    }

    // ===== Preferred seeds =====

    public void setPreferredSeed(BlockPos roostPos, @Nullable Item item) {
        if (item == null) {
            this.preferredSeeds.remove(roostPos);
        } else {
            this.preferredSeeds.put(roostPos.immutable(), item);
        }
        setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    @Nullable
    public Item getPreferredSeed(BlockPos roostPos) {
        return this.preferredSeeds.get(roostPos);
    }

    // ===== Round Robin API (this fixes your FeederScreen error) =====

    public boolean isRoundRobinEnabled() {
        return this.roundRobinEnabled;
    }

    public void setRoundRobinEnabled(boolean enabled) {
        if (this.roundRobinEnabled == enabled) return;

        this.roundRobinEnabled = enabled;
        this.roundRobinIndex = 0;

        setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    // ===== Stack mode API =====

    public StackSendMode getStackSendMode() {
        return this.stackSendMode;
    }

    public int getStackSendModeId() {
        return this.stackSendMode.id();
    }

    public void setStackSendMode(StackSendMode mode) {
        if (mode == null) mode = StackSendMode.SINGLE;
        if (this.stackSendMode == mode) return;

        this.stackSendMode = mode;

        setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public void setStackSendModeById(int id) {
        setStackSendMode(StackSendMode.byId(id));
    }

    // ===== NBT =====

    @Override
    protected void saveAdditional(@NotNull CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());

        long[] arr = this.activeRoosts.stream().mapToLong(BlockPos::asLong).toArray();
        nbt.putLongArray("feeder.roosts", arr);
        nbt.putInt("feeder.range", this.feedRange);

        // Round Robin
        nbt.putBoolean("feeder.round_robin", this.roundRobinEnabled);
        nbt.putInt("feeder.round_robin_index", this.roundRobinIndex);

        // Stack send mode
        nbt.putInt("feeder.stack_send_mode", this.stackSendMode.id());

        var list = new net.minecraft.nbt.ListTag();
        for (var e : this.preferredSeeds.entrySet()) {
            CompoundTag t = new CompoundTag();
            t.putLong("pos", e.getKey().asLong());
            ResourceLocation key = BuiltInRegistries.ITEM.getKey(e.getValue());
            t.putString("item", key.toString());
            list.add(t);
        }
        nbt.put("feeder.preferredSeeds", list);

        super.saveAdditional(nbt);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));

        this.preferredSeeds.clear();
        if (nbt.contains("feeder.preferredSeeds")) {
            var list = nbt.getList("feeder.preferredSeeds", net.minecraft.nbt.Tag.TAG_COMPOUND);
            for (int i = 0; i < list.size(); i++) {
                CompoundTag t = list.getCompound(i);
                BlockPos p = BlockPos.of(t.getLong("pos"));
                if (t.contains("item")) {
                    Item it = BuiltInRegistries.ITEM.get(new ResourceLocation(t.getString("item")));
                    this.preferredSeeds.put(p, it);
                }
            }
        }

        this.activeRoosts.clear();
        long[] arr = nbt.getLongArray("feeder.roosts");
        for (long l : arr) this.activeRoosts.add(BlockPos.of(l));

        this.feedRange = Math.max(5, Math.min(30, nbt.getInt("feeder.range")));

        // Round Robin
        this.roundRobinEnabled = nbt.getBoolean("feeder.round_robin");
        this.roundRobinIndex = nbt.getInt("feeder.round_robin_index");

        // Stack send mode
        this.stackSendMode = StackSendMode.byId(nbt.getInt("feeder.stack_send_mode"));

        pruneMissingRoosts();
    }

    // ===== Feeding logic =====

    private static void feedFromActiveRoosts(Level level, Feeder_Tile self) {
        if (self.activeRoosts.isEmpty()) return;

        final int r = self.getFeedRange();
        final int maxDist2 = r * r;

        // Build candidates
        ArrayList<BlockPos> candidates = new ArrayList<>();
        for (BlockPos rpos : self.activeRoosts) {
            if (rpos.distSqr(self.getBlockPos()) > (double) (maxDist2 * 3)) continue;
            BlockEntity be = level.getBlockEntity(rpos);
            if (be instanceof Roost_Tile
                    || be instanceof Breeder_Tile
                    || be instanceof Trainer_Tile) {

                candidates.add(rpos);
            }
        }

        if (candidates.isEmpty()) return;

        // Deterministic order (fast + version-safe)
        candidates.sort(Comparator.comparingLong(BlockPos::asLong));

        if (!self.roundRobinEnabled) {
            // Feed all roosts
            for (BlockPos rpos : candidates) {
                feedSingleRoost(level, self, rpos);
            }
        } else {
            // Feed exactly one roost per tick, rotate
            int idx = Math.floorMod(self.roundRobinIndex, candidates.size());
            BlockPos chosen = candidates.get(idx);

            feedSingleRoost(level, self, chosen);

            self.roundRobinIndex = (idx + 1) % candidates.size();
            self.setChanged();
        }
    }

    private static int getFeedTargetSlot(BlockEntity be) {
        if (be instanceof Roost_Tile) {
            return 0;
        }
        if (be instanceof Breeder_Tile) {
            return 1;
        }
        if (be instanceof Trainer_Tile) {
            return 1;
        }
        return -1;
    }


    private static void feedSingleRoost(Level level, Feeder_Tile self, BlockPos rpos) {
        BlockEntity be = level.getBlockEntity(rpos);
        if (be == null) return;

        int targetSlot = getFeedTargetSlot(be);
        if (targetSlot < 0) return;

        IItemHandler targetInv = be.getCapability(
                ForgeCapabilities.ITEM_HANDLER, null
        ).orElse(null);
        if (targetInv == null) return;

        ItemStack target = targetInv.getStackInSlot(targetSlot);
        Item desired = !target.isEmpty()
                ? target.getItem()
                : self.preferredSeeds.get(rpos);

        if (desired == null) return;

        // Simulate capacity
        ItemStack probe = new ItemStack(desired, desired.getMaxStackSize());
        ItemStack remainder = targetInv.insertItem(targetSlot, probe.copy(), true);
        int canAccept = probe.getCount()
                - (remainder.isEmpty() ? 0 : remainder.getCount());

        if (canAccept <= 0) return;

        // Apply stack mode cap
        int modeLimit = self.stackSendMode.limitFor(probe);
        int toMove = Math.min(canAccept, modeLimit);

        // Pull from feeder inventory
        for (int i = 0; i < self.itemHandler.getSlots() && toMove > 0; i++) {
            ItemStack src = self.itemHandler.getStackInSlot(i);
            if (src.isEmpty() || src.getItem() != desired) continue;

            int moveCount = Math.min(toMove, src.getCount());
            ItemStack extractedSim = self.itemHandler.extractItem(i, moveCount, true);
            if (extractedSim.isEmpty()) continue;

            ItemStack leftover = targetInv.insertItem(targetSlot, extractedSim, false);
            int moved = extractedSim.getCount()
                    - (leftover.isEmpty() ? 0 : leftover.getCount());

            if (moved > 0) {
                self.itemHandler.extractItem(i, moved, false);
                toMove -= moved;
            }
        }
    }

    // ===== Drops =====

    public void drops() {
        // Create a SimpleContainer to hold the items from the item handler
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        // Create an ItemStack for the block
        ItemStack itemStack = new ItemStack(ModBlocks.FEEDER.get());

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


    private boolean isValidFeedTarget(BlockPos pos) {
        if (this.level == null || pos == null) return false;

        BlockEntity be = this.level.getBlockEntity(pos);
        return be instanceof Roost_Tile
                || be instanceof Breeder_Tile
                || be instanceof Trainer_Tile;
    }


    private void pruneMissingRoosts() {
        if (this.level == null || activeRoosts.isEmpty()) return;
        activeRoosts.removeIf(pos -> !isValidFeedTarget(pos));
    }


    private void maybePruneRoostsPeriodic() {
        if ((++_roostValidationTicker % 20) == 0) {
            pruneMissingRoosts();
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




    // ===== Tick =====

    public static void tick(Level level, BlockPos pos, BlockState state, Feeder_Tile tile) {
        if (level.isClientSide()) return;
        tile.maybePruneRoostsPeriodic();
        feedFromActiveRoosts(level, tile);
        setChanged(level, pos, state);
    }

    // ===== Sync =====

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Nullable
    public IItemHandler getItemHandlerCapability(@Nullable Direction side) {
        return itemHandler;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("block.chicken_roost.feeder");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory inv, @NotNull Player player) {
        return new Feeder_Handler(id, inv, this);
    }
}
