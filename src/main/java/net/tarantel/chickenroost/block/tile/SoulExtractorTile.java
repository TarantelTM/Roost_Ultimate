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
import net.tarantel.chickenroost.api.ICollectorTarget;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.handler.SoulExtractorHandler;
import net.tarantel.chickenroost.item.base.*;
import net.tarantel.chickenroost.networking.SyncAutoOutputPayload;
import net.tarantel.chickenroost.recipes.ModRecipes;
import net.tarantel.chickenroost.recipes.SoulExtractorRecipe;
import net.tarantel.chickenroost.util.Config;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public class SoulExtractorTile extends BlockEntity implements MenuProvider, ICollectorTarget {

    public final ItemStackHandler itemHandler = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(slot == 0){
                resetProgress();
            }
            assert level != null;
            if(!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0 -> (stack.getItem() instanceof ChickenItemBase);
                case 1 -> false;
                default -> super.isItemValid(slot, stack);
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

    private String customName = "EXTRACTOR";

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



    public ItemStack getRenderStack() {
        ItemStack stack;

        if(!itemHandler.getStackInSlot(0).isEmpty()) {
            stack = itemHandler.getStackInSlot(0);
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
    public int progress = 0;
    public int maxProgress = (Config.extractor_speedtimer.get() * 20);

    public int getScaledProgress() {
        int progresss = progress;
        int maxProgresss = maxProgress;
        int progressArrowSize = 200;

        return maxProgresss != 0 && progresss != 0 ? progresss * progressArrowSize / maxProgresss : 0;
    }
    public SoulExtractorTile(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SOUL_EXTRACTOR.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> SoulExtractorTile.this.progress;
                    case 1 -> SoulExtractorTile.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> SoulExtractorTile.this.progress = value;
                    case 1 -> SoulExtractorTile.this.maxProgress = value;
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
        return Component.translatable("name.chicken_roost.soul_extractor_");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
        return new SoulExtractorHandler(id, inventory, this, this.data);
    }

    private final IItemHandler itemHandlerSided = new InputOutputItemHandler(itemHandler, (i, stack) -> i == 0, i -> i == 1);


    public @Nullable IItemHandler getItemHandlerCapability(@Nullable Direction side) {
        if(side == null)
            return itemHandler;

        return itemHandlerSided;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        assert level != null;
        if(!level.isClientSide()) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
        setChanged();
    }

    @Override
    public void saveAdditional(CompoundTag nbt, HolderLookup.@NotNull Provider lookup) {
        nbt.put("inventory", itemHandler.serializeNBT(lookup));
        nbt.putInt("soul_extractor.progress", this.progress);
        nbt.putBoolean("AutoOutput", autoOutput);
        nbt.putBoolean("LastRedstonePowered", lastRedstonePowered);
        nbt.putBoolean("AutoOutputByRedstone", autoOutputByRedstone);
        nbt.putString("soul_extractor.custom_name", this.customName);
        super.saveAdditional(nbt, lookup);
    }

    @Override
    public void loadAdditional(@NotNull CompoundTag nbt, HolderLookup.@NotNull Provider lookup) {
        super.loadAdditional(nbt, lookup);
        itemHandler.deserializeNBT(lookup, nbt.getCompound("inventory"));
        progress = nbt.getInt("soul_extractor.progress");
        this.customName = nbt.getString("soul_extractor.custom_name");
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

        ItemStack itemStack = new ItemStack(ModBlocks.SOUL_EXTRACTOR.get());
        NonNullList<ItemStack> items = inventory.getItems();
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            items.set(i, itemHandler.getStackInSlot(i));
        }
        itemStack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(inventory.getItems()));
        block.setItem(0, itemStack.copy());

        Containers.dropContents(Objects.requireNonNull(this.level), this.worldPosition, block);
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

    public static void tick(Level level, BlockPos pos, BlockState state, SoulExtractorTile pEntity) {
        if(level.isClientSide()) {
            return;
        }

        boolean powered = level.hasNeighborSignal(pos);

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

        setChanged(level, pos, state);
        if(hasRecipe(pEntity)) {
            pEntity.progress++;
            if(pEntity.progress >= pEntity.maxProgress) {
                craftItem(pEntity);
            }
        } else {
            pEntity.resetProgress();
            setChanged(level, pos, state);
        }

        if(pEntity.isAutoOutputEnabled()) {
            tryPushOutputDown(level, pos, state, pEntity);
        }
    }

    public void setAutoOutputClient(boolean enabled) {
        this.autoOutput = enabled;
    }

    private static void tryPushOutputDown(Level level, BlockPos pos, BlockState state, SoulExtractorTile tile) {
        ItemStack outputStack = tile.itemHandler.getStackInSlot(1);
        if (outputStack.isEmpty()) {
            return;
        }

        IItemHandler belowHandler = level.getCapability(
                Capabilities.ItemHandler.BLOCK,
                pos.below(),
                Direction.UP
        );

        if (belowHandler == null) {
            return;
        }

        ItemStack remaining = outputStack.copy();

        for (int slot = 0; slot < belowHandler.getSlots() && !remaining.isEmpty(); slot++) {
            remaining = belowHandler.insertItem(slot, remaining, false);
        }

        if (remaining.getCount() == outputStack.getCount()) {
            return;
        }

        int moved = outputStack.getCount() - remaining.getCount();

        ItemStack newStack = outputStack.copy();
        newStack.shrink(moved);

        if (newStack.isEmpty()) {
            newStack = ItemStack.EMPTY;
        }

        tile.itemHandler.setStackInSlot(1, newStack);
        setChanged(level, pos, state);
    }



    private void resetProgress() {
        this.progress = 0;
    }


    private static void craftItem(SoulExtractorTile pEntity) {
        Level level = pEntity.level;
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }
        Optional<RecipeHolder<SoulExtractorRecipe>> recipe = Optional.empty();
        if (level != null) {
            recipe = level.getRecipeManager().getRecipeFor(ModRecipes.SOUL_EXTRACTION_TYPE.get(), getRecipeInput(inventory), level);
        }

        if(hasRecipe(pEntity)) {
            pEntity.itemHandler.extractItem(0, 1, false);
            pEntity.itemHandler.setStackInSlot(1, new ItemStack(recipe.get().value().output().copy().getItem(),
                    pEntity.itemHandler.getStackInSlot(1).getCount() + 1));

            pEntity.resetProgress();
        }
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
    private static boolean hasRecipe(SoulExtractorTile entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }
        Optional<RecipeHolder<SoulExtractorRecipe>> recipe = Optional.empty();
        if (level != null) {
            recipe = level.getRecipeManager().getRecipeFor(ModRecipes.SOUL_EXTRACTION_TYPE.get(), getRecipeInput(inventory), level);
            recipe.ifPresent(soulExtractorRecipeRecipeHolder -> entity.maxProgress = (Config.extractor_speedtimer.get() * soulExtractorRecipeRecipeHolder.value().time()));
        }

        return recipe.isPresent() && canInsertAmountIntoOutputSlot(inventory) &&
                canInsertItemIntoOutputSlot(inventory, recipe.get().value().output().copy().getItem().getDefaultInstance());
    }


    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack stack) {
        return inventory.getItem(1).getItem() == stack.getItem() || inventory.getItem(1).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(1).getMaxStackSize() > inventory.getItem(1).getCount();
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