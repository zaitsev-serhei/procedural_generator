package com.procedural_generator.algorithm;

import com.procedural_generator.domain.model.MapConnection;
import com.procedural_generator.domain.model.MapRoom;

import java.util.List;

public record GenerationResult(
        int[][] tiles,
        List<MapRoom> rooms,
        List<MapConnection> connections,
        int iterations
) {
}
