
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

public class EquipeService {

    private final EntityManagerFactory fabrique;

    public EquipeService(EntityManagerFactory fabrique) {
        this.fabrique = fabrique;
    }

    public List<Equipe> toutesLesEquipes() {

        EntityManager em = fabrique.createEntityManager();

        try {
            return em.createQuery(
                    "SELECT e FROM Equipe e ORDER BY e.nom",
                    Equipe.class
            ).getResultList();
        } finally {
            em.close();
        }
    }


    public boolean ajouterEquipe(String nom) {

        if (nom == null || nom.isBlank()) {
            System.out.println("Erreur : le nom de l'équipe est obligatoire.");
            return false;
        }

        EntityManager em = fabrique.createEntityManager();

        try {
            em.getTransaction().begin();

            Long nombre = em.createQuery(
                            "SELECT COUNT(e) FROM Equipe e WHERE LOWER(e.nom) = LOWER(:nom)",
                            Long.class
                    ).setParameter("nom", nom.trim())
                    .getSingleResult();

            if (nombre > 0) {
                em.getTransaction().rollback();
                System.out.println("Erreur : une équipe porte déjà ce nom.");
                return false;
            }

            Equipe equipe = new Equipe(nom.trim());
            em.persist(equipe);

            em.getTransaction().commit();

            System.out.println("Équipe ajoutée avec succès.");
            return true;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("Erreur lors de l'ajout de l'équipe.");
            return false;

        } finally {
            em.close();
        }
    }

}
