package geslab.database.user;

public class Proveedor {
	private int codproveedor;
	private String nombre;
	private String direccion;
	private String telefono;
	private String fax;
	private String email;
	private String contacto;
	
	public Proveedor(int cod, String nombre, String direccion, String telefono, String fax, String email, String contacto) {
		this.codproveedor = cod;
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
		this.fax = fax;
		this.email = email;
		this.contacto = contacto;
	}

	public int getCodproveedor() {
		return codproveedor;
	}

	public String getNombre() {
		return nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public String getFax() {
		return fax;
	}

	public String getEmail() {
		return email;
	}

	public String getContacto() {
		return contacto;
	}
}
