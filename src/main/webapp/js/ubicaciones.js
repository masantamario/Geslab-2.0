var accion = "";
var codigo = "";

function insertar() {
	accion = "insertar";
	var campos = ["nombre", "area", "centro", "oculta"];
	campos.forEach(function(valor, indice, array) {
		document.getElementById("insertar-" + valor).value = "";
	});
	document.getElementById("tituloModal").innerText = "Nueva ubicacion"
	$("#modalUbicacion").modal();
}

function editar(cod) {
	accion = "editar";
	codigo = cod;
	var campos = ["nombre", "area", "centro"];
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

function filtrar(campo, col) {
	var input, filter, table, tr, td, i, txtValue;
	input = document.getElementById("filtro-" + campo);
	filtro = input.value.toUpperCase();
	tabla = document.getElementById("tabla-ubicaciones");
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
