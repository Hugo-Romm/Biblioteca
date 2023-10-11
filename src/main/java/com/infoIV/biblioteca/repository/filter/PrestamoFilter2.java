package com.infoIV.biblioteca.repository.filter;

import java.io.Serializable;
import java.util.Date;

import com.infoIV.biblioteca.model.Lector;
import com.infoIV.biblioteca.model.Libro;
import com.infoIV.biblioteca.model.StatusPrestamo;

public class PrestamoFilter2 implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long numero;
	private Date fechaPrestamo;
	private Date fechaDevolucion;
	private Date fechaRecuperacion;
	private Lector nombreLector;
	private Libro libro;
	private Integer nroeje;
	private StatusPrestamo[] statuses;
	
	
	
	


	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public Integer getNroeje() {
		return nroeje;
	}

	public void setNroeje(Integer nroeje) {
		this.nroeje = nroeje;
	}
	public Date getFechaPrestamo() {
		return fechaPrestamo;
	}

	public void setFechaPrestamo(Date fechaPrestamo) {
		this.fechaPrestamo = fechaPrestamo;
	}

	public Date getFechaDevolucion() {
		return fechaDevolucion;
	}
	public void setFechaDevolucion(Date fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
	}

	public void setFechaRecuperacion(Date fechaRecuperacion) {
		this.fechaRecuperacion = fechaRecuperacion;
	}
	public Date getFechaRecuperacion() {
		return fechaRecuperacion;
	}



	public Lector getNombreLector() {
		return nombreLector;
	}

	public void setNombreLector(Lector nombreLector) {
		this.nombreLector = nombreLector;
	}
	
	public Libro getLibro() {
		return libro;
	}

	public void setLibro(Libro libro) {
		this.libro = libro;
	}


	public StatusPrestamo[] getStatuses() {
		return statuses;
	}

	public void setStatuses(StatusPrestamo[] statuses) {
		this.statuses = statuses;
	}

}