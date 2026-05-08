package com.procedural_generator.algorithm.graph;

import com.procedural_generator.domain.enums.TileType;

import java.util.Map;

public class GraphTileRenderer {

    public int[][] render(
            GraphModel graph,
            Map<Integer, Position> positions,
            int width,
            int height
    ) {

        int[][] tiles = new int[height][width];

        // 1. init void
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles[y][x] = TileType.VOID.ordinal();
            }
        }

        // 2. draw nodes
        for (Map.Entry<Integer, Position> entry : positions.entrySet()) {
            Position p = entry.getValue();

            if (inBounds(p, width, height)) {
                tiles[p.y()][p.x()] = TileType.FLOOR.ordinal();
            }
        }

        // 3. draw edges (corridors)
        for (GraphModel.Edge edge : graph.edges()) {

            Position a = positions.get(edge.from());
            Position b = positions.get(edge.to());

            if (a != null && b != null) {
                drawLine(tiles, a, b);
            }
        }

        return tiles;
    }

    private void drawLine(int[][] grid, Position a, Position b) {

        int x = a.x();
        int y = a.y();

        while (x != b.x() || y != b.y()) {

            if (y >= 0 && y < grid.length &&
                    x >= 0 && x < grid[0].length) {

                grid[y][x] = TileType.CORRIDOR.ordinal();
            }

            if (x < b.x()) x++;
            else if (x > b.x()) x--;

            if (y < b.y()) y++;
            else if (y > b.y()) y--;
        }
    }

    private boolean inBounds(Position p, int w, int h) {
        return p.x() >= 0 && p.x() < w &&
                p.y() >= 0 && p.y() < h;
    }
}
