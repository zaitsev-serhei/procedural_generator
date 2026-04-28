package com.procedural_generator.validation.impl;

import com.procedural_generator.domain.model.MapGeneration;
import com.procedural_generator.validation.MapValidator;
import org.springframework.stereotype.Component;

@Component
public class GenerationTimeoutValidator implements MapValidator {

    private static final long MAX_ITERATIONS = 1_000_000;

    @Override
    public void validate(MapGeneration mapGeneration) {

        if (mapGeneration.getIterations() > MAX_ITERATIONS) {
            throw new IllegalStateException(
                    "Generation exceeded allowed complexity: " + mapGeneration.getIterations()
            );
        }
    }
}
