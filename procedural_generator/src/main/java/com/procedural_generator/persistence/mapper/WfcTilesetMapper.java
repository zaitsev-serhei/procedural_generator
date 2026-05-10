package com.procedural_generator.persistence.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.procedural_generator.domain.model.WfcTileset;
import com.procedural_generator.persistence.entity.WfcTilesetEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class WfcTilesetMapper {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public WfcTileset toDomain(WfcTilesetEntity entity) {
        if (entity == null) {
            return null;
        }

        return new WfcTileset(
                null,
                entity.getName(),
                toDomainRules(entity.getTilesetData())
        );
    }

    public WfcTilesetEntity toEntity(WfcTileset domain) {
        if (domain == null) {
            return null;
        }

        WfcTilesetEntity entity = new WfcTilesetEntity();
        entity.setName(domain.getName());
        entity.setTilesetData(toEntityRules(domain.getRules()));
        return entity;
    }

    @SuppressWarnings("unchecked")
    private Map<Integer, WfcTileset.Rule> toDomainRules(String rawRulesJson) {

        System.out.println(rawRulesJson);

        if (rawRulesJson == null || rawRulesJson.isBlank()) {
            return Map.of();
        }

        try {

            // CURRENT DB FORMAT:
            // {
            //   "0": [0,1],
            //   "1": [1,2]
            // }

            Map<String, List<Integer>> rawRules =
                    objectMapper.readValue(
                            rawRulesJson,
                            new TypeReference<>() {}
                    );

            Map<Integer, WfcTileset.Rule> rules = new HashMap<>();

            for (Map.Entry<String, List<Integer>> entry : rawRules.entrySet()) {

                int tileCode = Integer.parseInt(entry.getKey());

                List<Integer> allowed = entry.getValue();

                // SAME RULES FOR ALL DIRECTIONS
                rules.put(
                        tileCode,
                        new WfcTileset.Rule(
                                new ArrayList<>(allowed),
                                new ArrayList<>(allowed),
                                new ArrayList<>(allowed),
                                new ArrayList<>(allowed)
                        )
                );
            }

            System.out.println("PARSED RULES = " + rules.size());

            return rules;

        } catch (Exception e) {

            throw new IllegalArgumentException(
                    "Invalid tilesetData json",
                    e
            );
        }
    }

    private String toEntityRules(Map<Integer, WfcTileset.Rule> rules) {

        if (rules == null) {
            return "{}";
        }

        Map<String, Object> rawRules = new HashMap<>();

        for (Map.Entry<Integer, WfcTileset.Rule> entry : rules.entrySet()) {

            WfcTileset.Rule rule = entry.getValue();

            Map<String, Object> directional = new HashMap<>();
            directional.put("up", rule.up());
            directional.put("down", rule.down());
            directional.put("left", rule.left());
            directional.put("right", rule.right());

            rawRules.put(entry.getKey().toString(), directional);
        }

        try {
            return objectMapper.writeValueAsString(rawRules); // ❗ NO "rules" wrapper
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Cannot serialize tileset rules", e);
        }
    }

    private List<Integer> toIntList(Object value) {
        if (!(value instanceof List<?> listValue)) {
            return List.of();
        }

        List<Integer> result = new ArrayList<>(listValue.size());
        for (Object item : listValue) {
            result.add(((Number) item).intValue());
        }
        return result;
    }
}
