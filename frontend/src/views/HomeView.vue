<template>
  <div class="flex flex-1 overflow-hidden bg-[#0f1117]">
    <aside class="w-[220px] shrink-0 flex flex-col overflow-y-auto bg-[#1a1d27] border-r border-[#2e3348]">
      <MapControls
        :overlay-mode="overlayMode"
        :selected-overlay-id="selectedOverlayId"
        :history-items="historyStore.items"
        @generate="onGenerate"
        @update:overlayMode="overlayMode = $event"
        @update:selectedOverlayId="selectedOverlayId = $event"
      />
    </aside>

    <div class="flex flex-col flex-1 overflow-hidden">
      <main
        class="relative bg-[#0f1117] border-b border-[#2e3348] shrink-0"
        style="height: 420px; overflow: hidden; cursor: grab;"
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

                <div v-else class="text-[13px] text-[#555a7a]">Generate a map to preview it here.</div>
              </template>

              <template v-else>
                <div class="grid h-full w-full grid-cols-2 gap-3 p-3">
                  <div class="overflow-hidden rounded-[10px] bg-[#0f1117] border border-[#2e3348]">
                    <div class="h-full w-full flex items-center justify-center p-3">
                      <MapCanvas
                        v-if="store.map"
                        :tiles="store.map.tiles"
                        :rooms="store.map.rooms"
                        :connections="store.map.connections"
                        :showOverlay="false"
                      />
                      <div v-else class="text-[13px] text-[#555a7a]">Generate a map to preview it here.</div>
                    </div>
                  </div>
                  <div class="overflow-hidden rounded-[10px] bg-[#0f1117] border border-[#2e3348]">
                    <div class="h-full w-full flex items-center justify-center p-3">
                      <template v-if="selectedOverlayMap">
                        <MapCanvas
                          :tiles="selectedOverlayMap.tiles"
                          :rooms="selectedOverlayMap.rooms"
                          :connections="selectedOverlayMap.connections"
                          :showOverlay="false"
                        />
                      </template>
                      <div v-else class="text-[13px] text-[#555a7a]">Select an overlay generation</div>
                    </div>
                  </div>
                </div>
              </template>
          </div>
        </div>

        <div class="absolute top-3 left-3 flex gap-1.5 pointer-events-none">
          <span class="text-[11px] px-2 py-0.5 rounded-full bg-black/40 text-[#8b90b8] backdrop-blur-sm border border-[#2e3348]/50">
            {{ generationHistory[0]?.algorithm || 'No map' }}
          </span>
          <span class="text-[11px] px-2 py-0.5 rounded-full bg-black/40 text-[#8b90b8] backdrop-blur-sm border border-[#2e3348]/50">
            {{ generationHistory[0]?.size || '50 x 50' }}
          </span>
        </div>

        <div class="absolute bottom-3 right-3 flex flex-col gap-1 z-10">
          <button @click.stop="zoomIn" class="w-7 h-7 flex items-center justify-center rounded-md text-sm bg-black/40 border border-[#2e3348]/60 text-[#8b90b8] hover:bg-[#22263a] hover:text-[#f0f2ff] backdrop-blur-sm transition-all duration-150 active:scale-95">+</button>
          <button @click.stop="zoomOut" class="w-7 h-7 flex items-center justify-center rounded-md text-sm bg-black/40 border border-[#2e3348]/60 text-[#8b90b8] hover:bg-[#22263a] hover:text-[#f0f2ff] backdrop-blur-sm transition-all duration-150 active:scale-95">−</button>
          <button @click.stop="resetView" class="w-7 h-7 flex items-center justify-center rounded-md text-sm bg-black/40 border border-[#2e3348]/60 text-[#8b90b8] hover:bg-[#22263a] hover:text-[#f0f2ff] backdrop-blur-sm transition-all duration-150 active:scale-95">⊡</button>
        </div>
      </main>

      <div class="h-9 shrink-0 flex items-center gap-1.5 px-4 bg-[#1a1d27] border-b border-[#2e3348]">
        <button
          type="button"
          :class="[viewMode === 'single' ? 'bg-[#6366f1]/15 border border-[#6366f1]/40 text-[#6366f1]' : 'bg-transparent border border-[#2e3348] text-[#555a7a] hover:border-[#3d4460] hover:text-[#8b90b8]', 'text-[11px] font-medium px-3 py-1 rounded-full cursor-pointer transition-all duration-150']"
          @click="viewMode = 'single'"
        >
          Single
        </button>
        <button
          type="button"
          :class="[viewMode === 'overlay' ? 'bg-[#6366f1]/15 border border-[#6366f1]/40 text-[#6366f1]' : 'bg-transparent border border-[#2e3348] text-[#555a7a] hover:border-[#3d4460] hover:text-[#8b90b8]', 'text-[11px] font-medium px-3 py-1 rounded-full cursor-pointer transition-all duration-150']"
          @click="viewMode = 'overlay'"
        >
          Overlay
        </button>
        <button
          type="button"
          :class="[viewMode === 'split' ? 'bg-[#6366f1]/15 border border-[#6366f1]/40 text-[#6366f1]' : 'bg-transparent border border-[#2e3348] text-[#555a7a] hover:border-[#3d4460] hover:text-[#8b90b8]', 'text-[11px] font-medium px-3 py-1 rounded-full cursor-pointer transition-all duration-150']"
          @click="viewMode = 'split'"
        >
          Split
        </button>
      </div>

      <div class="flex-1 overflow-y-auto bg-[#0f1117]">
        <table class="w-full border-collapse" style="font-family: 'Inter', sans-serif;">
          <thead class="sticky top-0 z-10 bg-[#1a1d27] border-b border-[#2e3348]">
            <tr>
                <th class="px-4 py-2.5 text-left text-[10px] font-semibold uppercase tracking-[0.06em] text-[#555a7a] whitespace-nowrap">#</th>
                <th class="px-4 py-2.5 text-left text-[10px] font-semibold uppercase tracking-[0.06em] text-[#555a7a] whitespace-nowrap">Algorithm</th>
                <th class="px-4 py-2.5 text-left text-[10px] font-semibold uppercase tracking-[0.06em] text-[#555a7a] whitespace-nowrap">Seed</th>
                <th class="px-4 py-2.5 text-left text-[10px] font-semibold uppercase tracking-[0.06em] text-[#555a7a] whitespace-nowrap">Rooms</th>
                <th class="px-4 py-2.5 text-left text-[10px] font-semibold uppercase tracking-[0.06em] text-[#555a7a] whitespace-nowrap">Corridors</th>
                <th class="px-4 py-2.5 text-left text-[10px] font-semibold uppercase tracking-[0.06em] text-[#555a7a] whitespace-nowrap">Time (ms)</th>
                <th class="px-4 py-2.5 text-left text-[10px] font-semibold uppercase tracking-[0.06em] text-[#555a7a] whitespace-nowrap">Canvas Size</th>
                <th class="px-4 py-2.5 text-left text-[10px] font-semibold uppercase tracking-[0.06em] text-[#555a7a] whitespace-nowrap">Status</th>
              </tr>
            </thead>
            <tbody>
              <tr
                v-for="(row, index) in generationHistory"
                :key="row.id"
                class="border-b border-[#1e2233] hover:bg-[#1a1d27] transition-colors duration-100 cursor-pointer"
              >
                <td class="px-4 py-2 text-[12px] text-[#555a7a] font-mono">{{ index + 1 }}</td>
                <td class="px-4 py-2 text-[12px] text-[#8b90b8]">{{ row.algorithm }}</td>
                <td class="px-4 py-2 text-[11px] font-mono text-[#555a7a]">{{ row.seed }}</td>
                <td class="px-4 py-2 text-[12px] text-[#8b90b8]">{{ row.rooms }}</td>
                <td class="px-4 py-2 text-[12px] text-[#8b90b8]">{{ row.corridors }}</td>
                <td class="px-4 py-2 text-[12px] text-[#8b90b8]">{{ row.time }}</td>
                <td class="px-4 py-2 text-[12px] text-[#8b90b8]">{{ row.size }}</td>
                <td class="px-4 py-2 text-[12px] text-[#8b90b8]">
                  <span
                    v-if="row.status === 'success'"
                    class="inline-flex items-center gap-1 text-[10px] font-medium px-2 py-0.5 rounded-full bg-emerald-500/10 text-emerald-400 border border-emerald-500/20"
                  >
                    <span class="w-1 h-1 rounded-full bg-emerald-400 inline-block"></span>
                    success
                  </span>
                  <span
                    v-else
                    class="inline-flex items-center gap-1 text-[10px] font-medium px-2 py-0.5 rounded-full bg-amber-500/10 text-amber-400 border border-amber-500/20"
                  >
                    <span class="w-1 h-1 rounded-full bg-amber-400 inline-block"></span>
                    warning
                  </span>
                </td>
              </tr>
              <tr v-if="generationHistory.length === 0">
                <td colspan="8" class="px-4 py-12 text-center text-[13px] text-[#555a7a]">No generations yet — configure parameters and click Generate map</td>
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
