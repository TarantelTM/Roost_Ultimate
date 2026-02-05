package net.tarantel.chickenroost.pipe;

public enum SideMode {
    PASSIVE,
    INPUT,
    OUTPUT;

    public SideMode next() {
        return values()[(ordinal() + 1) % values().length];
    }
}