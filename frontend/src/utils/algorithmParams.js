export const algorithmDefaults = {
  BSP: {
    minRoomSize: 5,
    maxRoomSize: 15,
    corridorWidth: 2,
  },

  CELLULAR: {
    fillProbability: 0.45,
    iterations: 5,
    birthLimit: 4,
    deathLimit: 3,
  },

  PERLIN: {
    octaves: 4,
    persistence: 0.5,
    lacunarity: 2.0,
    scale: 32,
  },

  GRAPH: {
    nodeCount: 10,
    connectivity: 0.3,
    layoutStrategy: "GRID",
  },

  WFC: {
    patternSize: 3,
    backtrackLimit: 5,
  },
};