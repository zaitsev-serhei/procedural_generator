export const algorithmOptions = [
  { value: "PERLIN", label: "Perlin Noise" },
  { value: "CELLULAR", label: "Cellular Automata" },
  { value: "BSP", label: "Binary Space Partition" },
  { value: "GRAPH", label: "Graph Layout" },
  { value: "WFC", label: "Wave Function Collapse" },
];

export const algorithmSchemas = {
  BSP: [
    { key: "roomCount", label: "Room Count", type: "number", default: 10 },
    { key: "minRoomSize", label: "Min Room Size", type: "number", default: 5 },
    { key: "maxRoomSize", label: "Max Room Size", type: "number", default: 15 },
    { key: "corridorWidth", label: "Corridor Width", type: "number", default: 2 },
    { key: "splitRatio", label: "Split Ratio", type: "number", step: 0.05, default: 0.5 },
  ],

  CELLULAR: [
    { key: "fillProbability", label: "Fill Probability", type: "number", step: 0.05, default: 0.45 },
    { key: "iterations", label: "Iterations", type: "number", default: 5 },
    { key: "birthLimit", label: "Birth Limit", type: "number", default: 4 },
    { key: "deathLimit", label: "Death Limit", type: "number", default: 3 },
  ],

  PERLIN: [
    { key: "octaves", label: "Octaves", type: "number", default: 4 },
    { key: "persistence", label: "Persistence", type: "number", step: 0.1, default: 0.5 },
    { key: "lacunarity", label: "Lacunarity", type: "number", step: 0.1, default: 2.0 },
    { key: "scale", label: "Scale", type: "number", default: 32 },
  ],

  GRAPH: [
    { key: "nodeCount", label: "Node Count", type: "number", default: 10 },
    { key: "connectivity", label: "Connectivity", type: "number", step: 0.1, default: 0.3 },
    { key: "layoutStrategy", label: "Layout", type: "select", options: ["GRID", "FORCE"], default: "GRID" },
    { key: "minEdgeWeight", label: "Min Edge Weight", type: "number", step: 0.1, default: 1.0 },
    { key: "maxEdgeWeight", label: "Max Edge Weight", type: "number", step: 0.1, default: 5.0 },
  ],

  WFC: [
    { key: "tilesetName", label: "Tileset Name", type: "text", default: "default" },
    { key: "maxRetries", label: "Max Retries", type: "number", default: 8 },
  ],
};

export const getAlgorithmDefaults = (algorithm) =>
  Object.fromEntries(
    (algorithmSchemas[algorithm] || []).map((field) => [field.key, field.default])
  );
