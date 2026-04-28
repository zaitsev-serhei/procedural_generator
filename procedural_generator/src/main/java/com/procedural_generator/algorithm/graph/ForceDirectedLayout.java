package com.procedural_generator.algorithm.graph;

import java.util.Random;

public class ForceDirectedLayout implements GraphLayoutStrategy {

    @Override
    public int[][] layout(MapGraph graph, int width, int height) {

        int[][] grid = new int[height][width];
        Random random = new Random();

        for (Integer node : graph.getNodes()) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            grid[y][x] = node;
        }

        return grid;
    }
}
