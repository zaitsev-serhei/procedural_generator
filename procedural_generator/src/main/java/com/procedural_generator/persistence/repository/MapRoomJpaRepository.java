package com.procedural_generator.persistence.repository;

import com.procedural_generator.persistence.entity.MapRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MapRoomJpaRepository extends JpaRepository<MapRoomEntity, Long> {

    List<MapRoomEntity> findByMapGenerationId(UUID mapGenerationId);
}
