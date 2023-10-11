package com.infoIV.biblioteca.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;


@Entity
@Table(name="config")
public class Config implements Serializable {

	private static final long serialVersionUID = 1L;
    private Long codigo;
	private String organom;
	private Integer deumon;
	private Integer diamor;
	private Integer diapre;
	private String seña;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "config_seq")
	@SequenceGenerator(name = "config_seq", sequenceName = "config_seq", allocationSize=1)	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	@NotBlank
	@Size(max = 100)
	@Column(nullable = false, length = 100)	
	
	public String getOrganom() {
		return organom;
	}

	public void setOrganom(String organom) {
		this.organom = organom;
	}
	
	@NotNull @Min(0) @Max(value = 999999999, message = "El valor es muy alto. Verifique!")
	@Column(name="deumon", nullable = false, length = 10)
	
	public Integer getDeumon() {
		return deumon;
	}

	public void setDeumon(Integer deumon) {
		this.deumon = deumon;
	}
	
	@NotNull @Min(0) @Max(value = 999, message = "El valor es muy alto. Verifique!")
	@Column(name="diamor", nullable = false, length = 3)
	
	public Integer getDiamor() {
		return diamor;
	}

	public void setDiamor(Integer diamor) {
		this.diamor = diamor;
	}
	

	@NotNull @Min(0) @Max(value = 999, message = "El valor es muy alto. Verifique!")
	@Column(name="diapre", nullable = false, length = 3)	
	
	public Integer getDiapre() {
		return diapre;
	}
	public void setDiapre(Integer diapre) {
		this.diapre = diapre;
	}
	
	@Size(max = 50)
	@Column(length = 50)	
	
	public String getSeña() {
		return seña;
	}

	public void setSeña(String seña) {
		this.seña = seña;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Config other = (Config) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
		
}
