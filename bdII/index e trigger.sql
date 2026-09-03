USE `materialacademico` ;

CREATE UNIQUE INDEX idx_semestre_etapa
ON Semestre(Etapa);

DELIMITER $
DROP TRIGGER IF EXISTS trg_validar_semestre $
CREATE TRIGGER trg_validar_semestre
BEFORE INSERT ON Semestre
FOR EACH ROW
BEGIN
    IF NEW.dataFim <= NEW.dataInicio THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'A data de fim deve ser posterior à data de início.';
    END IF;
END$
DELIMITER ;

DELIMITER $
DROP TRIGGER IF EXISTS trg_validar_semestreUp $
CREATE TRIGGER trg_validar_semestreUp
BEFORE UPDATE ON Semestre
FOR EACH ROW
BEGIN
    IF NEW.dataFim <= NEW.dataInicio THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'A data de fim deve ser posterior à data de início.';
    END IF;
END$
DELIMITER ;