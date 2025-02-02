package controladores;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.dao.EjecucionDAO;
import modelo.dao.EstadisticaDAO;
import modelo.dao.HabitoDAO;
import modelo.dao.RecordatorioDAO;
import modelo.dao.UsuarioDAO;
import modelo.entidades.Ejecucion;
import modelo.entidades.Estadistica;
import modelo.entidades.Habito;
import modelo.entidades.Meta;
import modelo.entidades.Recordatorio;
import modelo.entidades.Usuario;

@WebServlet("/EjecucionController")
public class EjecucionController extends HttpServlet {

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

		String ruta = (req.getParameter("ruta") == null) ? "listarEjecuciones" : req.getParameter("ruta");

		switch (ruta) {
		case "listarEjecuciones":

			this.listarEjecuciones(req, resp);
			break;
		case "crearEjecuciones":

			this.crearEjecuciones(req, resp);
			break;
		case "registrarEjecucion":

			this.registrarEjecucion(req, resp);
			break;
		}
	}

	private void registrarEjecucion(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		int idEjecucion = Integer.parseInt(req.getParameter("idEjecucion"));
		int cantidadRealizada = 0;
		Time tiempoCompletado = null;
		Time tiempoTotalT = null;

		int cantidaTotal = Integer.parseInt(req.getParameter("cantidadEjecHabito"));

		if (req.getParameter("tiempoEjecHabito") != null && (req.getParameter("tiempoEjecHabito") != "")) {
			String tiempoTotal = req.getParameter("tiempoEjecHabito");
			tiempoTotalT = Time.valueOf(tiempoTotal);

		}

		if (req.getParameter("cantidadActual") != null && !req.getParameter("cantidadActual").isEmpty()) {
			cantidadRealizada = Integer.parseInt(req.getParameter("cantidadActual"));
		}
		if (req.getParameter("tiempoTranscurrido") != null && !req.getParameter("tiempoTranscurrido").isEmpty()) {
			String tiempoRealizado = req.getParameter("tiempoTranscurrido");
			tiempoCompletado = Time.valueOf(tiempoRealizado);
			cantidadRealizada = 0;
		}

		EjecucionDAO ejecucionDAO = new EjecucionDAO();
		Ejecucion ejecucion = ejecucionDAO.buscarEjecucionPorId(idEjecucion);
		int idHabito = ejecucion.getHabito().getIdHabito();

		if (ejecucion != null) {

			ejecucion.setCantidadCompleta(cantidadRealizada);
			ejecucion.setTiempoCompletado(tiempoCompletado);
			ejecucion.setCantidadTotal(cantidaTotal);
			ejecucion.setTiempoTotal(tiempoTotalT);
			ejecucion.setEstado(false);
			;

			ejecucionDAO.actualizarEjecucion(ejecucion);

			Recordatorio recordatorio = ejecucion.getRecordatorio();
			if (recordatorio != null) {
				recordatorio.setEstado(false);

				RecordatorioDAO notificacionDAO = new RecordatorioDAO();
				notificacionDAO.actualizarRecordatorio(recordatorio);
			}

			EstadisticaDAO estadisticasDAO = new EstadisticaDAO();
			Estadistica estadisticas = estadisticasDAO.obtenerEstadisticasPorHabito(idHabito);
			if (estadisticas != null) {

				int cantidadAcumulada = estadisticas.getCantidadAcumulada() + cantidadRealizada;
				estadisticas.setCantidadAcumulada(cantidadAcumulada);

				if (estadisticas.getTiempoAcumulado() != null) {

					long tiempoAcumuladoSegundos = 0;
					if (estadisticas.getTiempoAcumulado() != null) {

						tiempoAcumuladoSegundos = convertirTimeASegundos(estadisticas.getTiempoAcumulado());
					}

					long tiempoCompletadoSegundos = convertirTimeASegundos(tiempoCompletado);

					long tiempoTotalSegundos = tiempoAcumuladoSegundos + tiempoCompletadoSegundos;

					String tiempoFinalStr = convertirSegundosAFormato(tiempoTotalSegundos);

					Time tiempoAcumuladoFinal = Time.valueOf(tiempoFinalStr);
					estadisticas.setTiempoAcumulado(tiempoAcumuladoFinal);
				} else {

				}

				double progresoAcumulado = 0;

				if (estadisticas.getTiempoFinalEsperado() != null) {

					Time tiempoAcumulado = estadisticas.getTiempoAcumulado();
					Time tiempoFinalEsperado = estadisticas.getTiempoFinalEsperado();

					long tiempoAcumuladoSegundos = convertirTimeASegundos(tiempoAcumulado);
					long tiempoFinalEsperadoSegundos = convertirTimeASegundos(tiempoFinalEsperado);

					if (tiempoFinalEsperadoSegundos != 0) {
						progresoAcumulado = (double) tiempoAcumuladoSegundos / tiempoFinalEsperadoSegundos * 100;
					}
				} else {

					if (estadisticas.getCantidadFinalEsperada() != 0) {
						progresoAcumulado = (double) cantidadAcumulada / estadisticas.getCantidadFinalEsperada() * 100;
					}
				}

				estadisticas.setProgresoAcumulado(progresoAcumulado);

				int totalEjecuciones = estadisticas.getTotalEjecuciones() + 1;
				estadisticas.setTotalEjecuciones(totalEjecuciones);

				estadisticasDAO.actualizarEstadisticas(estadisticas);

			}

		}
		resp.sendRedirect("EjecucionController?ruta=listarEjecuciones");

	}

	private long convertirTimeASegundos(Time time) {
		String timeStr = time.toString();
		String[] parts = timeStr.split(":");
		int horas = Integer.parseInt(parts[0]);
		int minutos = Integer.parseInt(parts[1]);
		int segundos = Integer.parseInt(parts[2]);
		return (horas * 3600) + (minutos * 60) + segundos;
	}

	private String convertirSegundosAFormato(long segundos) {
		int horas = (int) (segundos / 3600);
		segundos %= 3600;
		int minutos = (int) (segundos / 60);
		segundos %= 60;

		return String.format("%02d:%02d:%02d", horas, minutos, (int) segundos);
	}

	private void crearEjecuciones(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		int idHabito = Integer.parseInt(req.getParameter("idHabito"));

		HttpSession session = req.getSession();
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		Meta meta = (Meta) session.getAttribute("meta");
		int idUsuario = usuario.getIdUsuario();

		RecordatorioDAO notificacionDAO = new RecordatorioDAO();
		HabitoDAO habitoDao = new HabitoDAO();
		Habito habito = null;
		try {
			habito = habitoDao.obtenerHabitoPorId(idHabito);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Recordatorio> recordatorios = notificacionDAO.obtenerRecordatoriosPorHabito(idHabito);
		java.util.Date fechaActual = new java.util.Date();
		java.sql.Date fechaSQL = new java.sql.Date(fechaActual.getTime());

		if (recordatorios != null && !recordatorios.isEmpty()) {
			List<Ejecucion> ejecuciones = new ArrayList<>();
			EjecucionDAO ejecucionDAO = new EjecucionDAO();
			for (Recordatorio recordatorio : recordatorios) {
				Ejecucion ejecucion = new Ejecucion();
				ejecucion.setHabito(recordatorio.getHabitoAsociado());
				ejecucion.setUsuario(usuario);
				ejecucion.setEstado(true);
				ejecucion.setFecha(fechaSQL);
				ejecucion.setHora(recordatorio.getHora());
				ejecucion.setRecordatorio(recordatorio);

				ejecuciones.add(ejecucion);
				usuario.getEjecuciones().add(ejecucion);
				recordatorio.getEjecuciones().add(ejecucion);
				habito.getEjecuciones().add(ejecucion);

				ejecucionDAO.crearEjecucion(ejecucion);

			}

			UsuarioDAO usuarioDAO = new UsuarioDAO();
			usuarioDAO.actualizarListasUsuario(usuario);

			req.getRequestDispatcher("HabitoController?ruta=listarHabitos&idmeta=" + meta.getIdMeta()).forward(req,
					resp);
		} else {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND, "No se encontraron recordatorios para este hábito.");
		}
	}

	private void listarEjecuciones(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		Usuario usuario = (Usuario) session.getAttribute("usuario");

		int idUsuario = usuario.getIdUsuario();

		EjecucionDAO ejecucionDAO = new EjecucionDAO();

		List<Ejecucion> ejecuciones = ejecucionDAO.listarEjecucionesPorUsuario(idUsuario);

		req.setAttribute("ejecuciones", ejecuciones);
		getServletContext().getRequestDispatcher("/jsp/menuPrincipal.jsp").forward(req, resp);

	}

}
