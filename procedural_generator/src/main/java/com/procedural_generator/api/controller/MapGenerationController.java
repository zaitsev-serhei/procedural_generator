package com.procedural_generator.api.controller;

import com.procedural_generator.api.dto.request.GenerationRequestDto;
import com.procedural_generator.api.dto.response.ConnectionDto;
import com.procedural_generator.api.dto.response.MapMetadataDto;
import com.procedural_generator.api.dto.response.MapResponseDto;
import com.procedural_generator.api.dto.response.RoomDto;
import com.procedural_generator.domain.model.MapConnection;
import com.procedural_generator.domain.model.MapGeneration;
import com.procedural_generator.domain.model.MapRoom;
import com.procedural_generator.service.MapGenerationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/maps")
public class MapGenerationController {

    private final MapGenerationService mapGenerationService;

    public MapGenerationController(MapGenerationService mapGenerationService) {
        this.mapGenerationService = mapGenerationService;
    }

    @PostMapping("/generate")
    public MapResponseDto generate(@RequestBody @Valid GenerationRequestDto request) {
        System.out.println(request);
        Map<String, Object> params = request.params();

        MapGeneration generation = mapGenerationService.generate(
                request.algorithmType(),
                request.seed(),
                request.width(),
                request.height(),
                params
        );

        return toResponseDto(generation);
    }


    private MapResponseDto toResponseDto(MapGeneration generation) {

        List<RoomDto> rooms = generation.getRooms().stream()
                .map(this::toRoomDto)
                .toList();

        List<ConnectionDto> connections = generation.getConnections().stream()
                .map(this::toConnectionDto)
                .toList();

        MapMetadataDto metadata = new MapMetadataDto(
                generation.getId().toString(),
                generation.getAlgorithmType().name(),
                generation.getSeed(),
                generation.getWidth(),
                generation.getHeight(),
                generation.getIterations(),
                generation.getCreatedAt()
        );

        return new MapResponseDto(
                generation.getTiles(),
                rooms,
                connections,
                metadata
        );
    }


    private RoomDto toRoomDto(MapRoom room) {
        return new RoomDto(
                room.getX(),
                room.getY(),
                room.getWidth(),
                room.getHeight(),
                room.getRoomIndex()
        );
    }

    private ConnectionDto toConnectionDto(MapConnection connection) {
        return new ConnectionDto(
                connection.getFromIndex(),
                connection.getToIndex()
        );
    }
}
