<%@ page language="java" contentType="text/html;" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		
		<!-- Bootstrap CSS -->
<!--		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">-->
		<link rel="stylesheet" href="../css/bootstrap.min.css">
		
		<!-- Custom CSS -->
		<link rel="stylesheet" href="../css/style.css">
		
		<title>Geslab 2.0</title>
	</head>
	
	<body>
        <div  class="container-fluid">
            <div class="container">
                <div class="row">
                    <div class="col-12 py-5">
                        <img class="logo-img" src="../images/logo-geslab.svg"> 
                    </div>
                </div>

                <div class="row align-items-center py-4">
                    <div class="col-12 col-md-5 ubu">
                        <img class="ubu-img" src="../images/logo-ubu.svg"> 
                        <p class="col-sm-12 col-lg-8 descripcion-txt mt-3 pl-0">Aplicación de gestión del inventario de los laboratorios de química desarrollada para la Universidad de Burgos</p>
                    </div>
                    
                    <div class="col-12 col-md-7">
                        <div class="row">
                            <div class="col-12 col-lg-8 login-container py-3 px-4">
                                <form>
                                    <div class="row align-items-center">
                                        <label for="usuario-ip" class="col-4 login-label">Usuario</label>
                                        <div class="col-8">
                                          <input type="text" class="col-12 login-input" id="usuario-ip" placeholder="bioqbm">
                                        </div>
                                    </div>
                                    
                                    <div class="row align-items-center mt-2">
                                        <label for="password-ip" class="col-6 login-label">Contraseña</label>
                                        <div class="col-6">
                                          <input type="password" class="col-12 login-input" id="password-ip" placeholder="********">
                                        </div>
                                    </div>
                                    
                                    <div class="row justify-content-end mt-4 px-3">
                                        <button type="submit" class="btn login-button py-2 px-3">Entrar</button>
                                    </div>
                                          
                                </form>                        
                            </div>
                            
                        </div>    
                    </div>
                </div>
            </div>
        </div>
	
		<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
		<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<!--		<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>-->
		<script src="../js/bootstrap.min.js"></script>
	</body>

</html>

<!-- <html>

    <head>
        <meta charset="UTF-8">    
        Librerias online 
        <script src="https://cdnjs.cloudflare.com/ajax/libs/p5.js/0.5.11/p5.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/p5.js/0.5.11/addons/p5.dom.js"></script>
        
        <script src="https://cdn.jsdelivr.net/npm/p5@1.0.0/lib/p5.js"></script>
        <style> body {padding: 0; margin: 0;} </style>
    </head>
    
    <body>
        <script type="text/javascript" src="../../resources/js/sketch.js"></script>


		<script>
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
		</script>
       
    </body>
    
    
</html> -->

