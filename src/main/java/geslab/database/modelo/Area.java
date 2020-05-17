package geslab.database.modelo;
//import java.util.ArrayList;

public class Area {
	private int codarea;
	private String nombre;
	private String dpto;
//	private ArrayList<String> centros;
	
	public Area(int codarea, String nombre, String dpto) {
		this.codarea = codarea;
		this.nombre = nombre;
		this.dpto = dpto;
		//this.centros = centros;
	}

	public int getCodarea() {
		return codarea;
	}

	public String getNombre() {
		return nombre;
	}

	public String getDpto() {
		return dpto;
	}

//	public ArrayList<String> getCentros() {
//		return centros;
//	}

}
