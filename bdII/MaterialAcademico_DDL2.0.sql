drop schema if exists materialacademico;
CREATE SCHEMA IF NOT EXISTS `materialacademico`;
USE `materialacademico` ;

CREATE TABLE `Material_academico` (
  `idMat` INT NOT NULL AUTO_INCREMENT,
  `tipo` VARCHAR(30) NOT NULL,
  `nome` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`idMat`));

CREATE TABLE `Semestre` (
  `idSem` INT NOT NULL AUTO_INCREMENT,
  `Etapa` ENUM("I", "II", "III", "IV", "V", "VI", "VII", "VIII") NOT NULL,
  PRIMARY KEY (`idSem`));

CREATE TABLE `Disciplina` (
  `idDisc` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(50) NOT NULL,
  `idSem` INT NOT NULL,
  PRIMARY KEY (`idDisc`, `idSem`),
  CONSTRAINT `fk_Disciplina_Semestre1`
    FOREIGN KEY (`idSem`)
    REFERENCES `Semestre` (`idSem`));


CREATE TABLE `tem` (
  `idMat` INT NOT NULL,
  `idDisc` INT NOT NULL,
  PRIMARY KEY (`idMat`, `idDisc`),
  CONSTRAINT `fk_Material_academico_has_Disciplina_Material_academico1`
    FOREIGN KEY (`idMat`)
    REFERENCES `Material_academico` (`idMat`),
  CONSTRAINT `fk_Material_academico_has_Disciplina_Disciplina1`
    FOREIGN KEY (`idDisc`)
    REFERENCES `Disciplina` (`idDisc`));

