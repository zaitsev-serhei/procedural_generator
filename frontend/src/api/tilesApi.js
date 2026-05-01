import { api } from "./client";

export const generateMap = (payload) => {
  return api.post("/maps/generate", payload);
};

export const getHistory = (page = 0, size = 20) => {
  return api.get(`/maps/history?page=${page}&size=${size}`);
};