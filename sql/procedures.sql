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