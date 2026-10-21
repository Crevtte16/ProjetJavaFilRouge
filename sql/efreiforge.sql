
CREATE DATABASE IF NOT EXISTS efreiforge
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE efreiforge;

-- 1. Table des adresses
CREATE TABLE IF NOT EXISTS adresse (
                                       id BIGINT NOT NULL AUTO_INCREMENT,
                                       rue VARCHAR(255),
    code_postal VARCHAR(255),
    ville VARCHAR(255),
    pays VARCHAR(255),
    PRIMARY KEY (id)
    ) ENGINE=InnoDB;

-- 2. Table des équipes
CREATE TABLE IF NOT EXISTS equipe (
                                      id BIGINT NOT NULL AUTO_INCREMENT,
                                      nom VARCHAR(255),
    PRIMARY KEY (id)
    ) ENGINE=InnoDB;

-- 3. Table des collaborateurs
-- L'héritage JPA utilise par défaut une table commune.
CREATE TABLE IF NOT EXISTS collaborateur (
                                             identifiant VARCHAR(255) NOT NULL,
    prenom VARCHAR(255),
    nom VARCHAR(255),
    salaire DOUBLE,
    DTYPE VARCHAR(31),
    langage_prefere VARCHAR(255),
    adresse_id BIGINT,
    equipe_id BIGINT NULL,
    PRIMARY KEY (identifiant),
    UNIQUE KEY uk_collaborateur_adresse (adresse_id),
    CONSTRAINT fk_collaborateur_adresse
    FOREIGN KEY (adresse_id) REFERENCES adresse(id),
    CONSTRAINT fk_collaborateur_equipe
    FOREIGN KEY (equipe_id) REFERENCES equipe(id)
    ) ENGINE=InnoDB;

-- 4. Insérer les adresses
INSERT INTO adresse (id, rue, code_postal, ville, pays)
VALUES
    (1, '12 rue des Lilas', '75000', 'Paris', 'France'),
    (2, '3 avenue de la République', '94800', 'Villejuif', 'France'),
    (3, '7 place Bellecour', '69002', 'Lyon', 'France'),
    (4, '15 rue Victor Hugo', '31000', 'Toulouse', 'France'),
    (5, '8 avenue de Bretagne', '44000', 'Nantes', 'France'),
    (6, '21 rue Nationale', '59000', 'Lille', 'France')
    ON DUPLICATE KEY UPDATE
                         rue = VALUES(rue),
                         code_postal = VALUES(code_postal),
                         ville = VALUES(ville),
                         pays = VALUES(pays);

-- 5. Insérer les équipes
INSERT INTO equipe (id, nom)
VALUES
    (1, 'Développement'),
    (2, 'Tests')
    ON DUPLICATE KEY UPDATE
                         nom = VALUES(nom);

-- 6. Insérer les collaborateurs
-- C001 et C002 : Développement
-- C003 et C004 : Tests
-- C005 et C006 : sans équipe

INSERT INTO collaborateur
(identifiant, prenom, nom, salaire, DTYPE,
 langage_prefere, adresse_id, equipe_id)
VALUES
    ('C001', 'Alice', 'Martin', 42000, 'Programmeur',
     'Java', 1, 1),

    ('C002', 'Bob', 'Dupont', 38000, 'Testeur',
     NULL, 2, 1),

    ('C003', 'Clara', 'Bernard', 45000, 'Programmeur',
     'Python', 3, 2),

    ('C004', 'David', 'Moreau', 39000, 'Testeur',
     NULL, 4, 2),

    ('C005', 'Emma', 'Simon', 47000, 'Programmeur',
     'JavaScript', 5, NULL),

    ('C006', 'Hugo', 'Petit', 40000, 'Testeur',
     NULL, 6, NULL)

    ON DUPLICATE KEY UPDATE
                         prenom = VALUES(prenom),
                         nom = VALUES(nom),
                         salaire = VALUES(salaire),
                         DTYPE = VALUES(DTYPE),
                         langage_prefere = VALUES(langage_prefere),
                         adresse_id = VALUES(adresse_id),
                         equipe_id = VALUES(equipe_id);
