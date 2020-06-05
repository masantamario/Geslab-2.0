package geslab.database.user;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Ficha {

	private DateFormat formatoFechaCal = new SimpleDateFormat("yyyy-MM-dd");
	private DateFormat formatoFechaImp = new SimpleDateFormat("dd-MM-yyyy");

	private int codficha;
	private Producto producto;
	private BigDecimal capacidad;
	private String g_ml;
	private String calidad;
	private Ubicacion ubicacion;
	private String marca;
	private String proveedor;
	private Date caducidad;
	private String lote;
	private boolean residuo;
	private int stock;

	public Ficha(int codficha, Producto producto, BigDecimal capacidad, String g_ml, String calidad,
			Ubicacion ubicacion, String marca, String proveedor, Date caducidad, String lote, Boolean residuo, int stock) {
		this.codficha = codficha;
		this.producto = producto;
		if (capacidad != null) this.capacidad = capacidad.setScale(2, RoundingMode.HALF_EVEN);
		this.g_ml = g_ml;
		this.calidad = calidad;
		this.ubicacion = ubicacion;
		this.marca = marca;
		this.proveedor = proveedor;
		this.caducidad = caducidad;
		this.lote = lote;
		this.residuo = residuo;
		this.stock = stock;

	}

	public int getCodficha() {
		return codficha;
	}

	public Producto getProducto() {
		return producto;
	}

	public BigDecimal getCapacidad() {
		return capacidad;
	}

	public String getG_ml() {
		return g_ml;
	}

	public String getCalidad() {
		return calidad;
	}

	public Ubicacion getUbicacion() {
		return ubicacion;
	}

	public String getMarca() {
		return marca;
	}

	public String getProveedor() {
		return proveedor;
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

	public boolean isResiduo() {
		return residuo;
	}

	public String esResiduo() {
		return (residuo) ? "Si" : "No";
	}

	public int getStock() {
		return stock;
	}

}
