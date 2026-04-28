package com.procedural_generator.api.dto.response;

public record RoomDto(
        int x,
        int y,
        int width,
        int height,
        int roomIndex
) {
}
