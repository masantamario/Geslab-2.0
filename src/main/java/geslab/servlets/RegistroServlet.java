package geslab.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import geslab.database.Conexion;
import geslab.database.admin.Usuario;

/**
 * Servlet implementation class RegistroServlet
 */
@WebServlet("/registro.do")
public class RegistroServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession sesion = null;
	private Usuario usuario = null;
       
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	sesion = request.getSession();
		usuario = (Usuario) sesion.getAttribute("usuario");
		
		if(usuario != null) {
			request.getRequestDispatcher("/WEB-INF/registro.jsp").forward(request, response);
		}else {
			response.sendRedirect("/login.do");
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Conexion cn = new Conexion();
		
		try {
			String nombre = request.getParameter("nombre");
			String correo = request.getParameter("correo");
			String pass = cn.encriptar(request.getParameter("password"));
		
			usuario.setNombre(nombre);
			usuario.setMail(correo);
			cn.cambiarContrasena(usuario, pass);
			
			System.out.println(usuario.getNombre());
			System.out.println(usuario.getMail());
			
			cn.updateUsuario(usuario);
			
			sesion.setAttribute("usuario", cn.leerUsuario(usuario.getUsuario()));
		}catch(Exception msg) {
			sesion.setAttribute("mensaje", msg.getMessage());
		}
		
		cn.cerrarConexion();
		response.sendRedirect("/index.do");
	}

}
