package com.infoIV.biblioteca.repository.filter;

import java.io.Serializable;

//import com.ag.hotel.validation.SKU;

public class LectorFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long codigo;
	private String nombre;
	private String cedula;

	//@SKU
	public Long getId() {
		return codigo;
}

	public void setId(Long codigo) {
		this.codigo = codigo ;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	@Override
	public String toString() {
		return "LectorFilter [codigo=" + codigo + ", nombre=" + nombre + ", cedula=" + cedula + "]";
	}
	
	

}