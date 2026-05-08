<template>
  <div class="flex flex-col h-full min-w-0 overflow-x-hidden text-white space-y-[5px]">
    <div class="p-3 border-b border-[#2e3348]">
      <label class="text-[10px] uppercase tracking-widest text-white font-medium block mb-1.5">Algorithm</label>
      <select
        v-model="algorithm"
        class="w-full min-w-0 h-9 rounded-xl bg-white border border-slate-300 px-3 text-sm text-slate-900 focus:outline-none focus:ring-2 focus:ring-indigo-500/30"
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

    <div class="p-3 border-b border-[#2e3348] grid grid-cols-2 gap-3">
      <div>
        <label class="text-xs text-white block mb-1.5">Width</label>
        <input
          type="number"
          v-model.number="form.width"
          class="w-full min-w-0 h-9 rounded-xl bg-white border border-slate-300 px-3 text-sm text-slate-900 focus:outline-none focus:ring-2 focus:ring-indigo-500/30"
        />
      </div>
      <div>
        <label class="text-xs text-white block mb-1.5">Height</label>
        <input
          type="number"
          v-model.number="form.height"
          class="w-full min-w-0 h-9 rounded-xl bg-white border border-slate-300 px-3 text-sm text-slate-900 focus:outline-none focus:ring-2 focus:ring-indigo-500/30"
        />
      </div>
    </div>

    <div class="p-3 border-b border-[#2e3348] space-y-[5px]">
      <label class="text-[10px] uppercase tracking-widest text-white/70 font-medium block mb-3">Parameters</label>
      <ParamsForm v-model="form" :algorithm="algorithm" />
    </div>

    <div class="p-3 border-b border-[#2e3348]">
      <label class="text-xs text-white block mb-1.5">Seed</label>
      <div class="flex gap-2">
        <input
          type="text"
          v-model="seed"
          class="flex-1 min-w-0 h-9 rounded-xl bg-white border border-slate-300 px-3 text-xs font-mono text-slate-900 focus:outline-none focus:ring-2 focus:ring-indigo-500/30"
        />
        <button
          type="button"
          @click="randomizeSeed"
          class="h-9 rounded-xl bg-[#161b2f] border border-[#2e3348] px-3 text-sm font-medium text-white hover:bg-[#1f253e] transition"
        >
          ↻
        </button>
      </div>
    </div>

    <div class="p-3 border-b border-[#2e3348]">
      <button
        type="button"
        @click="generate"
        class="w-full h-9 rounded-xl bg-indigo-600 text-white text-sm font-medium hover:bg-indigo-700 active:scale-[0.98] transition duration-150"
      >
        Generate
      </button>
    </div>

    <div class="p-3">
      <div class="flex items-center justify-between mb-3">
        <span class="text-xs uppercase tracking-widest text-white/70 font-medium">Compare / Overlay</span>
        <label class="inline-flex items-center gap-2 text-sm text-white/80">
          <input
            type="checkbox"
            :checked="overlayModeLocal"
            @change="toggleOverlayMode($event)"
            class="h-4 w-4 rounded border-[#2e3348] bg-white text-slate-900 focus:ring-indigo-500"
          />
          On
        </label>
      </div>

      <select
        v-if="overlayModeLocal"
        :value="selectedOverlayIdLocal"
        @change="onOverlaySelect($event.target.value)"
        class="w-full min-w-0 h-9 rounded-xl bg-white border border-slate-300 px-3 text-sm text-slate-900 focus:outline-none focus:ring-2 focus:ring-indigo-500/30"
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
