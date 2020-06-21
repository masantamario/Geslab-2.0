package geslab.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import geslab.database.Conexion;
import geslab.database.admin.Usuario;
import geslab.database.user.Ubicacion;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/ubicaciones.do")
public class UbicacionesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Usuario usuario = null;
	private HttpSession sesion = null;
	private HttpServletRequest request = null;
	private Conexion cn = null;
	
	// Variables ubicación
		private String nombre, centro, area;
		private boolean oculta;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.request = request;
		sesion = request.getSession();
		usuario = (Usuario) sesion.getAttribute("usuario");

		if (usuario != null) {
			if (usuario.getNombre().equals("")) {
				response.sendRedirect("/registro.do");
			} else {

				Conexion cn = new Conexion();

				request.setAttribute("departamentos", cn.leerDepartamentos());
				request.setAttribute("areas", cn.leerAreas());
				request.setAttribute("centros", cn.leerCentros());
				request.setAttribute("ubicaciones", cn.leerUbicaciones(usuario));
				request.setAttribute("proveedores", cn.leerProveedores());
				request.setAttribute("usuario", usuario);
				request.setAttribute("mensaje", sesion.getAttribute("mensaje"));

				cn.cerrarConexion();

				request.getRequestDispatcher("/WEB-INF/ubicaciones.jsp").forward(request, response);
			}
		} else {
			response.sendRedirect("/login.do");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.request = request;
		String accion = request.getParameter("accion");
		String codigo = request.getParameter("codigo");
		cn = new Conexion();
		try {
			leerParametrosUbicacion();
			switch (accion) {
			case "insertar":
				cn.insertarUbicacion(new Ubicacion(0, nombre, area, "", centro, oculta));
				break;
			case "editar":
				cn.updateUbicacion(new Ubicacion(Integer.valueOf(codigo), nombre, area, "", centro, oculta));
				
				
				String n = cn.leerUbicacion(Integer.valueOf(codigo)).getNombre();
				if (n.equals(nombre) || (!n.equals(nombre) && !cn.existeUbicacion(nombre, area, centro))) {
					cn.updateUbicacion(new Ubicacion(Integer.valueOf(codigo), nombre, area, "", centro, oculta));
				} else {
					throw new Exception("Nombre no válido");
				}
				break;
				
			}
		} catch (Exception msg) {
			sesion.setAttribute("mensaje", msg.getMessage());
		}
		
		cn.cerrarConexion();
		response.sendRedirect("/ubicaciones.do");
	}
	
	private void leerParametrosUbicacion() throws Exception {
		try {
			nombre = request.getParameter("insertar-nombre");
			if (nombre.equals("")) throw new Exception("Campo nombre obligatorio");
			
			centro = request.getParameter("insertar-centro");
			if (centro.equals("")) throw new Exception("Campo centro obligatorio");
			
			area = usuario.getArea();
			oculta = (request.getParameter("insertar-oculta") != null);
			
		}catch (Throwable exception) {
			if(exception.getClass().toString().equals("class java.lang.Exception")) {
				throw new Exception(exception.getMessage());	
			}else {
				throw new Exception("Parámetros de ubicación incorrectos");	
			}
		} 	
	}

}
