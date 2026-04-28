package com.procedural_generator.domain.model;

public class MapConnection {
    private final int fromIndex;
    private final int toIndex;

    public MapConnection(int fromIndex, int toIndex) {
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
    }

    public int getFromIndex() {
        return fromIndex;
    }

    public int getToIndex() {
        return toIndex;
    }
}
