package rest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;

import dtos.Boat.BoatDTO;
import dtos.Owner.OwnerDTO;
import entities.Boat;
import entities.Harbour;
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

class BoatResourceTest {
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Boat b1, b2, b3;
    private static Harbour h1, h2;

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
        b1 = new Boat("TestBrandOne", "TestMakeOne", "TestNameOne", "TestImageOne");
        b2 = new Boat("TestBrandTwo", "TestMakeTwo", "TestNameTwo", "TestImageTwo");
        b3 = new Boat("TestBrandThree", "TestMakeThree", "TestNameThree", "TestImageThree");

        h1 = new Harbour("TestNameOne", "TestAddressOne", 50);
        h2 = new Harbour("TestNameTwo", "TestAddressTwo", 30);

        b1.setHarbour(h1);
        b2.setHarbour(h1);
        b3.setHarbour(h2);
        try {
            em.getTransaction().begin();
            em.createQuery("delete from Boat").executeUpdate();
            em.createQuery("delete from Harbour").executeUpdate();
            em.persist(b1);
            em.persist(b2);
            em.persist(b3);
            em.persist(h1);
            em.persist(h2);
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
                .when().post("/login")
                .then()
                .extract().path("token");
        //System.out.println("TOKEN ---> " + securityToken);
    }

    private void logOut() {
        securityToken = null;
    }

    @Test
    public void testGetBoatsByHarbour() {
        List<BoatDTO> boats;

        BoatDTO b1DTO = new BoatDTO(b1);
        BoatDTO b2DTO = new BoatDTO(b2);
        BoatDTO b3DTO = new BoatDTO(b3);

        boats = given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .pathParam("id", h1.getId())
                .when()
                .get("/boat/{id}").then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath()
                .getList("boats", BoatDTO.class);

        assertEquals(2, boats.size());

        assertThat(boats, containsInAnyOrder(b1DTO, b2DTO));

        assertThat(boats, not(hasItem(b3DTO)));
    }

    @Test
    public void testCreateBoat() {
        given()
                .contentType("application/json")
                .body(new BoatDTO("TestBrandFour", "TestMakeFour", "TestNameFour", "TestImageFour"))
                .when()
                .post("boat")
                .then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("brand", equalTo("TestBrandFour"))
                .body("make", equalTo("TestMakeFour"))
                .body("name", equalTo("TestNameFour"))
                .body("image", equalTo("TestImageFour"));

    }
}