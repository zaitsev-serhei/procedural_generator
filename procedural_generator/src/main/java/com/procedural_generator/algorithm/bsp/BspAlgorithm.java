package com.procedural_generator.algorithm.bsp;

import com.procedural_generator.algorithm.GenerationAlgorithm;
import com.procedural_generator.algorithm.GenerationContext;
import com.procedural_generator.algorithm.GenerationResult;
import com.procedural_generator.domain.enums.AlgorithmType;
import com.procedural_generator.domain.enums.TileType;
import com.procedural_generator.domain.model.MapConnection;
import com.procedural_generator.domain.model.MapRoom;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
public class BspAlgorithm implements GenerationAlgorithm {
    private final BspSplitter splitter = new BspSplitter();
    private final CorridorBuilder corridorBuilder = new CorridorBuilder();

    @Override
    public AlgorithmType getType() {
        return AlgorithmType.BSP;
    }

    @Override
    public GenerationResult generate(GenerationContext context) {
        BspParams params = mapParams(context.params());

        int[][] tiles = initializeTiles(context.width(), context.height());

        BspNode root = new BspNode(0, 0, context.width(), context.height());
        Random random = new Random(context.seed());

        List<BspNode> leaves = new ArrayList<>();
        splitRecursively(root, params, random, leaves);

        List<MapRoom> rooms = createRooms(leaves, tiles, params, random);
        List<MapConnection> connections = connectRooms(tiles, rooms, params);

        return new GenerationResult(
                tiles,
                rooms,
                connections,
                leaves.size()
        );
    }

    private BspParams mapParams(Map<String, Object> params) {
        return new BspParams(
                ((Number) params.get("roomCount")).intValue(),
                ((Number) params.get("minRoomSize")).intValue(),
                ((Number) params.get("maxRoomSize")).intValue(),
                ((Number) params.get("corridorWidth")).intValue(),
                ((Number) params.get("splitRatio")).doubleValue()
        );
    }

    private int[][] initializeTiles(int width, int height) {
        int[][] tiles = new int[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles[y][x] = TileType.WALL.getCode();
            }
        }

        return tiles;
    }

    private void splitRecursively(
            BspNode node,
            BspParams params,
            Random random,
            List<BspNode> leaves
    ) {
        if (leaves.size() >= params.roomCount()) {
            leaves.add(node);
            return;
        }

        boolean success = splitter.split(
                node,
                params.minRoomSize(),
                params.splitRatio(),
                random
        );

        if (!success) {
            leaves.add(node);
            return;
        }

        splitRecursively(node.getLeftChild(), params, random, leaves);
        splitRecursively(node.getRightChild(), params, random, leaves);
    }

    private List<MapRoom> createRooms(
            List<BspNode> leaves,
            int[][] tiles,
            BspParams params,
            Random random
    ) {
        List<MapRoom> rooms = new ArrayList<>();

        for (int i = 0; i < leaves.size(); i++) {
            BspNode leaf = leaves.get(i);

            int roomWidth = randomRange(
                    params.minRoomSize(),
                    Math.min(params.maxRoomSize(), leaf.getWidth() - 1),
                    random
            );

            int roomHeight = randomRange(
                    params.minRoomSize(),
                    Math.min(params.maxRoomSize(), leaf.getHeight() - 1),
                    random
            );

            int roomX = leaf.getX() + randomRange(0, Math.max(1, leaf.getWidth() - roomWidth), random);
            int roomY = leaf.getY() + randomRange(0, Math.max(1, leaf.getHeight() - roomHeight), random);

            MapRoom room = new MapRoom(
                    roomX,
                    roomY,
                    roomWidth,
                    roomHeight,
                    i
            );

            leaf.setRoom(room);
            rooms.add(room);

            carveRoom(tiles, room);
        }

        return rooms;
    }

    private void carveRoom(int[][] tiles, MapRoom room) {
        for (int y = room.getY(); y < room.getY() + room.getHeight(); y++) {
            for (int x = room.getX(); x < room.getX() + room.getWidth(); x++) {
                if (y >= 0 && y < tiles.length && x >= 0 && x < tiles[0].length) {
                    tiles[y][x] = TileType.FLOOR.getCode();
                }
            }
        }
    }

    private List<MapConnection> connectRooms(
            int[][] tiles,
            List<MapRoom> rooms,
            BspParams params
    ) {
        List<MapConnection> connections = new ArrayList<>();

        for (int i = 1; i < rooms.size(); i++) {
            MapConnection connection = corridorBuilder.connect(
                    tiles,
                    rooms.get(i - 1),
                    rooms.get(i),
                    params.corridorWidth()
            );

            connections.add(connection);
        }

        return connections;
    }

    private int randomRange(int min, int max, Random random) {
        if (max <= min) {
            return min;
        }
        return random.nextInt(max - min + 1) + min;
    }
}
