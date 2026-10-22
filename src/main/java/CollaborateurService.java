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


    public boolean ajouter(Collaborateur collaborateur) {

        EntityManager em = fabrique.createEntityManager();

        try {
            em.getTransaction().begin();

            em.persist(collaborateur);

            em.getTransaction().commit();

            return true;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            System.out.println(
                    "Erreur lors de l'enregistrement en base : "
                            + e.getMessage()
            );

            return false;

        } finally {
            em.close();
        }
    }

// Affiche les collabo de la bdd
    public java.util.List<Collaborateur> tous() {
        EntityManager em = fabrique.createEntityManager();

        try {
            return em.createQuery(
                    "SELECT c FROM Collaborateur c ORDER BY c.nom, c.prenom",
                    Collaborateur.class
            ).getResultList();
        } finally {
            em.close();
        }
    }


}
