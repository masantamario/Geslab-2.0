let angulo = 0;
let velocidad = 0.03;
let radio = 235;
let centroX;
let centroY;

function setup() {
  createCanvas(800, 800);
  centroX = width/2;
  centroY = height/2;
}

function draw() {
  background(255, 255, 255);
  noFill();
  stroke(112, 130, 247);
  strokeWeight(10);
  ellipse(centroX, centroY, radio*2);
  
  fill(112, 130, 247);
  noStroke();
  ellipseMode(CENTER);
  ellipse(centroX, centroY, 200);
  
  let x = centroX + radio * cos(angulo);
  let y = centroY + radio * sin(angulo);
  
  ellipse(x, y, 50);
  
  angulo = angulo + velocidad;
  
  
  
}