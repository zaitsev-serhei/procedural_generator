CREATE TABLE wfc_tilesets (
                              id UUID PRIMARY KEY,
                              name VARCHAR(100) UNIQUE NOT NULL,
                              tileset_data JSONB NOT NULL
);