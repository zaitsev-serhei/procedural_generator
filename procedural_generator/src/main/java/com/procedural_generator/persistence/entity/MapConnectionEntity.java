package com.procedural_generator.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "map_connections")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MapConnectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int fromIndex;

    @Column(nullable = false)
    private int toIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "map_generation_id", nullable = false)
    private MapGenerationEntity mapGeneration;
}
