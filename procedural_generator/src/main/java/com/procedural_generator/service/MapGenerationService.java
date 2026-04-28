package com.procedural_generator.service;

import com.procedural_generator.domain.enums.AlgorithmType;
import com.procedural_generator.domain.model.MapGeneration;

import java.util.Map;

public interface MapGenerationService {
    public MapGeneration generate(
            AlgorithmType algorithmType,
            long seed,
            int width,
            int height,
            Map<String, Object> params
    );
}
