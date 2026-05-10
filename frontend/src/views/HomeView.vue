<template>
  <div class="pg-app-bg">
    <aside class="pg-sidebar-panel">
      <div class="pg-sidebar-frame">
        <div class="pg-sidebar-surface">
          <MapControls
            :overlay-mode="overlayMode"
            :selected-overlay-id="selectedOverlayId"
            :history-items="historyStore.items"
            @generate="onGenerate"
            @update:overlayMode="overlayMode = $event"
            @update:selectedOverlayId="selectedOverlayId = $event"
          />
        </div>
      </div>
    </aside>

    <div class="flex flex-col flex-1 overflow-hidden">
      <main
        class="pg-preview-panel"
        @mousedown.prevent="startDrag"
        @mousemove="onMouseMove"
        @mouseup="endDrag"
        @mouseleave="endDrag"
        @wheel.prevent="onWheel"
      >
        <div
          :style="{ ...transformStyle, transformOrigin: 'center center', transition: dragging ? 'none' : 'transform 0.1s ease', position: 'absolute', inset: 0, display: 'flex', alignItems: 'center', justifyContent: 'center' }"
        >
          <div class="absolute inset-0 flex items-center justify-center">
              <template v-if="viewMode !== 'split'">
                <template v-if="store.map">
                  <div class="relative inline-block">
                    <MapCanvas
                      :tiles="store.map.tiles"
                      :rooms="store.map.rooms"
                      :connections="store.map.connections"
                      :showOverlay="false"
                    />

                    <MapCanvas
                      v-if="overlayMode && selectedOverlayMap"
                      class="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 opacity-40 pointer-events-none"
                      :tiles="selectedOverlayMap.tiles"
                      :rooms="selectedOverlayMap.rooms"
                      :connections="selectedOverlayMap.connections"
                      :showOverlay="false"
                    />
                  </div>
                </template>

                <div v-else class="pg-empty-state">Generate a map to preview it here.</div>
              </template>

              <template v-else>
                <div class="grid h-full w-full grid-cols-2 gap-3 p-3">
                  <div class="pg-muted-panel">
                    <div class="h-full w-full flex items-center justify-center p-3">
                      <MapCanvas
                        v-if="store.map"
                        :tiles="store.map.tiles"
                        :rooms="store.map.rooms"
                        :connections="store.map.connections"
                        :showOverlay="false"
                      />
                      <div v-else class="pg-empty-state">Generate a map to preview it here.</div>
                    </div>
                  </div>
                  <div class="pg-muted-panel">
                    <div class="h-full w-full flex items-center justify-center p-3">
                      <template v-if="selectedOverlayMap">
                        <MapCanvas
                          :tiles="selectedOverlayMap.tiles"
                          :rooms="selectedOverlayMap.rooms"
                          :connections="selectedOverlayMap.connections"
                          :showOverlay="false"
                        />
                      </template>
                      <div v-else class="pg-empty-state">Select an overlay generation</div>
                    </div>
                  </div>
                </div>
              </template>
          </div>
        </div>

        <div class="absolute top-3 left-3 flex gap-1.5 pointer-events-none">
          <span class="pg-floating-chip">
            {{ generationHistory[0]?.algorithm || 'No map' }}
          </span>
          <span class="pg-floating-chip">
            {{ generationHistory[0]?.size || '50 x 50' }}
          </span>
        </div>

        <div class="pg-floating-toolbar z-10">
          <button @click.stop="zoomIn" class="pg-toolbar-button">+</button>
          <button @click.stop="zoomOut" class="pg-toolbar-button">−</button>
          <button @click.stop="resetView" class="pg-toolbar-button">⊡</button>
        </div>
      </main>

      <div class="pg-segmented-control">
        <button
          type="button"
          :class="[viewMode === 'single' ? 'pg-segmented-button-active' : 'pg-segmented-button-idle', 'pg-segmented-button']"
          @click="viewMode = 'single'"
        >
          Single
        </button>
        <button
          type="button"
          :class="[viewMode === 'overlay' ? 'pg-segmented-button-active' : 'pg-segmented-button-idle', 'pg-segmented-button']"
          @click="viewMode = 'overlay'"
        >
          Overlay
        </button>
        <button
          type="button"
          :class="[viewMode === 'split' ? 'pg-segmented-button-active' : 'pg-segmented-button-idle', 'pg-segmented-button']"
          @click="viewMode = 'split'"
        >
          Split
        </button>
      </div>

      <div class="pg-table-surface">
        <table class="w-full border-collapse" style="font-family: 'Inter', sans-serif;">
          <thead class="pg-table-head">
            <tr>
                <th class="pg-table-header-cell">#</th>
                <th class="pg-table-header-cell">Algorithm</th>
                <th class="pg-table-header-cell">Seed</th>
                <th class="pg-table-header-cell">Rooms</th>
                <th class="pg-table-header-cell">Corridors</th>
                <th class="pg-table-header-cell">Time (ms)</th>
                <th class="pg-table-header-cell">Canvas Size</th>
                <th class="pg-table-header-cell">Status</th>
              </tr>
            </thead>
            <tbody>
              <tr
                v-for="(row, index) in generationHistory"
                :key="row.id"
                class="pg-table-row"
              >
                <td class="pg-table-cell-muted font-mono">{{ index + 1 }}</td>
                <td class="pg-table-cell">{{ row.algorithm }}</td>
                <td class="pg-table-cell-muted font-mono text-[11px]">{{ row.seed }}</td>
                <td class="pg-table-cell">{{ row.rooms }}</td>
                <td class="pg-table-cell">{{ row.corridors }}</td>
                <td class="pg-table-cell">{{ row.time }}</td>
                <td class="pg-table-cell">{{ row.size }}</td>
                <td class="pg-table-cell">
                  <span
                    v-if="row.status === 'success'"
                    class="pg-status-chip pg-status-chip-success"
                  >
                    <span class="pg-status-dot pg-status-chip-success"></span>
                    success
                  </span>
                  <span
                    v-else
                    class="pg-status-chip pg-status-chip-warning"
                  >
                    <span class="pg-status-dot bg-amber-300"></span>
                    warning
                  </span>
                </td>
              </tr>
              <tr v-if="generationHistory.length === 0">
                <td colspan="8" class="pg-empty-state">No generations yet — configure parameters and click Generate map</td>
              </tr>
            </tbody>
          </table>
        </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from "vue";
