package modelo.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import modelo.entidades.Ejecucion;
import modelo.entidades.Estadistica;
import modelo.entidades.Habito;
import modelo.entidades.Meta;
import modelo.entidades.Recordatorio;

public class HabitoDAO {

	public HabitoDAO() {

	}

	public List<Habito> obtenerHabitos(int idMeta) throws SQLException {
		System.out.println("Entro a obtener habitos");
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("GestionHabitosWeb");
		EntityManager em = emf.createEntityManager();

		String JPQL_list = "SELECT h FROM Habito h WHERE h.metaAsociada= :idMeta";
		Query query = em.createQuery(JPQL_list);
		MetaDAO metaDAO = new MetaDAO();
		Meta meta = metaDAO.obtenerMetaPorId(idMeta);
		System.out.println("Esta es la meta obtenida: " + meta);
		query.setParameter("idMeta", meta);

		List<Habito> habitos = query.getResultList();

		if (habitos == null) {
			habitos = new ArrayList<>();
		}

		return habitos;
	}

	public void crearHabito(Habito h) throws SQLException {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("GestionHabitosWeb");
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		em.persist(h);
		em.getTransaction().commit();

	}

	public void modificarHabito(Habito h) throws SQLException {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("GestionHabitosWeb");
		EntityManager em = emf.createEntityManager();

		Habito hactual = em.find(Habito.class, h.getIdHabito());
		hactual.setNombre(h.getNombre());
		hactual.setCategoria(h.getCategoria());
		hactual.setTipoMedicion(h.getTipoMedicion());
		hactual.setFrecuencia(h.getFrecuencia());
		hactual.setCantidadTotal(h.getCantidadTotal());
		hactual.setTiempoTotal(h.getTiempoTotal());

		em.getTransaction().begin();
		em.merge(hactual);
		em.getTransaction().commit();

	}

	public void eliminarHabito(int idHabito) throws SQLException {

	    EntityManagerFactory emf = Persistence.createEntityManagerFactory("GestionHabitosWeb");
	    EntityManager em = emf.createEntityManager();

	    // Iniciar la transacción
	    em.getTransaction().begin();

	    try {
	        // Eliminar las ejecuciones asociadas al hábito
	        Query queryEjecuciones = em.createQuery("DELETE FROM Ejecucion e WHERE e.recordatorio.habitoAsociado.idHabito = :idHabito");
	        queryEjecuciones.setParameter("idHabito", idHabito);
	        queryEjecuciones.executeUpdate();

	        // Eliminar los recordatorios asociados al hábito
	        Query queryRecordatorios = em.createQuery("DELETE FROM Recordatorio r WHERE r.habitoAsociado.idHabito = :idHabito");
	        queryRecordatorios.setParameter("idHabito", idHabito);
	        queryRecordatorios.executeUpdate();

	        // Eliminar las estadísticas asociadas al hábito (si aplica)
	        Query queryEstadisticas = em.createQuery("DELETE FROM Estadistica e WHERE e.habito.idHabito = :idHabito");
	        queryEstadisticas.setParameter("idHabito", idHabito);
	        queryEstadisticas.executeUpdate();

	        // Eliminar el hábito
	        Query queryHabito = em.createQuery("DELETE FROM Habito h WHERE h.idHabito = :idHabito");
	        queryHabito.setParameter("idHabito", idHabito);
	        queryHabito.executeUpdate();

	        // Confirmar los cambios
	        em.getTransaction().commit();
	    } catch (Exception e) {
	        em.getTransaction().rollback();
	        throw new SQLException("Error al eliminar el hábito y sus registros asociados.", e);
	    } finally {
	        // Cerrar el EntityManager
	        em.close();
	    }
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

	public List<Habito> obtenerHabitosPorUsuario(int idUsuario) throws SQLException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("GestionHabitosWeb");
		EntityManager em = emf.createEntityManager();

		// Usamos una consulta JPQL para obtener todos los hábitos asociados a metas de
		// un usuario específico
		String jpql = "SELECT h FROM Habito h WHERE h.metaAsociada.usuario.idUsuario = :idUsuario";
		Query query = em.createQuery(jpql);
		query.setParameter("idUsuario", idUsuario);

		List<Habito> habitos = query.getResultList();

		if (habitos == null) {
			habitos = new ArrayList<>();
		}

		em.close();
		emf.close();

		return habitos;
	}

	public boolean actualizarListaHabito(Habito habito) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("GestionHabitosWeb");
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(habito); // Actualizar hábito con nueva estadística
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			return false;
		}
	}

}
