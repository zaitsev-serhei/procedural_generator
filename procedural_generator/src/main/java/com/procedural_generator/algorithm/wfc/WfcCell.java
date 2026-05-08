package com.procedural_generator.algorithm.wfc;

import java.util.HashSet;
import java.util.Set;

public class WfcCell {

    private Set<Integer> possible;
    private boolean collapsed;
    private Integer tile;

    public WfcCell(Set<Integer> allTiles) {
        this.possible = new HashSet<>(allTiles);
    }

    public int entropy() {
        return possible.size();
    }

    public boolean isCollapsed() {
        return collapsed;
    }

    public Set<Integer> getPossibleTiles() {
        return possible;
    }

    public void collapseTo(int t) {
        possible.clear();
        possible.add(t);
        collapsed = true;
        tile = t;
    }

    public Integer collapsedTile() {
        return tile;
    }

    public void removeImpossible(Set<Integer> allowed) {
        possible.retainAll(allowed);
    }
}
