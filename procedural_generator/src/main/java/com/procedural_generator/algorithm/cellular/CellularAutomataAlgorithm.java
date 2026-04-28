package com.procedural_generator.algorithm.cellular;

import com.procedural_generator.algorithm.GenerationAlgorithm;
import com.procedural_generator.algorithm.GenerationContext;
import com.procedural_generator.algorithm.GenerationResult;
import com.procedural_generator.domain.enums.AlgorithmType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

@Component
public class CellularAutomataAlgorithm implements GenerationAlgorithm {

    private final CellularSimulator simulator;

    public CellularAutomataAlgorithm(CellularSimulator simulator) {
        this.simulator = simulator;
    }

    @Override
    public GenerationResult generate(GenerationContext context) {

        Map<String, Object> params = context.params();

        CellularParams p = mapParams(params);

        int[][] grid = initGrid(context.width(), context.height(), p.fillProbability(), context.seed());

        int iterations = 0;

        for (int i = 0; i < p.iterations(); i++) {
            grid = simulator.step(grid, p.birthLimit(), p.deathLimit());
            iterations++;
        }

        return new GenerationResult(
                grid,
                new ArrayList<>(),
                new ArrayList<>(),
                iterations
        );
    }

    @Override
    public AlgorithmType getType() {
        return AlgorithmType.CELLULAR;
    }

    private int[][] initGrid(int width, int height, double fill, long seed) {

        int[][] grid = new int[height][width];
        Random random = new Random(seed);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y][x] = random.nextDouble() < fill ? 1 : 0;
            }
        }

        return grid;
    }

    private CellularParams mapParams(Map<String, Object> params) {

        return new CellularParams(
                (double) params.getOrDefault("fillProbability", 0.45),
                (int) params.getOrDefault("iterations", 5),
                (int) params.getOrDefault("birthLimit", 4),
                (int) params.getOrDefault("deathLimit", 3)
        );
    }
}
