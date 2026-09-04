DROP SCHEMA IF EXISTS materialacademico;
CREATE SCHEMA IF NOT EXISTS `materialacademico`;
USE `materialacademico`;

CREATE TABLE `Semestre` (
  `idSem` INT NOT NULL AUTO_INCREMENT,
  `dataInicio` DATE NOT NULL,
  `dataFim` DATE NOT NULL,
  `Etapa` ENUM('I','II','III','IV','V','VI','VII','VIII') NOT NULL,
  PRIMARY KEY (`idSem`)
);

CREATE TABLE `Disciplina` (
  `idDisc` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(50) NOT NULL,
  `idSem` INT NOT NULL,
  PRIMARY KEY (`idDisc`),
  CONSTRAINT `fk_Disciplina_Semestre`
    FOREIGN KEY (`idSem`) REFERENCES `Semestre` (`idSem`)
);

CREATE TABLE `MaterialAcademico` (
  `idMat` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(100) NOT NULL,
  `tipo` VARCHAR(30) NOT NULL,
  `caminhoArquivo` VARCHAR(255) NOT NULL,
  `idDisc` INT NOT NULL,
  PRIMARY KEY (`idMat`),
  CONSTRAINT `fk_Material_Disciplina`
    FOREIGN KEY (`idDisc`) REFERENCES `Disciplina` (`idDisc`)
);

-- INDEX
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

-- TRIGGER
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

-- VIEW
CREATE VIEW vw_Materiais AS
SELECT m.nome AS Material, m.tipo AS Tipo, d.nome AS Disciplina, s.Etapa
FROM MaterialAcademico m
JOIN Disciplina d ON m.idDisc = d.idDisc
JOIN Semestre s ON d.idSem = s.idSem;

-- FUNCTION
DELIMITER $

CREATE FUNCTION fn_QtdMateriaisPorDisciplina(p_idDisc INT)
RETURNS INT
DETERMINISTIC
BEGIN
    DECLARE qtd INT;
    SELECT COUNT(*) INTO qtd
    FROM MaterialAcademico
    WHERE idDisc = p_idDisc;
    RETURN qtd;
END$

DELIMITER ;


-- SELECTS
select * from disciplina;
select * from semestre;
select * from materialAcademico;
select * from vw_Materiais;
select fn_QtdMateriaisPorDisciplina(1) as TotalMateriais;