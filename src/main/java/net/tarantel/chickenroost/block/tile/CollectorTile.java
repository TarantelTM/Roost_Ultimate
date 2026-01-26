package net.tarantel.chickenroost.block.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.block.blocks.RoostBlock;
import net.tarantel.chickenroost.handler.CollectorHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class CollectorTile extends BlockEntity implements MenuProvider {

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


    public CollectorTile(BlockPos pos, BlockState state) {
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


    private boolean isValidCollectorTarget(BlockPos pos) {
        if (this.level == null || pos == null) return false;

        BlockEntity be = this.level.getBlockEntity(pos);
        if (be == null) return false;

        return be instanceof RoostTile
                || be instanceof BreederTile
                || be instanceof SoulExtractorTile;
    }

    private void pruneMissingCollectorTargets() {
        if (this.level == null || activeRoosts.isEmpty()) return;

        activeRoosts.removeIf(pos -> !isValidCollectorTarget(pos));
    }


    private void maybePruneCollectorTargetsPeriodic() {

        if ((++_roostValidationTicker % 20) == 0) {
            pruneMissingCollectorTargets();
        }
    }




    @Override
    public void saveAdditional(CompoundTag nbt, HolderLookup.@NotNull Provider lookup) {
        nbt.put("inventory", itemHandler.serializeNBT(lookup));

        long[] arr = this.activeRoosts.stream().mapToLong(BlockPos::asLong).toArray();
        nbt.putLongArray("collector.roosts", arr);
        nbt.putInt("collector.range", this.collectRange);
        super.saveAdditional(nbt, lookup);
    }

    @Override
    public void loadAdditional(@NotNull CompoundTag nbt, HolderLookup.@NotNull Provider lookup) {
        super.loadAdditional(nbt, lookup);
        itemHandler.deserializeNBT(lookup, nbt.getCompound("inventory"));

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
    private static void collectFromActiveRoosts(Level level, CollectorTile self) {
        if (self.activeRoosts.isEmpty()) return;

        for (BlockPos pos : new HashSet<>(self.activeRoosts)) {

            BlockEntity be = level.getBlockEntity(pos);
            if (be == null) continue;

            IItemHandler sourceInv =
                    level.getCapability(Capabilities.ItemHandler.BLOCK, pos, null);

            if (sourceInv == null) continue;

            if (be instanceof RoostTile) {
                pullFromSlot(sourceInv, self.itemHandler, 2);
            }

            else if (be instanceof SoulExtractorTile) {
                pullFromSlot(sourceInv, self.itemHandler, 1);
            }

            else if (be instanceof BreederTile) {
                int[] breederSlots = {
                        3, 4, 5, 6, 7, 8, 9, 10, 11
                };
                pullFromMultipleSlots(sourceInv, self.itemHandler, breederSlots);
            }
        }
    }


    public @Nullable IItemHandler getItemHandlerCapability(@Nullable Direction side) {

        return itemHandler;
    }
    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        SimpleContainer block = new SimpleContainer(1);

        ItemStack itemStack = new ItemStack(ModBlocks.COLLECTOR.get());
        NonNullList<ItemStack> items = inventory.getItems();
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            items.set(i, itemHandler.getStackInSlot(i));
        }
        itemStack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(inventory.getItems()));
        block.setItem(0, itemStack.copy());

        Containers.dropContents(Objects.requireNonNull(this.level), this.worldPosition, block);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, CollectorTile tile) {
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
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider prov) {
        return saveWithFullMetadata(prov);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("block.chicken_roost.collector");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory inv, @NotNull Player player) {
        return new CollectorHandler(id, inv, this);
    }
}
