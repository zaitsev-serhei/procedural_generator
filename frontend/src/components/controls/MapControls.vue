<template>
  <div class="pg-control-shell">
    <div class="pg-control-section">
      <label class="pg-field-label-caps">Algorithm</label>
      <select
        v-model="algorithm"
        class="pg-input"
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

    <div class="pg-control-section-muted grid grid-cols-2 gap-3">
      <div>
        <label class="pg-field-label">Width</label>
        <input
          type="number"
          v-model.number="form.width"
          class="pg-input"
        />
      </div>
      <div>
        <label class="pg-field-label">Height</label>
        <input
          type="number"
          v-model.number="form.height"
          class="pg-input"
        />
      </div>
    </div>

    <div class="pg-control-section space-y-2">
      <label class="pg-field-label-caps">Parameters</label>
      <ParamsForm v-model="form" :algorithm="algorithm" />
    </div>

    <div class="pg-control-section-muted">
      <label class="pg-field-label">Seed</label>
      <div class="flex gap-2">
        <input
          type="text"
          v-model="seed"
          class="pg-input flex-1 font-mono text-xs"
        />
        <button
          type="button"
          @click="randomizeSeed"
          class="pg-icon-button"
        >
          ↻
        </button>
      </div>
    </div>

    <div class="pg-control-section">
      <button
        type="button"
        @click="generate"
        class="pg-primary-button"
      >
        Generate
      </button>
    </div>

    <div class="p-3">
      <div class="flex items-center justify-between mb-3">
        <span class="pg-field-label-caps mb-0">Compare / Overlay</span>
        <label class="inline-flex items-center gap-2 text-sm text-slate-300">
          <input
            type="checkbox"
            :checked="overlayModeLocal"
            @change="toggleOverlayMode($event)"
            class="pg-checkbox"
          />
          On
        </label>
      </div>

      <select
        v-if="overlayModeLocal"
        :value="selectedOverlayIdLocal"
        @change="onOverlaySelect($event.target.value)"
        class="pg-input"
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
