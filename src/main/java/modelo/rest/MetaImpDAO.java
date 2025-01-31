package modelo.rest;

import java.sql.SQLException;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import modelo.dao.MetaRestDAO;
import modelo.entidades.Meta;

public class MetaImpDAO implements MetaRestDAO {
    
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("GestionHabitosWeb");
    private EntityManager em = emf.createEntityManager();
    
    
    @Override
    public List<Meta> obtenerMetasPorUsuario(int idUsuario) throws SQLException {
        String jpql = "SELECT m FROM Meta m WHERE m.usuario.idUsuario = :idUsuario";
        TypedQuery<Meta> query = em.createQuery(jpql, Meta.class);
        query.setParameter("idUsuario", idUsuario);
        return query.getResultList();
    }
    
    @Override
    public void eliminarMeta(int idMeta) throws SQLException {
        Meta meta = em.find(Meta.class, idMeta);
        
        if (meta != null) {
            try {
                em.getTransaction().begin();
                em.remove(meta);
                em.getTransaction().commit();
            } catch (Exception e) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                e.printStackTrace();
            }
        }
    }
    
    @Override
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
    
    @Override
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
    
    @Override
    public Meta obtenerMetaPorId(int idMeta) {
        return em.find(Meta.class, idMeta);
    }
}
