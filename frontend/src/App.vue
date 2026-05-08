<template>
  <div class="pg-app-shell">
    <header class="pg-topbar">
      <span class="pg-brand">2D Map Generator</span>
      <router-link to="/" custom v-slot="{ navigate, isActive }">
        <button
          type="button"
          @click="navigate"
          :class="[isActive ? 'pg-nav-button-active' : 'pg-nav-button-idle', 'pg-nav-button']"
        >
          Home
        </button>
      </router-link>
      <div class="h-4 w-px bg-slate-800"></div>
      <router-link to="/history" custom v-slot="{ navigate, isActive }">
        <button
          type="button"
          @click="navigate"
          :class="[isActive ? 'pg-nav-button-active' : 'pg-nav-button-idle', 'pg-nav-button']"
        >
          History
        </button>
      </router-link>
    </header>

    <router-view />
  </div>
</template>

<script setup>
</script>
