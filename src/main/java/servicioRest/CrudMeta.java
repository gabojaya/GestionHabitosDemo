package servicioRest;

import java.sql.SQLException;
import java.util.List;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import modelo.dao.MetaRestDAO;
import modelo.entidades.Meta;
import modelo.rest.MetaImpDAO;

@Path("/meta")
public class CrudMeta {

    private MetaRestDAO metaDAO;

    public CrudMeta() {
        this.metaDAO = new MetaImpDAO();
    }

    @GET
    @Path("/listar/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Meta> obtenerMetasPorUsuario(@PathParam("idUsuario") int idUsuario) throws SQLException {
        return metaDAO.obtenerMetasPorUsuario(idUsuario);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Meta obtenerMetaPorId(@PathParam("id") int id) {
        return metaDAO.obtenerMetaPorId(id);
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean crearMeta(Meta meta) throws SQLException {
        return metaDAO.insertarMeta(meta);
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean actualizarMeta(Meta meta) throws SQLException {
        return metaDAO.modificarMeta(meta);
    }

    @DELETE
    @Path("/delete/{id}")
    public void eliminarMeta(@PathParam("id") int id) throws SQLException {
        metaDAO.eliminarMeta(id);
    }
}
