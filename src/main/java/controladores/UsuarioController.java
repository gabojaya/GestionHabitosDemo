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
		// Logica del control
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
	        // Obtener el usuario por ID
	        Usuario usuario = usuarioDAO.obtenerUsuarioPorId(id);
	        
	        // Enviar los datos al frontend mediante cabeceras
	        resp.addIntHeader("idUsuario", usuario.getIdUsuario());
	        resp.addHeader("nombre", usuario.getNombre());
	        resp.addHeader("apellido", usuario.getApellido());
	        resp.addHeader("nombreUsuario", usuario.getNombreUsuario());
	        resp.addHeader("email", usuario.getEmail());
	        resp.addHeader("clave", usuario.getClave());
	        
	        // Redirigir al formulario de edición
	        getServletContext().getRequestDispatcher("/jsp/editarUsuario.jsp").forward(req, resp);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		
	}

	private void eliminarUsuario(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		// Obtener el id del usuario desde la URL
	    String idUsuario = req.getParameter("id");
	    
	    if (idUsuario != null) {
	        try {
	            // Convertir el id en un número entero
	            int usuarioId = Integer.parseInt(idUsuario);
	            
	            // Llamar al DAO para eliminar el usuario
	            UsuarioDAO usuarioDAO = new UsuarioDAO();
	            boolean eliminado = usuarioDAO.eliminarUsuario(usuarioId);
	            
	            // Redirigir a una página de éxito o a la lista de usuarios
	            if (eliminado) {
	            	resp.sendRedirect("LoginController?ruta=solicitarIniciar");
	            } else {
	                resp.sendRedirect("error.jsp"); // Redirige a una página de error si no se eliminó correctamente
	            }
	        } catch (NumberFormatException e) {
	            // Manejo de errores si el id no es válido
	            resp.sendRedirect("error.jsp");
	        }
	    } else {
	        resp.sendRedirect("error.jsp"); // Si no se pasa un id, redirigir a una página de error
	    }
		
	}

	private void solicitarUsuario(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		HttpSession session = req.getSession();
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		if (usuario == null) {
			// Redirige al login si el usuario no está en sesión
			resp.sendRedirect("login.jsp");
			return;
		}

		// Obtener el ID del usuario de la sesión
		int idUsuario = usuario.getIdUsuario();

		UsuarioDAO usuarioDAO = new UsuarioDAO();

		// Cargar el usuario desde la base de datos
		Usuario usuarioActualizado = usuarioDAO.obtenerUsuarioPorId(idUsuario);
		session.setAttribute("usuario", usuarioActualizado);

		getServletContext().getRequestDispatcher("/jsp/menuPrincipal.jsp").forward(req, resp);
		
	}

	private void solicitarModificarUsuario(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		if (usuario == null) {
			// Redirige al login si el usuario no está en sesión
			resp.sendRedirect("login.jsp");
			return;
		}

		// Obtener el ID del usuario de la sesión
		int idUsuario = usuario.getIdUsuario();

		UsuarioDAO usuarioDAO = new UsuarioDAO();

		// Cargar el usuario desde la base de datos
		Usuario usuarioModificado = usuarioDAO.obtenerUsuarioPorId(idUsuario);
		session.setAttribute("usuario", usuarioModificado);

		getServletContext().getRequestDispatcher("/jsp/menuPrincipal.jsp").forward(req, resp);

	}

	private void modificarUsuario(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		if (usuario == null) {
			// Redirige al login si el usuario no está en sesión
			resp.sendRedirect("login.jsp");
			return;
		}

		// Obtener el ID del usuario de la sesión
		int idUsuario = usuario.getIdUsuario();

		// Crear una instancia de UsuarioDAO
		UsuarioDAO usuarioDAO = new UsuarioDAO();

		// Cargar el usuario desde la base de datos
		Usuario usuarioModificado = usuarioDAO.obtenerUsuarioPorId(idUsuario);

		// Obtener los nuevos datos desde el formulario
		String nombre = req.getParameter("nombreM");
		String apellido = req.getParameter("apellidoM");
		String nombreUsuario = req.getParameter("nombreUsuarioM");
		String email = req.getParameter("emailM");
		String clave = req.getParameter("claveM");

		// Actualizar los valores del usuario
		usuarioModificado.setNombre(nombre);
		usuarioModificado.setApellido(apellido);
		usuarioModificado.setNombreUsuario(nombreUsuario);
		usuarioModificado.setEmail(email);
		usuarioModificado.setClave(clave);

		try {
			boolean actualizado = usuarioDAO.modificarUsuario(usuarioModificado);
			if (actualizado) {
				// Redirige a una página de éxito
				req.getRequestDispatcher("LoginController?ruta=mostrarPantallaPrincipal").forward(req, resp);
			} else {
				// Redirige a una página de error
				resp.sendRedirect("error.jsp?mensaje=Error al actualizar el usuario");
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

}
