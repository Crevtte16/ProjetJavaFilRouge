import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "equipe")
public class Equipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
// pour la jpa
    protected Equipe() {

    }

    public Equipe(String nom) {


        this.nom = nom;
    }

    public Long getId() {
        return id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }


    @Override
    public String toString() {
        return "Equipe{" +
                "id=" + id + ""
                + ", nom='" + nom + "'}";

    }


}
