package modelo.dao;

import java.sql.SQLException;
import java.util.List;
import modelo.entidades.Meta;

public interface MetaRestDAO {
    List<Meta> obtenerMetasPorUsuario(int idUsuario) throws SQLException;
    void eliminarMeta(int idMeta) throws SQLException;
    boolean insertarMeta(Meta meta) throws SQLException;
    boolean modificarMeta(Meta meta) throws SQLException;
    Meta obtenerMetaPorId(int idMeta);
}
