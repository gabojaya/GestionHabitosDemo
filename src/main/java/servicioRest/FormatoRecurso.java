package servicioRest;

import java.sql.SQLException;
import java.util.List;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import modelo.rest.MetaImpDAO;
import modelo.dao.MetaRestDAO;
import modelo.entidades.Meta;

@Path("/formato")
public class FormatoRecurso {
    
    private MetaRestDAO metaDAO;

    public FormatoRecurso() {
        this.metaDAO = new MetaImpDAO(); // Inyectar dependencia
    }

    @Path("/xml")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Meta> getMetaXML() {
        try {
			return this.metaDAO.obtenerMetasPorUsuario(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }

    @Path("/json")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Meta> getMetaJSON() {
        try {
			return this.metaDAO.obtenerMetasPorUsuario(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }

    @Path("/text")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getMetaTEXT() {
        Meta meta = this.metaDAO.obtenerMetaPorId(1);
        return Response.ok(meta != null ? meta.toString() : "Meta not found").build();
    }
}
