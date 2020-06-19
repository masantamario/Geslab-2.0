package geslab.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import geslab.database.Conexion;
import geslab.database.admin.Usuario;
import geslab.database.user.Marca;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/marcas.do")
public class MarcasServlet extends HttpServlet {
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

				request.setAttribute("marcas", cn.leerMarcas());
				request.setAttribute("proveedores", cn.leerProveedores());
				request.setAttribute("usuario", usuario);

				cn.cerrarConexion();

				request.getRequestDispatcher("/WEB-INF/marcas.jsp").forward(request, response);
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
		String tlfn = request.getParameter("insertar-tlfn");
		String direccion = request.getParameter("insertar-direccion");

		String[] provString = request.getParameterValues("insertar-proveedores");
		List<String> provList = new ArrayList<String>();
		if (provString != null)
			provList = Arrays.asList(provString);
		ArrayList<String> proveedores = new ArrayList<String>(provList);

		switch (accion) {
		case "insertar":
			if (!cn.existeMarca(nombre))
				cn.insertarMarca(new Marca(0, nombre, tlfn, direccion, proveedores));
			break;
		case "editar":
			String n = cn.leerMarca(Integer.valueOf(codigo)).getNombre();
			if (n.equals(nombre) || (!n.equals(nombre) && !cn.existeMarca(nombre))) {
				cn.updateMarca(new Marca(Integer.valueOf(codigo), nombre, tlfn, direccion, proveedores));
			} else {
				System.out.println("Nombre no válido");
			}
			break;
		}
		cn.cerrarConexion();
		response.sendRedirect("/marcas.do");
	}

}
