package modelo.dao;


import java.sql.SQLException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import modelo.entidades.Usuario;

public class UsuarioDAO {
	
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("GestionHabitosWeb");
    private static final EntityManager em = emf.createEntityManager();
    
	public UsuarioDAO() {

	}
	

	public Usuario validarCredenciales(String nombreUsuario, String clave) throws SQLException {
	    System.out.println("Entro a validar");

	    try {
            
            Usuario usuario = em.createQuery("SELECT u FROM Usuario u WHERE u.nombreUsuario = :nombreUsuario AND u.clave = :clave", Usuario.class)
                    .setParameter("nombreUsuario", nombreUsuario)
                    .setParameter("clave", clave)
                    .getSingleResult();
            return usuario;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}

	
	public boolean crearUsuario(Usuario usuario) throws SQLException {
		try {
            em.getTransaction().begin();
            em.persist(usuario);  // Inserta el nuevo usuario
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();  // Revierte si ocurre un error
            }
            return false;
        }
	}
	
	public boolean modificarUsuario(Usuario usuario) {
	    try {
	        em.getTransaction().begin();
	        em.merge(usuario); // Actualiza el usuario en la base de datos
	        em.getTransaction().commit();
	        return true;
	    } catch (Exception e) {
	        e.printStackTrace();
	        if (em.getTransaction().isActive()) {
	            em.getTransaction().rollback(); // Revierte los cambios si ocurre un error
	        }
	        return false;
	    }
	}
	public Usuario obtenerUsuarioPorId(int idUsuario) {
	    try {
	        return em.find(Usuario.class, idUsuario);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}

}
