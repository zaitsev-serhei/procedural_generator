package com.procedural_generator.algorithm.graph;

import java.util.List;
import java.util.Set;

public record GraphModel(
        Set<Integer> nodes,
        List<Edge> edges
) {
    public record Edge(int from, int to, double weight) {}
}