package com.procedural_generator.algorithm;

import com.procedural_generator.domain.enums.AlgorithmType;

public interface GenerationAlgorithm {
    GenerationResult generate(GenerationContext context);
    AlgorithmType getType();
}
