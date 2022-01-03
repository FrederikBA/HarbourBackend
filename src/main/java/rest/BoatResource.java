package rest;

import javax.ws.rs.*;

@Path("/boat")
public class BoatResource {
    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello, World!";
    }
}