<%@ page language="java" contentType="text/html;" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<!--		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">-->
<link rel="stylesheet" href="../css/bootstrap.min.css">

<!-- Custom CSS -->
<link rel="stylesheet" href="../css/style.css">

<title>Geslab 2.0</title>
</head>

<body>
	<%@page import="java.util.ArrayList"%>      <%--Importing all the dependent classes--%>
	<%@page import= "java.util.Iterator"%> 
	<%@page import= "geslab.database.modelo.*;"%> 
	<% ArrayList<Usuario> usuarios = (ArrayList) request.getAttribute("usuarios"); %>
	<% ArrayList<Area> areas = (ArrayList) request.getAttribute("areas"); %> 
	
	
	<div class="container-fluid">
		<div class="container">
			<div class="row py-5 align-items-center">
				<div class="col-3">
					<img class="logo-img" src="../images/logo-geslab.svg">
				</div>

				<div class="col-6">
					<p class="col-12 descripcion-txt">Bienvenido, ${nombre}</p>
				</div>
			</div>

			<div class="row">
				<div class="col-3">
					<div class="row">
						<div class="col-12">
							<button onclick="mostrarUsuarios()" class="btn boton-grande py-2 px-5">Usuarios</button>
						</div>
					</div>

					<div class="row pt-2">
						<div class="col-12">
							<button class="btn boton-grande py-2 px-5">Departamentos</button>
						</div>
					</div>

					<div class="row pt-2">
						<div class="col-12">
							<button onclick="mostrarAreas()" class="btn boton-grande py-2 px-5">Áreas</button>
						</div>
					</div>

					<div class="row pt-2">
						<div class="col-12">
							<button class="btn boton-grande py-2 px-5">Centros</button>
						</div>
					</div>

				</div>
				
				<script type="text/javascript">
							function mostrarUsuarios() {
							  document.getElementById("tabla-usuarios").style.display = '';
							  document.getElementById("tabla-areas").style.display = 'none';
							}
							
							function mostrarAreas() {
								  document.getElementById("tabla-areas").style.display = '';
								  document.getElementById("tabla-usuarios").style.display = 'none';
								}
							
							</script>
				

				<div class="col-9 container-admin">
				

					<div class="row py-3" id="tabla-usuarios" style="height: 100%">  <!-- USUARIOS  ----------------  -->
						<div class="container px-5">
							<div class="row" style="height: 85%">
								<table class="table table-borderless table-hover ">
									<thead>
										<tr>
											<th scope="col">Usuario</th>
											<th scope="col">Rol</th>
											<th scope="col">Federada</th>
											<th scope="col">Activa</th>
										</tr>
									</thead>
									<tbody>
										<%
										Iterator<Usuario> it = usuarios.iterator();
										while(it.hasNext()){
											Usuario u = it.next();
										%>
										<tr>
											<td><%=u.getNombre()%></td>
											<td><%=u.getRol()%></td>
											<td><%=u.getFederada()%></td>
											<td><%=u.getActivo()%></td>
										</tr>
										<%} %>
										<tr id="form-nuevo-usuario" style="display: none">
											<td><input type="text" name="nuevo-usuario" id="nuevo-usuario" placeholder="Usuario"></td>
											<td><input type="text" name="nuevo-rol" id="nuevo-rol" placeholder="Rol"></td>
											<td><input type="text" name="nuevo-federada" id="nuevo-federada" placeholder="Federada"></td>
											<td><input type="text" name="nuevo-activa" id="nuevo-activa" placeholder="Activa"></td>
										</tr>
										
									</tbody>
								</table>
							</div>
							
							<script type="text/javascript">
							function añadirUsuario() {
							  document.getElementById("form-nuevo-usuario").style.display = '';
							  document.getElementById("fila-añadir-usuario").style.display = 'none';
							  document.getElementById("fila-confirmar-usuario").style.display = '';
							}
							
							function cancelarAñadirUsuario(){
								location.reload();
							}
							</script>
							
							<div class="row justify-content-end" id="fila-añadir-usuario">
								<button onclick="añadirUsuario()" id="btn-usuario" class="btn login-button py-2 px-3">Añadir usuario</button>
							</div>
							
							<div class="row justify-content-end" id="fila-confirmar-usuario" style="display: none">
								<button onclick="cancelarAñadirUsuario()" id="btn-cancelar-usuario" class="btn login-button py-2 px-3 mr-3">Cancelar</button>
								<button onclick="confirmarAñadirUsuario()" id="btn-confirmar-usuario" class="btn login-button py-2 px-3">Confirmar</button>
							</div>
							
						</div>
					</div>
					
					
					<div class="row py-3" id="tabla-areas" style="height: 100%; display: 'none'">  <!-- AREAS  ----------------  -->
						<div class="container px-5">
							<div class="row" style="height: 85%">
								<table class="table table-borderless table-hover ">
									<thead>
										<tr>
											<th scope="col">Código</th>
											<th scope="col">Área</th>
											<th scope="col">Departamento</th>
										</tr>
									</thead>
									<tbody>
										<%
										Iterator<Area> itArea = areas.iterator();
										while(itArea.hasNext()){
											Area a = itArea.next();
										%>
										<tr>
											<td><%=a.getCodarea()%></td>
											<td><%=a.getNombre()%></td>
											<td><%=a.getDpto()%></td>
										</tr>
										<%} %>
										<tr id="form-nuevo-area" style="display: none">
											<td><input type="text" name="nuevo-codarea-area" id="uevo-codarea-area" placeholder="Codigo"></td>
											<td><input type="text" name="nuevo-nombre-area" id="nuevo-nombre-area" placeholder="Nombre"></td>
											<td><input type="text" name="nuevo-depto-area" id="nuevo-depto-area" placeholder="Departamento"></td>
										</tr>
										
									</tbody>
								</table>
							</div>
							
							<script type="text/javascript">
							function añadirArea() {
							  document.getElementById("form-nuevo-area").style.display = '';
							  document.getElementById("fila-añadir-area").style.display = 'none';
							  document.getElementById("fila-confirmar-area").style.display = '';
							}
							
							function cancelarAñadirArea(){
								location.reload();
							}
							</script>
							
							<div class="row justify-content-end" id="fila-añadir-area">
								<button onclick="añadirArea()" id="btn-usuario" class="btn login-button py-2 px-3">Añadir área</button>
							</div>
							
							<div class="row justify-content-end" id="fila-confirmar-area" style="display: none">
								<button onclick="cancelarAñadirArea()" id="btn-cancelar" class="btn login-button py-2 px-3 mr-3">Cancelar</button>
								<button onclick="confirmarAñadirArea()" id="btn-confirmar" class="btn login-button py-2 px-3">Confirmar</button>
							</div>
							
						</div>
					</div>
					
					
				</div>
			</div>

		</div>
	</div>

<!-- 			<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script> -->
<!-- 			<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script> -->
	<!--		<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>-->
	<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
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

