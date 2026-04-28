package com.procedural_generator.persistence.adapter;

import com.procedural_generator.domain.model.WfcTileset;
import com.procedural_generator.domain.port.WfcTilesetRepository;
import com.procedural_generator.persistence.mapper.WfcTilesetMapper;
import com.procedural_generator.persistence.repository.WfcTilesetJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class WfcTilesetRepositoryAdapter implements WfcTilesetRepository {

    private final WfcTilesetJpaRepository jpaRepository;
    private final WfcTilesetMapper mapper;

    public WfcTilesetRepositoryAdapter(WfcTilesetJpaRepository jpaRepository, WfcTilesetMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<WfcTileset> findByName(String name) {
        return jpaRepository.findByName(name).map(mapper::toDomain);
    }

    @Override
    public WfcTileset save(WfcTileset tileset) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(tileset)));
    }

    @Override
    public List<WfcTileset> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }
}
