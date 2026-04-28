package com.procedural_generator.algorithm.wfc;

import com.procedural_generator.domain.model.WfcTileset;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class WfcPropagator {

    public boolean propagate(
            WfcCell[][] grid,
            int startX,
            int startY,
            Map<Integer, WfcTileset.Rule> rules
    ) {
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{startX, startY});

        while (!queue.isEmpty()) {
            int[] pos = queue.poll();
            int x = pos[0];
            int y = pos[1];

            if (updateNeighbor(grid, x, y, x, y - 1, "up", rules)) {
                if (grid[y - 1][x].entropy() == 0) {
                    return false;
                }
                queue.add(new int[]{x, y - 1});
            }

            if (updateNeighbor(grid, x, y, x, y + 1, "down", rules)) {
                if (grid[y + 1][x].entropy() == 0) {
                    return false;
                }
                queue.add(new int[]{x, y + 1});
            }

            if (updateNeighbor(grid, x, y, x - 1, y, "left", rules)) {
                if (grid[y][x - 1].entropy() == 0) {
                    return false;
                }
                queue.add(new int[]{x - 1, y});
            }

            if (updateNeighbor(grid, x, y, x + 1, y, "right", rules)) {
                if (grid[y][x + 1].entropy() == 0) {
                    return false;
                }
                queue.add(new int[]{x + 1, y});
            }
        }

        return true;
    }

    private boolean updateNeighbor(
            WfcCell[][] grid,
            int sourceX,
            int sourceY,
            int targetX,
            int targetY,
            String direction,
            Map<Integer, WfcTileset.Rule> rules
    ) {
        if (targetY < 0 || targetY >= grid.length || targetX < 0 || targetX >= grid[0].length) {
            return false;
        }

        WfcCell sourceCell = grid[sourceY][sourceX];
        WfcCell targetCell = grid[targetY][targetX];

        Set<Integer> allowed = new HashSet<>();
        for (int sourceTile : sourceCell.getPossibleTiles()) {
            WfcTileset.Rule rule = rules.get(sourceTile);
            if (rule == null) {
                continue;
            }

            List<Integer> directionalAllowed = switch (direction) {
                case "up" -> rule.up();
                case "down" -> rule.down();
                case "left" -> rule.left();
                case "right" -> rule.right();
                default -> List.of();
            };

            allowed.addAll(directionalAllowed);
        }

        if (allowed.isEmpty()) {
            return false;
        }

        return targetCell.constrain(allowed);
    }
}
