package controladores;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
			this.solicitarMetas(req, resp);
			break;
			
		case "eliminarMeta":
			System.out.println("Llamando a eliminarMeta");
			this.eliminarMeta(req, resp);
			break;
		case "agregarMeta":
			System.out.println("Llamando a agregarMeta");
			this.agregarMeta(req, resp);
			break;
		case "modificarMeta":
			System.out.println("Llamando a modificarMeta");
			this.modificarMeta(req, resp);
			break;
		
		}
	}
	

	private void modificarMeta(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    System.out.println("Entro a editar meta");
	    int idMeta = Integer.parseInt(req.getParameter("idMeta"));
	    System.out.println("La id de la meta que se edita es: "+idMeta);
	    String nombre = req.getParameter("nombre-meta");
	    System.out.println("El nombre de la meta a editar es : "+nombre);
	    String descripcion = req.getParameter("descripcion-meta");
	    System.out.println("La descripcion de la meta que se edita es: "+descripcion);
	    String fechaInicioStr = req.getParameter("fecha-inicio");
	    System.out.println("La fechaInicio de la meta que se edita es: "+fechaInicioStr);
	    String fechaFinStr = req.getParameter("fecha-fin");
	    System.out.println("La fechaFin de la meta que se edita es: "+fechaFinStr);

	    try {

	        // Definir un SimpleDateFormat para el formato yyyy-MM-dd
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	        // Convertir las fechas a formato java.util.Date
	        Date fechaInicioUtil = dateFormat.parse(fechaInicioStr);
	        Date fechaFinUtil = dateFormat.parse(fechaFinStr);

	        // Convertir java.util.Date a java.sql.Date
	        java.sql.Date fechaInicio = new java.sql.Date(fechaInicioUtil.getTime());
	        java.sql.Date fechaFin = new java.sql.Date(fechaFinUtil.getTime());

	        // Calcular días objetivo
	        long diasObjetivo = java.time.temporal.ChronoUnit.DAYS.between(
	            fechaInicio.toLocalDate(),
	            fechaFin.toLocalDate()
	        );

	        if (diasObjetivo < 0) {
	            req.setAttribute("error", "La fecha de inicio no puede ser posterior a la fecha de fin.");
	            req.getRequestDispatcher("error.jsp").forward(req, resp);
	            return;
	        }

	        // Crear objeto Meta
	        Meta meta = new Meta();
	        meta.setIdMeta(idMeta);
	        meta.setNombre(nombre);
	        meta.setDescripcion(descripcion);
	        meta.setFechaInicio(fechaInicio);
	        meta.setFechaFin(fechaFin);
	        //meta.setProgreso(0.0); // Progreso inicial
	        meta.setEstado(true); // Activa por defecto
	        meta.setDiasObjetivo((int) diasObjetivo); // Convertimos a int

	        // Llamar al DAO para modificar la meta
	        MetaDAO metaDAO = new MetaDAO();
	        boolean isUpdated = metaDAO.modificarMeta(meta);

	        if (isUpdated) {
	            System.out.println("Meta modificada correctamente con id: " + idMeta);
	            req.getRequestDispatcher("HabitoController?ruta=listar&idmeta="+ idMeta).forward(req, resp);
	        } else {
	            req.setAttribute("error", "Error al modificar la meta");
	            req.getRequestDispatcher("error.jsp").forward(req, resp); // Redirige a una página de error
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        req.setAttribute("error", "Error en el procesamiento de la solicitud");
	        req.getRequestDispatcher("error.jsp").forward(req, resp); // Redirige a una página de error
	    }
	}


	private void agregarMeta(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    System.out.println("Entrando a agregarMeta");

	    try {
	        int idUsuario = Integer.parseInt(req.getParameter("idUsuario"));
	        System.out.println("La id del usuario es: "+ idUsuario);
	        String nombre = req.getParameter("nombre-meta");
	        String descripcion = req.getParameter("descripcion-meta");

	        // Obtener las fechas en formato dd/MM/yyyy desde el formulario
	        String fechaInicioStr = req.getParameter("fecha-inicio");
	        String fechaFinStr = req.getParameter("fecha-fin");

	        // Definir un SimpleDateFormat para el formato dd/MM/yyyy
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	        // Convertir las fechas a formato java.util.Date
	        Date fechaInicioUtil = dateFormat.parse(fechaInicioStr);
	        Date fechaFinUtil = dateFormat.parse(fechaFinStr);

	        // Convertir java.util.Date a java.sql.Date
	        java.sql.Date fechaInicio = new java.sql.Date(fechaInicioUtil.getTime());
	        java.sql.Date fechaFin = new java.sql.Date(fechaFinUtil.getTime());

	        // Calcular días objetivo
	        long diasObjetivo = java.time.temporal.ChronoUnit.DAYS.between(
	            fechaInicio.toLocalDate(),
	            fechaFin.toLocalDate()
	        );

	        if (diasObjetivo < 0) {
	            req.setAttribute("error", "La fecha de inicio no puede ser posterior a la fecha de fin.");
	            req.getRequestDispatcher("error.jsp").forward(req, resp);
	            return;
	        }

	        // Crear objeto Meta
	        Meta meta = new Meta();
	        meta.setIdUsuario(idUsuario);
	        meta.setNombre(nombre);
	        meta.setDescripcion(descripcion);
	        meta.setFechaInicio(fechaInicio);
	        meta.setFechaFin(fechaFin);
	        meta.setProgreso(0.0); // Progreso inicial
	        meta.setEstado(true); // Activa por defecto
	        meta.setDiasObjetivo((int) diasObjetivo); // Convertimos a int

	        // Llamar al DAO para agregar la meta
	        MetaDAO metaDAO = new MetaDAO();
	        boolean isInserted = metaDAO.insertarMeta(meta);

	        if (isInserted) {
	            System.out.println("Meta agregada correctamente con id: "+ meta.getIdMeta());
	            req.getRequestDispatcher("HabitoController?ruta=listar&idmeta="+ meta.getIdMeta()).forward(req, resp);
	        } else {
	            req.setAttribute("error", "Error al agregar la meta");
	            req.getRequestDispatcher("error.jsp").forward(req, resp);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        req.setAttribute("error", "Error en el procesamiento de la solicitud");
	        req.getRequestDispatcher("error.jsp").forward(req, resp);
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

	private void solicitarMetas(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Entro a obtener metas");
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
