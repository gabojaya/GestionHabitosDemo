package modelo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.bdd.BddConnection;
import modelo.entidades.Meta;

public class MetaDAO {
	
	public MetaDAO() {

	}

	public List<Meta> obtenerMetasPorUsuario(int idUsuario) throws SQLException {
        List<Meta> metas = new ArrayList<>();

        String _SQL_GET_METAS = "SELECT * FROM Meta WHERE idUsuario = ?";
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            
            pstmt = BddConnection.getConexion().prepareStatement(_SQL_GET_METAS);
            pstmt.setInt(1, idUsuario); 
            
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Meta meta = new Meta();
                meta.setIdMeta(rs.getInt("idMeta"));
                meta.setIdUsuario(rs.getInt("idUsuario"));
                meta.setNombre(rs.getString("nombre"));
                meta.setDescripcion(rs.getString("descripcion"));
                meta.setFechaInicio(rs.getDate("fechaInicio"));
                meta.setFechaFin(rs.getDate("fechaFin"));
                meta.setProgreso(rs.getInt("progreso"));
                metas.add(meta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; 
        } finally {
            
            BddConnection.cerrar(rs);
            BddConnection.cerrar(pstmt);
        }

        return metas;
    }

}
