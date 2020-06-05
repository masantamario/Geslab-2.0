package geslab.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import geslab.database.Conexion;
import geslab.database.admin.Usuario;
import geslab.database.user.Proveedor;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/proveedores.do")
public class ProveedoresServlet extends HttpServlet {
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

				request.setAttribute("proveedores", cn.leerProveedores());
				request.setAttribute("marcas", cn.leerMarcas());
				request.setAttribute("usuario", usuario);

				cn.cerrarConexion();

				request.getRequestDispatcher("/WEB-INF/proveedores.jsp").forward(request, response);
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
		String codigo = request.getParameter("codigo");
		cn = new Conexion();
		System.out.println("Accion: " + accion);
		System.out.println("Código: " + codigo);

		String nombre = request.getParameter("insertar-nombre");
		String direccion = request.getParameter("insertar-direccion");
		String tlfn = request.getParameter("insertar-tlfn");
		String fax = request.getParameter("insertar-fax");
		String email = request.getParameter("insertar-mail");
		ArrayList<String> marcas = new ArrayList<String>(Arrays.asList(request.getParameterValues("insertar-marcas")));
		System.out.println(marcas);
		switch (accion) {
		case "insertar":
			cn.insertarProveedor(new Proveedor(0, nombre, direccion, tlfn, fax, email, marcas));
			break;
		case "editar":
//			cn.insertarProveedor(new Proveedor(0, nombre, direccion, tlfn, fax, email));
			break;

		}
		cn.cerrarConexion();
		response.sendRedirect("/proveedores.do");
	}

}
