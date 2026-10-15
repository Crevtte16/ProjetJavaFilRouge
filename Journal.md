# TP4 : COMPTE RENDU

Pour commencer ce TP, nous sommes partis du corrigé que vous nous avez fourni  afin de disposer d'une base saine et fonctionnelle. Cela nous a permis de nous concentrer sur les objectifs de chaque mission, tout en comprenant progressivement les modifications et les améliorations du tp.



# Partie A — Fiabiliser l’application

## 1) Commencez par casser votre application

On nous demandait de trouver au moins deux situations différentes provoquant un comportement incorrect, une donnée incohérente, une règle métier violée ou un plantage. Malgré plusieurs tests, nous n'avons pas réussi à casser l'application ni à provoquer de plantage. Cependant, nous avons identifié deux problèmes potentiels



**Problème 1 : augmentation négative**

Nous avons testé une augmentation négative, mais le programme ignorait simplement la commande. Nous aurions pu nous attendre à ce qu'il refuse explicitement cette valeur ou signale que le pourcentage fourni est incorrect.

**Problème 2 : augmentation excessive**

Nous avons entré un pourcentage d'augmentation totalement exorbitant, et le programme acceptait cette modification. Il aurait été préférable d'imposer une limite raisonnable ou de contrôler la valeur selon les règles métier.

### Réflexion

Cette mission nous a permis de distinguer une erreur technique d'une règle métier. Dans notre cas, le contrôle des augmentations de salaire relève principalement des règles métier et doit être effectué dans la classe `Collaborateur`.

## 2) Une règle métier mérite mieux qu’un println

L'objectif de cette mission était d'empêcher l'ajout de deux collaborateurs possédant le même identifiant.

Nous avons créé la classe `CollaborateurDejaExistantException`, qui hérite de `RuntimeException` et conserve l'identifiant concerné.

Nous avons ensuite modifié la méthode `ajouter()` de la classe `Annuaire` pour vérifier si l'identifiant existe déjà. Si c'est le cas, une exception précise est levée ; sinon, le collaborateur est ajouté normalement.

Enfin, nous avons adapté le menu de `HelloEfrei` en ajoutant un dixieme boutons afin que l'utilisateur puisse ajouter un collaboraateur.

### Difficulté rencontrée

Au début, nous avons eu du mal à bien utiliser le `try/catch` dans le menu `HelloEfrei`. Il fallait comprendre où placer le bloc `try` et comment intercepter correctement notre exception personnalisée pour afficher un message à l'utilisateur sans interrompre l'application.
## 3) Un projet que l’on sait reconstruire

L'objectif était de transformer notre projet en projet Maven et de vérifier qu'il pouvait toujours être compilé et exécuté depuis IntelliJ.

### Difficultés rencontrées

Le fichier `pom.xml` n'était pas reconnu par IntelliJ, car il se trouvait dans le mauvais dossier. Nous avons résolu le problème en le plaçant à la racine du projet.

Nous avons également essayé d'utiliser la commande `mvn` dans PowerShell, mais elle n'était pas reconnue. Nous avons donc utilisé la fenêtre Maven intégrée à IntelliJ pour compiler le projet.

### Réflexion

Déclarer une bibliothèque dans `pom.xml` permet de gérer automatiquement les dépendances sans copier les fichiers `.jar`. Lorsqu'un camarade récupère notre projet, Maven peut télécharger les bibliothèques nécessaires sans avoir a le faire nous meme.

## 4) « Ça ne marche pas » n’est pas un diagnostic

L'objectif de cette mission était d'apprendre à diagnostiquer les problèmes à l'aide du debugger et à ajouter des logs utiles.

Nous avons ajouté les dépendances SLF4J dans `pom.xml`, puis créé un logger pour enregistrer différentes informations techniques.

Nous avons utilisé plusieurs niveaux de logs : `INFO` pour les événements normaux, `WARN` pour les situations inhabituelles, `DEBUG` pour les détails utiles à l'analyse et `ERROR` pour les erreurs.

Nous avons également utilisé le debugger sur le calcul d'une augmentation de salaire afin d'observer les variables et de comprendre le déroulement du programme.

### Difficultés rencontrées



