<template>
  <div class="flex flex-col h-full">
    <div class="px-4 py-3 border-b border-gray-100 dark:border-gray-800">
      <label class="text-[10px] uppercase tracking-widest text-gray-400 font-medium block mb-1.5">Algorithm</label>
      <select
        v-model="algorithm"
        class="w-full h-8 text-sm px-3 rounded-md bg-gray-50 dark:bg-gray-800 border border-gray-200 dark:border-gray-700 text-gray-900 dark:text-gray-100 focus:outline-none focus:ring-1 focus:ring-indigo-500"
      >
        <option
          v-for="option in algorithmOptions"
          :key="option.value"
          :value="option.value"
        >
          {{ option.label }}
        </option>
      </select>
    </div>

    <div class="px-4 py-3 border-b border-gray-100 dark:border-gray-800 grid grid-cols-2 gap-3">
      <div>
        <label class="text-xs text-gray-500 dark:text-gray-400 block mb-1.5">Width</label>
        <input
          type="number"
          v-model.number="form.width"
          class="w-full h-8 rounded-md bg-gray-50 dark:bg-gray-800 border border-gray-200 dark:border-gray-700 px-3 text-sm text-gray-900 dark:text-gray-100"
        />
      </div>
      <div>
        <label class="text-xs text-gray-500 dark:text-gray-400 block mb-1.5">Height</label>
        <input
          type="number"
          v-model.number="form.height"
          class="w-full h-8 rounded-md bg-gray-50 dark:bg-gray-800 border border-gray-200 dark:border-gray-700 px-3 text-sm text-gray-900 dark:text-gray-100"
        />
      </div>
    </div>

    <div class="px-4 py-3 border-b border-gray-100 dark:border-gray-800">
      <label class="text-[10px] uppercase tracking-widest text-gray-400 font-medium block mb-3">Parameters</label>
      <ParamsForm v-model="form" :algorithm="algorithm" />
    </div>

    <div class="px-4 py-3 border-b border-gray-100 dark:border-gray-800">
      <label class="text-xs text-gray-500 dark:text-gray-400 block mb-1.5">Seed</label>
      <div class="flex gap-2">
        <input
          type="text"
          v-model="seed"
          class="flex-1 h-8 rounded-md bg-gray-50 dark:bg-gray-800 border border-gray-200 dark:border-gray-700 px-3 text-sm font-mono text-gray-900 dark:text-gray-100"
        />
        <button
          type="button"
          @click="randomizeSeed"
          class="h-8 rounded-md bg-gray-100 dark:bg-gray-800 border border-gray-200 dark:border-gray-700 px-3 text-sm text-gray-700 dark:text-gray-200 hover:bg-gray-200 dark:hover:bg-gray-700"
        >
          ↻
        </button>
      </div>
    </div>

    <div class="px-4 py-3 border-b border-gray-100 dark:border-gray-800">
      <button
        type="button"
        @click="generate"
        class="w-full h-10 rounded-lg bg-indigo-600 text-white text-sm font-medium hover:bg-indigo-500 transition"
      >
        Generate
      </button>
    </div>

    <div class="px-4 py-3">
      <div class="flex items-center justify-between mb-3">
        <span class="text-xs uppercase tracking-widest text-gray-400 font-medium">Compare / Overlay</span>
        <label class="inline-flex items-center gap-2 text-sm text-gray-600 dark:text-gray-300">
          <input
            type="checkbox"
            :checked="overlayModeLocal"
            @change="toggleOverlayMode($event)"
            class="h-4 w-4 rounded border-gray-300 dark:border-gray-700 bg-white dark:bg-gray-800"
          />
          On
        </label>
      </div>

      <select
        v-if="overlayModeLocal"
        :value="selectedOverlayIdLocal"
        @change="onOverlaySelect($event.target.value)"
        class="w-full h-9 rounded-md bg-gray-50 dark:bg-gray-800 border border-gray-200 dark:border-gray-700 px-3 text-sm text-gray-900 dark:text-gray-100"
      >
        <option value="">Select generation</option>
        <option
          v-for="item in historyItems"
          :key="item.id"
          :value="item.id"
        >
          {{ item.algorithmType }} · {{ item.seed }}
        </option>
      </select>
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from "vue";
import ParamsForm from "./ParamsForm.vue";
import { algorithmOptions, getAlgorithmDefaults } from "../../utils/algorithmSchemas";

const props = defineProps({
  overlayMode: {
    type: Boolean,
    default: false,
  },
  selectedOverlayId: {
    type: [String, Number],
    default: "",
  },
  historyItems: {
    type: Array,
    default: () => [],
  },
});

const emit = defineEmits(["generate", "update:overlayMode", "update:selectedOverlayId"]);

const algorithm = ref("BSP");
const seed = ref(Date.now());
const form = ref({ width: 50, height: 50, params: getAlgorithmDefaults("BSP") });
const overlayModeLocal = ref(props.overlayMode);
const selectedOverlayIdLocal = ref(props.selectedOverlayId || "");

watch(
  () => props.overlayMode,
  (value) => {
    overlayModeLocal.value = value;
  }
);

watch(
  () => props.selectedOverlayId,
  (value) => {
    selectedOverlayIdLocal.value = value || "";
  }
);

watch(algorithm, (value) => {
  form.value = {
    ...form.value,
    params: getAlgorithmDefaults(value),
  };
});

const randomizeSeed = () => {
  seed.value = Math.floor(Math.random() * 900000000) + 100000000;
};

const generate = () => {
  const payload = {
    algorithmType: algorithm.value,
    seed: Number(seed.value),
    width: Number(form.value.width),
    height: Number(form.value.height),
    params: { ...form.value.params },
  };

  emit("generate", payload);
};

const toggleOverlayMode = (event) => {

  const nextValue = event.target.checked;
  overlayModeLocal.value = nextValue;
  emit("update:overlayMode", nextValue);
  if (!nextValue) {
    selectedOverlayIdLocal.value = "";
    emit("update:selectedOverlayId", "");
  }
};

const onOverlaySelect = (value) => {
  selectedOverlayIdLocal.value = value;
  emit("update:selectedOverlayId", value);
};
</script>
