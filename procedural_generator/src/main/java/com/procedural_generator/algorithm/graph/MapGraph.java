package com.procedural_generator.algorithm.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MapGraph {

    private final Map<Integer, List<Edge>> adjacency = new HashMap<>();

    public void addNode(int nodeId) {
        adjacency.putIfAbsent(nodeId, new ArrayList<>());
    }

    public void addEdge(int from, int to, double weight) {
        adjacency.putIfAbsent(from, new ArrayList<>());
        adjacency.putIfAbsent(to, new ArrayList<>());

        adjacency.get(from).add(new Edge(to, weight));
        adjacency.get(to).add(new Edge(from, weight));
    }

    public List<Edge> getNeighbors(int nodeId) {
        return adjacency.getOrDefault(nodeId, List.of());
    }

    public Set<Integer> getNodes() {
        return adjacency.keySet();
    }

    public record Edge(int to, double weight) {
    }
}
