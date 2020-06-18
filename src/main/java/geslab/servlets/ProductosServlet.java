package geslab.servlets;

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
//	private HttpServletResponse response = null;
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
//		this.response = response;
		String accion = request.getParameter("accion");
		String codigo = request.getParameter("codigo");
		cn = new Conexion();
		System.out.println("Accion: " + accion);
		System.out.println("Código: " + codigo);

		leerParametrosProducto();
		switch (accion) {
		case "insertar":
			Producto productoNuevo = null;
			if (cn.leerProducto(cas) == null) {
				productoNuevo = new Producto(cas, nombre, formula, formula_des, peso_mol, n_einecs, n_ec, precauciones,
						msds, peligros, prudencias, pictogramas);
				cn.insertarProducto(productoNuevo);
			}
			break;
			
		case "editar":
			Producto producto = cn.leerProducto(cas);
			Producto productoEdit= new Producto(cas, nombre, formula, formula_des, peso_mol, n_einecs, n_ec, precauciones,
					msds, peligros, prudencias, pictogramas);
			cn.updateProducto(producto, productoEdit);
			break;
		}
		cn.cerrarConexion();
		response.sendRedirect("/productos.do");
	}

	private void leerParametrosProducto() {
		cas = request.getParameter("insertar-cas");
		nombre = request.getParameter("insertar-nombre");
		formula = request.getParameter("insertar-formula");
		formula_des = request.getParameter("insertar-f_des");
		peso_mol = new BigDecimal(request.getParameter("insertar-peso"));
		n_einecs = request.getParameter("insertar-einecs");
		n_ec = request.getParameter("insertar-ec");
		precauciones = request.getParameter("insertar-precauciones");
		msds = request.getParameter("insertar-msds");
		leerPeligrosPrudencias();
	}
	
	private void leerPeligrosPrudencias() {
		String[] pelString = request.getParameterValues("insertar-peligros");
		String[] pruString = request.getParameterValues("insertar-prudencias");
		String[] picString = request.getParameterValues("insertar-pictogramas");
		List<String> pelList = new ArrayList<String>();
		List<String> pruList = new ArrayList<String>();
		List<String> picList = new ArrayList<String>();
		
		if(pelString != null) pelList = Arrays.asList(pelString);
		if(pruString != null) pruList = Arrays.asList(pruString);
		if(picString != null) picList = Arrays.asList(picString);
		
		ArrayList<String> pelLeidos = new ArrayList<String>(pelList);
		ArrayList<String> pruLeidas = new ArrayList<String>(pruList);
		ArrayList<String> picLeidas = new ArrayList<String>(picList);
		
		peligros.clear();
		for(String pel : pelLeidos) {
			for(Peligro p : cn.leerPeligros()) {
				if(pel.equals(p.getFrase())) {
					peligros.add(p);
					break;
				}
			}
		}
		
		prudencias.clear();
		for(String pru : pruLeidas) {
			for(Prudencia p : cn.leerPrudencias()) {
				if(pru.equals(p.getFrase())) {
					prudencias.add(p);
					break;
				}
			}
		}
		
		pictogramas.clear();
		for(String pic : picLeidas) {
			for(Pictograma p : cn.leerPictogramas()) {
				if(pic.equals(p.getReferencia())) {
					pictogramas.add(p);
					break;
				}
			}
		}
		
		
	}

}
