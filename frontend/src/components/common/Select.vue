<template>
  <div class="relative">
    <select class="w-full h-8 px-2.5 pr-7 text-[13px] text-[#f0f2ff] bg-[#22263a] border border-[#2e3348] rounded-md appearance-none focus:outline-none focus:border-[#6366f1] focus:ring-1 focus:ring-[#6366f1]/30 cursor-pointer transition-colors duration-150">
      <option v-for="option in options" :key="option.value" :value="option.value">
        {{ option.label }}
      </option>
    </select>
    <div class="pointer-events-none absolute right-2.5 top-1/2 -translate-y-1/2 text-[#555a7a] text-[10px]">▾</div>
  </div>
</template>

<script>
export default {
  name: "Select",
  props: {
    options: {
      type: Array,
      default: () => [],
    },
  },
};
</script>
