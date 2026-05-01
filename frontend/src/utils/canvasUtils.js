export const getCanvasSize = (width, height, tileSize = 20) => ({
  width: width * tileSize,
  height: height * tileSize,
});

export const drawGrid = (context, width, height, tileSize) => {
  context.clearRect(0, 0, width * tileSize, height * tileSize);
  context.strokeStyle = "rgba(148, 163, 184, 0.25)";
  for (let x = 0; x <= width; x += 1) {
    context.beginPath();
    context.moveTo(x * tileSize, 0);
    context.lineTo(x * tileSize, height * tileSize);
    context.stroke();
  }
  for (let y = 0; y <= height; y += 1) {
    context.beginPath();
    context.moveTo(0, y * tileSize);
    context.lineTo(width * tileSize, y * tileSize);
    context.stroke();
  }
};
