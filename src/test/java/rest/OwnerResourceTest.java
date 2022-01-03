package rest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;

import dtos.Owner.OwnerDTO;
import entities.Owner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


class OwnerResourceTest {
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Owner o1, o2, o3;

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();
        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        o1 = new Owner("TestNameOne", "TestAddressOne", "TestPhoneOne");
        o2 = new Owner("TestNameTwo", "TestAddressTwo", "TestPhoneTwo");
        o3 = new Owner("TestNameThree", "TestAddressThree", "TestPhoneThree");

        try {
            em.getTransaction().begin();
            em.createQuery("delete from Owner").executeUpdate();
            em.persist(o1);
            em.persist(o2);
            em.persist(o3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    //This is how we hold on to the token after login, similar to that a client must store the token somewhere
    private static String securityToken;

    //Utility method to login and set the returned securityToken
    private static void login(String role, String password) {
        String json = String.format("{username: \"%s\", password: \"%s\"}", role, password);
        securityToken = given()
                .contentType("application/json")
                .body(json)
                //.when().post("/api/login")
                .when().post("/login")
                .then()
                .extract().path("token");
        //System.out.println("TOKEN ---> " + securityToken);
    }

    private void logOut() {
        securityToken = null;
    }

    @Test
    public void testServerIsUp() {
        given().when().get("/owner/all").then().statusCode(200);
    }

    @Test
    public void testGetAll() {
        List<OwnerDTO> owners;
        owners = given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .get("/owner/all").then()
                .extract()
                .body()
                .jsonPath()
                .getList("owners", OwnerDTO.class);

        assertEquals(3, owners.size());

        OwnerDTO o1DTO = new OwnerDTO(o1);
        OwnerDTO o2DTO = new OwnerDTO(o2);
        OwnerDTO o3DTO = new OwnerDTO(o3);

        assertThat(owners, containsInAnyOrder(o1DTO, o2DTO, o3DTO));
    }
}