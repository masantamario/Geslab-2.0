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
	private Conexion cn = null;

	// Variables proveedor
	private String nombre, tlfn, direccion, fax, email;
	ArrayList<String> marcas = null;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.request = request;
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
				request.setAttribute("mensaje", sesion.getAttribute("mensaje"));

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

		String accion = request.getParameter("accion");
		String codigo = request.getParameter("codigo");
		cn = new Conexion();

		try {
			leerParametrosProveedor();
			switch (accion) {
			case "insertar":
				cn.insertarProveedor(new Proveedor(0, nombre, direccion, tlfn, fax, email, marcas));
				break;
			case "editar":
				String n = cn.leerProveedor(Integer.valueOf(codigo)).getNombre();
				if (n.equals(nombre) || (!n.equals(nombre) && !cn.existeProveedor(nombre))) {
					cn.updateProveedor(
							new Proveedor(Integer.valueOf(codigo), nombre, direccion, tlfn, fax, email, marcas));
				} else {
					throw new Exception("Nombre no válido");
				}
				break;
			}
		} catch (Exception msg) {
			sesion.setAttribute("mensaje", msg.getMessage());
		}
		cn.cerrarConexion();
		response.sendRedirect("/proveedores.do");
	}

	private void leerParametrosProveedor() throws Exception {
		try {
			nombre = request.getParameter("insertar-nombre");
			if (nombre.equals(""))
				throw new Exception("Campo nombre obligatorio");

			direccion = request.getParameter("insertar-direccion");
			tlfn = request.getParameter("insertar-tlfn");
			fax = request.getParameter("insertar-fax");
			email = request.getParameter("insertar-mail");

			String[] marcasString = request.getParameterValues("insertar-marcas");
			List<String> marcasList = new ArrayList<String>();
			if (marcasString != null)
				marcasList = Arrays.asList(marcasString);
			marcas = new ArrayList<String>(marcasList);
			
		} catch (Throwable exception) {
			if (exception.getClass().toString().equals("class java.lang.Exception")) {
				throw new Exception(exception.getMessage());
			} else {
				throw new Exception("Parámetros de proveedor incorrectos");
			}
		}
	}

}
