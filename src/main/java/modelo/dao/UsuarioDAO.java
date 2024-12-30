package modelo.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.bdd.BddConnection;
import modelo.entidades.Usuario;

public class UsuarioDAO {

	public UsuarioDAO() {

	}
	
	public List<Usuario> getUsuarios() throws SQLException {

		List<Usuario> usuarios = new ArrayList<Usuario>();

		String _SQL_GET_ALL = "SELECT * FROM usuario";

		PreparedStatement pstmt = BddConnection.getConexion().prepareStatement(_SQL_GET_ALL);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			Usuario u = new Usuario();

			u.setIdUsuario(rs.getInt("idUsuario"));
			u.setNombre(rs.getString("nombre"));
			u.setApellido(rs.getString("apellido"));
			u.setNombreUsuario(rs.getString("nombreUsuario"));
			u.setEmail(rs.getString("email"));
			u.setClave(rs.getString("clave"));
			u.setFechaInicio(rs.getDate("fechaInicio"));
			u.setNotificacionesActivas(rs.getBoolean("notificacionesActivas"));

			usuarios.add(u);
		}

		BddConnection.cerrar(rs);
		BddConnection.cerrar(pstmt);
		BddConnection.cerrar();

		return usuarios;

	}

	public Usuario validarCredenciales(String nombreUsuario, String clave) throws SQLException {
	    System.out.println("Entro a validar");

	    String _SQL_VALIDAR = "SELECT * FROM Usuario WHERE nombreUsuario = ? AND clave = ?";
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        Connection conn = BddConnection.getConexion();
	        pstmt = conn.prepareStatement(_SQL_VALIDAR);
	        pstmt.setString(1, nombreUsuario);
	        pstmt.setString(2, clave);

	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            Usuario u = new Usuario();
	            u.setIdUsuario(rs.getInt("idUsuario"));
	            u.setNombre(rs.getString("nombre"));
	            u.setApellido(rs.getString("apellido"));
	            u.setNombreUsuario(rs.getString("nombreUsuario"));
	            u.setEmail(rs.getString("email"));
	            u.setClave(rs.getString("clave"));
	            u.setFechaInicio(rs.getDate("fechaInicio"));
	            u.setNotificacionesActivas(rs.getBoolean("notificacionesActivas"));

	            return u;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        BddConnection.cerrar(rs);
	        BddConnection.cerrar(pstmt);
	        BddConnection.cerrar();
	    }

	    return null;
	}

	
	public boolean crearUsuario(Usuario usuario) throws SQLException {
	    Connection conn = BddConnection.getConexion();
	    String _SQL_INSERT = "INSERT INTO Usuario (nombre, apellido, nombreUsuario, email, clave, fechaInicio, notificacionesActivas) "
	                       + "VALUES (?, ?, ?, ?, ?, ?, ?)";
	    PreparedStatement pstmt = BddConnection.getConexion().prepareStatement(_SQL_INSERT);

	    try {
	        // Prepara la sentencia SQL
	        pstmt = conn.prepareStatement(_SQL_INSERT);

	        // Establece los valores en el PreparedStatement
	        pstmt.setString(1, usuario.getNombre());
	        pstmt.setString(2, usuario.getApellido());
	        pstmt.setString(3, usuario.getNombreUsuario());
	        pstmt.setString(4, usuario.getEmail());
	        pstmt.setString(5, usuario.getClave());
	        pstmt.setDate(6, new java.sql.Date(usuario.getFechaInicio().getTime()));
	        pstmt.setBoolean(7, usuario.isNotificacionesActivas());

	        // Ejecuta la actualización
	        int filasAfectadas = pstmt.executeUpdate();

	        // Retorna true si el registro fue exitoso
	        return filasAfectadas > 0;
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    } finally {
	        // Cierra los recursos
			BddConnection.cerrar(pstmt);
			BddConnection.cerrar();
	    }
	}

}
