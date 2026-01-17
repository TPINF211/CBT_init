USE cbt_db;

-- 1. Procédure pour récupérer toutes les propositions
DELIMITER //
CREATE PROCEDURE IF NOT EXISTS GetAllPropositions()
BEGIN
    SELECT p.*, m.nom as nom_membre
    FROM propositions p
    JOIN membres m ON p.id_membre = m.id_membre
    ORDER BY p.date DESC;
END //
DELIMITER ;

-- 2. Procédure pour récupérer une proposition par ID
DELIMITER //
CREATE PROCEDURE IF NOT EXISTS GetPropositionByID(IN p_id INT)
BEGIN
    SELECT p.*, m.nom as nom_membre, m.email
    FROM propositions p
    JOIN membres m ON p.id_membre = m.id_membre
    WHERE p.id_proposition = p_id;
END //
DELIMITER ;

-- 3. Procédure pour ajouter une nouvelle proposition
DELIMITER //
CREATE PROCEDURE IF NOT EXISTS AjouterProposition(
    IN p_nom VARCHAR(255),
    IN p_id_membre INT,
    IN p_description TEXT
)
BEGIN
    -- Vérifier que le membre existe
    DECLARE membre_exists INT;
    SELECT COUNT(*) INTO membre_exists FROM membres WHERE id_membre = p_id_membre;

    IF membre_exists > 0 THEN
        INSERT INTO propositions (nom, id_membre, description, date)
        VALUES (p_nom, p_id_membre, p_description, CURDATE());

        SELECT LAST_INSERT_ID() AS new_pro_id, 'SUCCESS' AS status;
    ELSE
        SELECT -1 AS new_pro_id, 'ERROR: Member not found' AS status;
    END IF;
END //
DELIMITER ;

-- 4. Procédure pour supprimer une proposition
DELIMITER //
CREATE PROCEDURE IF NOT EXISTS DeleteProposition(IN p_id INT)
BEGIN
    DELETE FROM propositions WHERE id_proposition = p_id;
    SELECT ROW_COUNT() AS affected_rows;
END //
DELIMITER ;

-- 5. Procédure pour changer le statut d'une proposition
DELIMITER //
CREATE PROCEDURE IF NOT EXISTS UpdatePropositionStatus(
    IN p_id INT,
    IN New_status ENUM('Approuvee', 'Rejetee', 'En_Examen')
)
BEGIN
    UPDATE propositions
    SET status = New_status
    WHERE id_proposition = p_id;

    SELECT ROW_COUNT() AS rows_updated;
END //
DELIMITER ;

-- 6. Procédure pour récupérer tous les votes
DELIMITER //
CREATE PROCEDURE IF NOT EXISTS GetAllVotes()
BEGIN
    SELECT v.*, p.nom as proposition_nom, m.nom as membre_nom
    FROM votes v
    JOIN propositions p ON v.id_proposition = p.id_proposition
    JOIN membres m ON v.id_membre = m.id_membre
    ORDER BY v.date DESC;
END //
DELIMITER ;

-- 7. Procédure pour récupérer un vote par ID
DELIMITER //
CREATE PROCEDURE IF NOT EXISTS GetVoteByID(IN v_id INT)
BEGIN
    SELECT v.*, p.nom as proposition_nom, m.nom as membre_nom
    FROM votes v
    JOIN propositions p ON v.id_proposition = p.id_proposition
    JOIN membres m ON v.id_membre = m.id_membre
    WHERE v.id_vote = v_id;
END //
DELIMITER ;

-- 8. Procédure pour créer un vote
DELIMITER //
CREATE PROCEDURE IF NOT EXISTS CreerVote(
    IN p_id_proposition INT,
    IN p_id_membre INT,
    IN p_choix ENUM('Oui', 'Non')
)
BEGIN
    -- Vérifier que la proposition existe
    DECLARE prop_exists INT;
    DECLARE membre_exists INT;

    SELECT COUNT(*) INTO prop_exists FROM propositions WHERE id_proposition = p_id_proposition;
    SELECT COUNT(*) INTO membre_exists FROM membres WHERE id_membre = p_id_membre;

    IF prop_exists > 0 AND membre_exists > 0 THEN
        -- Vérifier si le membre n'a pas déjà voté pour cette proposition
        IF NOT EXISTS (SELECT 1 FROM votes WHERE id_proposition = p_id_proposition AND id_membre = p_id_membre) THEN
            INSERT INTO votes (id_proposition, id_membre, choix, date)
            VALUES (p_id_proposition, p_id_membre, p_choix, CURDATE());

            SELECT LAST_INSERT_ID() AS new_vote_id, 'SUCCESS' AS status;
        ELSE
            SELECT -1 AS new_vote_id, 'ERROR: Member already voted for this proposition' AS status;
        END IF;
    ELSE
        SELECT -1 AS new_vote_id, 'ERROR: Proposition or Member not found' AS status;
    END IF;
