CREATE TABLE map_generations (
                                 id UUID PRIMARY KEY,
                                 algorithm_type VARCHAR(50) NOT NULL,
                                 seed BIGINT NOT NULL,
                                 width INT NOT NULL,
                                 height INT NOT NULL,
                                 tiles JSONB NOT NULL,
                                 iterations INT NOT NULL,
                                 created_at TIMESTAMP NOT NULL
);