Les consignes de cette mission étaient très bien guidées, ce qui nous a permis de suivre les étapes progressivement. Nous n'avons pas rencontré de problème particulier lors de l'utilisation du debugger et de la mise en place des logs.

Cette partie nous a permis de faire la differnece entre les deux type de destinataire pour les messages renvoyé par la console: Ceux à l'utilisayeur et ceux aux developpeur

---

# Partie B — Alice et Alex refusent de disparaître

## 5) Alice refuse de disparaître

L'objectif de cette mission était de conserver les collaborateurs dans une base de données, même après l'arrêt de l'application.

Nous avons installé et utilisé XAMPP pour démarrer MariaDB, puis phpMyAdmin pour accéder à la base `efrei_tp4`. Ainsi Nous avons vérifié la présence de la table de démo `collaborateur_demo`.

Nous avons ensuite ajouté les dépendances nécessaires dans Maven : Hibernate, l'API Jakarta Persistence et le pilote JDBC MariaDB.

Nous avons créé le fichier `src/main/resources/META-INF/persistence.xml`, qui configure la connexion à la base et définit l'unité de persistance `collaborateurs-pu`.

Enfin, nous avons annoté nos classes avec `@Entity` et `@Id` afin qu'Hibernate puisse les représenter dans la base de données. Nous avons utilisé l'identifiant métier du collaborateur comme clé primaire.

### Difficultés rencontrées

Au début, Hibernate ne trouvait pas le fichier `persistence.xml` et ne parvenait donc pas à créer l'unité de persistance. Nous avons dû vérifier son emplacement et nous assurer qu'il se trouvait bien dans `src/main/resources/META-INF/`.

Après correction, la connexion à la base fonctionnait et Hibernate pouvait créer les tables nécessaires.

Nous avons également dû effectuer des recherches complémentaires pour comprendre le rôle des annotations JPA, notamment @Entity, @Id et @Column, ainsi que leur utilisation dans nos classes Java.

Nous avons également rencontré des difficultés avec la classe TestJPA. Lors de son exécution, la console affichait de nombreux messages en rouge provenant principalement des logs d'Hibernate, notamment lors de la connexion à la base de données et de la création des tables. Nous pensions au départ qu'il s'agissait d'erreurs dans notre programme, alors que certains messages correspondaient simplement au fonctionnement normal d'Hibernate. Nous avons donc dû apprendre à distinguer les messages d'information et les avertissements des véritables erreurs bloquantes.

## 6) Alice entre dans la base

L'objectif était d'enregistrer un collaborateur avec JPA, puis de pouvoir le retrouver grâce à son identifiant.

Nous avons créé la classe `TestPersistence` pour enregistrer un collaborateur à l'aide de `EntityManager` et de `persist()`. Nous avons vérifié que l'enregistrement apparaissait dans la base de données.

Nous avons ensuite créé `CollaborateurService`, avec une méthode `rechercher()` utilisant `find()` pour retrouver un collaborateur à partir de son identifiant.

Nous avons testé la recherche d'un collaborateur existant, C010, puis celle d'un identifiant inexistant, C999. La première renvoyait le collaborateur et la seconde renvoyait `null`.

### Difficulté rencontrée

Une erreur est apparue car la classe `Programmeur` ne possédait pas de constructeur sans argument. Nous avons ajouté un constructeur sans argument avec une visibilité `protected`, comme attendu par JPA. Nous nous sommes permis d'utiliser l'intelligence Artificielle pour ce probleme.

## 7) Alice obtient une augmentation sans écrire UPDATE

L'objectif était de modifier le salaire d'un collaborateur enregistré dans la base de données.

Nous avons récupéré le collaborateur avec `find()`, puis appelé sa méthode métier `augmenterSalaire()` dans une transaction. Après le `commit()`, nous avons constaté que le salaire avait été modifié dans la base.

Nous avons également observé qu'Hibernate générait automatiquement une requête SQL `UPDATE`, sans que nous ayons besoin de l'écrire nous-mêmes.

Cette mission nous a permis de comprendre le fonctionnement d'une entité gérée : JPA détecte les modifications apportées à l'objet et les synchronise avec la base lors de la validation de la transaction.

## 8) Retrouver autrement que par identifiant

L'objectif était de rechercher des collaborateurs selon un critère autre que leur identifiant.

