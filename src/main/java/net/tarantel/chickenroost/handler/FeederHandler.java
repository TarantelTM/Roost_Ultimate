package net.tarantel.chickenroost.handler;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.block.tile.FeederTile;
import net.tarantel.chickenroost.networking.ModNetworking;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FeederHandler extends AbstractContainerMenu {

    private static final int FEEDER_SLOTS = 54;
    private static final int PREFERRED_ROWS = 6;

    private final Level level;
    private final FeederTile blockEntity;

    /* UI STATE */
    private boolean uiBlockFeederSlots = false;
    private final List<BlockPos> visibleTargets = new ArrayList<>();

    /* Preferred seed slots (fixe Instanzen!) */
    private final List<PreferredSeedSlot> preferredSlots = new ArrayList<>();

    /* Slot layout (GUI-lokal!) */
    private int slotBaseX;
    private int slotBaseY;
    private int slotLineH;

    /* ================= CONSTRUCTORS ================= */

    public FeederHandler(int id, Inventory inv, FriendlyByteBuf buf) {
        this(id, inv, inv.player.level().getBlockEntity(buf.readBlockPos()));
    }

    public FeederHandler(int id, Inventory playerInventory, BlockEntity be) {
        super(ModHandlers.FEEDER_MENU.get(), id);

        this.level = playerInventory.player.level();
        this.blockEntity = (FeederTile) be;

        /* ---------- PREFERRED SEED SLOTS (IMMER EXISTENT) ---------- */
        for (int i = 0; i < PREFERRED_ROWS; i++) {
            final int row = i;

            PreferredSeedSlot slot = new PreferredSeedSlot(
                    blockEntity,
                    () -> row < visibleTargets.size() ? visibleTargets.get(row) : null,
                    0,
                    0
            );

            preferredSlots.add(slot);
            addSlot(slot);
        }

        /* ---------- FEEDER INVENTORY ---------- */
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 9; c++) {
                addSlot(new SlotItemHandler(
                        blockEntity.itemHandler,
                        c + r * 9,
                        8 + c * 18,
                        18 + r * 18
                ) {
                    @Override
                    public boolean isActive() {
                        return !uiBlockFeederSlots;
                    }

                    @Override
                    public boolean mayPickup(@NotNull Player player) {
                        return !uiBlockFeederSlots;
                    }

                    @Override
                    public boolean mayPlace(@NotNull ItemStack stack) {
                        return !uiBlockFeederSlots;
                    }
                });
            }
        }

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

        if (!level.isClientSide() && playerInventory.player instanceof ServerPlayer sp) {
            ModNetworking.sendFeederTargets(sp, blockEntity);
        }
    }

    /* ================= API FÜR SCREEN ================= */

    public void setUiBlockFeederSlots(boolean block) {
        this.uiBlockFeederSlots = block;
    }

    public void setPreferredSlotBasePosition(int x, int y, int lineH) {
        this.slotBaseX = x;
        this.slotBaseY = y;
        this.slotLineH = lineH;
        //updatePreferredSlotPositions();
    }

    public void setVisibleTargets(List<BlockPos> targets) {
        if (!uiBlockFeederSlots) return;
        if (visibleTargets.equals(targets)) return;

        visibleTargets.clear();
        visibleTargets.addAll(targets);

    }

    /*private void updatePreferredSlotPositions() {
        for (int i = 0; i < 6; i++) {
            final int row = i;

            PreferredSeedSlot slot = new PreferredSeedSlot(
                    blockEntity,
                    () -> row < visibleTargets.size() ? visibleTargets.get(row) : null,
                    FIXED_X,
                    FIXED_Y + i * LINE_H
            );

            addSlot(slot);
        }

    }*/


    public void initPreferredSlots(int baseX, int baseY, int lineH) {
        if (!preferredSlots.isEmpty()) return;

        for (int row = 0; row < 6; row++) {
            final int index = row;

            PreferredSeedSlot slot = new PreferredSeedSlot(
                    blockEntity,
                    () -> index < visibleTargets.size()
                            ? visibleTargets.get(index)
                            : null,
                    baseX,
                    baseY + index * lineH
            );

            preferredSlots.add(slot);
            addSlot(slot);
        }
    }


    public FeederTile getBlockEntity() {
        return blockEntity;
    }

    /* ================= SHIFT CLICK ================= */

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        if (uiBlockFeederSlots) return ItemStack.EMPTY;

        Slot slot = slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;

        ItemStack stack = slot.getItem();
        ItemStack copy = stack.copy();

        if (index < FEEDER_SLOTS) {
            if (!moveItemStackTo(stack, FEEDER_SLOTS, slots.size(), true)) {
                return ItemStack.EMPTY;
            }
        } else {
            if (!moveItemStackTo(stack, 0, FEEDER_SLOTS, false)) {
                return ItemStack.EMPTY;
            }
        }

        if (stack.isEmpty()) slot.set(ItemStack.EMPTY);
        else slot.setChanged();

        return copy;
    }

    /* ================= VALIDATION ================= */

    @Override
    public boolean stillValid(@NotNull Player player) {
        return stillValid(
                ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player,
                ModBlocks.FEEDER.get()
        );
    }

    /* ================= PLAYER INVENTORY ================= */

    private void addPlayerInventory(Inventory inv) {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 9; c++) {
                addSlot(new Slot(inv, c + r * 9 + 9,
                        8 + c * 18,
                        139 + r * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory inv) {
        for (int i = 0; i < 9; i++) {
            addSlot(new Slot(inv, i,
                    8 + i * 18,
                    197));
        }
    }
}
