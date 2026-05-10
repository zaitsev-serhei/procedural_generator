<template>
  <div class="pg-tile-legend" aria-label="Tile legend">
    <div class="pg-tile-legend-title">Tiles</div>
    <div
      v-for="tile in legendItems"
      :key="tile.key"
      class="pg-tile-legend-item"
    >
      <span
        :style="{ background: tile.color }"
        class="pg-tile-legend-swatch"
      ></span>
      <span>{{ tile.label }}</span>
    </div>
  </div>
</template>

<script setup>
import { computed } from "vue";
import { tileColors } from "../../utils/tileColors";

const tileLabels = {
  0: "Void",
  1: "Floor",
  2: "Wall",
  3: "Water",
  4: "Door",
  5: "Corridor",
};

const legendItems = computed(() =>
  Object.entries(tileColors).map(([key, color]) => ({
    key,
    color,
    label: tileLabels[key] || `Tile ${key}`,
  }))
);
</script>
