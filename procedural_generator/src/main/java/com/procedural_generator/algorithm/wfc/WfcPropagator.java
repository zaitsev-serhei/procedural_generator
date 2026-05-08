package com.procedural_generator.algorithm.wfc;

import com.procedural_generator.domain.model.WfcTileset;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class WfcPropagator {

    private static final int[][] DIRS = {
            {0, -1}, // up
            {0, 1},  // down
            {-1, 0}, // left
            {1, 0}   // right
    };

    public boolean propagate(
            WfcCell[][] grid,
            int x,
            int y,
            Map<Integer, WfcTileset.Rule> rules
    ) {

        Deque<int[]> stack = new ArrayDeque<>();
        stack.push(new int[]{x, y});

        while (!stack.isEmpty()) {

            int[] c = stack.pop();

            int cx = c[0];
            int cy = c[1];

            for (int d = 0; d < 4; d++) {

                int nx = cx + DIRS[d][0];
                int ny = cy + DIRS[d][1];

                if (!inBounds(grid, nx, ny)) continue;

                WfcCell current = grid[cy][cx];
                WfcCell neighbor = grid[ny][nx];

                if (neighbor.isCollapsed()) continue;

                Set<Integer> allowed = computeAllowed(current, rules, d);

                int before = neighbor.entropy();
                neighbor.removeImpossible(allowed);

                if (neighbor.getPossibleTiles().isEmpty()) {
                    return false;
                }

                if (neighbor.entropy() < before) {
                    stack.push(new int[]{nx, ny});
                }
            }
        }

        return true;
    }

    private Set<Integer> computeAllowed(
            WfcCell cell,
            Map<Integer, WfcTileset.Rule> rules,
            int dir
    ) {

        Set<Integer> allowed = new HashSet<>();

        for (int tile : cell.getPossibleTiles()) {

            WfcTileset.Rule rule = rules.get(tile);
            if (rule == null) continue;

            List<Integer> values = switch (dir) {
                case 0 -> rule.up();
                case 1 -> rule.down();
                case 2 -> rule.left();
                case 3 -> rule.right();
                default -> List.of();
            };

            allowed.addAll(values);
        }

        return allowed;
    }

    private boolean inBounds(WfcCell[][] grid, int x, int y) {
        return y >= 0 && y < grid.length &&
                x >= 0 && x < grid[0].length;
    }
}
