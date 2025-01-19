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
        String jpql = "SELECT e FROM Ejecucion e WHERE e.usuario.idUsuario = :idUsuario";
        TypedQuery<Ejecucion> query = em.createQuery(jpql, Ejecucion.class);
        query.setParameter("idUsuario", idUsuario);
        return query.getResultList();
    }

	public void crearEjecucion(Ejecucion ejecucion) {
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

}
