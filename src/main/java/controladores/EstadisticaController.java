package controladores;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;

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

@WebServlet("/EstadisticaController")
public class EstadisticaController extends HttpServlet {

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
		case "crearEstadistica":
			this.crearEstadistica(req, resp);
			break;
		case "actualizarEstadisticas":
			this.actualizarEstadisticas(req, resp);
			break;
		case "verEstadistica":
			this.obtenerEstadisticaPorHabito(req, resp);
			break;
		}
	}

	private void actualizarEstadisticas(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("Entró a actualizar estadísticas");

		// Obtenemos el id del hábito y la meta
		int idHabito = Integer.parseInt(req.getParameter("idhab"));
		HttpSession session = req.getSession();
		int idMeta = (int) session.getAttribute("idmeta");

		// Obtener la meta para obtener días objetivo
		MetaDAO metaDAO = new MetaDAO();
		Meta meta = metaDAO.obtenerMetaPorId(idMeta);
		int diasObjetivos = meta.getDiasObjetivo();

		// Obtener el hábito actualizado
		HabitoDAO habitoDAO = new HabitoDAO();
		Habito habito;
		try {
			habito = habitoDAO.obtenerHabitoPorId(idHabito);

			// Calcular los nuevos valores para la estadística
			int frecuencia = habito.getFrecuencia();
			int cantidadTotal = habito.getCantidadTotal();
			Time tiempoTotal = habito.getTiempoTotal();

			// Calcular cantidadFinalEsperada
			int cantidadFinalEsperada = 0;
			if (cantidadTotal > 0) {
				cantidadFinalEsperada = frecuencia * cantidadTotal * diasObjetivos;
			}

			// Calcular tiempoFinalEsperado
			Time tiempoFinalEsperado = null;
			if (tiempoTotal != null) {
				long tiempoEnSegundos = convertirTimeASegundos(tiempoTotal);
				long tiempoTotalEsperadoSegundos = frecuencia * tiempoEnSegundos * diasObjetivos;
				String tiempoFinalStr = convertirSegundosAFormato(tiempoTotalEsperadoSegundos);
				tiempoFinalEsperado = Time.valueOf(tiempoFinalStr);
			}

			// Obtener la estadística actual del hábito
			EstadisticaDAO estadisticaDAO = new EstadisticaDAO();
			Estadistica estadistica = estadisticaDAO.obtenerEstadisticasPorHabito(idHabito);

			if (estadistica != null) {
				// Actualizar los valores de la estadística con los nuevos cálculos
				estadistica.setCantidadFinalEsperada(cantidadFinalEsperada);
				estadistica.setTiempoFinalEsperado(tiempoFinalEsperado);
				estadistica.setTiempoAcumulado(estadistica.getTiempoAcumulado()); // Mantener tiempo acumulado actual
				estadistica.setCantidadAcumulada(estadistica.getCantidadAcumulada()); // Mantener cantidad acumulada

				// Actualizar la estadística en la base de datos
				estadisticaDAO.actualizarEstadisticas(estadistica);

				System.out.println("Estadística actualizada con éxito");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Redirigir después de la actualización
		resp.sendRedirect("HabitoController?ruta=listar&idmeta=" + idMeta);

	}

	private void crearEstadistica(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("Entro a crear Estadistica");
		int idHabito = (int) req.getAttribute("idHabito");
		System.out.println("La id del habito extraido es: " + idHabito);
		HttpSession session = req.getSession();
		int id = (int) session.getAttribute("idmeta");

		MetaDAO metadao = new MetaDAO();
		Meta meta = metadao.obtenerMetaPorId(id);
		int diasObjetivos = meta.getDiasObjetivo();

		HabitoDAO habitoDAO = new HabitoDAO();
		Habito habito;

		EstadisticaDAO estdao = new EstadisticaDAO();
		Estadistica est = new Estadistica();

		try {
			habito = habitoDAO.obtenerHabitoPorId(idHabito);
			int frecuencia = habito.getFrecuencia();
			int cantidadTotal = habito.getCantidadTotal();
			Time tiempoTotal = habito.getTiempoTotal();

			// Calcular cantidadFinalEsperada
			int cantidadFinalEsperada = 0;
			if (cantidadTotal > 0) {
				cantidadFinalEsperada = frecuencia * cantidadTotal * diasObjetivos;
			}

			// Calcular tiempoFinalEsperado
			Time tiempoFinalEsperado = null;
			if (tiempoTotal != null) {
				// Obtener los milisegundos del tiempo total
				long tiempoEnSegundos = convertirTimeASegundos(tiempoTotal);

				// Calcular tiempo esperado en segundos
				long tiempoTotalEsperadoSegundos = frecuencia * tiempoEnSegundos * diasObjetivos;
				System.out.println("Este es el tiempoTotalEsperadoSegundos " + tiempoTotalEsperadoSegundos);

				// Convertir los segundos a formato HH:mm:ss
				String tiempoFinalStr = convertirSegundosAFormato(tiempoTotalEsperadoSegundos);
				System.out.println("Tiempo Final Esperado (formato HH:mm:ss): " + tiempoFinalStr);

				// Usar el nuevo formato convertido
				tiempoFinalEsperado = Time.valueOf(tiempoFinalStr);
				Time tiempoInicial = Time.valueOf("00:00:00");
				est.setTiempoAcumulado(tiempoInicial);
				System.out.println("Luego del 00:00:00 ");
				est.setTiempoFinalEsperado(tiempoFinalEsperado);
			} else {
				est.setTiempoAcumulado(null); // Tiempo inicial acumulado en 0
				est.setTiempoFinalEsperado(habito.getTiempoTotal());
			}
			System.out.println("Nombre del habito: " + habito.getNombre());
			// Configurar los valores en la estadística
			est.setHabito(habito);
			est.setCantidadAcumulada(0);

			est.setTotalEjecuciones(0);
			est.setCantidadFinalEsperada(cantidadFinalEsperada);
			est.setProgresoAcumulado(0.0);
			est.setEstado(false); // Estado inicial

			habito.getEstadisticas().add(est);
			estdao.crearEstadistica(est);

			habitoDAO.actualizarListaHabito(habito);

			resp.sendRedirect("HabitoController?ruta=listar&idmeta=" + id);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// Método para convertir Time a segundos (HH:mm:ss)
	private long convertirTimeASegundos(Time time) {
		String timeStr = time.toString(); // Convertir Time a String (HH:mm:ss)
		String[] parts = timeStr.split(":"); // Dividir el String por ":"
		int horas = Integer.parseInt(parts[0]);
		int minutos = Integer.parseInt(parts[1]);
		int segundos = Integer.parseInt(parts[2]);
		return (horas * 3600) + (minutos * 60) + segundos; // Convertir todo a segundos
	}

	// Método para convertir segundos a formato HH:mm:ss
	private String convertirSegundosAFormato(long segundos) {
		int horas = (int) (segundos / 3600); // Obtener horas
		segundos %= 3600;
		int minutos = (int) (segundos / 60); // Obtener minutos
		segundos %= 60;

		// Retornar el String en formato "HH:mm:ss"
		return String.format("%02d:%02d:%02d", horas, minutos, (int) segundos);
	}

	private void obtenerEstadisticaPorHabito(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("Se entro en obtener estadisticas");
		System.out.println(req.getParameter("habito"));
		HttpSession session = req.getSession();
		int idhab = Integer.parseInt(req.getParameter("habito"));
		System.out.println(idhab);
		EstadisticaDAO estdao = new EstadisticaDAO();
		Estadistica est = new Estadistica();
		est = estdao.obtenerEstadisticasPorHabito(idhab);
		req.setAttribute("estadistica", est);
		String data = "&data-ca=" + est.getCantidadAcumulada() + "&data-cf=" + est.getCantidadFinalEsperada()
				+ "&data-ta=" + est.getTiempoAcumulado() + "&data-tf=" + est.getTiempoFinalEsperado();
		System.out.println("este es el data: " + data);
		resp.sendRedirect("HabitoController?ruta=listarHabitosUsuario" + data);
	}
}
