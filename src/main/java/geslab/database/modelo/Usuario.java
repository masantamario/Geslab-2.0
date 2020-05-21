package geslab.database.modelo;

import java.util.Date;

public class Usuario {
	private int idusuario = 0;
	private String usuario = "";
	private String nombre = "";
	private String mail = "";
	private Rol rol = null;
	private String area = "";
	private boolean federada = false;
	private boolean activo = false;
	private Date fecha_creacion = null;

	public Usuario(int idusuario, String usuario, String nombre, String mail, boolean federada,
			boolean activo, int rol, String area, Date fecha_creacion) {
		this.idusuario = idusuario;
		this.usuario = usuario;
		if(nombre != null) this.nombre = nombre;
		if(mail != null) this.mail = mail;
		this.federada = federada;
		this.activo = activo;

		if (rol == 1) {
			this.rol = Rol.ADMINISTRADOR;
		} else if (rol == 2) {
			this.rol = Rol.GESTOR;
		} else if (rol == 3) {
			this.rol = Rol.USUARIO;
		}

		this.area = area;
		this.fecha_creacion = fecha_creacion;
	}

	public Usuario(int idusuario, String usuario, int rol, String area, boolean federada, boolean activo) {
		this.idusuario = idusuario;
		this.usuario = usuario;
		this.federada = federada;
		this.activo = activo;
		if (rol == 1) {
			this.rol = Rol.ADMINISTRADOR;
		} else if (rol == 2) {
			this.rol = Rol.GESTOR;
		} else if (rol == 3) {
			this.rol = Rol.USUARIO;
		}
		this.area = area;

	}

	public int getIdusuario() {
		return this.idusuario;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public String getNombre() {
		return this.nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;;
	}
	
	public String getMail() {
		return this.mail;
	}
	
	public void setMail(String mail) {
		this.mail = mail;;
	}

	public Rol getRol() {
		return this.rol;
	}

	public String getArea() {
		return this.area;
	}

	public boolean getFederada() {
		return this.federada;
	}

	public boolean getActivo() {
		return this.activo;
	}


	public void imprimir() {
		System.out.println("- Id Usuario: " + this.idusuario);
		System.out.println("- Usuario: " + this.usuario);
		System.out.println("- Nombre: " + this.nombre);
		System.out.println("- Email: " + this.mail);
		System.out.println("- Rol: " + this.rol);
		System.out.println("- Area: " + this.area);
		System.out.println("- Federada: " + this.federada);
		System.out.println("- Activo: " + this.activo);
		System.out.println("- Fecha creación: " + this.fecha_creacion);
	}
	
	public String toString() {
		String cadena = "-Id: " + idusuario + "\n-Usuario: " + usuario + "\n-Nombre: "
				+ nombre + "\n-Correo: " + mail + "\n-Federada: " + "\n-Rol: " + rol + "\n-Area: " + area + federada
				+ "\n-Activo: " + activo + "\n-Fecha creacion: " + fecha_creacion;
		return cadena;
	}

}
