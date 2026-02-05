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
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.network.PacketDistributor;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.api.ICollectorTarget;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.handler.BreederHandler;
import net.tarantel.chickenroost.item.base.*;
import net.tarantel.chickenroost.networking.SyncAutoOutputPayload;
import net.tarantel.chickenroost.recipes.BreederRecipe;
import net.tarantel.chickenroost.recipes.ModRecipes;
import net.tarantel.chickenroost.util.Config;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.neoforged.neoforge.energy.EnergyStorage;
import java.util.*;

public class BreederTile extends BlockEntity implements MenuProvider, ICollectorTarget {

    private boolean migrating = false;
    public int progress = 0;
    public int maxProgress = ( Config.breed_speed_tick.get() * 20);
    public final ItemStackHandler itemHandler = new ItemStackHandler(12) {
        @Override
        protected void onContentsChanged(int slot) {
            if (!migrating) {
                setChanged();
                if (slot == 0 || slot == 2) {
                    resetProgress();
                }
                assert level != null;
                if (!level.isClientSide()) {
                    level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
                }
            }
        }


        @Override
        public int getSlotLimit(int slot)
        {
            if(slot == 0 || slot == 2){
                return 1;
            }
            return 64;
        }
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0, 2 -> (stack.getItem() instanceof ChickenItemBase);
                case 1 -> (stack.getItem() instanceof ChickenSeedBase);
                default -> false;
            };
        }
    };


    private static final int CHICKEN_SLOT = 0;

    @Override
    public int getReadSlot() {
        return CHICKEN_SLOT;
    }

    @Override
    public @Nullable IItemHandler getItemHandler() {
        return ccView;
    }

    private final IItemHandler ccView = new IItemHandler() {
        @Override
        public int getSlots() {
            return itemHandler.getSlots();
        }

        @Override
        public @NotNull ItemStack getStackInSlot(int slot) {
            return itemHandler.getStackInSlot(slot);
        }

        @Override
        public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            return stack;
        }

        @Override
        public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
            return ItemStack.EMPTY;
        }

        @Override
        public int getSlotLimit(int slot) {
            return itemHandler.getSlotLimit(slot);
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return itemHandler.isItemValid(slot, stack);
        }
    };

    private String customName = "BREEDER";

    public void setCustomName(String name) {
        if (name == null) name = "";
        this.customName = name;
        setChanged();
        if (this.level != null && !this.level.isClientSide) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
        }
    }

    public String getCustomName() {
        return this.customName;
    }



    public ItemStack currentOutput = ItemStack.EMPTY;

    public ItemStack getRenderStack1() {
        ItemStack stack;

        if(!itemHandler.getStackInSlot(0).isEmpty()) {
            stack = itemHandler.getStackInSlot(0);
        } else {
            stack = ItemStack.EMPTY;
        }

        return stack;
    }

    public ItemStack getRenderStack2() {
        ItemStack stack;

        if(!itemHandler.getStackInSlot(2).isEmpty()) {
            stack = itemHandler.getStackInSlot(2);
        } else {
            stack = ItemStack.EMPTY;
        }

        return stack;
    }

    public ItemStack getRenderStack3() {
        ItemStack stack;

        if(!itemHandler.getStackInSlot(1).isEmpty()) {
            stack = itemHandler.getStackInSlot(1);
        } else {
            stack = ItemStack.EMPTY;
        }

        return stack;
    }

    public void setHandler(ItemStackHandler itemStackHandler) {
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            itemHandler.setStackInSlot(i, itemStackHandler.getStackInSlot(i));
        }
    }
    protected final ContainerData data;


    public int getScaledProgress() {
        int progresss = progress;
        int maxProgresss = this.maxProgress;
        int progressArrowSize = 200;

        return maxProgresss != 0 && progresss != 0 ? progresss * progressArrowSize / maxProgresss : 0;
    }
    public BreederTile(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BREEDER.get(), pos, state);


        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> BreederTile.this.progress;
                    case 1 -> BreederTile.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> BreederTile.this.progress = value;
                    case 1 -> BreederTile.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
        return new BreederHandler(id, inventory, this, this.data);
    }

    private final IItemHandler itemHandlerSided = new InputOutputItemHandler(itemHandler, (i, stack) -> i == 0 || i == 1 || i == 2, i -> i == 3 || i == 4 || i == 5 || i == 6 || i == 7 || i == 8 || i == 9 || i == 10 || i == 11);


    public @Nullable IItemHandler getItemHandlerCapability(@Nullable Direction side) {
        if(side == null)
            return itemHandler;
        return itemHandlerSided;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        setChanged();
    }
    @Override
    public void saveAdditional(CompoundTag nbt, HolderLookup.@NotNull Provider lookup) {
        nbt.put("inventory", itemHandler.serializeNBT(lookup));
        nbt.putInt("breeder.progress", this.progress);
        nbt.putBoolean("AutoOutput", autoOutput);
        nbt.putBoolean("LastRedstonePowered", lastRedstonePowered);
        nbt.putBoolean("AutoOutputByRedstone", autoOutputByRedstone);
        nbt.putString("breeder.custom_name", this.customName);
        super.saveAdditional(nbt, lookup);
    }

    @Override
    public void loadAdditional(@NotNull CompoundTag nbt, HolderLookup.@NotNull Provider lookup) {
        super.loadAdditional(nbt, lookup);
        this.customName = nbt.getString("breeder.custom_name");
        migrating = true;
        try {
            if (nbt.contains("inventory")) {
                CompoundTag invTag = nbt.getCompound("inventory");

                int oldSize = invTag.contains("Size") ? invTag.getInt("Size") : itemHandler.getSlots();
                ItemStackHandler oldHandler = new ItemStackHandler(oldSize);
                oldHandler.deserializeNBT(lookup, invTag);

                for (int i = 0; i < Math.min(oldHandler.getSlots(), itemHandler.getSlots()); i++) {
                    ItemStack oldStack = oldHandler.getStackInSlot(i);
                    if (!oldStack.isEmpty() && itemHandler.getStackInSlot(i).isEmpty()) {
                        itemHandler.setStackInSlot(i, oldStack);
                    }
                }
            }

            this.progress = nbt.getInt("breeder.progress");
        } finally {
            migrating = false;
        }

        if (nbt.contains("AutoOutput")) {
            this.autoOutput = nbt.getBoolean("AutoOutput");
        } else {
            this.autoOutput = false;
        }
        if (nbt.contains("LastRedstonePowered")) {
            this.lastRedstonePowered = nbt.getBoolean("LastRedstonePowered");
        } else {
            this.lastRedstonePowered = false;
        }

        if(nbt.contains("AutoOutputByRedstone")){
            this.autoOutputByRedstone = nbt.getBoolean("AutoOutputByRedstone");
        } else {
            this.autoOutputByRedstone = false;
        }

    }




    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        SimpleContainer block = new SimpleContainer(1);
        ItemStack itemStack = new ItemStack(ModBlocks.BREEDER.get());
        NonNullList<ItemStack> items = inventory.getItems();
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            items.set(i, itemHandler.getStackInSlot(i));
        }
        itemStack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(inventory.getItems()));
        block.setItem(0, itemStack.copy());


        Containers.dropContents(Objects.requireNonNull(this.level), this.worldPosition, block);
    }

    private static boolean hasFreeOrStackableSlot(BreederTile pEntity, ItemStack result) {
        for (int slot = 3; slot <= 11; slot++) {
            ItemStack stackInSlot = pEntity.itemHandler.getStackInSlot(slot);

            if (stackInSlot.isEmpty()) return true;

            if (ItemStack.isSameItemSameComponents(stackInSlot, result)
                    && stackInSlot.getCount() < stackInSlot.getMaxStackSize()) {
                return true;
            }
        }
        return false;
    }
    private boolean autoOutput = false;


    private boolean lastRedstonePowered = false;
    private boolean autoOutputByRedstone = false;

    public boolean isAutoOutputEnabled() {
        return autoOutput;
    }

    public void setAutoOutputEnabled(boolean enabled) {
        this.autoOutput = enabled;
        this.autoOutputByRedstone = false;

        if (level != null && !level.isClientSide) {
            setChanged(level, worldPosition, getBlockState());
        }
    }

    public void setAutoOutputFromGui(boolean enabled) {
        this.autoOutput = enabled;
        this.autoOutputByRedstone = false;
        syncToClients();
    }


    private void setAutoOutputFromRedstone(boolean enabled) {
        this.autoOutput = enabled;
        this.autoOutputByRedstone = enabled;
        syncToClients();
    }

    private void syncToClients() {
        if (level instanceof ServerLevel serverLevel) {
            PacketDistributor.sendToPlayersTrackingChunk(
                    serverLevel,
                    serverLevel.getChunkAt(worldPosition).getPos(),
                    new SyncAutoOutputPayload(worldPosition, autoOutput)
            );
        }
        setChanged();
    }

    public static void tick(Level levelL, BlockPos pos, BlockState state, BreederTile pEntity) {
        if(levelL.isClientSide()) {
            return;
        }

        boolean powered = levelL.hasNeighborSignal(pos);

        if (powered && !pEntity.lastRedstonePowered) {
            if (!pEntity.autoOutput) {
                pEntity.setAutoOutputFromRedstone(true);
            }
        }

        if (!powered && pEntity.lastRedstonePowered) {
            if (pEntity.autoOutputByRedstone) {
                pEntity.setAutoOutputFromRedstone(false);
            }
        }

        pEntity.lastRedstonePowered = powered;

        setChanged(levelL, pos, state);

        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }

        List<RecipeHolder<BreederRecipe>> recipes = levelL.getRecipeManager().getRecipesFor(ModRecipes.BASIC_BREEDING_TYPE.get(), getRecipeInput(inventory), levelL);

        if (!recipes.isEmpty()) {


            if (pEntity.progress == 0 || pEntity.currentOutput.isEmpty()) {
                pEntity.currentOutput = pickRandomVariant(recipes);

                Optional<RecipeHolder<BreederRecipe>> first = levelL.getRecipeManager().getRecipeFor(ModRecipes.BASIC_BREEDING_TYPE.get(), getRecipeInput(inventory), levelL);
                first.ifPresent(holder -> pEntity.maxProgress = ( Config.breed_speed_tick.get() * holder.value().time()));
            }


            if (hasFreeOrStackableSlot(pEntity, pEntity.currentOutput)) {
                pEntity.progress++;

                if (pEntity.progress >= pEntity.maxProgress) {
                    craftItem(pEntity);
                    pEntity.currentOutput = ItemStack.EMPTY;
                }
            } else {
                pEntity.resetProgress();
                pEntity.currentOutput = ItemStack.EMPTY;
            }
        } else {
            pEntity.resetProgress();
            pEntity.currentOutput = ItemStack.EMPTY;
            setChanged(levelL, pos, state);
        }

        if(pEntity.isAutoOutputEnabled()) {

            tryPushBreederOutputsDown(levelL, pos, state, pEntity);
        }

    }

    public void setAutoOutputClient(boolean enabled) {
        this.autoOutput = enabled;
    }



    private static void tryPushBreederOutputsDown(Level level, BlockPos pos, BlockState state, BreederTile tile) {

        IItemHandler belowHandler = level.getCapability(
                Capabilities.ItemHandler.BLOCK,
                pos.below(),
                Direction.UP
        );

        if (belowHandler == null) {
            return;
        }

        boolean changed = false;


        for (int outputSlot = 3; outputSlot <= 11; outputSlot++) {
            ItemStack stackInSlot = tile.itemHandler.getStackInSlot(outputSlot);
            if (stackInSlot.isEmpty()) {
                continue;
            }

            ItemStack remaining = stackInSlot.copy();


            for (int targetSlot = 0; targetSlot < belowHandler.getSlots() && !remaining.isEmpty(); targetSlot++) {
                remaining = belowHandler.insertItem(targetSlot, remaining, false);
            }


            if (remaining.getCount() == stackInSlot.getCount()) {
                continue;
            }


            int moved = stackInSlot.getCount() - remaining.getCount();


            ItemStack newStack = stackInSlot.copy();
            newStack.shrink(moved);

            if (newStack.isEmpty()) {
                newStack = ItemStack.EMPTY;
            }

            tile.itemHandler.setStackInSlot(outputSlot, newStack);
            changed = true;
        }

        if (changed) {
            setChanged(level, pos, state);
        }
    }




    private void resetProgress() {

        this.progress = 0;
    }

    private static ItemStack pickRandomVariant(List<RecipeHolder<BreederRecipe>> recipes) {
        Random ran = new Random();
        int randomIndex = ran.nextInt(recipes.size());
        RecipeHolder<BreederRecipe> randomRecipe = recipes.get(randomIndex);
        return new ItemStack(randomRecipe.value().output().getItem());
    }

    private static void craftItem(BreederTile pEntity) {
        Level level = pEntity.level;
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }

        assert level != null;
        List<RecipeHolder<BreederRecipe>> recipes = level.getRecipeManager().getRecipesFor(ModRecipes.BASIC_BREEDING_TYPE.get(), getRecipeInput(inventory), level);



            ItemStack chickenOutput = pEntity.currentOutput != null && !pEntity.currentOutput.isEmpty()
                    ? pEntity.currentOutput
                    : (recipes.isEmpty() ? ItemStack.EMPTY : new ItemStack(recipes.get(new Random().nextInt(recipes.size())).value().output().getItem()));

            if (chickenOutput.isEmpty()) {
                pEntity.resetProgress();
                return;
            }


            for (int slot = 3; slot <= 11; slot++) {
                ItemStack stackInSlot = pEntity.itemHandler.getStackInSlot(slot);

                if (!stackInSlot.isEmpty() &&
                        ItemStack.isSameItemSameComponents(stackInSlot, chickenOutput) &&
                        stackInSlot.getCount() < stackInSlot.getMaxStackSize()) {


                    int spaceLeft = stackInSlot.getMaxStackSize() - stackInSlot.getCount();
                    int toInsert = Math.min(spaceLeft, chickenOutput.getCount());


                    if(ChickenRoostMod.CONFIG.BreederSeeds) {
                        pEntity.itemHandler.extractItem(0, 0, true);
                        pEntity.itemHandler.extractItem(2, 0, true);
                        pEntity.itemHandler.extractItem(1, 1, false);


                        stackInSlot.grow(toInsert);
                        pEntity.itemHandler.setStackInSlot(slot, stackInSlot);

                        pEntity.resetProgress();
                    }else {
                        pEntity.itemHandler.extractItem(0, 0, true);
                        pEntity.itemHandler.extractItem(2, 0, true);



                        stackInSlot.grow(toInsert);
                        pEntity.itemHandler.setStackInSlot(slot, stackInSlot);

                        pEntity.resetProgress();
                    }
                    return;
                }
            }


            for (int slot = 3; slot <= 11; slot++) {
                if (pEntity.itemHandler.getStackInSlot(slot).isEmpty()) {

                    if(ChickenRoostMod.CONFIG.BreederSeeds) {
                        pEntity.itemHandler.extractItem(0, 0, true);
                        pEntity.itemHandler.extractItem(2, 0, true);
                        pEntity.itemHandler.extractItem(1, 1, false);


                        pEntity.itemHandler.setStackInSlot(slot, chickenOutput);

                        pEntity.resetProgress();
                    } else {
                        pEntity.itemHandler.extractItem(0, 0, true);
                        pEntity.itemHandler.extractItem(2, 0, true);


                        pEntity.itemHandler.setStackInSlot(slot, chickenOutput);

                        pEntity.resetProgress();
                    }
                    return;
                }
            }

            pEntity.resetProgress();
    }

    public static RecipeInput getRecipeInput(SimpleContainer inventory) {
        return new RecipeInput() {
            @Override
            public @NotNull ItemStack getItem(int index) {
                return inventory.getItem(index).copy();
            }

            @Override
            public int size() {
                return inventory.getContainerSize();
            }
        };
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
}
