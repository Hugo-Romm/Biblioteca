package com.infoIV.biblioteca.repository.filter;

import java.io.Serializable;

//import com.infoIV.biblioteca.validation.SKU;

public class ConfigFilter implements Serializable {

	private static final long serialVersionUID = 1L;
    private Long codigo;
	private String organom;
	private Integer deumon;
	private Integer diamor;
	private Integer diapre;

	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public Integer getDeumon() {
		return deumon;
	}

	public void setDeumon(Integer deumon) {
		this.deumon = deumon;
	}

	public Integer getDiamor() {
		return diamor;
	}

	public void setDiamor(Integer diamor) {
		this.diamor = diamor;
	}

	public Integer getDiapre() {
		return diapre;
	}

	public void setDiapre(Integer diapre) {
		this.diapre = diapre;
	}

	public String getOrganom() {
		return organom;
	}

	public void setOrganom(String organom) {
		this.organom = organom;
	}

}