package com.procedural_generator.service;

import com.procedural_generator.api.dto.response.MapSummaryDto;
import com.procedural_generator.api.dto.response.PagedResponseDto;
import org.springframework.data.domain.Pageable;

public interface MapHistoryService {
    PagedResponseDto<MapSummaryDto> getHistory(Pageable pageable);
}
