package geslab.database;

import java.util.ArrayList;
import java.util.Arrays;


public class Test {
	public static void main(String[] args) {
		Conexion cn = new Conexion();
		cn.conectar();
		
		System.out.println(cn.encriptar("admin"));
		
		cn.cerrarConexion();
			
	}
	
}
