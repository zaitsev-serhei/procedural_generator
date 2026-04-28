package com.procedural_generator.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "map_rooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MapRoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int x;

    @Column(nullable = false)
    private int y;

    @Column(nullable = false)
    private int width;

    @Column(nullable = false)
    private int height;

    @Column(nullable = false)
    private int roomIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "map_generation_id", nullable = false)
    private MapGenerationEntity mapGeneration;
}