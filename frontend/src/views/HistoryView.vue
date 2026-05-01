<template>
  <div class="p-4">

    <h1 class="text-xl mb-4">Map History</h1>

    <div v-if="store.loading">Loading...</div>

    <table class="w-full text-sm bg-gray-800 rounded">
      <thead>
        <tr class="text-left border-b border-gray-600">
          <th class="p-2">Algorithm</th>
          <th class="p-2">Size</th>
          <th class="p-2">Seed</th>
          <th class="p-2">Action</th>
        </tr>
      </thead>

      <tbody>
        <tr
          v-for="item in store.items"
          :key="item.id"
          class="border-b border-gray-700"
        >
          <td class="p-2">{{ item.algorithmType }}</td>
          <td class="p-2">{{ item.width }}x{{ item.height }}</td>
          <td class="p-2">{{ item.seed }}</td>

          <td class="p-2">
            <button
              class="bg-green-500 px-2 py-1 rounded"
              @click="load(item)"
            >
              Load
            </button>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- PAGINATION -->
    <div class="mt-4 flex gap-2">
      <button
        class="bg-gray-700 px-3 py-1"
        :disabled="store.page === 0"
        @click="store.fetch(store.page - 1)"
      >
        Prev
      </button>

      <span>Page {{ store.page + 1 }} / {{ store.totalPages }}</span>

      <button
        class="bg-gray-700 px-3 py-1"
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