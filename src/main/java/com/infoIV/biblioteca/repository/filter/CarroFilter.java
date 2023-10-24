package com.infoIV.biblioteca.repository.filter;

import java.io.Serializable;

//import com.algaworks.pedidovenda.validation.SKU;

public class CarroFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long codigo;
	private String marca;

	//@SKU
	public Long getCodigo() {
		return codigo;
}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	@Override
	public String toString() {
		return "CarroFilter [codigo=" + codigo + ", marca=" + marca + "]";
	}
	

}