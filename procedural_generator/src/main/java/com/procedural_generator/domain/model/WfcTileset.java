package com.procedural_generator.domain.model;

import java.util.List;
import java.util.Map;

public class WfcTileset {

    private final Long id;
    private final String name;
    private final Map<Integer, Rule> rules;

    public WfcTileset(Long id, String name, Map<Integer, Rule> rules) {
        this.id = id;
        this.name = name;
        this.rules = rules;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Map<Integer, Rule> getRules() {
        return rules;
    }

    public record Rule(
            List<Integer> up,
            List<Integer> down,
            List<Integer> left,
            List<Integer> right
    ) {
    }
}
