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
import geslab.database.user.Calidad;



/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/calidades.do")
public class CalidadesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Usuario usuario = null;
	private HttpSession sesion = null;
	private HttpServletRequest request = null;
	private Conexion cn = null;
	
	//Variables calidad
	private String nombre;

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

				request.setAttribute("calidades", cn.leerCalidades());
				request.setAttribute("usuario", usuario);
				request.setAttribute("mensaje", sesion.getAttribute("mensaje"));

				cn.cerrarConexion();

				request.getRequestDispatcher("/WEB-INF/calidades.jsp").forward(request, response);
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
		leerParametrosCalidad();
			switch (accion) {
			case "insertar":
				cn.insertarCalidad(new Calidad(0, nombre));
				break;
			case "editar":
				String n = cn.leerCalidad(Integer.valueOf(codigo)).getNombre();
				if (n.equals(nombre) || (!n.equals(nombre) && !cn.existeCalidad(nombre))) {
					cn.updateCalidad(new Calidad(Integer.valueOf(codigo), nombre));
				} else {
					throw new Exception("Nombre no válido");
				}
				break;
			} 
		}catch (Exception msg) {
			sesion.setAttribute("mensaje", msg.getMessage());
		}
		
		cn.cerrarConexion();
		response.sendRedirect("/calidades.do");
	}
	
	private void leerParametrosCalidad() throws Exception {
		try {
			nombre = request.getParameter("insertar-nombre");
			if (nombre.equals("")) throw new Exception("Campo nombre obligatorio");
			
		} catch (Throwable exception) {
			if(exception.getClass().toString().equals("class java.lang.Exception")) {
				throw new Exception(exception.getMessage());	
			}else {
				throw new Exception("Parámetros de calidad incorrectos");	
			}
		} 
	}

}
