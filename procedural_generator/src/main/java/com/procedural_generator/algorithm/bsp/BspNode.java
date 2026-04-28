package com.procedural_generator.algorithm.bsp;

import com.procedural_generator.domain.model.MapRoom;
import lombok.Setter;

public class BspNode {
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    @Setter
    private BspNode leftChild;
    @Setter
    private BspNode rightChild;

    @Setter
    private MapRoom room;

    public BspNode(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean isLeaf() {
        return leftChild == null && rightChild == null;
    }

    public int centerX() {
        return x + width / 2;
    }

    public int centerY() {
        return y + height / 2;
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

    public BspNode getLeftChild() {
        return leftChild;
    }

    public BspNode getRightChild() {
        return rightChild;
    }

    public MapRoom getRoom() {
        return room;
    }

}
