package com.sira.model;

public class HistorialMedico {
    private String fecha;
    private String tipo;

    public HistorialMedico() {
    }

    public HistorialMedico(String fecha, String tipo) {
        this.fecha = fecha;
        this.tipo = tipo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
