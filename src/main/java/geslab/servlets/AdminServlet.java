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
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private Conexion cn = null;

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		request.setAttribute("nombre", usuario.getNombre());
		String mostrarTabla = "centro";
		if (request.getParameter("tabla") != null)
			mostrarTabla = request.getParameter("tabla");

		Conexion cn = new Conexion();
		ArrayList<Usuario> usuarios = cn.leerUsuarios();
		ArrayList<Departamento> departamentos = cn.leerDepartamentos();
		ArrayList<Centro> centros = cn.leerCentros();
		ArrayList<Area> areas = cn.leerAreas();
		Rol[] roles = Rol.values();

		cn.cerrarConexion();

		request.setAttribute("mostrarTabla", mostrarTabla);
		request.setAttribute("usuarios", usuarios);
		request.setAttribute("departamentos", departamentos);
		request.setAttribute("areas", areas);
		request.setAttribute("centros", centros);
		request.setAttribute("roles", roles);
		request.getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.request = request;
		this.response = response;
		String accion = request.getParameter("accion");
		String elemento = request.getParameter("elemento");
		String codigo = request.getParameter("codigo");
		cn = new Conexion();
		System.out.println("Accion: " + accion);
		System.out.println("Elemento: " + elemento);
		System.out.println("Código: " + codigo);

		System.out.println("Insertando " + elemento);
		switch (elemento) {
		case "usuario":
			response = accionUsuario(accion, codigo);
			break;
		case "area":
			response = accionArea(accion, codigo);
			break;
		case "departamento":
			response = accionDepartamento(accion, codigo);
			break;
		case "centro":
			response = accionCentro(accion, codigo);
			break;
		}

		response.sendRedirect("/admin.do?tabla=" + elemento);

	}

	private HttpServletResponse accionCentro(String accion, String codigo) {
		int cod = codigo.equals("nuevo") ? 0 : Integer.parseInt(codigo);
		String c = (String) request.getParameter("centro-" + codigo);
		Centro centro = new Centro(cod, c);
		cn.insertarCentro(centro);
		return response;
	}

	private HttpServletResponse accionDepartamento(String accion, String codigo) {
		int cod = codigo.equals("nuevo") ? 0 : Integer.parseInt(codigo);
		String d = (String) request.getParameter("departamento-" + codigo);
		System.out.println("-- Departamento: " + d);
		cn.insertarDepartamento(new Departamento(cod, d));
		System.out.println("Departamento añadido");
		return response;
	}

	private HttpServletResponse accionArea(String accion, String codigo) {
		int cod = codigo.equals("nuevo") ? 0 : Integer.parseInt(codigo);
		String a = (String) request.getParameter("area-" + codigo);
		String d = (String) request.getParameter("dpto-area-" + codigo);
		System.out.println("-- Area: " + a);
		System.out.println("-- Departamento: " + d);
		cn.insertarArea(new Area(cod, a, d));
		System.out.println("Departamento añadido");

		return response;
	}

	private HttpServletResponse accionUsuario(String accion, String codigo) {
		int id = codigo.equals("nuevo") ? 0 : Integer.parseInt(codigo);
		String u = (String) request.getParameter("usuario-" + codigo);
		int r = Integer.parseInt(request.getParameter("rol-usuario-" + codigo));
		String ar = (String) request.getParameter("area-usuario-" + codigo);
		boolean f = (request.getParameter("federada-usuario-" + codigo) != null);
		boolean a = (request.getParameter("activo-usuario-" + codigo) != null);

		System.out.println("-- USUARIO: " + u);
		System.out.println("-- ROL: " + r);
		System.out.println("-- FEDERADA: " + f);
		System.out.println("-- ACTIVO: " + a);
		cn.insertarUsuario(new Usuario(id, u, r, ar, f, a));
		System.out.println("Usuario añadido");

		return response;
	}
}
