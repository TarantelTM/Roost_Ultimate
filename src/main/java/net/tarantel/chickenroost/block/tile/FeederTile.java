package net.tarantel.chickenroost.block.tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities.ItemHandler;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.handler.FeederHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FeederTile extends BlockEntity implements MenuProvider {
   public final ItemStackHandler itemHandler = new ItemStackHandler(54) {
      protected void onContentsChanged(int slot) {
         FeederTile.this.setChanged();
         if (FeederTile.this.level != null && !FeederTile.this.level.isClientSide) {
            FeederTile.this.level.sendBlockUpdated(FeederTile.this.getBlockPos(), FeederTile.this.getBlockState(), FeederTile.this.getBlockState(), 3);
         }
      }
   };
   private final Set<BlockPos> activeRoosts = new HashSet<>();
   private int feedRange = 10;
   private final Map<BlockPos, Item> preferredSeeds = new HashMap<>();
   private boolean roundRobinEnabled = false;
   private int roundRobinIndex = 0;
   private FeederTile.StackSendMode stackSendMode = FeederTile.StackSendMode.SINGLE;
   private int _roostValidationTicker = 0;

   public FeederTile(BlockPos pos, BlockState state) {
      super(ModBlockEntities.FEEDER.get(), pos, state);
   }

   public int getFeedRange() {
      return this.feedRange;
   }

   public void setFeedRange(int r) {
      r = Math.max(5, Math.min(30, r));
      if (this.feedRange != r) {
         this.feedRange = r;
         this.setChanged();
         if (this.level != null && !this.level.isClientSide) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
         }
      }
   }

   public void addActiveRoost(BlockPos roost) {
      if (this.activeRoosts.add(roost.immutable())) {
         this.setChanged();
         if (this.level != null && !this.level.isClientSide) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
         }
      }
   }

   public void removeActiveRoost(BlockPos roost) {
      if (this.activeRoosts.remove(roost)) {
         this.setChanged();
         if (this.level != null && !this.level.isClientSide) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
         }
      }
   }

   public Set<BlockPos> getActiveRoosts() {
      return Collections.unmodifiableSet(this.activeRoosts);
   }

   public void setPreferredSeed(BlockPos roostPos, @Nullable Item item) {
      if (item == null) {
         this.preferredSeeds.remove(roostPos);
      } else {
         this.preferredSeeds.put(roostPos.immutable(), item);
      }

      this.setChanged();
      if (this.level != null && !this.level.isClientSide) {
         this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
      }
   }

   @Nullable
   public Item getPreferredSeed(BlockPos roostPos) {
      return this.preferredSeeds.get(roostPos);
   }

   public boolean isRoundRobinEnabled() {
      return this.roundRobinEnabled;
   }

   public void setRoundRobinEnabled(boolean enabled) {
      if (this.roundRobinEnabled != enabled) {
         this.roundRobinEnabled = enabled;
         this.roundRobinIndex = 0;
         this.setChanged();
         if (this.level != null && !this.level.isClientSide) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
         }
      }
   }

   public FeederTile.StackSendMode getStackSendMode() {
      return this.stackSendMode;
   }

   public int getStackSendModeId() {
      return this.stackSendMode.id();
   }

   public void setStackSendMode(FeederTile.StackSendMode mode) {
      if (mode == null) {
         mode = FeederTile.StackSendMode.SINGLE;
      }

      if (this.stackSendMode != mode) {
         this.stackSendMode = mode;
         this.setChanged();
         if (this.level != null && !this.level.isClientSide) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
         }
      }
   }

   public void setStackSendModeById(int id) {
      this.setStackSendMode(FeederTile.StackSendMode.byId(id));
   }

   public void saveAdditional(CompoundTag nbt, @NotNull Provider lookup) {
      nbt.put("inventory", this.itemHandler.serializeNBT(lookup));
      long[] arr = this.activeRoosts.stream().mapToLong(BlockPos::asLong).toArray();
      nbt.putLongArray("feeder.roosts", arr);
      nbt.putInt("feeder.range", this.feedRange);
      nbt.putBoolean("feeder.round_robin", this.roundRobinEnabled);
      nbt.putInt("feeder.round_robin_index", this.roundRobinIndex);
      nbt.putInt("feeder.stack_send_mode", this.stackSendMode.id());
      ListTag list = new ListTag();

      for (Entry<BlockPos, Item> e : this.preferredSeeds.entrySet()) {
         CompoundTag t = new CompoundTag();
         t.putLong("pos", e.getKey().asLong());
         ResourceLocation key = BuiltInRegistries.ITEM.getKey(e.getValue());
         t.putString("item", key.toString());
         list.add(t);
      }

      nbt.put("feeder.preferredSeeds", list);
      super.saveAdditional(nbt, lookup);
   }

   public void loadAdditional(@NotNull CompoundTag nbt, @NotNull Provider lookup) {
      super.loadAdditional(nbt, lookup);
      this.itemHandler.deserializeNBT(lookup, nbt.getCompound("inventory"));
      this.preferredSeeds.clear();
      if (nbt.contains("feeder.preferredSeeds")) {
         ListTag list = nbt.getList("feeder.preferredSeeds", 10);

         for (int i = 0; i < list.size(); i++) {
            CompoundTag t = list.getCompound(i);
            BlockPos p = BlockPos.of(t.getLong("pos"));
            if (t.contains("item")) {
               Item it = (Item)BuiltInRegistries.ITEM.get(ResourceLocation.parse(t.getString("item")));
               this.preferredSeeds.put(p, it);
            }
         }
      }

      this.activeRoosts.clear();
      long[] arr = nbt.getLongArray("feeder.roosts");

      for (long l : arr) {
         this.activeRoosts.add(BlockPos.of(l));
      }

      this.feedRange = Math.max(5, Math.min(30, nbt.getInt("feeder.range")));
      this.roundRobinEnabled = nbt.getBoolean("feeder.round_robin");
      this.roundRobinIndex = nbt.getInt("feeder.round_robin_index");
      this.stackSendMode = FeederTile.StackSendMode.byId(nbt.getInt("feeder.stack_send_mode"));
      this.pruneMissingRoosts();
   }

   private static void feedFromActiveRoosts(Level level, FeederTile self) {
      if (!self.activeRoosts.isEmpty()) {
         int r = self.getFeedRange();
         int maxDist2 = r * r;
         ArrayList<BlockPos> candidates = new ArrayList<>();

         for (BlockPos rpos : self.activeRoosts) {
            if (!(rpos.distSqr(self.getBlockPos()) > maxDist2 * 3)) {
               BlockEntity be = level.getBlockEntity(rpos);
               if (be instanceof RoostTile || be instanceof BreederTile || be instanceof TrainerTile) {
                  candidates.add(rpos);
               }
            }
         }

         if (!candidates.isEmpty()) {
            candidates.sort(Comparator.comparingLong(BlockPos::asLong));
            if (!self.roundRobinEnabled) {
               for (BlockPos rposx : candidates) {
                  feedSingleRoost(level, self, rposx);
               }
            } else {
               int idx = Math.floorMod(self.roundRobinIndex, candidates.size());
               BlockPos chosen = candidates.get(idx);
               feedSingleRoost(level, self, chosen);
               self.roundRobinIndex = (idx + 1) % candidates.size();
               self.setChanged();
            }
         }
      }
   }

   private static int getFeedTargetSlot(BlockEntity be) {
      if (be instanceof RoostTile) {
         return 0;
      } else if (be instanceof BreederTile) {
         return 1;
      } else {
         return be instanceof TrainerTile ? 1 : -1;
      }
   }

   private static void feedSingleRoost(Level level, FeederTile self, BlockPos rpos) {
      BlockEntity be = level.getBlockEntity(rpos);
      if (be != null) {
         int targetSlot = getFeedTargetSlot(be);
         if (targetSlot >= 0) {
            IItemHandler targetInv = (IItemHandler)level.getCapability(ItemHandler.BLOCK, rpos, null);
            if (targetInv != null) {
               ItemStack target = targetInv.getStackInSlot(targetSlot);
               Item desired = !target.isEmpty() ? target.getItem() : self.preferredSeeds.get(rpos);
               if (desired != null) {
                  ItemStack probe = new ItemStack(desired);
                  probe.setCount(probe.getMaxStackSize());
                  ItemStack remainder = targetInv.insertItem(targetSlot, probe.copy(), true);
                  int canAccept = probe.getCount() - (remainder.isEmpty() ? 0 : remainder.getCount());
                  if (canAccept > 0) {
                     int modeLimit = self.stackSendMode.limitFor(probe);
                     int toMove = Math.min(canAccept, modeLimit);

                     for (int i = 0; i < self.itemHandler.getSlots() && toMove > 0; i++) {
                        ItemStack src = self.itemHandler.getStackInSlot(i);
                        if (!src.isEmpty() && src.getItem() == desired) {
                           int moveCount = Math.min(toMove, src.getCount());
                           ItemStack extractedSim = self.itemHandler.extractItem(i, moveCount, true);
                           if (!extractedSim.isEmpty()) {
                              ItemStack leftover = targetInv.insertItem(targetSlot, extractedSim, false);
                              int moved = extractedSim.getCount() - (leftover.isEmpty() ? 0 : leftover.getCount());
                              if (moved > 0) {
                                 self.itemHandler.extractItem(i, moved, false);
                                 toMove -= moved;
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public void drops() {
      SimpleContainer block = new SimpleContainer(1);
      ItemStack itemStack = new ItemStack((ItemLike)ModBlocks.FEEDER.get());
      NonNullList<ItemStack> items = NonNullList.withSize(this.itemHandler.getSlots(), ItemStack.EMPTY);

      for (int i = 0; i < this.itemHandler.getSlots(); i++) {
         items.set(i, this.itemHandler.getStackInSlot(i));
      }

      itemStack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(items));
      block.setItem(0, itemStack.copy());
      Containers.dropContents(Objects.requireNonNull(this.level), this.worldPosition, block);
   }

   private boolean isValidFeedTarget(BlockPos pos) {
      if (this.level != null && pos != null) {
         BlockEntity be = this.level.getBlockEntity(pos);
         return be instanceof RoostTile || be instanceof BreederTile || be instanceof TrainerTile;
      } else {
         return false;
      }
   }

   private void pruneMissingRoosts() {
      if (this.level != null && !this.activeRoosts.isEmpty()) {
         this.activeRoosts.removeIf(pos -> !this.isValidFeedTarget(pos));
      }
   }

   private void maybePruneRoostsPeriodic() {
      if (++this._roostValidationTicker % 20 == 0) {
         this.pruneMissingRoosts();
      }
   }

   public static void tick(Level level, BlockPos pos, BlockState state, FeederTile tile) {
      if (!level.isClientSide()) {
         tile.maybePruneRoostsPeriodic();
         feedFromActiveRoosts(level, tile);
         setChanged(level, pos, state);
      }
   }

   public Packet<ClientGamePacketListener> getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this);
   }

   @NotNull
   public CompoundTag getUpdateTag(@NotNull Provider prov) {
      return this.saveWithFullMetadata(prov);
   }

   @Nullable
   public IItemHandler getItemHandlerCapability(@Nullable Direction side) {
      return this.itemHandler;
   }

   @NotNull
   public Component getDisplayName() {
      return Component.translatable("block.chicken_roost.feeder");
   }

   @Nullable
   public AbstractContainerMenu createMenu(int id, @NotNull Inventory inv, @NotNull Player player) {
      return new FeederHandler(id, inv, this);
   }

   public static enum StackSendMode {
      SINGLE,
      HALF,
      FULL;

      public FeederTile.StackSendMode next() {
         return values()[(this.ordinal() + 1) % values().length];
      }

      public int limitFor(ItemStack stack) {
         int max = Math.max(1, stack.getMaxStackSize());

         return switch (this) {
            case SINGLE -> 1;
            case HALF -> Math.max(1, max / 2);
            case FULL -> max;
         };
      }

      public int id() {
         return this.ordinal();
      }

      public static FeederTile.StackSendMode byId(int id) {
         return id >= 0 && id < values().length ? values()[id] : SINGLE;
      }
   }
}
