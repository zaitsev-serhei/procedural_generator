<template>
  <canvas ref="overlayCanvas" class="absolute top-0 left-0 pointer-events-none"></canvas>
</template>

<script setup>
import { onMounted, watch, ref } from "vue";

const props = defineProps({
  rooms: Array,
  connections: Array,
  width: Number,
  height: Number,
  tileSize: Number,
});

const overlayCanvas = ref(null);

const draw = () => {
  if (!props.rooms) return;

  const canvas = overlayCanvas.value;
  const ctx = canvas.getContext("2d");

  canvas.width = props.width * props.tileSize;
  canvas.height = props.height * props.tileSize;

  ctx.clearRect(0, 0, canvas.width, canvas.height);

  // 🔷 Draw rooms
  ctx.strokeStyle = "rgba(0,255,0,0.8)";
  ctx.lineWidth = 2;

  props.rooms.forEach(room => {
    ctx.strokeRect(
      room.x * props.tileSize,
      room.y * props.tileSize,
      room.width * props.tileSize,
      room.height * props.tileSize
    );
  });

  // 🔶 Draw connections
  ctx.strokeStyle = "rgba(255,0,0,0.8)";

  props.connections.forEach(conn => {
    const r1 = props.rooms[conn.fromIndex];
    const r2 = props.rooms[conn.toIndex];

    if (!r1 || !r2) return;

    const x1 = (r1.x + r1.width / 2) * props.tileSize;
    const y1 = (r1.y + r1.height / 2) * props.tileSize;

    const x2 = (r2.x + r2.width / 2) * props.tileSize;
    const y2 = (r2.y + r2.height / 2) * props.tileSize;

    ctx.beginPath();
    ctx.moveTo(x1, y1);
    ctx.lineTo(x2, y2);
    ctx.stroke();
  });
};

onMounted(draw);
watch(() => [props.rooms, props.connections], draw);
</script>