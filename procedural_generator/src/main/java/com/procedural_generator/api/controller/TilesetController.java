package com.procedural_generator.api.controller;

import com.procedural_generator.api.dto.response.TileTypeDto;
import com.procedural_generator.service.TilesetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tiles")
public class TilesetController {

    private final TilesetService service;

    public TilesetController(TilesetService service) {
        this.service = service;
    }

    @GetMapping
    public List<TileTypeDto> getTileTypes() {
        return service.getTileTypes();
    }
}
