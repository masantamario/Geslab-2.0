package geslab.database.user;

import java.util.ArrayList;

public class Proveedor {
	private int codproveedor;
	private String nombre;
	private String direccion;
	private String telefono;
	private String fax;
	private String email;
	private ArrayList<String> marcas;
	
	public Proveedor(int cod, String nombre, String direccion, String telefono, String fax, String email, ArrayList<String> marcas) {
		this.codproveedor = cod;
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
		this.fax = fax;
		this.email = email;
		this.marcas = marcas;
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

	public ArrayList<String> getMarcas() {
		return marcas;
	}

}
