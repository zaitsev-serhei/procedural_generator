package com.procedural_generator.algorithm.graph;

public record GraphParams(
        int nodeCount,
        double connectivity,
        String layoutStrategy,
        double minEdgeWeight,
        double maxEdgeWeight

) {
}
