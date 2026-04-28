package com.procedural_generator.algorithm.graph;

public interface GraphLayoutStrategy {

    int[][] layout(MapGraph graph, int width, int height);
}
