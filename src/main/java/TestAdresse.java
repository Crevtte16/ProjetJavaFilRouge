import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class TestAdresse {

    public static void main(String[] args) {

        EntityManagerFactory fabrique = Persistence.createEntityManagerFactory("collaborateurs-pu");
        EntityManager em = fabrique.createEntityManager();

        try {em.getTransaction().begin();
            Adresse adresse = new Adresse(
                    "10 rue de Paris",
                    "75001",
                    "Paris",
                    "France"
            );
            Programmeur alice = new Programmeur(
                    "C020",
                    "Alice",
                    "Martin",
                    42000,
                    adresse,
                    "Java"
            );

            em.persist(alice);
            em.getTransaction().commit();
            System.out.println("Alice et son adresse ont été enregistrées !");

        } finally {
            em.close();
            fabrique.close();
        }
    }
}