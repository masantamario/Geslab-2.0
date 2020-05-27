package geslab.database.user;

public class Marca {
	private int codmarca;
	private String nombre;
	private String descripcion;
	private String telefono;
	private String direccion;
	
	public Marca(int cod, String nombre, String descripcion, String telefono, String direccion) {
		this.codmarca = cod;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.telefono = telefono;
		this.direccion = direccion;
	}

	public int getCodmarca() {
		return codmarca;
	}

	public String getNombre() {
		return nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getTelefono() {
		return telefono;
	}

	public String getDireccion() {
		return direccion;
	}

}
