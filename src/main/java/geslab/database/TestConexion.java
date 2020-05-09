package geslab.database;


public class TestConexion {
	public static void main(String[] args) {
		Conexion cn = new Conexion();
		cn.conectar();
		System.out.println(cn.existeUsuario("admin", "admin"));
		cn.cerrarConexion();
	}
	
}
