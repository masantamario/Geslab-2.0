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
	ArrayList<Area> areas = (ArrayList<Area>) request.getAttribute("areas");
	ArrayList<Departamento> departamentos = (ArrayList<Departamento>) request.getAttribute("departamentos");
	ArrayList<Centro> centros = (ArrayList<Centro>) request.getAttribute("centros");
	ArrayList<Ficha> fichas = (ArrayList<Ficha>) request.getAttribute("fichas");
	ArrayList<Entrada> entradas = (ArrayList<Entrada>) request.getAttribute("entradas");
	ArrayList<Salida> salidas = (ArrayList<Salida>) request.getAttribute("salidas");
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
<!-- 				  			<a class="dropdown-item header__dropdown-item" href="/index.do" >Entradas/Salidas</a> -->
				  			<a class="dropdown-item header__dropdown-item" href="/productos.do" >Productos</a>
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
								<p class="filtros__label">Producto</p>
									<input class="filtros__input" id="filtro-producto" type="text" onkeyup="filtrado()">
								<p class="filtros__label">CAS</p>
									<input class="filtros__input" id="filtro-cas" type="text" onkeyup="filtrado()">
								<p class="filtros__label">Fórmula</p>
									<input class="filtros__input" id="filtro-formula" type="text" onkeyup="filtrado()">
								<p class="filtros__label">Departamento</p>
									<select class="filtros__select" id="filtro-dpto" onchange="filtrado()">
										<option selected></option>
										<%for(Departamento d:departamentos){ %>
											<option><%=d.getNombre()%></option>
										<%}%>
									</select>
								<p class="filtros__label">Área</p>
									<select class="filtros__select" id="filtro-area" onchange="filtrado()">
										<option selected></option>
										<%for(Area a:areas){ %>
											<option><%=a.getNombre()%></option>
										<%}%>
									</select>
								<p class="filtros__label">Centro</p>
									<select class="filtros__select" id="filtro-centro" onchange="filtrado()">
										<option selected></option>
										<%for(Centro c:centros){ %>
											<option><%=c.getNombre()%></option>
										<%}%>
									</select>
								<p class="filtros__label">Ubicación</p>
									<select class="filtros__select" id="filtro-ubicacion" onchange="filtrado()">
										<option selected></option>
										<%for(Ubicacion u:ubicaciones){ %>
											<option><%=u.getNombre()%></option>
										<%}%>
									</select>
								<p class="filtros__label">Marca</p>
									<select class="filtros__select" id="filtro-marca" onchange="filtrado()">
										<option selected></option>
										<%for(Marca m:marcas){ %>
											<option><%=m.getNombre()%></option>
										<%}%>
									</select>
								<p class="filtros__label">Proveedor</p>
									<select class="filtros__select" id="filtro-proveedor" onchange="filtrado()">
										<option selected></option>
										<%for(Proveedor p:proveedores){ %>
											<option><%=p.getNombre()%></option>
										<%}%>
									</select>
								<p class="filtros__label">Calidad</p>
									<select class="filtros__select" id="filtro-calidad" onchange="filtrado()">
										<option selected></option>
										<%for(Calidad c:calidades){ %>
											<option><%=c.getNombre()%></option>
										<%}%>
									</select>
								<p class="filtros__label">Ub. Oculta</p>
									<select class="filtros__select" id="filtro-oculto" onchange="filtrado()">
										<option selected></option>
										<option>Si</option>
										<option>No</option>
									</select>
								<p class="filtros__label">Residuo</p>
									<select class="filtros__select" id="filtro-residuo" onchange="filtrado()">
										<option selected></option>
										<option>Si</option>
										<option>No</option>
									</select>
									
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
					
					<form id="mostrarTabla" action="/index.do" method="get">
						<input type="hidden" id="tabla" name="tabla" value="" />
						
						<div id="fila-pestañas" class="row mx-0 pb-2 align-items-center justify-content-between">
							<div class="col-4">
								<div class="row">
									<div class="col-6 px-0">
										<button id="bt-existencias" class="btn fila-pestañas__pestaña fila-pestanas__pestana--active">Existencias</button>
									</div>
									
								</div>
							</div>
						
							<!-- <div class="col-4">
								<div class="row justify-content-end align-items-center">
									<p class="d-inline-flex fila-pestañas__fecha--label">Desde<p>
                                	<input type="date" id="filtro-desde" onchange="filtrarFecha()" class="flex-fill form-control fila-pestañas__fecha--input">
								</div>
								<div class="row pt-2 justify-content-end align-items-center">
									<p class="d-inline-flex fila-pestañas__fecha--label">Hasta<p>
                                	<input type="date" id="filtro-hasta" onchange="filtrarFecha()" class="flex-fill form-control fila-pestañas__fecha--input">
								</div>

							</div> -->
						</div>
					</form>
					
					
					
					<div class="col-12 container-info px-3">
						<div class="row pt-1 align-items-start" id="fila-tabla" style="height: 80%">
							<div class="col table-responsive" data-simplebar data-simplebar-auto-hide="false" style="height: 100%">
								
									<table id="tabla-existencias" class="table table-borderless table-hover table-sm">
										<thead >
										    <tr class="tabla-header">
										      <th class="tabla-header--item" scope="col">Producto</th>
										      <th class="tabla-header--item" scope="col">Cpcd.</th>
										      <th class="tabla-header--item" scope="col">Uds.</th>
										      <th class="tabla-header--item" scope="col">Calidad</th>
										      <th class="tabla-header--item" scope="col">Ubicacion</th>
										      <th class="tabla-header--item" scope="col">Dpto.</th>
										      <th class="tabla-header--item" scope="col">Marca</th>
										      <th class="tabla-header--item" scope="col"></th>
										      
										      <th style="display: none" scope="col">Caducidad</th>
										      <th style="display: none" scope="col">Lote</th>
										      <th style="display: none" scope="col">Residuo</th>
										      
										      <th style="display: none" scope="col">CAS</th>
										      <th style="display: none" scope="col">Formula</th>
										      <th style="display: none" scope="col">Dpto.</th>
										      
										    </tr>
								  		</thead>
										
										 <tbody class="tabla-body">
											 	<%for (Ficha f : fichas) {%>
											 		<tr data-filtro=true data-fila=<%=f.getCodficha()%>>
												      <td class="tabla-body--row" id="producto-<%=f.getCodficha()%>"><%=f.getProducto().getNombre()%></td>
												      <td class="tabla-body--row" id="cpcd-gml-<%=f.getCodficha()%>"><%=f.getCapacidad()%> <%=f.getG_ml()%>.</td>
												      <td class="tabla-body--row" id="uds-<%=f.getCodficha()%>"><%=f.getStock()%></td>
												      <td class="tabla-body--row" id="calidad-<%=f.getCodficha()%>"><%=f.getCalidad()%></td>
												      <td class="tabla-body--row" id="ubicacion-<%=f.getCodficha()%>"><%=f.getUbicacion().getNombre()%></td>
												      <td class="tabla-body--row" id="dpto-<%=f.getCodficha()%>"><%=f.getUbicacion().getDpto()%></td>
												      <td class="tabla-body--row" id="marca-<%=f.getCodficha()%>"><%=f.getMarca()%></td>

												      <td id="caducidad-<%=f.getCodficha()%>" style="display: none"><%=f.getCaducidadCal()%></td>											      
												      <td id="lote-<%=f.getCodficha()%>" style="display: none"><%=f.getLote()%></td>										      
												      <td id="residuo-<%=f.getCodficha()%>" style="display: none"><%=f.esResiduo()%></td>
												      
												      <td id="cas-<%=f.getCodficha()%>" style="display: none"><%=f.getProducto().getCas()%></td>	
												      <td id="formula-<%=f.getCodficha()%>" style="display: none"><%=f.getProducto().getFormula()%></td>											      
												      <td id="proveedor-<%=f.getCodficha()%>" style="display: none"><%=f.getProveedor()%></td>											      
												      <td id="area-<%=f.getCodficha()%>" style="display: none"><%=f.getUbicacion().getArea()%></td>											      
												      <td id="centro-<%=f.getCodficha()%>" style="display: none"><%=f.getUbicacion().getCentro()%></td>											      
												      <td id="oculto-<%=f.getCodficha()%>" style="display: none"><%=f.getUbicacion().esOculta()%></td>											      
	
												      <td class="tabla-body--row info" style="text-align: right;">
												      <%if(usuario.getArea().equals(f.getUbicacion().getArea())){%>
												      	<button type="button" id="boton-entrada" class="boton-tabla__accion boton-tabla__accion--add" onclick="entSal(<%=f.getCodficha()%>, 'entrada')">
												      		<i class="fas fa-plus"></i></button>
												      	<button type="button" id="boton-salida" class="boton-tabla__accion" onclick="entSal(<%=f.getCodficha()%>, 'salida')">
												      		<i class="fas fa-minus"></i></i></button>
												      <%}%>
												      </td>
												    </tr>
											 	<%} %>
											 	
									    </tbody>
									</table>
							</div>
						</div>
						<div class="row align-items-end" id="fila-info" style="height: 20%">
							<div class="col align-self-end">
								
								<div class="row extra-info mx-1" id="container-info" style="height:236px; display:none">
									<div class="col">
										<div class="row" style="height: 100%">
											<div class="col-12 px-4">
		                                        <div class="row justify-content-end pt-1">
		                                            <div class="col-1 text-right" onclick='ocultarExtraInfo()'><i class="fas fa-times extra-info__boton"></i></div>
		                                        </div>
		
		                                        <div class="row pt-2">
		                                            <div class="col-3 form-inline">
		                                                <p class="extra-info__label d-inline-flex" title="Fecha caducidad">Cad.<p>
		                                                <p class="extra-info__input flex-fill" id="extra-info-caducidad" title="Fecha caducidad"><p>
		                                            </div>
		                                            <div class="col-3 form-inline">
		                                                <p class="extra-info__label d-inline-flex">Lote<p>
		                                                <p class="extra-info__input flex-fill" id="extra-info-lote"><p>
		                                            </div>
		                                            <div class="col-2 form-inline">
		                                                <p class="extra-info__label d-inline-flex" title="Residuo">Res.<p>
		                                                <p class="extra-info__input flex-fill" id="extra-info-residuo" title="Residuo"><p>
		                                            </div>
		                                            <div class="col-4 form-inline">
		                                                <p class="extra-info__label d-inline-flex" title="Proveedor">Prov.<p>
		                                                <p class="extra-info__input flex-fill" id="extra-info-proveedor" title="Proveedor"><p>
		                                            </div>
		                                            
		                                        </div>
		                                        
		                                     	                                        
		                                        
		                                         <div class="row pt-3 justify-content-between" style="height: 50%">
		                                        	<div class="col-5 container-tabla-extra-info">
		                                        		<div class="col table-responsive" data-simplebar data-simplebar-auto-hide="true" style="height: 100%">
															<table id="tabla-entradas" class="table table-borderless table-hover table-sm">
																<thead >
																    <tr class="tabla-header">
																      <th class="tabla-header--item" scope="col">Entrada</th>
																      <th class="tabla-header--item" scope="col">Fecha</th>
																    </tr>
														  		</thead>
																
																 <tbody id="body-entradas" class="tabla-body">
															    </tbody>
															</table>
														</div>
		                                        	</div>
		                                        	
		                                        	<div class="col-5 container-tabla-extra-info">
		                                        		<div class="col table-responsive" data-simplebar data-simplebar-auto-hide="true" style="height: 100%">
															<table id="tabla-salidas" class="table table-borderless table-hover table-sm">
																<thead >
																    <tr class="tabla-header">
																    	<th class="tabla-header--item" scope="col">Salida</th>
																    	<th class="tabla-header--item" scope="col">Fecha</th>
																    </tr>
														  		</thead>
																
																 <tbody id="body-salidas" class="tabla-body">
															    </tbody>
															</table>
														</div>
		                                        	</div>
		                                        
		                                        </div>
		                                        
		                                    </div>
	                                    </div>
                                    </div>
								
								</div>
								
								
								<div class="row py-3" style="height: 20%" id="fila-insertar">
									<div class="col">
										<button type="button" id="boton-tabla__insertar" class="btn boton-tabla__añadir float-right" onclick="insertar()">Nueva ficha</button>
									</div>
								</div>
							</div>
							
						</div>
		
					</div>

				</div>
				
				
			
			</div>
			
			
		</div>
	</div>
	
	<div class="modal fade" id="modalFicha" tabindex="-1" role="dialog" aria-labelledby="modalFicha" aria-hidden="true">
	  <div class="modal-dialog modal-dialog-scrollable modal-lg" role="document">
	    <div class="modal-content">
	    	<form id="insertar-ficha" action="/index.do" method="post">
		    	<div id="variables" style="display: none;">
					<input id="accion-ficha" name="accion"></input> 
					<input id="codigo-ficha" name="codigo"></input>
				</div>
	      <div class="modal-header justify-content-between">
      		<div class="col-6">
      			<h5 class="modal-title">Nueva ficha</h5>
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
          
                        <div class="col-4">
                            <p class="modal__label">Capacidad</p>
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
									<%if(usuario.getArea().equals(u.getArea())){%>
										<option><%=u.getNombre()%></option>
									<%}%>
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
	
	<div class="modal fade" id="modalEntSal" tabindex="-1" role="dialog" aria-labelledby="modalEntSal" aria-hidden="true">
	  <div class="modal-dialog modal-dialog-scrollable modal-sm" role="document">
	    <div class="modal-content">
	    	<form id="insertar-ent-sal" action="/index.do" method="post">
		    	<div id="variables" style="display: none;">
					<input id="accion-EntSal" name="accion"></input> 
					<input id="codigo-fichaEntSal" name="codigo"></input>
				</div>
	      <div class="modal-header justify-content-between">
      		<div class="col-12">
      			<h5 class="modal-title" id="tituloModalEntSal"></h5>
      		</div>
	      </div>
	      <div class="modal-body">
	        <div class="row">
	        	<div class="col px-4">
	        	
	        		<div class="row align-items-center pb-2">
	        			<div class="col-5 pt-2">
                            <p class="modal__label">Unidades</p>
                        </div>  
                        <div class="col-7 qty">
		                        <span class="minus boton-tabla__accion boton-tabla__accion--add">-</span>
		                        <input type="number" class="count" id="insertar-unidades" name="insertar-unidades" value="1">
		                        <span class="plus boton-tabla__accion boton-tabla__accion--add">+</span>
		                    </div>
                        <div class="col-12 pt-3">
                            <p class="modal__label">Fecha</p>
                            <input class="modal__input" type="date" id="insertar-fecha" name="insertar-fecha">
                        </div> 
                        
	        		</div>
	        		
	        	</div>
	        </div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn boton-tabla__cancelar" onclick="cancelarEntSal()">Cancelar</button>
	        <button type="button" class="btn boton-tabla__añadir" onclick="confirmarEntSal()">Añadir</button>
	      </div>
	      </form>
	    </div>
	  </div>
	</div>
	
	
	<script src="https://unpkg.com/simplebar@latest/dist/simplebar.min.js"></script>
	<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" crossorigin="anonymous"></script>
	<script	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
	<script src="../js/bootstrap/bootstrap.min.js"></script>
	<script src="../js/index.js"></script>
	<script> inicializar(); </script>
	
	<script> 
		function mostrarEntradas(codficha){
			$("#tabla-entradas tbody tr").remove(); 
			body = document.getElementById("body-entradas");
			
			<% for(Entrada e : entradas){%>
				if(codficha == "<%=e.getFicha().getCodficha()%>"){
					fila = document.createElement("tr");
					var fecha = document.createElement("td");
					var uds = document.createElement("td");
					textoFecha = document.createTextNode("<%=e.getFecha()%>");
					textoUds = document.createTextNode("<%=e.getUnidades()%> uds.");
					fecha.appendChild(textoFecha);
					uds.appendChild(textoUds);
					fila.appendChild(uds);
					fila.appendChild(fecha);
					body.appendChild(fila);
				}
			<%}%>
		}
		
		function mostrarSalidas(codficha){
			$("#tabla-salidas tbody tr").remove(); 
			body = document.getElementById("body-salidas");
			
			<% for(Salida s : salidas){%>
				if(codficha == "<%=s.getFicha().getCodficha()%>"){
					fila = document.createElement("tr");
					var fecha = document.createElement("td");
					var uds = document.createElement("td");
					textoFecha = document.createTextNode("<%=s.getFecha()%>");
					textoUds = document.createTextNode("<%=s.getUnidades()%> uds.");
					fecha.appendChild(textoFecha);
					uds.appendChild(textoUds);
					fila.appendChild(uds);
					fila.appendChild(fecha);
					body.appendChild(fila);
				}
			<%}%>
		}
	</script>
	
</body>
</html>