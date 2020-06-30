<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<!-- Bootstrap CSS -->
<link rel="stylesheet" href="../css/bootstrap/bootstrap.min.css">

<!-- Custom CSS -->
<link rel="stylesheet" href="../css/style.css">

<!-- Extra CSS -->
<link rel="stylesheet" href="https://unpkg.com/simplebar@latest/dist/simplebar.css"/>
<script src="https://kit.fontawesome.com/e907f1c9ed.js" crossorigin="anonymous"></script>

<title>Geslab 2.0</title>
<link rel="shortcut icon" type="image/png" href="../images/favicon.png"/>

</head>
<body>
	<%@page import="java.util.ArrayList"%>
	<%@page import="geslab.database.admin.*"%>
	<%@page import="geslab.database.user.*"%>
	<%
	Usuario usuario = (Usuario) request.getAttribute("usuario");
	String mensaje = (String) request.getAttribute("mensaje");
	ArrayList<Calidad> calidades = (ArrayList<Calidad>) request.getAttribute("calidades");
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
				  			<a class="dropdown-item header__dropdown-item" href="/index.do" >Entradas/Salidas</a>
				  			<a class="dropdown-item header__dropdown-item" href="/productos.do" >Productos</a>
				  			<a class="dropdown-item header__dropdown-item" href="/ubicaciones.do" >Ubicaciones</a>
<!-- 				  			<a class="dropdown-item header__dropdown-item" href="/calidades.do" >Calidades</a> -->
				  			<a class="dropdown-item header__dropdown-item" href="/marcas.do" >Marcas</a>
				  			<a class="dropdown-item header__dropdown-item" href="/proveedores.do" >Proveedores</a>
				  			<a class="dropdown-item header__dropdown-item--logout" href="/login.do?accion=logout" >Cerrar sesion</a>
					  	</div>
					</div>
				</div>
			</div>
			
			
			<div class="row pt-3" style="height: 83vh">
			
				<div class="col-3 pl-0 pr-3" id="columna-filtro" style="height: 100%">
					<div class="container filtros py-3" style="height: 100%">
						<div class="row px-2" data-simplebar data-simplebar-auto-hide="false" style="height: 85%">
							<div class="col-12" style="height: 100%">
								<p class="filtros__label">Nombre</p>
									<input class="filtros__input" id="filtro-nombre" type="text" onkeyup="filtrado()">
									
								
								
									
							</div>
						</div>
						<div class="row align-items-center justify-content-center" style="height: 15%">
							<div class="col px-4">
								<button type="button" class="btn filtros__boton" onclick="reiniciarFiltro()">Reiniciar filtro</button>
							</div>
						</div>
					</div>
				</div>
			

				<div class="col-9 pl-3 pr-0" id="columna-datos" style="height: 100%">
					
						
					<div id="fila-pestañas" class="row mx-0 pb-2 align-items-center justify-content-between">
						<div class="col-4">
							<div class="row">
								<div class="col-6 px-0">
									<button id="bt-calidades" class="btn fila-pestañas__pestaña fila-pestanas__pestana--active">Calidades</button>
								</div>
								
							</div>
						</div>
					
					</div>
					
					
					
					<div class="col-12 container-info px-3">
						<div class="row pt-1 align-items-start" id="fila-tabla" style="height: 80%">
							<div class="col table-responsive" data-simplebar data-simplebar-auto-hide="false" style="height: 100%">
								
									<table id="tabla-calidades" class="table table-borderless table-hover table-sm">
										<thead >
										    <tr class="tabla-header">
										      <th class="tabla-header--item" scope="col">Nombre</th>
										      <th class="tabla-header--item" scope="col"></th>
										    </tr>
								  		</thead>
										
										 <tbody class="tabla-body">
											 	<%for (Calidad c : calidades) {%>
											 		<tr data-fila=<%=c.getCodcalidad()%>>
												      <td class="tabla-body--row" id="nombre-<%=c.getCodcalidad()%>"><%=c.getNombre()%></td>
	
												      <%if(usuario.getRol() != Rol.USUARIO){ %>
													      <td class="tabla-body--row" style="text-align: right;">
													      	<button type="button" id="" class="boton-tabla__accion" onclick="editar(<%=c.getCodcalidad()%>)">
													      		<i class="fas fa-pen"></i></button></td>
												      <%} %>
												    </tr>
											 	<%} %>
									    </tbody>
									</table>
							</div>
						</div>
						
						<div class="row py-3 align-items-center" style="height: 20%" id="fila-insertar">
									
									<div class="col">
										<%if(mensaje != null){ %>
										<div id="mensaje" class="mensaje-alerta form-inline mr-4 justify-content-between float-right">
	                                         <p class="d-inline-flex pr-4"><%=mensaje%><p>
	                                         <button id="cerrar-mensaje" type="button" class="close flex-fill" aria-label="Close" style="color: #ff0837;"><span aria-hidden="true">&times;</span></button>
                                     	</div>
                                     	<%
                                     	request.getSession().setAttribute("mensaje", null);
										} %>
									</div>
									
									<div class="col-3">
										<%if(usuario.getRol() != Rol.USUARIO){ %>
										<button type="button" id="boton-tabla__insertar" class="btn boton-tabla__añadir float-right" onclick="insertar()">Nueva calidad</button>
										<%} %>
									</div>
								</div>
						
		
					</div>

				</div>
				
				
			
			</div>
			
			
		</div>
	</div>
	
	<div class="modal fade" id="modalCalidad" tabindex="-1" role="dialog" aria-labelledby="modalCalidad" aria-hidden="true">
	  <div class="modal-dialog modal-dialog-scrollable modal-sm" role="document">
	    <div class="modal-content">
	    	<form id="insertar-calidad" action="/calidades.do" method="post">
		    	<div id="variables" style="display: none;">
					<input id="accion" name="accion"></input> 
					<input id="codigo" name="codigo"></input>
				</div>
	      <div class="modal-header justify-content-between">
      		<div class="col">
      			<h5 class="modal-title" id="tituloModal">Calidad</h5>
      		</div>
	      </div>
	      <div class="modal-body">
	        <div class="row">
	        	<div class="col px-4">
	        	
	        		<div class="row">
                        <div class="col-12">
                            <p class="modal__label">Nombre</p>
                            <input class="modal__input" type="text" id="insertar-nombre" name="insertar-nombre">
                        </div>
                        
	        		</div>
	        		
	        	</div>
	        </div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn boton-tabla__cancelar" onclick="cancelar()">Cancelar</button>
	        <button type="button" class="btn boton-tabla__añadir" onclick="confirmar()" id="botonInsertar">Confirmar</button>
	      </div>
	      </form>
	    </div>
	  </div>
	</div>
	
	
	<script src="https://unpkg.com/simplebar@latest/dist/simplebar.min.js"></script>
	<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" crossorigin="anonymous"></script>
	<script	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
	<script src="../js/bootstrap/bootstrap.min.js"></script>
	<script src="../js/calidades.js"></script>
	<script> inicializar(); </script>
	
</body>
</html>