package com.procedural_generator.api.controller;

import com.procedural_generator.api.dto.response.MapSummaryDto;
import com.procedural_generator.api.dto.response.PagedResponseDto;
import com.procedural_generator.service.MapHistoryService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/maps")
public class MapHistoryController {

    private final MapHistoryService service;

    public MapHistoryController(MapHistoryService service) {
        this.service = service;
    }

    @GetMapping("/history")
    public PagedResponseDto<MapSummaryDto> history(Pageable pageable) {
        return service.getHistory(pageable);
    }
}
