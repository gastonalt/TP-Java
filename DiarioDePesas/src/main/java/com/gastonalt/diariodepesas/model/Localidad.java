package com.gastonalt.diariodepesas.model;

public class Localidad {

	private int cod_postal;
	private String nombre;
	
	public Localidad(int cod_postal, String nombre) {
		super();
		this.cod_postal = cod_postal;
		this.nombre = nombre;
	}
	
	public int getCod_postal() {
		return cod_postal;
	}
	public void setCod_postal(int codPostal) {
		this.cod_postal = codPostal;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
