CREATE TABLE map_rooms (
                           id BIGSERIAL PRIMARY KEY,
                           x INT NOT NULL,
                           y INT NOT NULL,
                           width INT NOT NULL,
                           height INT NOT NULL,
                           room_index INT NOT NULL,
                           map_generation_id UUID NOT NULL REFERENCES map_generations(id) ON DELETE CASCADE
);

CREATE TABLE map_connections (
                                 id BIGSERIAL PRIMARY KEY,
                                 from_index INT NOT NULL,
                                 to_index INT NOT NULL,
                                 map_generation_id UUID NOT NULL REFERENCES map_generations(id) ON DELETE CASCADE
);