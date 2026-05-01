<template>
  <div class="h-screen flex flex-col overflow-hidden bg-gray-100 text-slate-900 dark:bg-gray-950 dark:text-slate-100">
    <header class="h-10 bg-white dark:bg-gray-900 border-b border-gray-200 dark:border-gray-800 flex items-center px-4 gap-3 shrink-0">
      <span class="text-sm font-medium text-gray-900 dark:text-gray-100 mr-auto">MapGen</span>
      <router-link to="/" class="text-sm px-3 py-1 rounded-md hover:bg-gray-100 dark:hover:bg-gray-800 text-gray-600 dark:text-gray-400 transition">Home</router-link>
      <div class="w-px h-4 bg-gray-200 dark:bg-gray-700"></div>
      <router-link to="/history" class="text-sm px-3 py-1 rounded-md hover:bg-gray-100 dark:hover:bg-gray-800 text-gray-600 dark:text-gray-400 transition">History</router-link>
    </header>

    <router-view />
  </div>
</template>

<script setup>
</script>
