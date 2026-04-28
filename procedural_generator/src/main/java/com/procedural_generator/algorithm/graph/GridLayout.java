package com.procedural_generator.algorithm.graph;

public class GridLayout implements GraphLayoutStrategy {

    @Override
    public int[][] layout(MapGraph graph, int width, int height) {

        int[][] grid = new int[height][width];

        int cols = (int) Math.sqrt(graph.getNodes().size()) + 1;
        int rows = cols;

        int xStep = width / cols;
        int yStep = height / rows;

        int i = 0;

        for (Integer node : graph.getNodes()) {

            int x = (i % cols) * xStep;
            int y = (i / cols) * yStep;

            grid[y][x] = node;
            i++;
        }

        return grid;
    }
}
