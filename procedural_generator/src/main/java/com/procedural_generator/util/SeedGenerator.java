package com.procedural_generator.util;

import java.util.Random;

public class SeedGenerator {

    public static long generate() {
        return new Random().nextLong();
    }
}
