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

//	private HttpServletRequest request = null;
//	private HttpServletResponse response = null;
	private Conexion cn = null;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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

				cn.cerrarConexion();

				request.getRequestDispatcher("/WEB-INF/ubicaciones.jsp").forward(request, response);
			}
		} else {
			response.sendRedirect("/login.do");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		this.request = request;
//		this.response = response;

		String accion = request.getParameter("accion");
		String codigo = request.getParameter("codigo");
		cn = new Conexion();
		System.out.println("Accion: " + accion);
		System.out.println("Código: " + codigo);

		String nombre = request.getParameter("insertar-nombre");
		String centro = request.getParameter("insertar-centro");
		String area = usuario.getArea();
		Boolean oculta = (request.getParameter("insertar-oculta") != null);

		switch (accion) {
		case "insertar":
			if (!cn.existeUbicacion(nombre, area, centro)) {
				cn.insertarUbicacion(new Ubicacion(0, nombre, area, "", centro, oculta));
			}
			break;
		case "editar":
			cn.updateUbicacion(new Ubicacion(Integer.valueOf(codigo), nombre, area, "", centro, oculta));
			break;
		}
		cn.cerrarConexion();
		response.sendRedirect("/ubicaciones.do");
	}

}
