package com.procedural_generator.service;

import com.procedural_generator.api.dto.response.TileTypeDto;

import java.util.List;

public interface TilesetService {
    List<TileTypeDto> getTileTypes();
}
