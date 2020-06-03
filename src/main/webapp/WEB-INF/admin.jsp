<%-- <%@ page language="java" contentType="text/html;" pageEncoding="UTF-8"%> --%>
<%@page language="java" contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>

<html lang="en">
<head>
<!-- <meta charset="UTF-8"> -->
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<link rel="stylesheet" href="../css/bootstrap/bootstrap.min.css">

<!-- Custom CSS -->
<link rel="stylesheet" href="../css/style.css">
<script src="https://kit.fontawesome.com/e907f1c9ed.js" crossorigin="anonymous"></script>

<title>Geslab 2.0</title>
<link rel="shortcut icon" type="image/png" href="../images/favicon.png"/>

</head>



<body>
	<%@page import="java.util.ArrayList"%>
	<%@page import="geslab.database.admin.*"%>
	
	<%
	Usuario usuario = (Usuario) request.getAttribute("usuario");
	ArrayList<Usuario> usuarios = (ArrayList<Usuario>) request.getAttribute("usuarios");
	ArrayList<Area> areas = (ArrayList<Area>) request.getAttribute("areas");
	ArrayList<Departamento> departamentos = (ArrayList<Departamento>) request.getAttribute("departamentos");
	ArrayList<Centro> centros = (ArrayList<Centro>) request.getAttribute("centros");
	String tabla = (String) request.getAttribute("mostrarTabla");
	%>
	
	

	<div class="container-fluid">
		<div class="container">
			<div class="row py-5 justify-content-between align-items-center">
				<div class="col-3">
					<img class="header__logo" src="../images/logo-geslab.svg">
				</div>

				<div class="col-3 pr-0 text-right header__menu">
						<div class="dropdown show">
						<a class="dropdown-toggle header__dropdown" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					    <%=usuario.getNombre()%></a>
					
					  	<div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
				  			<a class="dropdown-item header__dropdown-item--logout" href="/login.do?accion=logout" >Cerrar sesion</a>
					  	</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-3">

					<form id="mostrarTabla" action="/admin.do" method="get">
						<input type="hidden" id="tabla" name="tabla" value="" />
						<div class="row">
							<div class="col-12">
								<button id="bt-centros" value="centros" onclick="mostrarElemento('centro')"
									class="btn filtro_admin__boton">Centros</button>
							</div>
						</div>

						<div class="row pt-2">
							<div class="col-12">
								<button id="bt-departamentos" value="departamentos"
									onclick="mostrarElemento('departamento')"
									class="btn filtro_admin__boton">Departamentos</button>
							</div>
						</div>

						<div class="row pt-2">
							<div class="col-12">
								<button id="bt-areas" value="areas" onclick="mostrarElemento('area')"
									class="btn filtro_admin__boton">Áreas</button>
							</div>
						</div>

						<div class="row pt-2">
							<div class="col-12">
								<button id="bt-usuarios" value="usuarios" onclick="mostrarElemento('usuario')"
									class="btn filtro_admin__boton">Usuarios</button>
							</div>
						</div>
					</form>

				</div>



				<div class="col-9 container-info">


					<div class="row py-3" id="container-tabla" style="height: 100%">
						<div class="container px-5">
							<form id="form-confirmar" name="form-confirmar"
								action="/admin.do" method="post" style="height: 100%">

								<div id="variables" style="display: none;">
									<input id="accion" name="accion"></input> <input id="elemento"
										name="elemento"></input> <input id="codigo" name="codigo"></input>
								</div>

								<div class="row" style="height: 85%">
								
									<%if(tabla.equals("centro")){ %>
									<table id="tabla-centros"
										class="table table-borderless table-hover table-sm">
										<thead class="tabla-header">
											<tr>
												<th class="tabla-header--item" scope="col">Centro</th>
												<th class="tabla-header--item" style="text-align: right" scope="col"></th>
											</tr>
											
										</thead>
										
										<tbody class="tabla-body">
											<%for (Centro c : centros) {%>
											<tr class="centro-<%=c.getCodcentro()%>">
												<td class="tabla-body--row"><input type="text" class="label-tabla__text"
													name="centro-<%=c.getCodcentro()%>"
													value="<%=c.getNombre()%>" placeholder="<%=c.getNombre()%>"
													disabled></td>

												<td class="tabla-body--row" style="text-align: right"
													id="editar-centro-<%=c.getCodcentro()%>">
													<button type="button"
														onclick="editar(<%=c.getCodcentro()%>)"
														id="btn-editar-centro-<%=c.getCodcentro()%>"
														class="boton-tabla__accion"><i class="fas fa-pen"></i></button>
												</td>
												<td class="tabla-body--row" style="text-align: right; display: none"
													id="conf-canc-editar-centro-<%=c.getCodcentro()%>">
													<button type="submit"
														id="btn-confirmar-centro-<%=c.getCodcentro()%>"
														class="boton-tabla__accion boton-tabla__accion--success"><i class="fas fa-check"></i></button>
													<button type="button"
														onclick="cancelarEditar(<%=c.getCodcentro()%>)"
														id="btn-cancelar-centro-<%=c.getCodcentro()%>"
														class="boton-tabla__accion boton-tabla__accion"><i class="fas fa-times"></i></button>
													
												</td>
											</tr>
											<%}%>
											<tr class="form-nuevo-elemento" id="form-nuevo-centro"
												style="display: none">
												<td><input type="text" onchange="comprobarCampos()"
													class="label-tabla__input" name="centro-nuevo"
													placeholder="Centro"></td>
											</tr>
										</tbody>
									</table>

									<%} else if(tabla.equals("departamento")){ %>

									<table id="tabla-departamentos"
										class="table table-borderless table-hover table-sm">
										<thead class="tabla-header">
											<tr>
												<th class="tabla-header--item" scope="col">Departamento</th>
												<th class="tabla-header--item" style="text-align: right" scope="col"></th>
											</tr>
										</thead>
										<tbody class="tabla-body">
											<%for (Departamento d : departamentos) {%>
											<tr class="departamento-<%=d.getCoddpto()%>">
												<td class="tabla-body--row"><input type="text" class="label-tabla__text"
													name="departamento-<%=d.getCoddpto()%>"
													value="<%=d.getNombre()%>" placeholder="<%=d.getNombre()%>"
													disabled></td>

												<td class="tabla-body--row" style="text-align: right"
													id="editar-departamento-<%=d.getCoddpto()%>">
													<button type="button" onclick="editar(<%=d.getCoddpto()%>)"
														id="btn-editar-departamento-<%=d.getCoddpto()%>"
														class="boton-tabla__accion"><i class="fas fa-pen"></i></button>
												</td>
												<td class="tabla-body--row" style="text-align: right; display: none"
													id="conf-canc-editar-departamento-<%=d.getCoddpto()%>">
													<button type="submit"
														id="btn-confirmar-departamento-<%=d.getCoddpto()%>"
														class="boton-tabla__accion boton-tabla__accion--success"><i class="fas fa-check"></i></button>
													<button type="button"
														onclick="cancelarEditar(<%=d.getCoddpto()%>)"
														id="btn-cancelar-departamento-<%=d.getCoddpto()%>"
														class="boton-tabla__accion"><i class="fas fa-times"></i></button>
												</td>
											</tr>
											<%} %>

											<tr class="form-nuevo-elemento" id="form-nuevo-departamento"
												style="display: none">
												<td><input type="text" onchange="comprobarCampos()"
													class="label-tabla__input" name="departamento-nuevo"
													placeholder="Departamento"></td>
											</tr>

										</tbody>
									</table>

									<%} else if(tabla.equals("area")){ %>

									<table id="tabla-areas"
										class="table table-borderless table-hover table-sm">
										<thead class="tabla-header">
											<tr>
												<th class="tabla-header--item" scope="col">Área</th>
												<th class="tabla-header--item" scope="col">Departamento</th>
												<th class="tabla-header--item" style="text-align: right" scope="col"></th>
											</tr>
										</thead>
										<tbody class="tabla-body">

											<%for (Area a : areas) {%>
											<tr class="area-<%=a.getCodarea()%>">
												<td class="tabla-body--row"><input type="text" class="label-tabla__text"
													name="area-<%=a.getCodarea()%>" value="<%=a.getNombre()%>"
													placeholder="<%=a.getNombre()%>" disabled></td>
												<td class="tabla-body--row">
													<select class="label-tabla__select--text"
														name="dpto-area-<%=a.getCodarea()%>" disabled>
															<%for (Departamento d : departamentos) {%>
															<option value="<%=d.getNombre()%>"
																<%if (d.getNombre().equals(a.getDpto())) {%> selected
																<%}%>><%=d.getNombre()%></option>
															<%}%>
													</select>
												</td>

												<td class="tabla-body--row" style="text-align: right"
													id="editar-area-<%=a.getCodarea()%>">
													<button type="button" onclick="editar(<%=a.getCodarea()%>)"
														id="btn-editar-area-<%=a.getCodarea()%>"
														class="boton-tabla__accion"><i class="fas fa-pen"></i></button>
												</td>
												<td class="tabla-body--row" style="text-align: right; display: none"
													id="conf-canc-editar-area-<%=a.getCodarea()%>">
													<button type="submit"
														id="btn-confirmar-area-<%=a.getCodarea()%>"
														class="boton-tabla__accion boton-tabla__accion--success"><i class="fas fa-check"></i></button>
													<button type="button"
														onclick="cancelarEditar(<%=a.getCodarea()%>)"
														id="btn-cancelar-area-<%=a.getCodarea()%>"
														class="boton-tabla__accion"><i class="fas fa-times"></i></button>
												</td>
											</tr>
											<%}%>

											<tr class="form-nuevo-elemento" id="form-nuevo-area"
												style="display: none">
												<td><input type="text" onchange="comprobarCampos()"
													class="label-tabla__input" name="area-nuevo"
													placeholder="Nombre"></td>
												<td><select onchange="comprobarCampos()"
													class="label-tabla__input" name="dpto-area-nuevo">
														<%for (Departamento d : departamentos) {%>
														<option value="<%=d.getNombre()%>"><%=d.getNombre()%>
														</option>
														<%} %>
												</select></td>

											</tr>

										</tbody>
									</table>

									<%} else if(tabla.equals("usuario")){ %>

									<table id="tabla-usuarios"
										class="table table-borderless table-hover table-sm">
										<thead class="tabla-header">
											<tr>
												<th class="tabla-header--item" scope="col">Usuario</th>
												<th class="tabla-header--item" scope="col">Rol</th>
												<th class="tabla-header--item" scope="col">Área</th>
												<th class="tabla-header--item" scope="col" style="text-align: center">Fed.</th>
												<th class="tabla-header--item" scope="col" style="text-align: center">Act.</th>
												<th class="tabla-header--item" style="text-align: right" scope="col"></th>
											</tr>
										</thead>
										<tbody class="tabla-body">
											<%for (Usuario u : usuarios) {%>
											<tr class="usuario-<%=u.getIdusuario()%>">
												<td class="tabla-body--row"><input type="text" class="label-tabla__text"
													name="usuario-<%=u.getIdusuario()%>"
													value="<%=u.getUsuario()%>"
													placeholder="<%=u.getUsuario()%>" disabled></td>
												<td class="tabla-body--row"><select class="label-tabla__select--text"
													name="rol-usuario-<%=u.getIdusuario()%>" disabled>
														<%for (Rol r : Rol.values()) {%>
														<option value="<%=r.getId()%>"
															<%if (r.getRol().equals(u.getRol().getRol())) {%>
															selected <%}%>><%=r.getRol()%></option>
														<%}%>
												</select></td>
												<td class="tabla-body--row"><select class="label-tabla__select--text"
													name="area-usuario-<%=u.getIdusuario()%>" disabled>
														<%for (Area a : areas) {%>
														<option value="<%=a.getNombre()%>"
															<%if (a.getNombre().equals(u.getArea())) {%>
															selected <%}%>><%=a.getNombre()%></option>
														<%}%>
												</select></td>
												<td class="tabla-body--row" style="text-align: center"><input type="checkbox"
													name="federada-usuario-<%=u.getIdusuario()%>"
													onclick="javascript: return false;"
													<%if (u.getFederada()) {%> checked <%}%>></td>
												<td class="tabla-body--row" style="text-align: center"><input type="checkbox"
													name="activo-usuario-<%=u.getIdusuario()%>"
													onclick="javascript: return false;"
													<%if (u.getActivo()) {%> checked <%}%>></td>

												<td style="text-align: right"
													id="editar-usuario-<%=u.getIdusuario()%>">
													<button type="button"
														onclick="editar(<%=u.getIdusuario()%>)"
														id="btn-editar-usuario-<%=u.getIdusuario()%>"
														class="boton-tabla__accion"><i class="fas fa-pen"></i></button>
												</td>
												<td class="tabla-body--row" style="text-align: right; display: none"
													id="conf-canc-editar-usuario-<%=u.getIdusuario()%>">
													
													<button type="submit"
														id="btn-confirmar-area-<%=u.getIdusuario()%>"
														class="boton-tabla__accion boton-tabla__accion--success"><i class="fas fa-check"></i></button>
													<button type="button"
														onclick="cancelarEditar(<%=u.getIdusuario()%>)"
														id="btn-cancelar-usuario-<%=u.getIdusuario()%>"
														class="boton-tabla__accion"><i class="fas fa-times"></i></button>
												</td>

											</tr>
											<%}%>
											<tr class="form-nuevo-elemento " id="form-nuevo-usuario"
												style="display: none">
												<td><input type="text" onchange="comprobarCampos()"
													class="label-tabla__input" name="usuario-nuevo"
													placeholder="Usuario"></td>
												<td><select onchange="comprobarCampos()"
													class="label-tabla__select" name="rol-usuario-nuevo" id="nuevo-rol">
														<%for(Rol r : Rol.values()){ %>
														<option value="<%=r.getId()%>"><%=r.getRol()%></option>
														<%} %>
												</select></td>
												<td><select onchange="comprobarCampos()"
													class="label-tabla__select" name="area-usuario-nuevo" id="nuevo-area">
														<%for(Area a : areas){ %>
														<option value="<%=a.getNombre()%>"><%=a.getNombre()%></option>
														<%} %>
												</select></td>
												<td style="text-align: center"><input type="checkbox" class="check-elemento"
													name="federada-usuario-nuevo" value="true"></td>
												<td style="text-align: center"><input type="checkbox" class="check-elemento"
													name="activo-usuario-nuevo" value="true"></td>
													<td></td>
											</tr>
										</tbody>
									</table>
								<%} %>
								
								</div>

								<div class="row justify-content-end" id="fila-insertar">
									<button type="button" onclick="insertar()" id="btn-insertar"
										class="btn boton-tabla__añadir"></button>
								</div>

								<div class="row justify-content-end" id="fila-confirmar"
									style="display: none">
									<button type="button" onclick="cancelar()" id="btn-cancelar"
										class="btn boton-tabla__cancelar mr-3">Cancelar</button>
									<button type="submit" id="btn-confirmar" name="btn-confirmar"
										class="btn boton-tabla__añadir">Confirmar</button>
								</div>
							</form>
						</div>
					</div>

				</div>
			</div>

		</div>
	</div>

	<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"></script>
	<script	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
	<script src="../js/bootstrap/bootstrap.min.js"></script>
	<script src="../js/admin.js"></script>
	<script> inicializar('<%=tabla%>'); </script>

</body>

</html>
