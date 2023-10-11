package com.infoIV.biblioteca.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="imprenta")
public class Imprenta implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long codigo;
	private String razonsocial;	
	private String obse;


	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "imprenta_seq")
	@SequenceGenerator(name = "imprenta_seq", sequenceName = "imprenta_seq", allocationSize=1)	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	@NotBlank
	@Size(max = 80)
	@Column(nullable = false, length = 80)	
	
	public String getRazonsocial() {
		return razonsocial;
	}

	public void setRazonsocial(String razon_social) {
		this.razonsocial = razon_social;
	}
	
	
	
	@Size(max = 200)
	@Column(nullable = false, length = 200)
	
	public String getObse() {
		return obse;
	}

	public void setObse(String obse) {
		this.obse = obse;
	}
	

	@Transient
	public boolean isNovo() {
		return getCodigo() == null;
	}
	
	@Transient
	public boolean isExistente() {
		return !isNovo();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Imprenta other = (Imprenta) obj;
		
		if (codigo == null)
		{ if (other.codigo != null)
				return false;
		} 
		else 
			if (!codigo.equals(other.codigo))
			return false;
		
		
		return true;
	}

	
	
	
	
}
