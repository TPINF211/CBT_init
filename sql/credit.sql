

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
    IN c_id INT,
    IN c_id_membre VARCHAR(255),
    IN c_montant INT
)
BEGIN
    -- Vérifier que le credit existe
    DECLARE credits_exists INT;
    SELECT COUNT(*) INTO credits_exists FROM credits WHERE id_membre = c_id_membre;

    IF credits_exists > 0 THEN
        INSERT INTO credits (id, id_membre, montant, date_emprunt, date_remboursement )
        VALUES (c_id, c_id_membre, c_montant, CURDATE());

        SELECT LAST_INSERT_ID() AS new_pro_id, 'SUCCESS' AS status;
    ELSE
        SELECT -1 AS new_pro_id, 'ERROR: Credit does not exist' AS status;
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
    SET status = New_status
    WHERE id_credits = c_id;

    SELECT ROW_COUNT() AS rows_updated;
END //
DELIMITER ;