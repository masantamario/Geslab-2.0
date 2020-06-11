var accion = "";
var codigo = "";

function inicializar() {
	$("input").attr("spellcheck", "false");
	$(document.body).on("click", "td:not(.info)", function() {
		mostrarExtraInfo(this.parentElement.dataset.fila);
	});
}

function insertar() {
	accion = "insertar";
	var campos = ["cas", "nombre", "formula", "f_des", "einecs", "ec", "precauciones"];
	campos.forEach(function(valor, indice, array) {
		document.getElementById("insertar-" + valor).value = "";
	});
	document.getElementById("tituloModal").innerText = "Nuevo producto"
	$("#modalProducto").modal();
}

function editar(cod) {
	accion = "editar";
	codigo = cod;
	var campos = ["cas", "nombre", "formula", "f_des", "einecs", "ec"];
	campos.forEach(function(valor, indice, array) {
		document.getElementById("insertar-" + valor).value = document
				.getElementById(valor + "-" + cod).innerText;
	});
	

	document.getElementById("tituloModal").innerText = "Editar producto (#"+cod+")";
	$("#modalProducto").modal();
}

function cancelar() {
	accion = "";
	$("#modalProducto").modal("hide");
}

function confirmar() {
	document.getElementById("accion").value = accion;
	document.getElementById("codigo").value = codigo;
	document.getElementById("insertar-producto").submit();
}

function filtrado() {
	var campos = ["cas", "nombre", "formula", "einecs", "ec"];
	var filtros = [];
	campos.forEach( function(valor, indice, array) {
	    filtros[indice] = document.getElementById("filtro-" + valor).value.toUpperCase();
	});
	tabla = document.getElementById("tabla-productos");
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
	var campos = ["cas", "nombre", "formula", "einecs", "ec"];
	campos.forEach( function(valor, indice, array) {
		document.getElementById("filtro-" + valor).value = "";
	});
	filtrado();
}

function mostrarExtraInfo(codentrada) {
	var campos = ["peso", "einecs", "ec"];
	campos.forEach(function(valor, indice, array) {
		document.getElementById("extra-info-" + valor).innerText = document
				.getElementById(valor + "-" + codentrada).innerText;
	});

	document.getElementById("fila-tabla").style.height = "35%";
	document.getElementById("fila-info").style.height = "65%";
	document.getElementById("container-info").style.display = "";

}

function ocultarExtraInfo() {
	document.getElementById("fila-tabla").style.height = "80%";
	document.getElementById("fila-info").style.height = "20%";
	document.getElementById("container-info").style.display = "none";
}
