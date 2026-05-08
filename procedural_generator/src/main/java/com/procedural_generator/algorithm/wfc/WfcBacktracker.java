package com.procedural_generator.algorithm.wfc;

import com.procedural_generator.domain.model.WfcTileset;

import java.util.ArrayList;
import java.util.List;
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

        Exception last = null;

        for (int attempt = 0; attempt < maxRetries; attempt++) {
            try {
                return run(width, height, rules, new Random(seed + attempt));
            } catch (Exception e) {
                last = e;
            }
        }

        throw new IllegalStateException("WFC failed after retries", last);
    }

    private int[][] run(
            int width,
            int height,
            Map<Integer, WfcTileset.Rule> rules,
            Random random
    ) {

        Set<Integer> allTiles = rules.keySet();

        if (allTiles.isEmpty()) {
            throw new IllegalStateException("Tileset rules are empty");
        }

        // 🧠 STEP 1: INIT GRID (SUPERPOSITION)
        WfcCell[][] grid = initGrid(width, height, allTiles);

        while (true) {

            // 🧠 STEP 2: FIND LOWEST ENTROPY CELL
            int[] cell = findLowestEntropy(grid, random);

            if (cell == null) {
                return finalize(grid);
            }

            int x = cell[0];
            int y = cell[1];

            // 🧠 STEP 3: COLLAPSE
            int chosen = pick(grid[y][x], random);
            grid[y][x].collapseTo(chosen);

            // 🧠 STEP 4: PROPAGATE
            boolean ok = propagator.propagate(grid, x, y, rules);

            if (!ok) {
                throw new IllegalStateException("Contradiction");
            }
        }
    }

    private WfcCell[][] initGrid(int w, int h, Set<Integer> tiles) {

        WfcCell[][] grid = new WfcCell[h][w];

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                grid[y][x] = new WfcCell(tiles);
            }
        }

        return grid;
    }

    private int[] findLowestEntropy(WfcCell[][] grid, Random r) {

        int best = Integer.MAX_VALUE;
        int bx = -1, by = -1;

        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {

                WfcCell c = grid[y][x];

                if (c.isCollapsed()) continue;

                int e = c.entropy();

                if (e < best || (e == best && r.nextBoolean())) {
                    best = e;
                    bx = x;
                    by = y;
                }
            }
        }

        return bx == -1 ? null : new int[]{bx, by};
    }

    private int pick(WfcCell cell, Random r) {

        List<Integer> list = new ArrayList<>(cell.getPossibleTiles());
        return list.get(r.nextInt(list.size()));
    }

    private int[][] finalize(WfcCell[][] grid) {

        int[][] out = new int[grid.length][grid[0].length];

        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {

                if (!grid[y][x].isCollapsed()) {
                    throw new IllegalStateException("Uncollapsed cell");
                }

                out[y][x] = grid[y][x].collapsedTile();
            }
        }

        return out;
    }
}
