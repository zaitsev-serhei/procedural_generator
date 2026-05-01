<template>
  <section class="map-controls space-y-4 rounded-3xl border border-slate-800 bg-slate-900/90 p-4 shadow-glow">
    <AlgorithmSelector v-model="algorithm" />
    <ParamsForm v-model="form" :algorithm="algorithm" />
    <div class="flex items-center gap-3">
      <label class="text-sm text-slate-300">Seed</label>
      <input v-model="seed" type="number" class="input" />
      <button @click="generate" class="button">Generate</button>
    </div>
  </section>
</template>

<script setup>
import { ref } from "vue";
import AlgorithmSelector from "./AlgorithmSelector.vue";
import ParamsForm from "./ParamsForm.vue";

const emit = defineEmits(["generate"]);

const algorithm = ref("BSP");
const seed = ref(Date.now());
const form = ref({ width: 50, height: 50, params: {} });

const generate = () => {
  const payload = {
    algorithmType: algorithm.value,
    seed: Number(seed.value),
    width: Number(form.value.width),
    height: Number(form.value.height),
    params: form.value.params || {},
  };

  console.log("REQUEST →", payload); // 🔥 debug

  emit("generate", payload);
};
</script>

<style scoped>
.input {
  background: #1f2937;
  color: #f8fafc;
  border: 1px solid #334155;
  border-radius: 0.75rem;
  padding: 0.5rem 0.75rem;
}

.button {
  background: #6366f1;
  color: white;
  border: none;
  border-radius: 0.75rem;
  padding: 0.5rem 1rem;
  cursor: pointer;
}

.button:hover {
  background: #4f46e5;
}
</style>
