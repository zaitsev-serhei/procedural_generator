package com.procedural_generator.persistence.repository;

import com.procedural_generator.persistence.entity.WfcTilesetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WfcTilesetJpaRepository extends JpaRepository<WfcTilesetEntity, Long> {

    Optional<WfcTilesetEntity> findByName(String name);
}
