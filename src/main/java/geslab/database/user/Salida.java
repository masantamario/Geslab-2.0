package geslab.database.user;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Salida {
	private DateFormat formatoFechaCal = new SimpleDateFormat("yyyy-MM-dd");
	private DateFormat formatoFechaImp = new SimpleDateFormat("dd-MM-yyyy");

	private int codsalida;
	private Ficha ficha;
	private Date fecha;
	private int unidades;
	private int usuario;
	
	public Salida(int codsalida, Ficha ficha, Date fecha, int unidades, int usuario) {
		this.codsalida = codsalida;
		this.ficha = ficha;
		this.fecha = fecha;
		this.unidades = unidades;
		this.usuario = usuario;
	}

	public int getCodsalida() {
		return codsalida;
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

	public int getUsuario() {
		return usuario;
	}
	
}
