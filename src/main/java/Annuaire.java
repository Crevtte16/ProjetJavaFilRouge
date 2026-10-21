import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Annuaire des collaborateurs de l'entreprise.
 *
 * Stockage interne : LinkedHashMap<String, Collaborateur>
 *   - La clé est l'identifiant du collaborateur (ex. "C001").
 *   - LinkedHashMap préserve l'ordre d'insertion (utile pour l'affichage).
 *   - Accès en O(1) par identifiant (vs. O(n) avec une simple liste).
 *
 * Point clé TP3 : equals()/hashCode() de Collaborateur (basé sur l'identifiant)
 * est essentiel pour que containsKey() et remove() fonctionnent correctement.
 */
public class Annuaire {

    private Map<String, Collaborateur> collaborateurs = new LinkedHashMap<>();

    /**
     * Ajoute un collaborateur à l'annuaire.
     *
     * @return true si l'ajout a réussi, false si l'identifiant est déjà utilisé.
     */
    public boolean ajouter(Collaborateur collaborateur) {

        String identifiant = collaborateur.getIdentifiant();

        if (collaborateurs.containsKey(identifiant)) {
            throw new CollaborateurDejaExistantException(identifiant);
        }
        collaborateurs.put(identifiant, collaborateur);
        return true;
    }


    public Collaborateur trouver(String identifiant) {
        return collaborateurs.get(identifiant);
    }


    public boolean supprimer(String identifiant) {
        return collaborateurs.remove(identifiant) != null;
    }


    public int taille() {
        return collaborateurs.size();
    }


    public List<Collaborateur> tous() {
        return new ArrayList<>(collaborateurs.values());
    }

    // --- Filtres ---


    public List<Programmeur> programmeurs() {
        List<Programmeur> liste = new ArrayList<>();
        for (Collaborateur c : collaborateurs.values()) {
            // instanceof avec pattern variable (Java 16+)
            if (c instanceof Programmeur programmeur) {
                liste.add(programmeur);
            }
        }
        return liste;
    }


    public List<Collaborateur> nomContenant(String fragment) {
        List<Collaborateur> liste = new ArrayList<>();
        for (Collaborateur c : collaborateurs.values()) {
            if (c.getNom().toLowerCase().contains(fragment.toLowerCase())) {
                liste.add(c);
            }
        }
        return liste;
    }

    public List<Collaborateur> salaireSuperieurA(double seuil) {
        List<Collaborateur> liste = new ArrayList<>();
        for (Collaborateur c : collaborateurs.values()) {
            if (c.getSalaire() > seuil) {
                liste.add(c);
            }
        }
        return liste;
    }


    public List<Collaborateur> triesParNom() {
        return triesSelon(Comparator.comparing(Collaborateur::getNom));
    }


    public List<Collaborateur> triesParSalaire() {
        return triesSelon(Comparator.comparingDouble(Collaborateur::getSalaire));
    }


    public List<Collaborateur> triesParNomPuisPrenom() {
        return triesSelon(
            Comparator.comparing(Collaborateur::getNom)
                      .thenComparing(Collaborateur::getPrenom)
        );
    }


    private List<Collaborateur> triesSelon(Comparator<Collaborateur> comparateur) {
        List<Collaborateur> liste = tous();
        liste.sort(comparateur);
        return Collections.unmodifiableList(liste);
    }
}
