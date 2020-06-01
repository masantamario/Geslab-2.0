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
import geslab.database.user.Ubicacion;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/index.do")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Usuario usuario = null;
	private HttpSession sesion = null;
	private Conexion cn = null;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		sesion = request.getSession();
		usuario = (Usuario) sesion.getAttribute("usuario");

		if (usuario != null) {
			if (usuario.getNombre().equals("")) {
				response.sendRedirect("/registro.do");
			} else {
				String mostrarTabla = "entrada";
				if (request.getParameter("tabla") != null)
					mostrarTabla = request.getParameter("tabla");

				Conexion cn = new Conexion();

				request.setAttribute("mostrarTabla", mostrarTabla);
				request.setAttribute("departamentos", cn.leerDepartamentos());
				request.setAttribute("areas", cn.leerAreas());
				request.setAttribute("centros", cn.leerCentros());
				request.setAttribute("ubicaciones", cn.leerUbicaciones());
				request.setAttribute("proveedores", cn.leerProveedores());
				request.setAttribute("marcas", cn.leerMarcas());
				request.setAttribute("calidades", cn.leerCalidades());
				request.setAttribute("productos", cn.leerProductos());

				request.setAttribute("entradas", cn.leerEntradas());
//				request.setAttribute("salidas", cn.leerSalidas());

				request.setAttribute("usuario", usuario);

				request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);

				cn.cerrarConexion();
			}
		} else {
			response.sendRedirect("/login.do");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String accion = request.getParameter("accion");
		String elemento = request.getParameter("elemento");
		int codigo = Integer.parseInt(request.getParameter("codigo"));
		cn = new Conexion();
		System.out.println("Accion: " + accion);
		System.out.println("Elemento: " + elemento);
		System.out.println("Código: " + codigo);

		String calidad = request.getParameter("insertar-calidad");
		Ubicacion ubicacion = null;
		for (Ubicacion u : cn.leerUbicaciones()) {
			String nombre = request.getParameter("insertar-ubicacion");
			if (u.getNombre().equals(nombre)) {
				ubicacion = u;
				break;
			}
		}
		String proveedor = request.getParameter("insertar-proveedor");
		String marca = request.getParameter("insertar-marca");
		Producto producto = null;
		for (Producto p : cn.leerProductos()) {
			String nombre = request.getParameter("insertar-producto");
			if (p.getNombre().equals(nombre)) {
				producto = p;
				break;
			}
		}

		Date fecha = Date.valueOf(request.getParameter("insertar-fecha"));
		Date caducidad = Date.valueOf(request.getParameter("insertar-caducidad"));
		String lote = request.getParameter("insertar-lote");
		BigDecimal unidades = new BigDecimal(request.getParameter("insertar-uds"));
		BigDecimal capacidad = new BigDecimal(request.getParameter("insertar-cpcd"));
		String g_ml = request.getParameter("insertar-g-ml");
		Boolean residuo = (request.getParameter("insertar-residuo") != null);
		
		Ficha ficha = null;
		Entrada entrada = null;
		if(accion.equals("insertar")) {
			ficha = cn.existeFicha(new Ficha(0, calidad, ubicacion, proveedor, marca, producto));
			entrada = new Entrada(0, ficha, fecha, caducidad, lote, unidades, capacidad, g_ml, residuo);
			if (ficha.getCodficha() == 0) {
				cn.insertarFicha(ficha);
				ficha = cn.existeFicha(ficha);
			}
			
		}else if(accion.equals("editar")) {
			for (Entrada e : cn.leerEntradas()) {
				if (e.getCodentrada() == codigo) {
					ficha = e.getFicha();
					break;
				}
			}
			entrada = new Entrada(codigo, ficha, fecha, caducidad, lote, unidades, capacidad, g_ml, residuo);
		}
		
		cn.insertarEntrada(entrada);
		cn.cerrarConexion();
		response.sendRedirect("/login.do");
	}

}
