package com.procedural_generator.algorithm.graph;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ForceDirectedLayout implements GraphLayoutStrategy {

    @Override
    public Map<Integer, Position> layout(GraphModel graph, int width, int height) {

        Map<Integer, Position> positions = new HashMap<>();
        Random random = new Random();

        for (Integer node : graph.nodes()) {

            positions.put(node,
                    new Position(
                            random.nextInt(Math.max(1, width)),
                            random.nextInt(Math.max(1, height))
                    )
            );
        }

        return positions;
    }
}
