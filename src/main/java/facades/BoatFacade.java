package facades;

import dtos.Boat.BoatDTO;
import dtos.Boat.BoatsDTO;
import entities.Boat;
import entities.Harbour;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class BoatFacade {

    private static EntityManagerFactory emf;
    private static BoatFacade instance;

    private BoatFacade() {
    }

    public static BoatFacade getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new BoatFacade();
        }
        return instance;
    }

    public BoatsDTO getAllBoats() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Boat> query = em.createQuery("SELECT b FROM Boat b", Boat.class);
            List<Boat> result = query.getResultList();
            return new BoatsDTO(result);
        } finally {
            em.close();
        }
    }

    public BoatsDTO getBoatsByHarbour(int harbourId) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Boat> query = em.createQuery("SELECT b FROM Boat b WHERE b.harbour.id =:harbourId", Boat.class);
            query.setParameter("harbourId", harbourId);
            List<Boat> result = query.getResultList();
            return new BoatsDTO(result);
        } finally {
            em.close();
        }
    }

    public BoatDTO createBoat(BoatDTO boatDTO) {
        EntityManager em = emf.createEntityManager();
        Boat boat = new Boat(boatDTO.getBrand(), boatDTO.getMake(), boatDTO.getName(), boatDTO.getImage());
        try {
            em.getTransaction().begin();
            em.persist(boat);
            em.getTransaction().commit();
            return new BoatDTO(boat);
        } finally {
            em.close();
        }
    }

    //TODO: Error Handling with harbour capacity
    public BoatDTO connectToHarbor(int boatId, int harbourId) {
        EntityManager em = emf.createEntityManager();
        Boat boat = em.find(Boat.class, boatId);
        Harbour harbour = em.find(Harbour.class, harbourId);
        boat.setHarbour(harbour);
        try {
            em.getTransaction().begin();
            em.merge(boat);
            em.getTransaction().commit();
            return new BoatDTO(boat);
        } finally {
            em.close();
        }
    }
}
