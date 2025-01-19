package modelo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import modelo.bdd.BddConnection;
import modelo.entidades.Habito;
import modelo.entidades.Meta;

public class HabitoDAO {
	
	public HabitoDAO() {

	}
	
	public List<Habito> obtenerHabitos(int idMeta) throws SQLException{
		System.out.println("Entro a obtener habitos");
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("GestionHabitosWeb");
		EntityManager em = emf.createEntityManager();
		
		String JPQL_list = "SELECT h FROM Habito h WHERE h.metaAsociada= :idMeta";
		Query query= em.createQuery(JPQL_list);
		MetaDAO metaDAO = new MetaDAO();
		Meta meta = metaDAO.obtenerMetaPorId(idMeta); 
		System.out.println("Esta es la meta obtenida: " +meta);
		query.setParameter("idMeta", meta);

		
	    List<Habito> habitos = query.getResultList();
	    
	    
	    if (habitos == null) {
	        habitos = new ArrayList<>();
	    }
	    
        return habitos;
	}
	
	public void crearHabito(Habito h) throws SQLException{
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("GestionHabitosWeb");
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		em.persist(h);
		em.getTransaction().commit();
		
	}
	
	public void modificarHabito(Habito h) throws SQLException{
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("GestionHabitosWeb");
		EntityManager em = emf.createEntityManager();
		
		Habito hactual=em.find(Habito.class, h.getIdHabito());
		hactual.setNombre(h.getNombre());
		hactual.setCategoria(h.getCategoria());
		hactual.setTipoMedicion(h.getTipoMedicion());
		hactual.setFrecuencia(h.getFrecuencia());
		hactual.setCantidadTotal(h.getCantidadTotal());
		hactual.setTiempoTotal(h.getTiempoTotal());
		hactual.setHorario(h.getHorario());
		
		em.getTransaction().begin();
		em.merge(hactual);
		em.getTransaction().commit();
		
		
	}
	
	public void eliminarHabito(int idHabito) throws SQLException{
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("GestionHabitosWeb");
		EntityManager em = emf.createEntityManager();
		
		Habito h = em.find(Habito.class, idHabito);
		
		em.getTransaction().begin();
		em.remove(h);
		em.getTransaction().commit();
		
	}
	
	public Habito obtenerHabitoPorId(int idHabito) throws SQLException {
	    EntityManagerFactory emf = Persistence.createEntityManagerFactory("GestionHabitosWeb");
	    EntityManager em = emf.createEntityManager();
	    
	    try {
	        // Busca el hábito por su ID
	        Habito habito = em.find(Habito.class, idHabito);
	        return habito;
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new SQLException("Error al obtener el hábito con ID: " + idHabito, e);
	    } finally {
	        em.close();
	        emf.close();
	    }
	}

	

}
