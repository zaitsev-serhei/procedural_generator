package com.procedural_generator.algorithm.cellular;

import org.springframework.stereotype.Component;

@Component
public class CellularSimulator {

    public int[][] step(int[][] grid, int birthLimit, int deathLimit) {

        int height = grid.length;
        int width = grid[0].length;

        int[][] newGrid = new int[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                int aliveNeighbors = countAlive(grid, x, y);

                if (grid[y][x] == 1) {
                    newGrid[y][x] = aliveNeighbors < deathLimit ? 0 : 1;
                } else {
                    newGrid[y][x] = aliveNeighbors > birthLimit ? 1 : 0;
                }
            }
        }

        return newGrid;
    }

    private int countAlive(int[][] grid, int x, int y) {

        int count = 0;

        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {

                if (dx == 0 && dy == 0) continue;

                int nx = x + dx;
                int ny = y + dy;

                if (ny >= 0 && ny < grid.length && nx >= 0 && nx < grid[0].length) {
                    count += grid[ny][nx];
                }
            }
        }

        return count;
    }
}
