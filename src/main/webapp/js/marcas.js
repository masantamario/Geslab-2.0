var accion = "";
var codigo = "";

function inicializar(){
	$("input").attr("spellcheck", "false");
	
	$(document.body).on("click", "td:not(.info)", function() {
		mostrarExtraInfo(this.parentElement.dataset.fila);
	});
	
	
	$('#insertar-proveedores option').mousedown(function(e) {
	    e.preventDefault();
	    $(this).prop('selected', !$(this).prop('selected'));
	    return false;
	});
}

function insertar() {
	accion = "insertar";
	var campos = ["nombre", "tlfn", "direccion"];
	campos.forEach(function(valor, indice, array) {
		document.getElementById("insertar-" + valor).value = "";
	});
	selector = document.getElementById("insertar-proveedores");
	opt =  selector.getElementsByTagName("option");
	for (i = 0; i < opt.length; i++) {
		opt[i].selected = false;
	}
	document.getElementById("tituloModal").innerText = "Nueva marca"
	$("#modalMarca").modal();
}

function editar(cod) {
	accion = "editar";
	codigo = cod;
	var campos = ["nombre", "tlfn", "direccion"];
	campos.forEach(function(valor, indice, array) {
		document.getElementById("insertar-" + valor).value = document
				.getElementById(valor + "-" + cod).innerText;
	});
	
	selector = document.getElementById("insertar-proveedores");
	opt =  selector.getElementsByTagName("option");
	for (i = 0; i < opt.length; i++) {
		opt[i].selected = false;
	}

	$("#proveedores-" + cod + " option").each(function(){
		$("#insertar-proveedores").find("#" + $(this).val().replace(' ','_').replace('.', '_')).prop("selected","selected");
	});
	
	document.getElementById("tituloModal").innerText = "Editar marca (#"+cod+")";
	$("#modalMarca").modal();
}

function cancelar() {
	accion = "";
	$("#modalMarca").modal("hide");
}

function confirmar() {
	document.getElementById("accion").value = accion;
	document.getElementById("codigo").value = codigo;
	document.getElementById("insertar-marca").submit();
}

function mostrarExtraInfo(codmarca) {
	mostrarProvs(codmarca);
	document.getElementById("fila-tabla").style.height = "35%";
	document.getElementById("fila-info").style.height = "65%";
	document.getElementById("container-info").style.display = "";

}

function ocultarExtraInfo() {
	document.getElementById("fila-tabla").style.height = "80%";
	document.getElementById("fila-info").style.height = "20%";
	document.getElementById("container-info").style.display = "none";
}

function filtrado() {
	var campos = ["nombre"];
	var filtros = [];
	campos.forEach( function(valor, indice, array) {
	    filtros[indice] = document.getElementById("filtro-" + valor).value.toUpperCase();
	});
	tabla = document.getElementById("tabla-marcas");
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
	var campos = ["nombre"];
	campos.forEach( function(valor, indice, array) {
		document.getElementById("filtro-" + valor).value = "";
	});
	filtrado();
}