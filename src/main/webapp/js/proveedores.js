var accion = "";
var codigo = "";

function inicializar(){
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

function filtrar(campo, col) {
	var input, filter, table, tr, td, i, txtValue;
	input = document.getElementById("filtro-" + campo);
	filtro = input.value.toUpperCase();
	tabla = document.getElementById("tabla-proveedores");
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
