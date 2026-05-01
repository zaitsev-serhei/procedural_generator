<template>
  <div class="flex-1 overflow-hidden flex flex-col bg-[#0f1117]">
    <div class="px-6 py-4 border-b border-[#2e3348] bg-[#1a1d27] shrink-0">
      <h1 class="text-[14px] font-semibold text-[#f0f2ff]">Generation history</h1>
      <p class="text-[12px] text-[#555a7a] mt-0.5">All past generations. Click a row to reload.</p>
    </div>

    <div v-if="store.loading" class="px-4 py-3 text-[12px] text-[#8b90b8] border-b border-[#2e3348] bg-[#0f1117]">Loading...</div>

    <div class="flex-1 overflow-y-auto bg-[#0f1117]">
      <table class="w-full border-collapse" style="font-family: 'Inter', sans-serif;">
        <thead class="sticky top-0 z-10 bg-[#1a1d27] border-b border-[#2e3348]">
          <tr>
            <th class="px-4 py-2.5 text-left text-[10px] font-semibold uppercase tracking-[0.06em] text-[#555a7a] whitespace-nowrap">#</th>
            <th class="px-4 py-2.5 text-left text-[10px] font-semibold uppercase tracking-[0.06em] text-[#555a7a] whitespace-nowrap">Algorithm</th>
            <th class="px-4 py-2.5 text-left text-[10px] font-semibold uppercase tracking-[0.06em] text-[#555a7a] whitespace-nowrap">Seed</th>
            <th class="px-4 py-2.5 text-left text-[10px] font-semibold uppercase tracking-[0.06em] text-[#555a7a] whitespace-nowrap">Rooms</th>
            <th class="px-4 py-2.5 text-left text-[10px] font-semibold uppercase tracking-[0.06em] text-[#555a7a] whitespace-nowrap">Corridors</th>
            <th class="px-4 py-2.5 text-left text-[10px] font-semibold uppercase tracking-[0.06em] text-[#555a7a] whitespace-nowrap">Canvas Size</th>
            <th class="px-4 py-2.5 text-left text-[10px] font-semibold uppercase tracking-[0.06em] text-[#555a7a] whitespace-nowrap">Date</th>
            <th class="px-4 py-2.5 text-left text-[10px] font-semibold uppercase tracking-[0.06em] text-[#555a7a] whitespace-nowrap">Action</th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="(item, index) in store.items"
            :key="item.id"
            class="border-b border-[#1e2233] hover:bg-[#1a1d27] transition-colors duration-100 cursor-pointer"
            @click="load(item)"
          >
            <td class="px-4 py-2 text-[12px] text-[#555a7a] font-mono">{{ index + 1 }}</td>
            <td class="px-4 py-2 text-[12px] text-[#8b90b8]">{{ item.algorithmType }}</td>
            <td class="px-4 py-2 text-[11px] font-mono text-[#555a7a]">{{ item.seed }}</td>
            <td class="px-4 py-2 text-[12px] text-[#8b90b8]">{{ item.rooms?.length ?? item.rooms ?? '—' }}</td>
            <td class="px-4 py-2 text-[12px] text-[#8b90b8]">{{ item.connections?.length ?? item.corridors ?? '—' }}</td>
            <td class="px-4 py-2 text-[12px] text-[#8b90b8]">{{ item.width }}x{{ item.height }}</td>
            <td class="px-4 py-2 text-[12px] text-[#8b90b8]">{{ formatDate(item.createdAt || item.date) }}</td>
            <td class="px-4 py-2 text-[12px] text-[#8b90b8]">
              <button class="text-[11px] font-medium px-3 py-1 rounded-full cursor-pointer transition-all duration-150 bg-[#6366f1]/15 border border-[#6366f1]/40 text-[#6366f1] hover:bg-[#6366f1]/25">Load</button>
            </td>
          </tr>
          <tr v-if="!store.items.length">
            <td colspan="8" class="px-4 py-12 text-center text-[13px] text-[#555a7a]">No history records found.</td>
          </tr>
        </tbody>
      </table>
    </div>

    <div class="shrink-0 flex items-center gap-2 px-6 py-3 border-t border-[#2e3348] bg-[#1a1d27]">
      <button
        class="h-8 px-3 rounded-lg text-[13px] font-medium text-[#8b90b8] bg-[#22263a] border border-[#2e3348] hover:bg-[#2e3348] hover:text-[#f0f2ff] disabled:text-[#555a7a] disabled:cursor-not-allowed transition-all duration-150"
        :disabled="store.page === 0"
        @click="store.fetch(store.page - 1)"
      >
        Prev
      </button>

      <span class="self-center text-[12px] text-[#555a7a]">Page {{ store.page + 1 }} / {{ store.totalPages }}</span>

      <button
        class="h-8 px-3 rounded-lg text-[13px] font-medium text-[#8b90b8] bg-[#22263a] border border-[#2e3348] hover:bg-[#2e3348] hover:text-[#f0f2ff] disabled:text-[#555a7a] disabled:cursor-not-allowed transition-all duration-150"
        :disabled="store.page >= store.totalPages - 1"
        @click="store.fetch(store.page + 1)"
      >
        Next
      </button>
    </div>
  </div>
</template>

<script setup>
import { onMounted } from "vue";
import { useHistoryStore } from "../store/historyStore";
import { useMapStore } from "../store/mapStore";
import { useRouter } from "vue-router";
import { getMapById } from "../api/mapApi";

const store = useHistoryStore();
const mapStore = useMapStore();
const router = useRouter();

const formatDate = (value) => {
  if (!value) {
    return "—";
  }
  const date = new Date(value);
  return date.toLocaleString();
};

onMounted(() => {
  store.fetch();
});

const load = async (item) => {
  try {
    const res = await getMapById(item.id);
    mapStore.setMap(res.data);
    router.push("/");
  } catch (e) {
    console.error(e);
    alert("Failed to load map");
  }
};
</script>
