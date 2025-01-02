package controladores;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.dao.UsuarioDAO;
import modelo.entidades.Usuario;

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.ruteador(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.ruteador(req, resp);
	}

	private void ruteador(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String ruta = (req.getParameter("ruta") == null) ? "solicitarIniciar" : req.getParameter("ruta");

		switch (ruta) {
		case "solicitarIniciar":
			this.mostrarIniciar(req, resp);
			break;
		case "solicitarRegistro":
			this.mostrarRegistro(req, resp);
			break;
		case "iniciarSesion":
			this.iniciarSesion(req, resp);
			break;
		case "mostrarPantallaPrincipal":
			this.mostrarPantallaPrincipal(req, resp);
			break;
		case "registrarUsuario":
			this.registrarUsuario(req, resp);
			break;

		}
	}

	private void mostrarIniciar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Entro por iniciar");
		req.setAttribute("form", "iniciar");
		req.getRequestDispatcher("jsp/login.jsp").forward(req, resp);
	}

	private void mostrarRegistro(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("Entro por registrar");
		req.setAttribute("form", "registrar");
		req.getRequestDispatcher("jsp/login.jsp").forward(req, resp);
	}

	private void iniciarSesion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Entro a iniciar sesion");
		String nombreUsuario = req.getParameter("nombreUsuario");
		String clave = req.getParameter("clave");

		Usuario u;
		HttpSession session = req.getSession();
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		try {
			u = usuarioDAO.validarCredenciales(nombreUsuario, clave);
			if (u != null) {
				session.setAttribute("usuario", u);
				req.getRequestDispatcher("LoginController?ruta=mostrarPantallaPrincipal").forward(req, resp);

			} else {
				resp.sendRedirect("LoginController?ruta=solicitarIniciar");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resp.sendRedirect("LoginController?ruta=solicitarIniciar");
		}
	}

	private void mostrarPantallaPrincipal(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("jsp/menuPrincipal.jsp").forward(req, resp); 
		System.out.println("Entro al menu principal");
	}

	private void registrarUsuario(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String nombre = req.getParameter("nombreN");
		String apellido = req.getParameter("apellidoN");
		String nombreUsuario = req.getParameter("nombreUsuarioN");
		String email = req.getParameter("email");
		String clave = req.getParameter("clave");

		Usuario usuario = new Usuario(nombre, apellido, nombreUsuario, email, clave);

		UsuarioDAO usuarioDAO = new UsuarioDAO();
		boolean registrado;
		try {
			registrado = usuarioDAO.crearUsuario(usuario);

			if (registrado) {
				req.setAttribute("nombreUsuario", nombreUsuario);
				req.setAttribute("clave", clave);
				req.getRequestDispatcher(
						"LoginController?ruta=iniciarSesion&nombreUsuario=" + nombreUsuario + "&clave=" + clave)
						.forward(req, resp);

			} else {
				resp.sendRedirect("error.jsp");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
