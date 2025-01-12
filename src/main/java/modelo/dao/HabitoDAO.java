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

public class HabitoDAO {
	
	public HabitoDAO() {

	}
	
	public List<Habito> obtenerHabitos(int idMeta) throws SQLException{
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("GestionHabitosWeb");
		EntityManager em = emf.createEntityManager();
		
		String JPQL_list = "SELECT h FROM habito h WHERE h.metaAsociada= :idMeta";
		Query query= em.createQuery(JPQL_list);
		query.setParameter("idMeta", idMeta);
		
		List<Habito> habitos = (List<Habito>)query.getResultList();
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

}
