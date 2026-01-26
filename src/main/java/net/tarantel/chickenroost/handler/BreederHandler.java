package net.tarantel.chickenroost.handler;


import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.tarantel.chickenroost.block.tile.BreederTile;
import net.tarantel.chickenroost.item.base.*;
import net.tarantel.chickenroost.util.Config;
import org.jetbrains.annotations.NotNull;

public class BreederHandler extends AbstractContainerMenu {

    public final BreederTile blockEntity;
    public final Level level;
    private final ContainerData data;
    public int x, y, z;

    public BreederHandler(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
    }

    public BreederHandler(int id, Inventory inv, BlockEntity entity, ContainerData data) {
        super(ModHandlers.BREEDER_MENU.get(), id);
        checkContainerSize(inv, 12);
        blockEntity = (BreederTile) entity;
        this.level = inv.player.level();
        this.data = data;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);



        ItemCapabilityMenuHelper.getCapabilityItemHandler(this.level, this.blockEntity).ifPresent(itemHandler -> {
            this.addSlot(new SlotItemHandler(itemHandler, 1, 24, 43){
                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {
                    return (stack.getItem() instanceof ChickenSeedBase);
                }
            });

            this.addSlot(new SlotItemHandler(itemHandler, 0, 11, 19){
                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {
                    return (stack.getItem() instanceof ChickenItemBase);
                }
            });
            this.addSlot(new SlotItemHandler(itemHandler, 2, 39, 19){
                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {
                    return (stack.getItem() instanceof ChickenItemBase);
                }
            });

            this.addSlot(new SlotItemHandler(itemHandler, 3, 100, 7){
                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {
                    return false;
                }
            });
            this.addSlot(new SlotItemHandler(itemHandler, 4, 126, 7){
                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {
                    return false;
                }
            });
            this.addSlot(new SlotItemHandler(itemHandler, 5, 153, 7){
                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {
                    return false;
                }
            });
            this.addSlot(new SlotItemHandler(itemHandler, 6, 100, 30){
                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {
                    return false;
                }
            });
            this.addSlot(new SlotItemHandler(itemHandler, 7, 126, 30){
                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {
                    return false;
                }
            });
            this.addSlot(new SlotItemHandler(itemHandler, 8, 153, 30){
                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {
                    return false;
                }
            });
            this.addSlot(new SlotItemHandler(itemHandler, 9, 100, 54){
                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {
                    return false;
                }
            });
            this.addSlot(new SlotItemHandler(itemHandler, 10, 126, 54){
                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {
                    return false;
                }
            });
            this.addSlot(new SlotItemHandler(itemHandler, 11, 153, 54){
                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {
                    return false;
                }
            });


        });
        addDataSlots(data);
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

    private static final int TE_INVENTORY_SLOT_COUNT = 12;

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player playerIn, int index) {
        Slot sourceSlot = slots.get(index);
        if (!sourceSlot.hasItem()) return ItemStack.EMPTY;
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + index);
            return ItemStack.EMPTY;
        }
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, ModBlocks.BREEDER.get());
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

    public BlockEntity getBlockEntity() {
        return blockEntity;
    }
}