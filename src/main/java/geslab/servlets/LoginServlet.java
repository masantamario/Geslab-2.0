package geslab.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import geslab.database.Conexion;
import geslab.database.admin.Rol;
import geslab.database.admin.Usuario;

import javax.servlet.ServletException;

@WebServlet(urlPatterns = "/login.do")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private HttpServletResponse response;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.response = response;
		HttpSession sesion = request.getSession();
		Usuario usuario = (Usuario) sesion.getAttribute("usuario");
		
		String accion = request.getParameter("accion");
		if (accion != null && accion.equals("logout")) {
			request.getSession().invalidate();
		}
		
		if (usuario == null) {
			request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
		} else {
			redirigirUsuario(usuario);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.response = response;

		Conexion cn = new Conexion();
		String u = request.getParameter("usuario");
		String p = cn.encriptar(request.getParameter("password"));
		Usuario usuario = cn.existeUsuario(u, p);
		cn.cerrarConexion();

		if (usuario != null) {
			System.out.println("Usuario válido");
			HttpSession session = request.getSession();
			session.setAttribute("usuario", usuario);
			redirigirUsuario(usuario);
		} else {
			System.out.println("Usuario inválido");
			request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
		}
	}

	private void redirigirUsuario(Usuario usuario) throws IOException {
		if (usuario.getRol() == Rol.ADMINISTRADOR) {
			response.sendRedirect("/admin.do");
		} else {
			response.sendRedirect("/index.do");
		}
	}
}
