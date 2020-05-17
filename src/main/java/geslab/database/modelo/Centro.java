package geslab.database.modelo;

//import java.util.ArrayList;

public class Centro {
	private int codcentro;
	private String nombre;
	//private ArrayList<Area> areas;
	
	public Centro(int codcentro, String nombre) {
		this.codcentro = codcentro;
		this.nombre = nombre;
		//this.areas = areas;
	}

	public int getCodcentro() {
		return codcentro;
	}

	public String getNombre() {
		return nombre;
	}

//	public ArrayList<Area> getAreas() {
//		return areas;
//	}
}
