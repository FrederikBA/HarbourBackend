package facades;

import entities.Owner;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.hasItem;

class OwnerFacadeTest {
    private static EntityManagerFactory emf;
    private static OwnerFacade facade;
    private static Owner o1, o2, o3;

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

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void getAllOwnersTest() {
        int expected = 3;
        int actual = facade.getAllOwners().getOwners().size();
        assertEquals(expected, actual);
    }
}