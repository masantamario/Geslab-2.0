var accion = "";
var codigo = "";

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

function editar(cod, proovedores) {
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
	proovedores.forEach(function(valor, indice, array) {
		valor.selected = true;
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

function filtrar(campo, col) {
	var input, filter, table, tr, td, i, txtValue;
	input = document.getElementById("filtro-" + campo);
	filtro = input.value.toUpperCase();
	tabla = document.getElementById("tabla-marcas");
	tr = tabla.getElementsByTagName("tr");

	for (i = 0; i < tr.length; i++) {
		td = tr[i].getElementsByTagName("td")[col];
		if (td) {
			txtValue = td.textContent || td.innerText;
			if (txtValue.toUpperCase().indexOf(filtro) > -1) {
				tr[i].style.display = "";
			} else {
				tr[i].style.display = "none";
			}
		}
	}
}
