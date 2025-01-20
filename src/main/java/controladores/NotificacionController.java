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
import modelo.dao.NotificacionDAO;
import modelo.entidades.Habito;
import modelo.entidades.Meta;
import modelo.entidades.Recordatorio;

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
	
	private void ruteador(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Logica del control
		String ruta = (req.getParameter("ruta") == null) ? "asignarRecordatorio" : req.getParameter("ruta");

		switch (ruta) {
		case "asignarRecordatorio":
			System.out.println("Llamando a obtenerMetas");
			this.asignarRecordatorio(req, resp);
			break;
	
		}
	}

	private void asignarRecordatorio(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
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
            for (int i = 1; ; i++) {
                String horaParam = req.getParameter("horaHorario" + i);
                if (horaParam == null) break; // Salir del bucle si no hay más horarios
                horarios.add(Time.valueOf(horaParam + ":00"));
            }

            
            NotificacionDAO notificacionDAO = new NotificacionDAO();
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

            
            req.getRequestDispatcher("EjecucionController?ruta=crearEjecuciones&idHabito=" + idHabito).forward(req, resp);
            //req.getRequestDispatcher("HabitoController?ruta=listar&idmeta="+ meta.getIdMeta()).forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al asignar recordatorios");
        }
  	
	}
	
	
	
}



