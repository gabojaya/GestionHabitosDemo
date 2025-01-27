package modelo.dao;

import java.sql.SQLException;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import modelo.entidades.Recordatorio;

public class RecordatorioDAO {

	public RecordatorioDAO() {

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
    
	public List<Recordatorio> obtenerRecordatoriosActivosPorUsuario(int idUsuario) {
	    String jpql = "SELECT r FROM Recordatorio r JOIN r.habitoAsociado h JOIN h.metaAsociada m WHERE m.usuario.idUsuario = :idUsuario AND r.estado = true";
	    TypedQuery<Recordatorio> query = em.createQuery(jpql, Recordatorio.class);
	    query.setParameter("idUsuario", idUsuario);
	    List<Recordatorio> recordatoriosActivos = query.getResultList();
	    
	    return recordatoriosActivos;
	}

	public Recordatorio obtenerRecordatorioPorId(int idRecordatorio) {
	    String jpql = "SELECT r FROM Recordatorio r WHERE r.idRecordatorio = :idRecordatorio";
	    TypedQuery<Recordatorio> query = em.createQuery(jpql, Recordatorio.class);
	    query.setParameter("idRecordatorio", idRecordatorio);
	    
	    List<Recordatorio> recordatorios = query.getResultList();
	    Recordatorio recordatorio = recordatorios.stream().findFirst().orElse(null);
	    // Se asume que el id es único, por lo que se espera un único resultado.
	    return recordatorio;
	}
	
	public boolean actualizarRecordatorio(Recordatorio recordatorio) {
	    try {
	        em.getTransaction().begin();
	        em.merge(recordatorio);  // merge se usa para actualizar el registro existente
	        em.getTransaction().commit();
	        System.out.println("Recordatorio actualizado con éxito.");
	        return true;
	    } catch (Exception e) {
	        if (em.getTransaction().isActive()) {
	            em.getTransaction().rollback();
	        }
	        e.printStackTrace();
	        return false;
	    }
	}
}
