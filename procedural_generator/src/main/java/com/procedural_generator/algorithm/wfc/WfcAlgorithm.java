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
    private final WfcBacktracker backtracker;

    public WfcAlgorithm(WfcTilesetRepository tilesetRepository) {
        this.tilesetRepository = tilesetRepository;
        this.backtracker = new WfcBacktracker(); // engine
    }

    @Override
    public GenerationResult generate(GenerationContext context) {

        WfcParams params = mapParams(context.params());

        WfcTileset tileset = tilesetRepository.findByName("default")
                .orElseThrow(() ->
                        new IllegalArgumentException("Tileset not found: " + params.tilesetName())
                );

        // 🧠 HERE IS THE ENGINE CALL
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

        String tilesetName = (String) params.get("tilesetName");

        if (tilesetName == null || tilesetName.isBlank()) {
            throw new IllegalArgumentException("WFC requires tilesetName");
        }

        int maxRetries = 8;

        Object rawRetries = params.get("maxRetries");
        if (rawRetries instanceof Number n) {
            maxRetries = Math.max(1, n.intValue());
        }

        return new WfcParams(tilesetName, maxRetries);
    }
}
