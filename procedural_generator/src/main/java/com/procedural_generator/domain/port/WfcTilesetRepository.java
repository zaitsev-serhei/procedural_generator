package com.procedural_generator.domain.port;

import com.procedural_generator.domain.model.WfcTileset;

import java.util.List;
import java.util.Optional;

public interface WfcTilesetRepository {

    Optional<WfcTileset> findByName(String name);

    WfcTileset save(WfcTileset tileset);

    List<WfcTileset> findAll();
}
