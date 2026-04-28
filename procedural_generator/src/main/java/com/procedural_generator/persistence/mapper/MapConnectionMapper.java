package com.procedural_generator.persistence.mapper;

import com.procedural_generator.domain.model.MapConnection;
import com.procedural_generator.persistence.entity.MapConnectionEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MapConnectionMapper {

    public MapConnection toDomain(MapConnectionEntity entity) {
        if (entity == null) {
            return null;
        }

        return new MapConnection(
                entity.getFromIndex(),
                entity.getToIndex()
        );
    }

    public MapConnectionEntity toEntity(MapConnection domain) {
        if (domain == null) {
            return null;
        }

        MapConnectionEntity entity = new MapConnectionEntity();
        entity.setFromIndex(domain.getFromIndex());
        entity.setToIndex(domain.getToIndex());
        return entity;
    }

    public List<MapConnection> toDomainList(List<MapConnectionEntity> entities) {
        if (entities == null) {
            return List.of();
        }
        return entities.stream().map(this::toDomain).toList();
    }

    public List<MapConnectionEntity> toEntityList(List<MapConnection> domains) {
        if (domains == null) {
            return List.of();
        }
        return domains.stream().map(this::toEntity).toList();
    }
}
