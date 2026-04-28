package com.procedural_generator.api.dto.response;

import java.time.LocalDateTime;

public record MapMetadataDto(
        String id,
        String algorithmType,
        long seed,
        int width,
        int height,
        int iterations,
        LocalDateTime createdAt
) {
}
