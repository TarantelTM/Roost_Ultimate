package net.tarantel.chickenroost.handler;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.tarantel.chickenroost.block.tile.FeederTile;
import org.jetbrains.annotations.NotNull;

public class FeederHandler extends AbstractContainerMenu {

    private static final int CONTAINER_SLOTS = 54;
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INV_ROW_COUNT = 3;
    private static final int PLAYER_INV_COLUMN_COUNT = 9;

    private final Level level;
    private final BlockEntity blockEntity;

    private boolean uiBlockFeederSlots = false;

    public FeederHandler(int id, Inventory playerInv, FriendlyByteBuf buf) {
        this(id, playerInv, playerInv.player.level().getBlockEntity(buf.readBlockPos()));
    }

    public FeederHandler(int id, Inventory playerInventory, BlockEntity be) {
        super(ModHandlers.FEEDER_MENU.get(), id);
        this.level = playerInventory.player.level();
        this.blockEntity = be;

        if (be instanceof FeederTile tile) {
            var itemHandler = tile.itemHandler;


            for (int j = 0; j < 6; j++) {
                for (int k = 0; k < 9; k++) {
                    final int slotIndex = k + j * 9;
                    addSlot(new SlotItemHandler(itemHandler, slotIndex, 8 + k * 18, 18 + j * 18) {
                        @Override public boolean isActive() {
                            return !uiBlockFeederSlots;
                        }
                        @Override public boolean mayPickup(@NotNull Player player) {
                            return !uiBlockFeederSlots && super.mayPickup(player);
                        }
                        @Override public boolean mayPlace(@NotNull ItemStack stack) {
                            return !uiBlockFeederSlots && super.mayPlace(stack);
                        }
                    });
                }
            }
        }


        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
    }


    public void setUiBlockFeederSlots(boolean block) { this.uiBlockFeederSlots = block; }


    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            if (uiBlockFeederSlots && index < CONTAINER_SLOTS) {

                return ItemStack.EMPTY;
            }

            ItemStack stack = slot.getItem();
            itemstack = stack.copy();
            int containerSlots = CONTAINER_SLOTS;

            if (index < containerSlots) {
                if (!this.moveItemStackTo(stack, containerSlots, containerSlots + 36, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(stack, 0, containerSlots, false)) {
                return ItemStack.EMPTY;
            }

            if (stack.isEmpty()) slot.set(ItemStack.EMPTY);
            else slot.setChanged();
        }
        return itemstack;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return this.level.getBlockEntity(this.blockEntity.getBlockPos()) == this.blockEntity &&
                player.distanceToSqr(
                        this.blockEntity.getBlockPos().getX() + 0.5D,
                        this.blockEntity.getBlockPos().getY() + 0.5D,
                        this.blockEntity.getBlockPos().getZ() + 0.5D
                ) <= 64.0D;
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < PLAYER_INV_ROW_COUNT; ++i) {
            for (int l = 0; l < PLAYER_INV_COLUMN_COUNT; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 139 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < HOTBAR_SLOT_COUNT; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 197));
        }
    }

    public BlockEntity getBlockEntity() { return blockEntity; }
}
