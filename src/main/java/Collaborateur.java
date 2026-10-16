import java.util.Objects;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.ManyToOne;


@Entity
public abstract class Collaborateur {

    @Id
    private String identifiant;   // Nouveau en TP3 — clé logique unique (ex. "C001")
    private String prenom;
    private String nom;
    private double salaire;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "adresse_id")
    private Adresse adresse;

    @ManyToOne
    @JoinColumn(name = "equipe_id")
    private Equipe equipe;

    protected Collaborateur() {
    }
    protected Collaborateur(
            String identifiant,
            String prenom,
            String nom,
            double salaire,
            Adresse adresse) {
        this.identifiant = identifiant;
        this.prenom = prenom;
        this.nom = nom;
        this.salaire = salaire;
        this.adresse = adresse;
    }

    // --- Accesseurs ---

    public String getIdentifiant() { return identifiant; }
    public String getPrenom()      { return prenom; }
    public String getNom()         { return nom; }
    public double getSalaire()     { return salaire; }
    public Adresse getAdresse()    { return adresse; }

    // --- Accesseurs Ajouter pour voir et attribuer une equipe ---
    public Equipe getEquipe() {return equipe;}
    public void setEquipe(Equipe equipe) {this.equipe = equipe;}

    // --- Comportements ---

    /** Augmente le salaire du pourcentage donné. Ignoré si le pourcentage est négatif ou nul. */
    public void augmenterSalaire(double pourcentage) {
        if (pourcentage > 0) {
            salaire = salaire * (1 + pourcentage / 100);
        }
    }

    /** Chaque sous-classe définit son propre métier (utilisé dans afficherFiche et toString). */
    public abstract String getMetier();

    /** Chaque sous-classe définit sa manière de travailler. */
    public abstract void travailler();

    /**
     * Affichage détaillé de la fiche du collaborateur (multiligne).
     * Les sous-classes peuvent surcharger pour ajouter leurs propres informations.
     */
    public void afficherFiche() {
        System.out.println("[" + identifiant + "] " + prenom + " " + nom);
        System.out.println("Métier  : " + getMetier());
        System.out.println("Salaire : " + salaire + " €");
        System.out.println("Adresse :");
        adresse.afficher();
    }

    // --- Contrat equals/hashCode basé sur l'identifiant (TP3) ---

    /**
     * Deux collaborateurs sont logiquement égaux s'ils partagent le même identifiant.
     * Cela permet un comportement correct dans les collections (Set, Map, contains...).
     */
    @Override
    public boolean equals(Object autre) {
        if (this == autre) return true;
        if (!(autre instanceof Collaborateur collaborateur)) return false;
        return identifiant.equals(collaborateur.identifiant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifiant);
    }

    /** Représentation compacte sur une ligne (utilisée dans les listes). */
    @Override
    public String toString() {
        return String.format("[%s] %s %s (%s) - %.2f €",
                identifiant, prenom, nom, getMetier(), salaire);
    }
}
