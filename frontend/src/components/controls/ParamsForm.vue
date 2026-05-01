<template>
  <div class="space-y-3">
    <div
      v-for="field in schema"
      :key="field.key"
    >
      <label class="text-xs text-gray-500 dark:text-gray-400 block mb-1.5">{{ field.label }}</label>

      <input
        v-if="field.type === 'number'"
        type="number"
        :step="field.step || 1"
        v-model.number="local.params[field.key]"
        class="w-full h-8 rounded-md bg-gray-50 dark:bg-gray-800 border border-gray-200 dark:border-gray-700 px-3 text-sm text-gray-900 dark:text-gray-100 focus:outline-none focus:ring-1 focus:ring-indigo-500"
      />

      <input
        v-else-if="field.type === 'text'"
        type="text"
        v-model="local.params[field.key]"
        class="w-full h-8 rounded-md bg-gray-50 dark:bg-gray-800 border border-gray-200 dark:border-gray-700 px-3 text-sm text-gray-900 dark:text-gray-100 focus:outline-none focus:ring-1 focus:ring-indigo-500"
      />

      <select
        v-else-if="field.type === 'select'"
        v-model="local.params[field.key]"
        class="w-full h-8 rounded-md bg-gray-50 dark:bg-gray-800 border border-gray-200 dark:border-gray-700 px-3 text-sm text-gray-900 dark:text-gray-100 focus:outline-none focus:ring-1 focus:ring-indigo-500"
      >
        <option
          v-for="opt in field.options"
          :key="opt"
          :value="opt"
        >
          {{ opt }}
        </option>
      </select>
    </div>
  </div>
</template>

<script setup>
import { reactive, watch, computed } from "vue";
import { algorithmSchemas } from "../../utils/algorithmSchemas";

const props = defineProps({
  algorithm: {
    type: String,
    required: true,
  },
  modelValue: {
    type: Object,
    default: () => ({
      width: 50,
      height: 50,
      params: {},
    }),
  },
});

const emit = defineEmits(["update:modelValue"]);

const local = reactive({
  width: props.modelValue.width ?? 50,
  height: props.modelValue.height ?? 50,
  params: { ...(props.modelValue.params ?? {}) },
});

const schema = computed(() => algorithmSchemas[props.algorithm] || []);

watch(
  () => props.modelValue,
  (value) => {
    if (!value) {
      return;
    }
    local.width = value.width ?? local.width;
    local.height = value.height ?? local.height;
    local.params = { ...(value.params ?? local.params) };
  },
  { immediate: true, deep: true }
);

watch(
  schema,
  (newSchema) => {
    const nextParams = {};
    newSchema.forEach((field) => {
      nextParams[field.key] = props.modelValue?.params?.[field.key] ?? field.default;
    });
    local.params = nextParams;
  },
  { immediate: true }
);

watch(
  local,
  (value) => {
    emit("update:modelValue", {
      width: value.width,
      height: value.height,
      params: { ...value.params },
    });
  },
  { deep: true }
);
</script>
