<template>
  <section class="pg-compare-panel">
    <div class="pg-compare-panel-header">
      <div>
        <div class="pg-compare-panel-title">{{ title }}</div>
        <div class="pg-compare-panel-meta">{{ metaText }}</div>
      </div>
    </div>

    <div
      class="pg-compare-viewport"
      @mousedown.stop.prevent="startDrag"
      @mousemove.stop="onMouseMove"
      @mouseup.stop="endDrag"
      @mouseleave.stop="endDrag"
      @wheel.stop.prevent="onWheel"
    >
      <div
        v-if="map"
        class="absolute inset-0 flex items-center justify-center"
      >
        <div
          :style="{ ...transformStyle, transition: dragging ? 'none' : 'transform 0.1s ease' }"
          class="inline-block"
        >
          <MapCanvas
            :tiles="map.tiles"
            :rooms="map.rooms"
            :connections="map.connections"
            :showOverlay="false"
          />
        </div>
      </div>

      <div v-else class="pg-compare-placeholder">
        {{ placeholder }}
      </div>
    </div>
  </section>
</template>

<script setup>
import { computed, ref } from "vue";
import MapCanvas from "./MapCanvas.vue";

const props = defineProps({
  map: {
    type: Object,
    default: null,
  },
  title: {
    type: String,
    required: true,
  },
  placeholder: {
    type: String,
    default: "No map available",
  },
});

const scale = ref(1);
const tx = ref(0);
const ty = ref(0);
const dragging = ref(false);
const dragStart = ref({ x: 0, y: 0 });
const lastTranslate = ref({ x: 0, y: 0 });

const transformStyle = computed(() => ({
  transform: `translate(${tx.value}px, ${ty.value}px) scale(${scale.value})`,
  transformOrigin: "center center",
}));

const metaText = computed(() => {
  if (!props.map) {
    return "Waiting for map data";
  }

  const metadata = props.map.metadata || {};
  const algorithm = metadata.algorithmType || props.map.algorithmType || "Unknown";
  const seed = metadata.seed || props.map.seed || "—";
  const width = metadata.width || props.map.width || props.map.tiles?.[0]?.length || "—";
  const height = metadata.height || props.map.height || props.map.tiles?.length || "—";

  return `${algorithm} · seed ${seed} · ${width}x${height}`;
});

const startDrag = (event) => {
  dragging.value = true;
  dragStart.value = { x: event.clientX, y: event.clientY };
  lastTranslate.value = { x: tx.value, y: ty.value };
};

const onMouseMove = (event) => {
  if (!dragging.value) {
    return;
  }

  tx.value = lastTranslate.value.x + (event.clientX - dragStart.value.x);
  ty.value = lastTranslate.value.y + (event.clientY - dragStart.value.y);
};

const endDrag = () => {
  dragging.value = false;
};

const onWheel = (event) => {
  const delta = -event.deltaY * 0.0015;
  scale.value = Math.min(3, Math.max(0.05, scale.value + delta));
};
</script>
