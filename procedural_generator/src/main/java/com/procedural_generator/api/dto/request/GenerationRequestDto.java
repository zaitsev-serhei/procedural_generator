package com.procedural_generator.api.dto.request;

import com.procedural_generator.domain.enums.AlgorithmType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

public record GenerationRequestDto(
        @NotNull
        AlgorithmType algorithmType,

        @NotNull
        Long seed,

        @Min(10)
        int width,

        @Min(10)
        int height,

        @NotNull
        Map<String, Object> params
) {
}
