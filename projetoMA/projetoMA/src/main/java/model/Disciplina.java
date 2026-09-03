package model;

public class Disciplina {
    private int idDisc;
    private String nome;
    private int idSem;

    public Disciplina(int idDisc, String nome, int idSem) {
        this.idDisc = idDisc;
        this.nome = nome;
        this.idSem = idSem;
    }

    public Disciplina() {
    }
    
    
    public int getIdDisc() {
        return idDisc;
    }

    public void setIdDisc(int idDisc) {
        this.idDisc = idDisc;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdSem() {
        return idSem;
    }

    public void setIdSem(int idSem) {
        this.idSem = idSem;
    }

}
