import { defineStore } from "pinia";
import { getHistory } from "../api/tilesApi";

export const useHistoryStore = defineStore("history", {
  state: () => ({
    items: [],
    page: 0,
    size: 10,
    totalPages: 0,
    loading: false,
  }),

  actions: {
    async fetch(page = 0) {
      this.loading = true;

      try {
        const res = await getHistory(page, this.size);

        this.items = res.data.content;
        this.page = res.data.page;
        this.totalPages = res.data.totalPages;

      } catch (e) {
        console.error(e);
      } finally {
        this.loading = false;
      }
    },
  },
});