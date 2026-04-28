package com.procedural_generator.persistence.repository;

import com.procedural_generator.persistence.entity.MapGenerationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MapGenerationJpaRepository extends JpaRepository<MapGenerationEntity, UUID> {
}
