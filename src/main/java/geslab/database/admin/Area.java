package geslab.database.admin;

//import java.sql.PreparedStatement;

import geslab.database.Conexion;

public class Area extends Conexion {
	private int codarea;
	private String nombre;
	private String dpto;
	
	public Area(int codarea, String nombre, String dpto) {
		this.codarea = codarea;
		this.nombre = nombre;
		this.dpto = dpto;
	}

//	public boolean insertar() {
//		PreparedStatement pstm = null;
//		boolean correcto = false;
//		if (codarea == 0) {
//			try {
//				pstm = conexion.prepareStatement("INSERT INTO area (nombre, dpto) VALUES (?, (SELECT coddpto FROM dpto WHERE nombre=?))");
//				pstm.setString(1, nombre);
//				pstm.setString(2, dpto);
//				pstm.executeUpdate();
//	
//			} catch (SQLException e) {
//				printSQLException(e, "INSERTAR AREA");
//			} finally {
//				cerrarRsPstm(null, pstm, "insertarArea");
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
//			pstm = conexion.prepareStatement("UPDATE area SET nombre = ?, dpto=(SELECT coddpto FROM dpto WHERE nombre=?) WHERE codarea = ?");
//			pstm.setString(1, nombre);
//			pstm.setString(2, dpto);
//			pstm.setInt(3, codarea);
//			pstm.executeUpdate();
//	
//		} catch (SQLException e) {
//			printSQLException(e, "UPDATE AREA");
//		} finally {
//			cerrarRsPstm(null, pstm, "updateArea");
//		}
//		return correcto;
//	}

	public int getCodarea() {
		return codarea;
	}

	public String getNombre() {
		return nombre;
	}

	public String getDpto() {
		return dpto;
	}
	


}
