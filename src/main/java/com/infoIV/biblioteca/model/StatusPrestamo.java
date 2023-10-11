package com.infoIV.biblioteca.model;

public enum StatusPrestamo {

	ACTIVO("Activo"), 
	DEVUELTO("Devuelto"), 
	ELIMINADO("Eliminado");
	
	private String descricao;
	
	StatusPrestamo(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
