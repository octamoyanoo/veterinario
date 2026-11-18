package com.sira.model;

/**
 * Clase Entidad que representa a un Animal / Paciente de la Veterinaria.
 */
public class Animal {
    private String codigo;
    private String nombre;
    private String especie;
    private String estado;
    private String sexo;
    private String fechaIngreso;

    public Animal() {
    }

    public Animal(String nombre, String especie, String estado) {
        this.codigo = "";
        this.nombre = nombre;
        this.especie = especie;
        this.estado = estado;
        this.sexo = "hembra";
        this.fechaIngreso = "12/08/2026";
    }

    public Animal(String nombre, String especie, String estado, String sexo, String fechaIngreso) {
        this.codigo = "";
        this.nombre = nombre;
        this.especie = especie;
        this.estado = estado;
        this.sexo = sexo;
        this.fechaIngreso = fechaIngreso;
    }

    public Animal(String codigo, String nombre, String especie, String estado, String sexo, String fechaIngreso) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.especie = especie;
        this.estado = estado;
        this.sexo = sexo;
        this.fechaIngreso = fechaIngreso;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", especie='" + especie + '\'' +
                ", estado='" + estado + '\'' +
                ", sexo='" + sexo + '\'' +
                ", fechaIngreso='" + fechaIngreso + '\'' +
                '}';
    }
}
