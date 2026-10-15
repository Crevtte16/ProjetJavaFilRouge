

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.EntityTransaction;


import java.util.Objects;

@Entity
@Table(name = "equipe")
public class Equipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    protected Equipe() {
        // exigé par JPA
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


    public void ajouter(Collaborateur collaborateur) {
        dansUneTransaction(em -> {
            // find cherche par clé primaire : null = identifiant libre
            if (em.find(Collaborateur.class, collaborateur.getIdentifiant()) != null) {
                logger.warn("Doublon refusé pour l'identifiant {}", collaborateur.getIdentifiant());
                throw new CollaborateurDejaExistantException(collaborateur.getIdentifiant());
            }
            em.persist(collaborateur);
            return null;
        });
        logger.info("Collaborateur {} enregistré", collaborateur.getIdentifiant());
    }
    // equals/hashCode basés sur l'id : adaptés aux entités JPA
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Equipe other)) return false;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Equipe{id=" + id + ", nom='" + nom + "'}";
    }
}