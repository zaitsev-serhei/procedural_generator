<template>
  <div class="space-y-2">
    <div
      v-for="field in schema"
      :key="field.key"
      class="min-w-0"
    >
      <div class="pg-field-heading">
        <label class="pg-field-label">{{ field.label }}</label>
        <button
          type="button"
          class="pg-help-button"
          :aria-expanded="activeHelpKey === field.key"
          :aria-label="`Show help for ${field.label}`"
          @click="activeHelpKey = activeHelpKey === field.key ? '' : field.key"
        >
          ?
        </button>
      </div>

      <input
        v-if="field.type === 'number'"
        type="number"
        :step="field.step || 1"
        v-model.number="local.params[field.key]"
        :readonly="field.readOnly || false"
        class="pg-input-sm"
      />

      <input
        v-else-if="field.type === 'text'"
        type="text"
        v-model="local.params[field.key]"
        :readonly="field.readOnly || false"
        class="pg-input-sm"
      />

      <select
        v-else-if="field.type === 'select'"
        v-model="local.params[field.key]"
        :disabled="field.readOnly || false"
        class="pg-input-sm"
      >
        <option
          v-for="opt in field.options"
          :key="opt"
          :value="opt"
        >
          {{ opt }}
        </option>
      </select>

      <div
        v-if="activeHelpKey === field.key"
        class="pg-help-bubble"
      >
        <div class="pg-help-title">{{ getParamHelp(algorithm, field.key, field.label).label }}</div>
        <p>{{ getParamHelp(algorithm, field.key, field.label).descriptionEn }}</p>
        <p>{{ getParamHelp(algorithm, field.key, field.label).descriptionUa }}</p>
        <div class="pg-help-recommended">
          Recommended: {{ getParamHelp(algorithm, field.key, field.label).recommended }}
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, watch, computed, ref } from "vue";
import { algorithmSchemas, getParamHelp } from "../../utils/algorithmSchemas";

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
const activeHelpKey = ref("");

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
