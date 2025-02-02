package modelo.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import modelo.entidades.Ejecucion;

public class EjecucionDAO {
	
	public EjecucionDAO() {

	}
	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("GestionHabitosWeb");
    EntityManager em = emf.createEntityManager();
	
    public List<Ejecucion> listarEjecucionesPorUsuario(int idUsuario) {
    	String jpql = "SELECT e FROM Ejecucion e WHERE e.usuario.idUsuario = :idUsuario AND e.estado = true";
        TypedQuery<Ejecucion> query = em.createQuery(jpql, Ejecucion.class);
        query.setParameter("idUsuario", idUsuario);
        return query.getResultList();
    }

	public void crearEjecucion(Ejecucion ejecucion) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("GestionHabitosWeb");
	    EntityManager em = emf.createEntityManager();
		try {
	        em.getTransaction().begin();
	        em.persist(ejecucion);
	        em.getTransaction().commit();
	    } catch (Exception e) {
	        if (em.getTransaction().isActive()) {
	            em.getTransaction().rollback();
	        }
	        e.printStackTrace();
	    } finally {
	        em.close();
	    }
		
	}
	
	public Ejecucion buscarEjecucionPorId(int idEjecucion) {
	    return em.find(Ejecucion.class, idEjecucion);
	}

	public void actualizarEjecucion(Ejecucion ejecucion) {
	    try {
	        em.getTransaction().begin();
	        em.merge(ejecucion);  
	        em.getTransaction().commit();
	    } catch (Exception e) {
	        if (em.getTransaction().isActive()) {
	            em.getTransaction().rollback();
	        }
	        e.printStackTrace();
	    } finally {
	        em.close();
	    }
	}

	

}
