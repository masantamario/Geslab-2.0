package geslab.database.user;

public class Pictograma {
	private String referencia;
	private String descripcion;
	
	public Pictograma(String referencia, String descripcion) {
		this.referencia = referencia;
		this.descripcion = descripcion;
	}

	public String getReferencia() {
		return referencia;
	}

	public String getDescripcion() {
		return descripcion;
	}
		
}
