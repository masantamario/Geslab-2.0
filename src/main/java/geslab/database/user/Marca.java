package geslab.database.user;

import java.util.ArrayList;

public class Marca {
	private int codmarca;
	private String nombre;
	private String telefono;
	private String direccion;
	private ArrayList<String> proveedores;
	
	public Marca(int cod, String nombre, String telefono, String direccion, ArrayList<String> proveedores) {
		this.codmarca = cod;
		this.nombre = nombre;
		this.telefono = telefono;
		this.direccion = direccion;
		this.proveedores = proveedores;
	}

	public int getCodmarca() {
		return codmarca;
	}

	public String getNombre() {
		return nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public ArrayList<String> getProveedores() {
		return proveedores;
	}

}
