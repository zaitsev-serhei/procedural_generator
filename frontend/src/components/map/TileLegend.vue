<template>
  <div class="flex gap-4 flex-wrap mt-4">
    <div
      v-for="(color, key) in tileColors"
      :key="key"
      class="flex items-center gap-2"
    >
      <div
        :style="{ background: color }"
        class="w-4 h-4 border"
      ></div>
      <span>{{ key }}</span>
    </div>
  </div>
</template>

<script setup>
import { tileColors } from "../../utils/tileColors";
</script>