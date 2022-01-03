package facades;

import dtos.Boat.BoatsDTO;
import entities.Boat;

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
}
