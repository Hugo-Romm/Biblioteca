package com.infoIV.biblioteca.repository.filter;

import java.io.Serializable;
import java.util.Date;

//import com.algaworks.pedidovenda.validation.SKU;

public class PrestamoFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long numero;
	private Integer nroeje;
	private Date fecrec;
	private Long libro_codigo;
	

	public Integer getNroeje() {
		return nroeje;
	}

	public void setNroeje(Integer nroeje) {
		this.nroeje = nroeje;
	}

	public Date getFecrec() {
		return fecrec;
	}

	public void setFecrec(Date fecrec) {
		this.fecrec = fecrec;
	}

	public Long getLibro_codigo() {
		return libro_codigo;
	}

	public void setLibro_codigo(Long libro_codigo) {
		this.libro_codigo = libro_codigo;
	}

	//@SKU
	public Long getNumero() {
		return numero;
}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	
}