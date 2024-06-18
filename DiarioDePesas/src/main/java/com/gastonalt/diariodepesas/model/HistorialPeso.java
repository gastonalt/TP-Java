package com.gastonalt.diariodepesas.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class HistorialPeso {
	
	int id_usuario;
	LocalDateTime fecha_peso;
	BigDecimal peso;
	
	public int getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}
	public LocalDateTime getFecha_peso() {
		return fecha_peso;
	}
	public void setFecha_peso(LocalDateTime fecha_peso) {
		this.fecha_peso = fecha_peso;
	}
	public BigDecimal getPeso() {
		return peso;
	}
	public void setPeso(BigDecimal peso) {
		this.peso = peso;
	}
	
	public HistorialPeso(int id_usuario, LocalDateTime fecha_peso, BigDecimal peso) {
		super();
		this.id_usuario = id_usuario;
		this.fecha_peso = fecha_peso;
		this.peso = peso;
	}
	
}
