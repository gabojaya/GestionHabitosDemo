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

		String ruta = (req.getParameter("ruta") == null) ? "listar" : req.getParameter("ruta");

		switch (ruta) {
		case "listarHabitos":
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

	private void obtenerHabito(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		int id = Integer.parseInt(req.getParameter("idhabito"));

		HabitoDAO hdao = new HabitoDAO();
		try {
			Habito habito = hdao.obtenerHabitoPorId(id);
			resp.addIntHeader("idhabito", habito.getIdHabito());
			resp.addHeader("nombre", habito.getNombre());
			resp.addHeader("categoria", habito.getCategoria());
			resp.addIntHeader("frecuencia", habito.getFrecuencia());
			resp.addHeader("tipoMedicion", habito.getTipoMedicion());
			resp.addIntHeader("cantidadTotal", habito.getCantidadTotal());
			if (habito.getCantidadTotal() == 0) {
				resp.addHeader("tiempoTotal", habito.getTiempoTotal().toString());
			}
			getServletContext().getRequestDispatcher("/jsp/menuPrincipal.jsp").forward(req, resp);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void listarHabitosUsuario(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		HttpSession session = req.getSession();
		Usuario usuario = (Usuario) session.getAttribute("usuario");

		if (usuario != null) {
			int idUsuario = usuario.getIdUsuario();

			try {

				HabitoDAO habitoDAO = new HabitoDAO();

				List<Habito> habitos = habitoDAO.obtenerHabitosPorUsuario(idUsuario);

				req.setAttribute("habitos", habitos);

				getServletContext().getRequestDispatcher("/jsp/menuPrincipal.jsp").forward(req, resp);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {

			resp.sendRedirect("login.jsp");
		}

	}

	private void eliminarHabito(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		Meta meta = (Meta) session.getAttribute("meta");
		int idm = meta.getIdMeta();

		int idh = Integer.parseInt(req.getParameter("idhab"));

		HabitoDAO hdao = new HabitoDAO();
		try {
			hdao.eliminarHabito(idh);
			resp.sendRedirect("HabitoController?ruta=listarHabitos&idmeta=" + idm);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void ingresarDatosModificacionHabito(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		HttpSession session = req.getSession();
		Meta meta = (Meta) session.getAttribute("meta");
		int idm = meta.getIdMeta();
		int idh = Integer.parseInt(req.getParameter("idhab"));

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

			if (tiempo == null || tiempo.trim().isEmpty()) {
				tiempo = null;
			} else {

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
			resp.sendRedirect("EstadisticaController?ruta=actualizarEstadisticas&idmeta=" + idm + "&idhab=" + idh);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void ingresarDatosHabito(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		HttpSession session = req.getSession();
		int id = (int) session.getAttribute("idmeta");
		Meta meta = (Meta) session.getAttribute("meta");

		SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
		String tiempo = req.getParameter("tiempoTotal");

		String horario = req.getParameter("horario");
		Habito h = new Habito();
		h.setMetaAsociada(meta);
		h.setNombre(req.getParameter("nombre"));
		h.setCategoria(req.getParameter("categoria"));
		h.setTipoMedicion(req.getParameter("tipoMedicion"));
		h.setFrecuencia(Integer.parseInt(req.getParameter("frecuencia")));

		String cantidadTotalParam = req.getParameter("cantidadTotal");
		if (cantidadTotalParam == null || cantidadTotalParam.trim().isEmpty()) {
			h.setCantidadTotal(0);
		} else {
			h.setCantidadTotal(Integer.parseInt(cantidadTotalParam));
		}
		h.setEstado(true);
		try {

			if (tiempo == null || tiempo.trim().isEmpty()) {
				tiempo = null;
			} else {

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

			req.setAttribute("idHabito", h.getIdHabito());

			req.getRequestDispatcher("EstadisticaController?ruta=crearEstadistica").forward(req, resp);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void listarHabitos(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		int id = Integer.parseInt(req.getParameter("idmeta"));
		MetaDAO metaDAO = new MetaDAO();
		Meta meta = metaDAO.obtenerMetaPorId(id);

		req.setAttribute("idmeta", id);
		List<Habito> habs;
		HabitoDAO hdao = new HabitoDAO();
		HttpSession session = req.getSession();
		session.setAttribute("idmeta", id);
		session.setAttribute("meta", meta);

		try {
			habs = hdao.obtenerHabitos(id);

			req.setAttribute("habito", habs);
			session.setAttribute("idmeta", id);

			getServletContext().getRequestDispatcher("/jsp/menuPrincipal.jsp").forward(req, resp);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
