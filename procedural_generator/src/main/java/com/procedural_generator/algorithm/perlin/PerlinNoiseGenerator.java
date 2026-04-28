package com.procedural_generator.algorithm.perlin;

public class PerlinNoiseGenerator {

    private final long seed;
    private final int octaves;
    private final double persistence;
    private final double lacunarity;
    private final double scale;

    public PerlinNoiseGenerator(long seed, int octaves, double persistence, double lacunarity, double scale) {
        this.seed = seed;
        this.octaves = octaves;
        this.persistence = persistence;
        this.lacunarity = lacunarity;
        this.scale = scale;
    }

    public double noise(int x, int y) {

        double amplitude = 1.0;
        double frequency = 1.0;
        double value = 0.0;
        double max = 0.0;

        for (int i = 0; i < octaves; i++) {

            value += pseudoNoise(x * frequency, y * frequency) * amplitude;

            max += amplitude;
            amplitude *= persistence;
            frequency *= lacunarity;
        }

        return value / max;
    }

    private double pseudoNoise(double x, double y) {

        long n = (long) (x * 49632 + y * 325176 + seed * 918273);
        n = (n << 13) ^ n;

        return 1.0 - ((n * (n * n * 15731 + 789221) + 1376312589) & 0x7fffffff) / 1073741824.0;
    }
}
