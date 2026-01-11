CREATE DATABASE IF NOT EXISTS cbt_db;
USE cbt_db;


--tables projets
CREATE TABLE IF NOT EXISTS projets (
    id_projet INT AUTO_INCREMENT PRIMARY KEY,
    id_groupe INT NOT NULL,
    nom VARCHAR(255) NOT NULL,
    description TEXT,
    montant DECIMAL,
    status ENUM('Approuvée', 'Rejetée', 'En_Examen') DEFAULT 'En_Examen',
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- tables propositions
CREATE TABLE IF NOT EXISTS propositions(
    id_proposition INT AUTO_INCREMENT PRIMARY KEY,
    id_membre INT NOT NULL,
    description TEXT,
    date DATE,
    status ENUM('Approuvée', 'Rejetée', 'En_Examen') DEFAULT 'En_Examen',
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- tables votes

CREATE TABLE IF NOT EXISTS votes(
    id_vote INT AUTO_INCREMENT PRIMARY KEY,
    id_proposition INT NOT NULL,
    id_membre INT NOT NULL,
    choix ENUM('Oui', 'Non', 'Null') DEFAULT 'Null',
    date DATE,
    FOREIGN KEY (id_proposition) REFERENCES propositions(id_proposition) ON DELETE CASCADE,
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
