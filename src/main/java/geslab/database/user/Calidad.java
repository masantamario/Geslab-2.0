package geslab.database.user;

public class Calidad {
	private int codcalidad;
	private String nombre;
	
	public Calidad(int cod, String nombre) {
		this.codcalidad = cod;
		this.nombre = nombre;
	}

	public int getCodcalidad() {
		return codcalidad;
	}

	public String getNombre() {
		return nombre;
	}

}
