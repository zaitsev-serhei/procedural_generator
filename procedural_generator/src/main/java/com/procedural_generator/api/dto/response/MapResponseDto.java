package com.procedural_generator.api.dto.response;

import java.util.List;

public record MapResponseDto(
        int[][] tiles,
        List<RoomDto> rooms,
        List<ConnectionDto> connections,
        MapMetadataDto metadata
) {
}
