var accion = "";
var codigo = "";

function inicializar(){
	$(document).on("click", "#cerrar-mensaje", function() {
		$('#mensaje').css("display", "none");
	});
}

function insertar() {
	accion = "insertar";
	var campos = ["nombre", "centro", "oculta"];
	campos.forEach(function(valor, indice, array) {
		document.getElementById("insertar-" + valor).value = "";
	});
	document.getElementById("tituloModal").innerText = "Nueva ubicacion"
	$("#modalUbicacion").modal();
}

function editar(cod) {
	accion = "editar";
	codigo = cod;
	var campos = ["nombre", "centro"];
	campos.forEach(function(valor, indice, array) {
		document.getElementById("insertar-" + valor).value = document
				.getElementById(valor + "-" + cod).innerText;
	});
	
	if(document.getElementById("oculta-"+cod).innerText == "Si"){
		document.getElementById("insertar-oculta").checked = true;
	}

	document.getElementById("tituloModal").innerText = "Editar ubicacion (#"+cod+")";
	$("#modalUbicacion").modal();
}

function cancelar() {
	accion = "";
	$("#modalUbicacion").modal("hide");
}

function confirmar() {
	document.getElementById("accion").value = accion;
	document.getElementById("codigo").value = codigo;
	document.getElementById("insertar-ubicacion").submit();
}

function filtrado() {
	var campos = ["nombre", "dpto", "area", "centro", "oculta"];
	var filtros = [];
	campos.forEach( function(valor, indice, array) {
	    filtros[indice] = document.getElementById("filtro-" + valor).value.toUpperCase();
	});
	tabla = document.getElementById("tabla-ubicaciones");
	var filas = tabla.getElementsByTagName("tr");

	filas.forEach( function(valor, indice, array) {
		var cod = valor.dataset.fila;
		var columnas = filas[indice].getElementsByTagName("td");
		visible = true;
		if(indice > 0){
			campos.forEach(function (valor, indice, array){
				v = columnas.namedItem(valor + "-" + cod).innerText.toUpperCase();
				visible = v.indexOf(filtros[indice]) > -1 ? visible && true : visible && false;
			});
			filas[indice].style.display = visible ? "" : "none";
		}
	});
}

function reiniciarFiltro(){
	var campos = ["nombre", "dpto", "area", "centro", "oculta"];
	campos.forEach( function(valor, indice, array) {
		document.getElementById("filtro-" + valor).value = "";
	});
	filtrado();
}
