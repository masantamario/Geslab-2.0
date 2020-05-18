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
<!--		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">-->
<link rel="stylesheet" href="../css/bootstrap.min.css">

<!-- Custom CSS -->
<link rel="stylesheet" href="../css/style.css">

<title>Geslab 2.0</title>
</head>



<body>
	<%@page import="java.util.ArrayList"%>
	<%@page import="java.util.Iterator"%>
	<%@page import="geslab.database.modelo.*"%>
	
	<%
		ArrayList<Usuario> usuarios = (ArrayList<Usuario>) request.getAttribute("usuarios");
		ArrayList<Area> areas = (ArrayList<Area>) request.getAttribute("areas");
		ArrayList<Departamento> departamentos = (ArrayList<Departamento>) request.getAttribute("departamentos");
		ArrayList<Centro> centros = (ArrayList<Centro>) request.getAttribute("centros");
		String tabla = (String) request.getAttribute("mostrarTabla");
	%>

	<div class="container-fluid">
		<div class="container">
			<div class="row py-5 align-items-center">
				<div class="col-3">
					<img class="logo-img" src="../images/logo-geslab.svg">
				</div>

				<div class="col-6">
					<p class="col-12 descripcion-txt">Bienvenido</p>
				</div>
			</div>

			<div class="row">
				<div class="col-3">

					<form id="mostrarTabla" action="/admin.do" method="get">
						<input type="hidden" id="tabla" name="tabla" value="" />
						<div class="row">
							<div class="col-12">
								<button value="centros" onclick="mostrarElemento('centro')"
									class="btn boton-grande py-2 px-5">Centros</button>
							</div>
						</div>

						<div class="row pt-2">
							<div class="col-12">
								<button value="departamentos"
									onclick="mostrarElemento('departamento')"
									class="btn boton-grande py-2 px-5">Departamentos</button>
							</div>
						</div>

						<div class="row pt-2">
							<div class="col-12">
								<button value="areas" onclick="mostrarElemento('area')"
									class="btn boton-grande py-2 px-5">Áreas</button>
							</div>
						</div>

						<div class="row pt-2">
							<div class="col-12">
								<button value="usuarios" onclick="mostrarElemento('usuario')"
									class="btn boton-grande py-2 px-5">Usuarios</button>
							</div>
						</div>
					</form>

				</div>



				<div class="col-9 container-admin">


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
										class="table table-borderless table-hover ">
										<thead>
											<tr>
												<th scope="col">Centro</th>
												<th style="text-align: right" scope="col">Acciones</th>
											</tr>
										</thead>
										<tbody>
											<%for (Centro c : centros) {%>
											<tr class="centro-<%=c.getCodcentro()%>">
												<td><input type="text" class="input-info"
													name="centro-<%=c.getCodcentro()%>"
													value="<%=c.getNombre()%>" placeholder="<%=c.getNombre()%>"
													disabled></td>

												<td style="text-align: right"
													id="editar-centro-<%=c.getCodcentro()%>">
													<button type="button"
														onclick="editar(<%=c.getCodcentro()%>)"
														id="btn-editar-centro-<%=c.getCodcentro()%>"
														class="btn boton-accion">Editar</button>
												</td>
												<td style="text-align: right; display: none"
													id="conf-canc-editar-centro-<%=c.getCodcentro()%>">
													<button type="button"
														onclick="cancelarEditar(<%=c.getCodcentro()%>)"
														id="btn-cancelar-centro-<%=c.getCodcentro()%>"
														class="btn boton-accion">Cancelar</button>
													<button type="submit"
														id="btn-confirmar-centro-<%=c.getCodcentro()%>"
														class="btn boton-accion">Confirmar</button>
												</td>
											</tr>
											<%}%>
											<tr class="form-nuevo-elemento" id="form-nuevo-centro"
												style="display: none">
												<td><input type="text" onchange="comprobarCampos()"
													class="input-elemento" name="centro-nuevo"
													placeholder="Centro"></td>
											</tr>
										</tbody>
									</table>

									<%} else if(tabla.equals("departamento")){ %>

									<table id="tabla-departamentos"
										class="table table-borderless table-hover ">
										<thead>
											<tr>
												<th scope="col">Departamento</th>
												<th style="text-align: right" scope="col">Acciones</th>
											</tr>
										</thead>
										<tbody>
											<%for (Departamento d : departamentos) {%>
											<tr class="departamento-<%=d.getCoddpto()%>">
												<td><input type="text" class="input-info"
													name="departamento-<%=d.getCoddpto()%>"
													value="<%=d.getNombre()%>" placeholder="<%=d.getNombre()%>"
													disabled></td>

												<td style="text-align: right"
													id="editar-departamento-<%=d.getCoddpto()%>">
													<button type="button" onclick="editar(<%=d.getCoddpto()%>)"
														id="btn-editar-departamento-<%=d.getCoddpto()%>"
														class="btn boton-accion">Editar</button>
												</td>
												<td style="text-align: right; display: none"
													id="conf-canc-editar-departamento-<%=d.getCoddpto()%>">
													<button type="button"
														onclick="cancelarEditar(<%=d.getCoddpto()%>)"
														id="btn-cancelar-departamento-<%=d.getCoddpto()%>"
														class="btn boton-accion">Cancelar</button>
													<button type="submit"
														id="btn-confirmar-departamento-<%=d.getCoddpto()%>"
														class="btn boton-accion">Confirmar</button>
												</td>
											</tr>
											<%} %>

											<tr class="form-nuevo-elemento" id="form-nuevo-departamento"
												style="display: none">
												<td><input type="text" onchange="comprobarCampos()"
													class="input-elemento" name="departamento-nuevo"
													placeholder="Departamento"></td>
											</tr>

										</tbody>
									</table>

									<%} else if(tabla.equals("area")){ %>

									<table id="tabla-areas"
										class="table table-borderless table-hover ">
										<thead>
											<tr>
												<th scope="col">Área</th>
												<th scope="col">Departamento</th>
												<th style="text-align: right" scope="col">Acciones</th>
											</tr>
										</thead>
										<tbody>

											<%for (Area a : areas) {%>
											<tr class="area-<%=a.getCodarea()%>">
												<td><input type="text" class="input-info"
													name="area-<%=a.getCodarea()%>" value="<%=a.getNombre()%>"
													placeholder="<%=a.getNombre()%>" disabled></td>
												<td>
													<select class="select-info"
														name="dpto-area-<%=a.getCodarea()%>" disabled>
															<%for (Departamento d : departamentos) {%>
															<option value="<%=d.getNombre()%>"
																<%if (d.getNombre().equals(a.getDpto())) {%> selected
																<%}%>><%=d.getNombre()%></option>
															<%}%>
													</select>
												</td>

												<td style="text-align: right"
													id="editar-area-<%=a.getCodarea()%>">
													<button type="button" onclick="editar(<%=a.getCodarea()%>)"
														id="btn-editar-area-<%=a.getCodarea()%>"
														class="btn boton-accion">Editar</button>
												</td>
												<td style="text-align: right; display: none"
													id="conf-canc-editar-area-<%=a.getCodarea()%>">
													<button type="button"
														onclick="cancelarEditar(<%=a.getCodarea()%>)"
														id="btn-cancelar-area-<%=a.getCodarea()%>"
														class="btn boton-accion">Cancelar</button>
													<button type="submit"
														id="btn-confirmar-area-<%=a.getCodarea()%>"
														class="btn boton-accion">Confirmar</button>
												</td>
											</tr>
											<%}%>

											<tr class="form-nuevo-elemento" id="form-nuevo-area"
												style="display: none">
												<td><input type="text" onchange="comprobarCampos()"
													class="input-elemento" name="area-nuevo"
													placeholder="Nombre"></td>
												<td><select onchange="comprobarCampos()"
													class="input-elemento" name="dpto-area-nuevo">
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
										class="table table-borderless table-hover ">
										<thead>
											<tr>
												<th scope="col">Usuario</th>
												<th scope="col">Rol</th>
												<th scope="col">Área</th>
												<th scope="col">Federada</th>
												<th scope="col">Activo</th>
												<th style="text-align: right" scope="col">Acciones</th>
											</tr>
										</thead>
										<tbody>
											<%for (Usuario u : usuarios) {%>
											<tr class="usuario-<%=u.getIdusuario()%>">
												<td><input type="text" class="input-info"
													name="usuario-<%=u.getIdusuario()%>"
													value="<%=u.getUsuario()%>"
													placeholder="<%=u.getUsuario()%>" disabled></td>
												<td><select class="select-info"
													name="rol-usuario-<%=u.getIdusuario()%>" disabled>
														<%for (Rol r : Rol.values()) {%>
														<option value="<%=r.getId()%>"
															<%if (r.getRol().equals(u.getRol().getRol())) {%>
															selected <%}%>><%=r.getRol()%></option>
														<%}%>
												</select></td>
												<td><select class="select-info"
													name="area-usuario-<%=u.getIdusuario()%>" disabled>
														<%for (Area a : areas) {%>
														<option value="<%=a.getNombre()%>"
															<%if (a.getNombre().equals(u.getArea())) {%>
															selected <%}%>><%=a.getNombre()%></option>
														<%}%>
												</select></td>
												<td><input type="checkbox"
													name="federada-usuario-<%=u.getIdusuario()%>"
													onclick="javascript: return false;"
													<%if (u.getFederada()) {%> checked <%}%>></td>
												<td><input type="checkbox"
													name="activo-usuario-<%=u.getIdusuario()%>"
													onclick="javascript: return false;"
													<%if (u.getActivo()) {%> checked <%}%>></td>

												<td style="text-align: right"
													id="editar-usuario-<%=u.getIdusuario()%>">
													<button type="button"
														onclick="editar(<%=u.getIdusuario()%>)"
														id="btn-editar-usuario-<%=u.getIdusuario()%>"
														class="btn boton-accion">Editar</button>
												</td>
												<td style="text-align: right; display: none"
													id="conf-canc-editar-usuario-<%=u.getIdusuario()%>">
													<button type="button"
														onclick="cancelarEditar(<%=u.getIdusuario()%>)"
														id="btn-cancelar-usuario-<%=u.getIdusuario()%>"
														class="btn boton-accion">Cancelar</button>
													<button type="submit"
														id="btn-confirmar-area-<%=u.getIdusuario()%>"
														class="btn boton-accion">Confirmar</button>
												</td>

											</tr>
											<%}%>
											<tr class="form-nuevo-elemento " id="form-nuevo-usuario"
												style="display: none">
												<td><input type="text" onchange="comprobarCampos()"
													class="input-elemento" name="usuario-nuevo"
													placeholder="Usuario"></td>
												<td><select onchange="comprobarCampos()"
													class="input-elemento" name="rol-usuario-nuevo" id="nuevo-rol">
														<%for(Rol r : Rol.values()){ %>
														<option value="<%=r.getId()%>"><%=r.getRol()%></option>
														<%} %>
												</select></td>
												<td><select onchange="comprobarCampos()"
													class="input-elemento" name="area-usuario-nuevo" id="nuevo-area">
														<%for(Area a : areas){ %>
														<option value="<%=a.getNombre()%>"><%=a.getNombre()%></option>
														<%} %>
												</select></td>
												<td><input type="checkbox" class="check-elemento"
													name="federada-usuario-nuevo" value="true"></td>
												<td><input type="checkbox" class="check-elemento"
													name="activo-usuario-nuevo" value="true"></td>
											</tr>
										</tbody>
									</table>
								<%} %>
								
								</div>

								<div class="row justify-content-end" id="fila-insertar">
									<button type="button" onclick="insertar()" id="btn-insertar"
										class="btn login-button py-2 px-3"></button>
								</div>

								<div class="row justify-content-end" id="fila-confirmar"
									style="display: none">
									<button type="button" onclick="cancelar()" id="btn-cancelar"
										class="btn login-button py-2 px-3 mr-3">Cancelar</button>
									<button type="submit" id="btn-confirmar" name="btn-confirmar"
										class="btn login-button py-2 px-3">Confirmar</button>
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
	<script src="../js/bootstrap.min.js"></script>
	<script src="../js/admin.js"></script>
	<script> inicializar('<%=tabla%>'); </script>

</body>

</html>
