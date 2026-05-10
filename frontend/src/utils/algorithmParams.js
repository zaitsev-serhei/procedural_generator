export const algorithmDefaults = {
  BSP: {
    roomCount: 10,
    minRoomSize: 5,
    maxRoomSize: 15,
    corridorWidth: 2,
    splitRatio: 0.5,
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
    minEdgeWeight: 1.0,
    maxEdgeWeight: 5.0,
  },

  WFC: {
    tilesetName: "default",
    maxRetries: 8,
  },

  DFS_BACKTRACKING: {
  corridorWidth: 1
  },
};
