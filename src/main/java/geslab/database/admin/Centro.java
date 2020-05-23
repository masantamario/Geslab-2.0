package geslab.database.admin;

//import java.sql.PreparedStatement;

import geslab.database.Conexion;

//import java.util.ArrayList;

public class Centro extends Conexion{
	private int codcentro;
	private String nombre;
	
	public Centro(int codcentro, String nombre) {
		this.codcentro = codcentro;
		this.nombre = nombre;
	}

//	public boolean insertar() {
//		PreparedStatement pstm = null;
//		boolean correcto = false;
//		if (codcentro == 0) {
//			try {
//				pstm = conexion.prepareStatement("INSERT INTO centro (nombre) VALUES (?)");
//				pstm.setString(1, nombre);
//				pstm.executeUpdate();
//	
//			} catch (SQLException e) {
//				printSQLException(e, "INSERTAR CENTRO");
//			} finally {
//				cerrarRsPstm(null, pstm, "insertarCentro");
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
//			pstm = conexion.prepareStatement("UPDATE centro SET nombre = ? WHERE codcentro = ?");
//			pstm.setString(1, nombre);
//			pstm.setInt(2, codcentro);
//			pstm.executeUpdate();
//	
//		} catch (SQLException e) {
//			printSQLException(e, "UPDATE CENTRO");
//		} finally {
//			cerrarRsPstm(null, pstm, "updateCentro");
//		}
//		return correcto;
//	}

	public int getCodcentro() {
		return codcentro;
	}

	public String getNombre() {
		return nombre;
	}
	

}
