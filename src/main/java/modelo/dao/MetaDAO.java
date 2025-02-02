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

		Meta meta = em.find(Meta.class, idMeta);

		if (meta != null) {
			List<Habito> habs;
			HabitoDAO hdao = new HabitoDAO();
			habs = hdao.obtenerHabitos(idMeta);

			try {
				em.getTransaction().begin();

				for (Habito habito : habs) {
					
					hdao.eliminarHabito(habito.getIdHabito());
				}

				em.remove(meta);

				em.getTransaction().commit();
			} catch (Exception e) {
				if (em.getTransaction().isActive()) {
					em.getTransaction().rollback();
				}
				e.printStackTrace();
				throw new SQLException("Error al eliminar la meta y sus hábitos asociados.", e);
			}
		} else {
			throw new SQLException("Meta no encontrada con ID: " + idMeta);
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
