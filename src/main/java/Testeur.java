import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;


import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue("Testeur")
public class Testeur extends Collaborateur {

    // Constructeur sans paramètre : utilisé par Hibernate
    protected Testeur() {
        super();
    }

    // Ton constructeur actuel : à conserver
    public Testeur(
            String identifiant,
            String prenom,
            String nom,
            double salaire,
            Adresse adresse) {
        super(identifiant, prenom, nom, salaire, adresse);
    }

    @Override
    public String getMetier() {
        return "Testeur";
    }

    @Override
    public void travailler() {
        System.out.println(getPrenom() + " exécute une campagne de tests.");
    }
}
