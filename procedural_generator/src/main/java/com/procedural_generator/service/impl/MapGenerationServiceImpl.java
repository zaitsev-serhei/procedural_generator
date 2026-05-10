package com.procedural_generator.service.impl;

import com.procedural_generator.algorithm.AlgorithmRegistry;
import com.procedural_generator.algorithm.GenerationAlgorithm;
import com.procedural_generator.algorithm.GenerationContext;
import com.procedural_generator.algorithm.GenerationResult;
import com.procedural_generator.domain.enums.AlgorithmType;
import com.procedural_generator.domain.model.MapGeneration;
import com.procedural_generator.domain.port.MapGenerationRepository;
import com.procedural_generator.service.MapGenerationService;
import com.procedural_generator.validation.MapValidator;
import com.procedural_generator.validation.impl.CompositeMapValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class MapGenerationServiceImpl implements MapGenerationService {
    private final AlgorithmRegistry algorithmRegistry;
    private final MapGenerationRepository repository;
    private final CompositeMapValidator validator;

    public MapGenerationServiceImpl(
            AlgorithmRegistry algorithmRegistry,
            MapGenerationRepository repository,
            CompositeMapValidator validator) {
        this.algorithmRegistry = algorithmRegistry;
        this.repository = repository;
        this.validator = validator;
    }

    @Override
    public MapGeneration generate(
            AlgorithmType algorithmType,
            long seed,
            int width,
            int height,
            Map<String, Object> params
    ) {
        int maxAttempts = 5;

        GenerationAlgorithm algorithm = algorithmRegistry.get(algorithmType);

        RuntimeException lastException = null;

        if (algorithmType == AlgorithmType.DFS_BACKTRACKING) {
            width = normalizeMazeDimension(width);
            height = normalizeMazeDimension(height);
        }

        for (int attempt = 0; attempt < maxAttempts; attempt++) {

            long currentSeed = seed + attempt; // 🔥 важливо

            GenerationContext context = new GenerationContext(
                    currentSeed,
                    width,
                    height,
                    params
            );

            GenerationResult result = algorithm.generate(context);

            MapGeneration mapGeneration = new MapGeneration(
                    null,
                    algorithmType,
                    currentSeed,
                    width,
                    height,
                    result.tiles(),
                    result.rooms(),
                    result.connections(),
                    result.iterations(),
                    LocalDateTime.now()
            );

            try {
                validator.validate(mapGeneration);

                System.out.println("✅ Map valid on attempt " + (attempt + 1));

                return repository.save(mapGeneration);

            } catch (Exception e) {
                lastException = new RuntimeException("Attempt " + (attempt + 1) + " failed: " + e.getMessage(), e);

                System.out.println("❌ Attempt " + (attempt + 1) + " failed: " + e.getMessage());
            }
        }

        throw new RuntimeException(
                "Failed to generate valid map after " + maxAttempts + " attempts",
                lastException
        );
    }

    private int normalizeMazeDimension(int value) {
        return value % 2 == 0
                ? value - 1
                : value;
    }
}
