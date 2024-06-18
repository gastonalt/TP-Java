package com.gastonalt.diariodepesas.model;

import java.sql.Timestamp;

public class Resultado {
    private int idUsuario;
    private Timestamp fechaHoraCargaResultado;
    private int idEjercicio;
    private Integer tiempoMinutos;
    private Integer cantSeries;
    private Integer cantRepeticiones;
    private Double pesoSoportado;

    public Resultado(int idUsuario, Timestamp fechaHoraCargaResultado, int idEjercicio,
                     Integer tiempoMinutos) {
        this.idUsuario = idUsuario;
        this.fechaHoraCargaResultado = fechaHoraCargaResultado;
        this.idEjercicio = idEjercicio;
        this.tiempoMinutos = tiempoMinutos;
		this.cantSeries = null;
		this.cantRepeticiones = null;
		this.pesoSoportado = null;
    }
    
    public Resultado(int idUsuario, Timestamp fechaHoraCargaResultado, int idEjercicio,
            Integer cantSeries, Integer cantRepeticiones, Double pesoSoportado) {
		this.idUsuario = idUsuario;
		this.fechaHoraCargaResultado = fechaHoraCargaResultado;
		this.idEjercicio = idEjercicio;
        this.tiempoMinutos = null;
		this.cantSeries = cantSeries;
		this.cantRepeticiones = cantRepeticiones;
		this.pesoSoportado = pesoSoportado;
	}
    
    public Resultado(int idUsuario, Timestamp fechaHoraCargaResultado, int idEjercicio,
    		Integer tiempoMinutos, Integer cantSeries, Integer cantRepeticiones, Double pesoSoportado) {
		this.idUsuario = idUsuario;
		this.fechaHoraCargaResultado = fechaHoraCargaResultado;
		this.idEjercicio = idEjercicio;
        this.tiempoMinutos = tiempoMinutos;
		this.cantSeries = cantSeries;
		this.cantRepeticiones = cantRepeticiones;
		this.pesoSoportado = pesoSoportado;
	}

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Timestamp getFechaHoraCargaResultado() {
        return fechaHoraCargaResultado;
    }

    public void setFechaHoraCargaResultado(Timestamp fechaHoraCargaResultado) {
        this.fechaHoraCargaResultado = fechaHoraCargaResultado;
    }

    public int getIdEjercicio() {
        return idEjercicio;
    }

    public void setIdEjercicio(int idEjercicio) {
        this.idEjercicio = idEjercicio;
    }

    public Integer getTiempoMinutos() {
        return tiempoMinutos;
    }

    public void setTiempoMinutos(Integer tiempoMinutos) {
        this.tiempoMinutos = tiempoMinutos;
    }

    public Integer getCantSeries() {
        return cantSeries;
    }

    public void setCantSeries(Integer cantSeries) {
        this.cantSeries = cantSeries;
    }

    public Integer getCantRepeticiones() {
        return cantRepeticiones;
    }

    public void setCantRepeticiones(Integer cantRepeticiones) {
        this.cantRepeticiones = cantRepeticiones;
    }

    public Double getPesoSoportado() {
        return pesoSoportado;
    }

    public void setPesoSoportado(Double pesoSoportado) {
        this.pesoSoportado = pesoSoportado;
    }
}
