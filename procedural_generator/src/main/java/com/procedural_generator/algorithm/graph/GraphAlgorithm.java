package com.procedural_generator.algorithm.graph;

import com.procedural_generator.algorithm.GenerationAlgorithm;
import com.procedural_generator.algorithm.GenerationContext;
import com.procedural_generator.algorithm.GenerationResult;
import com.procedural_generator.domain.enums.AlgorithmType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

@Component
public class GraphAlgorithm implements GenerationAlgorithm {

    private final Map<String, GraphLayoutStrategy> strategies = Map.of(
            "GRID", new GridLayout(),
            "FORCE", new ForceDirectedLayout()
    );

    @Override
    public AlgorithmType getType() {
        return AlgorithmType.GRAPH;
    }

    @Override
    public GenerationResult generate(GenerationContext context) {
        GraphParams params = mapParams(context.params());
        MapGraph graph = createGraph(params, context.seed());
        int[][] tiles = strategies.getOrDefault(params.layoutStrategy(), new GridLayout())
                .layout(graph, context.width(), context.height());

        return new GenerationResult(
                tiles,
                new ArrayList<>(),
                new ArrayList<>(),
                params.nodeCount()
        );
    }

    private GraphParams mapParams(Map<String, Object> params) {
        int nodeCount = getInt(params, "nodeCount", 10);
        double connectivity = getDouble(params, "connectivity", 0.3);
        String layoutStrategy = getString(params, "layoutStrategy", "GRID");
        double minEdgeWeight = getDouble(params, "minEdgeWeight", 1.0);
        double maxEdgeWeight = getDouble(params, "maxEdgeWeight", 5.0);

        return new GraphParams(
                nodeCount,
                connectivity,
                layoutStrategy,
                minEdgeWeight,
                maxEdgeWeight
        );
    }

    private MapGraph createGraph(GraphParams params, long seed) {
        MapGraph graph = new MapGraph();
        Random random = new Random(seed);

        for (int i = 1; i <= params.nodeCount(); i++) {
            graph.addNode(i);
        }

        for (int from = 1; from <= params.nodeCount(); from++) {
            for (int to = from + 1; to <= params.nodeCount(); to++) {
                if (random.nextDouble() <= params.connectivity()) {
                    graph.addEdge(from, to, randomWeight(params, random));
                }
            }
        }

        return graph;
    }

    private double randomWeight(GraphParams params, Random random) {
        double min = Math.min(params.minEdgeWeight(), params.maxEdgeWeight());
        double max = Math.max(params.minEdgeWeight(), params.maxEdgeWeight());
        return min + (random.nextDouble() * (max - min));
    }

    private int getInt(Map<String, Object> map, String key, int defaultValue) {
        Object val = map.get(key);
        return val instanceof Number ? ((Number) val).intValue() : defaultValue;
    }

    private double getDouble(Map<String, Object> map, String key, double defaultValue) {
        Object val = map.get(key);
        return val instanceof Number ? ((Number) val).doubleValue() : defaultValue;
    }

    private String getString(Map<String, Object> map, String key, String defaultValue) {
        Object val = map.get(key);
        return val instanceof String text && !text.isBlank() ? text : defaultValue;
    }
}
