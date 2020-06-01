package geslab.database;

import java.math.BigDecimal;
import java.security.spec.KeySpec;
import javax.crypto.spec.PBEKeySpec;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import geslab.database.admin.*;
import geslab.database.user.*;

public class Conexion {
	private static final String CONTROLADOR = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/dbo?serverTimezone=UTC";
	private static final String USER = "root";
	private static final String PASS = "geslabMarioSantamaria";
	protected static Connection conexion = null;

	private static String secretKey = "GeslabGeslab2.0";
	private static String salt = "MarioSantamariaArias";

	public Conexion() {
		conectar();
	}

	public Connection conectar() {
		try {
			Class.forName(CONTROLADOR);
			conexion = DriverManager.getConnection(URL, USER, PASS);
		} catch (ClassNotFoundException e) {
			System.err.println("Error al cargar el controlador");
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("Error en la conexión");
			e.printStackTrace();
		}
		return conexion;
	}

	public void cerrarConexion() {
		try {
			if (conexion != null)
				conexion.close();
		} catch (SQLException e) {
			System.err.println("Error al cerrar la conexion");
			e.printStackTrace();
		}
	}

	public void cerrarRsPstm(ResultSet rs, PreparedStatement pstm, String metodo) {
		try {
			if (rs != null)
				rs.close();
			if (pstm != null)
				pstm.close();
		} catch (SQLException e) {
			System.err.println("Error al cerrar resulSet o PreparedStament en " + metodo);
		}
	}

