package com.infoIV.biblioteca.repository.filter;

import java.io.Serializable;

//import com.algaworks.pedidovenda.validation.SKU;

public class DeudaFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long num;
	private String descri;

	//@SKU
	public Long getNum() {
		return num;
}

	public void setNum(Long num) {
		this.num = num;
	}

	public String getDescri() {
		return descri;
	}

	public void setDescri(String descri) {
		this.descri = descri;
	}

}