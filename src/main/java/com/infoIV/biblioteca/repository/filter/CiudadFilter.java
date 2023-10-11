package com.infoIV.biblioteca.repository.filter;

import java.io.Serializable;

public class CiudadFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long codigo;
	private String descri;
	
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getDescri() {
		return descri;
	}
	public void setDescri(String descri) {
		this.descri = descri;
	}

	@Override
	public String toString() {
		return "CiudadFilter [codigo=" + codigo + ", descri=" + descri + "]";
	}
	
	
}
