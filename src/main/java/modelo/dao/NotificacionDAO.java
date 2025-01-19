package modelo.dao;

import java.sql.SQLException;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import modelo.entidades.Recordatorio;

public class NotificacionDAO {
	
	public NotificacionDAO() {

	}
	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("GestionHabitosWeb");
    EntityManager em = emf.createEntityManager();
    
 


    public boolean crearRecordatorio(Recordatorio recordatorio) throws SQLException {
        try {
            em.getTransaction().begin();
            em.persist(recordatorio);
            em.getTransaction().commit();
            System.out.println("Recordatorio creado con éxito: " + recordatorio.getMensaje());
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        }
    }




	public List<Recordatorio> obtenerRecordatoriosPorHabito(int idHabito) {
		String jpql = "SELECT r FROM Recordatorio r WHERE r.habitoAsociado.idHabito = :idHabito";
	    TypedQuery<Recordatorio> query = em.createQuery(jpql, Recordatorio.class);
	    query.setParameter("idHabito", idHabito);
	    
	    return query.getResultList();
	}
    


}
