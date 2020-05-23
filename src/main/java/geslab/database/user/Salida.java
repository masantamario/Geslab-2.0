package geslab.database.user;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Salida {
	private int codsalida;
	private Ficha ficha;
	private Timestamp fecha;
	private Timestamp caducidad;
	private String lote;
	private BigDecimal unidades;
	private BigDecimal capacidad;
	private String g_ml;
	private boolean residuo;
	
	public Salida(int codsalida, Ficha ficha, Timestamp fecha, Timestamp caducidad, String lote, BigDecimal unidades,
			BigDecimal capacidad, String g_ml, Boolean residuo) {
		this.codsalida = codsalida;
		this.ficha = ficha;
		this.fecha = fecha;
		this.caducidad = caducidad;
		this.lote = lote;
		this.unidades = unidades;
		this.capacidad = capacidad;
		this.g_ml = g_ml;
		this.residuo = residuo;
	}

	public int getCodsalida() {
		return codsalida;
	}

	public Ficha getFicha() {
		return ficha;
	}

	public Timestamp getFecha() {
		return fecha;
	}

	public Timestamp getCaducidad() {
		return caducidad;
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
	
}
