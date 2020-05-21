<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<!-- Bootstrap CSS -->
<link rel="stylesheet" href="../css/bootstrap.min.css">

<!-- Custom CSS -->
<link rel="stylesheet" href="../css/style.css">

<title>Geslab 2.0</title>
</head>
<body>
	<%@page import="java.util.ArrayList"%>
	<%@page import="geslab.database.modelo.*"%>
	<%
	Usuario usuario = (Usuario) request.getAttribute("usuario");
	ArrayList<Area> areas = (ArrayList<Area>) request.getAttribute("areas");
	ArrayList<Departamento> departamentos = (ArrayList<Departamento>) request.getAttribute("departamentos");
	ArrayList<Centro> centros = (ArrayList<Centro>) request.getAttribute("centros");
	
	%>
	<div class="container-fluid">
		<div class="container">
			<div class="row py-3 justify-content-between align-items-center">
				<div class="col-3">
					<img class="logo-img" src="../images/logo-geslab.svg">
				</div>
				
				<div class="col-3 pr-0 text-right">
					<p>Bienvenido, <%=usuario.getNombre()%></p>
				</div>
			</div>
			
			<div class="row pt-3">
				<div class="col-3" id="columna-filtros">
					<p class="filtro-label">Producto</p>
						<input class="filtro-input" id="filtro-producto" type="text">
					<p class="filtro-label">Fórmula</p>
						<input class="filtro-input" id="filtro-formula" type="text">
					<p class="filtro-label">Departamento</p>
						<select class="filtro-select" id="filtro-dpto">
							<%for(Departamento d:departamentos){ %>
								<option><%=d.getNombre()%></option>
							<%}%>
						</select>
					<p class="filtro-label">Área</p>
						<select class="filtro-select" id="filtro-area">
							<%for(Area a:areas){ %>
								<option><%=a.getNombre()%></option>
							<%}%>
						</select>
					<p class="filtro-label">Centro</p>
						<select class="filtro-select" id="filtro-centro">
							<%for(Centro c:centros){ %>
								<option><%=c.getNombre()%></option>
							<%}%>
						</select>
	
				</div>
				
				<div class="col-9" id="columna-datos">
					<div id="fila-pestañas" class="row align-items-center justify-content-between">
						<div class="col-4">
							<div class="row">
								<div class="col-6">Entradas</div>
								<div class="col-6">Salidas</div>
							</div>
						</div>
						
						<div class="col-3">
							<div class="row justify-content-end">Desde:</div>
							<div class="row justify-content-end">Hasta:</div>
						</div>
					</div>
					
					<div id="fila-tabla" class="row pt-3">
						<table class="table table-borderless table-hover">
							<thead>
							    <tr>
							      <th scope="col">Producto</th>
							      <th scope="col">Fecha</th>
							      <th scope="col">Caducidad</th>
							      <th scope="col">Lote</th>
							      <th scope="col">Uds.</th>
							      <th scope="col">Capacidad</th>
							      <th scope="col">Acciones</th>
							    </tr>
					  		</thead>
						
							 <tbody>
							    <tr>
							      <td scope="col">Nitrito</td>
							      <td scope="col">12/24/19</td>
							      <td scope="col">30/03/2022</td>
							      <td scope="col">12587D</td>
							      <td scope="col">10</td>
							      <td scope="col">300</td>
							      <td scope="col">Editar</td>
							    </tr>
						    </tbody>
						
						</table>
						
					</div>
					
				</div>
				
				
			
			</div>
			
			
		</div>
	</div>


</body>
</html>