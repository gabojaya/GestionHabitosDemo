package modelo.dao;

import java.sql.Connection;
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
	
	public boolean insertarMeta(Meta meta) throws SQLException {
	    System.out.println("Entro al metodo de insertarMeta en la base de datos");
	    String _SQL_INSERT = "INSERT INTO Meta (idUsuario, nombre, descripcion, fechaInicio, fechaFin, progreso, estado, diasObjetivo) "
	                       + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	    PreparedStatement pstmt = null;

	    try {
	        Connection conn = BddConnection.getConexion();
	        pstmt = conn.prepareStatement(_SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);

	        // Configurar los parámetros
	        pstmt.setInt(1, meta.getIdUsuario());
	        pstmt.setString(2, meta.getNombre());
	        pstmt.setString(3, meta.getDescripcion());
	        pstmt.setDate(4, new java.sql.Date(meta.getFechaInicio().getTime()));
	        pstmt.setDate(5, new java.sql.Date(meta.getFechaFin().getTime()));
	        pstmt.setDouble(6, meta.getProgreso());
	        pstmt.setBoolean(7, meta.isEstado());
	        pstmt.setInt(8, meta.getDiasObjetivo());

	        // Ejecutar la inserción
	        int filasAfectadas = pstmt.executeUpdate();

	        if (filasAfectadas > 0) {
	            // Obtener el id generado
	            ResultSet rs = pstmt.getGeneratedKeys();
	            if (rs.next()) {
	                meta.setIdMeta(rs.getInt(1)); // Asigna el id generado
	            }
	            return true;
	        } else {
	            return false;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    } finally {
	        // Cerrar recursos
	        BddConnection.cerrar(pstmt);
	        BddConnection.cerrar();
	    }
	}
	
	
	public boolean modificarMeta(Meta meta) throws SQLException {
	    System.out.println("Entro al metodo de modificarMeta en la base de datos");

	    String _SQL_UPDATE = "UPDATE Meta SET nombre = ?, descripcion = ?, fechaInicio = ?, fechaFin = ?, estado = ?, diasObjetivo = ? "
	                       + "WHERE idMeta = ?";

	    PreparedStatement pstmt = null;

	    try {
	        Connection conn = BddConnection.getConexion();
	        pstmt = conn.prepareStatement(_SQL_UPDATE);

	        // Configurar los parámetros
	        pstmt.setString(1, meta.getNombre());
	        pstmt.setString(2, meta.getDescripcion());
	        pstmt.setDate(3, new java.sql.Date(meta.getFechaInicio().getTime()));
	        pstmt.setDate(4, new java.sql.Date(meta.getFechaFin().getTime()));
	        pstmt.setBoolean(5, meta.isEstado());
	        pstmt.setInt(6, meta.getDiasObjetivo());
	        pstmt.setInt(7, meta.getIdMeta());

	        // Ejecutar la actualización
	        int filasAfectadas = pstmt.executeUpdate();

	        return filasAfectadas > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    } finally {
	        // Cerrar recursos
	        BddConnection.cerrar(pstmt);
	        BddConnection.cerrar();
	    }
	}


}
