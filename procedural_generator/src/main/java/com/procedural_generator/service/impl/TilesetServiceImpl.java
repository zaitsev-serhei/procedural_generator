package com.procedural_generator.service.impl;

import com.procedural_generator.api.dto.response.TileTypeDto;
import com.procedural_generator.domain.enums.TileType;
import com.procedural_generator.service.TilesetService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TilesetServiceImpl implements TilesetService {
    public List<TileTypeDto> getTileTypes() {
        return java.util.Arrays.stream(TileType.values())
                .map(t -> new TileTypeDto(t.getCode(), t.name()))
                .toList();
    }
}
