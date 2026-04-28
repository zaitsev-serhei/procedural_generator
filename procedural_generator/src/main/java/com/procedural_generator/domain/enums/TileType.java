package com.procedural_generator.domain.enums;

import java.util.Arrays;

public enum TileType {

    VOID(0),
    FLOOR(1),
    WALL(2),
    WATER(3),
    DOOR(4),
    CORRIDOR(5);

    private final int code;

    TileType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static TileType fromCode(int code) {
        return Arrays.stream(values())
                .filter(tile -> tile.code == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown tile code: " + code));
    }
}
