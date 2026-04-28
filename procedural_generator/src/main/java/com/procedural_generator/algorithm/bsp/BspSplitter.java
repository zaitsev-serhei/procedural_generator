package com.procedural_generator.algorithm.bsp;

import java.util.Random;

public class BspSplitter {
    public boolean split(BspNode node, int minRoomSize, double splitRatio, Random random) {
        if (!node.isLeaf()) {
            return false;
        }

        boolean splitHorizontally = chooseSplitDirection(node, splitRatio, random);

        if (splitHorizontally) {
            return splitHorizontally(node, minRoomSize, random);
        } else {
            return splitVertically(node, minRoomSize, random);
        }
    }

    private boolean chooseSplitDirection(BspNode node, double splitRatio, Random random) {
        if (node.getWidth() > node.getHeight() && node.getWidth() / (double) node.getHeight() >= splitRatio) {
            return false;
        }

        if (node.getHeight() > node.getWidth() && node.getHeight() / (double) node.getWidth() >= splitRatio) {
            return true;
        }

        return random.nextBoolean();
    }

    private boolean splitHorizontally(BspNode node, int minRoomSize, Random random) {
        int minSplit = minRoomSize;
        int maxSplit = node.getHeight() - minRoomSize;

        if (maxSplit <= minSplit) {
            return false;
        }

        int split = random.nextInt(maxSplit - minSplit) + minSplit;

        node.setLeftChild(new BspNode(
                node.getX(),
                node.getY(),
                node.getWidth(),
                split
        ));

        node.setRightChild(new BspNode(
                node.getX(),
                node.getY() + split,
                node.getWidth(),
                node.getHeight() - split
        ));

        return true;
    }

    private boolean splitVertically(BspNode node, int minRoomSize, Random random) {
        int minSplit = minRoomSize;
        int maxSplit = node.getWidth() - minRoomSize;

        if (maxSplit <= minSplit) {
            return false;
        }

        int split = random.nextInt(maxSplit - minSplit) + minSplit;

        node.setLeftChild(new BspNode(
                node.getX(),
                node.getY(),
                split,
                node.getHeight()
        ));

        node.setRightChild(new BspNode(
                node.getX() + split,
                node.getY(),
                node.getWidth() - split,
                node.getHeight()
        ));

        return true;
    }
}
