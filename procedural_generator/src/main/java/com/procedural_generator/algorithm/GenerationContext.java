package com.procedural_generator.algorithm;

import java.util.Map;

public record GenerationContext(
        long seed,
        int width,
        int height,
        Map<String, Object> params
) {
}
