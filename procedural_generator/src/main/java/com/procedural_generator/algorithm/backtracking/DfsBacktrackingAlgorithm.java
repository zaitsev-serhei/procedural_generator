package com.procedural_generator.algorithm.backtracking;

import com.procedural_generator.algorithm.GenerationAlgorithm;
import com.procedural_generator.algorithm.GenerationContext;
import com.procedural_generator.algorithm.GenerationResult;
import com.procedural_generator.domain.enums.AlgorithmType;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
public class DfsBacktrackingAlgorithm implements GenerationAlgorithm {

    private static final int WALL = 0;
    private static final int FLOOR = 1;

    @Override
    public AlgorithmType getType() {
        return AlgorithmType.DFS_BACKTRACKING;
    }

    @Override
    public GenerationResult generate(GenerationContext context) {

        DfsBacktrackingParams params = mapParams(context.params());

        int width = ensureOdd(context.width());
        int height = ensureOdd(context.height());

        int[][] tiles = createFilledMap(width, height);

        Random random = new Random(context.seed());

        generateMaze(
                tiles,
                random,
                1,
                1,
                params.corridorWidth()
        );

        return new GenerationResult(
                tiles,
                List.of(),
                List.of(),
                width * height
        );
    }

    private void generateMaze(
            int[][] tiles,
            Random random,
            int startX,
            int startY,
            int corridorWidth
    ) {

        Deque<MazeCell> stack = new ArrayDeque<>();

        tiles[startY][startX] = FLOOR;

        stack.push(new MazeCell(startX, startY));

        while (!stack.isEmpty()) {

            MazeCell current = stack.peek();

            List<Direction> directions =
                    new ArrayList<>(Arrays.asList(Direction.values()));

            Collections.shuffle(directions, random);

            boolean moved = false;

            for (Direction direction : directions) {

                // IMPORTANT
                int nx = current.x() + direction.dx() * 2;
                int ny = current.y() + direction.dy() * 2;

                if (!isInside(tiles, nx, ny)) {
                    continue;
                }

                if (tiles[ny][nx] == FLOOR) {
                    continue;
                }

                // wall between
                int wallX = current.x() + direction.dx();
                int wallY = current.y() + direction.dy();

                carveCorridor(
                        tiles,
                        wallX,
                        wallY,
                        corridorWidth
                );

                carveCorridor(
                        tiles,
                        nx,
                        ny,
                        corridorWidth
                );

                stack.push(new MazeCell(nx, ny));

                moved = true;
                break;
            }

            if (!moved) {
                stack.pop();
            }
        }
    }

    private boolean isInside(int[][] tiles, int x, int y) {

        return x > 0
                && y > 0
                && y < tiles.length - 1
                && x < tiles[0].length - 1;
    }

    private int[][] createFilledMap(int width, int height) {

        int[][] map = new int[height][width];

        for (int y = 0; y < height; y++) {
            Arrays.fill(map[y], WALL);
        }

        return map;
    }

    private int ensureOdd(int value) {
        return value % 2 == 0
                ? value - 1
                : value;
    }

    private DfsBacktrackingParams mapParams(Map<String, Object> params) {

        int corridorWidth = 1;

        Object raw = params.get("corridorWidth");

        if (raw instanceof Number n) {
            corridorWidth = Math.max(1, n.intValue());
        }

        return new DfsBacktrackingParams(corridorWidth);
    }

    private void carveCorridor(
            int[][] tiles,
            int centerX,
            int centerY,
            int corridorWidth
    ) {

        int radius = Math.max(0, corridorWidth / 2);

        for (int dy = -radius; dy <= radius; dy++) {
            for (int dx = -radius; dx <= radius; dx++) {

                int x = centerX + dx;
                int y = centerY + dy;

                if (y <= 0 || y >= tiles.length - 1) {
                    continue;
                }

                if (x <= 0 || x >= tiles[0].length - 1) {
                    continue;
                }

                tiles[y][x] = FLOOR;
            }
        }
    }
}
