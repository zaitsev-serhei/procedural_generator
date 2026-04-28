package com.procedural_generator.algorithm.bsp;

import com.procedural_generator.domain.enums.TileType;
import com.procedural_generator.domain.model.MapConnection;
import com.procedural_generator.domain.model.MapRoom;

public class CorridorBuilder {

    public MapConnection connect(
            int[][] tiles,
            MapRoom roomA,
            MapRoom roomB,
            int corridorWidth
    ) {
        int startX = roomA.getX() + roomA.getWidth() / 2;
        int startY = roomA.getY() + roomA.getHeight() / 2;

        int endX = roomB.getX() + roomB.getWidth() / 2;
        int endY = roomB.getY() + roomB.getHeight() / 2;

        carveHorizontal(tiles, startX, endX, startY, corridorWidth);
        carveVertical(tiles, startY, endY, endX, corridorWidth);

        return new MapConnection(roomA.getRoomIndex(), roomB.getRoomIndex());
    }

    private void carveHorizontal(int[][] tiles, int x1, int x2, int y, int corridorWidth) {
        int start = Math.min(x1, x2);
        int end = Math.max(x1, x2);

        for (int x = start; x <= end; x++) {
            for (int w = 0; w < corridorWidth; w++) {
                int tileY = y + w;
                if (isInside(tiles, x, tileY)) {
                    tiles[tileY][x] = TileType.CORRIDOR.getCode();
                }
            }
        }
    }

    private void carveVertical(int[][] tiles, int y1, int y2, int x, int corridorWidth) {
        int start = Math.min(y1, y2);
        int end = Math.max(y1, y2);

        for (int y = start; y <= end; y++) {
            for (int w = 0; w < corridorWidth; w++) {
                int tileX = x + w;
                if (isInside(tiles, tileX, y)) {
                    tiles[y][tileX] = TileType.CORRIDOR.getCode();
                }
            }
        }
    }

    private boolean isInside(int[][] tiles, int x, int y) {
        return y >= 0 && y < tiles.length && x >= 0 && x < tiles[0].length;
    }
}
