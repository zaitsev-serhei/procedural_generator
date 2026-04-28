package com.procedural_generator.domain.port;

import com.procedural_generator.domain.model.MapGeneration;
import com.procedural_generator.persistence.entity.MapGenerationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface MapGenerationRepository {
    MapGeneration save(MapGeneration mapGeneration);

    MapGeneration findById(UUID id);

    Page<MapGenerationEntity> findAll(Pageable pageable);
}
