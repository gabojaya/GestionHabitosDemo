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
import modelo.dao.HabitoDAO;
import modelo.entidades.Habito;

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
	
	private void ruteador(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Logica del control
		String ruta = (req.getParameter("ruta") == null) ? "listar" : req.getParameter("ruta");

		switch (ruta) {
		case "listar":
			this.listarHabitos(req, resp);
			break;
		case "ingresarDatosHabito":
			this.crearHabito(req,resp);
			break;
		case "ingresarDatosModificacionHabito":
			this.modificarHabito(req, resp);
			break;
		case "eliminarHabito":
			this.eliminarHabito(req, resp);
			break;
		}
	}
	
	private void eliminarHabito(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		System.out.println("Se entro a eliminar habito");
		int idm = Integer.parseInt(req.getParameter("idmeta"));
		int idh = Integer.parseInt(req.getParameter("idhab"));
		System.out.println(idm);
		System.out.println(idh);
		HabitoDAO hdao=new HabitoDAO();
		try {
			hdao.eliminarHabito(idh);
			resp.sendRedirect("HabitoController?ruta=listar&idmeta="+idm);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void modificarHabito(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
		System.out.println("Se entro a modificar habito");
		int idm = Integer.parseInt(req.getParameter("idmeta"));
		int idh = Integer.parseInt(req.getParameter("idhab"));
		System.out.println(idm);
		System.out.println(idh);
		SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
		String tiempo = req.getParameter("tiempoTotal");
		String horario = req.getParameter("horario");
		Habito h = new Habito();
		h.setIdHabito(idh);
		h.setMetaAsociada(idm);
		h.setNombre(req.getParameter("nombre"));
		h.setCategoria(req.getParameter("categoria"));
		h.setTipoMedicion(req.getParameter("tipoMedicion"));
		h.setFrecuencia(Integer.parseInt(req.getParameter("frecuencia")));
		h.setCantidadTotal(Integer.parseInt(req.getParameter("cantidadTotal")));
		try {
			Date ti = format.parse(tiempo);
			Date ho = format.parse(horario);
		    java.sql.Time sqlTiempo = new java.sql.Time(ti.getTime());
		    java.sql.Time sqlhorario = new java.sql.Time(ho.getTime());
		    h.setTiempoTotal(sqlTiempo);
		    h.setHorario(sqlhorario);
		}catch(Exception e) {
			e.printStackTrace();
		}
		HabitoDAO hdao=new HabitoDAO();
		try {
			hdao.modificarHabito(h);
			resp.sendRedirect("HabitoController?ruta=listar&idmeta="+idm);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void crearHabito(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Se entro a crear habito");
		//int id = Integer.parseInt(req.getParameter("idmeta"));
		HttpSession session = req.getSession();
		int id = (int) session.getAttribute("idmeta"); 
		System.out.println("Esta es la idMeta por session: "+session.getAttribute("idmeta"));
		
		SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
		String tiempo = req.getParameter("tiempoTotal");
		String horario = req.getParameter("horario");
		Habito h = new Habito();
		h.setMetaAsociada(id);
		h.setNombre(req.getParameter("nombre"));
		h.setCategoria(req.getParameter("categoria"));
		h.setTipoMedicion(req.getParameter("tipoMedicion"));
		h.setFrecuencia(Integer.parseInt(req.getParameter("frecuencia")));
		h.setCantidadTotal(Integer.parseInt(req.getParameter("cantidadTotal")));
		try {
			Date ti = format.parse(tiempo);
			Date ho = format.parse(horario);
		    java.sql.Time sqlTiempo = new java.sql.Time(ti.getTime());
		    java.sql.Time sqlhorario = new java.sql.Time(ho.getTime());
		    h.setTiempoTotal(sqlTiempo);
		    h.setHorario(sqlhorario);
		}catch(Exception e) {
			e.printStackTrace();
		}
		HabitoDAO hdao=new HabitoDAO();
		try {
			hdao.crearHabito(h);
			resp.sendRedirect("HabitoController?ruta=listar&idmeta="+id);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	private void listarHabitos(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Se entro a listar habito");
		int id = Integer.parseInt(req.getParameter("idmeta"));
		System.out.println("Con id de meta: "+id);
		System.out.println(req.getParameter("idmeta"));
		
		req.setAttribute("idmeta", id);
		System.out.println(req.getParameter("idmeta")+"Hola");
		List<Habito> habs;
		HabitoDAO hdao=new HabitoDAO();
		HttpSession session = req.getSession();
		session.getAttribute("idmeta"); 
		System.out.println("Esta es la idMeta por session; "+session.getAttribute("idmeta"));
		
		try {
			habs = hdao.obtenerHabitos(id);
			req.setAttribute("habito", habs);
			session.setAttribute("idmeta", id);
			System.out.println("idMeta configurada en sesión: " + session.getAttribute("idmeta"));
			
			getServletContext().getRequestDispatcher("/jsp/menuPrincipal.jsp").forward(req,resp);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
