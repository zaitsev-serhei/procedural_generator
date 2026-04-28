package com.procedural_generator.algorithm.perlin;

import com.procedural_generator.algorithm.GenerationAlgorithm;
import com.procedural_generator.algorithm.GenerationContext;
import com.procedural_generator.algorithm.GenerationResult;
import com.procedural_generator.domain.enums.AlgorithmType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;

@Component
public class PerlinNoiseAlgorithm implements GenerationAlgorithm {

    @Override
    public GenerationResult generate(GenerationContext context) {

        PerlinParams params = mapParams(context.params());

        PerlinNoiseGenerator generator = new PerlinNoiseGenerator(
                context.seed(),
                params.octaves(),
                params.persistence(),
                params.lacunarity(),
                params.scale()
        );

        int[][] tiles = new int[context.height()][context.width()];

        for (int y = 0; y < context.height(); y++) {
            for (int x = 0; x < context.width(); x++) {

                double value = generator.noise(x, y);

                tiles[y][x] = mapToTile(value, params.thresholds());
            }
        }

        return new GenerationResult(
                tiles,
                new ArrayList<>(),
                new ArrayList<>(),
                1
        );
    }

    @Override
    public AlgorithmType getType() {
        return AlgorithmType.PERLIN;
    }

    private int mapToTile(double value, double[] thresholds) {

        if (value < thresholds[0]) return 0;
        if (value < thresholds[1]) return 1;
        if (value < thresholds[2]) return 2;
        return 3;
    }

    private PerlinParams mapParams(Map<String, Object> params) {

        return new PerlinParams(
                (int) params.getOrDefault("octaves", 4),
                (double) params.getOrDefault("persistence", 0.5),
                (double) params.getOrDefault("lacunarity", 2.0),
                (double) params.getOrDefault("scale", 32.0),
                new double[]{
                        0.3, 0.5, 0.7
                }
        );
    }
}
