package controladores;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.dao.EstadisticaDAO;
import modelo.dao.HabitoDAO;
import modelo.entidades.Estadistica;
import modelo.entidades.Habito;

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
	
	private void ruteador(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Logica del control
		String ruta = (req.getParameter("ruta") == null) ? "listar" : req.getParameter("ruta");

		switch (ruta) {
		case "crearEstadistica":
			this.crearEstadistica(req, resp);
			break;
	
		}
	}

	private void crearEstadistica(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException{
		System.out.println("Entro a crear Estadistica");
		int idHabito = (int) req.getAttribute("idHabito"); 
		System.out.println("La id del habito extraido es: " + idHabito);
		HttpSession session = req.getSession();
		int id = (int) session.getAttribute("idmeta"); 
		
		HabitoDAO habitoDAO = new HabitoDAO();
		Habito habito;
		
		EstadisticaDAO estdao = new EstadisticaDAO();
		Estadistica est = new Estadistica();
		
		try {
			habito = habitoDAO.obtenerHabitoPorId(idHabito);
			System.out.println("Nombre del habito: " + habito.getNombre());
			est.setHabitos(habito);
			
			estdao.crearEstadistica(est);
			
			resp.sendRedirect("HabitoController?ruta=listar&idmeta="+id);
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
}
