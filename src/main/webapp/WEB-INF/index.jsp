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
	ArrayList<Area> areas = (ArrayList<Area>) request.getAttribute("areas");
	ArrayList<Departamento> departamentos = (ArrayList<Departamento>) request.getAttribute("departamentos");
	ArrayList<Centro> centros = (ArrayList<Centro>) request.getAttribute("centros");
	ArrayList<Entrada> entradas = (ArrayList<Entrada>) request.getAttribute("entradas");
	ArrayList<Ubicacion> ubicaciones = (ArrayList<Ubicacion>) request.getAttribute("ubicaciones");
	ArrayList<Proveedor> proveedores = (ArrayList<Proveedor>) request.getAttribute("proveedores");
	ArrayList<Marca> marcas = (ArrayList<Marca>) request.getAttribute("marcas");
	ArrayList<Calidad> calidades = (ArrayList<Calidad>) request.getAttribute("calidades");
	ArrayList<Producto> productos = (ArrayList<Producto>) request.getAttribute("productos");
	
	String tabla = (String) request.getAttribute("mostrarTabla");	
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
					  			<input type="hidden" id="opcion-menu" name="accion" value="" />
					  			<button class="dropdown-item header__dropdown-item" type="button">Ubicaciones</button>
					  			<button class="dropdown-item header__dropdown-item--logout" onclick="cerrarSesion()">Cerrar sesión</button>
					  		</form>
					    	
					  	</div>
					</div>
				</div>
			</div>
			
			
			<div class="row pt-3" style="height: 83vh">
			
				<div class="col-3 pl-0 pr-3" id="columna-filtro" style="height: 100%">
					<div class="container filtros py-3" style="height: 100%">
						<div class="row px-2" data-simplebar data-simplebar-auto-hide="false" style="height: 85%">
							<div class="col-12" style="height: 100%">
								<p class="filtros__label">Producto</p>
									<input class="filtros__input" id="filtro-producto" type="text" onkeyup="filtrar('producto', 1)">
								<p class="filtros__label">CAS</p>
									<input class="filtros__input" id="filtro-cas" type="text" onkeyup="filtrar('cas', 7)">
								<p class="filtros__label">Fórmula</p>
									<input class="filtros__input" id="filtro-formula" type="text" onkeyup="filtrar('formula', 8)">
								<p class="filtros__label">Departamento</p>
									<select class="filtros__select" id="filtro-dpto" onchange="filtrar('dpto', 14)">
										<option selected></option>
										<%for(Departamento d:departamentos){ %>
											<option><%=d.getNombre()%></option>
										<%}%>
									</select>
								<p class="filtros__label">Área</p>
									<select class="filtros__select" id="filtro-area" onchange="filtrar('area', 5)">
										<option selected></option>
										<%for(Area a:areas){ %>
											<option><%=a.getNombre()%></option>
										<%}%>
									</select>
								<p class="filtros__label">Centro</p>
									<select class="filtros__select" id="filtro-centro" onchange="filtrar('centro', 15)">
										<option selected></option>
										<%for(Centro c:centros){ %>
											<option><%=c.getNombre()%></option>
										<%}%>
									</select>
								<p class="filtros__label">Ubicación</p>
									<select class="filtros__select" id="filtro-ubicacion" onchange="filtrar('ubicacion', 4)">
										<option selected></option>
										<%for(Ubicacion u:ubicaciones){ %>
											<option><%=u.getNombre()%></option>
										<%}%>
									</select>
								<p class="filtros__label">Proveedor</p>
									<select class="filtros__select" id="filtro-proveedor" onchange="">
										<option selected></option>
										<%for(Proveedor p:proveedores){ %>
											<option><%=p.getNombre()%></option>
										<%}%>
									</select>
								<p class="filtros__label">Marca</p>
									<select class="filtros__select" id="filtro-marca" onchange="filtrar('marca', 6)">
										<option selected></option>
										<%for(Marca m:marcas){ %>
											<option><%=m.getNombre()%></option>
										<%}%>
									</select>
								<p class="filtros__label">Calidad</p>
									<select class="filtros__select" id="filtro-calidad" onchange="filtrar('calidad', 12)">
										<option selected></option>
										<%for(Calidad c:calidades){ %>
											<option><%=c.getNombre()%></option>
										<%}%>
									</select>
								<p class="filtros__label">Ub. Oculta</p>
									<select class="filtros__select" id="filtro-oculto" onchange="filtrar('oculto', 20)">
										<option selected></option>
										<option>Si</option>
										<option>No</option>
									</select>
								<p class="filtros__label">Residuo</p>
									<select class="filtros__select" id="filtro-residuo" onchange="filtrar('residuo', 13)">
										<option selected></option>
										<option>Si</option>
										<option>No</option>
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
						
							<div class="col-4">
								<div class="row justify-content-end align-items-center">
									<p class="d-inline-flex fila-pestañas__fecha--label">Desde<p>
                                	<input type="date" id="filtro-desde" onchange="filtrarFecha()" class="flex-fill form-control fila-pestañas__fecha--input">
								</div>
								<div class="row pt-2 justify-content-end align-items-center">
									<p class="d-inline-flex fila-pestañas__fecha--label">Hasta<p>
                                	<input type="date" id="filtro-hasta" onchange="filtrarFecha()" class="flex-fill form-control fila-pestañas__fecha--input">
								</div>

							</div>
						</div>
					</form>
					
					
					
					<div class="col-12 container-info px-3">
						<div class="row pt-1 align-items-start" id="fila-tabla" style="height: 80%">
							<div class="col table-responsive" data-simplebar data-simplebar-auto-hide="false" style="height: 100%">
								<%if(tabla.equals("entrada")){ %>
									<table id="tabla-entradas" class="table table-borderless table-hover table-sm">
										<thead >
										    <tr class="tabla-header">
										      <th class="tabla-header--item" scope="col">Fecha</th>
										      <th class="tabla-header--item" scope="col">Producto</th>
										      <th class="tabla-header--item" scope="col">Uds.</th>
										      <th class="tabla-header--item" scope="col">Cpcd.</th>
										      <th class="tabla-header--item" scope="col">Ub.</th>
										      <th class="tabla-header--item" scope="col">Area</th>
										      <th class="tabla-header--item" scope="col">Marca</th>
										      <th class="tabla-header--item" scope="col"></th>
										      
										      <th style="display: none" scope="col">Cas</th>
										      <th style="display: none" scope="col">Formula</th>
										      <th style="display: none" scope="col">Peso molecular</th>
										      <th style="display: none" scope="col">N Einecs</th>
										      <th style="display: none" scope="col">N Ec</th>
										      <th style="display: none" scope="col">Calidad</th>
										      <th style="display: none" scope="col">Residuo</th>
										      <th style="display: none" scope="col">Dpto</th>
										      <th style="display: none" scope="col">Centro</th>
										      <th style="display: none" scope="col">Caducidad</th>
										      <th style="display: none" scope="col">Peligro</th>
										      <th style="display: none" scope="col">Prudencia</th>
										      <th style="display: none" scope="col">Fecha</th>
										      <th style="display: none" scope="col">Ub. Oculta</th>
										    </tr>
								  		</thead>
										
										 <tbody class="tabla-body">
										 	<%for (Entrada e : entradas) {%>

										 		<tr data-fila=<%=e.getCodentrada()%>>
											      <td class="tabla-body--row" id="fecha-<%=e.getCodentrada()%>"><%=e.getFecha()%></td>
											      <td class="tabla-body--row" id="producto-<%=e.getCodentrada()%>"><%=e.getFicha().getProducto().getNombre()%></td>
											      <td class="tabla-body--row" id="uds-<%=e.getCodentrada()%>"><%=e.getUnidades()%></td>
											      <td class="tabla-body--row"><%=e.getCapacidad()%> <%=e.getG_ml()%>.</td>
											      <td class="tabla-body--row" id="ubicacion-<%=e.getCodentrada()%>"><%=e.getFicha().getUbicacion().getNombre()%></td>
											      <td class="tabla-body--row" id="area-<%=e.getCodentrada()%>"><%=e.getFicha().getUbicacion().getArea()%></td>
											      <td class="tabla-body--row" id="marca-<%=e.getCodentrada()%>"><%=e.getFicha().getMarca()%></td>

											      <td id="cas-<%=e.getCodentrada()%>" style="display: none"><%=e.getFicha().getProducto().getCas()%></td>											      
											      <td id="formula-<%=e.getCodentrada()%>" style="display: none"><%=e.getFicha().getProducto().getFormula()%></td>											      
											      <td id="peso-<%=e.getCodentrada()%>" style="display: none"><%=e.getFicha().getProducto().getPeso_mol()%></td>											      
											      <td id="einecs-<%=e.getCodentrada()%>" style="display: none"><%=e.getFicha().getProducto().getN_einecs()%></td>											      
											      <td id="ec-<%=e.getCodentrada()%>" style="display: none"><%=e.getFicha().getProducto().getN_ec()%></td>											      
											      <td id="calidad-<%=e.getCodentrada()%>" style="display: none"><%=e.getFicha().getCalidad()%></td>											      
											      <td id="residuo-<%=e.getCodentrada()%>" style="display: none"><%=e.esResiduo()%></td>											      
											      <td id="dpto-<%=e.getCodentrada()%>" style="display: none"><%=e.getFicha().getUbicacion().getDpto()%></td>
											      <td id="centro-<%=e.getCodentrada()%>" style="display: none"><%=e.getFicha().getUbicacion().getCentro()%></td>											      
											      <td id="caducidad-<%=e.getCodentrada()%>" style="display: none"><%=e.getCaducidadCal()%></td>											      
											      <td id="peligro-<%=e.getCodentrada()%>" style="display: none">Peligro</td>											      
											      <td id="prudencia-<%=e.getCodentrada()%>" style="display: none">Prudencia</td>	
											      <td id="fecha-<%=e.getCodentrada()%>" style="display: none"><%=e.getFechaCal()%></td>										      
											      <td id="oculto-<%=e.getCodentrada()%>" style="display: none"><%=e.getFicha().getUbicacion().esOculta()%></td>										      
											      <td id="g-ml-<%=e.getCodentrada()%>" style="display: none"><%=e.getG_ml()%></td>										      
											      <td id="cpcd-<%=e.getCodentrada()%>" style="display: none"><%=e.getCapacidad()%></td>										      
											      <td id="proveedor-<%=e.getCodentrada()%>" style="display: none"><%=e.getFicha().getProveedor()%></td>										      
											      <td id="lote-<%=e.getCodentrada()%>" style="display: none"><%=e.getLote()%></td>										      

											      
											      <td class="tabla-body--row" style="text-align: right;">
											      	<button type="button" id="" class="boton-tabla__accion" onclick="editar(<%=e.getCodentrada()%>)">
											      		<i class="fas fa-pen"></i></button></td>
											      
											    </tr>
										 	<%} %>
  
									    </tbody>
									</table>
								<%} else if(tabla.equals("salida")){ %>
								
								<%} %>
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
                                            <div class="col-3 form-inline">
                                                <p class="extra-info__label d-inline-flex">CAS<p>
                                                <p class="extra-info__input flex-fill" id="extra-info-cas"><p>
                                            </div>
                                            <div class="col-4 form-inline">
                                                <p class="extra-info__label d-inline-flex">Fórmula<p>
                                                <p class="extra-info__input flex-fill" id="extra-info-formula"><p>
                                            </div>
                                            <div class="col-3 form-inline">
                                                <p class="extra-info__label d-inline-flex">PM<p>
                                                <p class="extra-info__input flex-fill" id="extra-info-peso"><p>
                                            </div>
                                            <div class="col-2 form-inline">
                                                <p class="extra-info__label d-inline-flex">Oculto<p>
                                                <p class="extra-info__input flex-fill" id="extra-info-oculto"><p>
                                            </div>
                                        </div>

                                        <div class="row pt-2">
                                            <div class="col-4 form-inline">
                                                <p class="extra-info__label d-inline-flex">Nº EINECS<p>
                                                <p class="extra-info__input flex-fill" id="extra-info-einecs"><p>
                                            </div>
                                            <div class="col-3 form-inline">
                                                <p class="extra-info__label d-inline-flex">Nº EC<p>
                                                <p class="extra-info__input flex-fill" id="extra-info-ec"><p>
                                            </div>
                                            <div class="col-3 form-inline">
                                                <p class="extra-info__label d-inline-flex">Calidad<p>
                                                <p class="extra-info__input flex-fill" id="extra-info-calidad"><p>
                                            </div>
                                            <div class="col-2 form-inline">
                                                <p class="extra-info__label d-inline-flex">Res.<p>
                                                <p class="extra-info__input flex-fill" id="extra-info-residuo"><p>
                                            </div>
                                        </div>

                                        <div class="row pt-2">
                                            <div class="col-4 form-inline">
                                                <p class="extra-info__label d-inline-flex">Dpto.<p>
                                                <p class="extra-info__input flex-fill" id="extra-info-dpto"><p>
                                            </div>
                                            <div class="col-5 form-inline">
                                                <p class="extra-info__label d-inline-flex">Centro<p>
                                                <p class="extra-info__input flex-fill" id="extra-info-centro"><p>
                                            </div>
                                            <div class="col-3 form-inline">
                                                <p class="extra-info__label d-inline-flex">Cad.<p>
                                                <p class="extra-info__input flex-fill" id="extra-info-caducidad"><p>
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
										<button type="button" id="boton-tabla__insertar" class="btn boton-tabla__añadir float-right" onclick="insertar()"></button>
									</div>
								</div>
							</div>
							
						</div>
		
					</div>

				</div>
				
				
			
			</div>
			
			
		</div>
	</div>
	
	<div class="modal fade" id="modalEntrada" tabindex="-1" role="dialog" aria-labelledby="exampleModalScrollableTitle" aria-hidden="true">
	  <div class="modal-dialog modal-dialog-scrollable modal-lg" role="document">
	    <div class="modal-content">
	    	<form id="insertar-entrada" action="/index.do" method="post">
		    	<div id="variables" style="display: none;">
					<input id="accion" name="accion"></input> 
					<input id="elemento" name="elemento"></input>
					<input id="codigo" name="codigo"></input>
				</div>
	      <div class="modal-header justify-content-between">
      		<div class="col-3">
      			<h5 class="modal-title" id="exampleModalScrollableTitle">Entrada</h5>
      		</div>
      		<div class="col-3">
      			<input class="modal__input" type="date" id="insertar-fecha" name="insertar-fecha">
      		</div>
	      </div>
	      <div class="modal-body">
	        <div class="row">
	        	<div class="col px-4">
	        		<div class="row">
	        			<div class="col-6">
                            <p class="modal__label">Producto</p>
                            <select class="modal__input" id="insertar-producto" name="insertar-producto">
                            	<option selected></option>
								<%for(Producto p:productos){ %>
									<option><%=p.getNombre()%></option>
								<%}%>
							</select>
                        </div>
                        <div class="col-2">
                            <p class="modal__label">Uds.</p>
                            <input class="modal__input" type="text" id="insertar-uds" name="insertar-uds">
                        </div>
                        <div class="col-2">
                            <p class="modal__label">Cpcd.</p>
                            <input class="modal__input" type="text" id="insertar-cpcd" name="insertar-cpcd">
                        </div>
                        <div class="col-2">
                            <p class="modal__label">g/ml</p>
                            <select class="modal__input" id="insertar-g-ml" name="insertar-g-ml">
								<option selected></option>
								<option>g</option>
								<option>ml</option>
							</select>
                        </div>
	        		</div>
	        		<div class="row pt-4">
	        			<div class="col-4">
                            <p class="modal__label">Ubicación</p>
                            <select class="modal__input" id="insertar-ubicacion" name="insertar-ubicacion">
                            	<option selected></option>
								<%for(Ubicacion u:ubicaciones){ %>
									<option><%=u.getNombre()%></option>
								<%}%>
							</select>
                        </div>
                        <div class="col-4">
                            <p class="modal__label">Marca</p>
                            <select class="modal__input" id="insertar-marca" name="insertar-marca">
                            	<option selected></option>
								<%for(Marca m:marcas){ %>
									<option><%=m.getNombre()%></option>
								<%}%>
							</select>
                        </div>
                        <div class="col-4">
                            <p class="modal__label">Proovedor</p>
                            <select class="modal__input" id="insertar-proveedor" name="insertar-proveedor">
                            	<option selected></option>
								<%for(Proveedor p:proveedores){ %>
									<option><%=p.getNombre()%></option>
								<%}%>
							</select>
                        </div>
	        		</div>
	        		
	        		<div class="row pt-4">
	        			<div class="col-4">
                            <p class="modal__label">Calidad</p>
                            <select class="modal__input" id="insertar-calidad" name="insertar-calidad">
                            	<option selected></option>
								<%for(Calidad c:calidades){ %>
									<option><%=c.getNombre()%></option>
								<%}%>
							</select>
                        </div>
                        <div class="col-4">
                            <p class="modal__label">Lote</p>
                            <input class="modal__input" type="text" id="insertar-lote" name="insertar-lote">
                        </div>
                        <div class="col-4">
                            <p class="modal__label">Caducidad</p>
                            <input class="modal__input" type="date" id="insertar-caducidad" name="insertar-caducidad">
                        </div>
	        		</div>
	        		
	        		<div class="row pt-4">
	        			<div class="col-4">
                            <input class="" type="checkbox" id="insertar-residuo" name="insertar-residuo"> Residuo
                        </div>
	        		</div>
	        		
	        	</div>
	        </div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn boton-tabla__cancelar" onclick="cancelar()">Cancelar</button>
	        <button type="button" class="btn boton-tabla__añadir" onclick="confirmar()">Añadir</button>
	      </div>
	      </form>
	    </div>
	  </div>
	</div>
	
	
	<script src="https://unpkg.com/simplebar@latest/dist/simplebar.min.js"></script>
	<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" crossorigin="anonymous"></script>
	<script	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
	<script src="../js/bootstrap.min.js"></script>
	<script src="../js/index.js"></script>
	<script> inicializar('<%=tabla%>'); </script>
	
</body>
</html>