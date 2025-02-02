package controladores;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.dao.EstadisticaDAO;
import modelo.dao.HabitoDAO;
import modelo.dao.MetaDAO;
import modelo.entidades.Estadistica;
import modelo.entidades.Habito;
import modelo.entidades.Meta;
import modelo.entidades.Usuario;

@WebServlet("/HabitoController")
public class HabitoController extends HttpServlet {

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
		case "listar":
			this.listarHabitos(req, resp);
			break;
		case "ingresarDatosHabito":
			this.ingresarDatosHabito(req, resp);
			break;
		case "ingresarDatosModificacionHabito":
			this.ingresarDatosModificacionHabito(req, resp);
			break;
		case "eliminarHabito":
			this.eliminarHabito(req, resp);
			break;
		case "listarHabitosUsuario":
			this.listarHabitosUsuario(req, resp);
			break;
		case "obtenerHabito":
			this.obtenerHabito(req, resp);
			break;
		}

	}
	
	private void obtenerHabito(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		System.out.println("Entro a obtener habito");
		int id = Integer.parseInt(req.getParameter("idhabito"));
		System.out.println(id);
		HabitoDAO hdao = new HabitoDAO();
		try {
			Habito habito = hdao.obtenerHabitoPorId(id);
			resp.addIntHeader("idhabito", habito.getIdHabito());
			resp.addHeader("nombre", habito.getNombre());
			resp.addHeader("categoria", habito.getCategoria());
			resp.addIntHeader("frecuencia", habito.getFrecuencia());
			resp.addHeader("tipoMedicion", habito.getTipoMedicion());
			resp.addIntHeader("cantidadTotal", habito.getCantidadTotal());
			if(habito.getCantidadTotal()== 0) {
				resp.addHeader("tiempoTotal", habito.getTiempoTotal().toString());
			}
			getServletContext().getRequestDispatcher("/jsp/menuPrincipal.jsp").forward(req, resp);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	private void listarHabitosUsuario(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		System.out.println("Entro a listarHabitosUsuarios");
		HttpSession session = req.getSession();
	    Usuario usuario = (Usuario) session.getAttribute("usuario");
	    
	    if (usuario != null) {
	        int idUsuario = usuario.getIdUsuario();
	        
	        try {
	            
	            HabitoDAO habitoDAO = new HabitoDAO();
	            
	            // Obtener la lista de hábitos del usuario
	            List<Habito> habitos = habitoDAO.obtenerHabitosPorUsuario(idUsuario);
	            
	            
	            req.setAttribute("habitos", habitos);
	            
	         
	            getServletContext().getRequestDispatcher("/jsp/menuPrincipal.jsp").forward(req, resp);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    } else {
	        // Si no hay usuario en la sesión, redirigir al login
	        resp.sendRedirect("login.jsp");
	    }
		
	}

	private void eliminarHabito(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Se entro a eliminar habito");
		HttpSession session = req.getSession();
		Meta meta = (Meta) session.getAttribute("meta");
		int idm = meta.getIdMeta();

		int idh = Integer.parseInt(req.getParameter("idhab"));
		System.out.println(idm);
		System.out.println(idh);
		HabitoDAO hdao = new HabitoDAO();
		try {
			hdao.eliminarHabito(idh);
			resp.sendRedirect("HabitoController?ruta=listar&idmeta=" + idm);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void ingresarDatosModificacionHabito(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("Se entro a modificar habito");
		HttpSession session = req.getSession();
		Meta meta = (Meta) session.getAttribute("meta");
		int idm = meta.getIdMeta();
		int idh = Integer.parseInt(req.getParameter("idhab"));
		System.out.println(idm);
		System.out.println(idh);
		SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
		String tiempo = req.getParameter("tiempoTotal");
		Habito h = new Habito();
		h.setIdHabito(idh);
		h.setMetaAsociada(meta);
		h.setNombre(req.getParameter("nombre"));
		h.setCategoria(req.getParameter("categoria"));
		h.setTipoMedicion(req.getParameter("tipoMedicion"));
		h.setFrecuencia(Integer.parseInt(req.getParameter("frecuencia")));
		h.setCantidadTotal(Integer.parseInt(req.getParameter("cantidadTotal")));
		try {
			// Validar si el valor de tiempo es nulo o vacío antes de procesarlo
			if (tiempo == null || tiempo.trim().isEmpty()) {
				tiempo = null;
			} else {
				// Si el formato es "mm:ss", agregar ":00" para convertirlo a "hh:mm:ss"
				tiempo = tiempo + ":00";
			}
			Date ti = format.parse(tiempo);
			java.sql.Time sqlTiempo = new java.sql.Time(ti.getTime());
			h.setTiempoTotal(sqlTiempo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		HabitoDAO hdao = new HabitoDAO();
		try {
			hdao.modificarHabito(h);
			resp.sendRedirect("HabitoController?ruta=listar&idmeta=" + idm);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void ingresarDatosHabito(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("Se entro a crear habito");
		// int id = Integer.parseInt(req.getParameter("idmeta"));
		HttpSession session = req.getSession();
		int id = (int) session.getAttribute("idmeta");
		Meta meta = (Meta) session.getAttribute("meta");
		System.out.println("Esta es la idMeta por session: " + session.getAttribute("idmeta"));

		SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
		String tiempo = req.getParameter("tiempoTotal");
		System.out.println("Tiempo Total:" + tiempo);
		String horario = req.getParameter("horario");
		Habito h = new Habito();
		h.setMetaAsociada(meta);
		h.setNombre(req.getParameter("nombre"));
		h.setCategoria(req.getParameter("categoria"));
		h.setTipoMedicion(req.getParameter("tipoMedicion"));
		h.setFrecuencia(Integer.parseInt(req.getParameter("frecuencia")));
		// Validación para cantidadTotal
		String cantidadTotalParam = req.getParameter("cantidadTotal");
		if (cantidadTotalParam == null || cantidadTotalParam.trim().isEmpty()) {
			h.setCantidadTotal(0);
		} else {
			h.setCantidadTotal(Integer.parseInt(cantidadTotalParam));
		}
		h.setEstado(true);
		try {

			// Validar si el valor de tiempo es nulo o vacío antes de procesarlo
			if (tiempo == null || tiempo.trim().isEmpty()) {
				tiempo = null;
			} else {
				// Si el formato es "mm:ss", agregar ":00" para convertirlo a "hh:mm:ss"
				tiempo = tiempo + ":00";
			}

			Date ti = format.parse(tiempo);
			java.sql.Time sqlTiempo = new java.sql.Time(ti.getTime());
			h.setTiempoTotal(sqlTiempo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		meta.getHabitos().add(h);
		HabitoDAO hdao = new HabitoDAO();

		try {
			hdao.crearHabito(h);
			System.out.println("La id del habito creado es " + h.getIdHabito());

			req.setAttribute("idHabito", h.getIdHabito());
			// resp.sendRedirect("HabitoController?ruta=listar&idmeta="+id);

			req.getRequestDispatcher("EstadisticaController?ruta=crearEstadistica").forward(req, resp);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void listarHabitos(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Se entro a listar habito");
		int id = Integer.parseInt(req.getParameter("idmeta"));
		MetaDAO metaDAO = new MetaDAO();
		Meta meta = metaDAO.obtenerMetaPorId(id);
		System.out.println("Con id de meta: " + id);
		System.out.println(req.getParameter("idmeta"));

		req.setAttribute("idmeta", id);
		System.out.println(req.getParameter("idmeta") + "Hola");
		List<Habito> habs;
		HabitoDAO hdao = new HabitoDAO();
		HttpSession session = req.getSession();
		session.setAttribute("idmeta", id);
		session.setAttribute("meta", meta);

		System.out.println("Esta es la idMeta por session; " + session.getAttribute("idmeta"));

		try {
			habs = hdao.obtenerHabitos(id);
			System.out.println("Esta es la lista de habitos" + habs);
			for (Habito habito : habs) {
				System.out.println("ID: " + habito.getIdHabito() + ", Nombre: " + habito.getNombre());
			}

			req.setAttribute("habito", habs);
			session.setAttribute("idmeta", id);
			System.out.println("idMeta configurada en sesión: " + session.getAttribute("idmeta"));

			getServletContext().getRequestDispatcher("/jsp/menuPrincipal.jsp").forward(req, resp);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
