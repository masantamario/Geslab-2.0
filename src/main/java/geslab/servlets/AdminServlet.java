package geslab.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import geslab.database.Conexion;
import geslab.database.modelo.Usuario;

import javax.servlet.ServletException;

@WebServlet(urlPatterns = "/admin.do")
public class AdminServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		request.setAttribute("nombre", usuario.getNombre());
		
		Conexion cn = new Conexion();
		request.setAttribute("usuarios", cn.leerUsuarios());
		request.setAttribute("areas", cn.leerAreas());
		cn.cerrarConexion();
		request.getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {		

	}
}
