package com.procedural_generator.persistence.repository;

import com.procedural_generator.persistence.entity.MapConnectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MapConnectionJpaRepository extends JpaRepository<MapConnectionEntity, Long> {

    List<MapConnectionEntity> findByMapGenerationId(UUID mapGenerationId);
}
