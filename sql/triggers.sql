USE cbt_db;



DELIMITER //
CREATE TRIGGER trigger_verification_cotisation
BEFORE INSERT ON cotisations
FOR EACH ROW
BEGIN
    DECLARE min_cotisation DECIMAL(10,2);
    SET min_cotisation = 1000.00; -- Valeur minimale de cotisation

    IF NEW.montant < min_cotisation THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Le montant de la cotisation doit être au moins 1000';
    END IF;
END //
DELIMITER ;

-- Trigger pour vérifier qu'un membre ne bénéficie pas deux fois du même tour
DELIMITER //
CREATE TRIGGER trigger_verification_tour_unique
BEFORE INSERT ON tours
FOR EACH ROW
BEGIN
    IF EXISTS (
        SELECT 1 FROM tours
        WHERE id_groupe = NEW.id_groupe
        AND id_beneficiaire = NEW.id_beneficiaire
    ) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Ce membre a déjà bénéficié d un tour dans ce groupe';
    END IF;
END //
DELIMITER ;
