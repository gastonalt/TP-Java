package com.gastonalt.diariodepesas.model;

import java.util.Date;

public class Usuario {
	
	private int id_usuario;
	private String username;
	private String password;
	private String nombre;
	private String apellido;
	private Date fechaNacimiento;
	private String direccion;
	private String email;
	private Localidad localidad;
	private boolean isAdmin;
	
	public Usuario(int id_usuario, String username, String password, String nombre, String apellido,
			Date fechaNacimiento, String direccion, String email, Localidad localidad) {
		super();
		this.id_usuario = id_usuario;
		this.username = username;
		this.password = password;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
		this.direccion = direccion;
		this.email = email;
		this.localidad = localidad;
	}
	
	public Usuario(int id_usuario, String username, String nombre, String apellido,
			Date fechaNacimiento, String direccion, String email, Localidad localidad) {
		super();
		this.id_usuario = id_usuario;
		this.username = username;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
		this.direccion = direccion;
		this.email = email;
		this.localidad = localidad;
	}
	
	public Usuario(int id_usuario, String username, String nombre, String apellido,
			Date fechaNacimiento, String direccion, String email, boolean isAdmin, Localidad localidad) {
		super();
		this.id_usuario = id_usuario;
		this.username = username;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
		this.direccion = direccion;
		this.email = email;
		this.isAdmin = isAdmin;
		this.localidad = localidad;
	}
	
	public Usuario(String username, String password, String nombre, String apellido,
			Date fechaNacimiento, String direccion, String email, Localidad localidad) {
		super();
		this.username = username;
		this.password = password;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
		this.direccion = direccion;
		this.email = email;
		this.localidad = localidad;
	}
	
	
	public int getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Localidad getLocalidad() {
		return localidad;
	}
	public void setLocalidad(Localidad localidad) {
		this.localidad = localidad;
	}
	// MÉTODO PARA EL ADMIN (ES QUIEN AGREGARÁ LOCALIDADES POR EJEMPLO)
    public boolean isAdmin() {
        return isAdmin;
    }
    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

}
