
public class CollaborateurDejaExistantException extends RuntimeException {
    private final String identifiant;
    public CollaborateurDejaExistantException(String identifiant) {
        super("Le collaborateur " + identifiant + " existe déjà.");
        this.identifiant = identifiant;
    }
    public String identifiant() {
        return identifiant;
    }
}
