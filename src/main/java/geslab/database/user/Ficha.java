package geslab.database.user;

import geslab.database.Conexion;

public class Ficha extends Conexion{
	
	private int codficha;
	private String calidad;
	private Ubicacion ubicacion;
	private String proveedor;
	private String marca;
	private Producto producto;
	
	public Ficha(int codficha, String calidad, Ubicacion ubicacion, String proveedor, String marca, Producto producto) {
		this.codficha = codficha;
		this.calidad = calidad;
		this.ubicacion = ubicacion;
		this.proveedor = proveedor;
		this.marca = marca;
		this.producto = producto;
	}

	public int getCodficha() {
		return codficha;
	}

	public String getCalidad() {
		return calidad;
	}

	public Ubicacion getUbicacion() {
		return ubicacion;
	}

	public String getProveedor() {
		return proveedor;
	}

	public String getMarca() {
		return marca;
	}

	public Producto getProducto() {
		return producto;
	}

}
