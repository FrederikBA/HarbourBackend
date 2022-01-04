package facades;

import dtos.Harbour.HarboursDTO;
import entities.Harbour;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class HarbourFacade {

    private static EntityManagerFactory emf;
    private static HarbourFacade instance;

    private HarbourFacade() {
    }

    public static HarbourFacade getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new HarbourFacade();
        }
        return instance;
    }

    public HarboursDTO getAllHarbours() {
        EntityManager em = emf.createEntityManager();

        try {
            TypedQuery<Harbour> query = em.createQuery("SELECT h from Harbour h", Harbour.class);
            List<Harbour> result = query.getResultList();
            return new HarboursDTO(result);
        } finally {
            em.close();
        }
    }
}
