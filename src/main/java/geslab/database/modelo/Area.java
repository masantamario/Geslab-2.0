package geslab.database.modelo;

public class Area {
	private int codarea;
	private String nombre;
	private String dpto;
	
	public Area(int codarea, String nombre, String dpto) {
		this.setCodarea(codarea);
		this.setNombre(nombre);
		this.setDpto(dpto);
	}

	public int getCodarea() {
		return codarea;
	}

	public void setCodarea(int codarea) {
		this.codarea = codarea;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDpto() {
		return dpto;
	}

	public void setDpto(String dpto) {
		this.dpto = dpto;
	}
}
