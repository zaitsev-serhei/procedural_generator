package com.procedural_generator.persistence.adapter;

import com.procedural_generator.domain.model.MapGeneration;
import com.procedural_generator.domain.port.MapGenerationRepository;
import com.procedural_generator.persistence.entity.MapConnectionEntity;
import com.procedural_generator.persistence.entity.MapGenerationEntity;
import com.procedural_generator.persistence.entity.MapRoomEntity;
import com.procedural_generator.persistence.repository.MapConnectionJpaRepository;
import com.procedural_generator.persistence.repository.MapGenerationJpaRepository;
import com.procedural_generator.persistence.repository.MapRoomJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class MapGenerationRepositoryAdapter implements MapGenerationRepository {

    private final MapGenerationJpaRepository generationRepo;
    private final MapRoomJpaRepository roomRepo;
    private final MapConnectionJpaRepository connectionRepo;

    public MapGenerationRepositoryAdapter(
            MapGenerationJpaRepository generationRepo,
            MapRoomJpaRepository roomRepo,
            MapConnectionJpaRepository connectionRepo
    ) {
        this.generationRepo = generationRepo;
        this.roomRepo = roomRepo;
        this.connectionRepo = connectionRepo;
    }

    @Override
    public MapGeneration save(MapGeneration mapGeneration) {

        MapGenerationEntity saved = generationRepo.save(
                MapGenerationEntity.builder()
                        .id(mapGeneration.getId())
                        .algorithmType(mapGeneration.getAlgorithmType().name())
                        .seed(mapGeneration.getSeed())
                        .width(mapGeneration.getWidth())
                        .height(mapGeneration.getHeight())
                        .tiles(serializeTiles(mapGeneration.getTiles()))
                        .iterations(mapGeneration.getIterations())
                        .createdAt(mapGeneration.getCreatedAt())
                        .build()
        );

        List<MapRoomEntity> roomEntities = mapGeneration.getRooms().stream()
                .map(r -> MapRoomEntity.builder()
                        .x(r.getX())
                        .y(r.getY())
                        .width(r.getWidth())
                        .height(r.getHeight())
                        .roomIndex(r.getRoomIndex())
                        .mapGeneration(saved)
                        .build())
                .toList();

        roomRepo.saveAll(roomEntities);

        List<MapConnectionEntity> connectionEntities = mapGeneration.getConnections().stream()
                .map(c -> MapConnectionEntity.builder()
                        .fromIndex(c.getFromIndex())
                        .toIndex(c.getToIndex())
                        .mapGeneration(saved)
                        .build())
                .toList();

        connectionRepo.saveAll(connectionEntities);

        return mapGeneration;
    }

    @Override
    public MapGeneration findById(UUID id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Page<MapGenerationEntity> findAll(Pageable pageable) {
        return generationRepo.findAll(pageable);
    }

    private String serializeTiles(int[][] tiles) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for (int i = 0; i < tiles.length; i++) {
                sb.append("[");
                for (int j = 0; j < tiles[i].length; j++) {
                    sb.append(tiles[i][j]);
                    if (j < tiles[i].length - 1) sb.append(",");
                }
                sb.append("]");
                if (i < tiles.length - 1) sb.append(",");
            }
            sb.append("]");
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize tiles", e);
        }
    }
}
