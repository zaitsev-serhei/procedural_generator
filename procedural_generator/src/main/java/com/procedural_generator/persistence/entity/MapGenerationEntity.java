package com.procedural_generator.persistence.entity;

import com.procedural_generator.domain.enums.AlgorithmType;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "map_generations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MapGenerationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String algorithmType;

    @Column(nullable = false)
    private long seed;

    @Column(nullable = false)
    private int width;

    @Column(nullable = false)
    private int height;

    @Column(columnDefinition = "jsonb", nullable = false)
    private String tiles;

    @Column(nullable = false)
    private int iterations;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void prePersist() {
        if (id == null) {
            id = UUID.randomUUID();
        }
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }


}
