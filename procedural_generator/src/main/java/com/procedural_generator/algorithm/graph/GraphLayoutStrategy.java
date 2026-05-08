package com.procedural_generator.algorithm.graph;

import java.util.Map;

public interface GraphLayoutStrategy {

    Map<Integer, Position> layout(GraphModel graph, int width, int height);
}
