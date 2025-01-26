package modelo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import modelo.entidades.Estadistica;

public class EstadisticaDAO {
	
	public EstadisticaDAO() {

	}
	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("GestionHabitosWeb");
    EntityManager em = emf.createEntityManager();

	public boolean crearEstadistica(Estadistica est) {
		try {
            em.getTransaction().begin();

            em.persist(est);

            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
		
	}
	
	public Estadistica obtenerEstadisticasPorHabito(int idHabito) {
		String jpql = "SELECT e FROM Estadistica e WHERE e.habito.idHabito = :idHabito";
		TypedQuery<Estadistica> query = em.createQuery(jpql, Estadistica.class);
		query.setParameter("idHabito", idHabito);
		
		// Retorna una única estadística, o null si no se encuentra
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			// Si no se encuentra, se retorna null
			return null;
		}
	}
	
	// Método para actualizar las estadísticas
		public boolean actualizarEstadisticas(Estadistica estadistica) {
			try {
	            em.getTransaction().begin();
	            
	            // Se usa merge en lugar de persist para actualizar el objeto
	            em.merge(estadistica);
	            
	            em.getTransaction().commit();
	            return true;
	        } catch (Exception e) {
	            em.getTransaction().rollback();
	            e.printStackTrace();
	            return false;
	        }
		}

}
