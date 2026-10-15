import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Adresse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rue;
    private String codePostal;
    private String ville;
    private String pays;

    protected Adresse() {
    }

    public Adresse(String rue, String codePostal, String ville, String pays) {
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
        this.pays = pays;
    }

    public Long getId() {
        return id;
    }

    public String getRue() {
        return rue;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public String getVille() {
        return ville;
    }

    public String getPays() {
        return pays;
    }

    public void afficher() {
        System.out.println(rue);
        System.out.println(codePostal + " " + ville);
        System.out.println(pays);
    }

    @Override
    public String toString() {
        return rue + ", " + codePostal + " " + ville + " (" + pays + ")";
    }
}