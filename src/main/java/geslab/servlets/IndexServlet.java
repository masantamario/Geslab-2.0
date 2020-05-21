package geslab.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import geslab.database.Conexion;
import geslab.database.modelo.Area;
import geslab.database.modelo.Centro;
import geslab.database.modelo.Departamento;
import geslab.database.modelo.Usuario;

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
				Conexion cn = new Conexion();
				ArrayList<Departamento> departamentos = cn.leerDepartamentos();
				ArrayList<Centro> centros = cn.leerCentros();
				ArrayList<Area> areas = cn.leerAreas();
				cn.cerrarConexion();
				
				request.setAttribute("departamentos", departamentos);
				request.setAttribute("areas", areas);
				request.setAttribute("centros", centros);
				request.setAttribute("usuario", usuario);
				request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
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
