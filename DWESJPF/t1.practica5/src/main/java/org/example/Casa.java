package org.example;

public class Casa {
    private int id_casa;
    private String nombre_casa;
    private String fundador;
    private String jefe_casa;
    private String fantasma;

    public int getId_casa() {
        return id_casa;
    }

    public void setId_casa(int id_casa) {
        this.id_casa = id_casa;
    }

    public String getNombre_casa() {
        return nombre_casa;
    }

    public void setNombre_casa(String nombre_casa) {
        this.nombre_casa = nombre_casa;
    }

    public String getFundador() {
        return fundador;
    }

    public void setFundador(String fundador) {
        this.fundador = fundador;
    }

    public String getJefe_casa() {
        return jefe_casa;
    }

    public void setJefe_casa(String jefe_casa) {
        this.jefe_casa = jefe_casa;
    }

    public String getFantasma() {
        return fantasma;
    }

    public void setFantasma(String fantasma) {
        this.fantasma = fantasma;
    }

    @Override
    public String toString() {
        return "Casa{" +
                "id_casa=" + id_casa +
                ", nombre_casa='" + nombre_casa + '\'' +
                ", fundador='" + fundador + '\'' +
                ", jefe_casa='" + jefe_casa + '\'' +
                ", fantasma='" + fantasma + '\'' +
                '}';
    }
}
