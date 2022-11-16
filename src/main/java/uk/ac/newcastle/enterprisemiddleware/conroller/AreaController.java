package uk.ac.newcastle.enterprisemiddleware.conroller;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import uk.ac.newcastle.enterprisemiddleware.vobject.Area;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

/**
 * <p>Clientside representation of an AreaCode object pulled from an external RESTFul API.</p>
 *
 * <p>This is the mirror opposite of a server side JAX-RS service</p>
 *
 * @author hugofirth
 */
@Path("/areas")
@RegisterRestClient(configKey = "area-api")
public interface AreaController {

    @GET
    List<Area> getAreas();


    @GET
    @Path("/{id:[0-9]+}")
    Area getAreaById(@PathParam("id") int id);
}