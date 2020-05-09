package geslab.database.modelo;
import java.util.Date;

public class Usuario {
	private int idusuario;
	private String usuario;
	private String contrasena;
	private String nombre;
	private String mail;
	private boolean federada;
	private int rol;
	private int area;
	private Date fecha_creacion;
	
	
	public Usuario(String usuario, String contrasena) {
		this.usuario = usuario;
		this.contrasena = contrasena;
	}


	public int getIdusuario() {
		return idusuario;
	}


	public void setIdusuario(int idusuario) {
		this.idusuario = idusuario;
	}


	public String getUsuario() {
		return usuario;
	}


	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}


	public String getContrasena() {
		return contrasena;
	}


	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getMail() {
		return mail;
	}


	public void setMail(String mail) {
		this.mail = mail;
	}


	public boolean isFederada() {
		return federada;
	}


	public void setFederada(boolean federada) {
		this.federada = federada;
	}


	public int getRol() {
		return rol;
	}


	public void setRol(int rol) {
		this.rol = rol;
	}


	public int getArea() {
		return area;
	}


	public void setArea(int area) {
		this.area = area;
	}


	public Date getFecha_creacion() {
		return fecha_creacion;
	}


	public void setFecha_creacion(Date fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
	}
	
	public void imprimir() {
		System.out.println("- Id Usuario: " + this.idusuario);
		System.out.println("- Usuario: " + this.usuario);
		System.out.println("- Contraseña: " + this.contrasena );
		System.out.println("- Nombre: " + this.nombre);
		System.out.println("- Email: " + this.mail);
		System.out.println("- Federada: " + this.federada);
		System.out.println("- Rol: " + this.rol);
		System.out.println("- Area: " + this.area);
		System.out.println("- Fecha creación: " + this.fecha_creacion);
	}
	
}
