<script setup>
import { ref } from "vue";
import AlgorithmSelector from "./AlgorithmSelector.vue";
import ParamsForm from "./ParamsForm.vue";

const emit = defineEmits(["generate"]);

const algorithm = ref("BSP");
const seed = ref(Date.now());
const form = ref({});

<ParamsForm
  v-model="form" :algorithm="algorithm"
/>

const generate = () => {
  const payload = {
    algorithmType: algorithm.value,
    seed: Number(seed.value),
    width: Number(form.value.width),
    height: Number(form.value.height),
    params: { ...toRaw(form.value.params) },
  };

  console.log("REQUEST →", payload); // 🔥 debug
  console.log(JSON.stringify(payload, null, 2));
  emit("generate", payload);
};
</script>