END //
DELIMITER ;

-- 9. Procédure pour mettre à jour un vote
DELIMITER //
CREATE PROCEDURE IF NOT EXISTS UpdateVote(
    IN v_id INT,
    IN p_choix ENUM('Oui', 'Non')
)
BEGIN
    UPDATE votes
    SET choix = p_choix
    WHERE id_vote = v_id;

    SELECT ROW_COUNT() AS rows_updated;
END //
DELIMITER ;

-- 10. Procédure pour obtenir les résultats d'une proposition
DELIMITER //
CREATE PROCEDURE IF NOT EXISTS GetVoteResults(IN p_id_proposition INT)
BEGIN
    SELECT
        COUNT(CASE WHEN choix = 'Oui' THEN 1 END) AS votes_oui,
        COUNT(CASE WHEN choix = 'Non' THEN 1 END) AS votes_non,
        COUNT(*) AS total_votes,
        ROUND((COUNT(CASE WHEN choix = 'Oui' THEN 1 END) * 100.0 / COUNT(*)), 2) AS pourcentage_oui
    FROM votes
    WHERE id_proposition = p_id_proposition;
END //
DELIMITER ;

--11. procédure pour obtenir les stats d'un membre du groupe

DELIMITER //
CREATE PROCEDURE IF NOT EXISTS GetStatMembre(IN p_id_membre INT)
BEGIN
    SELECT
        COUNT(DISTINCT c.id_cotisation) AS total_contisations,
        COUNT(DISTINCT t.id_tour) AS total_bouffer,
        COUNT(DISTINCT p.id_penalite) AS nombre_penalite,
        COALESCE(SUM(p.montant), 0) AS total_penalite,
        COUNT(DISTINCT prop.id_proposition) AS nombre_prop
    FROM membres m
    LEFT JOIN cotisations c ON c.id_membre = m.id_membre
    LEFT JOIN tours t ON t.id_beneficiaire = m.id_membre
    LEFT JOIN penalites p ON p.id_membre = m.id_membre
    LEFT JOIN propositions prop ON prop.id_membre = m.id_membre
    WHERE m.id_membre = p_id_membre;
END //
DELIMITER;

--12. procédure pour obtenir les stats d'un membre du groupe
DELIMITER //
CREATE PROCEDURE IF NOT EXISTS TontineStat(IN p_id_membre INT)
BEGIN
    SELECT
        g.id_groupe,
        g.nom as nom_groupe,
        COUNT(DISTINCT m.id_membre) AS total_membres,
        COUNT(DISTINCT t.id_tour) AS tours_realises,
        COALESCE(SUM(c.montant), 0) AS total_cotisations,
        COALESCE(SUM(cr.montant), 0) AS total_credits_en_cours,
        COALESCE(SUM(p.montant), 0) AS total_penalites
    FROM membres m
    JOIN groupes g ON g.id_groupe = m.id_groupe
    LEFT JOIN tours t ON g.id_groupe = t.id_groupe
    LEFT JOIN cotisations c ON m.id_membre = c.id_membre
    LEFT JOIN credits cr ON m.id_membre = cr.id_membre AND cr.status = 'en_cours'
    LEFT JOIN penalites p ON m.id_membre = p.id_membre
    WHERE m.id_membre = p_id_membre
    GROUP BY g.id_groupe, g.nom;
END //
DELIMITER;

