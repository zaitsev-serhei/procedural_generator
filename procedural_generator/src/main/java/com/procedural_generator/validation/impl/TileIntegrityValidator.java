package com.procedural_generator.validation.impl;

import com.procedural_generator.domain.enums.AlgorithmType;
import com.procedural_generator.domain.enums.TileType;
import com.procedural_generator.domain.model.MapGeneration;
import com.procedural_generator.validation.MapValidator;
import org.springframework.stereotype.Component;

@Component
public class TileIntegrityValidator implements MapValidator {

    @Override
    public void validate(MapGeneration map) {

        int[][] tiles = map.getTiles();

        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length; x++) {

                int value = tiles[y][x];

                boolean valid = false;
                for (TileType type : TileType.values()) {
                    if (type.getCode() == value) {
                        valid = true;
                        break;
                    }
                }

                if (!valid) {
                    throw new IllegalStateException(
                            "Invalid tile at (" + x + "," + y + "): " + value
                    );
                }
            }
        }
    }

    @Override
    public boolean supports(AlgorithmType type) {
        return true;
    }
}
