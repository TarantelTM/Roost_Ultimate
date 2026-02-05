package net.tarantel.chickenroost.block.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.handler.FeederHandler;
import net.tarantel.chickenroost.networking.ModNetworking;
import net.tarantel.chickenroost.util.Config;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class FeederTile extends BlockEntity implements MenuProvider {


    private boolean dropped = false;

    public boolean hasDropped() {
        return dropped;
    }

    public void markDropped() {
        this.dropped = true;
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        SimpleContainer block = new SimpleContainer(1);
        ItemStack itemStack = new ItemStack(ModBlocks.FEEDER);
        NonNullList<ItemStack> items = inventory.getItems();
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            items.set(i, itemHandler.getStackInSlot(i));
        }
        itemStack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(inventory.getItems()));
        block.setItem(0, itemStack.copy());


        Containers.dropContents(Objects.requireNonNull(this.level), this.worldPosition, block);
    }

    /* ======================= ENUM ======================= */

    public enum StackSendMode {
        SINGLE, HALF, FULL;

        public int limitFor(ItemStack stack) {
            int max = Math.max(1, stack.getMaxStackSize());
            return switch (this) {
                case SINGLE -> 1;
                case HALF -> Math.max(1, max / 2);
                case FULL -> max;
            };
        }

        public int id() {
            return ordinal();
        }

        public static StackSendMode byId(int id) {
            if (id < 0 || id >= values().length) return SINGLE;
            return values()[id];
        }
    }

    /* ======================= INVENTORY ======================= */

    public final ItemStackHandler itemHandler = new ItemStackHandler(54) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            /*if (level instanceof ServerLevel sl) {
                sl.getChunkSource().blockChanged(worldPosition);
            }*/
        }
    };

    /* ======================= STATE ======================= */

    private final Set<BlockPos> activeRoosts = new HashSet<>();
    private final Map<BlockPos, Item> preferredSeeds = new HashMap<>();

    private int feedRange = 10;
    private boolean roundRobinEnabled = false;
    private int roundRobinIndex = 0;
    private StackSendMode stackSendMode = StackSendMode.SINGLE;

    private int roostValidationTicker = 0;

    public FeederTile(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FEEDER.get(), pos, state);
    }

    /* ======================= GETTERS ======================= */

    public int getFeedRange() {
        return feedRange;
    }

    public boolean isRoundRobinEnabled() {
        return roundRobinEnabled;
    }

    public int getStackSendModeId() {
        return stackSendMode.id();
    }

    public Set<BlockPos> getActiveRoosts() {
        return Collections.unmodifiableSet(activeRoosts);
    }

    @Nullable
    public Item getPreferredSeed(BlockPos pos) {
        return preferredSeeds.get(pos);
    }

    /* ======================= SETTERS ======================= */

    public void setFeedRange(int r) {
        r = Math.max(5, Math.min(Config.feederrange.get(), r));
        if (feedRange != r) {
            feedRange = r;
            markDirtyAndSync();
        }
    }

    public void setRoundRobinEnabled(boolean enabled) {
        if (roundRobinEnabled != enabled) {
            roundRobinEnabled = enabled;
            roundRobinIndex = 0;
            markDirtyAndSync();
        }
    }

    public void setStackSendModeById(int id) {
        StackSendMode next = StackSendMode.byId(id);
        if (stackSendMode != next) {
            stackSendMode = next;
            markDirtyAndSync();
        }
    }

    public void addActiveRoost(BlockPos pos) {
        if (activeRoosts.add(pos.immutable())) {
            markDirtyAndSync();
        }
    }

    public void removeActiveRoost(BlockPos pos) {
        BlockPos key = pos.immutable();
        boolean changed = activeRoosts.remove(key);
        preferredSeeds.remove(key);
        if (changed) {
            markDirtyAndSync();
        }
    }

    /**
     * 🔴 WICHTIG: synchronisiert korrekt Client ↔ Server
     */
    public void setPreferredSeed(BlockPos roostPos, @Nullable Item item) {
        BlockPos key = roostPos.immutable();

        boolean changed;
        if (item == null) {
            changed = preferredSeeds.remove(key) != null;
        } else {
            Item prev = preferredSeeds.put(key, item);
            changed = prev != item;
        }

        if (changed) {
            markDirtyAndSync();
        }
    }

    /* ======================= NBT ======================= */

    @Override
    protected void saveAdditional(ValueOutput out) {
        super.saveAdditional(out);

        itemHandler.serialize(out);

        var roostList = out.list("feeder.roosts", BlockPos.CODEC);
        activeRoosts.forEach(roostList::add);

        out.putInt("feeder.range", feedRange);
        out.putBoolean("feeder.round_robin", roundRobinEnabled);
        out.putInt("feeder.round_robin_index", roundRobinIndex);
        out.putInt("feeder.stack_send_mode", stackSendMode.id());

        var seedList = out.childrenList("feeder.preferredSeeds");
        for (var entry : preferredSeeds.entrySet()) {
            var child = seedList.addChild();
            child.store("pos", BlockPos.CODEC, entry.getKey());
            child.store("item", Item.CODEC,
                    BuiltInRegistries.ITEM.wrapAsHolder(entry.getValue()));
        }
    }

    @Override
    protected void loadAdditional(ValueInput in) {
        super.loadAdditional(in);

        itemHandler.deserialize(in);

        activeRoosts.clear();
        preferredSeeds.clear();

        in.listOrEmpty("feeder.roosts", BlockPos.CODEC)
                .forEach(activeRoosts::add);

        in.childrenListOrEmpty("feeder.preferredSeeds").forEach(child -> {
            var pos = child.read("pos", BlockPos.CODEC);
            var item = child.read("item", Item.CODEC);
            if (pos.isPresent() && item.isPresent()) {
                preferredSeeds.put(pos.get(), item.get().value());
            }
        });

        feedRange = Math.max(5,
                Math.min(Config.feederrange.get(),
                        in.getIntOr("feeder.range", 5)));

        roundRobinEnabled = in.getBooleanOr("feeder.round_robin", false);
        roundRobinIndex = in.getIntOr("feeder.round_robin_index", 0);
        stackSendMode = StackSendMode.byId(
                in.getIntOr("feeder.stack_send_mode", 0));

        pruneMissingRoosts();
    }

    /* ======================= FEED LOGIC ======================= */

    private static void feedFromActiveRoosts(Level level, FeederTile self) {
        if (self.activeRoosts.isEmpty()) return;

        List<BlockPos> targets = new ArrayList<>();
        int r2 = self.feedRange * self.feedRange;

        for (BlockPos pos : self.activeRoosts) {
            if (pos.distSqr(self.worldPosition) <= r2 * 3) {
                BlockEntity be = level.getBlockEntity(pos);
                if (be instanceof RoostTile
                        || be instanceof BreederTile
                        || be instanceof TrainerTile) {
                    targets.add(pos);
                }
            }
        }

        if (targets.isEmpty()) return;

        targets.sort(Comparator.comparingLong(BlockPos::asLong));

        if (!self.roundRobinEnabled) {
            targets.forEach(p -> feedSingle(level, self, p));
        } else {
            BlockPos p = targets.get(self.roundRobinIndex % targets.size());
            feedSingle(level, self, p);
            self.roundRobinIndex = (self.roundRobinIndex + 1) % targets.size();
        }
    }

    private static void feedSingle(Level level, FeederTile self, BlockPos pos) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be == null) return;

        int slot = (be instanceof RoostTile) ? 0 : 1;
        IItemHandler inv = (IItemHandler) level.getCapability(
                Capabilities.Item.BLOCK, pos, null);
        if (inv == null) return;

        Item desired = self.preferredSeeds.get(pos);
        if (desired == null) return;

        int toMove = self.stackSendMode.limitFor(new ItemStack(desired));

        for (int i = 0; i < self.itemHandler.getSlots() && toMove > 0; i++) {
            ItemStack src = self.itemHandler.getStackInSlot(i);
            if (src.isEmpty() || src.getItem() != desired) continue;

            int moved = Math.min(toMove, src.getCount());
            ItemStack rem = inv.insertItem(slot,
                    self.itemHandler.extractItem(i, moved, false), false);
            toMove -= moved - rem.getCount();
        }
    }

    /* ======================= HELPERS ======================= */

    private void pruneMissingRoosts() {
        if (level == null) return;

        activeRoosts.removeIf(pos -> {
            boolean valid = level.getBlockEntity(pos) instanceof RoostTile
                    || level.getBlockEntity(pos) instanceof BreederTile
                    || level.getBlockEntity(pos) instanceof TrainerTile;

            if (!valid) preferredSeeds.remove(pos);
            return !valid;
        });
    }

    private void markDirtyAndSync() {
        setChanged();
        if (level != null && !level.isClientSide()) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }


    public static void tick(Level level, BlockPos pos, BlockState state, FeederTile tile) {
        if (level.isClientSide()) return;
        if (++tile.roostValidationTicker % 20 == 0) tile.pruneMissingRoosts();
        feedFromActiveRoosts(level, tile);
    }

    /* ======================= MENU ======================= */

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("block.chicken_roost.feeder");
    }

    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory inv, @NotNull Player player) {
        return new FeederHandler(id, inv, this);
    }

    // ⚠️ CLIENT UI HELFER
    // ⚠️ NUR clientseitig benutzen
    /*public Set<BlockPos> getActiveRoostsInternal() {
        return activeRoosts;
    }*/


    // ===== CLIENT UI CACHE =====
    private final Set<BlockPos> clientActiveRoosts = new HashSet<>();

    // NUR Client
    public boolean isRoostActive(BlockPos pos) {
        return clientActiveRoosts.contains(pos);
    }

    // NUR Client
    public Set<BlockPos> getClientActiveRoosts() {
        return clientActiveRoosts;
    }

    // Wird vom Netzwerk-Packet aufgerufen
    public void setClientActiveRoosts(List<BlockPos> targets) {
        clientActiveRoosts.clear();
        clientActiveRoosts.addAll(targets);
    }

    public void onTargetsChanged() {
        if (level instanceof ServerLevel sl) {
            for (ServerPlayer sp : sl.getPlayers(p -> true)) {
                ModNetworking.sendFeederTargets(sp, this);
            }
        }
    }

}
