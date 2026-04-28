package com.procedural_generator.algorithm;

import com.procedural_generator.domain.enums.AlgorithmType;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class AlgorithmRegistry {
    private final Map<AlgorithmType, GenerationAlgorithm> algorithms = new EnumMap<>(AlgorithmType.class);

    public AlgorithmRegistry(List<GenerationAlgorithm> algorithmImplementations) {
        for (GenerationAlgorithm algorithm : algorithmImplementations) {
            algorithms.put(algorithm.getType(), algorithm);
        }
    }

    public GenerationAlgorithm get(AlgorithmType algorithmType) {
        GenerationAlgorithm algorithm = algorithms.get(algorithmType);

        if (algorithm == null) {
            throw new IllegalArgumentException("No algorithm registered for type: " + algorithmType);
        }

        return algorithm;
    }
}
