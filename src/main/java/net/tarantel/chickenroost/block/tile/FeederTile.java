package net.tarantel.chickenroost.block.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.block.blocks.RoostBlock;
import net.tarantel.chickenroost.handler.FeederHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class FeederTile extends BlockEntity implements MenuProvider {

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


    public FeederTile(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FEEDER.get(), pos, state);
    }


    public int getFeedRange() { return this.feedRange; }
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


    @Override
    public void saveAdditional(CompoundTag nbt, HolderLookup.@NotNull Provider lookup) {
        nbt.put("inventory", itemHandler.serializeNBT(lookup));

        long[] arr = this.activeRoosts.stream().mapToLong(BlockPos::asLong).toArray();
        nbt.putLongArray("feeder.roosts", arr);
        nbt.putInt("feeder.range", this.feedRange);


        var list = new net.minecraft.nbt.ListTag();
        for (var e : this.preferredSeeds.entrySet()) {
            CompoundTag t = new CompoundTag();
            t.putLong("pos", e.getKey().asLong());
            ResourceLocation key = BuiltInRegistries.ITEM.getKey(e.getValue());
            t.putString("item", key.toString());
            list.add(t);
        }
        nbt.put("feeder.preferredSeeds", list);

        super.saveAdditional(nbt, lookup);
    }

    @Override
    public void loadAdditional(@NotNull CompoundTag nbt, HolderLookup.@NotNull Provider lookup) {
        super.loadAdditional(nbt, lookup);
        itemHandler.deserializeNBT(lookup, nbt.getCompound("inventory"));


        this.preferredSeeds.clear();
        if (nbt.contains("feeder.preferredSeeds")) {
            var list = nbt.getList("feeder.preferredSeeds", net.minecraft.nbt.Tag.TAG_COMPOUND);
            for (int i = 0; i < list.size(); i++) {
                CompoundTag t = list.getCompound(i);
                BlockPos p = BlockPos.of(t.getLong("pos"));
                if (t.contains("item")) {
                    Item it = BuiltInRegistries.ITEM.get(ResourceLocation.parse(t.getString("item")));
                    this.preferredSeeds.put(p, it);
                }
            }
        }

        this.activeRoosts.clear();
        long[] arr = nbt.getLongArray("feeder.roosts");
        for (long l : arr) this.activeRoosts.add(BlockPos.of(l));

        this.feedRange = Math.max(5, Math.min(30, nbt.getInt("feeder.range")));
        pruneMissingRoosts();
    }


    private static void feedFromActiveRoosts(Level level, FeederTile self) {
        if (self.activeRoosts.isEmpty()) return;
        final int r = self.getFeedRange();
        final int maxDist2 = r * r;

        for (BlockPos rpos : new HashSet<>(self.activeRoosts)) {
            if (rpos.distSqr(self.getBlockPos()) > (double) (maxDist2 * 3)) {
                continue;
            }
            BlockEntity be = level.getBlockEntity(rpos);
            if(be instanceof RoostTile) {
            IItemHandler roostInv = level.getCapability(Capabilities.ItemHandler.BLOCK, rpos, null);
            if (roostInv == null) continue;


            ItemStack target = roostInv.getStackInSlot(0);
            Item desired = !target.isEmpty() ? target.getItem() : self.preferredSeeds.get(rpos);
            if (desired == null) continue;


            ItemStack probe = new ItemStack(desired, desired.getDefaultMaxStackSize());
            ItemStack remainder = roostInv.insertItem(0, probe.copy(), true);
            int canAccept = probe.getCount() - (remainder.isEmpty() ? 0 : remainder.getCount());
            if (canAccept <= 0) continue;

            int toMove = canAccept;
            for (int i = 0; i < self.itemHandler.getSlots() && toMove > 0; i++) {
                ItemStack src = self.itemHandler.getStackInSlot(i);
                if (src.isEmpty() || src.getItem() != desired) continue;

                int moveCount = Math.min(toMove, src.getCount());
                ItemStack extractedSim = self.itemHandler.extractItem(i, moveCount, true);
                if (extractedSim.isEmpty()) continue;

                ItemStack leftover = roostInv.insertItem(0, extractedSim, false);
                int moved = extractedSim.getCount() - (leftover.isEmpty() ? 0 : leftover.getCount());
                if (moved > 0) {
                    self.itemHandler.extractItem(i, moved, false);
                    toMove -= moved;
                }
            }
        }
        }
    }


    public void drops() {
        SimpleContainer block = new SimpleContainer(1);
        ItemStack itemStack = new ItemStack(ModBlocks.FEEDER.get());
        NonNullList<ItemStack> items = NonNullList.withSize(itemHandler.getSlots(), ItemStack.EMPTY);
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            items.set(i, itemHandler.getStackInSlot(i));
        }
        itemStack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(items));
        block.setItem(0, itemStack.copy());
        Containers.dropContents(Objects.requireNonNull(this.level), this.worldPosition, block);
    }


    private int _roostValidationTicker = 0;


    private boolean isValidRoost(BlockPos pos) {
        if (this.level == null || pos == null) return false;

        BlockEntity be = this.level.getBlockEntity(pos);
        if (!(be instanceof RoostTile)) return false;

        BlockState state = this.level.getBlockState(pos);
        return state.getBlock() instanceof RoostBlock;
    }

    private void pruneMissingRoosts() {
        if (this.level == null || activeRoosts == null || activeRoosts.isEmpty()) return;
        activeRoosts.removeIf(pos -> !isValidRoost(pos));
    }


    private void maybePruneRoostsPeriodic() {
        if ((++_roostValidationTicker % 20) == 0) {
            pruneMissingRoosts();
        }
    }



    public static void tick(Level level, BlockPos pos, BlockState state, FeederTile tile) {
        if (level.isClientSide()) return;
        tile.maybePruneRoostsPeriodic();
        feedFromActiveRoosts(level, tile);
        setChanged(level, pos, state);
    }


    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider prov) {
        return saveWithFullMetadata(prov);
    }

    @Nullable
    public net.neoforged.neoforge.items.IItemHandler getItemHandlerCapability(@Nullable Direction side) {
        return itemHandler;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("block.chicken_roost.feeder");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory inv, @NotNull Player player) {
        return new FeederHandler(id, inv, this);
    }
}
