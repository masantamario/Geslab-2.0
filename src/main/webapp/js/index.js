var elemento = "entrada";

function inicializar(e) {
	elemento = e;
	document.getElementById("boton-tabla__insertar").innerText = "Nueva "
			+ elemento
	$('#bt-' + elemento + 's').addClass('fila-pestanas__pestana--active');
	$("input").attr("spellcheck", "false");

	$(document).ready(function() {
		$(document.body).on("click", "tr[data-fila]", function() {
			mostrarExtraInfo(this.dataset.fila);
		});
	});

}

function mostrarElemento(e) {
	elemento = e;
	document.getElementById("tabla").value = e;
	document.getElementById("mostrarTabla").submit();
}

function cerrarSesion() {
	document.getElementById("accion").value = "logout";
	document.getElementById("opciones-usuario").submit();
}
function filtrarFecha() {
	var d = document.getElementById("filtro-desde").value;
	var h = document.getElementById("filtro-hasta").value;
	var desde, hasta;

	if(h == "") {
		document.getElementById("filtro-hasta").value = d;
		h = d;
	}
	if(d == ""){
		desde = new Date("1990-01-01");
		hasta = new Date();
		document.getElementById("filtro-hasta").value = "";
	}else{
		desde = new Date(d);
		hasta = new Date(h);
	}
	

	tabla = document.getElementById("tabla-" + elemento + "s");
	tr = tabla.getElementsByTagName("tr");

	for (i = 0; i < tr.length; i++) {
		td = tr[i].getElementsByTagName("td")[19];
		if (td) {
			txtValue = td.textContent || td.innerText;
			fecha = new Date(txtValue);
			if (fecha >= desde && fecha <= hasta) {
				tr[i].style.display = "";
			} else {
				tr[i].style.display = "none";
			}
		}
	}
	ocultarExtraInfo();

}

function filtrar(campo, col) {
	var input, filter, table, tr, td, i, txtValue;
	input = document.getElementById("filtro-" + campo);
	filtro = input.value.toUpperCase();
	tabla = document.getElementById("tabla-" + elemento + "s");
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
	ocultarExtraInfo();
}

function mostrarExtraInfo(codentrada) {
	var campos = [ "cas", "formula", "peso", "einecs", "ec", "calidad",
			"residuo", "dpto", "centro", "caducidad", "peligro", "prudencia", "oculto" ];
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