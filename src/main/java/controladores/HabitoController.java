package controladores;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
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
		}
	}
	
	private void modificarHabito(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
		System.out.println("Se entro a modificar habito");
		int idm = Integer.parseInt(req.getParameter("idmeta"));
		int idh = Integer.parseInt(req.getParameter("idhab"));
		System.out.println(idm);
		System.out.println(idh);
		Habito h = new Habito();
		h.setIdHabito(idh);
		h.setMetaAsociada(idm);
		h.setNombre(req.getParameter("nombre"));
		h.setCategoria(req.getParameter("categoria"));
		h.setTipoMedicion(req.getParameter("tipoMedicion"));
		h.setFrecuencia(Integer.parseInt(req.getParameter("frecuencia")));
		h.setCantidadTotal(30);
		h.setTiempoTotal(new java.sql.Time(123456789999l));
		h.setHorario(new java.sql.Time(123456789999l));
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
		int id = Integer.parseInt(req.getParameter("idmeta"));
		System.out.println(req.getParameter("idmeta"));
		Habito h = new Habito();
		h.setMetaAsociada(id);
		h.setNombre(req.getParameter("nombre"));
		h.setCategoria(req.getParameter("categoria"));
		h.setTipoMedicion(req.getParameter("tipoMedicion"));
		h.setFrecuencia(Integer.parseInt(req.getParameter("frecuencia")));
		h.setCantidadTotal(30);
		h.setTiempoTotal(new java.sql.Time(123456789999l));
		h.setHorario(new java.sql.Time(123456789999l));
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
		System.out.println(req.getParameter("idmeta"));
		req.setAttribute("idmeta", id);
		List<Habito> habs;
		HabitoDAO hdao=new HabitoDAO();
		
		try {
			habs = hdao.obtenerHabitos(id);
			req.setAttribute("habito", habs);
			getServletContext().getRequestDispatcher("/jsp/menuPrincipal.jsp").forward(req,resp);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
