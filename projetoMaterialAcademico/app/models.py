from django.db import models


class Aluno(models.Model):
    matricula = models.CharField(primary_key=True, max_length=9)
    nome = models.CharField(max_length=100)
    email = models.CharField(max_length=150)
    senha = models.CharField(max_length=8)
    telefone = models.CharField(max_length=11)

    class Meta:
        managed = False
        db_table = 'Aluno'


class Disciplina(models.Model):
    iddisc = models.AutoField(db_column='idDisc', primary_key=True)  # Field name made lowercase.
    nome = models.CharField(max_length=50)
    carga_horaria = models.IntegerField()
    idsem = models.ForeignKey('Semestre', models.DO_NOTHING, db_column='idSem')  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'Disciplina'


class MaterialAcademico(models.Model):
    idmat = models.AutoField(db_column='idMat', primary_key=True)  # Field name made lowercase.
    tipo = models.CharField(max_length=30)
    nome = models.CharField(max_length=100)

    class Meta:
        managed = False
        db_table = 'Material_academico'


class Matriculado(models.Model):
    pk = models.CompositePrimaryKey('matricula', 'idDisc')
    matricula = models.ForeignKey(Aluno, models.DO_NOTHING, db_column='matricula')
    iddisc = models.ForeignKey(Disciplina, models.DO_NOTHING, db_column='idDisc')  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'Matriculado'


class Ministra(models.Model):
    pk = models.CompositePrimaryKey('idProf', 'idDisc')
    idprof = models.ForeignKey('Professor', models.DO_NOTHING, db_column='idProf')  # Field name made lowercase.
    iddisc = models.ForeignKey(Disciplina, models.DO_NOTHING, db_column='idDisc')  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'Ministra'


class PreRequisito(models.Model):
    pk = models.CompositePrimaryKey('idDisc', 'idPreRequisito')
    iddisc = models.ForeignKey(Disciplina, models.DO_NOTHING, db_column='idDisc')  # Field name made lowercase.
    idprerequisito = models.ForeignKey(Disciplina, models.DO_NOTHING, db_column='idPreRequisito', related_name='prerequisito_idprerequisito_set')  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'Pre_requisito'


class Professor(models.Model):
    idprof = models.AutoField(db_column='idProf', primary_key=True)  # Field name made lowercase.
    nome = models.CharField(max_length=100)

    class Meta:
        managed = False
        db_table = 'Professor'


class Semestre(models.Model):
    idsem = models.AutoField(db_column='idSem', primary_key=True)  # Field name made lowercase.
    datainicio = models.DateField(db_column='dataInicio')  # Field name made lowercase.
    datafim = models.DateField(db_column='dataFim')  # Field name made lowercase.
    etapa = models.CharField(db_column='Etapa', max_length=4)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'Semestre'


class Tem(models.Model):
    pk = models.CompositePrimaryKey('idMat', 'idDisc')
    idmat = models.ForeignKey(MaterialAcademico, models.DO_NOTHING, db_column='idMat')  # Field name made lowercase.
    iddisc = models.ForeignKey(Disciplina, models.DO_NOTHING, db_column='idDisc')  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'tem'
