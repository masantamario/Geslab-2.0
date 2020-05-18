package geslab.database;

import java.util.ArrayList;

import geslab.database.modelo.Departamento;
import geslab.database.modelo.Rol;

public class Test {
	public static void main(String[] args) {
		Conexion cn = new Conexion();
		cn.conectar();
//		System.out.println(cn.existeUsuario("admin", "admin"));
//		cn.cerrarConexion();
		
//		for(Rol r:Rol.values()){ 											    	
//			System.out.println(r.getRol());
//		}
				
//		String f = "on";
//		boolean fd;
//		f=(f.equals("on"))?"true":"false";
//		f=(f==null)?"false":"true";
//		fd = (f!= null);
		
//		System.out.println(fd);
//		ArrayList<Departamento> departamentos = new ArrayList<Departamento>();
		
//		departamentos.add(new Departamento(1, "Ciencia"));
//		departamentos.add(new Departamento(2, "Química"));
//		departamentos.add(new Departamento(3, "Física"));
//		
//		int indice = departamentos.indexOf("Ciencia");
//		System.out.println(indice);
//		
//		String a = "hola";
//		String b = "hola";
//		
//		if(a == b) {
//			System.out.println("yesss");
//		}
		
//		for(Rol r : Rol.values()){
//			System.out.println(r.getRol());
//		}
		
		int codDpto = 0;
		for (Departamento dpto : cn.leerDepartamentos()) {
			if (dpto.getNombre().equals("Quemica")) {
				codDpto = dpto.getCoddpto();
			}
		}
		System.out.println(codDpto);
	}
	
}
