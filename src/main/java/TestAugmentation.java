import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class TestAugmentation {

    public static void main(String[] args) {

        EntityManagerFactory fabrique =
                Persistence.createEntityManagerFactory("collaborateurs-pu");

        EntityManager em = fabrique.createEntityManager();

        try {
            em.getTransaction().begin();

            Collaborateur alice =
                    em.find(Collaborateur.class, "C010");

            System.out.println("Avant : " + alice);

            alice.augmenterSalaire(10);

            System.out.println("Après : " + alice);

            em.getTransaction().commit();

        } finally {
            em.close();
            fabrique.close();
        }
    }
}