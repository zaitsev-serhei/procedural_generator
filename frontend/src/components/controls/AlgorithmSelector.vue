<template>
  <select
    class="bg-gray-800 p-2 rounded"
    v-model="model"
  >
    <option value="BSP">BSP</option>
    <option value="CELLULAR">Cellular</option>
    <option value="PERLIN">Perlin</option>
    <option value="GRAPH">Graph</option>
    <option value="WFC">WFC</option>
  </select>
</template>

<script setup>
const model = defineModel();
</script>