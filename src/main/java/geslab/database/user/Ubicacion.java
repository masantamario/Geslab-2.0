package geslab.database.user;

public class Ubicacion {
	
	private int codubicacion;
	private String nombre;
	private String area;
	private String centro;
	private boolean oculta;

	public Ubicacion(int codubicacion, String nombre, String area, String centro, boolean oculta) {
		this.codubicacion = codubicacion;
		this.nombre = nombre;
		this.area = area;
		this.centro = centro;
		this.oculta = oculta;
	}

	public int getCodubicacion() {
		return codubicacion;
	}

	public String getNombre() {
		return nombre;
	}

	public String getArea() {
		return area;
	}

	public String getCentro() {
		return centro;
	}

	public boolean isOculta() {
		return oculta;
	}
}
