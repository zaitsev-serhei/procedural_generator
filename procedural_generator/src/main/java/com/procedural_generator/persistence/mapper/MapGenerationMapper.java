package com.procedural_generator.persistence.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.procedural_generator.domain.enums.AlgorithmType;
import com.procedural_generator.domain.model.MapConnection;
import com.procedural_generator.domain.model.MapGeneration;
import com.procedural_generator.domain.model.MapRoom;
import com.procedural_generator.persistence.entity.MapGenerationEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
public class MapGenerationMapper {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public MapGenerationMapper() {
    }

    public MapGeneration toDomain(MapGenerationEntity entity) {
        if (entity == null) {
            return null;
        }

        return new MapGeneration(
                entity.getId(),
                parseAlgorithmType(entity.getAlgorithmType()),
                entity.getSeed(),
                entity.getWidth(),
                entity.getHeight(),
                entity.getTiles(),
                List.of(),
                List.of(),
                entity.getIterations(),
                entity.getCreatedAt()
        );
    }

    public MapGenerationEntity toEntity(MapGeneration domain) {
        if (domain == null) {
            return null;
        }

        MapGenerationEntity entity = new MapGenerationEntity();
        entity.setId(domain.getId());
        entity.setAlgorithmType(domain.getAlgorithmType().name());
        entity.setSeed(domain.getSeed());
        entity.setWidth(domain.getWidth());
        entity.setHeight(domain.getHeight());
        entity.setTiles(domain.getTiles());
        entity.setIterations(domain.getIterations());
        entity.setCreatedAt(domain.getCreatedAt());

        return entity;
    }

    public List<MapGeneration> toDomainList(List<MapGenerationEntity> entities) {
        if (entities == null) {
            return List.of();
        }
        return entities.stream().map(this::toDomain).toList();
    }

    public MapGeneration toDomain(
            MapGenerationEntity entity,
            List<MapRoom> rooms,
            List<MapConnection> connections
    ) {
        if (entity == null) {
            return null;
        }

        return new MapGeneration(
                entity.getId(),
                parseAlgorithmType(entity.getAlgorithmType()),
                entity.getSeed(),
                entity.getWidth(),
                entity.getHeight(),
                entity.getTiles(),
                rooms == null ? List.of() : rooms,
                connections == null ? List.of() : connections,
                entity.getIterations(),
                entity.getCreatedAt()
        );
    }

    private AlgorithmType parseAlgorithmType(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("algorithmType is empty");
        }
        return AlgorithmType.valueOf(value.trim().toUpperCase(Locale.ROOT));
    }

    private int[][] parseTiles(String json) {
        if (json == null || json.isBlank()) {
            return new int[0][0];
        }
        try {
            return objectMapper.readValue(json, int[][].class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Invalid tiles json", e);
        }
    }

    private String writeJson(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Cannot serialize value to json", e);
        }
    }
}
