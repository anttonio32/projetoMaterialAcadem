package model;

import java.sql.Date;

public class Semestre {

    private int idSem;
    private Date dataInicio;
    private Date dataFim;
    private String etapa;

    public Semestre() {
    }

    public Semestre(int idSem, Date dataInicio, Date dataFim, String etapa) {
        this.idSem = idSem;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.etapa = etapa;
    }

    public Semestre(Date dataInicio, Date dataFim, String etapa) {
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.etapa = etapa;
    }

    public int getIdSem() {
        return idSem;
    }

    public void setIdSem(int idSem) {
        this.idSem = idSem;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public String getEtapa() {
        return etapa;
    }

    public void setEtapa(String etapa) {
        this.etapa = etapa;
    }

}