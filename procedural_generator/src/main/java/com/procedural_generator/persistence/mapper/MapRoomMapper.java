package com.procedural_generator.persistence.mapper;

import com.procedural_generator.domain.model.MapRoom;
import com.procedural_generator.persistence.entity.MapRoomEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MapRoomMapper {

    public MapRoom toDomain(MapRoomEntity entity) {
        if (entity == null) {
            return null;
        }

        return new MapRoom(
                entity.getX(),
                entity.getY(),
                entity.getWidth(),
                entity.getHeight(),
                entity.getRoomIndex()
        );
    }

    public MapRoomEntity toEntity(MapRoom domain) {
        if (domain == null) {
            return null;
        }

        MapRoomEntity entity = new MapRoomEntity();
        entity.setX(domain.getX());
        entity.setY(domain.getY());
        entity.setWidth(domain.getWidth());
        entity.setHeight(domain.getHeight());
        entity.setRoomIndex(domain.getRoomIndex());
        return entity;
    }

    public List<MapRoom> toDomainList(List<MapRoomEntity> entities) {
        if (entities == null) {
            return List.of();
        }
        return entities.stream().map(this::toDomain).toList();
    }

    public List<MapRoomEntity> toEntityList(List<MapRoom> domains) {
        if (domains == null) {
            return List.of();
        }
        return domains.stream().map(this::toEntity).toList();
    }
}
