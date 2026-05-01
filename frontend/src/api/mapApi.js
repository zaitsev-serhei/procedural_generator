import { api } from "./client";

export const generateMap = (payload) => {
  return api.post("/maps/generate", payload);
};

export const getMapById = (id) => {
  return api.get(`/history/${id}`);
};