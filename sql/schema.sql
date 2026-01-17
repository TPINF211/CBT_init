CREATE DATABASE IF NOT EXISTS cbt_db;
USE cbt_db;


--table membres
CREATE TABLE IF NOT EXISTS membres (
    id_membre INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    mdp_hash VARCHAR(255) NOT NULL,
    role ENUM('Membre', 'President', 'Gestionnaire', 'Censeur', 'Secretaire') DEFAULT 'Membre',
    date_inscription DATETIME DEFAULT CURRENT_TIMESTAMP
    id_groupe INT,
    FOREIGN KEY (id_groupe) REFERENCES groupes(id_groupe) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--table groupes
CREATE TABLE IF NOT EXISTS groupes (
    id_groupe INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    description TEXT,
    type_tontine ENUM('presence','optionnelle'),
    date_creation DATETIME DEFAULT CURRENT_TIMESTAMP
    id_president INT,
    FOREIGN KEY (id_president) REFERENCES membres(id_membre) ON DELETE SET NULL
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

CREATE TABLE IF NOT EXISTS seances(
    id_seance INT AUTO_INCREMENT PRIMARY KEY,
    id_groupe INT NOT NULL,
    lieu VARCHAR(255) NOT NULL,
    date DATE DEFAULT (CURRENT_DATE),
    FOREIGN KEY (id_groupe) REFERENCES groupes(id_groupe) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS cotisations(
    id_cotisation  INT AUTO_INCREMENT PRIMARY KEY,
    id_seance INT NOT NULL,
    id_membre INT NOT NULL,
    montant DECIMAL(10,2),
    type ENUM('presence','optionnelle'),
    parts DECIMAL(10,2),
    date DATE DEFAULT (CURRENT_DATE),
    FOREIGN KEY (id_membre) REFERENCES membres(id_membre) ON DELETE CASCADE,
    FOREIGN KEY (id_seance) REFERENCES seances(id_seance) ON DELETE CASCADE,
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS credits(
    id_credit  INT AUTO_INCREMENT PRIMARY KEY,
    id_membre INT NOT NULL,
    montant DECIMAL(10,2),
    status ENUM('en_cours','rembourse'),
    interet DECIMAL(10,2),
    date_demande DATE DEFAULT (CURRENT_DATE),
    date_remboursement DATE DEFAULT (CURRENT_DATE),
    FOREIGN KEY (id_membre) REFERENCES membres(id_membre) ON DELETE CASCADE,
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS tours(
    id_tour  INT AUTO_INCREMENT PRIMARY KEY,
    id_groupe INT NOT NULL,
    id_beneficiaire INT NOT NULL,
    numero_tour INT NOT NULL,
    montant DECIMAL(10,2),
    date DATE DEFAULT (CURRENT_DATE),
    FOREIGN KEY (id_beneficiaire) REFERENCES membres(id_membre) ON DELETE CASCADE,
    FOREIGN KEY (id_groupe) REFERENCES groupes(id_groupe) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT NULL penalites(
    id_penalite INT AUTO_INCREMENT PRIMARY KEY,
    id_membre INT NOT NULL,
    montant DECIMAL(10,2),
    raisont TEXT,
    date DATE DEFAULT (CURRENT_DATE),
    FOREIGN KEY (id_membre) REFERENCES membres(id_membre) ON DELETE CASCADE,
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


ALTER TABLE membres
ADD CONSTRAINT fk_membre_groupe
FOREIGN KEY (id_groupe) REFERENCES groupes(id_groupe) ON DELETE SET NULL;

CREATE INDEX idx_propositions_membre ON propositions(id_membre);
CREATE INDEX idx_votes_proposition ON votes(id_proposition);

CREATE INDEX idx_votes_membre ON votes(id_membre);
CREATE INDEX idx_membres_groupe ON membres(id_groupe);
CREATE INDEX idx_membres_role ON membres(role);
CREATE INDEX idx_membres_email ON membres(email);

CREATE INDEX idx_cotisations_membre ON cotisations(id_membre);
CREATE INDEX idx_cotisations_seance ON cotisations(id_seance);
CREATE INDEX idx_cotisations_date ON cotisations(date);

CREATE INDEX idx_credits_membre ON credits(id_membre);
CREATE INDEX idx_credits_groupe ON credits(id_groupe);
CREATE INDEX idx_credits_status ON credits(status);

CREATE INDEX idx_tours_beneficiaire ON tours(id_beneficiaire);
CREATE INDEX idx_tours_groupe ON tours(id_groupe);
CREATE INDEX idx_tours_numero ON tours(numero_tour);

CREATE INDEX idx_penalites_membre ON penalites(id_membre);
CREATE INDEX idx_penalites_groupe ON penalites(id_groupe);

CREATE INDEX idx_propositions_membre ON propositions(id_membre);
CREATE INDEX idx_propositions_groupe ON propositions(id_groupe);
CREATE INDEX idx_propositions_status ON propositions(status);

CREATE INDEX idx_votes_proposition ON votes(id_proposition);
CREATE INDEX idx_votes_membre ON votes(id_membre);

CREATE INDEX idx_presence_seance ON presence(id_seance);
CREATE INDEX idx_presence_membre ON presence(id_membre);

CREATE INDEX idx_projets_groupe ON projets(id_groupe);
CREATE INDEX idx_projets_status ON projets(status);