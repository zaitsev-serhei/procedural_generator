package com.procedural_generator.algorithm.wfc;

import com.procedural_generator.domain.model.WfcTileset;

import java.util.Map;
import java.util.Random;
import java.util.Set;

public class WfcBacktracker {

    private final WfcPropagator propagator = new WfcPropagator();

    public int[][] solve(
            int width,
            int height,
            Map<Integer, WfcTileset.Rule> rules,
            int maxRetries,
            long seed
    ) {
        IllegalStateException lastException = null;

        for (int attempt = 0; attempt < maxRetries; attempt++) {
            try {
                return runAttempt(width, height, rules, new Random(seed + attempt));
            } catch (IllegalStateException ex) {
                lastException = ex;
            }
        }

        throw new IllegalStateException("WFC failed after retries", lastException);
    }

    private int[][] runAttempt(
            int width,
            int height,
            Map<Integer, WfcTileset.Rule> rules,
            Random random
    ) {
        Set<Integer> allTiles = rules.keySet();
        if (allTiles.isEmpty()) {
            throw new IllegalStateException("Tileset rules are empty");
        }

        WfcCell[][] grid = new WfcCell[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y][x] = new WfcCell(allTiles);
            }
        }

        while (true) {
            int[] nextCell = findNextCell(grid, random);
            if (nextCell == null) {
                return finalizeTiles(grid);
            }

            int x = nextCell[0];
            int y = nextCell[1];
            int pickedTile = pickRandom(grid[y][x], random);
            grid[y][x].collapseTo(pickedTile);

            boolean consistent = propagator.propagate(grid, x, y, rules);
            if (!consistent) {
                throw new IllegalStateException("Contradiction found during propagation");
            }
        }
    }

    private int[] findNextCell(WfcCell[][] grid, Random random) {
        int bestEntropy = Integer.MAX_VALUE;
        int bestX = -1;
        int bestY = -1;

        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                WfcCell cell = grid[y][x];
                if (cell.isCollapsed()) {
                    continue;
                }

                int entropy = cell.entropy();
                if (entropy < bestEntropy || (entropy == bestEntropy && random.nextBoolean())) {
                    bestEntropy = entropy;
                    bestX = x;
                    bestY = y;
                }
            }
        }

        if (bestX == -1) {
            return null;
        }

        return new int[]{bestX, bestY};
    }

    private int pickRandom(WfcCell cell, Random random) {
        int targetIndex = random.nextInt(cell.getPossibleTiles().size());
        int index = 0;

        for (int tile : cell.getPossibleTiles()) {
            if (index == targetIndex) {
                return tile;
            }
            index++;
        }

        throw new IllegalStateException("No tile selected");
    }

    private int[][] finalizeTiles(WfcCell[][] grid) {
        int height = grid.length;
        int width = grid[0].length;
        int[][] tiles = new int[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (!grid[y][x].isCollapsed()) {
                    throw new IllegalStateException("Grid has uncollapsed cells");
                }
                tiles[y][x] = grid[y][x].collapsedTile();
            }
        }

        return tiles;
    }
}
