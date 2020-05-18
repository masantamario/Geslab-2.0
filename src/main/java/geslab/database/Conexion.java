package geslab.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import geslab.database.modelo.*;

public class Conexion {
	private static final String CONTROLADOR = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/dbo?serverTimezone=UTC";
	private static final String USER = "root";
	private static final String PASS = "geslabMarioSantamaria";
	private static Connection conexion = null;

	public Conexion() {
		conectar();
	}

	public void conectar() {
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

	public Usuario leerUsuario(String u) {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Usuario usuario = null;

		try {
			pstm = conexion.prepareStatement("SELECT usuarios.idusuario, usuarios.contrasena, usuarios.usuario, usuarios.nombre, usuarios.mail, usuarios.rol, area.nombre AS area, usuarios.federada, usuarios.activo, usuarios.fecha_creacion\r\n" + 
					"FROM usuarios \r\n" + 
					"INNER JOIN area ON usuarios.area = area.codarea\r\n" + 
					"WHERE usuario = ?;");
		
		pstm.setString(1, u);
		rs = pstm.executeQuery();

		if (rs.next()) {
			usuario = new Usuario(rs.getInt("idusuario"), rs.getString("usuario"), rs.getString("contrasena"),
					rs.getString("nombre"), rs.getString("mail"), Boolean.valueOf(rs.getString("federada")),
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
				pstm = conexion.prepareStatement("INSERT INTO area (nombre, dpto) VALUES (?, ?)");
//				pstm = conexion.prepareStatement("INSERT INTO area (nombre, dpto) VALUES (?, (SELECT coddpto FROM dpto WHERE nombre=?))");
				pstm.setString(1, a.getNombre());
				int codDpto = 0;
				for (Departamento dpto : leerDepartamentos()) {
					if (dpto.getNombre().equals(a.getDpto())) {
						codDpto = dpto.getCoddpto();
					}
				}
				pstm.setInt(2, codDpto);
//				pstm.setString(2, a.getDpto());
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
						"INSERT INTO usuarios (usuario, contrasena, rol, area, federada, activo, fecha_creacion) VALUES (?, ?, ?, ?, ?, ?, NOW())");
				pstm.setString(1, u.getUsuario());
				pstm.setString(2, "");
				pstm.setInt(3, u.getRol().getId());
				for (Area area : leerAreas()) {
					if (area.getNombre().equals(u.getArea())) {
						pstm.setInt(4, area.getCodarea());
					}
				}
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
			pstm = conexion.prepareStatement("UPDATE area SET nombre = ?, dpto=? WHERE codarea = ?");
			pstm.setString(1, a.getNombre());
			int coddpto = 0;
			for (Departamento d : leerDepartamentos()) {
				if (d.getNombre().equals(a.getDpto())) {
					coddpto = d.getCoddpto();
					break;
				}
			}
			pstm.setInt(2, coddpto);
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
			pstm = conexion
					.prepareStatement("UPDATE usuarios SET usuario = ?, rol=?, area=?, federada=?, activo=? WHERE idusuario= ?");
			pstm.setString(1, u.getUsuario());
			pstm.setInt(2, u.getRol().getId());
			for (Area area : leerAreas()) {
				if (area.getNombre().equals(u.getArea())) {
					pstm.setInt(3, area.getCodarea());
				}
			}
			pstm.setString(4, String.valueOf(u.getFederada()));
			pstm.setString(5, String.valueOf(u.getActivo()));
			pstm.setInt(6, u.getIdusuario());
			pstm.executeUpdate();

		} catch (SQLException e) {
			printSQLException(e, "UPDATE USUARIO");
		} finally {
			cerrarRsPstm(null, pstm, "updateUsuario");
		}
		return correcto;
	}

//	public ArrayList<Centro> leerCentrosDeArea(int codarea) {
//		System.out.println("Leyendo centros de area");
//		PreparedStatement pstm = null;
//		ResultSet rs = null;
//		ArrayList<Centro> centros = new ArrayList<Centro>();
//		try {
//			pstm = conexion.prepareStatement(
//					"SELECT centro_area.area AS codarea, centro_area.centro AS codcentro, centro.nombre AS centro \r\n"
//							+ "from centro_area \r\n" + "INNER JOIN centro ON centro_area.centro = centro.codcentro\r\n"
//							+ "where area = ?;");
//
//			pstm.setInt(1, codarea);
//			rs = pstm.executeQuery();
//			while (rs.next()) {
//				centros.add(new Centro(rs.getInt("codcentro"), rs.getString("centro"),
//						leerAreasDeCentro(rs.getInt("codcentro"))));
//			}
//		} catch (SQLException e) {
//			printSQLException(e, "LEER CENTROS DE AREA");
//		}
//		return centros;
//	}

//	public ArrayList<Area> leerAreasDeCentro(int codcentro) {
//		System.out.println("Leyendo areas de centro");
//		PreparedStatement pstm = null;
//		ResultSet rs = null;
//		ArrayList<Area> areas = new ArrayList<Area>();
//		try {
//			pstm = conexion.prepareStatement(
//					"SELECT centro_area.centro AS codcentro, centro_area.area AS codarea, area.nombre AS area, area.dpto AS coddpto, dpto.nombre AS dpto\r\n"
//							+ "from centro_area \r\n" + "INNER JOIN area ON centro_area.area = area.codarea\r\n"
//							+ "INNER JOIN dpto ON area.dpto = dpto.coddpto\r\n" + "where centro = ?;");
//
//			pstm.setInt(1, codcentro);
//			rs = pstm.executeQuery();
//			while (rs.next()) {
//				int cod = rs.getInt("codarea");
//				String area = rs.getString("area");
//				Departamento dpto = new Departamento(rs.getInt("coddpto"), rs.getString("dpto"));
//				//ArrayList<Centro> centros = leerCentrosDeArea(rs.getInt("codarea"));
//				//areas.add(new Area(cod, area, dpto, centros));
//			}
//		} catch (SQLException e) {
//			printSQLException(e, "LEER AREAS DE CENTRO");
//		}
//		return areas;
//	}

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
