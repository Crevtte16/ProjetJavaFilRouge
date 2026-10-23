
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

public class CollaborateurService {

    private final EntityManagerFactory fabrique;

    public CollaborateurService(EntityManagerFactory fabrique) {
        this.fabrique = fabrique;}

    // Rechercher collaborateur avec son identifiant
    public Collaborateur rechercher(String identifiant) {
        EntityManager em = fabrique.createEntityManager();

        try {
            Collaborateur collaborateur = em.find(Collaborateur.class, identifiant);
            return collaborateur;
        } finally {
            em.close();



        }
    }

    // Ajouter un collaborateur dans la bdd
    public boolean ajouter(Collaborateur collaborateur) {
        EntityManager em = fabrique.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(collaborateur);
            em.getTransaction().commit();
            return true;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {em.getTransaction().rollback();}

            System.out.println("Erreur lors de l'enregistrement en base : " + e.getMessage());
            return false;

        } finally {em.close();}
    }

    // Récupérer tous les collaborateurs de la bdd
    public List<Collaborateur> tous() {
        EntityManager em = fabrique.createEntityManager();

        try {

            List<Collaborateur> liste = em.createQuery(
                    "SELECT c FROM Collaborateur c ORDER BY c.nom, c.prenom", Collaborateur.class).getResultList();

            return liste;

        } finally {
            em.close();


        }

    }
}
