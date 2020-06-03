var accion = "";
var codigo = "";

function insertar() {
	accion = "insertar";
	var campos = ["nombre"];
	campos.forEach(function(valor, indice, array) {
		document.getElementById("insertar-" + valor).value = "";
	});
	document.getElementById("tituloModal").innerText = "Nueva calidad"
	$("#modalCalidad").modal();
}

function editar(cod) {
	accion = "editar";
	codigo = cod;
	var campos = ["nombre"];
	campos.forEach(function(valor, indice, array) {
		document.getElementById("insertar-" + valor).value = document
				.getElementById(valor + "-" + cod).innerText;
	});
	
	document.getElementById("tituloModal").innerText = "Editar calidad (#"+cod+")";
	$("#modalCalidad").modal();
}

function cancelar() {
	accion = "";
	$("#modalCalidad").modal("hide");
}

function confirmar() {
	document.getElementById("accion").value = accion;
	document.getElementById("codigo").value = codigo;
	document.getElementById("insertar-calidad").submit();
}

function filtrar(campo, col) {
	var input, filter, table, tr, td, i, txtValue;
	input = document.getElementById("filtro-" + campo);
	filtro = input.value.toUpperCase();
	tabla = document.getElementById("tabla-calidades");
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
