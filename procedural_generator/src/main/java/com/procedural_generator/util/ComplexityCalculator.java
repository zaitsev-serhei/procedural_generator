package com.procedural_generator.util;

import com.procedural_generator.domain.model.MapGeneration;

public class ComplexityCalculator {

    public static double calculate(MapGeneration map) {

        int tiles = map.getWidth() * map.getHeight();
        int rooms = map.getRooms().size();
        int connections = map.getConnections().size();

        if (tiles == 0) return 0.0;

        return (rooms * 1.5 + connections * 2.0) / tiles;
    }
}
