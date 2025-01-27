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
		// Logica del control
		String ruta = (req.getParameter("ruta") == null) ? "asignarRecordatorio" : req.getParameter("ruta");

		switch (ruta) {
		case "asignarRecordatorio":
			System.out.println("Llamando a asignarRecordatorio");
			this.asignarRecordatorio(req, resp);
			break;
		case "listarRecordatorios":
			System.out.println("Llamando a listarRecordatorios");
			this.listarRecordatorios(req, resp);
			break;
		case "marcarLeido":
			System.out.println("Llamando a marcarLeido");
			this.marcarLeido(req, resp);
			break;

		}
	}

	private void marcarLeido(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {
		
		int idRecordatorio = Integer.parseInt(req.getParameter("idRecordatorio"));

		RecordatorioDAO notificacionDAO = new RecordatorioDAO();
	    Recordatorio recordatorio = notificacionDAO.obtenerRecordatorioPorId(idRecordatorio);
	    if (recordatorio != null) {
	        recordatorio.setEstado(false); // Cambia el estado del recordatorio
	        boolean actualizado = notificacionDAO.actualizarRecordatorio(recordatorio);

	        if (actualizado) {
	            System.out.println("Recordatorio marcado como leído exitosamente");
	        } else {
	            System.out.println("Error al actualizar el recordatorio");
	        }
	    }

	    // Redirige de vuelta a la lista de recordatorios
	    resp.sendRedirect("NotificacionController?ruta=listarRecordatorios");
		
	}

	private void listarRecordatorios(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("Entro a asginar Recordatorio");
		HttpSession session = req.getSession();
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		System.out.println("Este es el usuario: " + usuario);
		System.out.println("Este es el ID usuario: " + usuario.getIdUsuario());
		int idUsuario = usuario.getIdUsuario();

		// Obtener recordatorios activos del usuario desde el DAO
		RecordatorioDAO notificacionDAO = new RecordatorioDAO();
		List<Recordatorio> recordatoriosActivos = notificacionDAO
				.obtenerRecordatoriosActivosPorUsuario(idUsuario);

		// Añadir los recordatorios a la solicitud
		req.setAttribute("recordatorios", recordatoriosActivos);

		getServletContext().getRequestDispatcher("/jsp/menuPrincipal.jsp").forward(req, resp);

	}

	private void asignarRecordatorio(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("Entro a asginar Recordatorio");
		int idHabito = Integer.parseInt(req.getParameter("idHabito"));

		HabitoDAO habitoDAO = new HabitoDAO();
		Habito habito;
		HttpSession session = req.getSession();
		Meta meta = (Meta) session.getAttribute("meta");
		Date fechaInicioStr = meta.getFechaInicio();
		Date fechaFinStr = meta.getFechaFin();

		System.out.println(fechaInicioStr);
		System.out.println(fechaFinStr);

		try {
			habito = habitoDAO.obtenerHabitoPorId(idHabito);
			System.out.println("Este es el id del habito del que se esta creando recordatorio " + idHabito);
			System.out.println("Nombre del habito: " + habito.getNombre());

			List<Time> horarios = new ArrayList<>();
			for (int i = 1;; i++) {
				String horaParam = req.getParameter("horaHorario" + i);
				if (horaParam == null)
					break; // Salir del bucle si no hay más horarios
				horarios.add(Time.valueOf(horaParam + ":00"));
			}
			// Imprimir los elementos de la lista
			System.out.println("Horarios registrados:");
			for (Time horario : horarios) {
				System.out.println(horario);
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

				notificacionDAO.crearRecordatorio(recordatorio);
			}

			req.getRequestDispatcher("EjecucionController?ruta=crearEjecuciones&idHabito=" + idHabito).forward(req,
					resp);
			// req.getRequestDispatcher("HabitoController?ruta=listar&idmeta="+
			// meta.getIdMeta()).forward(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al asignar recordatorios");
		}

	}

}
