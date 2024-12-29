package controladores;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.dao.UsuarioDAO;
import modelo.entidades.Usuario;

@WebServlet("/LoginController")
public class LoginController extends HttpServlet{

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
	
	private void ruteador(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Logica del control
		String ruta = (req.getParameter("ruta") == null) ? "solicitarIniciar" : req.getParameter("ruta");

		switch (ruta) {
		case "solicitarIniciar":
			this.solicitarIniciar(req, resp);
			break;
		case "iniciarSesion":
			this.iniciarSesion(req, resp);
			break;
		case "mostrarPantallaPrincipal":
			this.mostrarPantallaPrincipal(req, resp);
			break;
	
		}
	}
	
	private void solicitarIniciar(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.sendRedirect("jsp/login.jsp");
	}
	private void mostrarPantallaPrincipal(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.sendRedirect("jsp/menuPrincipal.jsp");
		System.out.println("Entro al menu principal");
	}
	
	private void iniciarSesion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nombreUsuario = req.getParameter("nombreUsuario");
		String clave = req.getParameter("clave");

		Usuario u;
		HttpSession session = req.getSession();
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		try {
			u = usuarioDAO.validarCredenciales(nombreUsuario, clave);
			if (u != null) {
				session.setAttribute("usuario", u);
				//resp.sendRedirect("LoginController?ruta=solicitarIniciar");
				req.getRequestDispatcher("LoginController?ruta=mostrarPantallaPrincipal").forward(req, resp);
				
			} else {
				resp.sendRedirect("jsp/login.jsp");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
