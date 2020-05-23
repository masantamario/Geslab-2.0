var elemento = "entrada";

function inicializar(e){
	elemento = e;
	document.getElementById("boton-tabla__insertar").innerText = "Nueva " + elemento
//	document.getElementById("elemento").value = elemento;
	$('#bt-'+elemento+'s').addClass('fila-pestanas__pestana--active');
	$("input").attr("spellcheck", "false");

}

function mostrarElemento(e){
	elemento = e;
//	document.getElementById("btn-confirmar").value = elemento;
//	document.getElementById("elemento").value = elemento;
	document.getElementById("tabla").value = e;
	document.getElementById("mostrarTabla").submit();
}

function cerrarSesion(){
	document.getElementById("accion").value = "logout";
	document.getElementById("opciones-usuario").submit();
}