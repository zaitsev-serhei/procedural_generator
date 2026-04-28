package com.procedural_generator.domain.model;

import com.procedural_generator.domain.enums.AlgorithmType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class MapGeneration {

    private final UUID id;
    private final AlgorithmType algorithmType;
    private final long seed;
    private final int width;
    private final int height;
    private final int[][] tiles;
    private final List<MapRoom> rooms;
    private final List<MapConnection> connections;
    private final int iterations;
    private final LocalDateTime createdAt;

    public MapGeneration(
            UUID id,
            AlgorithmType algorithmType,
            long seed,
            int width,
            int height,
            int[][] tiles,
            List<MapRoom> rooms,
            List<MapConnection> connections,
            int iterations,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.algorithmType = algorithmType;
        this.seed = seed;
        this.width = width;
        this.height = height;
        this.tiles = tiles;
        this.rooms = rooms;
        this.connections = connections;
        this.iterations = iterations;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public AlgorithmType getAlgorithmType() {
        return algorithmType;
    }

    public long getSeed() {
        return seed;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[][] getTiles() {
        return tiles;
    }

    public List<MapRoom> getRooms() {
        return rooms;
    }

    public List<MapConnection> getConnections() {
        return connections;
    }

    public int getIterations() {
        return iterations;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