Nous avons écrit une requête JPQL permettant de retrouver les collaborateurs dont le salaire dépasse un seuil fixé à 45 000 euros.

Nous avons utilisé `createQuery()`, `setParameter()` et `getResultList()` pour exécuter la requête et afficher les résultats.

Nous avons constaté que JPQL utilise les noms des entités et de leurs attributs Java, contrairement au SQL qui utilise les noms des tables et des colonnes.

Cette mission nous a également permis de comprendre l'intérêt des paramètres dans les requêtes, notamment pour éviter de concaténer directement les valeurs saisies par l'utilisateur.

## 9) Alice a toujours une adresse

L'objectif était de rendre persistante la relation entre un collaborateur et son adresse. Nous avons choisi une relation `@OneToOne` car, dans notre modèle, chaque collaborateur possède une seule adresse et chaque adresse est associée à un seul collaborateur. Une relation `@ManyToOne` aurait été plus adaptée si plusieurs collaborateurs pouvaient partager la même adresse.

Nous avons transformé `Adresse` en entité JPA avec `@Entity` et `@Id`. Comme une adresse ne possédait pas d'identifiant métier, nous avons utilisé `@GeneratedValue` pour laisser la base générer son identifiant.

Dans `Collaborateur`, nous avons ajouté une relation `@OneToOne`, accompagnée de `@JoinColumn(name = "adresse_id")` et de `CascadeType.PERSIST`.

Nous avons ensuite exécuté le programme pour enregistrer un collaborateur avec une adresse. Hibernate a créé la table `Adresse`, ajouté la clé étrangère `adresse_id` dans `Collaborateur` et enregistré les deux objets.

Cette mission nous a permis de comprendre comment représenter une relation entre deux entités Java dans une base de données relationnelle.

### Difficultés rencontrées

Nous avons eu des difficultés à rendre la classe `Adresse` persistante et à établir la relation avec `Collaborateur`. Nous avons dû effectuer des recherches pour comprendre le rôle de `@OneToOne`, `@JoinColumn` et `CascadeType.PERSIST`. L'annotation `@GeneratedValue` nous a également posé problème, car nous devions comprendre comment générer automatiquement l'identifiant, pour que MariaDB cree une valeur unique à chaque nouvelle adresse. Enfin, nous avons vérifié dans phpMyAdmin que la clé étrangère  avait bien été créée et que l'adresse était enregistrée avec le collaborateur dans une table.


---

# Partie C — Prendre du recul

## 10) Comprendre d’abord. Automatiser ensuite.

L'objectif était de comparer une entité écrite manuellement avec une entité générée automatiquement par IntelliJ à partir de la base de données.

Nous avons configuré une connexion MariaDB dans la fenêtre Database d'IntelliJ, puis généré la classe `CollaborateurDemo` à partir de la table `collaborateur_demo`, dans le package `genere`.

Cette mission nous a permis de comprendre que la génération automatique facilite le travail, mais qu'il reste nécessaire de vérifier les choix effectués par l'outil.

### Difficultés rencontrées

Nous avons eu des difficultés à configurer la connexion à MariaDB depuis IntelliJ, car nous ne trouvions pas le bon chemin dans l'interface du logiciel. Nous avons donc utilisé l'IA pour nous guider étape par étape et trouver la bonne procédure. Le problème était peut-être lié à notre version d'IntelliJ, car les menus différaient de ceux indiqués dans le sujet du TP. Grâce à cette aide, nous avons finalement réussi à configurer la connexion et à accéder à la base de données. pour le reste, nous n'avons pas eu deproblemes particulier. Tout était très bien guider.


## 11) Avant de quitter Alice et Alex

## 11) Avant de quitter Alice et Alex

À la fin de ce TP, nous avons vérifié que notre projet se compilait avec Maven, qu'une règle métier était protégée par une exception précise et que les exceptions étaient correctement gérées. Nous avons appris à utiliser le debugger et les logs pour diagnostiquer les problèmes. Nous avons également réussi à enregistrer, retrouver et modifier des collaborateurs dans la base de données, à effectuer une recherche avec JPQL et à persister la relation entre `Collaborateur` et `Adresse`. Enfin, nous avons compris les limites d'un identifiant métier comme `C001` et l'intérêt de séparer les responsabilités pour rendre l'application plus claire et plus facile à maintenir.