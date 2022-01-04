package facades;

import dtos.Boat.BoatDTO;
import dtos.Boat.BoatsDTO;
import dtos.Harbour.HarbourDTO;
import dtos.Owner.OwnerDTO;
import entities.Boat;
import entities.Harbour;
import entities.Owner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.ws.rs.WebApplicationException;
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

        //Add harbour
        TypedQuery<Harbour> query = em.createQuery("SELECT h FROM Harbour h WHERE h.id =:harbourId", Harbour.class);
        query.setParameter("harbourId", boatDTO.getHarbour().getId());
        Harbour harbour = query.getSingleResult();
        boat.setHarbour(harbour);


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

    public BoatDTO editBoat(BoatDTO boatDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            Boat boat = em.find(Boat.class, boatDTO.getId());

            //Update boat values besides owner and harbour
            boat.setBrand(boatDTO.getBrand());
            boat.setMake(boatDTO.getMake());
            boat.setName(boatDTO.getName());
            boat.setImage(boatDTO.getImage());

            // Edit owners

            //Clear owners from the boat before to allow new owners to be set
            boat.getOwners().clear();

            for (int i = 0; i < boatDTO.getOwners().size(); i++) {
                OwnerDTO ownerDTO = boatDTO.getOwners().get(i);

                try {
                    Owner foundOwner = em.createQuery("SELECT o FROM Owner o WHERE o.name = :owner", Owner.class).setParameter("owner", ownerDTO.getName()).getSingleResult();
                    boat.addOwner(foundOwner);
                } catch (NoResultException error) {
                    throw new WebApplicationException("Owner: " + ownerDTO.getName() + ", does not exist", 400);
                }
            }

            //Edit harbour
            TypedQuery<Harbour> query = em.createQuery("SELECT h FROM Harbour h WHERE h.id =:harbourId", Harbour.class);
            query.setParameter("harbourId", boatDTO.getHarbour().getId());
            Harbour harbour = query.getSingleResult();
            boat.setHarbour(harbour);

            //Persist the boat
            em.getTransaction().begin();
            em.merge(boat);
            em.getTransaction().commit();

            return new BoatDTO(boat);
        } finally {
            em.close();
        }
    }

    public BoatDTO deleteBoat(int id) {
        EntityManager em = emf.createEntityManager();
        Boat boat = em.find(Boat.class, id);
        try {
            em.getTransaction().begin();
            em.createNativeQuery("DELETE FROM BOAT_OWNER WHERE boats_id = ?").setParameter(1, boat.getId()).executeUpdate();
            em.remove(boat);
            em.getTransaction().commit();
            return new BoatDTO(boat);
        } finally {
            em.close();
        }
    }
}
