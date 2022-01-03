package facades;

import dtos.Boat.BoatDTO;
import dtos.Owner.OwnerDTO;
import entities.Boat;
import entities.Owner;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.hasItem;

class OwnerFacadeTest {
    private static EntityManagerFactory emf;
    private static OwnerFacade facade;
    private static Owner o1, o2, o3;
    private static Boat b1, b2, b3;

    public OwnerFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = OwnerFacade.getInstance(emf);
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        o1 = new Owner("TestNameOne", "TestAddressOne", "TestPhoneOne");
        o2 = new Owner("TestNameTwo", "TestAddressTwo", "TestPhoneTwo");
        o3 = new Owner("TestNameThree", "TestAddressThree", "TestPhoneThree");

        b1 = new Boat("TestBrandOne", "TestMakeOne", "TestNameOne", "TestImageOne");
        b2 = new Boat("TestBrandTwo", "TestMakeTwo", "TestNameTwo", "TestImageTwo");
        b3 = new Boat("TestBrandThree", "TestMakeThree", "TestNameThree", "TestImageThree");

        o1.addBoat(b1);
        o1.addBoat(b2);
        o2.addBoat(b1);
        o2.addBoat(b3);

        try {
            em.getTransaction().begin();
            em.createQuery("delete from Owner").executeUpdate();
            em.createQuery("delete from Boat").executeUpdate();
            em.persist(o1);
            em.persist(o2);
            em.persist(o3);
            em.persist(b1);
            em.persist(b2);
            em.persist(b3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void getAllOwnersTest() {
        int expected = 3;
        int actual = facade.getAllOwners().getOwners().size();
        assertEquals(expected, actual);
    }

    @Test
    public void getOwnersByBoatTest() {
        List<OwnerDTO> owners = facade.getOwnersByBoat(b1.getId()).getOwners();

        OwnerDTO o1DTO = new OwnerDTO(o1);
        OwnerDTO o2DTO = new OwnerDTO(o2);
        OwnerDTO o3DTO = new OwnerDTO(o3);

        int expected = 2;
        int actual = owners.size();

        assertEquals(expected, actual);

        assertThat(owners, containsInAnyOrder(o1DTO, o2DTO));

        assertThat(owners, not(hasItem(o3DTO)));    
    }
}