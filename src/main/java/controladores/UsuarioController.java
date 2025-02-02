package controladores;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.dao.UsuarioDAO;
import modelo.entidades.Usuario;

@WebServlet("/UsuarioController")
public class UsuarioController extends HttpServlet {

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
		
		String ruta = (req.getParameter("ruta") == null) ? "listar" : req.getParameter("ruta");

		switch (ruta) {
		case "modificarUsuario":
			this.modificarUsuario(req, resp);
			break;
		case "solicitarModificarUsuario":
			this.solicitarModificarUsuario(req, resp);
			break;

		case "solicitarUsuario":
			this.solicitarUsuario(req, resp);
			break;
		case "eliminarUsuario":
			this.eliminarUsuario(req, resp);
			break;
		case "obtenerUsuario":
			this.obtenerUsuario(req, resp);
			break;
		}

	}

	private void obtenerUsuario(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		int id = Integer.parseInt(req.getParameter("idUsuario"));
	    UsuarioDAO usuarioDAO = new UsuarioDAO();
	    
	    try {

	        Usuario usuario = usuarioDAO.obtenerUsuarioPorId(id);
	        

	        resp.addIntHeader("idUsuario", usuario.getIdUsuario());
	        resp.addHeader("nombre", usuario.getNombre());
	        resp.addHeader("apellido", usuario.getApellido());
	        resp.addHeader("nombreUsuario", usuario.getNombreUsuario());
	        resp.addHeader("email", usuario.getEmail());
	        resp.addHeader("clave", usuario.getClave());
	        
	
	        getServletContext().getRequestDispatcher("/jsp/editarUsuario.jsp").forward(req, resp);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		
	}

	private void eliminarUsuario(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		

	    String idUsuario = req.getParameter("idUsuario");
	    
	    if (idUsuario != null) {
	        try {
	            
	            int usuarioId = Integer.parseInt(idUsuario);
	            
	            
	            UsuarioDAO usuarioDAO = new UsuarioDAO();
	            boolean eliminado = usuarioDAO.eliminarUsuario(usuarioId);
	            
	          
	            if (eliminado) {
	            	resp.sendRedirect("LoginController?ruta=solicitarIniciar");
	            } else {
	            
	                resp.sendRedirect("error.jsp"); 
	            }
	        } catch (NumberFormatException e) {
	        	
	            
	            resp.sendRedirect("error.jsp");
	        }
	    } else {
	    	
	        resp.sendRedirect("error.jsp"); 
	    }
		
	}

	private void solicitarUsuario(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		HttpSession session = req.getSession();
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		if (usuario == null) {
			
			resp.sendRedirect("login.jsp");
			return;
		}

		
		int idUsuario = usuario.getIdUsuario();

		UsuarioDAO usuarioDAO = new UsuarioDAO();

		
		Usuario usuarioActualizado = usuarioDAO.obtenerUsuarioPorId(idUsuario);
		session.setAttribute("usuario", usuarioActualizado);

		getServletContext().getRequestDispatcher("/jsp/menuPrincipal.jsp").forward(req, resp);
		
	}

	private void solicitarModificarUsuario(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		if (usuario == null) {
			
			resp.sendRedirect("login.jsp");
			return;
		}

		
		int idUsuario = usuario.getIdUsuario();

		UsuarioDAO usuarioDAO = new UsuarioDAO();

		
		Usuario usuarioModificado = usuarioDAO.obtenerUsuarioPorId(idUsuario);
		session.setAttribute("usuario", usuarioModificado);

		getServletContext().getRequestDispatcher("/jsp/menuPrincipal.jsp").forward(req, resp);

	}

	private void modificarUsuario(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		if (usuario == null) {
			
			resp.sendRedirect("login.jsp");
			return;
		}

		
		int idUsuario = usuario.getIdUsuario();

		
		UsuarioDAO usuarioDAO = new UsuarioDAO();

		
		Usuario usuarioModificado = usuarioDAO.obtenerUsuarioPorId(idUsuario);

		
		String nombre = req.getParameter("nombreM");
		String apellido = req.getParameter("apellidoM");
		String nombreUsuario = req.getParameter("nombreUsuarioM");
		String email = req.getParameter("emailM");
		String clave = req.getParameter("claveM");


		usuarioModificado.setNombre(nombre);
		usuarioModificado.setApellido(apellido);
		usuarioModificado.setNombreUsuario(nombreUsuario);
		usuarioModificado.setEmail(email);
		usuarioModificado.setClave(clave);

		try {
			boolean actualizado = usuarioDAO.modificarUsuario(usuarioModificado);
			if (actualizado) {

				req.getRequestDispatcher("LoginController?ruta=mostrarPantallaPrincipal").forward(req, resp);
			} else {

				resp.sendRedirect("error.jsp?mensaje=Error al actualizar el usuario");
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

}
