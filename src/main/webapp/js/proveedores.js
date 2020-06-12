var accion = "";
var codigo = "";

function inicializar(){
	$("input").attr("spellcheck", "false");
	
	$(document.body).on("click", "td:not(.info)", function() {
		mostrarExtraInfo(this.parentElement.dataset.fila);
	});
	
	
	$('#insertar-marcas option').mousedown(function(e) {
	    e.preventDefault();
	    $(this).prop('selected', !$(this).prop('selected'));
	    return false;
	});
}

function insertar() {
	accion = "insertar";
	var campos = ["nombre", "direccion", "tlfn", "fax", "mail"];
	campos.forEach(function(valor, indice, array) {
		document.getElementById("insertar-" + valor).value = "";
	});
	
	selector = document.getElementById("insertar-marcas");
	opt =  selector.getElementsByTagName("option");
	for (i = 0; i < opt.length; i++) {
		opt[i].selected = false;
	}
	document.getElementById("tituloModal").innerText = "Nuevo proveedor"
	$("#modalProveedor").modal();
}

function editar(cod, marcas) {
	accion = "editar";
	codigo = cod;
	console.log("Aqui llega")
	var campos = ["nombre", "direccion", "tlfn", "fax", "mail"];
	campos.forEach(function(valor, indice, array) {
		document.getElementById("insertar-" + valor).value = document
				.getElementById(valor + "-" + cod).innerText;
	});
	
	selector = document.getElementById("insertar-marcas");
	opt =  selector.getElementsByTagName("option");
	for (i = 0; i < opt.length; i++) {
		opt[i].selected = false;
	}
	marcas.forEach(function(valor, indice, array) {
		valor.selected = true;
	});

	document.getElementById("tituloModal").innerText = "Editar proveedor (#"+cod+")";
	$("#modalProveedor").modal();
}

function cancelar() {
	accion = "";
	$("#modalProveedor").modal("hide");
}

function confirmar() {
	document.getElementById("accion").value = accion;
	document.getElementById("codigo").value = codigo;
	document.getElementById("insertar-proveedor").submit();
}

function mostrarExtraInfo(codprov) {
	mostrarMarcas(codprov);
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
	tabla = document.getElementById("tabla-proveedores");
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
