package geslab.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import geslab.database.modelo.Area;
import geslab.database.modelo.Usuario;

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
//		return conexion;
	}
	
	public void cerrarConexion() {
		try {
			if (conexion != null) conexion.close();
		} catch (SQLException e) {
			System.out.println("Error al cerrar la conexion");
			e.printStackTrace();
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

//			System.out.println(pstm);
			rs = pstm.executeQuery();
			if( rs.next()) {
				usuario = new Usuario(u, p);
				usuario.setIdusuario(rs.getInt("idusuario"));
				usuario.setNombre(rs.getString("nombre"));
				usuario.setMail(rs.getString("mail"));
				usuario.setFederada(rs.getString("federada") == "s");
				usuario.setRol(rs.getInt("rol"));
				usuario.setArea(rs.getInt("area"));
				usuario.setFecha_creacion(rs.getDate("fecha_creacion"));
			}

		} catch (SQLException e) {
			System.out.println("Error en la consulta de usuario");

		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstm != null)
					pstm.close();
			} catch (SQLException e) {
				System.out.println("Error al cerrar resulSet o PreparedStament");
			}
		}

		return usuario;
	}
	
	public List<Usuario> leerUsuarios(){
		PreparedStatement pstm = null;
		ResultSet rs = null;
//		Usuario[] usuarios = null;
		List<Usuario> usuarios = new ArrayList<Usuario>();

		try {
			pstm = conexion.prepareStatement("select * from usuarios");
			rs = pstm.executeQuery();
			
			while( rs.next()) {
				Usuario u = new Usuario(rs.getString("usuario"), rs.getString("contrasena"));
				u.setIdusuario(rs.getInt("idusuario"));
				u.setNombre(rs.getString("nombre"));
				u.setMail(rs.getString("mail"));
				u.setFederada(rs.getString("federada") == "s");
				u.setRol(rs.getInt("rol"));
				u.setArea(rs.getInt("area"));
				u.setFecha_creacion(rs.getDate("fecha_creacion"));
				
				usuarios.add(u);
			}

		} catch (SQLException e) {
			System.out.println("Error en la lectura de usuarios");

		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstm != null)
					pstm.close();
			} catch (SQLException e) {
				System.out.println("Error al cerrar resulSet o PreparedStament");
			}
		}
		
		return usuarios;
	}

	
	public List<Area> leerAreas(){
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<Area> areas = new ArrayList<Area>();

		try {
			pstm = conexion.prepareStatement("select * from area");
			rs = pstm.executeQuery();
			
			while( rs.next()) {
				
				Area a = new Area(rs.getInt("codArea"), rs.getString("nombre"), rs.getString("dpto"));
				areas.add(a);
			}

		} catch (SQLException e) {
			System.out.println("Error en la lectura de las areas");

		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstm != null)
					pstm.close();
			} catch (SQLException e) {
				System.out.println("Error al cerrar resulSet o PreparedStament");
			}
		}
		
		return areas;
	}
}
