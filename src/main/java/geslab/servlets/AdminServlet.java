package geslab.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import geslab.database.Conexion;
import geslab.database.modelo.*;

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
		ArrayList<Usuario> usuarios = cn.leerUsuarios();
		ArrayList<Area> areas = cn.leerAreas();
		Rol[] roles = Rol.values();

		cn.cerrarConexion();

		request.setAttribute("usuarios", usuarios);
		request.setAttribute("areas", areas);
		request.setAttribute("roles", roles);
		request.getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String eleccion = request.getParameter("btn-confirmar");
		Conexion cn = new Conexion();
		System.out.println("Añadiendo " + eleccion);

		String u = (String) request.getParameter("nuevo-usuario");
		int r = Integer.parseInt(request.getParameter("nuevo-rol"));
		boolean f = (request.getParameter("nuevo-federada") != null);
		boolean a = (request.getParameter("nuevo-activo") != null);
		
		System.out.println("-- USUARIO: " + u);
		System.out.println("-- ROL: " + r);
		System.out.println("-- FEDERADA: " + f);
		System.out.println("-- ACTIVO: " + a);
		cn.insertarUsuario(new Usuario(u, r, f, a));
		System.out.println("Usuario añadido");
//		request.getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
//		getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
		response.sendRedirect("/admin.do");

	}
}
