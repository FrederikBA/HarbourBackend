package rest;

import javax.ws.rs.*;

@Path("/owner")
public class OwnerResource {
    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello, World!";
    }
}