<template>
  <select class="input w-full rounded-2xl border border-slate-700 bg-slate-950 px-3 py-2 text-sm text-slate-100">
    <option v-for="option in options" :key="option.value" :value="option.value">
      {{ option.label }}
    </option>
  </select>
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
