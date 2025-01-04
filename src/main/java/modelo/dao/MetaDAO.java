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
	
	public void eliminarMeta(int idMeta) throws SQLException {
        String _SQL_DELETE_EJECUCIONES = "DELETE FROM Ejecucion WHERE idHabito IN (SELECT idHabito FROM Habito WHERE metaAsociada = ?)";
        String _SQL_DELETE_RECORDATORIOS = "DELETE FROM Recordatorio WHERE idHabito IN (SELECT idHabito FROM Habito WHERE metaAsociada = ?)";
        String _SQL_DELETE_ESTADISTICAS = "DELETE FROM Estadistica WHERE idHabito IN (SELECT idHabito FROM Habito WHERE metaAsociada = ?)";
        String _SQL_DELETE_HABITOS = "DELETE FROM Habito WHERE metaAsociada = ?";
        String _SQL_DELETE_META = "DELETE FROM Meta WHERE idMeta = ?";

        PreparedStatement pstmtDeleteEjecuciones = null;
        PreparedStatement pstmtDeleteRecordatorios = null;
        PreparedStatement pstmtDeleteEstadisticas = null;
        PreparedStatement pstmtDeleteHabitos = null;
        PreparedStatement pstmtDeleteMeta = null;

        try {
            // Conexión a la base de datos
            var conexion = BddConnection.getConexion();

            // Eliminar ejecuciones asociadas
            pstmtDeleteEjecuciones = conexion.prepareStatement(_SQL_DELETE_EJECUCIONES);
            pstmtDeleteEjecuciones.setInt(1, idMeta);
            pstmtDeleteEjecuciones.executeUpdate();

            // Eliminar recordatorios asociados
            pstmtDeleteRecordatorios = conexion.prepareStatement(_SQL_DELETE_RECORDATORIOS);
            pstmtDeleteRecordatorios.setInt(1, idMeta);
            pstmtDeleteRecordatorios.executeUpdate();

            // Eliminar estadísticas asociadas
            pstmtDeleteEstadisticas = conexion.prepareStatement(_SQL_DELETE_ESTADISTICAS);
            pstmtDeleteEstadisticas.setInt(1, idMeta);
            pstmtDeleteEstadisticas.executeUpdate();

            // Eliminar hábitos asociados
            pstmtDeleteHabitos = conexion.prepareStatement(_SQL_DELETE_HABITOS);
            pstmtDeleteHabitos.setInt(1, idMeta);
            pstmtDeleteHabitos.executeUpdate();

            // Eliminar la meta
            pstmtDeleteMeta = conexion.prepareStatement(_SQL_DELETE_META);
            pstmtDeleteMeta.setInt(1, idMeta);
            pstmtDeleteMeta.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            // Cerrar recursos
            BddConnection.cerrar(pstmtDeleteEjecuciones);
            BddConnection.cerrar(pstmtDeleteRecordatorios);
            BddConnection.cerrar(pstmtDeleteEstadisticas);
            BddConnection.cerrar(pstmtDeleteHabitos);
            BddConnection.cerrar(pstmtDeleteMeta);
        }
    }
	
	

}
