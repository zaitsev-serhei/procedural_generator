package com.procedural_generator.domain.model;

public class MapRoom {
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final int roomIndex;

    public MapRoom(int x, int y, int width, int height, int roomIndex) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.roomIndex = roomIndex;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getRoomIndex() {
        return roomIndex;
    }
}
