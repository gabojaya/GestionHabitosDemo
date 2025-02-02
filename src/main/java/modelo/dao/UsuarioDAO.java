package modelo.dao;

import java.sql.SQLException;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import modelo.entidades.Meta;
import modelo.dao.MetaDAO;
import modelo.entidades.Usuario;

public class UsuarioDAO {

	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("GestionHabitosWeb");
	private static final EntityManager em = emf.createEntityManager();

	public UsuarioDAO() {

	}

	public Usuario validarCredenciales(String nombreUsuario, String clave) throws SQLException {

		try {

			Usuario usuario = em
					.createQuery("SELECT u FROM Usuario u WHERE u.nombreUsuario = :nombreUsuario AND u.clave = :clave",
							Usuario.class)
					.setParameter("nombreUsuario", nombreUsuario).setParameter("clave", clave).getSingleResult();
			return usuario;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean crearUsuario(Usuario usuario) throws SQLException {
		try {
			em.getTransaction().begin();
			em.persist(usuario);
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

	public boolean modificarUsuario(Usuario usuario) {
		try {
			em.getTransaction().begin();
			em.merge(usuario);
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

	public Usuario obtenerUsuarioPorId(int idUsuario) {
		Usuario usuario = null;
		try {
			usuario = em.find(Usuario.class, idUsuario);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usuario;
	}

	public boolean actualizarListasUsuario(Usuario usuario) {
		try {
			em.getTransaction().begin();
			em.merge(usuario);
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

	public boolean eliminarUsuario(int idUsuario) {
		try {
			em.getTransaction().begin();

			Usuario usuario = em.find(Usuario.class, idUsuario);
			if (usuario != null) {

				List<Meta> metas = usuario.getMetas();
				if (metas != null && !metas.isEmpty()) {
					MetaDAO metaDAO = new MetaDAO();
					for (Meta meta : metas) {

						metaDAO.eliminarMeta(meta.getIdMeta());
					}
				}

				em.remove(usuario);
				em.getTransaction().commit();
				return true;
			} else {
				em.getTransaction().rollback();
				return false;
			}
		} catch (SQLException e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			e.printStackTrace();
			return false;
		} finally {
			em.close();
		}
	}

}
