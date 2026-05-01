<template>
  <div class="min-h-screen bg-gray-900 text-white">
    <nav class="p-4 bg-gray-800 flex gap-4">
      <router-link to="/">Home</router-link>
      <router-link to="/history">History</router-link>
    </nav>

    <router-view />
  </div>
</template>

<script setup>
</script>