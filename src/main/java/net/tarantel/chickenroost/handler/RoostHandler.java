package net.tarantel.chickenroost.handler;


import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.block.tile.RoostTile;
import net.tarantel.chickenroost.item.base.ChickenItemBase;
import net.tarantel.chickenroost.item.base.ChickenSeedBase;
import org.jetbrains.annotations.NotNull;


public class RoostHandler extends AbstractContainerMenu {

    public final RoostTile blockEntity;
    public final Level level;
    private final ContainerData data;



    public RoostHandler(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));

    }

    public RoostHandler(int id, Inventory inv, BlockEntity entity, ContainerData data) {
        super(ModHandlers.ROOST_MENU_V1.get(), id);
        checkContainerSize(inv, 3);
        blockEntity = (RoostTile) entity;
        this.level = inv.player.level();
        this.data = data;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        Container dummy = new DummyContainer(3);
        int c = 0;


        addSlot(new TransferSlot(level, dummy, c++, blockEntity.inventory, // ResourceHandler
                0, 11, 19,
                stack -> stack.getItem() instanceof ChickenSeedBase
        ));

        addSlot(new TransferSlot(level, dummy, c++, blockEntity.inventory, // ResourceHandler
                    1, 39, 19,
                    stack -> stack.getItem() instanceof ChickenItemBase
        ) {
            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });

        addSlot(new TransferSlot(level, dummy, c++, blockEntity.inventory, // ResourceHandler
                2, 120, 19,                  // GUI position
                stack -> false           // mayPlace-Regel
        ));


        addDataSlots(this.data);
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getProgress() {
        return this.data.get(0);
    }

    public int getMaxProgress() {
        return this.data.get(1);
    }
    public int getScaledProgress(int arrowWidth) {
        int progress = getProgress();
        int maxProgress = getMaxProgress();

        if (maxProgress == 0 || progress == 0) {
            return 0;
        }

        return progress * arrowWidth / maxProgress;
    }

    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    private static final int TE_INVENTORY_SLOT_COUNT = 3;

    private static final int PLAYER_START = 0;
    private static final int PLAYER_END = 36;

    private static final int MACHINE_START = 36;
    private static final int MACHINE_END = MACHINE_START + TE_INVENTORY_SLOT_COUNT;

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {

        Slot slot = slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;

        ItemStack stack = slot.getItem();
        ItemStack copy = stack.copy();

        boolean fromPlayer  = index < PLAYER_END;
        boolean fromMachine = index >= MACHINE_START && index < MACHINE_END;

        // PLAYER -> MACHINE (nur Slots 0,1,2)
        if (fromPlayer) {
            try (Transaction tx = Transaction.openRoot()) {

                ItemResource res = ItemResource.of(stack);
                int remaining = stack.getCount();

                for (int s = 0; s <= 1 && remaining > 0; s++) {
                    remaining -= blockEntity.inventory.insert(s, res, remaining, tx);
                }

                int moved = stack.getCount() - remaining;
                if (moved <= 0) return ItemStack.EMPTY;

                stack.shrink(moved);
                tx.commit();
            }

            slot.setChanged();
            return copy;
        }

        // MACHINE -> PLAYER (alle Slots 0–11)
        if (fromMachine) {

            int machineSlot = index - MACHINE_START;

            // ✅ WICHTIG: exakte Slot-Resource holen
            ItemResource res = blockEntity.inventory.getResource(machineSlot);
            int amount = blockEntity.inventory.getAmountAsInt(machineSlot);

            if (res.isEmpty() || amount <= 0) return ItemStack.EMPTY;

            int extracted;
            try (Transaction tx = Transaction.openRoot()) {
                extracted = blockEntity.inventory.extract(machineSlot, res, amount, tx);
                if (extracted <= 0) return ItemStack.EMPTY;
                tx.commit();
            }

            ItemStack toGive = res.toStack(extracted);
            player.getInventory().placeItemBackInInventory(toGive);

            slot.setChanged();
            return copy;
        }

        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, ModBlocks.ROOST.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    public RoostTile getBlockEntity() {
        return blockEntity;
    }
}