import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class TestRecherche {

    public static void main(String[] args) {

        EntityManagerFactory fabrique = Persistence.createEntityManagerFactory("collaborateurs-pu");
        CollaborateurService service = new CollaborateurService(fabrique);
        Collaborateur alice = service.rechercher("C010");
        System.out.println("Résultat : " + alice);
        Collaborateur inconnu = service.rechercher("C999");
        System.out.println("C999 : " + inconnu);
        fabrique.close();
    }
}