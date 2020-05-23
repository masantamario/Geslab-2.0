package geslab.database.admin;

//import java.sql.PreparedStatement;
//import java.sql.SQLException;

import geslab.database.Conexion;

public class Departamento extends Conexion{
	private int coddpto;
	private String nombre;
	
	public Departamento(int coddpto, String nombre) {
		this.coddpto = coddpto;
		this.nombre = nombre;
	}

//	public boolean insertar() {
//		PreparedStatement pstm = null;
//		boolean correcto = false;
//		if (coddpto == 0) {
//			try {
//				pstm = conexion.prepareStatement("INSERT INTO dpto (nombre) VALUES (?)");
//				pstm.setString(1, nombre);
//				pstm.executeUpdate();
//	
//			} catch (SQLException e) {
//				printSQLException(e, "INSERTAR DEPARTAMENTO");
//			} finally {
//				cerrarRsPstm(null, pstm, "insertarDepartamento");
//			}
//		} else {
//			correcto = update();
//		}
//		return correcto;
//	}
//
//	public boolean update() {
//		PreparedStatement pstm = null;
//		boolean correcto = false;
//		try {
//			pstm = conexion.prepareStatement("UPDATE dpto SET nombre = ? WHERE coddpto = ?");
//			pstm.setString(1, nombre);
//			pstm.setInt(2, coddpto);
//			pstm.executeUpdate();
//	
//		} catch (SQLException e) {
//			printSQLException(e, "UPDATE DEPARTAMENTO");
//		} finally {
//			cerrarRsPstm(null, pstm, "updateDepartamento");
//		}
//		return correcto;
//	}

	public int getCoddpto() {
		return coddpto;
	}

	public String getNombre() {
		return nombre;
	}
	
}
