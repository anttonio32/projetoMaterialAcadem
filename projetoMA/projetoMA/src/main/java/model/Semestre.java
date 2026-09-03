package model;
import java.sql.Date;

public class Semestre {
    private int idSem;
    private String etapa;

    public Semestre() {
    }

    public Semestre(int idSem, String etapa) {
        this.idSem = idSem;
        this.etapa = etapa;
    }

    public Semestre(String etapa) {
        this.etapa = etapa;
    }

    public int getIdSem() {
        return idSem;
    }

    public void setIdSem(int idSem) {
        this.idSem = idSem;
    }

    public String getEtapa() {
        return etapa;
    }

    public void setEtapa(String etapa) {
        this.etapa = etapa;
    }
    
    
}
