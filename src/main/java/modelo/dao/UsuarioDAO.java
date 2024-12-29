package modelo.dao;


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
			u.setclave(rs.getString("clave"));
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

		for (Usuario usr : getUsuarios()) {
			if (usr.getNombreUsuario().equals(nombreUsuario) && usr.getclave().equals(clave)) {
				return usr;
			}
		}
		return null;
	}
}
