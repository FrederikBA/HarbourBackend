package facades;

import javax.persistence.EntityManagerFactory;

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
}