-- 12. Procédure pour obtenir les membres en retart de paiement
DELIMITER //
CREATE PROCEDURE obtenir_membres_en_retard(IN groupe_id INT)
BEGIN
    SELECT
        m.id_membre,
        m.nom,
        m.email,
        COUNT(c.id_cotisation) as cotisations_manquantes,
        DATEDIFF(CURDATE(), MAX(s.date)) as jours_depuis_derniere_seance
    FROM membres m
    JOIN presence p ON m.id_membre = p.id_membre
    JOIN seances s ON p.id_seance = s.id_seance
    JOIN cotisations c ON m.id_membre = c.id_membre AND c.id_seance = s.id_seance
    WHERE m.id_groupe = groupe_id
    AND s.date < CURDATE()
    AND c.id_cotisation IS NULL
    GROUP BY m.id_membre, m.nom, m.email
    HAVING COUNT(c.id_cotisation) > 0;
END //
DELIMITER ;

-- 13 procédure pour obtenir le solde total du groupe
DELIMITER //
CREATE PROCEDURE calculer_solde_groupe(IN groupe_id INT)
BEGIN
    SELECT
        g.nom,
        COALESCE(SUM(c.montant), 0) as total_cotisations,
        COALESCE(SUM(cr.montant), 0) as total_credits,
        COALESCE(SUM(t.montant), 0) as total_tours,
        COALESCE(SUM(p.montant), 0) as total_penalites,
        (COALESCE(SUM(c.montant), 0) + COALESCE(SUM(p.montant), 0) -
         COALESCE(SUM(t.montant), 0) - COALESCE(SUM(cr.montant), 0)) as solde
    FROM groupes g
    JOIN membres m ON g.id_groupe = m.id_groupe
    JOIN cotisations c ON m.id_membre = c.id_membre
    JOIN credits cr ON m.id_membre = cr.id_membre AND cr.status = 'en_cours'
    JOIN tours t ON g.id_groupe = t.id_groupe
    JOIN penalites p ON m.id_membre = p.id_membre
    WHERE g.id_groupe = groupe_id
END //
DELIMITER ;

-- 14. Procédure pour récupérer toutes les credits
DELIMITER //
CREATE PROCEDURE IF NOT EXISTS GetAllCredits()
BEGIN
    SELECT c.*, m.nom as nom_membre
    FROM credits c
    JOIN membres m ON c.id_membre = m.id_membre
    ORDER BY c.date DESC;
END //
DELIMITER ;

-- 15. Procédure pour récupérer une credit par ID
DELIMITER //
CREATE PROCEDURE IF NOT EXISTS GetCreditByID(IN c_id INT)
BEGIN
    SELECT c.*, m.nom as nom_membre, m.email
    FROM credits c
    JOIN membres m ON c.id_membre = m.id_membre
    WHERE c.id_credit = c_id;
END //
DELIMITER ;

-- 16. Procédure pour ajouter un nouveau credit
DELIMITER //
CREATE PROCEDURE IF NOT EXISTS AjouterCredits(
    IN c_id_membre INT,
    IN c_montant DECIMAL(10,2),
    IN c_interet DECIMAL(10,2)
)
BEGIN
    -- Vérifier que le membre existe
    DECLARE membre_exists INT;
    SELECT COUNT(*) INTO membre_exists FROM membres WHERE id_membre = c_id_membre;

    IF membre_exists > 0 THEN
        INSERT INTO credits (id_membre, montant, interet, status, date_demande)
        VALUES (c_id_membre, c_montant, c_interet, 'en_cours', CURDATE());

        SELECT LAST_INSERT_ID() AS new_credit_id, 'SUCCESS' AS status;
    ELSE
        SELECT -1 AS new_credit_id, 'ERROR: Member not found' AS status;
    END IF;
END //
DELIMITER ;

-- 17. Procédure pour supprimer un Credits
DELIMITER //
CREATE PROCEDURE IF NOT EXISTS DeleteCredits(IN c_id INT)
BEGIN
    DELETE FROM credits WHERE id_credit = c_id;
    SELECT ROW_COUNT() AS affected_rows;
END //
DELIMITER ;

-- 18. Procédure pour changer le statut d'un credits
DELIMITER //
CREATE PROCEDURE IF NOT EXISTS UpdateCreditStatus(
    IN c_id INT,
    IN New_status ENUM('en_cours', 'rembourse')
)
BEGIN
    UPDATE credits
    SET status = New_status,
        date_remboursement = CASE WHEN New_status = 'rembourse' THEN CURDATE() ELSE date_remboursement END
    WHERE id_credit = c_id;

    SELECT ROW_COUNT() AS rows_updated;
END //
DELIMITER ;
