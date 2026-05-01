package com.procedural_generator.validation.impl;

import com.procedural_generator.domain.enums.AlgorithmType;
import com.procedural_generator.domain.model.MapConnection;
import com.procedural_generator.domain.model.MapGeneration;
import com.procedural_generator.validation.MapValidator;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class ConnectivityValidator implements MapValidator {

    @Override
    public void validate(MapGeneration map) {

        if (map.getRooms().isEmpty()) {
            throw new IllegalStateException("No rooms generated");
        }

        Set<Integer> connected = new HashSet<>();

        for (MapConnection connection : map.getConnections()) {
            connected.add(connection.getFromIndex());
            connected.add(connection.getToIndex());
        }

        if (connected.size() < map.getRooms().size() / 2) {
            throw new IllegalStateException("Map is too fragmented (low connectivity)");
        }
    }

    @Override
    public boolean supports(AlgorithmType type) {
        return type == AlgorithmType.BSP || type == AlgorithmType.GRAPH;
    }
}
