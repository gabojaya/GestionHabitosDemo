package modelo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import modelo.bdd.BddConnection;
import modelo.entidades.Habito;

public class HabitoDAO {
	
	public HabitoDAO() {

	}
	
	public List<Habito> obtenerHabitos(int idMeta) throws SQLException{
		List<Habito> habitos = new ArrayList<>();
		String _SQL_GET_HABITOS = "SELECT * FROM habito WHERE metaAsociada = ?";
		PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
        	pstmt = BddConnection.getConexion().prepareStatement(_SQL_GET_HABITOS);
        	pstmt.setInt(1,idMeta);
        	rs= pstmt.executeQuery();
        	
        	while(rs.next()) {
        		Habito h=new Habito();
        		h.setIdHabito(rs.getInt("idHabito"));
        		h.setNombre(rs.getNString("nombre"));
        		h.setCategoria(rs.getNString("categoria"));
        		h.setMetaAsociada(rs.getInt("metaAsociada"));
        		h.setEstado((rs.getInt("estado")==1?true:false));
        		h.setTipoMedicion(rs.getString("tipoMedicion"));
        		h.setFrecuencia(rs.getInt("frecuencia"));
        		h.setCantidadTotal(rs.getInt("cantidadTotal"));
        		h.setTiempoTotal(rs.getTime("tiempoTotal"));
        		h.setHorario(rs.getTime("horario"));
        		habitos.add(h);
        	}
        }catch(SQLException e) {
        	throw e;
        }finally {
        	BddConnection.cerrar(rs);
    		BddConnection.cerrar(pstmt);
        }
        return habitos;
	}
	
	public void crearHabito(Habito h) throws SQLException{
		String _SQL_INSERT = "INSERT INTO habito (nombre, categoria, metaAsociada, tipoMedicion, frecuencia, cantidadTotal, tiempoTotal, horario)"
				+ "VALUES (?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = null;
		try {
			pstmt = BddConnection.getConexion().prepareStatement(_SQL_INSERT);
			pstmt.setString(1,h.getNombre());
			pstmt.setString(2,h.getCategoria());
			pstmt.setInt(3,h.getMetaAsociada());
			pstmt.setString(4,h.getTipoMedicion());
			pstmt.setInt(5,h.getFrecuencia());
			pstmt.setInt(6,h.getCantidadTotal());
			pstmt.setTime(7,h.getTiempoTotal());
			pstmt.setTime(8,h.getHorario());
			int filas = pstmt.executeUpdate();
		}catch(SQLException e) {
			throw e;
		}finally {
			BddConnection.cerrar(pstmt);
			BddConnection.cerrar();
		}
		
	}
	
	public void modificarHabito(Habito h) throws SQLException{
		String _SQL_UPDATE = "UPDATE habito SET nombre=?, categoria=?, tipoMedicion=?, frecuencia=?, cantidadTotal=?, tiempoTotal=?, horario=? "
				+ "WHERE idHabito = ?";
		PreparedStatement pstmt = null;
		try {
			pstmt = BddConnection.getConexion().prepareStatement(_SQL_UPDATE);
			pstmt.setString(1,h.getNombre());
			pstmt.setString(2,h.getCategoria());
			pstmt.setString(3,h.getTipoMedicion());
			pstmt.setInt(4,h.getFrecuencia());
			pstmt.setInt(5,h.getCantidadTotal());
			pstmt.setTime(6,h.getTiempoTotal());
			pstmt.setTime(7,h.getHorario());
			pstmt.setInt(8,h.getIdHabito());
			int filas = pstmt.executeUpdate();
		}catch(SQLException e) {
			throw e;
		}finally {
			BddConnection.cerrar(pstmt);
			BddConnection.cerrar();
		}
		
	}

}
