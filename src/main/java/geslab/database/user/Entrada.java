package geslab.database.user;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Entrada {
	private DateFormat formatoFechaCal = new SimpleDateFormat("yyyy-MM-dd");
	private DateFormat formatoFechaImp = new SimpleDateFormat("dd-MM-yyyy");

	private int codentrada;
	private Ficha ficha;
	private Date fecha;
	private int unidades;
	private String nota;
	private int usuario;

	public Entrada(int codentrada, Ficha ficha, Date fecha, int unidades, String nota, int usuario) {
		this.codentrada = codentrada;
		this.ficha = ficha;
		this.fecha = fecha;
		this.unidades = unidades;
		this.nota = nota;
		this.usuario = usuario;
	}

	public int getCodentrada() {
		return codentrada;
	}

	public Ficha getFicha() {
		return ficha;
	}

	public String getFecha() {
		String f = formatoFechaImp.format(fecha);
		return f;
	}

	public String getFechaCal() {
		String f = formatoFechaCal.format(fecha);
		return f;
	}

	public Date getFechaIns() {
		return this.fecha;
	}

	public int getUnidades() {
		return unidades;
	}

	public String getNota() {
		return nota;
	}

	public int getUsuario() {
		return usuario;
	}

}
