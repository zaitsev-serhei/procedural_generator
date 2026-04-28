package com.procedural_generator.validation;

import com.procedural_generator.api.dto.response.ValidationResultDto;
import com.procedural_generator.domain.model.MapGeneration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ValidationResultMapper {

    private final List<MapValidator> validators;

    public ValidationResultMapper(List<MapValidator> validators) {
        this.validators = validators;
    }

    public ValidationResultDto validate(MapGeneration map) {

        List<String> errors = new ArrayList<>();

        for (MapValidator validator : validators) {
            try {
                validator.validate(map);
            } catch (Exception e) {
                errors.add(e.getMessage());
            }
        }

        return new ValidationResultDto(errors.isEmpty(), errors);
    }
}
