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
import java.util.UUID;

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
        int attempt = 0;

        while (attempt < maxAttempts) {
            GenerationAlgorithm algorithm = algorithmRegistry.get(algorithmType);

            GenerationContext context = new GenerationContext(
                    seed,
                    width,
                    height,
                    params
            );

            GenerationResult result = algorithm.generate(context);

            MapGeneration mapGeneration = new MapGeneration(
                    UUID.randomUUID(),
                    algorithmType,
                    seed,
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
                return repository.save(mapGeneration);
            } catch (Exception e) {
                attempt++;
            }
        }
        throw new RuntimeException("Failed to generate valid map after " + maxAttempts + " attempts");
    }
}