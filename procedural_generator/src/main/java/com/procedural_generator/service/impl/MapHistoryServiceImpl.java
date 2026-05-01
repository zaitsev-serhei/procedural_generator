package com.procedural_generator.service.impl;

import com.procedural_generator.api.dto.response.MapSummaryDto;
import com.procedural_generator.api.dto.response.PagedResponseDto;
import com.procedural_generator.api.exception.ResourceNotFoundException;
import com.procedural_generator.domain.model.MapGeneration;
import com.procedural_generator.domain.port.MapGenerationRepository;
import com.procedural_generator.persistence.entity.MapGenerationEntity;
import com.procedural_generator.service.MapHistoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MapHistoryServiceImpl implements MapHistoryService {

    private final MapGenerationRepository repository;

    public MapHistoryServiceImpl(MapGenerationRepository repository) {
        this.repository = repository;
    }

    @Override
    public MapGeneration getById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("Map id must not be null");
        }

        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Map generation not found by id: " + id));
    }

    public PagedResponseDto<MapSummaryDto> getHistory(Pageable pageable) {

        Page<MapGenerationEntity> page = repository.findAll(pageable);

        return new PagedResponseDto<>(
                page.getContent().stream()
                        .map(this::toDto)
                        .toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.hasNext()
        );
    }

    private MapSummaryDto toDto(MapGenerationEntity entity) {
        return new MapSummaryDto(
                entity.getId().toString(),
                entity.getAlgorithmType(),
                entity.getSeed(),
                entity.getWidth(),
                entity.getHeight(),
                entity.getIterations(),
                entity.getCreatedAt()
        );
    }
}
