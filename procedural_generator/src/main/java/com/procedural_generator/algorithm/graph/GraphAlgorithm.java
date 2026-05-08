package com.procedural_generator.algorithm.graph;

import com.procedural_generator.algorithm.GenerationAlgorithm;
import com.procedural_generator.algorithm.GenerationContext;
import com.procedural_generator.algorithm.GenerationResult;
import com.procedural_generator.domain.enums.AlgorithmType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

@Component
public class GraphAlgorithm implements GenerationAlgorithm {

    private final Map<String, GraphLayoutStrategy> strategies = Map.of(
            "GRID", new GridLayout(),
            "FORCE", new ForceDirectedLayout()
    );

    private final GraphTileRenderer renderer = new GraphTileRenderer();

    @Override
    public AlgorithmType getType() {
        return AlgorithmType.GRAPH;
    }

    @Override
    public GenerationResult generate(GenerationContext context) {

        GraphParams params = mapParams(context.params());

        GraphModel graph = createGraph(params, context.seed());

        GraphLayoutStrategy strategy =
                strategies.getOrDefault(params.layoutStrategy(), new GridLayout());

        Map<Integer, Position> positions =
                strategy.layout(graph, context.width(), context.height());

        int[][] tiles = renderer.render(
                graph,
                positions,
                context.width(),
                context.height()
        );

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

    private GraphModel createGraph(GraphParams params, long seed) {

        Random random = new Random(seed);

        Set<Integer> nodes = new HashSet<>();
        List<GraphModel.Edge> edges = new ArrayList<>();

        for (int i = 1; i <= params.nodeCount(); i++) {
            nodes.add(i);
        }

        for (int from : nodes) {
            for (int to : nodes) {

                if (from < to && random.nextDouble() <= params.connectivity()) {
                    edges.add(new GraphModel.Edge(
                            from,
                            to,
                            randomWeight(params, random)
                    ));
                }
            }
        }

        return new GraphModel(nodes, edges);
    }

    private double randomWeight(GraphParams params, Random random) {
        return params.minEdgeWeight()
                + (params.maxEdgeWeight() - params.minEdgeWeight())
                * random.nextDouble();
    }

    private int getInt(Map<String, Object> map, String key, int def) {
        Object v = map.get(key);
        return v instanceof Number ? ((Number) v).intValue() : def;
    }

    private double getDouble(Map<String, Object> map, String key, double def) {
        Object v = map.get(key);
        return v instanceof Number ? ((Number) v).doubleValue() : def;
    }

    private String getString(Map<String, Object> map, String key, String def) {
        Object v = map.get(key);
        return v != null ? v.toString() : def;
    }
}
