package com.procedural_generator.algorithm.wfc;

import com.procedural_generator.algorithm.GenerationAlgorithm;
import com.procedural_generator.algorithm.GenerationContext;
import com.procedural_generator.algorithm.GenerationResult;
import com.procedural_generator.domain.enums.AlgorithmType;
import com.procedural_generator.domain.model.WfcTileset;
import com.procedural_generator.domain.port.WfcTilesetRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class WfcAlgorithm implements GenerationAlgorithm {

    private final WfcTilesetRepository tilesetRepository;
    private final WfcBacktracker backtracker = new WfcBacktracker();

    public WfcAlgorithm(WfcTilesetRepository tilesetRepository) {
        this.tilesetRepository = tilesetRepository;
    }

    @Override
    public GenerationResult generate(GenerationContext context) {
        WfcParams params = mapParams(context.params());

        WfcTileset tileset = tilesetRepository.findByName(params.tilesetName())
                .orElseThrow(() -> new IllegalArgumentException("Tileset not found: " + params.tilesetName()));

        int[][] tiles = backtracker.solve(
                context.width(),
                context.height(),
                tileset.getRules(),
                params.maxRetries(),
                context.seed()
        );

        return new GenerationResult(
                tiles,
                List.of(),
                List.of(),
                context.width() * context.height()
        );
    }

    @Override
    public AlgorithmType getType() {
        return AlgorithmType.WFC;
    }

    private WfcParams mapParams(Map<String, Object> params) {
        Object rawTilesetName = params.get("tilesetName");
        if (!(rawTilesetName instanceof String tilesetName) || tilesetName.isBlank()) {
            throw new IllegalArgumentException("WFC requires non-empty 'tilesetName'");
        }

        int maxRetries = 8;
        Object rawRetries = params.get("maxRetries");
        if (rawRetries instanceof Number number) {
            maxRetries = Math.max(1, number.intValue());
        }

        return new WfcParams(tilesetName, maxRetries);
    }
}
