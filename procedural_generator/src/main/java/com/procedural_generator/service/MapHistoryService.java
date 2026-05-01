package com.procedural_generator.service;

import com.procedural_generator.api.dto.response.MapSummaryDto;
import com.procedural_generator.api.dto.response.PagedResponseDto;
import com.procedural_generator.domain.model.MapGeneration;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface MapHistoryService {
    MapGeneration getById(UUID id);
    PagedResponseDto<MapSummaryDto> getHistory(Pageable pageable);
}
