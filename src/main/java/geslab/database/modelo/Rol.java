package geslab.database.modelo;


public enum Rol{
	ADMINISTRADOR(1, "Administrador"),
	GESTOR(2, "Gestor de inventario"),
	USUARIO(3, "Usuario");
	
	private final int idrol;
	private final String rol;
	
	Rol(int id, String rol) {
		this.idrol = id;
		this.rol = rol;
	}
	
	public int getId() {
		return idrol;
	}

	public String getRol() {
		return rol;
	}
	
}

//public class Rol {
//	private int idrol;
//	private String rol;
//	
//	public Rol(int idrol, String rol) {
//		this.setIdrol(idrol);
//		this.setRol(rol);
//	}
//
//	public int getIdrol() {
//		return idrol;
//	}
//
//	public void setIdrol(int idrol) {
//		this.idrol = idrol;
//	}
//
//	public String getRol() {
//		return rol;
//	}
//
//	public void setRol(String rol) {
//		this.rol = rol;
//	}
//
//	
//}
