<template>
  <div class="p-4">
    <MapControls @generate="onGenerate" />

    <div class="flex items-center gap-2 mb-2">
      <label>Debug Overlay</label>
      <input type="checkbox" v-model="showOverlay" />
    </div>

    <div v-if="store.loading">Generating...</div>
    <div v-if="store.error" class="text-red-500">{{ store.error }}</div>

    <MapCanvas
  v-if="store.map"
  :tiles="store.map.tiles"
  :rooms="store.map.rooms"
  :connections="store.map.connections"
  :showOverlay="showOverlay"
/>

    <TileLegend v-if="store.map" />
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useMapStore } from "../store/mapStore";
import MapCanvas from "../components/map/MapCanvas.vue";
import MapControls from "../components/controls/MapControls.vue";
import TileLegend from "../components/map/TileLegend.vue";

const store = useMapStore();
const showOverlay = ref(true);

const onGenerate = (params) => {
  store.generate(params);
};
</script>