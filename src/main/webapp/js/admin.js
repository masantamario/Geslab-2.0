var elemento = "usuario"; 

function ocultarTablas(){
	document.getElementById("tabla-usuarios").style.display = 'none';
	document.getElementById("tabla-areas").style.display = 'none';
}

function mostrarElementos(e){
	elemento = e;
	cancelar();
	ocultarTablas();
	document.getElementById("tabla-"+ elemento +"s").style.display = '';
	document.getElementById("btn-añadir").innerText = "Añadir "+ elemento
	document.getElementById("btn-confirmar").value = elemento;
	
}

function añadir() {
	$( ".form-nuevo-elemento" ).css('display','');
	document.getElementById("fila-añadir").style.display = 'none';
	document.getElementById("fila-confirmar").style.display = '';
	comprobarCampos();
}

function cancelar() {
	$( ".form-nuevo-elemento" ).css('display','none');
	document.getElementById("fila-añadir").style.display = '';
	document.getElementById("fila-confirmar").style.display = 'none';
	$( ".input-elemento" ).val('');
}

function comprobarCampos(){	
	var vacio = false;
		  $( "#form-nuevo-"+elemento + " > td > .input-elemento " ).each(function( index ) {
			  if( $( this ).val() == '') vacio = true;
			  }); 
	document.getElementById("btn-confirmar").disabled = vacio;
}

