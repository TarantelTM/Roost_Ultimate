package net.tarantel.chickenroost.block.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.api.ICollectorTarget;
import net.tarantel.chickenroost.block.blocks.Breeder_Block;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.handler.Breeder_Handler;
import net.tarantel.chickenroost.handler.Roost_Handler;
import net.tarantel.chickenroost.item.base.*;
import net.tarantel.chickenroost.network.BreederItemStackSyncS2CPacket;
import net.tarantel.chickenroost.network.ModMessages;
import net.tarantel.chickenroost.recipes.Breeder_Recipe;
import net.tarantel.chickenroost.recipes.ModRecipes;
import net.tarantel.chickenroost.recipes.Roost_Recipe;
import net.tarantel.chickenroost.util.Config;
import net.tarantel.chickenroost.util.WrappedHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class Breeder_Tile extends BlockEntity implements MenuProvider, ICollectorTarget {

    public int progress = 0;
    public int maxProgress = ( Config.ServerConfig.breed_speed_tick.get() * 20);
    public static Level randomLevel;

    public final ItemStackHandler itemHandler = new ItemStackHandler(12) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
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

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();


    private static final int OUTPUT_SLOT = 1;
    @Nullable
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
    public final ContainerData data;
    private int blabla = 0;
    public int getScaledProgress() {
        int progresss = progress;
        int maxProgresss = maxProgress;  // Max Progress
        int progressArrowSize = 200; // This is the height in pixels of your arrow

        return maxProgresss != 0 && progresss != 0 ? progresss * progressArrowSize / maxProgresss : 0;
    }

    public Breeder_Tile(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BREEDER.get(), pos, state);

        this.randomLevel = this.level;

        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> Breeder_Tile.this.progress;
                    case 1 -> Breeder_Tile.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> Breeder_Tile.this.progress = value;
                    case 1 -> Breeder_Tile.this.maxProgress = value;
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
        return Component.literal("Breeder");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
        return new Breeder_Handler(id, inventory, this, this.data);
    }

    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap =
            new HashMap<>();
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if(side == null) {
                return lazyItemHandler.cast();
            }
            for (Direction direction : Arrays.asList(Direction.DOWN, Direction.UP, Direction.NORTH, Direction.EAST, Direction.WEST, Direction.SOUTH)) {
                directionWrappedHandlerMap.put (direction, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 3 || i == 4 || i == 5 || i == 6 || i == 7 || i == 8 || i == 9 || i == 10, (i, s) -> false)));
            }
            for (Direction direction : Arrays.asList(Direction.DOWN,Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH, Direction.UP)) {
                directionWrappedHandlerMap.put (direction, LazyOptional.of(() -> new WrappedHandler(itemHandler, (index) -> index == 3 || index == 4 || index == 5 || index == 6 || index == 7 || index == 8 || index == 9 || index == 10,
                        (index, stack) -> itemHandler.isItemValid(0, stack) || itemHandler.isItemValid(1, stack) || itemHandler.isItemValid(2, stack))));
            }

            if(directionWrappedHandlerMap.containsKey(side)) {
                Direction localDir = this.getBlockState().getValue(Breeder_Block.FACING);

                if(side == Direction.UP || side == Direction.DOWN) {
                    return directionWrappedHandlerMap.get(side).cast();
                }

                return switch (localDir) {
                    default -> directionWrappedHandlerMap.get(side.getOpposite()).cast();
                    case EAST -> directionWrappedHandlerMap.get(side.getClockWise()).cast();
                    case SOUTH -> directionWrappedHandlerMap.get(side).cast();
                    case WEST -> directionWrappedHandlerMap.get(side.getCounterClockWise()).cast();
                };
            }
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        if(!level.isClientSide()) {
            ModMessages.sendToClients(new BreederItemStackSyncS2CPacket(this.itemHandler, worldPosition));
        }

    }
    private boolean migrating = false;
    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());
        nbt.putInt("breeder.progress", this.progress);
        nbt.putBoolean("AutoOutput", autoOutput);
        nbt.putBoolean("LastRedstonePowered", lastRedstonePowered);
        nbt.putString("breeder.custom_name", this.customName);
        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("breeder.progress");
        this.customName = nbt.getString("breeder.custom_name");
        if (nbt.contains("AutoOutput")) {
            autoOutput = nbt.getBoolean("AutoOutput");
        } else {
            autoOutput = false; // fallback, falls alte Welten
        }
        if (nbt.contains("LastRedstonePowered")) {
            lastRedstonePowered = nbt.getBoolean("LastRedstonePowered");
        } else {
            lastRedstonePowered = false;
        }
    }




    public void drops() {
        // Create a SimpleContainer to hold the items from the item handler
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        // Create an ItemStack for the block
        ItemStack itemStack = new ItemStack(ModBlocks.BREEDER.get());

        // Save the inventory contents to the ItemStack's NBT
        CompoundTag nbt = new CompoundTag();
        ListTag itemsTag = new ListTag();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty()) {
                CompoundTag itemTag = new CompoundTag();
                itemTag.putInt("Slot", i);
                stack.save(itemTag);
                itemsTag.add(itemTag);
            }
        }
        nbt.put("Items", itemsTag);
        itemStack.setTag(nbt);

        // Create a SimpleContainer to hold the block's ItemStack
        SimpleContainer block = new SimpleContainer(1);
        block.setItem(0, itemStack.copy());

        // Drop the contents in the world
        Containers.dropContents(Objects.requireNonNull(this.level), this.worldPosition, block);
    }

    private static boolean hasFreeOrStackableSlot(Breeder_Tile pEntity, ItemStack result) {
        for (int slot = 3; slot <= 11; slot++) {
            ItemStack stackInSlot = pEntity.itemHandler.getStackInSlot(slot);

            if (stackInSlot.isEmpty()) return true;

            if (ItemStack.isSameItemSameTags(stackInSlot, result)
                    && stackInSlot.getCount() < stackInSlot.getMaxStackSize()) {
                return true;
            }
        }
        return false;
    }
    private boolean autoOutput = false; // Standard: an


    private boolean lastRedstonePowered = false;

    public boolean isAutoOutputEnabled() {
        return autoOutput;
    }

    public void setAutoOutputEnabled(boolean enabled) {
        this.autoOutput = enabled;

        if (level != null && !level.isClientSide) {
            setChanged(level, worldPosition, getBlockState());
        }
    }

    public static void tick(Level levelL, BlockPos pos, BlockState state, Breeder_Tile pEntity) {
        if(levelL.isClientSide()) {
            return;
        }
        boolean powered = levelL.hasNeighborSignal(pos);
        if (powered && !pEntity.lastRedstonePowered) {
            pEntity.setAutoOutputEnabled(true); // nur bei OFF -> ON
        }
        pEntity.lastRedstonePowered = powered;

        setChanged(levelL, pos, state);

        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }
        /*Optional<Breeder_Recipe> recipes = Optional.empty();
        if (levelL != null) {
            // Get the recipe manager and find a matching recipe
            RecipeManager recipeManager = levelL.getRecipeManager();
            recipes = recipeManager.getRecipeFor(ModRecipes.BASIC_BREEDING_TYPE.get(), inventory, levelL);
        }*/

        List<Breeder_Recipe> recipes =
                levelL.getRecipeManager()
                        .getRecipesFor(Breeder_Recipe.Type.INSTANCE, inventory, levelL);
        if (!recipes.isEmpty()) {


            if (pEntity.progress == 0 || pEntity.currentOutput.isEmpty()) {
                pEntity.currentOutput = pickRandomVariant(recipes, levelL);

                Optional<Breeder_Recipe> first = levelL.getRecipeManager().getRecipeFor(ModRecipes.BASIC_BREEDING_TYPE.get(), inventory, levelL);
                first.ifPresent(holder -> pEntity.maxProgress = ( Config.ServerConfig.breed_speed_tick.get() * holder.time()));
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
            // <<< HIER: Output nach unten schieben >>>
            tryPushBreederOutputsDown(levelL, pos, state, pEntity);
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

    private static void tryPushBreederOutputsDown(Level level, BlockPos pos, BlockState state, Breeder_Tile tile) {
        // ItemHandler des Blocks direkt unter dem Breeder holen (von oben)
        BlockEntity belowEntity = level.getBlockEntity(pos.below());
        IItemHandler belowHandler = null;

        if (belowEntity != null) {
            belowHandler = belowEntity
                    .getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.UP)
                    .orElse(null);
        }

        if (belowHandler == null) {
            return;
        }

        boolean changed = false;

        // Alle Output-Slots durchgehen
        for (int outputSlot = 3; outputSlot <= 11; outputSlot++) {
            ItemStack stackInSlot = tile.itemHandler.getStackInSlot(outputSlot);
            if (stackInSlot.isEmpty()) {
                continue;
            }

            ItemStack remaining = stackInSlot.copy();

            // Versuche, so viel wie möglich in das Inventar darunter zu schieben
            for (int targetSlot = 0; targetSlot < belowHandler.getSlots() && !remaining.isEmpty(); targetSlot++) {
                remaining = belowHandler.insertItem(targetSlot, remaining, false); // simulate = false
            }

            // Wenn sich nichts geändert hat, wurde aus diesem Slot nichts eingefügt
            if (remaining.getCount() == stackInSlot.getCount()) {
                continue;
            }

            // Berechnen, wie viele Items tatsächlich verschoben wurden
            int moved = stackInSlot.getCount() - remaining.getCount();

            // Slot im Breeder anpassen (Stack verkleinern oder leeren)
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


    private static ItemStack pickRandomVariant(List<Breeder_Recipe> recipes, Level level) {
        if (recipes == null || recipes.isEmpty()) {
            return ItemStack.EMPTY;
        }

        Random random = new Random();

        // 1️⃣ zufällige Rezept-Liste wählen
        Breeder_Recipe recipe =
                recipes.get(random.nextInt(recipes.size()));

        if (recipes.isEmpty()) {
            return ItemStack.EMPTY;
        }



        return recipe.getResultItem(level.registryAccess()).copy();
    }





    static ItemStack ChickenOutput = ItemStack.EMPTY;
    static String outpit;
    private static void craftItem(Breeder_Tile pEntity) {
        Level level = pEntity.level;
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }

        assert level != null;
        List<Breeder_Recipe> recipes = pEntity.getLevel().getRecipeManager()
                .getRecipesFor(Breeder_Recipe.Type.INSTANCE, inventory, pEntity.getLevel());
        ItemStack chickenOutput = pEntity.currentOutput != null && !pEntity.currentOutput.isEmpty()
                ? pEntity.currentOutput
                : (recipes.isEmpty() ? ItemStack.EMPTY : new ItemStack(recipes.get(new Random().nextInt(recipes.size())).output().getItem()));

        if (chickenOutput.isEmpty()) {
            pEntity.resetProgress();
            return;
        }


        for (int slot = 3; slot <= 11; slot++) {
            ItemStack stackInSlot = pEntity.itemHandler.getStackInSlot(slot);

            if (!stackInSlot.isEmpty() &&
                    ItemStack.isSameItemSameTags(stackInSlot, chickenOutput) &&
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
                    //pEntity.itemHandler.extractItem(1, 1, false);


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
                    //pEntity.itemHandler.extractItem(1, 1, false);


                    pEntity.itemHandler.setStackInSlot(slot, chickenOutput);

                    pEntity.resetProgress();
                }
                return;
            }
        }

        pEntity.resetProgress();
    }

    private static boolean hasRecipe(Breeder_Tile entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<Breeder_Recipe> recipe = Optional.empty();
        if (level != null) {
            // Get the recipe manager and find a matching recipe
            RecipeManager recipeManager = level.getRecipeManager();
            recipe = recipeManager.getRecipeFor(ModRecipes.BASIC_BREEDING_TYPE.get(), inventory, level);

            // If a recipe is found, set the max progress based on the recipe's time
            if (recipe.isPresent()) {
                entity.maxProgress = Config.ServerConfig.breed_speed_tick.get() * recipe.get().getTime();
            }
        }

        return recipe.isPresent();
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }
}