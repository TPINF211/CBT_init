

USE cbt_db;

-- procédure pour récupérer toutes les propostions
DELIMITER //
CREATE PROCEDURE IF NOT EXISTS GetAllPropositions()
BEGIN
    SELECT * FROM propositions ORDER BY date DESC;
END //
DELIMITER;    

-- procédure pour récupérer une propostions à partir de son id
DELIMITER //
CREATE PROCEDURE IF NOT EXISTS GetpropositionsByID(IN id INT )
BEGIN
    SELECT * FROM propositions WHERE id_proposition = id;
END //
DELIMITER;


-- procédure pour ajouter une nouvelle proposition
DELIMITER //
CREATE PROCEDURE IF NOT EXISTS ajouterPropositions(
    IN p_nom VARCHAR(255),
    IN p_id_membre INT,
    IN p_description VARCHAR(255),
)
BEGIN 
    INSERT INTO propositions (nom,id_membre,description,date)
    VALUES(p_nom,p_id_membre,p_description,NOW());

    SELECT LAST_INSERT_ID() AS new_pro_id;

END //
DELIMITER;

-- procédure pour suprimées une proposition
DELIMITER //
CREATE PROCEDURE IF NOT EXISTS DeleteProp(IN p_id INT)
BEGIN
    DELETE FROM propositions WHERE id_proposition = p_id;
    SELECT ROW_COUNT() AS affected_rows;
END //
DELIMITER;


--procédure pour changer le status d'une propostions 
CREATE PROCEDURE IF NOT EXISTS UpdateProSta(
    IN p_id INT,
    IN New_status ENUM('Approuvée', 'Rejetée')
)
BEGIN
    UPDATE propositions
    SET status = New_status
    WHERE id_proposition = p_id;
END //
DELIMITER;    




DELIMITER //
CREATE PROCEDURE IF NOT EXISTS GetAllVotes()
BEGIN
    SELECT * FROM votes ORDER BY date DESC;
END //
DELIMITER; 

DELIMITER //
CREATE PROCEDURE IF NOT EXISTS GetVoteByID(IN id INT )
BEGIN
    SELECT * FROM votes WHERE id_vote = id;
END //
DELIMITER;



-- l'on vote une proposition que l'on a approuvée 
DELIMITER //
CREATE PROCEDURE IF NOT EXISTS CreerVotes(
    IN p_nom VARCHAR(255),
    IN p_id_membre INT,
    IN p_description VARCHAR(255),
)
BEGIN 
    INSERT INTO propositions (nom,id_membre,description,date)
    VALUES(p_nom,p_id_membre,p_description,NOW());

    SELECT LAST_INSERT_ID() AS new_pro_id;

END //
DELIMITER;


--procédure pour changer le status d'une propostions 
CREATE PROCEDURE IF NOT EXISTS UpdateProSta(
    IN v_id INT,
    IN New_choix ENUM('Oui', 'Non')
)
BEGIN
    UPDATE votes
    SET status = New_choix
    WHERE id_vote = v_id;
END //
DELIMITER;