	public String encriptar(String strToEncrypt) {
		try {
			byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			IvParameterSpec ivspec = new IvParameterSpec(iv);

			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), 65536, 256);
			SecretKey tmp = factory.generateSecret(spec);
			SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
			return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
		} catch (Exception e) {
			System.out.println("Error while encrypting: " + e.toString());
		}
		return null;
	}

	public String desencriptar(String strToDecrypt) {
		try {
			byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			IvParameterSpec ivspec = new IvParameterSpec(iv);

			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), 65536, 256);
			SecretKey tmp = factory.generateSecret(spec);
			SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
			return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
		} catch (Exception e) {
			System.out.println("Error while decrypting: " + e.toString());
		}
		return null;
	}

	public Usuario existeUsuario(String u, String p) {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Usuario usuario = null;

		try {
			pstm = conexion.prepareStatement("select * from usuarios where usuario = ? and contrasena = ? ");
			pstm.setString(1, u);
			pstm.setString(2, p);
			rs = pstm.executeQuery();
			if (rs.next())
				usuario = leerUsuario(u);

		} catch (SQLException e) {
			printSQLException(e, "EXISTE USUARIO");
		} finally {
			cerrarRsPstm(rs, pstm, "existeUsuario");
		}

		return usuario;
	}

	public Ficha existeFicha(Ficha f) {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Ficha ficha = null;

		try {
			pstm = conexion.prepareStatement("select * from ficha\r\n"
					+ "where calidad = (select codcalidad from calidad where nombre = ?) AND\r\n"
					+ "ubicacion = ? AND\r\n"
					+ "proveedor = (select codproveedor from proveedor where nombre = ?) AND\r\n"
					+ "marca = (select codmarca from marca where nombre = ?) AND\r\n" + "producto = ?");
			pstm.setString(1, f.getCalidad());
			pstm.setInt(2, f.getUbicacion().getCodubicacion());
			pstm.setString(3, f.getProveedor());
			pstm.setString(4, f.getMarca());
			pstm.setString(5, f.getProducto().getCas());
			rs = pstm.executeQuery();

			int cod = rs.next() ? rs.getInt("codficha") : 0;

			ficha = new Ficha(cod, f.getCalidad(), f.getUbicacion(), f.getProveedor(), f.getMarca(), f.getProducto());
		} catch (SQLException e) {
			printSQLException(e, "EXISTE FICHA");
		} finally {
			cerrarRsPstm(rs, pstm, "existeFicha");
		}
		return ficha;
	}

	public Usuario leerUsuario(String u) {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Usuario usuario = null;

		try {
			pstm = conexion.prepareStatement(
					"SELECT usuarios.idusuario, usuarios.usuario, usuarios.nombre, usuarios.mail, usuarios.rol, area.nombre AS area, usuarios.federada, usuarios.activo, usuarios.fecha_creacion\r\n"
							+ "FROM usuarios \r\n" + "INNER JOIN area ON usuarios.area = area.codarea\r\n"
							+ "WHERE usuario = ?;");

			pstm.setString(1, u);
			rs = pstm.executeQuery();

			if (rs.next()) {
				usuario = new Usuario(rs.getInt("idusuario"), rs.getString("usuario"), rs.getString("nombre"),
						rs.getString("mail"), Boolean.valueOf(rs.getString("federada")),
						Boolean.valueOf(rs.getString("activo")), rs.getInt("rol"), rs.getString("area"),
						rs.getDate("fecha_creacion"));
			}
			cerrarRsPstm(rs, pstm, "leerUsuario");
		} catch (SQLException e) {
			printSQLException(e, "LEER USUARIO");
		} finally {
			cerrarRsPstm(rs, pstm, "leerUsuario");
		}

		return usuario;

	}

	public Producto leerProducto(String cas) {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Producto producto = null;

		try {
			pstm = conexion.prepareStatement("SELECT producto.*, nombre_producto.nombre from producto \r\n"
					+ "inner join nombre_producto on producto.cas = nombre_producto.cas\r\n" + "where producto.cas=?;");
			pstm.setString(1, cas);
			rs = pstm.executeQuery();

			if (rs.next()) {
				String formula = rs.getString("formula");
				String formula_des = rs.getString("form_desarrollada");
				String n_einecs = rs.getString("numero_einecs");
				String n_ec = rs.getString("numero_ec");
				String precauciones = rs.getString("precauciones");
				String nombre = rs.getString("nombre");

				producto = new Producto(cas, nombre, formula, formula_des, n_einecs, n_ec, precauciones);
			}
		} catch (SQLException e) {
			printSQLException(e, "LEER PRODUCTO");
		} finally {
			cerrarRsPstm(rs, pstm, "leerProducto");
		}

		return producto;

	}

	public Ubicacion leerUbicacion(int codubicacion) {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Ubicacion ubicacion = null;

		try {
			pstm = conexion.prepareStatement(
					"SELECT ubicacion.nombre, area.nombre as area, dpto.nombre as dpto, centro.nombre as centro, oculta\r\n"
							+ "from ubicacion\r\n" + "inner join area on ubicacion.area = area.codarea\r\n"
							+ "inner join dpto on area.dpto = dpto.coddpto\r\n"
							+ "inner join centro on ubicacion.centro = centro.codcentro\r\n"
							+ "where codubicacion = ?");
			pstm.setInt(1, codubicacion);
			rs = pstm.executeQuery();

			if (rs.next()) {
				String nombre = rs.getString("nombre");
				String area = rs.getString("area");
				String dpto = rs.getString("dpto");
				String centro = rs.getString("centro");
				boolean oculta = rs.getBoolean("oculta");

				ubicacion = new Ubicacion(codubicacion, nombre, area, dpto, centro, oculta);
			}
		} catch (SQLException e) {
			printSQLException(e, "LEER UBICACION");
		} finally {
			cerrarRsPstm(rs, pstm, "leerUbicacion");
		}

		return ubicacion;

	}

	public ArrayList<Centro> leerCentros() {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<Centro> centros = new ArrayList<Centro>();

		try {
			pstm = conexion.prepareStatement("select * from centro;");
			rs = pstm.executeQuery();

			while (rs.next()) {
				int cod = rs.getInt("codcentro");
				String centro = rs.getString("nombre");
				centros.add(new Centro(cod, centro));
			}

		} catch (SQLException e) {
			printSQLException(e, "LEER CENTROS");

		} finally {
			cerrarRsPstm(rs, pstm, "leerCentros");
		}

		return centros;
	}

	public ArrayList<Departamento> leerDepartamentos() {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<Departamento> departamentos = new ArrayList<Departamento>();

		try {
			pstm = conexion.prepareStatement("select * from dpto");
			rs = pstm.executeQuery();

			while (rs.next()) {

				Departamento d = new Departamento(rs.getInt("coddpto"), rs.getString("nombre"));
				departamentos.add(d);
			}

		} catch (SQLException e) {
			printSQLException(e, "LEER DEPARTAMENTOS");

		} finally {
			cerrarRsPstm(rs, pstm, "leerDepartamentos");
		}

		return departamentos;
	}

	public ArrayList<Area> leerAreas() {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<Area> areas = new ArrayList<Area>();

		try {
			pstm = conexion.prepareStatement(
					"SELECT area.codarea, area.nombre AS Area, area.dpto AS coddpto, dpto.nombre AS Departamento\r\n"
							+ "FROM area \r\n" + "INNER JOIN dpto ON area.dpto = dpto.coddpto;");
			rs = pstm.executeQuery();

			while (rs.next()) {
				int cod = rs.getInt("codarea");
				String area = rs.getString("Area");
				String dpto = rs.getString("Departamento");

				areas.add(new Area(cod, area, dpto));
			}

		} catch (SQLException e) {
			printSQLException(e, "LEER AREAS");

		} finally {
			cerrarRsPstm(rs, pstm, "leerAreas");
		}

		return areas;
	}

	public ArrayList<Usuario> leerUsuarios() {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

		try {
			pstm = conexion.prepareStatement("select * from usuarios");
			rs = pstm.executeQuery();

			while (rs.next()) {
				Usuario u = leerUsuario(rs.getString("usuario"));
				usuarios.add(u);
			}

		} catch (SQLException e) {
			printSQLException(e, "LEER USUARIOS");

		} finally {
			cerrarRsPstm(rs, pstm, "leerUsuarios");
		}

		return usuarios;
	}

	public ArrayList<Producto> leerProductos() {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<Producto> productos = new ArrayList<Producto>();

		try {
			pstm = conexion.prepareStatement("select cas from producto");
			rs = pstm.executeQuery();

			while (rs.next()) {
				Producto p = leerProducto(rs.getString("cas"));
				productos.add(p);
			}

		} catch (SQLException e) {
			printSQLException(e, "LEER PRODUCTOS");

		} finally {
			cerrarRsPstm(rs, pstm, "leerProductos");
		}

		return productos;
	}

	public ArrayList<Ficha> leerFichas() {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<Ficha> fichas = new ArrayList<Ficha>();

		try {
			pstm = conexion.prepareStatement(
					"select codficha, calidad.nombre AS calidad, ubicacion, proveedor.nombre AS proveedor, marca.nombre AS marca, producto \r\n"
							+ "from ficha\r\n" + "inner join calidad on ficha.calidad = calidad.codcalidad\r\n"
							+ "inner join proveedor on ficha.proveedor = proveedor.codproveedor\r\n"
							+ "inner join marca on ficha.marca = marca.codmarca");
			rs = pstm.executeQuery();

			while (rs.next()) {
				int cod = rs.getInt("codficha");
				String calidad = rs.getString("calidad");
				Ubicacion ubicacion = leerUbicacion(rs.getInt("ubicacion"));
				String proveedor = rs.getString("proveedor");
				String marca = rs.getString("marca");
				Producto producto = leerProducto(rs.getString("producto"));

				fichas.add(new Ficha(cod, calidad, ubicacion, proveedor, marca, producto));
			}

		} catch (SQLException e) {
			printSQLException(e, "LEER FICHAS");

		} finally {
			cerrarRsPstm(rs, pstm, "leerFichas");
		}

		return fichas;
	}

	public ArrayList<Ubicacion> leerUbicaciones() {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<Ubicacion> ubicaciones = new ArrayList<Ubicacion>();

		try {
			pstm = conexion.prepareStatement("select codubicacion from ubicacion");
			rs = pstm.executeQuery();

			while (rs.next()) {
				ubicaciones.add(leerUbicacion(rs.getInt("codubicacion")));
			}

		} catch (SQLException e) {
			printSQLException(e, "LEER UBICACIONES");

		} finally {
			cerrarRsPstm(rs, pstm, "leerUbicaciones");
		}

		return ubicaciones;
	}

	public ArrayList<Proveedor> leerProveedores() {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<Proveedor> proveedores = new ArrayList<Proveedor>();

		try {
			pstm = conexion.prepareStatement("select * from proveedor");
			rs = pstm.executeQuery();

			while (rs.next()) {
				int cod = rs.getInt("codproveedor");
				String nombre = rs.getString("nombre");
				String direccion = rs.getString("direccion");
				String telefono = rs.getString("tfno");
				String fax = rs.getString("fax");
				String mail = rs.getString("email");
				String contacto = rs.getString("contacto");

				proveedores.add(new Proveedor(cod, nombre, direccion, telefono, fax, mail, contacto));
			}

		} catch (SQLException e) {
			printSQLException(e, "LEER PROVEEDORES");

		} finally {
			cerrarRsPstm(rs, pstm, "leerProveedores");
		}

		return proveedores;
	}

	public ArrayList<Marca> leerMarcas() {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<Marca> marcas = new ArrayList<Marca>();

		try {
			pstm = conexion.prepareStatement("select * from marca");
			rs = pstm.executeQuery();

			while (rs.next()) {
				int cod = rs.getInt("codmarca");
				String nombre = rs.getString("nombre");
				String descripcion = rs.getString("descripcion");
				String telefono = rs.getString("telefono");
				String direccion = rs.getString("direccion");

				marcas.add(new Marca(cod, nombre, descripcion, telefono, direccion));
			}

		} catch (SQLException e) {
			printSQLException(e, "LEER MARCAS");

		} finally {
			cerrarRsPstm(rs, pstm, "leerMarcas");
		}

		return marcas;
	}

	public ArrayList<Calidad> leerCalidades() {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<Calidad> calidades = new ArrayList<Calidad>();

		try {
			pstm = conexion.prepareStatement("select * from calidad");
			rs = pstm.executeQuery();

			while (rs.next()) {
				int cod = rs.getInt("codcalidad");
				String nombre = rs.getString("nombre");
				calidades.add(new Calidad(cod, nombre));
			}

		} catch (SQLException e) {
			printSQLException(e, "LEER CALIDADES");

		} finally {
			cerrarRsPstm(rs, pstm, "leerCalidades");
		}

		return calidades;
	}

	public ArrayList<Entrada> leerEntradasFicha(Ficha ficha) {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<Entrada> entradas = new ArrayList<Entrada>();

		try {
			pstm = conexion.prepareStatement("select * from entrada where ficha=?");
			pstm.setInt(1, ficha.getCodficha());
			rs = pstm.executeQuery();

			while (rs.next()) {
				int codentrada = rs.getInt("codentrada");
//				int codficha = rs.getInt("ficha");
//				Timestamp fecha = rs.getTimestamp("fecha");
//				Timestamp caducidad = rs.getTimestamp("fechacaducidad");
				Date fecha = rs.getDate("fecha");
				Date caducidad = rs.getDate("fechacaducidad");
				String lote = rs.getString("lote");
				BigDecimal unidades = rs.getBigDecimal("unidades");
				BigDecimal capacidad = rs.getBigDecimal("capacidad");
				String g_ml = rs.getString("g_ml");
				Boolean residuo = rs.getBoolean("residuo");
//				Boolean.toString(u.getFederada()));

				entradas.add(
						new Entrada(codentrada, ficha, fecha, caducidad, lote, unidades, capacidad, g_ml, residuo));
			}

		} catch (SQLException e) {
			printSQLException(e, "LEER ENTRADAS-FICHA");

		} finally {
			cerrarRsPstm(rs, pstm, "leerEntradasFicha");
		}

		return entradas;
	}

	public ArrayList<Salida> leerSalidasFicha(Ficha ficha) {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<Salida> salidas = new ArrayList<Salida>();

		try {
			pstm = conexion.prepareStatement("select * from entrada where ficha=?");
			pstm.setInt(1, ficha.getCodficha());
			rs = pstm.executeQuery();

			while (rs.next()) {
				int codentrada = rs.getInt("codsalida");
//				int codficha = rs.getInt("ficha");
				Timestamp fecha = rs.getTimestamp("fecha");
				Timestamp caducidad = rs.getTimestamp("caducidad");
				String lote = rs.getString("lote");
				BigDecimal unidades = rs.getBigDecimal("unidades");
				BigDecimal capacidad = rs.getBigDecimal("capacidad");
				String g_ml = rs.getString("g_ml");
				Boolean residuo = rs.getBoolean("residuo");
//				Boolean.toString(u.getFederada()));

				salidas.add(new Salida(codentrada, ficha, fecha, caducidad, lote, unidades, capacidad, g_ml, residuo));
			}

		} catch (SQLException e) {
			printSQLException(e, "LEER SALIDAS-FICHA");

		} finally {
			cerrarRsPstm(rs, pstm, "leerSalidasFicha");
		}

		return salidas;
	}

	public ArrayList<Entrada> leerEntradas() {
		ArrayList<Entrada> entradas = new ArrayList<Entrada>();
		ArrayList<Ficha> fichas = leerFichas();
		for (Ficha f : fichas) {
			entradas.addAll(leerEntradasFicha(f));
		}
		return entradas;
	}

	public ArrayList<Salida> leerSalidas() {
		ArrayList<Salida> salidas = new ArrayList<Salida>();
		ArrayList<Ficha> fichas = leerFichas();
		for (Ficha f : fichas) {
			salidas.addAll(leerSalidasFicha(f));
		}
		return salidas;
	}

	public boolean insertarCentro(Centro c) {
		PreparedStatement pstm = null;
		boolean correcto = false;
		if (c.getCodcentro() == 0) {
			try {
				pstm = conexion.prepareStatement("INSERT INTO centro (nombre) VALUES (?)");
				pstm.setString(1, c.getNombre());
				pstm.executeUpdate();

			} catch (SQLException e) {
				printSQLException(e, "INSERTAR CENTRO");
			} finally {
				cerrarRsPstm(null, pstm, "insertarCentro");
			}
		} else {
			correcto = updateCentro(c);
		}
		return correcto;
	}

	public boolean insertarDepartamento(Departamento d) {
		PreparedStatement pstm = null;
		boolean correcto = false;
		if (d.getCoddpto() == 0) {
			try {
				pstm = conexion.prepareStatement("INSERT INTO dpto (nombre) VALUES (?)");
				pstm.setString(1, d.getNombre());
				pstm.executeUpdate();

			} catch (SQLException e) {
				printSQLException(e, "INSERTAR DEPARTAMENTO");
			} finally {
				cerrarRsPstm(null, pstm, "insertarDepartamento");
			}
		} else {
			correcto = updateDepartamento(d);
		}
		return correcto;
	}

	public boolean insertarArea(Area a) {
		PreparedStatement pstm = null;
		boolean correcto = false;
		if (a.getCodarea() == 0) {
			try {
				pstm = conexion.prepareStatement(
						"INSERT INTO area (nombre, dpto) VALUES (?, (SELECT coddpto FROM dpto WHERE nombre=?))");
				pstm.setString(1, a.getNombre());
				pstm.setString(2, a.getDpto());
				pstm.executeUpdate();

			} catch (SQLException e) {
				printSQLException(e, "INSERTAR AREA");
			} finally {
				cerrarRsPstm(null, pstm, "insertarArea");
			}
		} else {
			correcto = updateArea(a);
		}
		return correcto;
	}

	public boolean insertarUsuario(Usuario u) {
		PreparedStatement pstm = null;
		boolean correcto = false;
		if (u.getIdusuario() == 0) {
			try {
				pstm = conexion.prepareStatement(
						"INSERT INTO usuarios (usuario, contrasena, rol, area, federada, activo, fecha_creacion) VALUES (?, ?, ?, (SELECT codarea FROM area WHERE nombre=?), ?, ?, NOW())");
				pstm.setString(1, u.getUsuario());
				pstm.setString(2, encriptar(u.getUsuario()));
				pstm.setInt(3, u.getRol().getId());
				pstm.setString(4, u.getArea());
				pstm.setString(5, Boolean.toString(u.getFederada()));
				pstm.setString(6, Boolean.toString(u.getActivo()));
				pstm.executeUpdate();

			} catch (SQLException e) {
				printSQLException(e, "INSERTAR USUARIO");
			} finally {
				cerrarRsPstm(null, pstm, "insertarUsuario");
			}
		} else {
			correcto = updateUsuario(u);
		}
		return correcto;
	}

	public boolean insertarFicha(Ficha f) {
		PreparedStatement pstm = null;
		boolean correcto = false;

		try {
			pstm = conexion
					.prepareStatement("INSERT INTO ficha(calidad, ubicacion, proveedor, marca, producto) VALUES (\r\n"
							+ "(select codcalidad from calidad where nombre = ?),\r\n"
							+ "?,\r\n"
							+ "(select codproveedor from proveedor where nombre = ?), \r\n"
							+ "(select codmarca from marca where nombre = ?),\r\n"
							+ "?)");
			pstm.setString(1, f.getCalidad());
			pstm.setInt(2, f.getUbicacion().getCodubicacion());
			pstm.setString(3, f.getProveedor());
			pstm.setString(4, f.getMarca());
			pstm.setString(5, f.getProducto().getCas());
			pstm.executeUpdate();

		} catch (SQLException e) {
			printSQLException(e, "INSERTAR ficha");
		} finally {
			cerrarRsPstm(null, pstm, "insertarFicha");
		}

		return correcto;
	}

	public boolean insertarEntrada(Entrada e) {
		PreparedStatement pstm = null;
		boolean correcto = false;
		if (e.getCodentrada() == 0) {
			try {
				pstm = conexion.prepareStatement(
						"INSERT INTO entrada (ficha, fecha, fechacaducidad, lote, unidades, capacidad, g_ml, residuo) values (?, ?, ?, ?, ?, ?, ?, ?)");
				pstm.setInt(1, e.getFicha().getCodficha());
				pstm.setDate(2, new Date(e.getFechaIns().getTime()));
				pstm.setDate(3, new Date(e.getCaducidadIns().getTime()));
				pstm.setString(4, e.getLote());
				pstm.setDouble(5, e.getUnidades().doubleValue());
				pstm.setDouble(6, e.getCapacidad().doubleValue());
				pstm.setString(7, e.getG_ml());
				pstm.setString(8, Boolean.toString(e.isResiduo()));

				pstm.executeUpdate();

			} catch (SQLException ex) {
				printSQLException(ex, "INSERTAR ENTRADA");
			} finally {
				cerrarRsPstm(null, pstm, "insertareNTRADA");
			}
		} else {
			correcto = updateEntrada(e);
		}
		return correcto;
	}

	public boolean updateCentro(Centro c) {
		PreparedStatement pstm = null;
		boolean correcto = false;
		try {
			pstm = conexion.prepareStatement("UPDATE centro SET nombre = ? WHERE codcentro = ?");
			pstm.setString(1, c.getNombre());
			pstm.setInt(2, c.getCodcentro());
			pstm.executeUpdate();

		} catch (SQLException e) {
			printSQLException(e, "UPDATE CENTRO");
		} finally {
			cerrarRsPstm(null, pstm, "updateCentro");
		}
		return correcto;
	}

	public boolean updateDepartamento(Departamento d) {
		PreparedStatement pstm = null;
		boolean correcto = false;
		try {
			pstm = conexion.prepareStatement("UPDATE dpto SET nombre = ? WHERE coddpto = ?");
			pstm.setString(1, d.getNombre());
			pstm.setInt(2, d.getCoddpto());
			pstm.executeUpdate();

		} catch (SQLException e) {
			printSQLException(e, "UPDATE DEPARTAMENTO");
		} finally {
			cerrarRsPstm(null, pstm, "updateDepartamento");
		}
		return correcto;
	}

	public boolean updateArea(Area a) {
		PreparedStatement pstm = null;
		boolean correcto = false;
		try {
			pstm = conexion.prepareStatement(
					"UPDATE area SET nombre = ?, dpto=(SELECT coddpto FROM dpto WHERE nombre=?) WHERE codarea = ?");
			pstm.setString(1, a.getNombre());
			pstm.setString(2, a.getDpto());
			pstm.setInt(3, a.getCodarea());
			pstm.executeUpdate();

		} catch (SQLException e) {
			printSQLException(e, "UPDATE AREA");
		} finally {
			cerrarRsPstm(null, pstm, "updateArea");
		}
		return correcto;
	}

	public boolean updateUsuario(Usuario u) {
		PreparedStatement pstm = null;
		boolean correcto = false;
		try {
			pstm = conexion.prepareStatement(
					"UPDATE usuarios SET usuario = ?, nombre = ?, mail = ?, rol=?, area=(SELECT codarea FROM area WHERE nombre=?), federada=?, activo=? WHERE idusuario= ?");
			pstm.setString(1, u.getUsuario());
			pstm.setString(2, u.getNombre());
			pstm.setString(3, u.getMail());
			pstm.setInt(4, u.getRol().getId());
			pstm.setString(5, u.getArea());
			pstm.setString(6, String.valueOf(u.getFederada()));
			pstm.setString(7, String.valueOf(u.getActivo()));
			pstm.setInt(8, u.getIdusuario());
			pstm.executeUpdate();

		} catch (SQLException e) {
			printSQLException(e, "UPDATE USUARIO");
		} finally {
			cerrarRsPstm(null, pstm, "updateUsuario");
		}
		return correcto;
	}
	
	public boolean updateEntrada(Entrada e) {
		PreparedStatement pstm = null;
		boolean correcto = false;
		try {
			pstm = conexion.prepareStatement(
					"UPDATE entrada SET fecha = ?, fechacaducidad = ?, lote = ?, unidades=?, capacidad=?, g_ml=?, residuo=? WHERE codentrada= ?");
			pstm.setDate(1, e.getFechaIns());
			pstm.setDate(2, e.getCaducidadIns());
			pstm.setString(3, e.getLote());
			pstm.setBigDecimal(4, e.getUnidades());
			pstm.setBigDecimal(5, e.getCapacidad());
			pstm.setString(6, e.getG_ml());
			pstm.setString(7, String.valueOf(e.isResiduo()));
			pstm.setInt(8, e.getCodentrada());
			
			pstm.executeUpdate();

		} catch (SQLException ex) {
			printSQLException(ex, "UPDATE ENTRADA");
		} finally {
			cerrarRsPstm(null, pstm, "updateEntrada");
		}
		return correcto;
	}

	public boolean cambiarContrasena(Usuario u, String p) {
		PreparedStatement pstm = null;
		boolean correcto = false;
		try {
			pstm = conexion.prepareStatement("UPDATE usuarios SET contrasena = ? WHERE idusuario= ?");
			pstm.setString(1, p);
			pstm.setInt(2, u.getIdusuario());
			pstm.executeUpdate();

		} catch (SQLException e) {
			printSQLException(e, "CAMBIAR CONTRASEÑA");
		} finally {
			cerrarRsPstm(null, pstm, "cambiarContrasena");
		}
		return correcto;
	}

	public boolean resetearContrasena(Usuario u) {
		PreparedStatement pstm = null;
		boolean correcto = false;
		try {
			pstm = conexion.prepareStatement("UPDATE usuarios SET contrasena = ? WHERE idusuario= ?");
			pstm.setString(1, encriptar(u.getUsuario()));
			pstm.setInt(2, u.getIdusuario());
			pstm.executeUpdate();

		} catch (SQLException e) {
			printSQLException(e, "RESETEAR CONTRASEÑA");
		} finally {
			cerrarRsPstm(null, pstm, "resetearContrasena");
		}
		return correcto;
	}

	public static void printSQLException(SQLException ex, String mensaje) {

		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				if (ignoreSQLException(((SQLException) e).getSQLState()) == false) {
					// e.printStackTrace(System.err);
					System.err.println("-------------------------------------------------");
					System.err.println("- ERROR EN: " + mensaje);
					System.err.println("  >> SQLState: " + ((SQLException) e).getSQLState());
					System.err.println("  >> Código error: " + ((SQLException) e).getErrorCode());
					System.err.println("  >> Mensaje: " + e.getMessage());
					System.err.println("-------------------------------------------------");
					Throwable t = ex.getCause();
					while (t != null) {
						System.out.println("Cause: " + t);
						t = t.getCause();
					}
				}
			}
		}
	}

	public static boolean ignoreSQLException(String sqlState) {
		if (sqlState == null) {
			System.out.println("The SQL state is not defined!");
			return false;
		}
		// X0Y32: Jar file already exists in schema
		if (sqlState.equalsIgnoreCase("X0Y32"))
			return true;
		// 42Y55: Table already exists in schema
		if (sqlState.equalsIgnoreCase("42Y55"))
			return true;
		return false;
	}

}
