
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
}
