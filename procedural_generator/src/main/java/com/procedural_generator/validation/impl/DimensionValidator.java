package com.procedural_generator.validation.impl;

import com.procedural_generator.domain.model.MapGeneration;
import com.procedural_generator.validation.MapValidator;
import org.springframework.stereotype.Component;

@Component
public class DimensionValidator implements MapValidator {

    @Override
    public void validate(MapGeneration map) {

        if (map.getWidth() <= 0 || map.getHeight() <= 0) {
            throw new IllegalStateException("Map dimensions must be > 0");
        }

        if (map.getTiles() == null) {
            throw new IllegalStateException("Tiles array is null");
        }

        if (map.getTiles().length != map.getHeight()) {
            throw new IllegalStateException("Tile height mismatch");
        }
    }
}
