import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class CollaborateurService {

    private final EntityManagerFactory fabrique;
    public CollaborateurService(EntityManagerFactory fabrique) {
        this.fabrique = fabrique;
    }

    public Collaborateur rechercher(String identifiant) {

        EntityManager em = fabrique.createEntityManager();

        try { return em.find(Collaborateur.class, identifiant);
        }
        finally {
            em.close();
        }
    }
}
