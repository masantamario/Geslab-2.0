package geslab.database;

import java.util.ArrayList;
import java.util.function.Predicate;

import geslab.database.admin.Departamento;
import geslab.database.admin.Rol;
import geslab.database.admin.Usuario;

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
		
		int id = 0;
		String nombre = "";
		int rol = 3;
		String area = "Matematicas";
		
		
		
		Usuario u1 = new Usuario(1, "Mario", 3, "Química", true, true);
		Usuario u2 = new Usuario(2, "Carla", 1, "Matematicas", true, true);
		Usuario u3 = new Usuario(3, "Isma", 2, "Ciencias", true, true);
		Usuario u4 = new Usuario(4, "Luisete", 3, "Matematicas", true, true);

        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
        usuarios.add(u1);
        usuarios.add(u2);
        usuarios.add(u3);
        usuarios.add(u4);
        

        ArrayList<Usuario> filtrado = new ArrayList<Usuario>();
        for (Usuario u: usuarios) {
        	boolean valido = true;
            
        	if(id != 0 && u.getIdusuario() != id) {
        		valido = false;
        		System.out.println("No coincide ID");
        	}
        	if(!nombre.equals("") && !u.getUsuario().equals(nombre)) {
        		valido = false;
        		System.out.println("No coincide Usuario");
        	}
        	if(rol != 0 && u.getRol().getId() != rol) {
        		valido = false;
        		System.out.println("No coincide Rol");
        	}
        	if(!area.equals("") && !u.getArea().equals(area)) {
        		valido = false;
        		System.out.println("No coincide Area");
        	}
        	
        	if(valido) filtrado.add(u);
        		
        }

        System.out.println(filtrado);

	}
	
}
