package geslab.database.admin;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import geslab.database.Conexion;

public class Usuario extends Conexion{
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

//	public boolean insertar() {
//		PreparedStatement pstm = null;
//		boolean correcto = false;
//		if (idusuario == 0) {
//			try {
//				pstm = conexion.prepareStatement(
//						"INSERT INTO usuarios (usuario, contrasena, rol, area, federada, activo, fecha_creacion) VALUES (?, ?, ?, (SELECT codarea FROM area WHERE nombre=?), ?, ?, NOW())");
//				pstm.setString(1, usuario);
//				pstm.setString(2, encriptar(usuario));
//				pstm.setInt(3, rol.getId());
//				pstm.setString(4, area);
//				pstm.setString(5, Boolean.toString(federada));
//				pstm.setString(6, Boolean.toString(activo));
//				pstm.executeUpdate();
//	
//			} catch (SQLException e) {
//				printSQLException(e, "INSERTAR USUARIO");
//			} finally {
//				cerrarRsPstm(null, pstm, "insertarUsuario");
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
//			pstm = conexion.prepareStatement(
//					"UPDATE usuarios SET usuario = ?, nombre = ?, mail = ?, rol=?, area=(SELECT codarea FROM area WHERE nombre=?), federada=?, activo=? WHERE idusuario= ?");
//			pstm.setString(1, usuario);
//			pstm.setString(2, nombre);
//			pstm.setString(3, mail);
//			pstm.setInt(4, rol.getId());
//			pstm.setString(5, area);
//			pstm.setString(6, String.valueOf(federada));
//			pstm.setString(7, String.valueOf(activo));
//			pstm.setInt(8, idusuario);
//			pstm.executeUpdate();
//	
//		} catch (SQLException e) {
//			printSQLException(e, "UPDATE USUARIO");
//		} finally {
//			cerrarRsPstm(null, pstm, "updateUsuario");
//		}
//		return correcto;
//	}

//	public boolean cambiarContrasena(String p) {
//		PreparedStatement pstm = null;
//		boolean correcto = false;
//		try {
//			pstm = conexion.prepareStatement("UPDATE usuarios SET contrasena = ? WHERE idusuario= ?");
//			pstm.setString(1, p);
//			pstm.setInt(2, idusuario);
//			pstm.executeUpdate();
//	
//		} catch (SQLException e) {
//			printSQLException(e, "CAMBIAR CONTRASEÑA");
//		} finally {
//			cerrarRsPstm(null, pstm, "cambiarContrasena");
//		}
//		return correcto;
//	}

//	public boolean resetearContrasena() {
//		PreparedStatement pstm = null;
//		boolean correcto = false;
//		try {
//			pstm = conexion.prepareStatement("UPDATE usuarios SET contrasena = ? WHERE idusuario= ?");
//			pstm.setString(1, encriptar(usuario));
//			pstm.setInt(2, idusuario);
//			pstm.executeUpdate();
//	
//		} catch (SQLException e) {
//			printSQLException(e, "RESETEAR CONTRASEÑA");
//		} finally {
//			cerrarRsPstm(null, pstm, "resetearContrasena");
//		}
//		return correcto;
//	}

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
		String cadena = "\n----------------------------------\n  -Id: " + idusuario + "\n  -Usuario: " + usuario + "\n  -Nombre: "
				+ nombre + "\n  -Correo: " + mail + "\n  -Federada: " + "\n  -Rol: " + rol + "\n  -Area: " + area + federada
				+ "\n  -Activo: " + activo + "\n  -Fecha creacion: " + fecha_creacion + "\n----------------------------------\n\n";
		return cadena;
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

}
