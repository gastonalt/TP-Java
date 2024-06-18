package com.gastonalt.diariodepesas.model;

public class Ejercicio {

    public enum TipoEjercicio {
        POR_TIEMPO,
        POR_REPETICION
    }

    private int id_ejercicio;
    private String descripcion;
    private TipoEjercicio tipoEjercicio;
    private Resultado ultimoResultado;

    public Ejercicio(int id_ejercicio, String descripcion, TipoEjercicio tipoEjercicio) {
        this.id_ejercicio = id_ejercicio;
        this.descripcion = descripcion;
        this.tipoEjercicio = tipoEjercicio;
    }
    public Ejercicio(String descripcion, TipoEjercicio tipoEjercicio) {
        this.descripcion = descripcion;
        this.tipoEjercicio = tipoEjercicio;
    }

    public int getId_ejercicio() {
        return id_ejercicio;
    }

    public void setId_ejercicio(int id_ejercicio) {
        this.id_ejercicio = id_ejercicio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoEjercicio getTipoEjercicio() {
        return tipoEjercicio;
    }

    public void setTipoEjercicio(TipoEjercicio tipoEjercicio) {
        this.tipoEjercicio = tipoEjercicio;
    }
    
    public Resultado getUltimoResultado() {
        return ultimoResultado;
    }

    public void setUltimoResultado(Resultado ultimoResultado) {
        this.ultimoResultado = ultimoResultado;
    }

}

