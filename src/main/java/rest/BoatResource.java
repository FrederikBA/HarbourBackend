package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.Boat.BoatDTO;
import facades.BoatFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

@Path("/boat")
public class BoatResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final BoatFacade facade = BoatFacade.getInstance(EMF);
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getBoatsByHarbour(@PathParam("id") int id) {
        return gson.toJson(facade.getBoatsByHarbour(id));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String addBoat(String boat) {
        BoatDTO b = gson.fromJson(boat, BoatDTO.class);
        BoatDTO bNew = facade.createBoat(b);
        return gson.toJson(bNew);
    }
}