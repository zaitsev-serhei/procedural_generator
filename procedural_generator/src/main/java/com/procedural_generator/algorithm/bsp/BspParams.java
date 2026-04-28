package com.procedural_generator.algorithm.bsp;

public record BspParams(
        int roomCount,
        int minRoomSize,
        int maxRoomSize,
        int corridorWidth,
        double splitRatio
) {
}
