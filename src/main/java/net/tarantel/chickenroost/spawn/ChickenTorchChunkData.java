package net.tarantel.chickenroost.spawn;

public class ChickenTorchChunkData implements IChickenTorchChunkData {

    private boolean hasTorch = false;
    private boolean scanned = false;

    @Override
    public boolean hasTorch() { return hasTorch; }

    @Override
    public void setHasTorch(boolean value) { this.hasTorch = value; }

    @Override
    public boolean scanned() { return scanned; }

    @Override
    public void setScanned(boolean value) { this.scanned = value; }
}
