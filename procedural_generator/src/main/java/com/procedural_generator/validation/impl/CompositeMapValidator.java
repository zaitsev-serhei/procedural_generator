package com.procedural_generator.validation.impl;

import com.procedural_generator.domain.model.MapGeneration;
import com.procedural_generator.validation.MapValidator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompositeMapValidator implements MapValidator {

    private final List<MapValidator> validators;

    public CompositeMapValidator(List<MapValidator> validators) {
        this.validators = validators;
    }

    @Override
    public void validate(MapGeneration mapGeneration) {

        for (MapValidator validator : validators) {

            // avoid recursive self-call
            if (validator == this) {
                continue;
            }

            validator.validate(mapGeneration);
        }
    }
}
