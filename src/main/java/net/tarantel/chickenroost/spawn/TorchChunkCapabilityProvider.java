package net.tarantel.chickenroost.spawn;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public class TorchChunkCapabilityProvider
        implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    private final IChickenTorchChunkData data = new ChickenTorchChunkData();
    private final LazyOptional<IChickenTorchChunkData> optional =
            LazyOptional.of(() -> data);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return cap == ModCapabilities.TORCH_DATA
                ? optional.cast()
                : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("HasTorch", data.hasTorch());
        tag.putBoolean("Scanned", data.scanned());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        data.setHasTorch(nbt.getBoolean("HasTorch"));
        data.setScanned(nbt.getBoolean("Scanned"));
    }
}
