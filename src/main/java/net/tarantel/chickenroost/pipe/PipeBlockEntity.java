package net.tarantel.chickenroost.pipe;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.tarantel.chickenroost.block.tile.ModBlockEntities;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class PipeBlockEntity extends BlockEntity {


    private int outputPipeRR = 0;
    public enum PipeMode {
        NONE,
        INPUT,
        OUTPUT;

        public PipeMode next() {
            return values()[(this.ordinal() + 1) % values().length];
        }

        public int id() {
            return this.ordinal();
        }

        public static PipeMode byId(int id) {
            if (id < 0 || id >= values().length) return NONE;
            return values()[id];
        }
    }

    private PipeMode stackSendMode = PipeMode.NONE;

    public PipeMode getStackSendMode() {
        return this.stackSendMode;
    }

    public int getStackSendModeId() {
        return this.stackSendMode.id();
    }

    public void setStackSendMode(PipeMode mode) {
        if (mode == null) mode = PipeMode.NONE;
        if (this.stackSendMode == mode) return;

        this.stackSendMode = mode;

        setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public void setStackSendModeById(int id) {
        setStackSendMode(PipeMode.byId(id));
    }


    @Nullable private List<BlockPos> cachedOutputPositions = null;
    private boolean outputCacheDirty = true;



    private final PipeTier tier;
    private PipeMode mode = PipeMode.NONE;

    private float accumulator = 0f;
    private int roundRobin = 0;



    public PipeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PIPE.get(), pos, state);

        if (state.getBlock() instanceof PipeBlock pipe) {
            this.tier = pipe.getTier();
        } else {
            this.tier = PipeTier.TIER1;
        }
    }

    public void cycleMode() {
        mode = switch (mode) {
            case NONE -> PipeMode.INPUT;
            case INPUT -> PipeMode.OUTPUT;
            case OUTPUT -> PipeMode.NONE;
        };

        invalidateNetwork(level);
        setChanged();
        System.out.println("Pipe mode now: " + mode);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        invalidateOutputCache();
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        invalidateOutputCache();
    }

    private List<BlockPos> getCachedOutputs(Level level) {
        if (!outputCacheDirty && cachedOutputPositions != null) {
            for (BlockPos pos : cachedOutputPositions) {
                if (!(level.getBlockEntity(pos) instanceof PipeBlockEntity)) {
                    outputCacheDirty = true;
                    break;
                }
            }
        }

        if (outputCacheDirty || cachedOutputPositions == null) {
            cachedOutputPositions = findAllOutputPositions(level);
            outputCacheDirty = false;
        }

        return cachedOutputPositions;
    }




    public PipeMode getMode() {
        return mode;
    }
    public void invalidateOutputCache() {
        outputCacheDirty = true;
    }


    public static void tick(Level level, BlockPos pos, BlockState state, PipeBlockEntity pipe) {
        if (level.isClientSide) return;

        pipe.accumulator += pipe.tier.itemsPerSecond / 20f;
        int moves = (int) pipe.accumulator;
        if (moves <= 0) return;
        pipe.accumulator -= moves;

        for (int i = 0; i < moves; i++) {
            if (!pipe.tryTransfer(level, pos)) break;
        }
    }

    private boolean tryTransfer(Level level, BlockPos pos) {

        if (mode != PipeMode.INPUT) return false;


        for (Direction d : Direction.values()) {
            IItemHandler src = getItemHandler(level, pos.relative(d), d.getOpposite());
            if (src == null) continue;

            int slots = src.getSlots();


            for (int i = 0; i < slots; i++) {
                int slot = (roundRobin + i) % slots;


                ItemStack simulated = src.extractItem(slot, 1, true);
                if (simulated.isEmpty()) continue;


                if (!pushIntoNetwork(level, pos, simulated, 0)) {
                    continue;
                }


                ItemStack extracted = src.extractItem(slot, 1, false);
                if (extracted.isEmpty()) continue;

                return true;
            }
        }

        return false;
    }

    private int maxHops() {
        return switch (tier) {
            case TIER1 -> 6;
            case TIER2 -> 14;
            case TIER3 -> 30;
            case TIER4 -> 62;
        };
    }

    private boolean pushIntoNetwork(Level level, BlockPos pos, ItemStack stack) {
        return pushIntoNetwork(level, pos, stack, 0);
    }

    private boolean pushIntoNetwork(Level level, BlockPos pos, ItemStack stack, int hops) {
        if (hops > maxHops()) return false;

        List<BlockPos> outputs = getCachedOutputs(level);
        if (outputs.isEmpty()) return false;

        int size = outputs.size();

        for (int i = 0; i < size; i++) {
            BlockPos targetPos = outputs.get((outputPipeRR + i) % size);

            BlockEntity be = level.getBlockEntity(targetPos);
            if (!(be instanceof PipeBlockEntity target)) {
                invalidateOutputCache();
                continue;
            }
            if (target.tier != this.tier || target.mode != PipeMode.OUTPUT) {
                invalidateOutputCache();
                continue;
            }

            if (target.acceptItem(level, targetPos, stack, hops + 1)) {
                outputPipeRR = (outputPipeRR + i + 1) % size;
                setChanged();
                return true;
            }
        }

        return false;
    }






    private boolean canAcceptSimulated(Level level, BlockPos pos, ItemStack stack) {
        List<IItemHandler> targets = findAdjacentInventories(level, pos);
        if (targets.isEmpty()) return false;

        for (IItemHandler target : targets) {
            ItemStack rem = ItemHandlerHelper.insertItem(target, stack, true);
            if (rem.isEmpty()) {
                return true;
            }
        }
        return false;
    }


    private boolean acceptItem(Level level, BlockPos pos, ItemStack stack, int hops) {

        if (mode == PipeMode.OUTPUT) {
            List<IItemHandler> targets = findAdjacentInventories(level, pos);
            if (targets.isEmpty()) {
                invalidateOutputCache();
                return false;
            }
            for (int i = 0; i < targets.size(); i++) {
                IItemHandler target = targets.get(roundRobin++ % targets.size());
                if (ItemHandlerHelper.insertItem(target, stack, false).isEmpty()) {
                    return true;
                }
            }
            return false;
        }

        if (mode == PipeMode.NONE) {
            return pushIntoNetwork(level, pos, stack, hops);
        }

        return false;
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
            IItemHandler h = getItemHandler(level, pos.relative(d), d.getOpposite());
            if (h != null) list.add(h);
        }
        return list;
    }

    @Nullable
    private IItemHandler getItemHandler(Level level, BlockPos pos, Direction side) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be == null) return null;

        return level.getCapability(
                Capabilities.ItemHandler.BLOCK,
                pos,
                level.getBlockState(pos),
                be,
                side
        );
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.@NotNull Provider lookup) {
        tag.putInt("Mode", mode.ordinal());
        tag.putFloat("Acc", accumulator);
        tag.putInt("RR", roundRobin);
        tag.putInt("OutputRR", outputPipeRR);

        super.saveAdditional(tag, lookup);
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider lookup) {
        super.loadAdditional(tag, lookup);
        mode = PipeMode.values()[tag.getInt("Mode")];
        accumulator = tag.getFloat("Acc");
        roundRobin = tag.getInt("RR");
        outputPipeRR = tag.getInt("OutputRR");

    }

    private static class PipeNode {
        final PipeBlockEntity pipe;
        final int hops;

        PipeNode(PipeBlockEntity pipe, int hops) {
            this.pipe = pipe;
            this.hops = hops;
        }
    }


    private List<BlockPos> findAllOutputPositions(Level level) {
        List<BlockPos> outputs = new ArrayList<>();
        List<PipeNode> queue = new ArrayList<>();
        List<BlockPos> visited = new ArrayList<>();

        queue.add(new PipeNode(this, 0));
        visited.add(worldPosition);

        while (!queue.isEmpty()) {
            PipeNode node = queue.remove(0);
            if (node.hops > maxHops()) continue;

            for (Direction d : Direction.values()) {
                BlockPos npos = node.pipe.worldPosition.relative(d);
                if (visited.contains(npos)) continue;

                BlockEntity be = level.getBlockEntity(npos);
                if (!(be instanceof PipeBlockEntity pipe)) continue;
                if (pipe.tier != this.tier) continue;

                visited.add(npos);

                if (pipe.mode == PipeMode.OUTPUT) {
                    outputs.add(npos);
                } else if (pipe.mode == PipeMode.NONE) {
                    queue.add(new PipeNode(pipe, node.hops + 1));
                }
            }
        }
        return outputs;
    }

    public void invalidateNetwork(Level level) {
        List<BlockPos> visited = new ArrayList<>();
        List<BlockPos> queue = new ArrayList<>();

        queue.add(worldPosition);
        visited.add(worldPosition);

        while (!queue.isEmpty()) {
            BlockPos pos = queue.remove(0);
            BlockEntity be = level.getBlockEntity(pos);
            if (!(be instanceof PipeBlockEntity pipe)) continue;

            pipe.outputCacheDirty = true;

            for (Direction d : Direction.values()) {
                BlockPos npos = pos.relative(d);
                if (visited.contains(npos)) continue;

                BlockEntity nbe = level.getBlockEntity(npos);
                if (nbe instanceof PipeBlockEntity np && np.tier == this.tier) {
                    visited.add(npos);
                    queue.add(npos);
                }
            }
        }
    }





}
