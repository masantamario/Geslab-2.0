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
	ArrayList<Peligro> peligros = (ArrayList<Peligro>) request.getAttribute("peligros");
	ArrayList<Prudencia> prudencias = (ArrayList<Prudencia>) request.getAttribute("prudencias");
	ArrayList<Pictograma> pictogramas = (ArrayList<Pictograma>) request.getAttribute("pictogramas");
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
				  			<a class="dropdown-item header__dropdown-item" href="/index.do" >Existencias</a>
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
									<input class="filtros__input" id="filtro-cas" type="text" onkeyup="filtrado()">
								<p class="filtros__label">Nombre</p>
									<input class="filtros__input" id="filtro-nombre" type="text" onkeyup="filtrado()">
								<p class="filtros__label">Fórmula</p>
									<input class="filtros__input" id="filtro-formula" type="text" onkeyup="filtrado()">
								<p class="filtros__label">No Einecs</p>
									<input class="filtros__input" id="filtro-einecs" type="text" onkeyup="filtrado()">
								<p class="filtros__label">No Ec</p>
									<input class="filtros__input" id="filtro-ec" type="text" onkeyup="filtrado()">
								
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
 												   <td id="precauciones-<%=p.getCas()%>" style="display: none"><%=p.getPrecauciones()%></td>
 												   <td id="msds-<%=p.getCas()%>" style="display: none"><%=p.getMsds()%></td>
 												   
 												   <td style="display: none">
												      	<select id="peligros-<%=p.getCas()%>">
									                    	<%for(Peligro pel: p.getPeligros()){%>
									                    		<option><%=pel.getFrase()%></option>
									                    	<%}%>
			                    						</select>
													</td>
													
													<td style="display: none">
												      	<select id="prudencias-<%=p.getCas()%>">
									                    	<%for(Prudencia pru: p.getPrudencias()){%>
									                    		<option><%=pru.getFrase()%></option>
									                    	<%}%>
			                    						</select>
													</td>
													
													<td style="display: none">
												      	<select id="pictogramas-<%=p.getCas()%>">
									                    	<%for(Pictograma pic: p.getPictogramas()){%>
									                    		<option><%=pic.getReferencia()%></option>
									                    	<%}%>
			                    						</select>
													</td>

											      <td class="tabla-body--row info" style="text-align: right;">
											      	<button type="button" id="" class="boton-tabla__accion" onclick="editar('<%=p.getCas()%>')">
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
										<div class="col"><div class="row" style="height: 100%">
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
		                                        	<div class="col-10 form-inline">
		                                                <p class="extra-info__label d-inline-flex" title="Precauciones">Prec.<p>
		                                                <p class="extra-info__input flex-fill" id="extra-info-precauciones"><p>
		                                            </div>
		                                            <div class="col-2 form-inline">
		                                                <a class="extra-info__label" target="_blank" id="extra-info-msds" title="Hoja de seguridad">MSDS</a>
		                                            </div>
		        
		                                        </div>
		                                        
		                                       <div class="row pt-3" style="height: 50%">
		                                        	<div class="col-3 container-tabla-extra-info">
		                                        		<div class="col table-responsive" data-simplebar data-simplebar-auto-hide="true" style="height: 100%">
															<table id="tabla-peligros" class="table table-borderless table-hover table-sm">
																<thead >
																    <tr class="tabla-header">
																      <th class="tabla-header--item" scope="col">Peligros</th>
																    </tr>
														  		</thead>
																
																 <tbody id="body-peligros" class="tabla-body">
															    </tbody>
															</table>
														</div>
		                                        	</div>
		                                        	
		                                        	<div class="col-3 container-tabla-extra-info">
		                                        		<div class="col table-responsive" data-simplebar data-simplebar-auto-hide="true" style="height: 100%">
															<table id="tabla-prudencias" class="table table-borderless table-hover table-sm">
																<thead >
																    <tr class="tabla-header">
																    	<th class="tabla-header--item" scope="col">Prudencias</th>
																    </tr>
														  		</thead>
																
																 <tbody id="body-prudencias" class="tabla-body">
															    </tbody>
															</table>
														</div>
		                                        	</div>
		                                        	
		                                        	<div class="col-5 px-5" style="height: 100%">
		                                        		<div class="row pt-1 align-items-start" style="height: 50%">
		                                        			<div class="col col__pic"><img id="GHS01" class="img-fluid" alt="GHS01"></div>
		                                        			<div class="col col__pic"><img id="GHS02" class="img-fluid" alt="GHS02"></div>
		                                        			<div class="col col__pic"><img id="GHS03" class="img-fluid" alt="GHS03"></div>
		                                        			<div class="col col__pic"><img id="GHS04" class="img-fluid" alt="GHS04"></div>
		                                        			<div class="col col__pic"><img id="GHS05" class="img-fluid" alt="GHS05"></div>

		                 
		                                        		</div>
		                                        		<div class="row pb-1 px-4 align-items-end" style="height: 50%">
		                                        			<div class="col col__pic"><img id="GHS06" class="img-fluid" alt="GHS06"></div>
		                                        			<div class="col col__pic"><img id="GHS07" class="img-fluid" alt="GHS07"></div>
		                                        			<div class="col col__pic"><img id="GHS08" class="img-fluid" alt="GHS08"></div>
		                                        			<div class="col col__pic"><img id="GHS09" class="img-fluid" alt="GHS09"></div>
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
		</div>
	</div>
	<div class="modal fade" id="modalProducto" tabindex="-1" role="dialog" aria-labelledby="modalProducto" aria-hidden="true">
	  <div class="modal-dialog modal-dialog-scrollable modal-lg" role="document">
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
                        <div class="col-3">
                            <p class="modal__label">Cas</p>
                            <input class="modal__input" type="text" id="insertar-cas" name="insertar-cas">
                        </div>
                        <div class="col-4">
                            <p class="modal__label">Nombre</p>
                            <input class="modal__input" type="text" id="insertar-nombre" name="insertar-nombre">
                        </div>
                        <div class="col-2">
                            <p class="modal__label">Fórmula</p>
                            <input class="modal__input" type="text" id="insertar-formula" name="insertar-formula">
                        </div>
                        <div class="col-3">
                            <p class="modal__label" title="Peso molecular">PM</p>
                            <input class="modal__input" type="text" id="insertar-peso" name="insertar-peso" title="Peso molecular">
                        </div>
                        
	        		</div>
	 	        		
	        		<div class="row pt-2">
	        			<div class="col-6">
                            <p class="modal__label">F. desarrollada</p>
                            <input class="modal__input" type="text" id="insertar-f_des" name="insertar-f_des">
                        </div>
	        			<div class="col-3">
                            <p class="modal__label">No Einecs</p>
                            <input class="modal__input" type="text" id="insertar-einecs" name="insertar-einecs">
                        </div>
                        <div class="col-3">
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
	        		
	        		<div class="row pt-2">
	        			<div class="col-4">
                            <p class="modal__label">Peligros</p>
                            <div class="form-group">
			                    <select class="mul-select modal__select" multiple="multiple" id="insertar-peligros" name="insertar-peligros">
			                    	<%for(Peligro p: peligros){%>
			                    		<option class="modal__select--opcion" title="<%=p.getIndicacion()%>" id=<%=p.getFrase()%>><%=p.getFrase()%></option>
			                    	<%}%>
			                    </select>
			                </div> 
                        </div>
                        
                        <div class="col-4">
                            <p class="modal__label">Prudencias</p>
                            <div class="form-group">
			                    <select class="mul-select modal__select" multiple="multiple" id="insertar-prudencias" name="insertar-prudencias">
			                    	<%for(Prudencia p: prudencias){%>
			                    		<option class="modal__select--opcion" title="<%=p.getConsejo()%>" id=<%=p.getFrase()%>><%=p.getFrase()%></option>
			                    	<%}%>
			                    </select>
			                </div> 
                        </div>
                        
                        <div class="col-4">
                            <p class="modal__label">Pictogramas</p>
                            <div class="form-group">
			                    <select class="mul-select modal__select" multiple="multiple" id="insertar-pictogramas" name="insertar-pictogramas">
			                    	<%for(Pictograma p: pictogramas){%>
			                    		<option class="modal__select--opcion" title="<%=p.getDescripcion()%>" id=<%=p.getReferencia()%>><%=p.getReferencia()%></option>
			                    	<%}%>
			                    </select>
			                </div> 
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
	
	<script> 
		function mostrarPeligorsPrudencias(cas){
			$("#tabla-peligros tbody tr").remove(); 
			$("#tabla-prudencias tbody tr").remove(); 
			bodyPel = document.getElementById("body-peligros");
			bodyPru = document.getElementById("body-prudencias");
			
			<%for(Pictograma pic : pictogramas){%>
				var foto = document.getElementById("<%=pic.getReferencia()%>");
				foto.src = "../images/" + "<%=pic.getReferencia()%>" + ".png";
			<%}%>
			
			
			<% for(Producto p : productos){%>
				if(cas == "<%=p.getCas()%>"){
					<%for(Peligro pel : p.getPeligros()){%>
						fila = document.createElement("tr");
						fila.classList.add("tabla-body--row");
						var peligro = document.createElement("td");
						frasePeligro = document.createTextNode("<%=pel.getFrase()%>");
						peligro.title = "<%=pel.getIndicacion()%>";
						peligro.appendChild(frasePeligro);
						fila.appendChild(peligro);
						bodyPel.appendChild(fila);
					<%}%>
					
					<%for(Prudencia pru : p.getPrudencias()){%>
						fila = document.createElement("tr");
						fila.classList.add("tabla-body--row");
						var prudencia = document.createElement("td");
						frasePrudencia = document.createTextNode("<%=pru.getFrase()%>");
						prudencia.title = "<%=pru.getConsejo()%>";
						prudencia.appendChild(frasePrudencia);
						fila.appendChild(prudencia);
						bodyPru.appendChild(fila);
					<%}%>
					
					<%for(Pictograma pic : p.getPictogramas()){%>
						var foto = document.getElementById("<%=pic.getReferencia()%>");
						foto.src = "../images/" + "<%=pic.getReferencia()%>" + "-active.png";
					<%}%>
				}
			<%}%>
		}
		
		
	</script>
	
</body>
</html>