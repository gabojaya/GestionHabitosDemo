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
import modelo.dao.MetaDAO;
import modelo.entidades.Meta;

@WebServlet("/MetaController")
public class MetaController extends HttpServlet {
	
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
		String ruta = (req.getParameter("ruta") == null) ? "listar" : req.getParameter("ruta");

		switch (ruta) {
		case "solicitarMetas":
			System.out.println("Llamando a obtenerMetas");
			this.obtenerMetas(req, resp);
			break;
			
		case "eliminarMeta":
			System.out.println("Llamando a eliminarMeta");
			this.eliminarMeta(req, resp);
			break;
		
		}
	}
	
	
	private void eliminarMeta(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Entro a eliminar meta");
		int idMeta = Integer.parseInt(req.getParameter("idMeta"));
		System.out.println("Esta es la id de la Meta"+idMeta);
		int idUsuario = Integer.parseInt(req.getParameter("idUsuario"));
		System.out.println("Esta es la id del Usuario"+idUsuario);
		
		
		// Instanciar el DAO y ejecutar el método de eliminación
	    MetaDAO metaDAO = new MetaDAO();
	    
	    try {
	        metaDAO.eliminarMeta(idMeta);
	        
	        // Responder con un mensaje de éxito
	        resp.setContentType("application/json");
	        resp.setCharacterEncoding("UTF-8");
	        resp.getWriter().write("{\"status\":\"success\"}");
	        System.out.println("Se elimino la meta con id "+ idMeta);
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al eliminar la meta");
	    }
		
		
		
	}

	private void obtenerMetas(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    int idUsuario = Integer.parseInt(req.getParameter("idUsuario"));
	    
	    List<Meta> metas; 
	    MetaDAO metaDAO = new MetaDAO(); 
	    HttpSession session = req.getSession();
	    
	    try {
	        metas = metaDAO.obtenerMetasPorUsuario(idUsuario);
	        session.setAttribute("metas", metas);
	        System.out.println("Metas obtenidas: " + metas.size());
	        System.out.println("Ingreso a obtenerMetas");

	        // Configurar la respuesta como JSON
	        resp.setContentType("application/json");
	        resp.setCharacterEncoding("UTF-8");
	        
	        // Construir el JSON manualmente
	        StringBuilder json = new StringBuilder();
	        json.append("[");
	        
	        for (int i = 0; i < metas.size(); i++) {
	            Meta meta = metas.get(i);
	            json.append("{");
	            json.append("\"idMeta\":").append(meta.getIdMeta()).append(",");
	            json.append("\"nombre\":\"").append(meta.getNombre()).append("\",");
	            json.append("\"descripcion\":\"").append(meta.getDescripcion()).append("\",");
	            json.append("\"fechaInicio\":\"").append(meta.getFechaInicio()).append("\",");
	            json.append("\"fechaFin\":\"").append(meta.getFechaFin()).append("\",");
	            json.append("\"progreso\":").append(meta.getProgreso());
	            json.append("}");
	            
	            if (i < metas.size() - 1) {
	                json.append(","); // Si no es el último elemento, agrega una coma
	            }
	        }
	        
	        json.append("]");
	        
	        // Escribir el JSON en la respuesta
	        resp.getWriter().write(json.toString());
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al obtener las metas");
	    }
	}

	
	
	
}
