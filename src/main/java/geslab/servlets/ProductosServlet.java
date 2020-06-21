package geslab.servlets;

import java.net.URL;
import java.io.IOException;
import java.math.BigDecimal;
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
import geslab.database.user.Peligro;
import geslab.database.user.Pictograma;
import geslab.database.user.Producto;
import geslab.database.user.Prudencia;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/productos.do")
public class ProductosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Usuario usuario = null;
	private HttpSession sesion = null;

	private HttpServletRequest request = null;
	private Conexion cn = null;

	// Variables producto
	private String cas, nombre, formula, formula_des, n_einecs, n_ec, precauciones, msds;
	private BigDecimal peso_mol;
	ArrayList<Peligro> peligros = new ArrayList<Peligro>();
	ArrayList<Prudencia> prudencias = new ArrayList<Prudencia>();
	ArrayList<Pictograma> pictogramas = new ArrayList<Pictograma>();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		sesion = request.getSession();
		usuario = (Usuario) sesion.getAttribute("usuario");

		if (usuario != null) {
			if (usuario.getNombre().equals("")) {
				response.sendRedirect("/registro.do");
			} else {

				Conexion cn = new Conexion();

				request.setAttribute("productos", cn.leerProductos());
				request.setAttribute("peligros", cn.leerPeligros());
				request.setAttribute("prudencias", cn.leerPrudencias());
				request.setAttribute("pictogramas", cn.leerPictogramas());
				request.setAttribute("usuario", usuario);
				request.setAttribute("mensaje", sesion.getAttribute("mensaje"));

				cn.cerrarConexion();

				request.getRequestDispatcher("/WEB-INF/productos.jsp").forward(request, response);
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
		Producto producto = null;
		cn = new Conexion();

		try {
			leerParametrosProducto();
			switch (accion) {
			case "insertar":
				producto = new Producto(cas, nombre, formula, formula_des, peso_mol, n_einecs, n_ec, precauciones, msds,
						peligros, prudencias, pictogramas);
				cn.insertarProducto(producto);
				break;

			case "editar":
				producto = new Producto(codigo, nombre, formula, formula_des, peso_mol, n_einecs, n_ec, precauciones,
						msds, peligros, prudencias, pictogramas);
				cn.updateProducto(producto);
				break;
			}

		} catch (Exception msg) {
			sesion.setAttribute("mensaje", msg.getMessage());
		}
		cn.cerrarConexion();
		response.sendRedirect("/productos.do");
	}

	private void leerParametrosProducto() throws Exception {
		try {
			cas = request.getParameter("insertar-cas");
			if (cas.equals(""))
				throw new Exception("Campo CAS obligatorio");

			nombre = request.getParameter("insertar-nombre");
			if (nombre.equals(""))
				throw new Exception("Campo nombre obligatorio");

			formula = request.getParameter("insertar-formula");
			if (formula.equals(""))
				throw new Exception("Campo fórmula obligatorio");

			formula_des = request.getParameter("insertar-f_des");

			String pm = request.getParameter("insertar-peso");
			peso_mol = pm.equals("") || pm.equals("null") ? null : new BigDecimal(pm);

			n_einecs = request.getParameter("insertar-einecs");
			n_ec = request.getParameter("insertar-ec");
			precauciones = request.getParameter("insertar-precauciones");
			msds = request.getParameter("insertar-msds");
			if (!msds.equals("") && !isValid(msds))
				throw new Exception("MSDS tiene que ser una URL");

			leerPeligrosPrudencias();
		} catch (Throwable exception) {
			if (exception.getClass().toString().equals("class java.lang.Exception")) {
				throw new Exception(exception.getMessage());
			} else {
				throw new Exception("Parámetros de producto incorrectos");
			}
		}

	}

	private void leerPeligrosPrudencias() {
		String[] pelString = request.getParameterValues("insertar-peligros");
		String[] pruString = request.getParameterValues("insertar-prudencias");
		String[] picString = request.getParameterValues("insertar-pictogramas");
		List<String> pelList = new ArrayList<String>();
		List<String> pruList = new ArrayList<String>();
		List<String> picList = new ArrayList<String>();

		if (pelString != null)
			pelList = Arrays.asList(pelString);
		if (pruString != null)
			pruList = Arrays.asList(pruString);
		if (picString != null)
			picList = Arrays.asList(picString);

		ArrayList<String> pelLeidos = new ArrayList<String>(pelList);
		ArrayList<String> pruLeidas = new ArrayList<String>(pruList);
		ArrayList<String> picLeidas = new ArrayList<String>(picList);

		peligros.clear();
		for (String pel : pelLeidos) {
			for (Peligro p : cn.leerPeligros()) {
				if (pel.equals(p.getFrase())) {
					peligros.add(p);
					break;
				}
			}
		}

		prudencias.clear();
		for (String pru : pruLeidas) {
			for (Prudencia p : cn.leerPrudencias()) {
				if (pru.equals(p.getFrase())) {
					prudencias.add(p);
					break;
				}
			}
		}

		pictogramas.clear();
		for (String pic : picLeidas) {
			for (Pictograma p : cn.leerPictogramas()) {
				if (pic.equals(p.getReferencia())) {
					pictogramas.add(p);
					break;
				}
			}
		}

	}

	public static boolean isValid(String url) {
		try {
			new URL(url).toURI();
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

}
