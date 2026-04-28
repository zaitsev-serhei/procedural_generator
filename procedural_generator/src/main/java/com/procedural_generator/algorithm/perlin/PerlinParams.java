package com.procedural_generator.algorithm.perlin;

public record PerlinParams(
        int octaves,
        double persistence,
        double lacunarity,
        double scale,
        double[] thresholds
) {
}
