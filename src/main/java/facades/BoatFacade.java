package facades;

import javax.persistence.EntityManagerFactory;

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
}
