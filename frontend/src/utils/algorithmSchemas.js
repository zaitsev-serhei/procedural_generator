export const algorithmOptions = [
  { value: "PERLIN", label: "Perlin Noise" },
  { value: "CELLULAR", label: "Cellular Automata" },
  { value: "BSP", label: "Binary Space Partition" },
  { value: "GRAPH", label: "Graph Layout" },
  { value: "DFS_BACKTRACKING", label: "DFS Backtracking" },
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

  DFS_BACKTRACKING: [
    { key: "corridorWidth", label: "Corridor Width", type: "number", default: 1, readOnly: true },
  ],

  WFC: [
    { key: "tilesetName", label: "Tileset Name", type: "text", default: "default" },
    { key: "maxRetries", label: "Max Retries", type: "number", default: 8 },
  ],
};

export const paramHelpContent = {
  BSP: {
    roomCount: {
      label: "Room Count",
      descriptionEn: "Number of rooms to generate. More rooms create denser dungeons.",
      descriptionUa: "Кількість кімнат для генерації. Більше кімнат створює щільніші підземелля.",
      recommended: "8-20",
    },
    minRoomSize: {
      label: "Min Room Size",
      descriptionEn: "Minimum size of generated rooms. Smaller values create tighter layouts.",
      descriptionUa: "Мінімальний розмір кімнат. Менші значення створюють тісніші структури.",
      recommended: "4-8",
    },
    maxRoomSize: {
      label: "Max Room Size",
      descriptionEn: "Maximum size of generated rooms. Larger values create spacious areas.",
      descriptionUa: "Максимальний розмір кімнат. Більші значення створюють просторі зони.",
      recommended: "8-16",
    },
    corridorWidth: {
      label: "Corridor Width",
      descriptionEn: "Width of corridors connecting rooms.",
      descriptionUa: "Ширина коридорів між кімнатами.",
      recommended: "1-3",
    },
    splitRatio: {
      label: "Split Ratio",
      descriptionEn: "Controls how evenly the map space is divided during partitioning.",
      descriptionUa: "Контролює рівномірність поділу простору під час розбиття карти.",
      recommended: "0.35-0.65",
    },
  },
  CELLULAR: {
    fillProbability: {
      label: "Fill Probability",
      descriptionEn: "Initial chance of placing a wall tile. Higher values create denser caves.",
      descriptionUa: "Початкова ймовірність створення стіни. Більші значення створюють щільніші печери.",
      recommended: "0.40-0.48",
    },
    iterations: {
      label: "Iterations",
      descriptionEn: "Number of smoothing simulation passes.",
      descriptionUa: "Кількість проходів згладжування карти.",
      recommended: "4-7",
    },
    birthLimit: {
      label: "Birth Limit",
      descriptionEn: "Empty cells become walls if nearby wall count exceeds this value.",
      descriptionUa: "Порожні клітинки стають стінами, якщо кількість сусідніх стін перевищує це значення.",
      recommended: "4-5",
    },
    deathLimit: {
      label: "Death Limit",
      descriptionEn: "Wall cells survive only if enough neighboring walls exist.",
      descriptionUa: "Стіни зберігаються лише за достатньої кількості сусідніх стін.",
      recommended: "3-4",
    },
  },
  PERLIN: {
    octaves: {
      label: "Octaves",
      descriptionEn: "Number of noise layers combined together for detail complexity.",
      descriptionUa: "Кількість шарів шуму для створення деталей рельєфу.",
      recommended: "3-6",
    },
    persistence: {
      label: "Persistence",
      descriptionEn: "Controls how strongly smaller noise layers affect the final result.",
      descriptionUa: "Контролює вплив дрібних шумових шарів на кінцевий результат.",
      recommended: "0.4-0.6",
    },
    lacunarity: {
      label: "Lacunarity",
      descriptionEn: "Frequency multiplier between noise layers. Higher values increase variation.",
      descriptionUa: "Множник частоти між шарами шуму. Вищі значення додають варіативності.",
      recommended: "1.8-2.5",
    },
    scale: {
      label: "Scale",
      descriptionEn: "Zoom level of the terrain pattern. Smaller values create more detail.",
      descriptionUa: "Масштаб шуму. Менші значення створюють більше деталей.",
      recommended: "16-64",
    },
  },
  GRAPH: {
    nodeCount: {
      label: "Node Count",
      descriptionEn: "Number of graph nodes or key locations.",
      descriptionUa: "Кількість вузлів графа або ключових точок.",
      recommended: "8-20",
    },
    connectivity: {
      label: "Connectivity",
      descriptionEn: "Chance of creating connections between nodes.",
      descriptionUa: "Ймовірність створення зв’язків між вузлами.",
      recommended: "0.2-0.5",
    },
    layoutStrategy: {
      label: "Layout Strategy",
      descriptionEn: "Strategy used to place graph nodes on the map.",
      descriptionUa: "Стратегія розташування вузлів графа на карті.",
      recommended: "GRID or FORCE",
    },
    minEdgeWeight: {
      label: "Min Edge Weight",
      descriptionEn: "Minimum connection cost or path weight.",
      descriptionUa: "Мінімальна вага або вартість з’єднання.",
      recommended: "1-3",
    },
    maxEdgeWeight: {
      label: "Max Edge Weight",
      descriptionEn: "Maximum connection cost or path weight.",
      descriptionUa: "Максимальна вага або вартість з’єднання.",
      recommended: "4-10",
    },
  },
  WFC: {
    tilesetName: {
      label: "Tileset Name",
      descriptionEn: "Name of the tileset defining adjacency rules between tiles.",
      descriptionUa: "Назва набору тайлів із правилами сумісності між тайлами.",
      recommended: "default",
    },
    maxRetries: {
      label: "Max Retries",
      descriptionEn: "Maximum number of regeneration attempts before failure.",
      descriptionUa: "Максимальна кількість спроб генерації перед помилкою.",
      recommended: "5-15",
    },
  },
  DFS_BACKTRACKING: {
    corridorWidth: {
      label: "Corridor Width",
      descriptionEn: "Width of maze corridors. Larger values create wider passages.",
      descriptionUa: "Ширина коридорів лабіринту. Більші значення створюють ширші проходи.",
      recommended: "1-3",
    },
  },
  COMMON: {
    width: {
      label: "Map Width",
      descriptionEn: "Width of the generated map in tiles.",
      descriptionUa: "Ширина згенерованої карти у тайлах.",
      recommended: "50-200",
    },
    height: {
      label: "Map Height",
      descriptionEn: "Height of the generated map in tiles.",
      descriptionUa: "Висота згенерованої карти у тайлах.",
      recommended: "50-200",
    },
    seed: {
      label: "Seed",
      descriptionEn: "Random seed used for deterministic map generation. Same seed produces identical maps.",
      descriptionUa: "Випадкове зерно для детермінованої генерації. Однаковий seed створює однакові карти.",
      recommended: "Random unique value",
    },
  },
};

export const getParamHelp = (algorithm, key, fallbackLabel = "Parameter") =>
  paramHelpContent[algorithm]?.[key] ||
  paramHelpContent.COMMON?.[key] || {
    label: fallbackLabel,
    descriptionEn: "Controls how this generator shapes the resulting map.",
    descriptionUa: "Керує тим, як цей генератор формує результат карти.",
    recommended: "Use the default value first, then tune gradually.",
  };

export const getAlgorithmDefaults = (algorithm) =>
  Object.fromEntries(
    (algorithmSchemas[algorithm] || []).map((field) => [field.key, field.default])
  );
