package net.tarantel.chickenroost.pipe;

public enum PipeTier {
    TIER1(1),
    TIER2(4),
    TIER3(16),
    TIER4(64);

    public final int itemsPerSecond;

    PipeTier(int ips) {
        this.itemsPerSecond = ips;
    }
}