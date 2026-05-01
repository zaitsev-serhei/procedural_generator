<template>
  <label class="block space-y-2 text-sm text-slate-200">
    <span>{{ label }}</span>
    <input
      :type="type"
      :placeholder="placeholder"
      class="input w-full rounded-2xl border border-slate-700 bg-slate-950 px-3 py-2 text-sm text-slate-100"
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
