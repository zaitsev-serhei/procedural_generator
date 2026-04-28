package com.procedural_generator.algorithm.cellular;

public record CellularParams(
        double fillProbability,
        int iterations,
        int birthLimit,
        int deathLimit
) {
}
