CREATE TABLE generation_params (
                                   id BIGSERIAL PRIMARY KEY,
                                   params JSONB NOT NULL,
                                   map_generation_id UUID NOT NULL UNIQUE REFERENCES map_generations(id) ON DELETE CASCADE
);