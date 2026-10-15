import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class TestJPA {

    public static void main(String[] args) {
        System.out.println(TestJPA.class.getClassLoader().getResource("META-INF/persistence.xml")
        );

        EntityManagerFactory fabrique = Persistence.createEntityManagerFactory("collaborateurs-pu");
        System.out.println("JPA ok");
        fabrique.close();
    }
}
