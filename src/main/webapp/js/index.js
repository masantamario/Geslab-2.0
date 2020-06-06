var accion = "";
var codigo = "";

function inicializar() {
	$("input").attr("spellcheck", "false");
	$(document.body).on("click", "td:not(.info)", function() {
		mostrarExtraInfo(this.parentElement.dataset.fila);
	});
//	$('.count').prop('disabled', true);
		$(document).on('click','.plus',function(){
		$('.count').val(parseInt($('.count').val()) + 1 );
	});
	$(document).on('click','.minus',function(){
		$('.count').val(parseInt($('.count').val()) - 1 );
			if ($('.count').val() == 0) {
				$('.count').val(1);
			}
    	});
}

function insertar() {
	accion = "insertar";
	$("#modalFicha").modal();
}

function cancelar() {
	accion = "";
	var camposFicha = ["calidad", "ubicacion", "proveedor", "marca", "producto"];
	camposFicha.forEach(function(valor, indice, array) {
		document.getElementById("insertar-" + valor).disabled = false;
	});
	$("#modalFicha").modal("hide");
}

function confirmar() {
	document.getElementById("accion-ficha").value = accion;
	document.getElementById("codigo-ficha").value = codigo;
	document.getElementById("insertar-ficha").submit();
}

function entSal(cod, entSal) {
	accion = entSal;
	codigo = cod;
	document.getElementById("tituloModalEntSal").innerText = "Nueva " + entSal;
	document.getElementById('insertar-fecha').valueAsDate = new Date();
	$("#modalEntSal").modal();
	ocultarExtraInfo();
}

function confirmarEntSal() {
	document.getElementById("accion-EntSal").value = accion;
	document.getElementById("codigo-fichaEntSal").value = codigo;
	document.getElementById("insertar-ent-sal").submit();
}

function cancelarEntSal() {
	accion = "";
	document.getElementById("insertar-unidades").value = "1";
	$("#modalEntSal").modal("hide");
}

function mostrarExtraInfo(codficha) {
	var campos = ["lote", "residuo"];
	campos.forEach(function(valor, indice, array) {
		document.getElementById("extra-info-" + valor).innerText = document
				.getElementById(valor + "-" + codficha).innerText;
	});
	
	var caducidad = document.getElementById("caducidad-" + codficha).innerText;
	document.getElementById("extra-info-caducidad").innerText = formatearFecha(caducidad);
	mostrarEntradas(codficha);
	mostrarSalidas(codficha); 

	document.getElementById("fila-tabla").style.height = "35%";
	document.getElementById("fila-info").style.height = "65%";
	document.getElementById("container-info").style.display = "";

}

function ocultarExtraInfo() {
	document.getElementById("fila-tabla").style.height = "80%";
	document.getElementById("fila-info").style.height = "20%";
	document.getElementById("container-info").style.display = "none";
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

function filtrarFecha() {
	var d = document.getElementById("filtro-desde").value;
	var h = document.getElementById("filtro-hasta").value;
	var desde, hasta;

	if (h == "") {
		document.getElementById("filtro-hasta").value = d;
		h = d;
	}
	if (d == "") {
		desde = new Date("1990-01-01");
		hasta = new Date();
		document.getElementById("filtro-hasta").value = "";
	} else {
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

function formatearFecha(fecha){
	var fecha = fecha.split("-");
	var fechaFormateada = fecha[2] + "-" + fecha[1] + "-" + fecha[0]; 
	return fechaFormateada;	
}