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
        if (rawRulesJson == null || rawRulesJson.isBlank()) {
            return Map.of();
        }

        Map<String, Object> rawRules;
        try {
            rawRules = objectMapper.readValue(rawRulesJson, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Invalid tilesetData json", e);
        }

        Map<Integer, WfcTileset.Rule> rules = new HashMap<>();
        for (Map.Entry<String, Object> entry : rawRules.entrySet()) {
            int tileCode = Integer.parseInt(entry.getKey());
            Map<String, Object> directionalRules = (Map<String, Object>) entry.getValue();

            List<Integer> up = toIntList(directionalRules.get("up"));
            List<Integer> down = toIntList(directionalRules.get("down"));
            List<Integer> left = toIntList(directionalRules.get("left"));
            List<Integer> right = toIntList(directionalRules.get("right"));

            rules.put(tileCode, new WfcTileset.Rule(up, down, left, right));
        }

        return rules;
    }

    private String toEntityRules(Map<Integer, WfcTileset.Rule> rules) {
        if (rules == null) {
            return "{}";
        }

        Map<String, Object> rawRules = new HashMap<>();
        for (Map.Entry<Integer, WfcTileset.Rule> entry : rules.entrySet()) {
            WfcTileset.Rule rule = entry.getValue();
            Map<String, Object> directionalRules = new HashMap<>();
            directionalRules.put("up", rule.up());
            directionalRules.put("down", rule.down());
            directionalRules.put("left", rule.left());
            directionalRules.put("right", rule.right());
            rawRules.put(entry.getKey().toString(), directionalRules);
        }

        try {
            return objectMapper.writeValueAsString(rawRules);
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
