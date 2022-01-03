package facades;

import dtos.Boat.BoatDTO;
import dtos.Boat.BoatsDTO;
import dtos.Owner.OwnerDTO;
import entities.Boat;
import entities.Harbour;
import entities.Owner;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import static org.junit.jupiter.api.Assertions.*;

class BoatFacadeTest {
    private static EntityManagerFactory emf;
    private static BoatFacade facade;
    private static Boat b1, b2, b3;
    private static Harbour h1, h2;

    public BoatFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = BoatFacade.getInstance(emf);
    }

    @AfterAll
    public static void tearDownClass() {
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

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void getBoatsByHarbourTest() {
        List<BoatDTO> boats = facade.getBoatsByHarbour(h1.getId()).getBoats();

        int expected = 2;
        int actual = boats.size();

        BoatDTO b1DTO = new BoatDTO(b1);
        BoatDTO b2DTO = new BoatDTO(b2);
        BoatDTO b3DTO = new BoatDTO(b3);

        //Test if the amount of boats in the harbour h1 is correct.
        assertEquals(expected, actual);

        //Test that the harbour in fact contains the correct boats.
        assertThat(boats, containsInAnyOrder(b1DTO, b2DTO));

        //Test that the harbour doesn't contain the boat b3
        assertThat(boats, not(hasItem(b3DTO)));
    }

    @Test
    public void createBoatTest() {
        //Create a new boat: b4DTO.
        BoatDTO b4DTO = facade.createBoat(new BoatDTO("TestBrandFour", "TestMakeFour", "TestNameFour", "TestImageFour"));

        List<BoatDTO> boats = facade.getAllBoats().getBoats();

        //Test if the size of the boat array is now 4 instead of 3.
        int expected = 4;
        int actual = boats.size();
        assertEquals(expected, actual);

        //Confirm that b4DTO has been added to the list of boats.
        assertThat(boats, hasItem(b4DTO));

    }

    @Test
    public void connectBoatTest() {
        //Connect boat b3 to harbour h2 using their ID(s).
        facade.connectToHarbor(b3.getId(), h2.getId());

        //Get the list of boats in the harbour h2.
        List<BoatDTO> harbourTwo = facade.getBoatsByHarbour(h2.getId()).getBoats();

        BoatDTO b3DTO = new BoatDTO(b3);

        //Confirm that harbour h2 now has the boat b3
        assertThat(harbourTwo, hasItem(b3DTO));
    }
}