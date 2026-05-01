package com.procedural_generator.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "map_generations")
@DynamicInsert
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

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "tiles", columnDefinition = "jsonb")
    private int[][] tiles;

    @Column(nullable = false)
    private int iterations;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }


}
