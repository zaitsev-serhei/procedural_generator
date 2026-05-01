<template>
  <div class="relative inline-block">
    <canvas ref="canvas"></canvas>

    <MapOverlay
      v-if="showOverlay"
      :rooms="rooms"
      :connections="connections"
      :width="width"
      :height="height"
      :tileSize="tileSize"
    />
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from "vue";
import { tileColors } from "../../utils/tileColors";
import MapOverlay from "./MapOverlay.vue";

const props = defineProps({
  tiles: Array,
  rooms: Array,
  connections: Array,
  showOverlay: Boolean,
});

const canvas = ref(null);

const tileSize = 10;

const width = ref(0);
const height = ref(0);

const draw = () => {
  if (!props.tiles) return;

  const ctx = canvas.value.getContext("2d");

  height.value = props.tiles.length;
  width.value = props.tiles[0].length;

  canvas.value.width = width.value * tileSize;
  canvas.value.height = height.value * tileSize;

  for (let y = 0; y < height.value; y++) {
    for (let x = 0; x < width.value; x++) {
      const tile = props.tiles[y][x];
      ctx.fillStyle = tileColors[tile] || "#ff00ff";
      ctx.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
    }
  }
};

onMounted(draw);
watch(() => props.tiles, draw);
</script>