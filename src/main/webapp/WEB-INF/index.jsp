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
<script src="https://kit.fontawesome.com/e907f1c9ed.js" crossorigin="anonymous"></script>


<title>Geslab 2.0</title>
</head>
<body>
	<%@page import="java.util.ArrayList"%>
	<%@page import="geslab.database.admin.*"%>
	<%@page import="geslab.database.user.*"%>
	<%
	
	Usuario usuario = (Usuario) request.getAttribute("usuario");
	ArrayList<Area> areas = (ArrayList<Area>) request.getAttribute("areas");
	ArrayList<Departamento> departamentos = (ArrayList<Departamento>) request.getAttribute("departamentos");
	ArrayList<Centro> centros = (ArrayList<Centro>) request.getAttribute("centros");
	ArrayList<Entrada> entradas = (ArrayList<Entrada>) request.getAttribute("entradas");
	
	String tabla = (String) request.getAttribute("mostrarTabla");
	System.out.println(tabla);
	
	%>
	<div class="container-fluid">
		<div class="container">
			<div class="row py-3 justify-content-between align-items-center">
				<div class="col-3">
					<img class="header__logo" src="../images/logo-geslab.svg">
				</div>
				
				<div class="col-3 pr-0 text-right header__menu">
						<div class="dropdown show">
						<a class="dropdown-toggle header__dropdown" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					    <%=usuario.getNombre()%></a>
					
					  	<div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
					  		<form id="opciones-usuario" action="/login.do" method="get">
					  			<input type="hidden" id="accion" name="accion" value="" />
					  			<button class="dropdown-item header__dropdown-item" onclick="cerrarSesion()">Ubicaciones</button>
					  			<button class="dropdown-item header__dropdown-item--logout" onclick="cerrarSesion()">Cerrar sesión</button>
					  		</form>
					    	
					  	</div>
					</div>
				</div>
			</div>
			
			
			<div class="row pt-3">
			
				<div class="col-3 pl-0 pr-3" id="columna-filtro">
					<div class="container filtros" style="height: 100%">
						<div class="row px-2 pt-3" style="height: 85%">
							<div class="col-12">
								<p class="filtros__label">Producto</p>
									<input class="filtros__input" id="filtro-producto" type="text">
								<p class="filtros__label">Fórmula</p>
									<input class="filtros__input" id="filtro-formula" type="text">
								<p class="filtros__label">Departamento</p>
									<select class="filtros__select" id="filtro-dpto">
										<%for(Departamento d:departamentos){ %>
											<option><%=d.getNombre()%></option>
										<%}%>
									</select>
								<p class="filtros__label">Área</p>
									<select class="filtros__select" id="filtro-area">
										<%for(Area a:areas){ %>
											<option><%=a.getNombre()%></option>
										<%}%>
									</select>
								<p class="filtros__label">Centro</p>
									<select class="filtros__select" id="filtro-centro">
										<%for(Centro c:centros){ %>
											<option><%=c.getNombre()%></option>
										<%}%>
									</select>
							</div>
						</div>
					</div>
				</div>
			

				<div class="col-9 pl-3 pr-0" id="columna-datos">
					
					<form id="mostrarTabla" action="/index.do" method="get">
						<input type="hidden" id="tabla" name="tabla" value="" />
						
						<div id="fila-pestañas" class="row mx-0 pb-2 align-items-center justify-content-between">
							<div class="col-4">
								<div class="row">
									<div class="col-6 px-0">
										<button id="bt-entradas" onclick="mostrarElemento('entrada')" class="btn fila-pestañas__pestaña">Entradas</button>
									</div>
									<div class="col-6 ">
										<button id="bt-salidas" onclick="mostrarElemento('salida')" class="btn fila-pestañas__pestaña">Salidas</button>
									</div>
								</div>
							</div>
						
							<div class="col-3">
								<div class="row justify-content-end">Desde:</div>
								<div class="row justify-content-end">Hasta:</div>
							</div>
						</div>
					</form>
					
					
					
					<div class="col-12 container-info px-0">
						<div class="container px-5" style="height: 100%">
							<div class="row py-1" id="fila-tabla" style="height: 85%">
								<%if(tabla.equals("entrada")){ %>
									<table class="table table-borderless table-hover table-sm">
										<thead class="tabla-header">
										    <tr>
										      <th class="tabla-header--item" scope="col">Fecha</th>
										      <th class="tabla-header--item" scope="col">Producto</th>
										      <th class="tabla-header--item" scope="col">Uds.</th>
										      <th class="tabla-header--item" scope="col">Cpcd.</th>
										      <th class="tabla-header--item" scope="col">Ub.</th>
										      <th class="tabla-header--item" scope="col">Area</th>
										      <th class="tabla-header--item" scope="col">Marca</th>
										    </tr>
								  		</thead>
									
										 <tbody class="tabla-body">
										 	<%for (Entrada e : entradas) {%>
										 		<tr>
											      <td class="tabla-body--row"><%=e.getFecha()%></td>
											      <td class="tabla-body--row"><%=e.getFicha().getProducto().getNombre()%></td>
											      <td class="tabla-body--row"><%=e.getUnidades()%></td>
											      <td class="tabla-body--row"><%=e.getCapacidad()%> <%=e.getG_ml()%>.</td>
											      <td class="tabla-body--row"><%=e.getFicha().getUbicacion().getNombre()%></td>
											      <td class="tabla-body--row"><%=e.getFicha().getUbicacion().getArea()%></td>
											      <td class="tabla-body--row"><%=e.getFicha().getMarca()%></td>
											      
											      <td class="tabla-body--row" style="text-align: right;">
											      	<button type="button" id="" class="boton-tabla__accion">
											      		<i class="fas fa-pen"></i></button></td>
											    </tr>
										 	<%} %>
										 	
										 
										    
									    </tbody>
									</table>
								<%} else if(tabla.equals("salida")){ %>
								
								<%} %>
							
								
							</div>
							
							<div class="row justify-content-end" id="fila-insertar">
								<button type="button" id="boton-tabla__insertar" class="btn boton-tabla__añadir"></button>
							</div>
						
						</div>
		
					</div>

				</div>
				
				
			
			</div>
			
			
		</div>
	</div>

	<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"></script>
	<script	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
	<script src="../js/bootstrap.min.js"></script>
	<script src="../js/index.js"></script>
	<script> inicializar('<%=tabla%>'); </script>
</body>
</html>