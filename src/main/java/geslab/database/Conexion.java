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

//	public String desencriptar(String strToDecrypt) {
//		try {
//			byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
//			IvParameterSpec ivspec = new IvParameterSpec(iv);
//
//			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
//			KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), 65536, 256);
//			SecretKey tmp = factory.generateSecret(spec);
//			SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
//
//			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
//			cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
//			return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
//		} catch (Exception e) {
//			System.out.println("Error while decrypting: " + e.toString());
//		}
//		return null;
//	}

	public Usuario existeUsuario(String u, String p) throws Exception {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Usuario usuario = null;

		try {
			pstm = conexion.prepareStatement("select * from usuarios where usuario = ? and contrasena = ? ");
			pstm.setString(1, u);
			pstm.setString(2, p);
			rs = pstm.executeQuery();
			if (rs.next()) {
				usuario = leerUsuario(u);
			}else {
				throw new Exception("Datos incorrectos");
			}

		} catch (SQLException e) {
			printSQLException(e, "EXISTE USUARIO");
			throw new Exception("Error al comprobar si EXISTE el USUARIO");
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
			pstm = conexion.prepareStatement("select * from ficha where producto = ? AND capacidad = ? AND "
					+ "g_ml = ? AND calidad = (select codcalidad from calidad where nombre = ?) AND "
					+ "ubicacion = ? AND marca = (select codmarca from marca where nombre = ?) AND "
					+ "proveedor = (select codproveedor from proveedor where nombre = ?) AND "
					+ "fechacaducidad = ? AND lote = ? AND residuo = ?");
			pstm.setString(1, f.getProducto().getCas());
			pstm.setDouble(2, f.getCapacidad().doubleValue());
			pstm.setString(3, f.getG_ml());
			pstm.setString(4, f.getCalidad());
			pstm.setInt(5, f.getUbicacion().getCodubicacion());
			pstm.setString(6, f.getMarca());
			pstm.setString(7, f.getProveedor());
			pstm.setDate(8, f.getCaducidadIns());
			pstm.setString(9, f.getLote());
			pstm.setString(10, Boolean.toString(f.isResiduo()));

			rs = pstm.executeQuery();

			if (rs.next()) {
				ficha = new Ficha(rs.getInt("codficha"), f.getProducto(), f.getCapacidad(), f.getG_ml(), f.getCalidad(),
						f.getUbicacion(), f.getMarca(), f.getProveedor(), f.getCaducidadIns(), f.getLote(),
						f.isResiduo(), rs.getInt("stock"));
			}

		} catch (SQLException e) {
			printSQLException(e, "EXISTE FICHA");
		} finally {
			cerrarRsPstm(rs, pstm, "existeFicha");
		}
		return ficha;
	}

	public Boolean existeUbicacion(String nombre, String area, String centro) {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Boolean retorno = false;
		try {
			pstm = conexion.prepareStatement("select * from ubicacion where nombre = ? "
					+ "AND area = (select codarea from area where nombre = ?) "
					+ "AND centro = (select codcentro from centro where nombre = ?)");
			pstm.setString(1, nombre);
			pstm.setString(2, area);
			pstm.setString(3, centro);

			rs = pstm.executeQuery();
			retorno = rs.next();

		} catch (SQLException e) {
			printSQLException(e, "EXISTE UBICACION");
		} finally {
			cerrarRsPstm(rs, pstm, "existeUbicacion");
		}
		return retorno;
	}

	public Boolean existeCalidad(String nombre) {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Boolean retorno = false;
		try {
			pstm = conexion.prepareStatement("select * from calidad where nombre = ?");
			pstm.setString(1, nombre);
			rs = pstm.executeQuery();
			retorno = rs.next();

		} catch (SQLException e) {
			printSQLException(e, "EXISTE CALIDAD");
		} finally {
			cerrarRsPstm(rs, pstm, "existeCalidad");
		}
		return retorno;
	}

	public Boolean existeMarca(String nombre) {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Boolean retorno = false;
		try {
			pstm = conexion.prepareStatement("select * from marca where nombre = ?");
			pstm.setString(1, nombre);
			rs = pstm.executeQuery();
			retorno = rs.next();

		} catch (SQLException e) {
			printSQLException(e, "EXISTE MARCA");
		} finally {
			cerrarRsPstm(rs, pstm, "existeMarca");
		}
		return retorno;
	}
	
	public Boolean existeProveedor(String nombre) {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Boolean retorno = false;
		try {
			pstm = conexion.prepareStatement("select * from proveedor where nombre = ?");
			pstm.setString(1, nombre);
			rs = pstm.executeQuery();
			retorno = rs.next();

		} catch (SQLException e) {
			printSQLException(e, "EXISTE PROVEEDOR");
		} finally {
			cerrarRsPstm(rs, pstm, "existeProveedor");
		}
		return retorno;
	}

	public Usuario leerUsuario(String u) throws Exception {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Usuario usuario = null;

		try {
			pstm = conexion.prepareStatement(
					"SELECT usuarios.idusuario, usuarios.usuario, usuarios.nombre, usuarios.mail, usuarios.rol, area.nombre AS area, usuarios.federada, usuarios.activo, usuarios.fecha_creacion "
							+ "FROM usuarios left JOIN area ON usuarios.area = area.codarea WHERE usuario = ?;");

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
			throw new Exception("Error al LEER el USUARIO");
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
			pstm = conexion.prepareStatement("SELECT producto.*, nombre_producto.nombre from producto "
					+ "inner join nombre_producto on producto.cas = nombre_producto.cas where producto.cas=?;");
			pstm.setString(1, cas);
			rs = pstm.executeQuery();

			if (rs.next()) {
				String formula = rs.getString("formula");
				String formula_des = rs.getString("form_desarrollada");
				String n_einecs = rs.getString("numero_einecs");
				BigDecimal peso_mol = rs.getBigDecimal("peso_mol");
				String n_ec = rs.getString("numero_ec");
				String precauciones = rs.getString("precauciones");
				String nombre = rs.getString("nombre");
				String msds = rs.getString("msds");
				ArrayList<Peligro> peligros = leerPeligrosProducto(cas);
				ArrayList<Prudencia> prudencias = leerPrudenciasProducto(cas);
				ArrayList<Pictograma> pictogramas = leerPictogramasProducto(cas);

				producto = new Producto(cas, nombre, formula, formula_des, peso_mol, n_einecs, n_ec, precauciones, msds,
						peligros, prudencias, pictogramas);
			}
		} catch (SQLException e) {
			printSQLException(e, "LEER PRODUCTO");
		} finally {
			cerrarRsPstm(rs, pstm, "leerProducto");
		}

		return producto;

	}

	private ArrayList<Peligro> leerPeligrosProducto(String cas) {
		PreparedStatement pstm = null;
		ResultSet rs = null;

		ArrayList<Peligro> peligros = new ArrayList<Peligro>();

		try {
			pstm = conexion.prepareStatement(
					"select peligro.frase as frase, peligro.indicacion as indicacion from peligro_producto "
							+ "inner join peligro on peligro_producto.frase = peligro.frase "
							+ "where peligro_producto.cas = ?");
			pstm.setString(1, cas);
			rs = pstm.executeQuery();

			while (rs.next()) {
				String frase = rs.getString("frase");
				String indicacion = rs.getString("indicacion");
				peligros.add(new Peligro(frase, indicacion));
			}

		} catch (SQLException e) {
			printSQLException(e, "LEER PELIGROS PRODUCTO");

		} finally {
			cerrarRsPstm(rs, pstm, "leerPeligrosProducto");
		}

		return peligros;
	}

	private ArrayList<Prudencia> leerPrudenciasProducto(String cas) {
		PreparedStatement pstm = null;
		ResultSet rs = null;

		ArrayList<Prudencia> prudencias = new ArrayList<Prudencia>();

		try {
			pstm = conexion.prepareStatement(
					"select prudencia.frase as frase, prudencia.consejo as consejo from prudencia_producto "
							+ "inner join prudencia on prudencia_producto.frase = prudencia.frase "
							+ "where prudencia_producto.cas = ?");
			pstm.setString(1, cas);
			rs = pstm.executeQuery();

			while (rs.next()) {
				String frase = rs.getString("frase");
				String consejo = rs.getString("consejo");
				prudencias.add(new Prudencia(frase, consejo));
			}

		} catch (SQLException e) {
			printSQLException(e, "LEER PRUDENCIAS PRODUCTO");

		} finally {
			cerrarRsPstm(rs, pstm, "leerPrudenciasProducto");
		}

		return prudencias;
	}

	private ArrayList<Pictograma> leerPictogramasProducto(String cas) {
		PreparedStatement pstm = null;
		ResultSet rs = null;

		ArrayList<Pictograma> pictogramas = new ArrayList<Pictograma>();

		try {
			pstm = conexion.prepareStatement(
					"select pictograma.referencia as referencia, pictograma.descripcion as descripcion from pictograma_producto "
							+ "inner join pictograma on pictograma_producto.referencia = pictograma.referencia "
							+ "where pictograma_producto.cas = ?");
			pstm.setString(1, cas);
			rs = pstm.executeQuery();

			while (rs.next()) {
				String referencia = rs.getString("referencia");
				String descripcion = rs.getString("descripcion");
				pictogramas.add(new Pictograma(referencia, descripcion));
			}

		} catch (SQLException e) {
			printSQLException(e, "LEER PICTOGRAMAS PRODUCTO");

		} finally {
			cerrarRsPstm(rs, pstm, "leerPictogramasProducto");
		}

		return pictogramas;
	}

	public Ubicacion leerUbicacion(int codubicacion) {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Ubicacion ubicacion = null;

		try {
			pstm = conexion.prepareStatement(
					"SELECT ubicacion.nombre, area.nombre as area, dpto.nombre as dpto, centro.nombre as centro, oculta "
							+ "from ubicacion inner join area on ubicacion.area = area.codarea "
							+ "inner join dpto on area.dpto = dpto.coddpto "
							+ "inner join centro on ubicacion.centro = centro.codcentro where codubicacion = ?");
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

//	public Boolean leerCalidad(int codcalidad) {
//		PreparedStatement pstm = null;
//		ResultSet rs = null;
//		Boolean retorno = false;
//		
//		try {
//			pstm = conexion.prepareStatement(
//					"SELECT * from calidad where codcalidad = ?");
//			pstm.setInt(1, codcalidad);
//			rs = pstm.executeQuery();
//
//			retorno = rs.next();
//		} catch (SQLException e) {
//			printSQLException(e, "LEER CALIDAD");
//		} finally {
//			cerrarRsPstm(rs, pstm, "leerCalidad");
//		}
//		return retorno;
//	}

	public Marca leerMarca(int codmarca) {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Marca marca = null;

		try {
			pstm = conexion.prepareStatement("SELECT * from marca where codmarca = ?");
			pstm.setInt(1, codmarca);
			rs = pstm.executeQuery();

			if (rs.next()) {
				String nombre = rs.getString("nombre");
				String tlf = rs.getString("telefono");
				String direccion = rs.getString("direccion");
				ArrayList<String> proveedores = leerProovedoresMarca(codmarca);

				marca = new Marca(codmarca, nombre, tlf, direccion, proveedores);
			}
		} catch (SQLException e) {
			printSQLException(e, "LEER MARCA");
		} finally {
			cerrarRsPstm(rs, pstm, "leerMarca");
		}
		return marca;
	}
	
	public Proveedor leerProveedor(int codprov) {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Proveedor proveedor = null;
		
		try {
			pstm = conexion.prepareStatement(
					"SELECT * from proveedor where codproveedor = ?");
			pstm.setInt(1, codprov);
			rs = pstm.executeQuery();

			if (rs.next()) {
				String nombre = rs.getString("nombre");
				String direccion = rs.getString("direccion");
				String tlf = rs.getString("tfno");
				String fax = rs.getString("fax");
				String mail = rs.getString("email");
				ArrayList<String> marcas = leerMarcasProveedor(codprov);

				proveedor = new Proveedor(codprov, nombre, direccion, tlf, fax, mail, marcas);
			}
		} catch (SQLException e) {
			printSQLException(e, "LEER PROVEEDOR");
		} finally {
			cerrarRsPstm(rs, pstm, "leerProveedor");
		}
		return proveedor;
	}
		
	public Calidad leerCalidad(int codcalidad) {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Calidad calidad = null;

		try {
			pstm = conexion.prepareStatement("SELECT * from calidad where codcalidad = ?");
			pstm.setInt(1, codcalidad);
			rs = pstm.executeQuery();

			if (rs.next()) {
				String nombre = rs.getString("nombre");
				
				calidad = new Calidad(codcalidad, nombre);
			}
		} catch (SQLException e) {
			printSQLException(e, "LEER CALIDAD");
		} finally {
			cerrarRsPstm(rs, pstm, "leerCalidad");
		}
		return calidad;
	}

	public Ficha leerFicha(int cod) throws Exception {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Ficha ficha = null;

		try {
			pstm = conexion.prepareStatement("select codficha, capacidad, g_ml, fechacaducidad, lote, residuo, "
					+ "stock, calidad.nombre AS calidad, ubicacion, proveedor.nombre AS proveedor, marca.nombre AS marca, producto "
					+ "from ficha inner join calidad on ficha.calidad = calidad.codcalidad "
					+ "inner join proveedor on ficha.proveedor = proveedor.codproveedor "
					+ "inner join marca on ficha.marca = marca.codmarca where codficha = ?");
			pstm.setInt(1, cod);
			rs = pstm.executeQuery();

			while (rs.next()) {
				Producto producto = leerProducto(rs.getString("producto"));
				BigDecimal capacidad = rs.getBigDecimal("capacidad");
				String g_ml = rs.getString("g_ml");
				String calidad = rs.getString("calidad");
				Ubicacion ubicacion = leerUbicacion(rs.getInt("ubicacion"));
				String marca = rs.getString("marca");
				String proveedor = rs.getString("proveedor");
				Date caducidad = rs.getDate("fechacaducidad");
				String lote = rs.getString("lote");
				Boolean residuo = rs.getBoolean("residuo");
				int stock = rs.getInt("stock");

				ficha = new Ficha(cod, producto, capacidad, g_ml, calidad, ubicacion, marca, proveedor, caducidad, lote,
						residuo, stock);
			}

		} catch (SQLException e) {
			printSQLException(e, "LEER FICHA");
			throw new Exception("Error al LEER FICHA #" + cod);

		} finally {
			cerrarRsPstm(rs, pstm, "leerFicha");
		}

		return ficha;
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
					"SELECT area.codarea, area.nombre AS Area, area.dpto AS coddpto, dpto.nombre AS Departamento "
							+ "FROM area INNER JOIN dpto ON area.dpto = dpto.coddpto;");
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

	public ArrayList<Usuario> leerUsuarios() throws Exception {
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

	public ArrayList<Peligro> leerPeligros() {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<Peligro> peligros = new ArrayList<Peligro>();

		try {
			pstm = conexion.prepareStatement("select * from peligro");
			rs = pstm.executeQuery();

			while (rs.next()) {
				String frase = rs.getString("frase");
				String indicacion = rs.getString("indicacion");
				peligros.add(new Peligro(frase, indicacion));
			}

		} catch (SQLException e) {
			printSQLException(e, "LEER PELIGROS");

		} finally {
			cerrarRsPstm(rs, pstm, "leerPeligros");
		}

		return peligros;
	}

	public ArrayList<Prudencia> leerPrudencias() {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<Prudencia> prudencias = new ArrayList<Prudencia>();

		try {
			pstm = conexion.prepareStatement("select * from prudencia");
			rs = pstm.executeQuery();

			while (rs.next()) {
				String frase = rs.getString("frase");
				String consejo = rs.getString("consejo");
				prudencias.add(new Prudencia(frase, consejo));
			}

		} catch (SQLException e) {
			printSQLException(e, "LEER PRUDENCIAS");

		} finally {
			cerrarRsPstm(rs, pstm, "leerPrudencias");
		}

		return prudencias;
	}

	public ArrayList<Pictograma> leerPictogramas() {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<Pictograma> pictogramas = new ArrayList<Pictograma>();

		try {
			pstm = conexion.prepareStatement("select * from pictograma");
			rs = pstm.executeQuery();

			while (rs.next()) {
				String referencia = rs.getString("referencia");
				String descripcion = rs.getString("descripcion");
				pictogramas.add(new Pictograma(referencia, descripcion));
			}

		} catch (SQLException e) {
			printSQLException(e, "LEER PICTOGRAMAS");

		} finally {
			cerrarRsPstm(rs, pstm, "leerPictogramas");
		}

		return pictogramas;
	}

	public ArrayList<Ficha> leerFichas(Usuario usuario) {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<Ficha> fichas = new ArrayList<Ficha>();

		try {
			pstm = conexion.prepareStatement("select codficha, capacidad, g_ml, fechacaducidad, lote, residuo, stock, "
					+ "calidad.nombre AS calidad, ubicacion, proveedor.nombre AS proveedor, marca.nombre AS marca, producto "
					+ "from ficha inner join calidad on ficha.calidad = calidad.codcalidad "
					+ "inner join proveedor on ficha.proveedor = proveedor.codproveedor "
					+ "inner join marca on ficha.marca = marca.codmarca "
					+ "inner join ubicacion on ficha.ubicacion = ubicacion.codubicacion "
					+ "where (ubicacion.area != (select codarea from area where nombre = ?) and ubicacion.oculta = 'false') "
					+ "or (ubicacion.area = (select codarea from area where nombre = ?))");
			pstm.setString(1, usuario.getArea());
			pstm.setString(2, usuario.getArea());
			rs = pstm.executeQuery();

			while (rs.next()) {
				int cod = rs.getInt("codficha");
				Producto producto = leerProducto(rs.getString("producto"));
				BigDecimal capacidad = rs.getBigDecimal("capacidad");
				String g_ml = rs.getString("g_ml");
				String calidad = rs.getString("calidad");
				Ubicacion ubicacion = leerUbicacion(rs.getInt("ubicacion"));
				String marca = rs.getString("marca");
				String proveedor = rs.getString("proveedor");
				Date caducidad = rs.getDate("fechacaducidad");
				String lote = rs.getString("lote");
				Boolean residuo = rs.getBoolean("residuo");
				int stock = rs.getInt("stock");

				fichas.add(new Ficha(cod, producto, capacidad, g_ml, calidad, ubicacion, marca, proveedor, caducidad,
						lote, residuo, stock));
			}

		} catch (SQLException e) {
			printSQLException(e, "LEER FICHAS");

		} finally {
			cerrarRsPstm(rs, pstm, "leerFichas");
		}

		return fichas;
	}

	public ArrayList<Ubicacion> leerUbicaciones(Usuario usuario) {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<Ubicacion> ubicaciones = new ArrayList<Ubicacion>();

		try {
//			pstm = conexion.prepareStatement("select codubicacion from ubicacion");
			pstm = conexion.prepareStatement("select codubicacion from ubicacion " + 
					"inner join area on ubicacion.area = area.codarea " + 
					"where (area.nombre != ? and ubicacion.oculta = 'false') or (area.nombre = ?)");
			pstm.setString(1, usuario.getArea());
			pstm.setString(2, usuario.getArea());
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
				ArrayList<String> marcas = leerMarcasProveedor(cod);

				proveedores.add(new Proveedor(cod, nombre, direccion, telefono, fax, mail, marcas));
			}

		} catch (SQLException e) {
			printSQLException(e, "LEER PROVEEDORES");

		} finally {
			cerrarRsPstm(rs, pstm, "leerProveedores");
		}

		return proveedores;
	}

	public ArrayList<String> leerMarcasProveedor(int cod) {
		PreparedStatement pstm = null;
		ResultSet rs = null;

		ArrayList<String> marcas = new ArrayList<String>();

		try {
			pstm = conexion
					.prepareStatement("select marca.nombre as marca, proveedor.nombre as proveedor from prov_marca "
							+ "inner join proveedor on proveedor.codproveedor = prov_marca.proveedor "
							+ "inner join marca on marca.codmarca = prov_marca.marca "
							+ "where proveedor.codproveedor = ?");
			pstm.setInt(1, cod);
			rs = pstm.executeQuery();

			while (rs.next()) {
				marcas.add(rs.getString("marca"));
			}

		} catch (SQLException e) {
			printSQLException(e, "LEER MARCAS PROVEEDOR");

		} finally {
			cerrarRsPstm(rs, pstm, "leerMarcasProveedor");
		}

		return marcas;
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
				String telefono = rs.getString("telefono");
				String direccion = rs.getString("direccion");
				ArrayList<String> proveedores = leerProovedoresMarca(cod);

				marcas.add(new Marca(cod, nombre, telefono, direccion, proveedores));
			}

		} catch (SQLException e) {
			printSQLException(e, "LEER MARCAS");

		} finally {
			cerrarRsPstm(rs, pstm, "leerMarcas");
		}

		return marcas;
	}

	public ArrayList<String> leerProovedoresMarca(int cod) {
		PreparedStatement pstm = null;
		ResultSet rs = null;

		ArrayList<String> proveedores = new ArrayList<String>();

		try {
			pstm = conexion
					.prepareStatement("select marca.nombre as marca, proveedor.nombre as proveedor from prov_marca "
							+ "inner join proveedor on proveedor.codproveedor = prov_marca.proveedor "
							+ "inner join marca on marca.codmarca = prov_marca.marca where marca.codmarca = ?");
			pstm.setInt(1, cod);
			rs = pstm.executeQuery();

			while (rs.next()) {
				proveedores.add(rs.getString("proveedor"));
			}

		} catch (SQLException e) {
			printSQLException(e, "LEER PROVEEDORES MARCA");

		} finally {
			cerrarRsPstm(rs, pstm, "leerProveedoresMarca");
		}

		return proveedores;
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
				Date fecha = rs.getDate("fecha");
				int unidades = rs.getBigDecimal("unidades").intValue();
				int usuario = rs.getInt("usuario");

				entradas.add(new Entrada(codentrada, ficha, fecha, unidades, usuario));
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
			pstm = conexion.prepareStatement("select * from salida where ficha=?");
			pstm.setInt(1, ficha.getCodficha());
			rs = pstm.executeQuery();

			while (rs.next()) {
				int codsalida = rs.getInt("codsalida");
				Date fecha = rs.getDate("fecha");
				int unidades = rs.getBigDecimal("unidades").intValue();
				int usuario = rs.getInt("usuario");

				salidas.add(new Salida(codsalida, ficha, fecha, unidades, usuario));
			}

		} catch (SQLException e) {
			printSQLException(e, "LEER SALIDAS-FICHA");

		} finally {
			cerrarRsPstm(rs, pstm, "leerSalidasFicha");
		}

		return salidas;
	}

	public ArrayList<Entrada> leerEntradas(Usuario usuario) {
		ArrayList<Entrada> entradas = new ArrayList<Entrada>();
		ArrayList<Ficha> fichas = leerFichas(usuario);
		for (Ficha f : fichas) {
			entradas.addAll(leerEntradasFicha(f));
		}
		return entradas;
	}

	public ArrayList<Salida> leerSalidas(Usuario usuario) {
		ArrayList<Salida> salidas = new ArrayList<Salida>();
		ArrayList<Ficha> fichas = leerFichas(usuario);
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
				correcto = pstm.executeUpdate() == 1 ? true : false;

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
				correcto = pstm.executeUpdate() == 1 ? true : false;

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
				correcto = pstm.executeUpdate() == 1 ? true : false;

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
						"INSERT INTO usuarios (usuario, contrasena, rol, area, federada, activo, fecha_creacion) "
								+ "VALUES (?, ?, ?, (SELECT codarea FROM area WHERE nombre=?), ?, ?, NOW())");
				pstm.setString(1, u.getUsuario());
				pstm.setString(2, encriptar(u.getUsuario()));
				pstm.setInt(3, u.getRol().getId());
				pstm.setString(4, u.getArea());
				pstm.setString(5, Boolean.toString(u.getFederada()));
				pstm.setString(6, Boolean.toString(u.getActivo()));
				correcto = pstm.executeUpdate() == 1 ? true : false;

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

	public boolean insertarFicha(Ficha f) throws Exception {
		PreparedStatement pstm = null;
		boolean correcto = false;
		
		if (existeFicha(f) == null) {
			try {
				pstm = conexion.prepareStatement(
						"INSERT INTO ficha(producto, capacidad, g_ml, calidad, ubicacion, marca, proveedor, fechacaducidad, lote, residuo, stock) VALUES "
								+ "(?, ?, ?, (select codcalidad from calidad where nombre = ?), ?, "
								+ "(select codmarca from marca where nombre = ?), "
								+ "(select codproveedor from proveedor where nombre = ?), ?, ?, ?, ?)");
				pstm.setString(1, f.getProducto().getCas());
				pstm.setBigDecimal(2, f.getCapacidad());
				pstm.setString(3, f.getG_ml());
				pstm.setString(4, f.getCalidad());
				pstm.setInt(5, f.getUbicacion().getCodubicacion());
				pstm.setString(6, f.getMarca());
				pstm.setString(7, f.getProveedor());
				pstm.setDate(8, f.getCaducidadIns());
				pstm.setString(9, f.getLote());
				pstm.setString(10, Boolean.toString(f.isResiduo()));
				pstm.setInt(11, f.getStock());
	
				correcto = pstm.executeUpdate() == 1 ? true : false;
	
			} catch (SQLException e) {
				printSQLException(e, "INSERTAR ficha");
				throw new Exception("Error al INSERTAR la ficha");
			} finally {
				cerrarRsPstm(null, pstm, "insertarFicha");
			}
		
		}else {
			throw new Exception("La ficha ya existe");
		}

		return correcto;
	}

	public boolean insertarEntrada(Entrada e) throws Exception {
		PreparedStatement pstm = null;
		boolean correcto = false;
		if (e.getCodentrada() == 0) {
			try {
				pstm = conexion
						.prepareStatement("INSERT INTO entrada (ficha, fecha, unidades, usuario) values (?, ?, ?, ?)");
				pstm.setInt(1, e.getFicha().getCodficha());
				pstm.setDate(2, new Date(e.getFechaIns().getTime()));
				pstm.setInt(3, e.getUnidades());
				pstm.setInt(4, e.getUsuario());
				correcto = pstm.executeUpdate() == 1 ? true : false;

				correcto = actualizarStock(e.getFicha().getCodficha(), e.getUnidades() + e.getFicha().getStock());

			} catch (SQLException ex) {
				printSQLException(ex, "INSERTAR ENTRADA");
				throw new Exception("Error al INSERTAR ENTRADA");
			} finally {
				cerrarRsPstm(null, pstm, "insertarEntrada");
			}
		}else {
			throw new Exception("Código de entrada incorrecto");
		}
		return correcto;
	}

	public boolean insertarSalida(Salida s) throws Exception {
		PreparedStatement pstm = null;
		boolean correcto = false;
		if (s.getCodsalida() == 0) {
			try {
				pstm = conexion
						.prepareStatement("INSERT INTO salida (ficha, fecha, unidades, usuario) values (?, ?, ?, ?)");
				pstm.setInt(1, s.getFicha().getCodficha());
				pstm.setDate(2, new Date(s.getFechaIns().getTime()));
				pstm.setInt(3, s.getUnidades());
				pstm.setInt(4, s.getUsuario());
				correcto = pstm.executeUpdate() == 1 ? true : false;

				correcto = actualizarStock(s.getFicha().getCodficha(), s.getFicha().getStock() - s.getUnidades());

			} catch (SQLException ex) {
				printSQLException(ex, "INSERTAR SALIDA");
				throw new Exception("Error al INSERTAR SALIDA");
			} finally {
				cerrarRsPstm(null, pstm, "insertarSalida");
			}
		}else {
			throw new Exception("Código de salida incorrecto");
		}
		return correcto;
	}

	private boolean actualizarStock(int codficha, int unidades) throws Exception {
		PreparedStatement pstm = null;
		boolean correcto = false;
		try {
			pstm = conexion.prepareStatement("UPDATE ficha SET stock = ? WHERE codficha= ?");
			pstm.setInt(1, unidades);
			pstm.setInt(2, codficha);
			correcto = pstm.executeUpdate() == 1 ? true : false;

		} catch (SQLException ex) {
			printSQLException(ex, "ACTUALIZAR STOCK");
			throw new Exception("Error actualizando stock");
		} finally {
			cerrarRsPstm(null, pstm, "actualizarStock");
		}
		return correcto;

	}

	public boolean insertarProducto(Producto p) throws Exception {
		PreparedStatement pstm = null;
		PreparedStatement pstm2 = null;
		boolean correcto = false;
		
		if (leerProducto(p.getCas()) == null) {
			try {
				pstm = conexion.prepareStatement(
						"INSERT INTO producto (cas, formula, form_desarrollada, peso_mol, numero_einecs, numero_ec, precauciones, msds) "
								+ "values (?, ?, ?, ?, ?, ?, ?, ?)");
				pstm.setString(1, p.getCas());
				pstm.setString(2, p.getFormula());
				pstm.setString(3, p.getFormula_des());
				pstm.setBigDecimal(4, p.getPeso_mol());
				pstm.setString(5, p.getN_einecs());
				pstm.setString(6, p.getN_ec());
				pstm.setString(7, p.getPrecauciones());
				pstm.setString(8, p.getMsds());
	
				correcto = pstm.executeUpdate() == 1 ? true : false;
	
				pstm2 = conexion.prepareStatement("INSERT INTO nombre_producto (cas, nombre) values (?, ?)");
				pstm2.setString(1, p.getCas());
				pstm2.setString(2, p.getNombre());
	
				pstm2.executeUpdate();
	
			} catch (SQLException ex) {
				printSQLException(ex, "INSERTAR PRODUCTO");
			} finally {
				cerrarRsPstm(null, pstm, "insertarProducto");
				cerrarRsPstm(null, pstm2, "insertarNombreProducto");
			}
		}else {
			throw new Exception("El producto ya existe");
		}

		return correcto;
	}

	public boolean insertarCalidad(Calidad c) throws Exception {
		PreparedStatement pstm = null;
		boolean correcto = false;
		if(!existeCalidad(c.getNombre())) {
			try {
				pstm = conexion.prepareStatement("INSERT INTO calidad (nombre) values (?)");
				pstm.setString(1, c.getNombre());
	
				correcto = pstm.executeUpdate() == 1 ? true : false;
	
			} catch (SQLException ex) {
				printSQLException(ex, "INSERTAR CALIDAD");
				throw new Exception("Error insertando calidad");
			} finally {
				cerrarRsPstm(null, pstm, "insertarCalidad");
			}
		}else {
			throw new Exception("La calidad ya existe");
		}

		return correcto;
	}

	public boolean insertarUbicacion(Ubicacion u) throws Exception {
		PreparedStatement pstm = null;
		boolean correcto = false;
		if (!existeUbicacion(u.getNombre(), u.getArea(), u.getCentro())) {
			try {
				pstm = conexion.prepareStatement("INSERT INTO ubicacion (nombre, area, centro, oculta) values "
						+ "(?, (select codarea from area where nombre = ?), "
						+ "(select codcentro from centro where nombre = ?), ?)");
				pstm.setString(1, u.getNombre());
				pstm.setString(2, u.getArea());
				pstm.setString(3, u.getCentro());
				pstm.setString(4, Boolean.toString(u.isOculta()));
	
				correcto = pstm.executeUpdate() == 1 ? true : false;
	
			} catch (SQLException ex) {
				printSQLException(ex, "INSERTAR UBICACION");
				throw new Exception("Error insertando ubicación");
			} finally {
				cerrarRsPstm(null, pstm, "insertarUbicacion");
			}
		}else {
			throw new Exception("La ubicación ya existe");
		}

		return correcto;
	}

	public boolean insertarMarca(Marca m) throws Exception {
		PreparedStatement pstm = null;
		boolean correcto = false;
		if(!existeMarca(m.getNombre())) {
			try {
				pstm = conexion.prepareStatement("INSERT INTO marca (nombre, telefono, direccion) values (?, ?, ?)");
				pstm.setString(1, m.getNombre());
				pstm.setString(2, m.getTelefono());
				pstm.setString(3, m.getDireccion());
				correcto = pstm.executeUpdate() == 1 ? true : false;
	
				for (String p : m.getProveedores()) {
					insertarProvMarca(p, m.getNombre());
				}
	
			} catch (SQLException ex) {
				printSQLException(ex, "INSERTAR MARCA");
				throw new Exception("Error insertando marca");
			} finally {
				cerrarRsPstm(null, pstm, "insertarMarca");
			}
		}else {
			throw new Exception("La marca ya existe");
		}

		return correcto;
	}

	public boolean insertarProveedor(Proveedor p) throws Exception {
		PreparedStatement pstm = null;
		boolean correcto = false;
		if(!existeProveedor(p.getNombre())) {
			try {
				pstm = conexion.prepareStatement(
						"INSERT INTO proveedor (nombre, direccion, tfno, fax, email) values (?, ?, ?, ?, ?)");
				pstm.setString(1, p.getNombre());
				pstm.setString(2, p.getDireccion());
				pstm.setString(3, p.getTelefono());
				pstm.setString(4, p.getFax());
				pstm.setString(5, p.getEmail());
				correcto = pstm.executeUpdate() == 1 ? true : false;
	
				for (String m : p.getMarcas()) {
					insertarProvMarca(p.getNombre(), m);
				}
	
			} catch (SQLException ex) {
				printSQLException(ex, "INSERTAR PROVEEDOR");
				throw new Exception("Error insertando proveedor");
			} finally {
				cerrarRsPstm(null, pstm, "insertarProveedor");
			}
		} else {
			throw new Exception("El proveedor ya existe");
		}

		return correcto;
	}

	public boolean insertarProvMarca(String p, String m) throws Exception {
		PreparedStatement pstm = null;
		boolean correcto = false;
		try {
			pstm = conexion.prepareStatement("INSERT INTO prov_marca (proveedor, marca) values "
					+ "((select codproveedor from proveedor where nombre=?), (select codmarca from marca where nombre=?))");
			pstm.setString(1, p);
			pstm.setString(2, m);

			correcto = pstm.executeUpdate() == 1 ? true : false;

		} catch (SQLException ex) {
			printSQLException(ex, "INSERTAR ProvMarca");
			throw new Exception("Error asignando proveedor-marca");
		} finally {
			cerrarRsPstm(null, pstm, "insertarProvMarca");
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
			correcto = pstm.executeUpdate() == 1 ? true : false;

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
			correcto = pstm.executeUpdate() == 1 ? true : false;

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
			correcto = pstm.executeUpdate() == 1 ? true : false;

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
			pstm = conexion.prepareStatement("UPDATE usuarios SET usuario = ?, nombre = ?, mail = ?, rol=?, "
					+ "area=(SELECT codarea FROM area WHERE nombre=?), federada=?, activo=? WHERE idusuario= ?");
			pstm.setString(1, u.getUsuario());
			pstm.setString(2, u.getNombre());
			pstm.setString(3, u.getMail());
			pstm.setInt(4, u.getRol().getId());
			pstm.setString(5, u.getArea());
			pstm.setString(6, String.valueOf(u.getFederada()));
			pstm.setString(7, String.valueOf(u.getActivo()));
			pstm.setInt(8, u.getIdusuario());
			correcto = pstm.executeUpdate() == 1 ? true : false;

		} catch (SQLException e) {
			printSQLException(e, "UPDATE USUARIO");
		} finally {
			cerrarRsPstm(null, pstm, "updateUsuario");
		}
		return correcto;
	}

	public boolean updateProducto(Producto producto) {
		PreparedStatement pstm = null;
		boolean correcto = false;
		try {
			pstm = conexion.prepareStatement(
					"UPDATE producto set formula = ?, form_desarrollada = ?, peso_mol = ?, numero_einecs = ?, "
							+ "numero_ec = ?, precauciones = ?, msds = ? WHERE cas = ?");

			pstm.setString(1, producto.getFormula());
			pstm.setString(2, producto.getFormula_des());
			pstm.setBigDecimal(3, producto.getPeso_mol());
			pstm.setString(4, producto.getN_einecs());
			pstm.setString(5, producto.getN_ec());
			pstm.setString(6, producto.getPrecauciones());
			pstm.setString(7, producto.getMsds());
			pstm.setString(8, producto.getCas());
			correcto = pstm.executeUpdate() == 1 ? true : false;

			updatePeligroProducto(producto);
			updatePrudenciaProducto(producto);
			updatePictogramaProducto(producto);

		} catch (SQLException ex) {
			printSQLException(ex, "UPDATE PRODUCTO");
		} finally {
			cerrarRsPstm(null, pstm, "updateProducto");
		}

		return correcto;
	}

	public boolean updatePeligroProducto(Producto producto) throws SQLException {
		boolean correcto = false;
		String cas = producto.getCas();
		ArrayList<String> origenPel = leerProducto(cas).getFrasesPeligro();
		ArrayList<String> updatePel = producto.getFrasesPeligro();
		ArrayList<String> eliminarPel = new ArrayList<String>(origenPel);
		ArrayList<String> añadirPel = new ArrayList<String>(updatePel);
		eliminarPel.removeAll(updatePel);
		añadirPel.removeAll(origenPel);

		for (String p : eliminarPel) {
			PreparedStatement pstm = conexion
					.prepareStatement("DELETE FROM peligro_producto WHERE cas = ? and frase = ?");
			pstm.setString(1, cas);
			pstm.setString(2, p);
			correcto = pstm.executeUpdate() == 1 ? true : false;
			cerrarRsPstm(null, pstm, "updatePeligroProducto");
		}

		for (String p : añadirPel) {
			PreparedStatement pstm = conexion
					.prepareStatement("INSERT INTO peligro_producto (cas, frase) values (?, ?)");
			pstm.setString(1, cas);
			pstm.setString(2, p);
			pstm.executeUpdate();
			cerrarRsPstm(null, pstm, "updatePeligroProducto");
		}

		return correcto;
	}

	public boolean updatePrudenciaProducto(Producto producto) throws SQLException {
		boolean correcto = false;
		String cas = producto.getCas();
		ArrayList<String> origenPru = leerProducto(cas).getFrasesPrudencia();
		ArrayList<String> updatePru = producto.getFrasesPrudencia();
		ArrayList<String> eliminarPru = new ArrayList<String>(origenPru);
		ArrayList<String> añadirPru = new ArrayList<String>(updatePru);
		eliminarPru.removeAll(updatePru);
		añadirPru.removeAll(origenPru);

		for (String p : eliminarPru) {
			PreparedStatement pstm = conexion
					.prepareStatement("DELETE FROM prudencia_producto WHERE cas = ? and frase = ?");
			pstm.setString(1, cas);
			pstm.setString(2, p);
			correcto = pstm.executeUpdate() == 1 ? true : false;
			cerrarRsPstm(null, pstm, "updatePrudenciaProducto");
		}

		for (String p : añadirPru) {
			PreparedStatement pstm = conexion
					.prepareStatement("INSERT INTO prudencia_producto (cas, frase) values (?, ?)");
			pstm.setString(1, cas);
			pstm.setString(2, p);
			pstm.executeUpdate();
			cerrarRsPstm(null, pstm, "updatePrudenciaProducto");
		}

		return correcto;
	}

	public boolean updatePictogramaProducto(Producto producto) throws SQLException {
		boolean correcto = false;
		String cas = producto.getCas();
		ArrayList<String> origenPic = leerProducto(cas).getReferenciasPictograma();
		ArrayList<String> updatePic = producto.getReferenciasPictograma();
		ArrayList<String> eliminarPic = new ArrayList<String>(origenPic);
		ArrayList<String> añadirPic = new ArrayList<String>(updatePic);
		eliminarPic.removeAll(updatePic);
		añadirPic.removeAll(origenPic);

		for (String p : eliminarPic) {
			PreparedStatement pstm = conexion
					.prepareStatement("DELETE FROM pictograma_producto WHERE cas = ? and referencia = ?");
			pstm.setString(1, cas);
			pstm.setString(2, p);
			correcto = pstm.executeUpdate() == 1 ? true : false;
			cerrarRsPstm(null, pstm, "updatePictogramaProducto");
		}

		for (String p : añadirPic) {
			PreparedStatement pstm = conexion
					.prepareStatement("INSERT INTO pictograma_producto (cas, referencia) values (?, ?)");
			pstm.setString(1, cas);
			pstm.setString(2, p);
			pstm.executeUpdate();
			cerrarRsPstm(null, pstm, "updatePictogramaProducto");
		}

		return correcto;
	}

	public boolean updateUbicacion(Ubicacion u) throws Exception {
		PreparedStatement pstm = null;
		boolean correcto = false;
		try {
			pstm = conexion.prepareStatement(
					"UPDATE ubicacion SET nombre = ?, area=(SELECT codarea FROM area WHERE nombre=?), "
							+ "centro = (SELECT codcentro FROM centro WHERE nombre=?), oculta = ? WHERE codubicacion = ?");
			pstm.setString(1, u.getNombre());
			pstm.setString(2, u.getArea());
			pstm.setString(3, u.getCentro());
			pstm.setString(4, Boolean.toString(u.isOculta()));
			pstm.setInt(5, u.getCodubicacion());
			correcto = pstm.executeUpdate() == 1 ? true : false;

		} catch (SQLException e) {
			printSQLException(e, "UPDATE UBICACION");
			throw new Exception("Error actualizando ubicación");
		} finally {
			cerrarRsPstm(null, pstm, "updateUbicacion");
		}
		return correcto;
	}

	public boolean updateCalidad(Calidad c) throws Exception {
		PreparedStatement pstm = null;
		boolean correcto = false;
		try {
			pstm = conexion.prepareStatement("UPDATE calidad SET nombre = ? WHERE codcalidad = ?");
			pstm.setString(1, c.getNombre());
			pstm.setInt(2, c.getCodcalidad());
			correcto = pstm.executeUpdate() == 1 ? true : false;

		} catch (SQLException e) {
			printSQLException(e, "UPDATE CALIDAD");
			throw new Exception("Error actualizando calidad");
		} finally {
			cerrarRsPstm(null, pstm, "updateCalidad");
		}
		return correcto;
	}

	public boolean updateMarca(Marca m) throws Exception {
		PreparedStatement pstm = null;
		boolean correcto = false;
		try {
			pstm = conexion
					.prepareStatement("UPDATE marca SET nombre = ?, telefono = ?, direccion = ? WHERE codmarca = ?");
			pstm.setString(1, m.getNombre());
			pstm.setString(2, m.getTelefono());
			pstm.setString(3, m.getDireccion());
			pstm.setInt(4, m.getCodmarca());
			correcto = pstm.executeUpdate() == 1 ? true : false;

			updateMarcaProvMarca(m);

		} catch (SQLException e) {
			printSQLException(e, "UPDATE MARCA");
			throw new Exception("Error actualizando marca");
		} finally {
			cerrarRsPstm(null, pstm, "updateMarca");
		}

		return correcto;
	}

	private void updateMarcaProvMarca(Marca marca) throws SQLException {
		int cod = marca.getCodmarca();
		ArrayList<String> origenProv = leerMarca(cod).getProveedores();
		ArrayList<String> updateProv = marca.getProveedores();
		ArrayList<String> eliminarProv = new ArrayList<String>(origenProv);
		ArrayList<String> añadirProv = new ArrayList<String>(updateProv);
		eliminarProv.removeAll(updateProv);
		añadirProv.removeAll(origenProv);

		for (String p : eliminarProv) {
			PreparedStatement pstm = conexion
					.prepareStatement("DELETE FROM prov_marca WHERE proveedor = (select codproveedor from proveedor where nombre = ?) "
							+ "and marca = ?");
			pstm.setString(1, p);
			pstm.setInt(2, cod);
			pstm.executeUpdate();
			cerrarRsPstm(null, pstm, "updateMarcaProvMarca");
		}

		for (String p : añadirProv) {
			PreparedStatement pstm = conexion
					.prepareStatement("INSERT INTO prov_marca (proveedor, marca) values ((select codproveedor from proveedor where nombre = ?), ?)");
			pstm.setString(1, p);
			pstm.setInt(2, cod);
			pstm.executeUpdate();
			cerrarRsPstm(null, pstm, "updateMarcaProvMarca");
		}

	}
	
	public boolean updateProveedor(Proveedor p) {
		PreparedStatement pstm = null;
		boolean correcto = false;
		try {
			pstm = conexion
					.prepareStatement("UPDATE proveedor SET nombre = ?, direccion = ?, tfno = ?, fax = ?, email = ? WHERE codproveedor = ?");
			pstm.setString(1, p.getNombre());
			pstm.setString(2, p.getDireccion());
			pstm.setString(3, p.getTelefono());
			pstm.setString(4, p.getFax());
			pstm.setString(5, p.getEmail());
			pstm.setInt(6, p.getCodproveedor());
			correcto = pstm.executeUpdate() == 1 ? true : false;

			updateMarcaProvMarca(p);

		} catch (SQLException e) {
			printSQLException(e, "UPDATE PROVEEDOR");
		} finally {
			cerrarRsPstm(null, pstm, "updateProveedor");
		}
		return correcto;
	}
	
	private void updateMarcaProvMarca(Proveedor proveedor) throws SQLException {
		int cod = proveedor.getCodproveedor();
		ArrayList<String> origenMarcas = leerProveedor(cod).getMarcas();
		ArrayList<String> updateMarcas = proveedor.getMarcas();
		ArrayList<String> eliminarMarcas = new ArrayList<String>(origenMarcas);
		ArrayList<String> añadirMarcas = new ArrayList<String>(updateMarcas);
		eliminarMarcas.removeAll(updateMarcas);
		añadirMarcas.removeAll(origenMarcas);

		for (String m : eliminarMarcas) {
			PreparedStatement pstm = conexion
					.prepareStatement("DELETE FROM prov_marca WHERE marca = (select codmarca from marca where nombre = ?) "
							+ "and proveedor = ?");
			pstm.setString(1, m);
			pstm.setInt(2, cod);
			pstm.executeUpdate();
			cerrarRsPstm(null, pstm, "updateProveedorProvMarca");
		}

		for (String m : añadirMarcas) {
			PreparedStatement pstm = conexion
					.prepareStatement("INSERT INTO prov_marca (marca, proveedor) values ((select codmarca from marca where nombre = ?), ?)");
			pstm.setString(1, m);
			pstm.setInt(2, cod);
			pstm.executeUpdate();
			cerrarRsPstm(null, pstm, "updateProveedorProvMarca");
		}

	}

	public boolean cambiarContrasena(Usuario u, String p) {
		PreparedStatement pstm = null;
		boolean correcto = false;
		try {
			pstm = conexion.prepareStatement("UPDATE usuarios SET contrasena = ? WHERE idusuario= ?");
			pstm.setString(1, p);
			pstm.setInt(2, u.getIdusuario());
			correcto = pstm.executeUpdate() == 1 ? true : false;

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
			pstm = conexion.prepareStatement("UPDATE usuarios SET contrasena = ?, nombre = null, mail = null WHERE idusuario= ?");
			pstm.setString(1, encriptar(u.getUsuario()));
			pstm.setInt(2, u.getIdusuario());
			correcto = pstm.executeUpdate() == 1 ? true : false;

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
