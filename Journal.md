# JOURNAL DE PROJET — EFREIForge

**Formation :** EFREI — LSI1   
**Projet Final:** EFREIForge

MALKI Ilyes et AKUGBE Stephen



## 1. Démarche et organisation du travail

Le projet EFREIForge a été réalisé à partir du TP4 de Java. L'objectif était de faire évoluer l'application existante afin de permettre la gestion des équipes de collaborateurs, tout en conservant les fonctionnalités déjà développées et si possible de la rendre la plus optimale possible.

Nous avons réutilisé les classes du TP4, notamment `Collaborateur`, `Programmeur`, `Testeur` et `Adresse`, ainsi que la configuration Maven, les dépendances JPA/Hibernate et la connexion à la base de données MariaDB.

Le travail a été organisé progressivement, nous avons tout d'abord crée de la classe `Equipe`,  nous avons mis en place de la relation entre les collaborateurs et les équipes, développé les opérations de gestion, puis testé avec la base de données. Cette démarche a permis de limiter les modifications supplementaires et de conserver les acquis des travaux pratiques précédents.

## 2. Deux décisions techniques

### 2.1. Utilisation de JPA et Hibernate pour la persistance

Nous avons conservé JPA et Hibernate afin de gérer les objets Java et leur correspondance avec les tables de la base de données. Ce choix permet de manipuler les collaborateurs et les équipes directement depuis Java, sans écrire manuellement une requête SQL pour chaque opération courante.

La configuration existante du TP4 a été adaptée pour utiliser la base `efreiforge` sous MariaDB.

### 2.2. Relation `ManyToOne` entre `Collaborateur` et `Equipe`

Nous avons utilisé une relation `ManyToOne` dans la classe `Collaborateur`, avec une clé étrangère `equipe_id`. Plusieurs collaborateurs peuvent ainsi appartenir à une même équipe, tandis qu'un collaborateur ne peut être associé qu'à une seule équipe à la fois.

La relation reste facultative : un collaborateur peut ne faire partie d'aucune équipe. Les opérations d'affectation, de retrait et de suppression doivent vérifier les règles métier afin d'éviter les modifications incohérentes.

## 3. État du projet et tableau de recette

L'application dispose d'un menu permettant d'accéder aux fonctionnalités de gestion des équipes. Les opérations développées comprennent la création et l'affichage des équipes, l'affectation et le retrait de collaborateurs, l'affichage des membres, le renommage et la suppression d'une équipe vide, ainsi que la liste des collaborateurs sans équipe.

### Difficulté rencontrée

Lors de l'intégration de Hibernate, nous avons rencontré des difficultés liées à la récupération des collaborateurs depuis la base de données. Hibernate ne reconnaissait pas correctement les sous-classes `Testeur` et `Programmeur`, ce qui provoquait des erreurs lors de l'affichage des membres d'une équipe. Nous avons dû vérifier les annotations JPA, notamment `@Entity` et `@DiscriminatorValue`, ainsi que les constructeurs sans paramètre nécessaires à Hibernate. Cette difficulté nous a permis de mieux comprendre le fonctionnement de la persistance des objets Java et la gestion de l'héritage avec JPA.

### Tableau de recette
| N° | Scénario | Résultat attendu | Constaté |
|---:|---|---|---|
| 1 | Créer l'équipe « Alpha », puis « alpha », puis une équipe sans nom (vide ou espaces). | La première est créée ; les deux autres sont refusées. | OK |
| 2 | Affecter à « Alpha » un collaborateur sans équipe, puis afficher « Alpha ». | Le collaborateur apparaît dans l'équipe. | OK |
| 3 | Affecter ce même collaborateur à une autre équipe, puis de nouveau à « Alpha ». | Deux refus ; il reste dans « Alpha ». | OK |
| 4 | Retirer d'« Alpha » un collaborateur qui n'en fait pas partie (d'abord un sans équipe, puis un membre d'une autre équipe). | Deux refus, aucune donnée modifiée. | OK |
| 5 | Afficher les collaborateurs sans équipe. | Le collaborateur affecté n'y figure pas ; les autres y sont. | OK |
| 6 | Arrêter puis relancer l'application, consulter équipes et affectations. | Tout est retrouvé à l'identique. | OK |
| 7 | Renommer une équipe : changer seulement la casse de son nom, puis prendre le nom d'une autre équipe. Supprimer une équipe non vide, puis une équipe vide. | Casse seule : acceptée ; nom déjà pris : refusé. Suppression refusée tant que l'équipe contient des collaborateurs, acceptée ensuite. | OK |
| 8 | Utiliser une équipe ou un collaborateur inexistant ; saisir du texte quand un nombre est attendu. | Message clair, l'application ne plante pas. | OK |


## 4. README — Lancer et essayer l'application

### 4.1. Prérequis

- IntelliJ Idea ou autre IDE compatible avec les projet maven
- MariaDB, dans notre cas nous avons utilisé XAMPP ;


### 4.2. Préparation de la base de données

1. Démarrer MariaDB depuis XAMPP et lancer les modules Apache et MySQL
2. Ouvrir phpMyAdmin.
3. Importer le script SQL fourni dans le dossier `sql/`.
4. Vérifier que la base `efreiforge` et ses tables ont bien été créées.

Le script SQL fournit des données initiales nécessaires aux essais, notamment des collaborateurs, des équipes et des affectations.

### 4.3. Démarrage de l'application

Ouvrir le projet Maven dans l'IDE, puis lancer la classe principale `HelloEfrei`.



### 4.4. Manipulations pour vérifier le résultat

- Afficher les équipes existantes et leurs membres.
- Créer une nouvelle équipe.
- Affecter un collaborateur sans équipe.
- Essayer d'affecter une deuxième fois un collaborateur déjà affecté et vérifier le refus.
- Retirer un collaborateur de son équipe.
- Renommer une équipe avec un nom déjà utilisé et vérifier le refus.
- Essayer de supprimer une équipe qui contient encore des collaborateurs.
- Afficher les collaborateurs sans équipe.
- Redémarrer l'application et vérifier que les équipes et les affectations conservées en base sont toujours présentes.

## 5. Contribution de chacun

Ilyes Malki : - Modifictaion lié au Menu d'HelloEfrei et ajout du menu gestion d'équipe.
                - Fichier sql
                - Modification des classe Collaborateur, testeur et Programmeur a la suite des nouvelle fonctionalité                
                

Stephen Akugbe : - Classe EquipeService
                    - CLasse Equipe

Nous avons effectuer l'integration du projet tout au long de sa conception grace a GitHub.



