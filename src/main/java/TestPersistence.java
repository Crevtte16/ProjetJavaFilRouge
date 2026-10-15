import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class TestPersistence {

    public static void main(String[] args) {

        EntityManagerFactory fabrique =
                Persistence.createEntityManagerFactory("collaborateurs-pu");

        EntityManager em = fabrique.createEntityManager();

        try {
            em.getTransaction().begin();

            Programmeur alice = new Programmeur(
                    "C010",
                    "Alice",
                    "Martin",
                    42000,
                    null,
                    "Java"
            );

            em.persist(alice);

            em.getTransaction().commit();

            System.out.println("Collaborateur enregistré !");

        } finally {
            em.close();
            fabrique.close();
        }
    }
}