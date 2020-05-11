package geslab.database.modelo;
import java.util.Date;

public class Usuario {
	private int idusuario;
	private String usuario;
	private String contrasena;
	private String nombre;
	private String mail;
	private boolean federada;
	private boolean activo;
	private int rol;
	private int area;
	private Date fecha_creacion;
	
		
	public Usuario(int idusuario, String usuario, String contrasena, String nombre, String mail, String federada, String activo, int rol, int area, Date fecha_creacion) {
		this.idusuario = idusuario;
		this.usuario = usuario;
		this.contrasena = contrasena;
		this.nombre = nombre;
		this.mail = mail;
		this.federada = federada == "s";
		this.activo = activo == "s";
		this.rol = rol;
		this.area = area;
		this.fecha_creacion = fecha_creacion;
	}

	public String[] toArray() {
		String[] usuarioArray = new String[10];
		
		usuarioArray[0] = "" + this.usuario;
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
	
	public int getRol() {
		return this.rol;
	}
	
	public boolean getFederada() {
		return this.federada;
	}
	
	public boolean getActivo() {
		return this.activo;
	}
}
