package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.BoatFacade;
import facades.HarbourFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

@Path("/harbour")
public class HarbourResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final HarbourFacade facade = HarbourFacade.getInstance(EMF);
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @Path("/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAll() {
        return gson.toJson(facade.getAllHarbours());
    }
}