package net.tarantel.chickenroost.pipe;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities.ItemHandler;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.tarantel.chickenroost.block.tile.ModBlockEntities;
import org.jetbrains.annotations.NotNull;

public class PipeBlockEntity extends BlockEntity {
   private int outputPipeRR = 0;
   private PipeBlockEntity.PipeMode stackSendMode = PipeBlockEntity.PipeMode.NONE;
   @Nullable
   private List<BlockPos> cachedOutputPositions = null;
   private boolean outputCacheDirty = true;
   private final PipeTier tier;
   private PipeBlockEntity.PipeMode mode = PipeBlockEntity.PipeMode.NONE;
   private float accumulator = 0.0F;
   private int roundRobin = 0;

   public PipeBlockEntity.PipeMode getStackSendMode() {
      return this.stackSendMode;
   }

   public int getStackSendModeId() {
      return this.stackSendMode.id();
   }

   public void setStackSendMode(PipeBlockEntity.PipeMode mode) {
      if (mode == null) {
         mode = PipeBlockEntity.PipeMode.NONE;
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
      this.setStackSendMode(PipeBlockEntity.PipeMode.byId(id));
   }

   public PipeBlockEntity(BlockPos pos, BlockState state) {
      super(ModBlockEntities.PIPE.get(), pos, state);
      if (state.getBlock() instanceof PipeBlock pipe) {
         this.tier = pipe.getTier();
      } else {
         this.tier = PipeTier.TIER1;
      }
   }

   public void cycleMode() {
      this.mode = switch (this.mode) {
         case NONE -> PipeBlockEntity.PipeMode.INPUT;
         case INPUT -> PipeBlockEntity.PipeMode.OUTPUT;
         case OUTPUT -> PipeBlockEntity.PipeMode.NONE;
      };
      this.invalidateNetwork(this.level);
      this.setChanged();
      System.out.println("Pipe mode now: " + this.mode);
   }

   public void onLoad() {
      super.onLoad();
      this.invalidateOutputCache();
   }

   public void setRemoved() {
      super.setRemoved();
      this.invalidateOutputCache();
   }

   private List<BlockPos> getCachedOutputs(Level level) {
      if (!this.outputCacheDirty && this.cachedOutputPositions != null) {
         for (BlockPos pos : this.cachedOutputPositions) {
            if (!(level.getBlockEntity(pos) instanceof PipeBlockEntity)) {
               this.outputCacheDirty = true;
               break;
            }
         }
      }

      if (this.outputCacheDirty || this.cachedOutputPositions == null) {
         this.cachedOutputPositions = this.findAllOutputPositions(level);
         this.outputCacheDirty = false;
      }

      return this.cachedOutputPositions;
   }

   public PipeBlockEntity.PipeMode getMode() {
      return this.mode;
   }

   public void invalidateOutputCache() {
      this.outputCacheDirty = true;
   }

   public static void tick(Level level, BlockPos pos, BlockState state, PipeBlockEntity pipe) {
      if (!level.isClientSide) {
         pipe.accumulator = pipe.accumulator + pipe.tier.itemsPerSecond / 20.0F;
         int moves = (int)pipe.accumulator;
         if (moves > 0) {
            pipe.accumulator -= moves;
            int i = 0;

            while (i < moves && pipe.tryTransfer(level, pos)) {
               i++;
            }
         }
      }
   }

   private boolean tryTransfer(Level level, BlockPos pos) {
      if (this.mode != PipeBlockEntity.PipeMode.INPUT) {
         return false;
      } else {
         for (Direction d : Direction.values()) {
            IItemHandler src = this.getItemHandler(level, pos.relative(d), d.getOpposite());
            if (src != null) {
               int slots = src.getSlots();

               for (int i = 0; i < slots; i++) {
                  int slot = (this.roundRobin + i) % slots;
                  ItemStack simulated = src.extractItem(slot, 1, true);
                  if (!simulated.isEmpty() && this.pushIntoNetwork(level, pos, simulated, 0)) {
                     ItemStack extracted = src.extractItem(slot, 1, false);
                     if (!extracted.isEmpty()) {
                        return true;
                     }
                  }
               }
            }
         }

         return false;
      }
   }

   private int maxHops() {
      return switch (this.tier) {
         case TIER1 -> 6;
         case TIER2 -> 14;
         case TIER3 -> 30;
         case TIER4 -> 62;
      };
   }

   private boolean pushIntoNetwork(Level level, BlockPos pos, ItemStack stack) {
      return this.pushIntoNetwork(level, pos, stack, 0);
   }

   private boolean pushIntoNetwork(Level level, BlockPos pos, ItemStack stack, int hops) {
      if (hops > this.maxHops()) {
         return false;
      } else {
         List<BlockPos> outputs = this.getCachedOutputs(level);
         if (outputs.isEmpty()) {
            return false;
         } else {
            int size = outputs.size();

            for (int i = 0; i < size; i++) {
               BlockPos targetPos = outputs.get((this.outputPipeRR + i) % size);
               if (level.getBlockEntity(targetPos) instanceof PipeBlockEntity target) {
                  if (target.tier == this.tier && target.mode == PipeBlockEntity.PipeMode.OUTPUT) {
                     if (target.acceptItem(level, targetPos, stack, hops + 1)) {
                        this.outputPipeRR = (this.outputPipeRR + i + 1) % size;
                        this.setChanged();
                        return true;
                     }
                  } else {
                     this.invalidateOutputCache();
                  }
               } else {
                  this.invalidateOutputCache();
               }
            }

            return false;
         }
      }
   }

   private boolean canAcceptSimulated(Level level, BlockPos pos, ItemStack stack) {
      List<IItemHandler> targets = this.findAdjacentInventories(level, pos);
      if (targets.isEmpty()) {
         return false;
      } else {
         for (IItemHandler target : targets) {
            ItemStack rem = ItemHandlerHelper.insertItem(target, stack, true);
            if (rem.isEmpty()) {
               return true;
            }
         }

         return false;
      }
   }

   private boolean acceptItem(Level level, BlockPos pos, ItemStack stack, int hops) {
      if (this.mode == PipeBlockEntity.PipeMode.OUTPUT) {
         List<IItemHandler> targets = this.findAdjacentInventories(level, pos);
         if (targets.isEmpty()) {
            this.invalidateOutputCache();
            return false;
         } else {
            for (int i = 0; i < targets.size(); i++) {
               IItemHandler target = targets.get(this.roundRobin++ % targets.size());
               if (ItemHandlerHelper.insertItem(target, stack, false).isEmpty()) {
                  return true;
               }
            }

            return false;
         }
      } else {
         return this.mode == PipeBlockEntity.PipeMode.NONE ? this.pushIntoNetwork(level, pos, stack, hops) : false;
      }
   }

   private ItemStack extractOne(IItemHandler handler) {
      for (int i = 0; i < handler.getSlots(); i++) {
         ItemStack sim = handler.extractItem(i, 1, true);
         if (!sim.isEmpty()) {
            return handler.extractItem(i, 1, false);
         }
      }

      return ItemStack.EMPTY;
   }

   private List<IItemHandler> findAdjacentInventories(Level level, BlockPos pos) {
      List<IItemHandler> list = new ArrayList<>();

      for (Direction d : Direction.values()) {
         IItemHandler h = this.getItemHandler(level, pos.relative(d), d.getOpposite());
         if (h != null) {
            list.add(h);
         }
      }

      return list;
   }

   @Nullable
   private IItemHandler getItemHandler(Level level, BlockPos pos, Direction side) {
      BlockEntity be = level.getBlockEntity(pos);
      return be == null ? null : (IItemHandler)level.getCapability(ItemHandler.BLOCK, pos, level.getBlockState(pos), be, side);
   }

   protected void saveAdditional(CompoundTag tag, @NotNull Provider lookup) {
      tag.putInt("Mode", this.mode.ordinal());
      tag.putFloat("Acc", this.accumulator);
      tag.putInt("RR", this.roundRobin);
      tag.putInt("OutputRR", this.outputPipeRR);
      super.saveAdditional(tag, lookup);
   }

   protected void loadAdditional(@NotNull CompoundTag tag, @NotNull Provider lookup) {
      super.loadAdditional(tag, lookup);
      this.mode = PipeBlockEntity.PipeMode.values()[tag.getInt("Mode")];
      this.accumulator = tag.getFloat("Acc");
      this.roundRobin = tag.getInt("RR");
      this.outputPipeRR = tag.getInt("OutputRR");
   }

   private List<BlockPos> findAllOutputPositions(Level level) {
      List<BlockPos> outputs = new ArrayList<>();
      List<PipeBlockEntity.PipeNode> queue = new ArrayList<>();
      List<BlockPos> visited = new ArrayList<>();
      queue.add(new PipeBlockEntity.PipeNode(this, 0));
      visited.add(this.worldPosition);

      while (!queue.isEmpty()) {
         PipeBlockEntity.PipeNode node = queue.remove(0);
         if (node.hops <= this.maxHops()) {
            for (Direction d : Direction.values()) {
               BlockPos npos = node.pipe.worldPosition.relative(d);
               if (!visited.contains(npos) && level.getBlockEntity(npos) instanceof PipeBlockEntity pipe && pipe.tier == this.tier) {
                  visited.add(npos);
                  if (pipe.mode == PipeBlockEntity.PipeMode.OUTPUT) {
                     outputs.add(npos);
                  } else if (pipe.mode == PipeBlockEntity.PipeMode.NONE) {
                     queue.add(new PipeBlockEntity.PipeNode(pipe, node.hops + 1));
                  }
               }
            }
         }
      }

      return outputs;
   }

   public void invalidateNetwork(Level level) {
      List<BlockPos> visited = new ArrayList<>();
      List<BlockPos> queue = new ArrayList<>();
      queue.add(this.worldPosition);
      visited.add(this.worldPosition);

      while (!queue.isEmpty()) {
         BlockPos pos = queue.remove(0);
         if (level.getBlockEntity(pos) instanceof PipeBlockEntity pipe) {
            pipe.outputCacheDirty = true;

            for (Direction d : Direction.values()) {
               BlockPos npos = pos.relative(d);
               if (!visited.contains(npos) && level.getBlockEntity(npos) instanceof PipeBlockEntity np && np.tier == this.tier) {
                  visited.add(npos);
                  queue.add(npos);
               }
            }
         }
      }
   }

   public static enum PipeMode {
      NONE,
      INPUT,
      OUTPUT;

      public PipeBlockEntity.PipeMode next() {
         return values()[(this.ordinal() + 1) % values().length];
      }

      public int id() {
         return this.ordinal();
      }

      public static PipeBlockEntity.PipeMode byId(int id) {
         return id >= 0 && id < values().length ? values()[id] : NONE;
      }
   }

   private static class PipeNode {
      final PipeBlockEntity pipe;
      final int hops;

      PipeNode(PipeBlockEntity pipe, int hops) {
         this.pipe = pipe;
         this.hops = hops;
      }
   }
}
