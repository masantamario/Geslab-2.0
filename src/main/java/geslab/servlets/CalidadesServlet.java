package geslab.servlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import geslab.database.Conexion;
import geslab.database.admin.Usuario;
import geslab.database.user.Entrada;
import geslab.database.user.Ficha;
import geslab.database.user.Producto;
import geslab.database.user.Ubicacion;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/calidades.do")
public class CalidadesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Usuario usuario = null;
	private HttpSession sesion = null;

	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
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

				request.setAttribute("calidades", cn.leerCalidades());
				request.setAttribute("usuario", usuario);
				
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
		this.response = response;
		String accion = request.getParameter("accion");
		int codigo = Integer.parseInt(request.getParameter("codigo"));
		cn = new Conexion();
		System.out.println("Accion: " + accion);
		System.out.println("Código: " + codigo);

		cn.cerrarConexion();
		request.getRequestDispatcher("/WEB-INF/calidades.jsp").forward(request, response);
	}


	

}
