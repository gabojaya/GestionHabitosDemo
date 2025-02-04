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
import modelo.dao.UsuarioDAO;
import modelo.entidades.Meta;
import modelo.entidades.Usuario;

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

	private void ruteador(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String ruta = (req.getParameter("ruta") == null) ? "listar" : req.getParameter("ruta");

		switch (ruta) {
		case "solicitarMetas":

			this.solicitarMetas(req, resp);
			break;

		case "eliminarMeta":

			this.eliminarMeta(req, resp);
			break;
		case "agregarMeta":

			this.agregarMeta(req, resp);
			break;
		case "modificarMeta":

			this.modificarMeta(req, resp);
			break;
		case "mostrarModificarMeta":

			this.mostrarModificarMeta(req, resp);
			break;

		}
	}

	private void mostrarModificarMeta(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		int id = Integer.parseInt(req.getParameter("idmeta"));

		MetaDAO metaDAO = new MetaDAO();

		Meta meta = metaDAO.obtenerMetaPorId(id);

		resp.addIntHeader("idmeta", meta.getIdMeta());
		resp.addHeader("nombre", meta.getNombre());
		resp.addHeader("descripcion", meta.getDescripcion());
		resp.addHeader("fechaInicio", meta.getFechaInicio().toString());
		resp.addHeader("fechaFin", meta.getFechaFin().toString());

		getServletContext().getRequestDispatcher("/jsp/menuPrincipal.jsp").forward(req, resp);

	}

	private void modificarMeta(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		int idMeta = Integer.parseInt(req.getParameter("idMeta"));
		HttpSession session = req.getSession();
		Usuario usuario = (Usuario) session.getAttribute("usuario");

		String nombre = req.getParameter("nombre-meta");

		String descripcion = req.getParameter("descripcion-meta");

		String fechaInicioStr = req.getParameter("fecha-inicio");

		String fechaFinStr = req.getParameter("fecha-fin");

		try {

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			Date fechaInicioUtil = dateFormat.parse(fechaInicioStr);
			Date fechaFinUtil = dateFormat.parse(fechaFinStr);

			java.sql.Date fechaInicio = new java.sql.Date(fechaInicioUtil.getTime());
			java.sql.Date fechaFin = new java.sql.Date(fechaFinUtil.getTime());

			long diasObjetivo = java.time.temporal.ChronoUnit.DAYS.between(fechaInicio.toLocalDate(),
					fechaFin.toLocalDate());

			if (diasObjetivo < 0) {
				req.setAttribute("error", "La fecha de inicio no puede ser posterior a la fecha de fin.");
				req.getRequestDispatcher("error.jsp").forward(req, resp);
				return;
			}

			Meta meta = new Meta();
			meta.setIdMeta(idMeta);
			meta.setNombre(nombre);
			meta.setDescripcion(descripcion);
			meta.setFechaInicio(fechaInicio);
			meta.setFechaFin(fechaFin);

			meta.setEstado(true);
			meta.setDiasObjetivo((int) diasObjetivo);
			meta.setUsuario(usuario);

			MetaDAO metaDAO = new MetaDAO();
			boolean isUpdated = metaDAO.modificarMeta(meta);

			if (isUpdated) {

				req.getRequestDispatcher("HabitoController?ruta=listarHabitos&idmeta=" + idMeta).forward(req, resp);
			} else {
				req.setAttribute("error", "Error al modificar la meta");
				req.getRequestDispatcher("error.jsp").forward(req, resp);
			}

		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("error", "Error en el procesamiento de la solicitud");
			req.getRequestDispatcher("error.jsp").forward(req, resp);
		}
	}

	private void agregarMeta(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		try {

			Usuario usuario = (Usuario) session.getAttribute("usuario");
			String nombre = req.getParameter("nombre-meta");
			String descripcion = req.getParameter("descripcion-meta");

			String fechaInicioStr = req.getParameter("fecha-inicio");
			String fechaFinStr = req.getParameter("fecha-fin");

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			Date fechaInicioUtil = dateFormat.parse(fechaInicioStr);
			Date fechaFinUtil = dateFormat.parse(fechaFinStr);

			java.sql.Date fechaInicio = new java.sql.Date(fechaInicioUtil.getTime());
			java.sql.Date fechaFin = new java.sql.Date(fechaFinUtil.getTime());

			long diasObjetivo = java.time.temporal.ChronoUnit.DAYS.between(fechaInicio.toLocalDate(),
					fechaFin.toLocalDate());

			if (diasObjetivo < 0) {
				req.setAttribute("error", "La fecha de inicio no puede ser posterior a la fecha de fin.");
				req.getRequestDispatcher("error.jsp").forward(req, resp);
				return;
			}

			Meta meta = new Meta();
			meta.setUsuario(usuario);
			meta.setNombre(nombre);
			meta.setDescripcion(descripcion);
			meta.setFechaInicio(fechaInicio);
			meta.setFechaFin(fechaFin);
			meta.setEstado(true);
			meta.setDiasObjetivo((int) diasObjetivo);

			MetaDAO metaDAO = new MetaDAO();
			boolean isInserted = metaDAO.insertarMeta(meta);

			if (isInserted) {

				req.getRequestDispatcher("HabitoController?ruta=listarHabitos&idmeta=" + meta.getIdMeta()).forward(req,
						resp);
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

		int idMeta = Integer.parseInt(req.getParameter("idMeta"));

		MetaDAO metaDAO = new MetaDAO();

		try {
			metaDAO.eliminarMeta(idMeta);
			req.getRequestDispatcher("MetaController?ruta=solicitarMetas").forward(req,
					resp);

		} catch (SQLException e) {
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al eliminar la meta");
		}

	}

	private void solicitarMetas(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		Usuario usuario = (Usuario) session.getAttribute("usuario");

		int idUsuario = usuario.getIdUsuario();

		List<Meta> metas;
		MetaDAO metaDAO = new MetaDAO();

		try {
			metas = metaDAO.obtenerMetasPorUsuario(idUsuario);
			session.setAttribute("metas", metas);

			req.setAttribute("metas", metas);

			getServletContext().getRequestDispatcher("/jsp/menuPrincipal.jsp").forward(req, resp);

		} catch (SQLException e) {
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al obtener las metas");
		}
	}

}
