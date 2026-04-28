package com.procedural_generator.api.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.Map;

public record AlgorithmParamsDto(
        @NotNull
        Map<String, Object> params
) {
}
