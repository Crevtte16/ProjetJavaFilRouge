import java.util.List;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Point d'entrée de l'application — Annuaire des collaborateurs.
 *
 * Reprend la structure du HelloEfrei du TP1 :
 *   - méthode afficherMenu() statique
 *   - méthodes utilitaires statiques (afficherListe, saisirIdentifiant...)
 *   - boucle principale avec validation hasNextInt() + switch fléché
 */
public class HelloEfrei {

    private static final Logger logger =
            LoggerFactory.getLogger(HelloEfrei.class);

    public static void main(String[] args) {

        logger.info("Demarrage.....");
        EntityManagerFactory fabrique =
                Persistence.createEntityManagerFactory("collaborateurs-pu");

        EquipeService equipeService = new EquipeService(fabrique);

        CollaborateurService collaborateurService = new CollaborateurService(fabrique);

        // --- Initialisation de l'annuaire avec les données de démonstration ---
        Annuaire annuaire = new Annuaire();
        for (Collaborateur c : DonneesDemo.creerCollaborateurs()) {
            annuaire.ajouter(c);
        }

        Scanner scanner = new Scanner(System.in);
        int choix = -1;

        while (choix != 0) {
            afficherMenu();

            if (scanner.hasNextInt()) {
                choix = scanner.nextInt();
                scanner.nextLine(); // consommer le saut de ligne résiduel

                switch (choix) {
                    case 0 -> System.out.println("À bientôt !");

                    case 1 -> afficherListe(annuaire.tous());

                    case 2 -> {
                        System.out.print("Fragment de nom : ");
                        String fragment = scanner.nextLine();
                        afficherListe(annuaire.nomContenant(fragment));
                    }

                    case 3 -> {
                        System.out.print("Salaire minimum (€) : ");
                        if (scanner.hasNextDouble()) {
                            double seuil = scanner.nextDouble();
                            scanner.nextLine();
                            afficherListe(annuaire.salaireSuperieurA(seuil));
                        } else {
                            System.out.println("Saisie invalide.");
                            scanner.nextLine();
                        }
                    }

                    case 4 -> afficherListe(annuaire.programmeurs());

                    case 5 -> afficherListe(annuaire.triesParNom());

                    case 6 -> afficherListe(annuaire.triesParSalaire());

                    case 7 -> afficherListe(annuaire.triesParNomPuisPrenom());

                    case 8 -> {
                        System.out.print("Identifiant du collaborateur : ");
                        String id = scanner.nextLine();
                        Collaborateur trouve = annuaire.trouver(id);
                        if (trouve != null) {
                            System.out.println();
                            trouve.afficherFiche();
                        } else {
                            System.out.println("Collaborateur introuvable : " + id);
                        }
                    }

                    case 9 -> {
                        System.out.print("Identifiant du collaborateur : ");
                        String id = scanner.nextLine();
                        Collaborateur trouve = annuaire.trouver(id);
                        if (trouve != null) {
                            System.out.print("Pourcentage d'augmentation : ");
                            if (scanner.hasNextDouble()) {
                                double pourcentage = scanner.nextDouble();
                                scanner.nextLine();
                                double ancienSalaire = trouve.getSalaire();
                                trouve.augmenterSalaire(pourcentage);
                                System.out.printf("Salaire : %.2f € -> %.2f €%n",
                                        ancienSalaire, trouve.getSalaire());
                            } else {
                                System.out.println("Saisie invalide.");
                                scanner.nextLine();
                            }
                        } else {
                            System.out.println("Collaborateur introuvable : " + id);
                        }
                    }
                    case 10 ->{
                        System.out.print("Identifiant : ");
                        String id = scanner.nextLine();
                        System.out.print("Prénom : ");
                        String prenom = scanner.nextLine();
                        System.out.print("Nom : ");
                        String nom = scanner.nextLine();
                        System.out.print("Salaire : ");
                        double salaire = scanner.nextDouble();
                        scanner.nextLine();

                        Adresse adresse = new Adresse("1 rue de Paris", "75000", "Paris", "France");

                        Collaborateur nouveau =
                                new Programmeur(id, prenom, nom, salaire, adresse, "Java");


                        try {
                            logger.info("Ajout du collaborateur {}", nouveau.getIdentifiant());

                            // Vérifier d'abord l'identifiant dans l'annuaire
                            if (annuaire.trouver(nouveau.getIdentifiant()) != null) {
                                System.out.println("Erreur : cet identifiant existe déjà.");
                            } else {
                                boolean ajouteEnBase = collaborateurService.ajouter(nouveau);

                                if (ajouteEnBase) {
                                    annuaire.ajouter(nouveau);
                                    System.out.println("Collaborateur ajouté en base et dans l'annuaire.");
                                } else {
                                    System.out.println("Échec de l'ajout en base de données.");
                                }
                            }

                        } catch (CollaborateurDejaExistantException e) {
                            System.out.println(
                                    "Erreur : le collaborateur " + e.identifiant()
                                            + " existe déjà."
                            );
                        }



                    }
                    case 11 -> {
                        int choixEquipe = -1;

                        while (choixEquipe != 9) {
                            System.out.println(" Gestion des équipes ");
                            System.out.println("1. Afficher les équipes");
                            System.out.println("2. Ajouter une équipe");
                            System.out.println("3. Affecter un collaborateur à une équipe");
                            System.out.println("4. Retirer un collaborateur d'une équipe");
                            System.out.println("5. Renommer une équipe");
                            System.out.println("6. Supprimer une équipe");
                            System.out.println("7. Afficher les collaborateur d'une equipe");
                            System.out.println("8. Afficher les collaborateurs sans équipe");
                            System.out.println("9. Retour au menu principal");
                            System.out.print("Votre choix : ");

                            if (scanner.hasNextInt()) {
                                choixEquipe = scanner.nextInt();
                                scanner.nextLine();

                                switch (choixEquipe) {
                                    case 1 -> {
                                        List<Equipe> equipes =
                                                equipeService.toutesLesEquipes();

                                        if (equipes.isEmpty()) {
                                            System.out.println("Aucune équipe enregistrée.");
                                        } else {
                                            for (Equipe equipe : equipes) {
                                                System.out.println(equipe);
                                            }
                                        }
                                    }
                                    case 2->{

                                        System.out.print("Nom de la nouvelle équipe : ");
                                        String nom = scanner.nextLine();
                                        equipeService.ajouterEquipe(nom);

                                    }
                                    case 3 -> {
                                        System.out.print("Identifiant du collaborateur a affecter : ");
                                        String identifiant = scanner.nextLine();

                                        System.out.print("Identifiant de l'équipe a agrandir : ");

                                        if (scanner.hasNextLong()) {
                                            Long idEquipe = scanner.nextLong();
                                            scanner.nextLine();

                                            equipeService.affecterCollaborateur(
                                                    identifiant, idEquipe
                                            );
                                        } else {
                                            System.out.println("Identifiant d'équipe invalide.");
                                            scanner.nextLine();
                                        }
                                    }
                                    case 4 -> {
                                        System.out.print("Identifiant du collaborateur : ");
                                        String identifiant = scanner.nextLine();

                                        System.out.print("Identifiant de l'équipe : ");

                                        if (scanner.hasNextLong()) {
                                            Long idEquipe = scanner.nextLong();
                                            scanner.nextLine();

                                            equipeService.retirerCollaborateur(
                                                    identifiant, idEquipe
                                            );
                                        } else {
                                            System.out.println("Identifiant d'équipe invalide.");
                                            scanner.nextLine();
                                        }
                                    }
                                    case 5 -> {
                                        System.out.print("Identifiant de l'équipe à renommer : ");

                                        if (scanner.hasNextLong()) {
                                            Long idEquipe = scanner.nextLong();
                                            scanner.nextLine();

                                            System.out.print("Nouveau nom de l'équipe : ");
                                            String nouveauNom = scanner.nextLine();

                                            equipeService.renommerEquipe(idEquipe, nouveauNom);

                                        } else {
                                            System.out.println("Identifiant invalide.");
                                            scanner.nextLine();
                                        }
                                    }
                                    case 6 -> {
                                        System.out.print("Identifiant de l'équipe à supprimer : ");

                                        if (scanner.hasNextLong()) {
                                            Long idEquipe = scanner.nextLong();
                                            scanner.nextLine();

                                            equipeService.supprimerEquipe(idEquipe);

                                        } else {
                                            System.out.println("Identifiant invalide.");
                                            scanner.nextLine();
                                        }
                                    }

                                    case 7 -> {
                                        System.out.print("Identifiant de l'équipe : ");

                                        if (scanner.hasNextLong()) {
                                            Long idEquipe = scanner.nextLong();
                                            scanner.nextLine();

                                            equipeService.afficherMembresEquipe(idEquipe);

                                        } else {
                                            System.out.println("Identifiant invalide.");
                                            scanner.nextLine();
                                        }
                                    }




                                    case 8 -> equipeService.afficherCollaborateursSansEquipe();

                                    case 9 -> System.out.println("Retour au menu principal.");



                                           // Supprimer une équipe uniquement si elle est vide.
                                    default -> System.out.println("Choix invalide.");
                                }
                            } else {
                                System.out.println("Veuillez saisir un nombre.");
                                scanner.nextLine();
                            }
                        }
                    }

                    default -> {
                        System.out.println("Option invalide. Choisissez entre 0 et 11.");
                    }
                }

            } else {
                System.out.println("Saisie invalide. Veuillez entrer un nombre.");
                scanner.nextLine();
            }
        }fabrique.close();
        scanner.close();
    }

