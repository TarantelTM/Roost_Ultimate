package net.tarantel.chickenroost.block.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.tarantel.chickenroost.block.blocks.Breeder_Block;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.item.base.ChickenItemBase;
import net.tarantel.chickenroost.item.base.RoostUltimateItem;
import net.tarantel.chickenroost.network.BreederItemStackSyncS2CPacket;
import net.tarantel.chickenroost.network.ModMessages;

import net.tarantel.chickenroost.util.WrappedHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ChickenStorage_Tile extends BlockEntity {

    public final ItemStackHandler itemHandler = new ItemStackHandler(2147483) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                default -> (stack.getItem() instanceof RoostUltimateItem);
            };
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    public void setHandler(ItemStackHandler itemStackHandler) {
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            itemHandler.setStackInSlot(i, itemStackHandler.getStackInSlot(i));
        }
    }
    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        SimpleContainer block = new SimpleContainer(1);

        int totalItems = 0;
        ItemStack itemStack = new ItemStack(ModBlocks.CHICKENSTORAGE.get());

        // Copy items from the item handler to the inventory
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            ItemStack slotStack = itemHandler.getStackInSlot(i);
            inventory.setItem(i, slotStack);
            if (!slotStack.isEmpty()) {
                totalItems += slotStack.getCount();
            }
        }

        // Serialize the inventory into an NBT tag
        CompoundTag tag = new CompoundTag();
        ListTag itemsList = new ListTag();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty()) {
                CompoundTag itemTag = new CompoundTag();
                itemTag.putInt("Slot", i); // Store the slot index
                stack.save(itemTag); // Save the item stack to NBT
                itemsList.add(itemTag);
            }
        }
        tag.put("Items", itemsList); // Add the list of items to the NBT tag
        tag.putInt("StorageAmount", totalItems); // Save total item count to NBT

        // Set the NBT tag on the item stack
        itemStack.setTag(tag);

        // Add the item stack to the block container
        block.setItem(0, itemStack.copy());

        // Drop the contents in the world
        if (this.level != null) {
            Containers.dropContents(this.level, this.worldPosition, block);
        }
    }
    public ChickenStorage_Tile(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CHICKENSTORAGE.get(), pos, state);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        if(!level.isClientSide()) {
            ModMessages.sendToClients(new BreederItemStackSyncS2CPacket(this.itemHandler, worldPosition));
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
        super.saveAdditional(nbt);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
    }

    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap = new HashMap<>();

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            // If side is null, return the main item handler
            if (side == null) {
                return lazyItemHandler.cast();
            }

            // Create a wrapped handler for the given side if it doesn't exist
            if (!directionWrappedHandlerMap.containsKey(side)) {
                directionWrappedHandlerMap.put(side, LazyOptional.of(() -> new WrappedHandler(itemHandler,
                        (index) -> true, // Allow extraction from any slot
                        (index, stack) -> true // Allow insertion into any slot
                )));
            }

            // Return the wrapped handler for the given side
            return directionWrappedHandlerMap.get(side).cast();
        }

        return super.getCapability(cap, side);
    }
    public static void tick(Level level, BlockPos pos, BlockState state, ChickenStorage_Tile pEntity) {
        if(level.isClientSide()) {
            return;
        }
        setChanged(level, pos, state);
    }
    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

}