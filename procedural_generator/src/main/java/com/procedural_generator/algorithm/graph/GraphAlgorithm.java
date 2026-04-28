package com.procedural_generator.algorithm.graph;

import java.util.Map;

public class GraphAlgorithm {

    private final Map<String, GraphLayoutStrategy> strategies = Map.of(
            "GRID", new GridLayout(),
            "FORCE", new ForceDirectedLayout()
    );

    public int[][] generate(MapGraph graph, int width, int height, String strategy) {
        return strategies.getOrDefault(strategy, new GridLayout())
                .layout(graph, width, height);
    }
}
