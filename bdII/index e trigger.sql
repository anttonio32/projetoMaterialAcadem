USE `materialacademico` ;

-- evita que dois alunos tenham o mesmo e-mail;
CREATE UNIQUE INDEX idx_aluno_email
ON Aluno(email);

