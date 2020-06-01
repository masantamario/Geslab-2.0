package geslab.database.user;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Entrada {
	private DateFormat formatoFechaCal = new SimpleDateFormat("yyyy-MM-dd");
	private DateFormat formatoFechaImp = new SimpleDateFormat("dd-MM-yyyy");

	private int codentrada;
	private Ficha ficha;
	private Date fecha;
	private Date caducidad;
	private String lote;
	private BigDecimal unidades;
	private BigDecimal capacidad;
	private String g_ml;
	private boolean residuo;

	public Entrada(int codentrada, Ficha ficha, Date fecha, Date caducidad, String lote, BigDecimal unidades,
			BigDecimal capacidad, String g_ml, Boolean residuo) {
		this.codentrada = codentrada;
		this.ficha = ficha;
		this.fecha = fecha;
		this.caducidad = caducidad;
		this.lote = lote;
		this.unidades = unidades;
		this.capacidad = capacidad.setScale(2, RoundingMode.HALF_EVEN);
		this.g_ml = g_ml;
		this.residuo = residuo;
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

	public String getCaducidad() {
		String cad = "N/D";
		if (caducidad != null)
			cad = formatoFechaImp.format(caducidad);
		return cad;
	}

	public String getCaducidadCal() {
		String cad = "N/D";
		if (caducidad != null)
			cad = formatoFechaCal.format(caducidad);
		return cad;
	}
	
	public Date getCaducidadIns() {
		return this.caducidad;
	}

	public String getLote() {
		return lote;
	}

	public BigDecimal getUnidades() {
		return unidades;
	}

	public BigDecimal getCapacidad() {
		return capacidad;
	}

	public String getG_ml() {
		return g_ml;
	}

	public boolean isResiduo() {
		return residuo;
	}

	public String esResiduo() {
		return (residuo) ? "Si" : "No";
	}

}
