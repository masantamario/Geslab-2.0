package geslab.database.user;

public class Peligro {
	private String frase;
	private String indicacion;
	
	public Peligro(String frase, String indicacion) {
		this.frase = frase;
		this.indicacion = indicacion;
	}

	public String getFrase() {
		return frase;
	}

	public String getIndicacion() {
		return indicacion;
	}
		
}
