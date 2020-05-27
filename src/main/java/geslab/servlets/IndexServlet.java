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

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/index.do")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Usuario usuario = null;
	private HttpSession sesion = null;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		sesion = request.getSession();
		usuario = (Usuario) sesion.getAttribute("usuario");
		
		if (usuario != null) {
			if (usuario.getNombre().equals("")) {
				response.sendRedirect("/registro.do");
			} else {
				String mostrarTabla = "entrada";
				if (request.getParameter("tabla") != null)
					mostrarTabla = request.getParameter("tabla");
				
				Conexion cn = new Conexion();

				request.setAttribute("mostrarTabla", mostrarTabla);
				request.setAttribute("departamentos", cn.leerDepartamentos());
				request.setAttribute("areas", cn.leerAreas());
				request.setAttribute("centros", cn.leerCentros());
				request.setAttribute("ubicaciones", cn.leerUbicaciones());
				request.setAttribute("proveedores", cn.leerProveedores());
				request.setAttribute("marcas", cn.leerMarcas());
				request.setAttribute("calidades", cn.leerCalidades());
				
				request.setAttribute("entradas", cn.leerEntradas());
//				request.setAttribute("salidas", cn.leerSalidas());
				
				request.setAttribute("usuario", usuario);
				
				request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
				
				cn.cerrarConexion();
			}
		} else {
			response.sendRedirect("/login.do");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
