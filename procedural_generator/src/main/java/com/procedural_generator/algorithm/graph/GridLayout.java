package com.procedural_generator.algorithm.graph;

import java.util.HashMap;
import java.util.Map;

public class GridLayout implements GraphLayoutStrategy {

    @Override
    public Map<Integer, Position> layout(GraphModel graph, int width, int height) {

        Map<Integer, Position> positions = new HashMap<>();

        int cols = (int) Math.sqrt(graph.nodes().size()) + 1;
        int rows = cols;

        int xStep = Math.max(1, width / cols);
        int yStep = Math.max(1, height / rows);

        int i = 0;

        for (Integer node : graph.nodes()) {

            int x = (i % cols) * xStep;
            int y = (i / cols) * yStep;

            positions.put(node, new Position(x, y));

            i++;
        }

        return positions;
    }
}
