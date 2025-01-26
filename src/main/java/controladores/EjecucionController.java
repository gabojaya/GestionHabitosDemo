package controladores;

import java.io.IOException;
import java.sql.Time;
import java.time.Duration;
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
import modelo.dao.NotificacionDAO;
import modelo.dao.RecordatorioDAO;
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
		// Logica del control
		String ruta = (req.getParameter("ruta") == null) ? "listarEjecuciones" : req.getParameter("ruta");

		switch (ruta) {
		case "listarEjecuciones":
			System.out.println("Llamando a listarEjecuciones");
			this.listarEjecuciones(req, resp);
			break;
		case "crearEjecuciones":
			System.out.println("Llamando a crearEjecuciones");
			this.crearEjecuciones(req, resp);
			break;
		case "registrarEjecucion":
			System.out.println("Llamando a registrarEjecucion");
			this.registrarEjecucion(req, resp);
			break;
		}
	}

	private void registrarEjecucion(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("Entro a REGISTRAR LA EJECUCION---------------");

		// Obtener datos del formulario
		int idEjecucion = Integer.parseInt(req.getParameter("idEjecucion"));
		System.out.println("Esta es la ejecucion: " + idEjecucion);
		int cantidadRealizada = 0;
		Time tiempoCompletado = null;
		Time tiempoTotalT = null;

		int cantidaTotal = Integer.parseInt(req.getParameter("cantidadEjecHabito"));
		System.out.println("Esta es la cantidadTotal: " + cantidaTotal);

		if (req.getParameter("tiempoEjecHabito") != null && (req.getParameter("tiempoEjecHabito") != "")) {
			String tiempoTotal = req.getParameter("tiempoEjecHabito");
			System.out.println("Este es tl tiempo total dentro del if: " + tiempoTotal);
			tiempoTotalT = Time.valueOf(tiempoTotal);

		}
		System.out.println("Esta es la tiempoTotal convertido: " + tiempoTotalT);

		// Verificar si se llenó la cantidad o el tiempo
		if (req.getParameter("cantidadActual") != null && !req.getParameter("cantidadActual").isEmpty()) {
			cantidadRealizada = Integer.parseInt(req.getParameter("cantidadActual"));
		}
		if (req.getParameter("tiempoTranscurrido") != null && !req.getParameter("tiempoTranscurrido").isEmpty()) {
			String tiempoRealizado = req.getParameter("tiempoTranscurrido"); // Ej: "01:30:00"
			tiempoCompletado = Time.valueOf(tiempoRealizado);
			cantidadRealizada = 0; // Si se llenó el tiempo, se pone cantidad en 0
		}

		System.out.println("ESTA ES LA CANTIDAD REALIZADA: " + cantidadRealizada);
		System.out.println("ESTA ES LA TIEMPO REALIZADO: " + tiempoCompletado);

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

				NotificacionDAO notificacionDAO = new NotificacionDAO();
				notificacionDAO.actualizarRecordatorio(recordatorio);
				System.out.println("Recordatorio actualizado con éxito.");
			}

			// Actualizar Estadísticas
			EstadisticaDAO estadisticasDAO = new EstadisticaDAO();
			Estadistica estadisticas = estadisticasDAO.obtenerEstadisticasPorHabito(idHabito);
			if (estadisticas != null) {
				// Actualizar las estadísticas acumuladas para cantidad
				int cantidadAcumulada = estadisticas.getCantidadAcumulada() + cantidadRealizada;
				estadisticas.setCantidadAcumulada(cantidadAcumulada);

				// Obtener el tiempo acumulado desde la base de datos
				long tiempoAcumuladoSegundos = 0;
				if (estadisticas.getTiempoAcumulado() != null) {
				    // Convertir el tiempo acumulado a segundos (milisegundos a segundos)
				    tiempoAcumuladoSegundos = estadisticas.getTiempoAcumulado().getTime() / 1000;
				}

				System.out.println("Tiempo acumulado antes de sumar (en segundos): " + tiempoAcumuladoSegundos);

				// Convertir tiempoCompletado a segundos (milisegundos a segundos)
				long tiempoCompletadoSegundos = tiempoCompletado.getTime() / 1000;
				System.out.println("Tiempo completado (en segundos): " + tiempoCompletadoSegundos);

				// Sumar los tiempos en segundos
				long tiempoTotalSegundos = tiempoAcumuladoSegundos + tiempoCompletadoSegundos;
				System.out.println("Tiempo acumulado después de sumar (en segundos): " + tiempoTotalSegundos);

				// Convertir el total de segundos a Time
				Time tiempoAcumuladoFinal = new Time(tiempoTotalSegundos * 1000);  // Convertir a milisegundos para usar en Time

				// Guardar el tiempo acumulado actualizado
				estadisticas.setTiempoAcumulado(tiempoAcumuladoFinal);

				// Actualizar el progreso acumulado (como ejemplo)
				double progresoAcumulado = 0;
				if (estadisticas.getCantidadFinalEsperada() != 0) {
					progresoAcumulado = (double) cantidadAcumulada / estadisticas.getCantidadFinalEsperada() * 100;
				}
				estadisticas.setProgresoAcumulado(progresoAcumulado);

				// Guardar cambios en las estadísticas
				estadisticasDAO.actualizarEstadisticas(estadisticas);
				System.out.println("Estadísticas actualizadas con éxito.");

			}

		}

		// req.getRequestDispatcher("HabitoController?ruta=listar&idmeta="+
		// meta.getIdMeta()).forward(req, resp);

	}

	private void crearEjecuciones(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("Entro a CREAR EJECUCIONES ---------------");
		int idHabito = Integer.parseInt(req.getParameter("idHabito"));

		HttpSession session = req.getSession();
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		Meta meta = (Meta) session.getAttribute("meta");
		int idUsuario = usuario.getIdUsuario();
		System.out.println("Se creo ejecuciones para el usuario: " + idUsuario);

		NotificacionDAO notificacionDAO = new NotificacionDAO();
		List<Recordatorio> recordatorios = notificacionDAO.obtenerRecordatoriosPorHabito(idHabito);
		java.util.Date fechaActual = new java.util.Date(); // Fecha actual del sistema
		java.sql.Date fechaSQL = new java.sql.Date(fechaActual.getTime());

		if (recordatorios != null && !recordatorios.isEmpty()) {

			for (Recordatorio recordatorio : recordatorios) {
				Ejecucion ejecucion = new Ejecucion();
				ejecucion.setHabito(recordatorio.getHabitoAsociado());
				ejecucion.setUsuario(usuario);
				ejecucion.setEstado(true);
				ejecucion.setFecha(fechaSQL);
				ejecucion.setHora(recordatorio.getHora());
				ejecucion.setRecordatorio(recordatorio);

				EjecucionDAO ejecucionDAO = new EjecucionDAO();

				ejecucionDAO.crearEjecucion(ejecucion);
			}

			req.getRequestDispatcher("HabitoController?ruta=listar&idmeta=" + meta.getIdMeta()).forward(req, resp);
		} else {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND, "No se encontraron recordatorios para este hábito.");
		}
	}

	private void listarEjecuciones(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		Usuario usuario = (Usuario) session.getAttribute("usuario");

		int idUsuario = usuario.getIdUsuario();

		System.out.println("El usuario de la lista de ejecuciones es: " + idUsuario);

		EjecucionDAO ejecucionDAO = new EjecucionDAO();

		List<Ejecucion> ejecuciones = ejecucionDAO.listarEjecucionesPorUsuario(idUsuario);
		System.out.println("Ejecuciones obtenidas: " + ejecuciones.size());

		req.setAttribute("ejecuciones", ejecuciones);
		getServletContext().getRequestDispatcher("/jsp/menuPrincipal.jsp").forward(req, resp);
		// req.getRequestDispatcher("jsp/menuPrincipal.jsp").forward(req, resp);
	}

}
