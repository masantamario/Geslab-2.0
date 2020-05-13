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
			System.out.println("Error al cargar el controlador");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Error en la conexión");
			e.printStackTrace();
		}
	}

	public void cerrarConexion() {
		try {
			if (conexion != null)
				conexion.close();
		} catch (SQLException e) {
			System.out.println("Error al cerrar la conexion");
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
			System.out.println("Error al cerrar resulSet o PreparedStament en " + metodo);
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
			System.out.println("Error en la consulta de usuario");
		} finally {
			cerrarRsPstm(rs, pstm, "existeUsuario");
		}
		
		return usuario;
	}

	public Usuario leerUsuario(String u) throws SQLException {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Usuario usuario = null;

		pstm = conexion.prepareStatement("select * from usuarios where usuario = ?");
		pstm.setString(1, u);
		rs = pstm.executeQuery();
		
		if (rs.next()) {
			usuario = new Usuario(rs.getInt("idusuario"), rs.getString("usuario"), rs.getString("contrasena"),
					rs.getString("nombre"), rs.getString("mail"), Boolean.valueOf(rs.getString("federada")), Boolean.valueOf(rs.getString("activo")), rs.getInt("rol"),
					rs.getInt("area"), rs.getDate("fecha_creacion"));
		}
		cerrarRsPstm(rs, pstm, "leerUsuario");
		return usuario;

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
			System.out.println("Error en la lectura de usuarios");

		} finally {
			cerrarRsPstm(rs, pstm, "leerUsuarios");
		}

		return usuarios;
	}
	
	public boolean insertarUsuario(Usuario u) {
		PreparedStatement pstm = null;
		boolean correcto = false;
		try {
		pstm = conexion.prepareStatement("INSERT INTO usuarios (usuario, contrasena, rol, federada, activo) VALUES (?, ?, ?, ?, ?)");
		pstm.setString(1, u.getUsuario());
		pstm.setString(2, "");
		pstm.setInt(3, u.getRol().getId());
		pstm.setString(4, Boolean.toString(u.getFederada()));
		pstm.setString(5, Boolean.toString(u.getActivo()));
		pstm.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Error al añadir usuario");
		} finally {
			cerrarRsPstm(null, pstm, "leerUsuarios");
		}
		
		return correcto;
	}

	public ArrayList<Area> leerAreas() {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<Area> areas = new ArrayList<Area>();

		try {
			pstm = conexion.prepareStatement("select * from area");
			rs = pstm.executeQuery();

			while (rs.next()) {

				Area a = new Area(rs.getInt("codArea"), rs.getString("nombre"), rs.getString("dpto"));
				areas.add(a);
			}

		} catch (SQLException e) {
			System.out.println("Error en la lectura de las areas");

		} finally {
			cerrarRsPstm(rs, pstm, "leerUsuarios");
		}

		return areas;
	}
	
	
}
