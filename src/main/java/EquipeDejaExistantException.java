public class EquipeDejaExistantException extends RuntimeException {
    private final String nomEquipe;
    public EquipeDejaExistantException(String nomEquipe) {
        super("L'equipe " + nomEquipe + " existe déjà.");
        this.nomEquipe = nomEquipe;
    }
    public String nomEquipe() {
        return nomEquipe;
    }







}

