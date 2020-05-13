<%@ page language="java" contentType="text/html;" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

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
	<%@page import="java.util.ArrayList"%>     
	<%@page import= "java.util.Iterator"%> 
	<%@page import= "geslab.database.modelo.*;"%> 
	<% ArrayList<Usuario> usuarios = (ArrayList<Usuario>) request.getAttribute("usuarios"); %>
	<% ArrayList<Area> areas = (ArrayList<Area>) request.getAttribute("areas"); %>
	
	
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
							<button onclick="mostrarElementos('usuario')" class="btn boton-grande py-2 px-5">Usuarios</button>
						</div>
					</div>

					<div class="row pt-2">
						<div class="col-12">
							<button class="btn boton-grande py-2 px-5">Departamentos</button>
						</div>
					</div>

					<div class="row pt-2">
						<div class="col-12">
							<button onclick="mostrarElementos('area')" class="btn boton-grande py-2 px-5">Áreas</button>
						</div>
					</div>

					<div class="row pt-2">
						<div class="col-12">
							<button class="btn boton-grande py-2 px-5">Centros</button>
						</div>
					</div>

				</div>
				
								

				<div class="col-9 container-admin">
				

					<div class="row py-3" id="tabla" style="height: 100%">  
						<div class="container px-5">
							<form id="form-confirmar" name="form-confirmar" action="/admin.do" method="post" style="height: 100%">
							<div class="row" style="height: 85%">
							
								<table id="tabla-usuarios"class="table table-borderless table-hover ">
									<thead>
										<tr>
											<th scope="col">Usuario</th>
											<th scope="col">Rol</th>
											<th scope="col">Federada</th>
											<th scope="col">Activo</th>
										</tr>
									</thead>
									<tbody>
										
<%-- 										<c:forEach var="u" items="${usuarios}"> --%>
<!-- 											<tr> -->
<%-- 												<td>${u.getUsuario()}</td> --%>
<%-- 												<td>${u.getRol().getRol()}</td> --%>
<%-- 												<td><input id="${u.getUsuario()}" type="checkbox" checked disabled></td> --%>
<%-- 												<td>${u.getActivo()}</td> --%>
<!-- 											</tr> -->
<%-- 										</c:forEach> --%>
										
										<%for(Usuario u:usuarios){ %>
											<tr>
												<td><%=u.getUsuario()%></td>
												<td><%=u.getRol().getRol()%></td>
												<td><input type="checkbox" onclick="javascript: return false;" <%if(u.getFederada()){%>checked<%} %>></td>
												<td><input type="checkbox" onclick="javascript: return false;" <%if(u.getActivo()){%>checked<%} %>></td>
											</tr>
										<%} %>
										<tr class="form-nuevo-elemento " id="form-nuevo-usuario" style="display: none">
											<td><input type="text" onchange="comprobarCampos()" class="input-elemento" name="nuevo-usuario" placeholder="Usuario"></td>
<!-- 											<td><input type="text" onchange="comprobarCampos()" class="input-elemento" name="nuevo-rol" placeholder="Rol"></td> -->
											<td>
												<select onchange="comprobarCampos()" class="input-elemento" name="nuevo-rol" id="nuevo-rol">
												    <c:forEach var="r" items="${roles}">
										        		<option value="${r.getId()}">${r.getRol()}</option>
													</c:forEach>
												</select>					
											</td>
<!-- 											<td><input type="text" onchange="comprobarCampos()" class="input-elemento" name="nuevo-federada" placeholder="Federada"></td> -->
<!-- 											<td><input type="text" onchange="comprobarCampos()" class="input-elemento" name="nuevo-activo" placeholder="Activo"></td> -->
											<td><input type="checkbox" class="check-elemento" name="nuevo-federada" value="true"></td>
											<td><input type="checkbox" class="check-elemento" name="nuevo-activo" value="true"></td>
										</tr>
									</tbody>
								</table>
								
								<table id="tabla-areas" class="table table-borderless table-hover ">
									<thead>
										<tr>
											<th scope="col">Código</th>
											<th scope="col">Área</th>
											<th scope="col">Departamento</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="a" items="${areas}">
											<tr>
												<td>${a.getCodarea()}</td>
												<td>${a.getNombre()}</td>
												<td>${a.getDpto()}</td>
											</tr>
										</c:forEach>
										
										
										<tr class="form-nuevo-elemento" id="form-nuevo-area" style="display: none">
											<td><input type="text" onchange="comprobarCampos()" class="input-elemento" name="nuevo-codarea-area" placeholder="Codigo"></td>
											<td><input type="text" onchange="comprobarCampos()" class="input-elemento" name="nuevo-nombre-area" placeholder="Nombre"></td>
											<td><input type="text" onchange="comprobarCampos()" class="input-elemento" name="nuevo-depto-area" placeholder="Departamento"></td>
										</tr>
										
									</tbody>
								</table>
								
							</div>
							
							<div class="row justify-content-end" id="fila-añadir">
								<button type="button" onclick="añadir()" id="btn-añadir" class="btn login-button py-2 px-3"></button>
							</div>
							
							<div class="row justify-content-end" id="fila-confirmar" style="display: none">
									<button type="button" onclick="cancelar()" id="btn-cancelar" class="btn login-button py-2 px-3 mr-3">Cancelar</button>	
									<button type="submit" id="btn-confirmar" name="btn-confirmar" class="btn login-button py-2 px-3">Confirmar</button>
							</div>
							</form>
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
	<script src="../js/admin.js"></script>
	<script> mostrarElementos('usuario'); </script>
	
</body>

</html>
