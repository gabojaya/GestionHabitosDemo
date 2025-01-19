package modelo.dao;


import java.sql.SQLException;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import modelo.entidades.Habito;
import modelo.entidades.Meta;

public class MetaDAO {
	
	public MetaDAO() {

	}
	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("GestionHabitosWeb");
    EntityManager em = emf.createEntityManager();
    
	public List<Meta> obtenerMetasPorUsuario(int idUsuario) throws SQLException {
		String jpql = "SELECT m FROM Meta m WHERE m.usuario.idUsuario = :idUsuario";
        TypedQuery<Meta> query = em.createQuery(jpql, Meta.class);
        query.setParameter("idUsuario", idUsuario);
        return query.getResultList();
    }
	
	public void eliminarMeta(int idMeta) throws SQLException {
		// Obtener la meta por su ID
		System.out.println("ESTAS EN META DAO");
        Meta meta = em.find(Meta.class, idMeta);
       
        List<Habito> habs;
        HabitoDAO hdao=new HabitoDAO();
        habs = hdao.obtenerHabitos(idMeta);
		System.out.println("Esta es la lista de habitos en METADAO" + habs);
		 for (Habito habito : habs) {
		        System.out.println("ID: " + habito.getIdHabito() + ", Nombre: " + habito.getNombre());
		    }
		 System.out.println("Esta es la lista de habitos en METADAO" + habs);
        try {
            em.getTransaction().begin();

            if (meta != null) {
                // Eliminar los hábitos asociados antes de eliminar la meta
                for (Habito habito : habs) {
                	habito = em.merge(habito);
                    em.remove(habito);
                }
                // Eliminar la meta
                em.remove(meta);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }
	
	public boolean insertarMeta(Meta meta) throws SQLException {
		try {
            em.getTransaction().begin();

            em.persist(meta);

            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
	}
	
	
	public boolean modificarMeta(Meta meta) throws SQLException {
		try {
            em.getTransaction().begin();

            em.merge(meta);

            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
	}
	
	public Meta obtenerMetaPorId(int idMeta) {
		return em.find(Meta.class, idMeta);
	}


}
