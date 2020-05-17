package geslab.database.modelo;

public class Departamento {
	private int coddpto;
	private String nombre;
	
	public Departamento(int coddpto, String nombre) {
		this.coddpto = coddpto;
		this.nombre = nombre;
	}

	public int getCoddpto() {
		return coddpto;
	}

	public String getNombre() {
		return nombre;
	}
}
