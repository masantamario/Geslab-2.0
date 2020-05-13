package geslab.database.modelo;

import java.util.Date;

public class Usuario {
	private int idusuario = 0;
	private String usuario = "";
	private String contrasena = "";
	private String nombre = "";
	private String mail = "";
	private boolean federada = false;
	private boolean activo = false;
	private Rol rol = null;
	private int area = 0;
	private Date fecha_creacion = null;
	
		
	public Usuario(int idusuario, String usuario, String contrasena, String nombre, String mail, boolean federada, boolean activo, int rol, int area, Date fecha_creacion) {
		this.idusuario = idusuario;
		this.usuario = usuario;
		this.contrasena = contrasena;
		this.nombre = nombre;
		this.mail = mail;
		this.federada = federada;
		this.activo = activo;
//		this.activo = Boolean.parseBoolean(activo);
		
		if(rol == 1) {
			this.rol = Rol.ADMINISTRADOR;
		}else if(rol == 2) {
			this.rol = Rol.GESTOR;
		}else if(rol == 3) {
			this.rol = Rol.USUARIO;
		}
		
		this.area = area;
		this.fecha_creacion = fecha_creacion;
	}
	
	public Usuario(String usuario, int rol, boolean federada, boolean activo) {
		this.usuario = usuario;
		this.federada = federada;
		this.activo = activo;
		if(rol == 1) {
			this.rol = Rol.ADMINISTRADOR;
		}else if(rol == 2) {
			this.rol = Rol.GESTOR;
		}else if(rol == 3) {
			this.rol = Rol.USUARIO;
		}
	
	}


	public String[] toArray() {
		String[] usuarioArray = new String[10];
		
		usuarioArray[0] = "" + this.idusuario;
		usuarioArray[1] = this.usuario;
		usuarioArray[2] = this.contrasena ;
		usuarioArray[3] = this.nombre;
		usuarioArray[4] = this.mail;
		usuarioArray[5] = "" + this.federada;
		usuarioArray[6] = "" + this.federada;
		usuarioArray[7] = "" + this.rol;
		usuarioArray[8] = "" + this.area;
		usuarioArray[9] = "" + this.fecha_creacion;
		
		return usuarioArray;
	}
	
	public void imprimir() {
		System.out.println("- Id Usuario: " + this.idusuario);
		System.out.println("- Usuario: " + this.usuario);
		System.out.println("- Contraseña: " + this.contrasena );
		System.out.println("- Nombre: " + this.nombre);
		System.out.println("- Email: " + this.mail);
		System.out.println("- Federada: " + this.federada);
		System.out.println("- Activo: " + this.activo);
		System.out.println("- Rol: " + this.rol);
		System.out.println("- Area: " + this.area);
		System.out.println("- Fecha creación: " + this.fecha_creacion);
	}

	
	public String getUsuario() {
		return this.usuario;
	}

	public String getNombre() {
		return this.nombre;
	}
	
	public Rol getRol() {
		return this.rol;
	}
	
	public boolean getFederada() {
		return this.federada;
	}
	
	public boolean getActivo() {
		return this.activo;
	}

}
