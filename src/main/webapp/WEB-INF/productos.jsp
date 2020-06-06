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
	ArrayList<Producto> productos = (ArrayList<Producto>) request.getAttribute("productos");
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
<!-- 				  			<a class="dropdown-item header__dropdown-item" href="/productos.do" >Productos</a> -->
				  			<a class="dropdown-item header__dropdown-item" href="/ubicaciones.do" >Ubicaciones</a>
				  			<a class="dropdown-item header__dropdown-item" href="/calidades.do" >Calidades</a>
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
								<p class="filtros__label">CAS</p>
									<select class="filtros__select" id="filtro-cas" onchange="filtrar('cas', 0)">
										<option selected></option>
										<%for(Producto p:productos){ %>
											<option><%=p.getCas()%></option>
										<%}%>
									</select>
								<p class="filtros__label">Nombre</p>
									<select class="filtros__select" id="filtro-nombre" onchange="filtrar('nombre', 1)">
										<option selected></option>
										<%for(Producto p:productos){ %>
											<option><%=p.getNombre()%></option>
										<%}%>
									</select>
									
								<p class="filtros__label">Fórmula</p>
									<select class="filtros__select" id="filtro-formula" onchange="filtrar('formula', 2)">
										<option selected></option>
										<%for(Producto p:productos){ %>
											<option><%=p.getFormula()%></option>
										<%}%>
									</select>
								<p class="filtros__label">No Einecs</p>
									<select class="filtros__select" id="filtro-einecs" onchange="filtrar('einecs', 4)">
										<option selected></option>
										<%for(Producto p:productos){ %>
											<option><%=p.getN_einecs()%></option>
										<%}%>
									</select>
								<p class="filtros__label">No Ec</p>
									<select class="filtros__select" id="filtro-ec" onchange="filtrar('ec', 5)">
										<option selected></option>
										<%for(Producto p:productos){ %>
											<option><%=p.getN_ec()%></option>
										<%}%>
									</select>
								
									
							</div>
						</div>
						<div class="row align-items-center justify-content-center" style="height: 15%">
							<div class="col px-4">
								<button type="button"  class="btn filtros__boton">Reiniciar filtro</button>
							</div>
						</div>
					</div>
				</div>
			

				<div class="col-9 pl-3 pr-0" id="columna-datos" style="height: 100%">
					
						
					<div id="fila-pestañas" class="row mx-0 pb-2 align-items-center justify-content-between">
						<div class="col-4">
							<div class="row">
								<div class="col-6 px-0">
									<button id="bt-productos" class="btn fila-pestañas__pestaña fila-pestanas__pestana--active">Productos</button>
								</div>
								
							</div>
						</div>
					
					</div>
					
					
					
					<div class="col-12 container-info px-3">
						<div class="row pt-1 align-items-start" id="fila-tabla" style="height: 80%">
							<div class="col table-responsive" data-simplebar data-simplebar-auto-hide="false" style="height: 100%">
								
									<table id="tabla-productos" class="table table-borderless table-hover table-sm">
										<thead >
										    <tr class="tabla-header">
										      <th class="tabla-header--item" scope="col">Cas</th>
										      <th class="tabla-header--item" scope="col">Nombre</th>
										      <th class="tabla-header--item" scope="col">Fórmula</th>
										      <th class="tabla-header--item" scope="col">F. Desarrollada</th>
										      <th class="tabla-header--item" scope="col"></th>
										      
										      <th style="display: none" scope="col">Peso molecular</th>
										      <th style="display: none" scope="col">N Einecs</th>
										      <th style="display: none" scope="col">N Ec</th>
										    </tr>
								  		</thead>
										
										 <tbody class="tabla-body">
											 	<%for (Producto p : productos) {%>
											 		<tr data-fila=<%=p.getCas()%>>
												      <td class="tabla-body--row" id="cas-<%=p.getCas()%>"><%=p.getCas()%></td>
												      <td class="tabla-body--row" id="nombre-<%=p.getCas()%>"><%=p.getNombre()%></td>
												      <td class="tabla-body--row" id="formula-<%=p.getCas()%>"><%=p.getFormula()%></td>
												      <td class="tabla-body--row" id="f_des-<%=p.getCas()%>"><%=p.getFormula_des()%></td>
												      
													   <td id="peso-<%=p.getCas()%>" style="display: none"><%=p.getPeso_mol()%></td>											       
	 												   <td id="einecs-<%=p.getCas()%>" style="display: none"><%=p.getN_einecs()%></td>											       
	 												   <td id="ec-<%=p.getCas()%>" style="display: none"><%=p.getN_ec()%></td>
	
												      <td class="tabla-body--row info" style="text-align: right;">
												      	<button type="button" id="" class="boton-tabla__accion" onclick="editar(<%=p.getCas()%>)">
												      		<i class="fas fa-pen"></i></button></td>
												    </tr>
											 	<%} %>
									    </tbody>
									</table>
							</div>
						</div>
						<div class="row align-items-end" id="fila-info" style="height: 20%">
							<div class="col align-self-end">
							
							<div class="row extra-info mx-1" id="container-info" style="height:236px; display:none">
									<div class="col"><div class="row">
									<div class="col-12 px-4">
                                        <div class="row justify-content-end pt-1">
                                            <div class="col-1 text-right" onclick='ocultarExtraInfo()'><i class="fas fa-times extra-info__boton"></i></div>
                                        </div>

                                        <div class="row pt-2">
                                        	<div class="col-4 form-inline">
                                                <p class="extra-info__label d-inline-flex">Peso<p>
                                                <p class="extra-info__input flex-fill" id="extra-info-peso"><p>
                                            </div>
                                            <div class="col-4 form-inline">
                                                <p class="extra-info__label d-inline-flex">No Einecs<p>
                                                <p class="extra-info__input flex-fill" id="extra-info-einecs"><p>
                                            </div>
                                            <div class="col-4 form-inline">
                                                <p class="extra-info__label d-inline-flex">No Ec<p>
                                                <p class="extra-info__input flex-fill" id="extra-info-ec"><p>
                                            </div>
                                        </div>
                                        
                                        <div class="row pt-2">
                                            <div class="col-8">
                                                <div class="row">
                                                    <div class="col-12 form-inline">
                                                        <p class="extra-info__label d-inline-flex">Peligro<p>
                                                        <p class="extra-info__input flex-fill" id="extra-info-peligro"><p>
                                                    </div>
                                                </div>
                                                <div class="row pt-2">
                                                    <div class="col-12 form-inline">
                                                        <p class="extra-info__label d-inline-flex">Prudencia<p>
                                                        <p class="extra-info__input flex-fill" id="extra-info-prudencia"><p>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-4">
                                                <div class="row">
                                                    <p class="extra-info__label d-inline-flex">Pictogramas<p>
                                                </div>
                                            </div>
                                        </div>
                                        
                                    </div>
                                    </div></div>
								
								</div>
								
						<div class="row py-3" style="height: 20%" id="fila-insertar">
							<div class="col">
								<button type="button" id="boton-tabla__insertar" class="btn boton-tabla__añadir float-right" onclick="insertar()">Nuevo producto</button>
							</div>
						</div>
						
		
					</div>

				</div>
				
				
			
			</div>
			
			
		</div>
	</div>
	
	<div class="modal fade" id="modalProducto" tabindex="-1" role="dialog" aria-labelledby="modalProducto" aria-hidden="true">
	  <div class="modal-dialog modal-dialog-scrollable modal-md" role="document">
	    <div class="modal-content">
	    	<form id="insertar-producto" action="/productos.do" method="post">
		    	<div id="variables" style="display: none;">
					<input id="accion" name="accion"></input> 
					<input id="codigo" name="codigo"></input>
				</div>
	      <div class="modal-header justify-content-between">
      		<div class="col">
      			<h5 class="modal-title" id="tituloModal">Producto</h5>
      		</div>
	      </div>
	      <div class="modal-body">
	        <div class="row">
	        	<div class="col px-4">
	        	
	        		<div class="row">
                        <div class="col-6">
                            <p class="modal__label">Cas</p>
                            <input class="modal__input" type="text" id="insertar-cas" name="insertar-cas">
                        </div>
                        <div class="col-6">
                            <p class="modal__label">Nombre</p>
                            <input class="modal__input" type="text" id="insertar-nombre" name="insertar-nombre">
                        </div>
                        
	        		</div>
	        		<div class="row pt-2">
	        			<div class="col-6">
                            <p class="modal__label">Fórmula</p>
                            <input class="modal__input" type="text" id="insertar-formula" name="insertar-formula">
                        </div>
                        <div class="col-6">
                            <p class="modal__label">F. desarrollada</p>
                            <input class="modal__input" type="text" id="insertar-f_des" name="insertar-f_des">
                        </div>
                        
	        		</div>
	        		
	        		<div class="row pt-2">
	        			<div class="col-2">
                            <p class="modal__label">PM</p>
                            <input class="modal__input" type="text" id="insertar-peso" name="insertar-peso">
                        </div>
	        			<div class="col-5">
                            <p class="modal__label">No Einecs</p>
                            <input class="modal__input" type="text" id="insertar-einecs" name="insertar-einecs">
                        </div>
                        <div class="col-5">
                            <p class="modal__label">No Ec</p>
                            <input class="modal__input" type="text" id="insertar-ec" name="insertar-ec">
                        </div>
	        		</div>
	        		
	        		<div class="row pt-2">
	        			<div class="col">
                            <p class="modal__label">Precauciones</p>
                            <input class="modal__input" type="text" id="insertar-precauciones" name="insertar-precauciones">
                        </div>
	        		</div>
	        		
	        		<div class="row pt-2">
	        			<div class="col">
                            <p class="modal__label">MSDS</p>
                            <input class="modal__input" type="text" id="insertar-msds" name="insertar-msds">
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
	<script src="../js/productos.js"></script>
	<script> inicializar(); </script>
	
</body>
</html>