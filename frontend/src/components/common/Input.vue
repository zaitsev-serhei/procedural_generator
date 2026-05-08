<template>
  <label class="block mb-3 last:mb-0">
    <span class="block text-[12px] font-medium text-[#8b90b8] mb-1.5">{{ label }}</span>
    <input
      :type="type"
      :placeholder="placeholder"
      class="w-full h-8 px-2.5 text-[13px] text-[#f0f2ff] bg-[#22263a] border border-[#2e3348] rounded-md placeholder-[#555a7a] focus:outline-none focus:border-[#6366f1] focus:ring-1 focus:ring-[#6366f1]/30 transition-colors duration-150"
    />
  </label>
</template>

<script>
export default {
  name: "Input",
  props: {
    label: String,
    type: {
      type: String,
      default: "text",
    },
    placeholder: String,
  },
};
</script>