    // --- Méthodes statiques utilitaires ---

    private static void afficherMenu() {
        System.out.println();
        System.out.println("=== Annuaire des collaborateurs (" + "EFREI" + ") ===");
        System.out.println("1. Afficher tous les collaborateurs");
        System.out.println("2. Rechercher par nom");
        System.out.println("3. Filtrer par salaire minimum");
        System.out.println("4. Afficher les programmeurs");
        System.out.println("5. Trier par nom");
        System.out.println("6. Trier par salaire");
        System.out.println("7. Trier par nom puis prénom");
        System.out.println("8. Afficher la fiche d'un collaborateur");
        System.out.println("9. Augmenter le salaire d'un collaborateur");
        System.out.println("10. Ajouter un collaborateur");
        System.out.println("11. Gestion des équipes");
        System.out.println("0. Quitter");
        System.out.print("Votre choix : ");
    }

    /**
     * Affiche une liste de collaborateurs.
     * Utilise List<? extends Collaborateur> pour accepter aussi bien
     * List<Collaborateur> que List<Programmeur> (covariance via wildcard borné).
     */
    private static void afficherListe(List<? extends Collaborateur> liste) {
        if (liste.isEmpty()) {
            System.out.println("Aucun collaborateur trouvé.");
        } else {
            System.out.println(liste.size() + " collaborateur(s) :");
            for (Collaborateur c : liste) {
                System.out.println("  " + c);
            }
        }
    }
}
