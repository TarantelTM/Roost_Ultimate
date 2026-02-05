package net.tarantel.chickenroost.block.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.item.base.ChickenSeedBase;
import net.tarantel.chickenroost.item.base.RoostUltimateItem;
import net.tarantel.chickenroost.util.ModDataComponents;
import net.tarantel.chickenroost.util.RoostItemContainerContents;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class ChickenStorageTile extends BlockEntity {

    public final ItemStackHandler itemHandler = new ItemStackHandler(2147483) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            assert level != null;
            if(!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return (stack.getItem() instanceof RoostUltimateItem || stack.getItem() instanceof ChickenSeedBase);
        }
    };

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

        int integer = 0;
        ItemStack itemStack = new ItemStack(ModBlocks.CHICKENSTORAGE);
        NonNullList<ItemStack> items = inventory.getItems();
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            items.set(i, itemHandler.getStackInSlot(i));
            if(!itemHandler.getStackInSlot(i).isEmpty()){
                integer += itemHandler.getStackInSlot(i).getCount();
            }
        }
        itemStack.set(ModDataComponents.CONTAINER.value(), RoostItemContainerContents.fromItems(inventory.getItems()));
        itemStack.set(ModDataComponents.STORAGEAMOUNT.value(), integer);
        block.setItem(0, itemStack.copy());


        Containers.dropContents(Objects.requireNonNull(this.level), this.worldPosition, block);
    }
    public ChickenStorageTile(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CHICKENSTORAGE.get(), pos, state);
    }

    public void setHandler(ItemStackHandler itemStackHandler) {
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            itemHandler.setStackInSlot(i, itemStackHandler.getStackInSlot(i));
        }
    }

    @Override
    public void onLoad() {
        super.onLoad();
        setChanged();
    }
    private static @Nullable ResourceHandler<ItemResource> getItemHandler(Level level, BlockPos pos, @Nullable Direction side) {
        return level.getCapability(Capabilities.Item.BLOCK, pos, side);
    }

    public static final BlockCapability<IItemHandler, @Nullable Direction> ITEM_HANDLER_BLOCK =
            BlockCapability.create(
                    Identifier.fromNamespaceAndPath("chicken_roost", "item_handler"),
                    IItemHandler.class,
                    Direction.class);


    @Override
    protected void saveAdditional(ValueOutput out) {
        super.saveAdditional(out);
        itemHandler.serialize(out);

    }

    @Override
    protected void loadAdditional(ValueInput in) {
        super.loadAdditional(in);
        itemHandler.deserialize(in);
    }


    public static void tick(Level level, BlockPos pos, BlockState state, ChickenStorageTile pEntity) {
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

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider registries) {
        return new CompoundTag();
    }
}