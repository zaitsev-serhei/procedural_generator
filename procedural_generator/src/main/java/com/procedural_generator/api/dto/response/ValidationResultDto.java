package com.procedural_generator.api.dto.response;

import java.util.List;

public record ValidationResultDto(
        boolean valid,
        List<String> errors
) {
}