import { useMapStore } from "../store/mapStore";
import { useHistoryStore } from "../store/historyStore";
import { getMapById } from "../api/mapApi";
import MapControls from "../components/controls/MapControls.vue";
import MapCanvas from "../components/map/MapCanvas.vue";

const store = useMapStore();
const historyStore = useHistoryStore();

const overlayMode = ref(false);
const selectedOverlayId = ref("");
const selectedOverlayMap = ref(null);
const generationHistory = ref([]);
const viewMode = ref("single");
const scale = ref(1);
const tx = ref(0);
const ty = ref(0);
const dragging = ref(false);
const dragStart = ref({ x: 0, y: 0 });
const lastTranslate = ref({ x: 0, y: 0 });

const transformStyle = computed(() => ({
  transform: `translate(${tx.value}px, ${ty.value}px) scale(${scale.value})`,
  transformOrigin: "0 0",
}));

const onGenerate = async (payload) => {
  const startTime = performance.now();

  try {
    await store.generate(payload);

    if (store.map) {
      generationHistory.value.unshift({
        id: `${Date.now()}-${Math.random().toString(36).slice(2, 8)}`,
        algorithm: payload.algorithmType,
        seed: payload.seed,
        rooms: store.map.rooms?.length ?? 0,
        corridors: store.map.connections?.length ?? 0,
        time: Math.round(performance.now() - startTime),
        size: `${payload.width}x${payload.height}`,
        status: "success",
      });
    }
  } catch (error) {
    console.error(error);
  }
};

const loadOverlayMap = async (id) => {
  selectedOverlayMap.value = null;

  if (!id) {
    return;
  }

  try {
    const response = await getMapById(id);
    selectedOverlayMap.value = response.data;
  } catch (error) {
    console.error(error);
  }
};

watch(selectedOverlayId, (id) => {
  loadOverlayMap(id);
});

watch(viewMode, (mode) => {
  overlayMode.value = mode !== "single";

  if (mode === "single") {
    selectedOverlayId.value = "";
  }
});

watch(overlayMode, (value) => {
  if (!value) {
    viewMode.value = "single";
  } else if (viewMode.value === "single") {
    viewMode.value = "overlay";
  }
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

const zoomIn = () => {
  scale.value = Math.min(3, scale.value + 0.2);
};

const zoomOut = () => {
  scale.value = Math.max(0.05, scale.value - 0.2);
};

const resetView = () => {
  scale.value = 1;
  tx.value = 0;
  ty.value = 0;
};

onMounted(() => {
  historyStore.fetch();
});
</script>
