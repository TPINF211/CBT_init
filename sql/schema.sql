CREATE DATABASE IF NOT EXISTS cbt_db;
USE cbt_db;


--table membres
CREATE TABLE IF NOT EXISTS membres (
    id_membre INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    mot_de_passe VARCHAR(255) NOT NULL,
    role ENUM('Membre', 'President', 'Gestionnaire', 'Censeur', 'Secretaire') DEFAULT 'Membre',
    date_inscription DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--table groupes
CREATE TABLE IF NOT EXISTS groupes (
    id_groupe INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    description TEXT,
    date_creation DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--tables projets
CREATE TABLE IF NOT EXISTS projets (
    id_projet INT AUTO_INCREMENT PRIMARY KEY,
    id_groupe INT NOT NULL,
    nom VARCHAR(255) NOT NULL,
    description TEXT,
    montant DECIMAL(10,2),
    status ENUM('Approuvee', 'Rejetee', 'En_Examen') DEFAULT 'En_Examen',
    date_creation DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_groupe) REFERENCES groupes(id_groupe) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- tables propositions
CREATE TABLE IF NOT EXISTS propositions (
    id_proposition INT AUTO_INCREMENT PRIMARY KEY,
    id_membre INT NOT NULL,
    nom VARCHAR(255) NOT NULL,
    description TEXT,
    date DATE,
    status ENUM('Approuvee', 'Rejetee', 'En_Examen') DEFAULT 'En_Examen',
    FOREIGN KEY (id_membre) REFERENCES membres(id_membre) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- tables votes

CREATE TABLE IF NOT EXISTS votes (
    id_vote INT AUTO_INCREMENT PRIMARY KEY,
    id_proposition INT NOT NULL,
    id_membre INT NOT NULL,
    choix ENUM('Oui', 'Non') DEFAULT 'Non',
    date DATE DEFAULT (CURRENT_DATE),
    FOREIGN KEY (id_proposition) REFERENCES propositions(id_proposition) ON DELETE CASCADE,
    FOREIGN KEY (id_membre) REFERENCES membres(id_membre) ON DELETE CASCADE,
    UNIQUE KEY unique_vote (id_proposition, id_membre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_propositions_membre ON propositions(id_membre);
CREATE INDEX idx_votes_proposition ON votes(id_proposition);
CREATE INDEX idx_votes_membre ON votes(id_membre);
