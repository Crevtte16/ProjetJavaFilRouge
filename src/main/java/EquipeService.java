
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

public class EquipeService {

    private final EntityManagerFactory fabrique;

    public EquipeService(EntityManagerFactory fabrique) {
        this.fabrique = fabrique;
    }

    // Classes du menu Gestion equipe

    // Afficher les equipes

    public List<Equipe> toutesLesEquipes() {

        EntityManager em = fabrique.createEntityManager();

        try {
            return em.createQuery(
                    "SELECT e FROM Equipe e ORDER BY e.nom",
                    Equipe.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    // Ajouter des equipes
    public boolean ajouterEquipe(String nom) {

        if (nom == null || nom.isBlank()) {
            System.out.println("Erreur : le nom de l'équipe est obligatoire.");
            return false;
        }

        EntityManager em = fabrique.createEntityManager();

        try {
            em.getTransaction().begin();

            Long nombre = em.createQuery(
                            "SELECT COUNT(e) FROM Equipe e WHERE LOWER(e.nom) = LOWER(:nom)", Long.class).setParameter("nom", nom.trim()).getSingleResult();

            if (nombre > 0) {
                em.getTransaction().rollback();
                System.out.println("Impossible, il y a deja un equipe avec ce nom");
                return false;
            }

            Equipe equipe = new Equipe(nom.trim());
            em.persist(equipe);

            em.getTransaction().commit();

            System.out.println("Équipe ajoutée avec succès!");
            return true;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("Erreur lors de l'ajout de l'équipe.");
            return false;

        } finally {
            em.close();
        }
    }

    // affacter un collaboratuer a une  equipe
    public boolean affecterCollaborateur(String identifiant, Long idEquipe) {

        EntityManager em = fabrique.createEntityManager();

        try {
            em.getTransaction().begin();

            Collaborateur collaborateur =
                    em.find(Collaborateur.class, identifiant);

            Equipe equipe = em.find(Equipe.class, idEquipe);

            if (collaborateur == null) {
                em.getTransaction().rollback();
                System.out.println("Erreur :  ce collaborateur  est introuvable.");
                return false;
            }

            if (equipe == null) {
                em.getTransaction().rollback();
                System.out.println("Erreur : l'équipe est introuvable.");
                return false;
            }

            if (collaborateur.getEquipe() != null) {
                em.getTransaction().rollback();
                System.out.println(
                        "Erreur : ce collaborateur appartient déjà à une équipe."
                );
                return false;
            }

            collaborateur.setEquipe(equipe);

            em.getTransaction().commit();

            System.out.println("Collaborateur affecter à l'équipe avec succès.");
            return true;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            System.out.println("Erreur lors de l'affectation du collaborateur.");
            return false;

        } finally {
            em.close();
        }
    }
    // retirer un collaboratuer a une  equipe

    public boolean retirerCollaborateur(String identifiant, Long idEquipe) {

        EntityManager em = fabrique.createEntityManager();

        try {
            em.getTransaction().begin();

            Collaborateur collaborateur =
                    em.find(Collaborateur.class, identifiant);

            Equipe equipe = em.find(Equipe.class, idEquipe);

            if (collaborateur == null) {
                em.getTransaction().rollback();
                System.out.println("Erreur : collaboteur introuvable.");
                return false;
            }

            if (equipe == null) {
                em.getTransaction().rollback();
                System.out.println("Erreur : équipe introuvable.");
                return false;
            }

            if (collaborateur.getEquipe() == null
                    || !collaborateur.getEquipe().getId().equals(idEquipe)) {
                em.getTransaction().rollback();
                System.out.println(
                        "Erreur : ce collaborateur n'appartient pas à cette équipe."
                );
                return false;
            }

            collaborateur.setEquipe(null);

            em.getTransaction().commit();

            System.out.println("Collaborateur retiré de l'équipe avec succès.");
            return true;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            System.out.println("Retrait du collabo impossible.");
            return false;

        } finally {
            em.close();
        }
    }
    // renommer  une  equipe

    public boolean renommerEquipe(Long idEquipe, String nouveauNom) {

        if (nouveauNom == null || nouveauNom.isBlank()) {
            System.out.println("Erreur : il est obligatoir de saisir un nom.");
            return false;
        }

        nouveauNom = nouveauNom.trim();

        EntityManager em = fabrique.createEntityManager();

        try {
            em.getTransaction().begin();

            Equipe equipe = em.find(Equipe.class, idEquipe);

            if (equipe == null) {
                em.getTransaction().rollback();
                System.out.println("Erreur : équipe introuvable.");
                return false;
            }

            Long nombre = em.createQuery(
                            "SELECT COUNT(e) FROM Equipe e " +
                                    "WHERE LOWER(e.nom) = LOWER(:nom) AND e.id <> :id", Long.class)
                    .setParameter("nom", nouveauNom)
                    .setParameter("id", idEquipe)
                    .getSingleResult();

            if (nombre > 0) {
                em.getTransaction().rollback();
                System.out.println("Erreur : ce nom est déjà utilisé.");
                return false;
            }

            equipe.setNom(nouveauNom);

            em.getTransaction().commit();

            System.out.println("Équipe renommée avec succès.");
            return true;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            System.out.println("Erreur lors du renommage de l'équipe.");
            return false;

        } finally {
            em.close();
        }
    }
// Supp une equipe
    public boolean supprimerEquipe(Long idEquipe) {

        EntityManager em = fabrique.createEntityManager();

        try {
            em.getTransaction().begin();

            Equipe equipe = em.find(Equipe.class, idEquipe);

            if (equipe == null) {
                em.getTransaction().rollback();
                System.out.println("Erreur : équipe introuvable.");
                return false;
            }

            Long effectif = em.createQuery(
                            "SELECT COUNT(c) FROM Collaborateur c "
                                    + "WHERE c.equipe.id = :idEquipe", Long.class)
                    .setParameter("idEquipe", idEquipe)
                    .getSingleResult();

            if (effectif > 0) {
                em.getTransaction().rollback();
                System.out.println("Suppression refusée : l'équipe n'est pas vide");
                return false;
            }

            em.remove(equipe);

            em.getTransaction().commit();

            System.out.println("Équipe supprimée avec succès.");
            return true;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            System.out.println("Erreur lors de la suppression de l'équipe.");
            return false;

        } finally {
            em.close();
        }
    }

// Afficher les membres d'une equipe
    public void afficherMembresEquipe(Long idEquipe) {

        EntityManager em = fabrique.createEntityManager();

        try {
            Equipe equipe = em.find(Equipe.class, idEquipe);

            if (equipe == null) {
                System.out.println("Erreur : équipe introuvable.");
                return;
            }

            List<Collaborateur> membres = em.createQuery(
                            "SELECT c FROM Collaborateur c " +
                                    "WHERE c.equipe.id = :idEquipe ORDER BY c.prenom",
                            Collaborateur.class).setParameter("idEquipe", idEquipe).getResultList();

            System.out.println("\nÉquipe : " + equipe.getNom());

            if (membres.isEmpty()) {
                System.out.println("Aucun collaborateur n'est  dans cette équipe.");
            } else {
                for (Collaborateur c : membres) {
                    System.out.println("Identifiant : " + c.getIdentifiant() + " | Prénom : " + c.getPrenom());
                }
            }

        } finally {
            em.close();
        }
    }

// afficher les sans equipes
    public void afficherCollaborateursSansEquipe() {

        EntityManager em = fabrique.createEntityManager();

        try {
            List<Collaborateur> collaborateurs = em.createQuery(
                    "SELECT c FROM Collaborateur c " +
                            "WHERE c.equipe IS NULL ORDER BY c.nom, c.prenom", Collaborateur.class).getResultList();

            if (collaborateurs.isEmpty()) {
                System.out.println("Tous les collaborateurs ont une équipe.");
            } else {
                System.out.println("\n=== Collaborateurs sans équipe ===");

                for (Collaborateur c : collaborateurs) {
                    System.out.println("- " + c.getIdentifiant() + " : " + c.getPrenom() + " " + c.getNom()
                    );
                }
            }

        } finally {
            em.close();
        }
    }



}
