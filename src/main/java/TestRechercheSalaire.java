import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class TestRechercheSalaire {

    public static void main(String[] args) {

        EntityManagerFactory fabrique =
                Persistence.createEntityManagerFactory("collaborateurs-pu");

        EntityManager em = fabrique.createEntityManager();

        try {
            List<Collaborateur> resultats = em.createQuery(
                            "select c from Collaborateur c where salaire > :seuil", Collaborateur.class).setParameter("seuil", "45000").getResultList();

            for (Collaborateur collaborateur : resultats) {

                System.out.println(collaborateur);
            }

        }
        finally {
            em.close();
            fabrique.close();
        }
    }
}