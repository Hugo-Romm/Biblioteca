package com.infoIV.biblioteca.repository.filter;

import java.io.Serializable;

//import com.infoIV.biblioteca.validation.SKU;

public class ConfiguracionesFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long codigo;
	private String descri;

	//@SKU
	public Long getCodigo() {
		return codigo;
}

	public void setCodigo(Long codigo) {
		this.codigo = codigo == null ? null : codigo;
	}

	public String getDescri() {
		return descri;
	}

	public void setDescri(String descri) {
		this.descri = descri;
	}

}