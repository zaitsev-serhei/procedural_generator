package com.procedural_generator.algorithm;

import com.procedural_generator.algorithm.backtracking.DfsBacktrackingAlgorithm;
import com.procedural_generator.algorithm.bsp.BspAlgorithm;
import com.procedural_generator.algorithm.cellular.CellularAutomataAlgorithm;
import com.procedural_generator.algorithm.graph.GraphAlgorithm;
import com.procedural_generator.algorithm.perlin.PerlinNoiseAlgorithm;
import com.procedural_generator.algorithm.wfc.WfcAlgorithm;
import com.procedural_generator.domain.enums.AlgorithmType;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class AlgorithmRegistry {

    private final Map<AlgorithmType, GenerationAlgorithm> registry = new EnumMap<>(AlgorithmType.class);

    public AlgorithmRegistry(List<GenerationAlgorithm> algorithms) {
        for (GenerationAlgorithm algorithm : algorithms) {
            if (algorithm instanceof BspAlgorithm) {
                registry.put(AlgorithmType.BSP, algorithm);
            } else if (algorithm instanceof CellularAutomataAlgorithm) {
                registry.put(AlgorithmType.CELLULAR, algorithm);
            } else if (algorithm instanceof GraphAlgorithm) {
                registry.put(AlgorithmType.GRAPH, algorithm);
            } else if (algorithm instanceof WfcAlgorithm) {
                registry.put(AlgorithmType.WFC, algorithm);
            } else if (algorithm instanceof PerlinNoiseAlgorithm) {
                registry.put(AlgorithmType.PERLIN, algorithm);
            } else if (algorithm instanceof DfsBacktrackingAlgorithm) {
                registry.put(AlgorithmType.DFS_BACKTRACKING, algorithm);
            }
        }
    }

    public GenerationAlgorithm get(AlgorithmType type) {
        GenerationAlgorithm algorithm = registry.get(type);

        if (algorithm == null) {
            throw new IllegalArgumentException("No algorithm registered for type: " + type);
        }

        return algorithm;
    }
}
