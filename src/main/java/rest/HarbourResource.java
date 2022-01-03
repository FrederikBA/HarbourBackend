package rest;

import javax.ws.rs.*;

@Path("/harbour")
public class HarbourResource {
    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello, World!";
    }
}