package facades;

import javax.persistence.EntityManagerFactory;

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
}
