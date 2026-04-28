package com.procedural_generator.algorithm.wfc;

import java.util.HashSet;
import java.util.Set;

public class WfcCell {

    private final Set<Integer> possibleTiles;

    public WfcCell(Set<Integer> possibleTiles) {
        this.possibleTiles = new HashSet<>(possibleTiles);
    }

    public Set<Integer> getPossibleTiles() {
        return possibleTiles;
    }

    public boolean isCollapsed() {
        return possibleTiles.size() == 1;
    }

    public int entropy() {
        return possibleTiles.size();
    }

    public boolean constrain(Set<Integer> allowedTiles) {
        return possibleTiles.retainAll(allowedTiles);
    }

    public void collapseTo(int tile) {
        possibleTiles.clear();
        possibleTiles.add(tile);
    }

    public int collapsedTile() {
        return possibleTiles.iterator().next();
    }
}
