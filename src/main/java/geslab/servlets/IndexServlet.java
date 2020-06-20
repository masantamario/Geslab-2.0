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
import geslab.database.user.Salida;
import geslab.database.user.Ubicacion;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/index.do")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Usuario usuario = null;
	private HttpSession sesion = null;

	private HttpServletRequest request = null;
//	private HttpServletResponse response = null;
	private Conexion cn = null;

	// Variables ficha
	private String calidad, proveedor, marca;
	private Ubicacion ubicacion = null;
	private Producto producto = null;

	// Variables entrada-salida
	private Date fecha, caducidad;
	private String lote, g_ml;
	private int unidades;
	private BigDecimal capacidad;
	private boolean residuo;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		sesion = request.getSession();
		usuario = (Usuario) sesion.getAttribute("usuario");
		
		if (usuario != null) {
			if (usuario.getNombre().equals("")) {
				response.sendRedirect("/registro.do");
			} else {

				Conexion cn = new Conexion();

				request.setAttribute("departamentos", cn.leerDepartamentos());
				request.setAttribute("areas", cn.leerAreas());
				request.setAttribute("centros", cn.leerCentros());
				request.setAttribute("ubicaciones", cn.leerUbicaciones(usuario));
				request.setAttribute("proveedores", cn.leerProveedores());
				request.setAttribute("marcas", cn.leerMarcas());
				request.setAttribute("calidades", cn.leerCalidades());
				request.setAttribute("productos", cn.leerProductos());
				request.setAttribute("fichas", cn.leerFichas(usuario));
				request.setAttribute("entradas", cn.leerEntradas(usuario));
				request.setAttribute("salidas", cn.leerSalidas(usuario));
				request.setAttribute("usuario", usuario);
//				request.setAttribute("mensaje", request.getParameter("mensaje"));

				request.setAttribute("mensaje", sesion.getAttribute("mensaje"));
				
				cn.cerrarConexion();

				request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
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

		Ficha ficha = null;
		String mensaje = null;
		
		try {
			switch (accion) {
			case "insertar":
				leerParametrosFicha();
				ficha = new Ficha(0, producto, capacidad, g_ml, calidad, ubicacion, marca, proveedor, caducidad, lote,
						residuo, 0);
				cn.insertarFicha(ficha);
	
				break;
			case "entrada":
				leerParametrosEntSal();
				ficha = cn.leerFicha(Integer.parseInt(codigo));
				Entrada entrada = new Entrada(0, ficha, fecha, unidades, usuario.getIdusuario());
				cn.insertarEntrada(entrada);
				break;
	
			case "salida":
				leerParametrosEntSal();
				ficha = cn.leerFicha(Integer.parseInt(codigo));
				Salida salida = new Salida(0, ficha, fecha, unidades, usuario.getIdusuario());
				cn.insertarSalida(salida);
				break;
	
			}
		}catch(Exception msg) {
			mensaje = msg.getMessage();
			sesion.setAttribute("mensaje", mensaje);
		}

		cn.cerrarConexion();
		response.sendRedirect("/index.do");

//		request.getRequestDispatcher("/WEB-INF/index.jsp").include(request, response);
//		getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").include(request, response);
	
//		String url = null;
//		url = mensaje == null ? "/index.do" : "/index.do?mensaje=" + mensaje;
//		response.sendRedirect(url);

	}

	private void leerParametrosFicha() {
		for (Producto p : cn.leerProductos()) {
			String nombre = request.getParameter("insertar-producto");
			if (p.getNombre().equals(nombre)) {
				producto = p;
				break;
			}
		}
		capacidad = new BigDecimal(request.getParameter("insertar-cpcd"));
		g_ml = request.getParameter("insertar-g-ml");
		for (Ubicacion u : cn.leerUbicaciones(usuario)) {
			String nombre = request.getParameter("insertar-ubicacion");
			if (u.getNombre().equals(nombre)) {
				ubicacion = u;
				break;
			}
		}
		marca = request.getParameter("insertar-marca");
		proveedor = request.getParameter("insertar-proveedor");
		calidad = request.getParameter("insertar-calidad");
		lote = request.getParameter("insertar-lote");
		caducidad = Date.valueOf(request.getParameter("insertar-caducidad"));
		residuo = (request.getParameter("insertar-residuo") != null);
	}

	private void leerParametrosEntSal() {
		fecha = Date.valueOf(request.getParameter("insertar-fecha"));
		unidades = Integer.parseInt(request.getParameter("insertar-unidades"));

	}

}
