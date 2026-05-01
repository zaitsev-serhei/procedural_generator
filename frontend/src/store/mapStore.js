import { defineStore } from "pinia";
import { generateMap } from "../api/mapApi";

export const useMapStore = defineStore("map", {
  state: () => ({
    map: null,
    loading: false,
    error: null,
  }),
  actions: {
    setMap(map) {
      this.map = {
        tiles: map.tiles,
        rooms: map.rooms || [],
        connections: map.connections || [],
      };
    },
    async generate(payload) {
      this.loading = true;
      this.error = null;

      try {
        const res = await generateMap(payload);

        console.log("RESPONSE ←", res.data); // 🔥 debug

        this.map = {
          tiles: res.data.tiles,
          rooms: res.data.rooms || [],
          connections: res.data.connections || [],
        };
      } catch (e) {
        console.error(e);
        this.error = e.response?.data?.message || e.message;
      } finally {
        this.loading = false;
      }
    },
  },
});