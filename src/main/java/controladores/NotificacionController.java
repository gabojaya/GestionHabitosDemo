package controladores;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.dao.HabitoDAO;
import modelo.dao.RecordatorioDAO;
import modelo.entidades.Habito;
import modelo.entidades.Meta;
import modelo.entidades.Recordatorio;
import modelo.entidades.Usuario;

@WebServlet("/NotificacionController")
public class NotificacionController extends HttpServlet {

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

		String ruta = (req.getParameter("ruta") == null) ? "asignarRecordatorio" : req.getParameter("ruta");

		switch (ruta) {
		case "asignarRecordatorio":

			this.asignarRecordatorio(req, resp);
			break;
		case "listarRecordatorios":

			this.listarRecordatorios(req, resp);
			break;
		case "marcarLeido":

			this.marcarLeido(req, resp);
			break;

		}
	}

	private void marcarLeido(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		int idRecordatorio = Integer.parseInt(req.getParameter("idRecordatorio"));
		//Instancia del DAO
		RecordatorioDAO notificacionDAO = new RecordatorioDAO();
		Recordatorio recordatorio = notificacionDAO.obtenerRecordatorioPorId(idRecordatorio);
		if (recordatorio != null) {
			recordatorio.setEstado(false);
			boolean actualizado = notificacionDAO.actualizarRecordatorio(recordatorio);

		}

		resp.sendRedirect("NotificacionController?ruta=listarRecordatorios");

	}

	private void listarRecordatorios(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		HttpSession session = req.getSession();
		Usuario usuario = (Usuario) session.getAttribute("usuario");

		int idUsuario = usuario.getIdUsuario();

		RecordatorioDAO notificacionDAO = new RecordatorioDAO();
		List<Recordatorio> recordatoriosActivos = notificacionDAO.obtenerRecordatoriosActivosPorUsuario(idUsuario);

		req.setAttribute("recordatorios", recordatoriosActivos);

		getServletContext().getRequestDispatcher("/jsp/menuPrincipal.jsp").forward(req, resp);

	}

	private void asignarRecordatorio(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		int idHabito = Integer.parseInt(req.getParameter("idHabito"));

		HabitoDAO habitoDAO = new HabitoDAO();
		Habito habito;
		HttpSession session = req.getSession();
		Meta meta = (Meta) session.getAttribute("meta");
		Date fechaInicioStr = meta.getFechaInicio();
		Date fechaFinStr = meta.getFechaFin();

		try {
			habito = habitoDAO.obtenerHabitoPorId(idHabito);

			List<Time> horarios = new ArrayList<>();
			for (int i = 1;; i++) {
				String horaParam = req.getParameter("horaHorario" + i);
				if (horaParam == null)
					break;
				horarios.add(Time.valueOf(horaParam + ":00"));
			}

			RecordatorioDAO notificacionDAO = new RecordatorioDAO();
			for (Time hora : horarios) {
				Recordatorio recordatorio = new Recordatorio();
				recordatorio.setMensaje("Recordatorio para hábito " + idHabito);
				recordatorio.setHora(hora);
				recordatorio.setEstado(true);
				recordatorio.setFechaInicio(fechaInicioStr);
				recordatorio.setFechaFin(fechaFinStr);
				recordatorio.setRepetir(true);
				recordatorio.setHabitoAsociado(habito);

				habito.getRecordatorios().add(recordatorio);

				notificacionDAO.crearRecordatorio(recordatorio);
			}
			habitoDAO.actualizarListaHabito(habito);

			req.getRequestDispatcher("EjecucionController?ruta=crearEjecuciones&idHabito=" + idHabito).forward(req,
					resp);

		} catch (Exception e) {
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al asignar recordatorios");
		}

	}

}
