package facades;

import dtos.Owner.OwnersDTO;
import entities.Owner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class OwnerFacade {

    private static EntityManagerFactory emf;
    private static OwnerFacade instance;

    private OwnerFacade() {
    }

    public static OwnerFacade getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new OwnerFacade();
        }
        return instance;
    }

    public OwnersDTO getAllOwners() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Owner> query = em.createQuery("SELECT o from Owner o", Owner.class);
            List<Owner> result = query.getResultList();
            return new OwnersDTO(result);
        } finally {
            em.close();
        }
    }

    public OwnersDTO getOwnersByBoat(int boatId) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Owner> query = em.createQuery("SELECT o from Owner o JOIN o.boats b WHERE b.id =:boatId", Owner.class);
            query.setParameter("boatId", boatId);
            List<Owner> result = query.getResultList();
            return new OwnersDTO(result);
        } finally {
            em.close();
        }
    }
}
